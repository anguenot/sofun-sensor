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
import org.sofun.sensor.arjel.api.event.EventPASPGAIN;
import org.sofun.sensor.arjel.api.event.EventPASPGAINFields;

/**
 * Event type PASPGAIN implementation.
 * 
 * @author @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventPASPGAINImpl extends XMLTraceImpl implements EventPASPGAIN {

    private static final long serialVersionUID = 7050909684948275521L;

    private String fieldTech;

    private String fieldClair;

    private String fieldDateHeure;

    private String fieldSoldeAvantGain;

    private String fieldGain;

    private String fieldSoldeApresGain;

    private String fieldGainAbond;

    private String fieldInfo;

    public EventPASPGAINImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTech, String fieldClair,
            String fieldDateHeure, String fieldSoldeAvantGain,
            String fieldGain, String fieldSoldeApresGain,
            String fieldGainAbond, String fieldInfo) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTech = fieldTech;
        this.fieldClair = fieldClair;
        this.fieldDateHeure = fieldDateHeure;
        this.fieldSoldeAvantGain = fieldSoldeAvantGain;
        this.fieldGain = fieldGain;
        this.fieldSoldeApresGain = fieldSoldeApresGain;
        this.fieldGainAbond = fieldGainAbond;
        this.fieldInfo = fieldInfo;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventPASPGAINFields.Tech).addText(getFieldTech());
            root.addElement(EventPASPGAINFields.Clair).addText(getFieldClair());
            root.addElement(EventPASPGAINFields.DateHeure).addText(
                    getFieldDateHeure());
            root.addElement(EventPASPGAINFields.SoldeAvantGain).addText(
                    getFieldSoldeAvantGain());
            root.addElement(EventPASPGAINFields.Gain).addText(getFieldGain());
            root.addElement(EventPASPGAINFields.SoldeApresGain).addText(
                    getFieldSoldeApresGain());
            root.addElement(EventPASPGAINFields.GainAbond).addText(
                    getFieldGainAbond());
            root.addElement(EventPASPGAINFields.Info).addText(getFieldInfo());

        }
        return document;
    }

    @Override
    public String getFieldTech() {
        return fieldTech;
    }

    @Override
    public void setFieldTech(String fieldTech) {
        this.fieldTech = fieldTech;
    }

    @Override
    public String getFieldClair() {
        return fieldClair;
    }

    @Override
    public void setFieldClair(String fieldClair) {
        this.fieldClair = fieldClair;
    }

    @Override
    public String getFieldDateHeure() {
        return fieldDateHeure;
    }

    @Override
    public void setFieldDateHeure(String fieldDateHeure) {
        this.fieldDateHeure = fieldDateHeure;
    }

    @Override
    public String getFieldSoldeAvantGain() {
        return fieldSoldeAvantGain;
    }

    @Override
    public void setFieldSoldeAvantGain(String fieldSoldeAvantGain) {
        this.fieldSoldeAvantGain = fieldSoldeAvantGain;
    }

    @Override
    public String getFieldGain() {
        return fieldGain;
    }

    @Override
    public void setFieldGain(String fieldGain) {
        this.fieldGain = fieldGain;
    }

    @Override
    public String getFieldSoldeApresGain() {
        return fieldSoldeApresGain;
    }

    @Override
    public void setFieldSoldeApresGain(String fieldSoldeApresGain) {
        this.fieldSoldeApresGain = fieldSoldeApresGain;
    }

    @Override
    public String getFieldGainAbond() {
        return fieldGainAbond;
    }

    @Override
    public void setFieldGainAbond(String fieldGainAbond) {
        this.fieldGainAbond = fieldGainAbond;
    }

    @Override
    public String getFieldInfo() {
        return fieldInfo;
    }

    @Override
    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

}
