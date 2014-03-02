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

import org.dom4j.Document;
import org.dom4j.Element;
import org.sofun.sensor.arjel.api.event.EventCLOTUREDEM;
import org.sofun.sensor.arjel.api.event.EventCLOTUREDEMFields;

/**
 * Event type CLOTUREDEM implementation
 *
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventCLOTUREDEMImpl extends XMLTraceImpl implements
        EventCLOTUREDEM {

    private static final long serialVersionUID = 5770091097194529164L;

    private String fieldTypAg;

    private String fieldSoldeClos;

    public EventCLOTUREDEMImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldSoldeClos) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldSoldeClos = fieldSoldeClos;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventCLOTUREDEMFields.TypAg).addText(
                    getFieldTypAg());
            root.addElement(EventCLOTUREDEMFields.SoldeClos).addText(
                    getFieldSoldeClos());
        }
        return document;
    }

    @Override
    public String getFieldTypAg() {
        return fieldTypAg;
    }

    @Override
    public void setFieldTypAg(String fieldTypAg) {
        this.fieldTypAg = fieldTypAg;
    }

    @Override
    public String getFieldSoldeClos() {
        return fieldSoldeClos;
    }

    @Override
    public void setFieldSoldeClos(String fieldSoldeClos) {
        this.fieldSoldeClos = fieldSoldeClos;
    }

}
