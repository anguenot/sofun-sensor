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

import java.util.Enumeration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.storage.api.StorageService;
import org.sofun.sensor.storage.api.exception.StorageException;

/**
 * Abstract Cecurity JMS Message Listener.
 * 
 * <p>
 * 
 * Defines some commons API for Cecurity JMS message listeners.
 * 
 * @see CecurityEventOutListener: Event.<N>.Out listener
 * @see CecurityArchiveOutListener: Archive.<N>.Out listener
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public abstract class AbstractCecurityListener implements MessageListener {

    private static final Log log = LogFactory
            .getLog(AbstractCecurityListener.class);

    /** Sensor storage */
    private transient StorageService storage;

    /**
     * Returns the sensor's storage service.
     * 
     * <p>
     * 
     * Storage service is an EJB
     * 
     * @return a {@link StorageService} instance.
     * @throws StorageException
     */
    protected StorageService getStorage() throws StorageException {
        if (storage == null) {
            InitialContext ctx;
            try {
                ctx = new InitialContext();
                return (StorageService) ctx
                        .lookup("sofun-sensor/StorageServiceImpl/local");
            } catch (NamingException e) {
                throw new StorageException(e);
            }
        }
        return storage;
    }

    /**
     * Log details about the received message when in debug mode.
     * 
     * @param message: a {@link Message} instance.
     * @throws JMSException
     */
    protected void logMessageDetails(Message message) throws JMSException {
        if (log.isDebugEnabled()) {
            @SuppressWarnings("rawtypes")
            Enumeration props = message.getPropertyNames();
            while (props.hasMoreElements()) {
                String propName = (String) props.nextElement();
                log.debug(propName + ": " + message.getObjectProperty(propName));
            }
        }
    }

}
