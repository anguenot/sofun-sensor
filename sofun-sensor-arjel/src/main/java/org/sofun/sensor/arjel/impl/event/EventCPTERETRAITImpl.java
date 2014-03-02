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
import org.sofun.sensor.arjel.api.event.EventCPTEALIMFields;
import org.sofun.sensor.arjel.api.event.EventCPTERETRAIT;
import org.sofun.sensor.arjel.api.event.EventCPTERETRAITFields;

/**
 * Event type CPTERETRAIT implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventCPTERETRAITImpl extends XMLTraceImpl implements
        EventCPTERETRAIT {

    private static final long serialVersionUID = -6074705394945788095L;

    private String fieldTypAg;

    private String fieldSoldeAvant;

    private String fieldSoldeMouvement;

    private String fieldSoldeApres;

    public EventCPTERETRAITImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldSoldeAvant,
            String fieldSoldeMouvement, String fieldSoldeApres) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldSoldeAvant = fieldSoldeAvant;
        this.fieldSoldeMouvement = fieldSoldeMouvement;
        this.fieldSoldeApres = fieldSoldeApres;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventCPTERETRAITFields.TypAg).addText(
                    getFieldTypAg());
            root.addElement(EventCPTEALIMFields.SoldeAvant).addText(
                    getFieldSoldeAvant());
            root.addElement(EventCPTEALIMFields.SoldeMouvement).addText(
                    getFieldSoldeMouvement());
            root.addElement(EventCPTEALIMFields.SoldeApres).addText(
                    getFieldSoldeApres());
        }
        return document;
    }

    @Override
    public String getFieldSoldeAvant() {
        return fieldSoldeAvant;
    }

    @Override
    public void setFieldSoldeAvant(String fieldSoldeAvant) {
        this.fieldSoldeAvant = fieldSoldeAvant;
    }

    @Override
    public String getFieldSoldeMouvement() {
        return fieldSoldeMouvement;
    }

    @Override
    public void setFieldSoldeMouvement(String fieldSoldeMouvement) {
        this.fieldSoldeMouvement = fieldSoldeMouvement;
    }

    @Override
    public String getFieldSoldeApres() {
        return fieldSoldeApres;
    }

    @Override
    public void setFieldSoldeApres(String fieldSoldeApres) {
        this.fieldSoldeApres = fieldSoldeApres;
    }

    @Override
    public String getFieldTypAg() {
        return fieldTypAg;
    }

    @Override
    public void setFieldTypAg(String fieldTypAg) {
        this.fieldTypAg = fieldTypAg;
    }

}
