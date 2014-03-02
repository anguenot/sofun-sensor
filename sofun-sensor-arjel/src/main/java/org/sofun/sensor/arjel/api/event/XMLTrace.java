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

package org.sofun.sensor.arjel.api.event;

import java.io.Serializable;

import org.sofun.sensor.arjel.api.exception.XMLValidationException;

/**
 * ARJEL XML Document
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface XMLTrace extends Serializable {

    /**
     * Validates XML Document against ARJEL XSD
     * 
     * <p>
     * 
     * Note, this is the responsibility of the sensor (not the safe) to ensure
     * XML documents are valid.
     * 
     * @see XSD schems within ARJEL technical resources.
     * 
     * @return true of false
     */
    void validate() throws XMLValidationException;

    /**
     * Translates structure's names and pre-defined values.
     * 
     * <p>
     * 
     * @see traduction.csv within ARJEL technical resources.
     * 
     */
    String translate();

    /**
     * Has the document been translated already?
     * 
     * @return true or false
     */
    boolean isTranslated();

    /**
     * Returns the XML representation.
     * 
     * @return a {@link String} containing the XML
     */
    String getXML();

    /**
     * Returns the mandatory and unique identifier as provided by ARJEL.
     * Uniqueness is guaranteed by ARJEL in between operators.
     * 
     * <p>
     * 
     * Note in case of multiple safes, this identifier must remain the same.
     * 
     * @return a {@link String}
     */
    String getOperatorID();

    /**
     * Sets the mandatory and unique identifier as provided by ARJEL. Uniqueness
     * is guaranteed by ARJEL in between operators.
     * 
     * <p>
     * 
     * Note in case of multiple safes, this identifier must remain the same.
     * 
     * @return a {@link String}
     */
    void setOperatorID(String operatorID);

    /**
     * Returns the optional identifier of the operator's safe. The first safe
     * starts at '1', the second '2' etc.
     * 
     * @return a {@link String}
     */
    String getSafeID();

    /**
     * Sets the optional identifier of the operator's safe. The first safe
     * starts at '1', the second '2' etc.
     * 
     * @return a {@link String}
     */
    void setSafeID(String safeID);

    /**
     * Returns the mandatory and unique game event identifier specific to a
     * given safe. Starting with zero (0) and automatically incremented. Safe
     * should ensure it is unique
     * 
     * @return a {@link String}
     */
    String getEventId();

    /**
     * Sets the mandatory and unique game event identifier specific to a given
     * safe. Starting with zero (0) and automatically incremented. Safe should
     * ensure it is unique
     * 
     * @param eventId: a {@link String}
     */
    void setEventId(String eventId);

    /**
     * Returns the mandatory and unique event date and time (up to seconds), in
     * UTC, of the game event as visible by the user. Sensor generates it.
     * 
     * @return a {@link String}
     */
    String getEventDate();

    /**
     * Sets the mandatory and unique event date and time (up to seconds), in
     * UTC, of the game event as visible by the user. Sensor generates it.
     * 
     * @param eventDate: a {@link String}
     */
    void setEventDate(String eventDate);

    /**
     * Returns Mandatory and unique player identifier. This must never changed
     * from account creation until account deletion.
     * 
     * @return a {@link String}
     */
    String getPlayerId();

    /**
     * Sets Mandatory and unique player identifier. This must never changed from
     * account creation until account deletion.
     * 
     * @param playerId: a {@link String}
     */
    void setPlayerId(String playerId);

    /**
     * Returns the mandatory and unique player remote IP as seen by gaming
     * platform.
     * 
     * @return a {@link String}
     */
    String getPlayerIP();

    /**
     * Sets the mandatory and unique player remote IP as seen by gaming
     * platform.
     * 
     * @param playerIP: a {@link String}
     */
    void setPlayerIP(String playerIP);

    /**
     * Returns the mandatory and unique session identifier (non user visible).
     * 
     * <p>
     * 
     * Sensor is responsible of session management.
     * 
     * @return a {@link String}
     */
    String getSessionID();

    /**
     * Sets the mandatory and unique session identifier (non user visible).
     * 
     * <p>
     * 
     * Sensor is responsible of session management.
     * 
     * @return a {@link String}
     */
    void setSessionID(String sessionID);

    /**
     * Returns the ARJEL event type.
     * 
     * <p>
     * 
     * @see {@link EventType}
     * 
     * @return a {@link String}
     */
    String getEventType();

    /**
     * Sets the ARJEL event type.
     * 
     * <p>
     * 
     * @see {@link EventType}
     * 
     * @param type: a {@link String}
     */
    void setEventType(String type);

    /**
     * Returns an element value given its name.
     * 
     * @param name: an element name.
     * @return a {@link String} or null if not found
     */
    String getElementTextValue(String name);

    /**
     * Sets an XML element value given its name.
     * 
     * @param name: an element name
     * @param value: a {@link String}
     */
    void setElementTextValue(String name, String value);

    /**
     * Add an HTML Comment.
     * 
     * @param after: element name after which to insert the comment
     * @param comment: the actual comment
     */
    void addHTMLComment(String after, String comment);

}
