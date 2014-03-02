/*
 * Copyright (c)  Sofun Gaming SAS.
 * Copyright (c)  Julien Anguenot <julien@anguenot.org>
 * Copyright (c)  Julien De Preaumont <juliendepreaumont@gmail.com>
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Julien Anguenot <julien@anguenot.org> - initial API and implementation
*/

package org.sofun.sensor.cecurity.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.arjel.api.exception.XMLValidationException;
import org.sofun.sensor.cecurity.api.CecurityService;
import org.sofun.sensor.cecurity.api.ClientIDGenerator;
import org.sofun.sensor.cecurity.api.Configuration;
import org.sofun.sensor.cecurity.api.MessageInHeaderField;
import org.sofun.sensor.cecurity.api.TimeGenerator;
import org.sofun.sensor.cecurity.api.ejb.CecurityServiceLocal;
import org.sofun.sensor.cecurity.api.ejb.CecurityServiceRemote;
import org.sofun.sensor.cecurity.api.exception.CecurityException;
import org.sofun.sensor.storage.api.Archive;
import org.sofun.sensor.storage.api.StorageService;
import org.sofun.sensor.storage.api.ejb.StorageServiceLocal;
import org.sofun.sensor.storage.api.exception.StorageException;
import org.sofun.sensor.storage.impl.ArchiveImpl;

/**
 * Cecurity Service Implementation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Stateless
@Local(CecurityServiceLocal.class)
@Remote(CecurityServiceRemote.class)
public class CecurityServiceImpl implements CecurityService {

    private static final long serialVersionUID = -8885296082161243997L;

    private static final Log log = LogFactory.getLog(CecurityServiceImpl.class);

    @EJB(
            beanName = "StorageServiceImpl",
            beanInterface = StorageServiceLocal.class)
    private transient StorageService storage;

    private static final ClientIDGenerator idGen = new ClientIDGeneratorImpl();

    private static final TimeGenerator timeGen = new TimeGeneratorImpl();

    private static final String hashAlgo = (String) Configuration
            .getProperties().get("message.hash.algo");

    /** Queue where to send messages out to Cecurity */
    private static final String eventIn = (String) Configuration
            .getProperties().get("queue.in");

    /**
     * Queue where to receive ACK messages. For every sent message an ACK
     * message will be returned on this queue.
     */
    private static final String eventOut = (String) Configuration
            .getProperties().get("queue.out");

    /**
     * Queue where to receive archived ACK message. Messages are archived by
     * `lot` which contains a set of messages archived at once by the safe.
     */
    private static final String archiveOut = (String) Configuration
            .getProperties().get("queue.archive");

    /** `eventOut` JMS queue listener */
    private static CecurityEventOutListener out = new CecurityEventOutListener();

    /** `archiveOut` JMS queue listener */
    private static CecurityArchiveOutListener archives = new CecurityArchiveOutListener();

    /** Connection and session are not shared across threads, it's thread-safe */
    private static volatile ConnectionFactory inConnectionFactory;
    private static volatile ConnectionFactory outConnectionFactory;

    /** ActiveMQ JMS Connection pool */
    private static volatile PooledConnectionFactory inPooledConnectionFactory;
    private static volatile PooledConnectionFactory outPooledConnectionFactory;

    /** JMS Connection (not shared in between threads) */
    private transient Connection inConnection = null;
    private transient Connection outConnection = null;

    /** JMS session out (not shared in between threads) */
    private transient Session inSession = null;
    private transient Session outSession = null;

    /** JMS message producer (not shared in between threads) */
    private transient MessageProducer producer = null;

    public CecurityServiceImpl() {

    }

    /**
     * Returns the ActiveMQ Incoming connection factory.
     * 
     * <p>
     * 
     * Incoming here is queue centered : it means from sensor to e-safe
     * 
     * <p>
     * 
     * Thread safe: works with acquire/release semantics for volatile
     * 
     * @return a {@link ConnectionFactory} instance
     */
    private ConnectionFactory getInConnectionFactory() {
        ConnectionFactory con = inConnectionFactory;
        if (con == null) {
            synchronized (this) {
                con = inConnectionFactory;
                if (con == null) {
                    inConnectionFactory = con = new ActiveMQConnectionFactory(
                            (String) Configuration.getProperties().get(
                                    "broker.in.url"));
                }
            }
        }
        return inConnectionFactory;
    }

    /**
     * Returns the ActiveMQ Outgoing connection factory.
     * 
     * <p>
     * 
     * Outgoing here is queue centered : it means from e-safe to sensor
     * 
     * <p>
     * 
     * Thread safe: works with acquire/release semantics for volatile
     * 
     * @return a {@link ConnectionFactory} instance
     */
    private ConnectionFactory getOutConnectionFactory() {
        ConnectionFactory con = outConnectionFactory;
        if (con == null) {
            synchronized (this) {
                con = outConnectionFactory;
                if (con == null) {
                    outConnectionFactory = con = new ActiveMQConnectionFactory(
                            (String) Configuration.getProperties().get(
                                    "broker.out.url"));
                }
            }
        }
        return inConnectionFactory;
    }

    /**
     * Returns the ActiveMQ Incoming polled connection factory.
     * 
     * <p>
     * 
     * Incoming here is queue centered : it means from sensor to e-safe
     * 
     * <p>
     * 
     * Thread safe: works with acquire/release semantics for volatile
     * 
     * @return a {@link PooledConnectionFactory} instance
     */
    private PooledConnectionFactory getInPooledConnectionFactory() {
        PooledConnectionFactory pool = inPooledConnectionFactory;
        if (pool == null) {
            synchronized (this) {
                pool = inPooledConnectionFactory;
                if (pool == null) {
                    inPooledConnectionFactory = pool = new PooledConnectionFactory();
                    inPooledConnectionFactory
                            .setConnectionFactory(getInConnectionFactory());
                }
            }
        }
        return inPooledConnectionFactory;
    }

    /**
     * Returns the ActiveMQ Outgoing pooled connection factory.
     * 
     * <p>
     * 
     * Outgoing here is queue centered : it means from e-safe to sensor
     * 
     * <p>
     * 
     * Thread safe: works with acquire/release semantics for volatile
     * 
     * @return a {@link PooledConnectionFactory} instance
     */
    private PooledConnectionFactory getOutPooledConnectionFactory() {
        PooledConnectionFactory pool = outPooledConnectionFactory;
        if (pool == null) {
            synchronized (this) {
                pool = outPooledConnectionFactory;
                if (pool == null) {
                    outPooledConnectionFactory = pool = new PooledConnectionFactory();
                    outPooledConnectionFactory
                            .setConnectionFactory(getOutConnectionFactory());
                }
            }
        }
        return inPooledConnectionFactory;
    }

    @Override
    @PostConstruct
    public void init() {

        // Initializing 2 connections to the cecurity safe. We need to
        // maintain 2 different connections of incoming and outgoing
        // messages because of the fail over settings. See Cecurity HA
        // documentation.

        try {

            // Incoming connection : from sensor to e-safe
            inConnection = getInPooledConnectionFactory().createConnection();
            inSession = inConnection.createSession(true,
                    Session.AUTO_ACKNOWLEDGE);
            // Event.In queue
            Destination destination = inSession.createQueue(eventIn);
            producer = inSession.createProducer(destination);
            inConnection.start();

            // Outgoing connection : from e-safe to sensor
            outConnection = getOutPooledConnectionFactory().createConnection();
            outSession = outConnection.createSession(true,
                    Session.AUTO_ACKNOWLEDGE);
            // Event.Out queue
            Destination feedback = outSession.createQueue(eventOut);
            MessageConsumer consumer = outSession.createConsumer(feedback);
            consumer.setMessageListener(out);
            // Archive.Out queue
            Destination archive = outSession.createQueue(archiveOut);
            MessageConsumer archiveConsumer = outSession
                    .createConsumer(archive);
            archiveConsumer.setMessageListener(archives);
            outConnection.start();

        } catch (JMSException e) {
            log.error("Cannot connect to remote Cecurity queue. Will retry later.");
            log.error(e.getMessage());
        }

    }

    @Override
    public XMLTrace preProcess(XMLTrace trace) {
        /**
         * Event unique identifier is generated by the safe. Instead of
         * including an identifier before sending the trace to the actual safe
         * we add an HTML comment such as (<!--SAFE_ID-->) It will then be
         * replaced by a unique and sequential unique identifier safe side.
         */
        final String ID_SAFE_HTML_COMMENT = "SAFE_ID";
        // Reset element text value if any.
        trace.setElementTextValue("IDEvt", "");
        // Add an HTML comment as required by Cecurity
        // Safe (Cecurity) is responsible for the event identifier generation.
        trace.addHTMLComment("IDEvt", ID_SAFE_HTML_COMMENT);
        return trace;
    }

    @Override
    public void store(XMLTrace trace) throws CecurityException {

        // Trace must *not* be translated. See below for processing.
        if (trace.isTranslated()) {
            final String msg = "Trace must not be translated in order to "
                    + "be pre-processed.";
            log.error(msg);
            throw new CecurityException(msg);
        }

        // Validate the trace. This is the responsibility of the sensor to do
        // so. If the trace is invalid no need to go further. We spit it out and
        // it must fail. We do have an issue at Sensor level.
        try {
            trace.validate();
        } catch (XMLValidationException e) {
            throw new CecurityException(e);
        }

        // Process actual trace ; dealing with Cecurity specifics
        trace = preProcess(trace);

        log.info("Sending trace:" + trace.getXML());

        // ARJEL Translation of the XML
        final String content = trace.translate();

        // We generate and store an archive of the trace before trying to send
        // it out. We *must* raise if it fails. Be careful with this if
        // changing the code below
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashAlgo);
            md.update(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new CecurityException(e);
        } catch (UnsupportedEncodingException e) {
            throw new CecurityException(e);
        }
        final String digest = new String(Hex.encodeHex(md.digest()));
        final String clientId = idGen.getUniqueClientID();
        final long timestamp = timeGen.getTime().getTime();

        final Map<String, String> props = new HashMap<String, String>();
        props.put(MessageInHeaderField.JH_DIGEST, digest);
        props.put(MessageInHeaderField.DIGEST_ALGO, hashAlgo);
        props.put(MessageInHeaderField.CLIENT_ID, clientId);
        props.put(MessageInHeaderField.TIME_STAMP, String.valueOf(timestamp));
        final Archive archive = new ArchiveImpl(clientId, content, props);
        try {
            storage.set(archive);
        } catch (StorageException e) {
            throw new CecurityException(e);
        }
        send(archive);

    }

    @Override
    public void send(Archive archive) throws CecurityException {

        if (archive == null) {
            log.warn("Trying to resent an archive when the given actual "
                    + "archive is null.");
            return;
        }

        if (archive.isSent()) {
            if (archive.isSafeArchived()) {
                try {
                    log.warn("Trying to resent an archive already marked as archived."
                            + " Removin archive");
                    storage.del(archive.getKey());
                } catch (StorageException e) {
                    throw new CecurityException(e);
                }
                return;
            }
            // This archive has already been flagged as sent out by might not
            // have been acked. Let's retry to make sure. If this is a duplicate
            // it will be handled later on when ACK message will be analyzed.
            log.warn("Trying to schedule an archive for resent when the actual "
                    + "archive is already marked as sent out. key="
                    + archive.getKey());
            // If the archive is marked as acked we schedule for resent. Worst
            // case Cecurity will tell us this is a duplicate and it will get
            // removed later on. Better be safe than sorry.
        }

        final String content = archive.getXML();
        final String digest = archive.getProperties().get(
                MessageInHeaderField.JH_DIGEST);
        final String clientId = archive.getProperties().get(
                MessageInHeaderField.CLIENT_ID);
        final long timestamp = Long.valueOf(archive.getProperties().get(
                MessageInHeaderField.TIME_STAMP));

        TextMessage message = null;
        boolean sent = false;
        try {
            // Verify if session has been established
            // @see init()
            if (inSession == null) {
                log.warn("A problem occured with remote ActiveMQ. "
                        + "Reinitializing JMS session...");
                init();
            }
            // If connection failed we will retry later using actual archive.
            // Note, archives are always stored before returning the response to
            // a player request.
            if (inSession != null) {
                message = inSession.createTextMessage(content);
                message.setStringProperty(MessageInHeaderField.JH_DIGEST,
                        digest);
                message.setStringProperty(MessageInHeaderField.DIGEST_ALGO,
                        hashAlgo);
                message.setStringProperty(MessageInHeaderField.CLIENT_ID,
                        clientId);
                message.setLongProperty(MessageInHeaderField.TIME_STAMP,
                        timestamp);
                producer.send(message);
                inSession.commit();
                sent = true;
            }
        } catch (Throwable e) {
            log.error(e.getMessage());
            // this will be rechecked next time the archive is rescheduled
            inSession = null;
        } finally {
            if (sent) {
                final String infoStr = "Sending : " + digest + ";" + hashAlgo
                        + ";" + clientId + ";" + timestamp + ";" + content;
                log.debug(infoStr);
                archive.setSent(true);
                try {
                    storage.set(archive);
                } catch (StorageException e) {
                    throw new CecurityException(e);
                }
            } else {
                log.error("Cannot send message to Cecurity. Will retry later....");
            }
        }
    }

    @Timeout
    @Schedule(minute = "*/5", hour = "*", persistent = false)
    public void commitOutSession() {
        log.debug("commitOutSession(): commiting Cecurity out session.");
        if (outSession != null) {
            // We need to commit this JMS OUYT session to remove the
            // Event.<N>.Out and Archive.<N>.Out from the queue Cecurity side.
            try {
                outSession.commit();
            } catch (JMSException e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                // This is case is not "exceptional"
                log.debug("Cannot COMMIT Cecurity JMS OUT session "
                        + e.getMessage());
            }
        }
    }

    @PreDestroy
    public void destroyBean() {
        log.debug("@PreDestroy: commiting & closing Cecurity out session.");
        if (outSession != null) {
            // We need to commit this JMS OUT session to remove the
            // Event.<N>.Out and Archive.<N>.Out from the queue Cecurity side.
            try {
                outSession.commit();
                outSession.close();
            } catch (JMSException e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                // This is case is not "exceptional"
                log.debug("Cannot COMMIT OR CLOSE Cecurity JMS OUT session "
                        + e.getMessage());
            }
        }
        if (inSession != null) {
            try {
                inSession.close();
            } catch (JMSException e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error("Cannot CLOSE Cecurity JMS IN session "
                        + e.getMessage());
            }
        }
    }

}
