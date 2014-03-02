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

package org.sofun.sensor.arjel.impl.event;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.sofun.sensor.arjel.api.Configuration;
import org.sofun.sensor.arjel.api.EventCategory;
import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.arjel.api.event.XMLTraceFields;
import org.sofun.sensor.arjel.api.exception.XMLValidationException;

/**
 * ARJEL XML Trace implementation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class XMLTraceImpl implements XMLTrace {

    private static final long serialVersionUID = 8180279504545185928L;

    private static final Log log = LogFactory.getLog(XMLTraceImpl.class);

    private static final String XSI_NAMESPACE = "http://www.w3.org/2001/XMLSchema";

    private boolean translated = false;

    private String operatorID;

    private String safeID;

    private String eventId;

    private String eventType;

    private String eventDate;

    private String playerId;

    private String playerIP;

    private String sessionID;

    /** Element's translations: map from verbose element to reduced element */
    private static final Map<String, String> translations = new HashMap<String, String>();

    /** Cached DOM document. Implementation internals */
    protected transient Document document;

    /** Document factory instance. Implementation internals */
    protected DocumentFactory df = DocumentFactory.getInstance();

    public XMLTraceImpl(String operatorID, String eventType, String eventDate,
            String playerID, String playerIP, String sessionID) {
        this.operatorID = operatorID;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.playerId = playerID;
        this.playerIP = playerIP;
        this.sessionID = sessionID;
        this.safeID = "1"; // Default Safe ID
        this.eventId = ""; // Safe will generate this.
    }

    public XMLTraceImpl(String operatorID, String safeID, String eventType,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID) {
        this(operatorID, eventType, eventDate, playerID, playerIP, sessionID);
        this.eventId = eventId;
        this.safeID = safeID;
    }

    public XMLTraceImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP, String sessionID) {
        // This must be implemented by children classes.
        this(operatorID, safeID, "", eventId, eventDate, playerID, playerIP,
                sessionID);
    }

    /**
     * Returns a XSD file given its name.
     * 
     * @param schema: the schema name
     * @return a {@link File} instance
     */
    private File getSchemaResource(String schema) {
        return new File(Configuration.getProperties().getProperty(
                "schema.location")
                + "/"
                + Configuration.getProperties().getProperty("det.version")
                + "/xsd" + "/" + schema);
    }

    /**
     * Returns the CSV elements translation file
     * 
     * @return a {@link File} instance
     */
    private File getElementsTranslationFile() {
        return new File(Configuration.getProperties().getProperty(
                "schema.location")
                + "/"
                + Configuration.getProperties().getProperty("det.version")
                + "/traduction.csv");
    }

    /**
     * Returns the translation of element name.
     * 
     * @param name: the `verbose` element name
     * @return the corresponding translated name.
     */
    private String getTranslatedNameFor(String name) {
        if (translations.size() == 0) {
            FileInputStream in = null;
            InputStreamReader streamReader = null;
            BufferedReader bufferedReader = null;
            File f = null;
            try {
                f = getElementsTranslationFile();
                in = new FileInputStream(f);
                streamReader = new InputStreamReader(in, "UTF-8");
                bufferedReader = new BufferedReader(streamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    final String[] params = line.split(";");
                    translations.put(params[0], params[1]);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
                if (streamReader != null) {
                    try {
                        streamReader.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        }
        return translations.get(name);
    }

    /**
     * Returns the internal representation of the XML Document.
     * 
     * @return a {@link Document} instance
     */
    protected Document getDocument() {
        if (document == null) {
            document = DocumentHelper.createDocument();
            // Header (order matters)
            Element root = document.addElement(getEventType());
            // If values below are null we log the issue. The validation will
            // fail at proxy level.
            if (getOperatorID() == null) {
                log.error(XMLTraceFields.IDOper + " is null");
            } else {
                root.addElement(XMLTraceFields.IDOper).addText(getOperatorID());
            }
            if (getEventDate() == null) {
                log.error(XMLTraceFields.DateEvt + " is null");
            } else {
                root.addElement(XMLTraceFields.DateEvt).addText(getEventDate());
            }
            if (getEventId() == null) {
                log.error(XMLTraceFields.IDEvt + " is null");
            } else {
                root.addElement(XMLTraceFields.IDEvt).addText(getEventId());
            }
            if (getPlayerId() == null) {
                log.error(XMLTraceFields.IDJoueur + " is null");
            } else {
                root.addElement(XMLTraceFields.IDJoueur).addText(getPlayerId());
            }
            if (getSessionID() == null) {
                log.error(XMLTraceFields.IDSession + " is null");
            } else {
                root.addElement(XMLTraceFields.IDSession).addText(
                        getSessionID());
            }
            if (getPlayerIP() == null) {
                log.error(XMLTraceFields.IPJoueur + " is null");
            } else {
                root.addElement(XMLTraceFields.IPJoueur).addText(getPlayerIP());
            }
            // Not mandatory
            root.addElement(XMLTraceFields.IDCoffre).addText(getSafeID());
        }
        return document;
    }

    @Override
    public void validate() throws XMLValidationException {

        List<Source> schemas = new ArrayList<Source>();

        // Decides which XML Schema must be used for validation.
        if (EventCategory.getPlayerAcccountEvents().contains(getEventType())
                || EventCategory.getBankingEvents().contains(getEventType())) {
            schemas.add(new StreamSource(getSchemaResource("CJ.xsd")));
        } else if (EventCategory.getBettingEvents().contains(getEventType())) {
            schemas.add(new StreamSource(getSchemaResource("PASP.xsd")));
        } else {
            throw new XMLValidationException(
                    "Event Type does not match any category. "
                            + "Impossible to validate");
        }

        // Lookup a factory for the W3C XML Schema language
        SchemaFactory factory = SchemaFactory.newInstance(XSI_NAMESPACE);

        File xml = null;
        try {

            // Compile the schemas.
            Schema schema = factory.newSchema(schemas
                    .toArray(new Source[schemas.size()]));
            Validator validator = schema.newValidator();

            // load the file to validate
            final SecureRandom randomGenerator = new SecureRandom();
            xml = File.createTempFile(
                    String.valueOf(randomGenerator.nextInt(1000000)), ".xml");
            BufferedWriter out = new BufferedWriter(new FileWriter(xml));
            out.write(new String(getXML().getBytes(), "UTF-8"));
            out.close();

            // check the document
            Source source = new StreamSource(xml);
            validator.validate(source);

        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            throw new XMLValidationException(e.getMessage());
        } finally {
            if (xml != null) {
                boolean deleted = xml.delete();
                if (!deleted) {
                    log.error("Issue deleting temporary file with path="
                            + xml.getPath());
                }
            }
        }

    }

    @Override
    public String translate() {
        Element root = getDocument().getRootElement();
        if (!isTranslated()) {
            root.setName(getTranslatedNameFor(root.getName()));
            for (@SuppressWarnings("unchecked")
            Iterator<Element> i = root.elementIterator(); i.hasNext();) {
                Element element = i.next();
                element.setName(getTranslatedNameFor(element.getName()));
                // First level only required
                for (@SuppressWarnings("unchecked")
                Iterator<Element> j = element.elementIterator(); j.hasNext();) {
                    Element child = j.next();
                    child.setName(getTranslatedNameFor(child.getName()));

                }
            }
            translated = true;
        }
        return root.asXML();
    }

    @Override
    public String getXML() {
        // many UTF-8 encoded files include a three-byte UTF-8 Byte-order mark.
        // remove it otherwise we cannot validate XML document
        // http://en.wikipedia.org/wiki/Byte_Order_Mark
        return getDocument().asXML().trim().replaceFirst("^([\\W]+)<", "<");
    }

    @Override
    public String getOperatorID() {
        return operatorID;
    }

    @Override
    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    @Override
    public String getSafeID() {
        if (safeID == null) {
            // SafeID is optional and defaults to 1.
            return "1";
        }
        return safeID;
    }

    @Override
    public void setSafeID(String safeID) {
        this.safeID = safeID;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getEventDate() {
        return eventDate;
    }

    @Override
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String getPlayerId() {
        return playerId;
    }

    @Override
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String getPlayerIP() {
        return playerIP;
    }

    @Override
    public void setPlayerIP(String playerIP) {
        this.playerIP = playerIP;
    }

    @Override
    public String getSessionID() {
        return sessionID;
    }

    @Override
    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public void setEventType(String type) {
        this.eventType = type;
    }

    @Override
    public boolean isTranslated() {
        return translated;
    }

    @Override
    public void setElementTextValue(String name, String value) {
        if (name != null && value != null) {
            final String xpath = "//" + getEventType() + "/" + name;
            Node node = getDocument().selectSingleNode(xpath);
            if (node != null) {
                node.setText(value);
            } else {
                log.error("Cannot find path=" + xpath);
            }
        } else {
            log.error("Name and value pair should be not null.");
        }
    }

    @Override
    public String getElementTextValue(String name) {
        String value = null;
        if (name != null) {
            final String xpath = "//" + getEventType() + "/" + name;
            Node node = getDocument().selectSingleNode(xpath);
            if (node != null) {
                value = node.getText();
            } else {
                log.error("Cannot find path=" + xpath);
            }
        } else {
            log.error("Name cannot be null.");
        }
        return value;
    }

    @Override
    public void addHTMLComment(String after, String comment) {
        if (after != null && comment != null) {
            final String xpath = "//" + getEventType() + "/" + after;
            Element el = getDocument().getRootElement().element(after);
            if (el != null) {
                Comment c = df.createComment(comment);
                el.add(c);
            } else {
                log.error("Cannot find path=" + xpath);
            }
        } else {
            log.error("Name and value pair should be not null.");
        }
    }

}
