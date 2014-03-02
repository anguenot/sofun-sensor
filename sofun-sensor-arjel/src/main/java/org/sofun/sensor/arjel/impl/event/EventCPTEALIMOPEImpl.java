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
import org.sofun.sensor.arjel.api.event.EventCPTEALIMOPE;
import org.sofun.sensor.arjel.api.event.EventCPTEALIMOPEFields;

/**
 * Event type CPTEALIMOPE implementation.
 * 
 * @author @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventCPTEALIMOPEImpl extends XMLTraceImpl implements
        EventCPTEALIMOPE {

    private static final long serialVersionUID = 7818300970528692832L;

    private String fieldTypAg;

    private String fieldBonusAvant;

    private String fieldBonusApres;

    private String fieldBonusMouvement;

    private String fieldBonusNom;

    public EventCPTEALIMOPEImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldBonusAvant,
            String fieldBonusApres, String fieldBonusMouvement,
            String fieldBonusNom) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldBonusAvant = fieldBonusAvant;
        this.fieldBonusApres = fieldBonusApres;
        this.fieldBonusMouvement = fieldBonusMouvement;
        this.fieldBonusNom = fieldBonusNom;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventCPTEALIMOPEFields.TypAg).addText(
                    getFieldTypAg());
            root.addElement(EventCPTEALIMOPEFields.BonusAvant).addText(
                    getFieldBonusAvant());
            root.addElement(EventCPTEALIMOPEFields.BonusMouvement).addText(
                    getFieldBonusMouvement());
            root.addElement(EventCPTEALIMOPEFields.BonusApres).addText(
                    getFieldBonusApres());
            root.addElement(EventCPTEALIMOPEFields.BonusNom).addText(
                    getFieldBonusNom());
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
    public String getFieldBonusAvant() {
        return fieldBonusAvant;
    }

    @Override
    public void setFieldBonusAvant(String fieldBonusAvant) {
        this.fieldBonusAvant = fieldBonusAvant;
    }

    @Override
    public String getFieldBonusApres() {
        return fieldBonusApres;
    }

    @Override
    public void setFieldBonusApres(String fieldBonusApres) {
        this.fieldBonusApres = fieldBonusApres;
    }

    @Override
    public String getFieldBonusMouvement() {
        return fieldBonusMouvement;
    }

    @Override
    public void setFieldBonusMouvement(String fieldBonusMouvement) {
        this.fieldBonusMouvement = fieldBonusMouvement;
    }

    @Override
    public String getFieldBonusNom() {
        return fieldBonusNom;
    }

    @Override
    public void setFieldBonusNom(String fieldBonusNom) {
        this.fieldBonusNom = fieldBonusNom;
    }

}
