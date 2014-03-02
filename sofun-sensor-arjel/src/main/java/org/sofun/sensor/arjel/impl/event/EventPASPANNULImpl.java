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
import org.sofun.sensor.arjel.api.event.EventPASPANNUL;
import org.sofun.sensor.arjel.api.event.EventPASPANNULFields;

/**
 * Event type PASPANNUL field constants.
 * 
 * @author @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventPASPANNULImpl extends XMLTraceImpl implements EventPASPANNUL {

    private static final long serialVersionUID = -6085487059046170477L;

    private String fieldTech;

    private String fieldClair;

    private String fieldDateHeure;

    private String fieldSoldeAvantRembours;

    private String fieldMontantRembours;

    private String fieldSoldeApresRembours;

    private String fieldInfo;

    public EventPASPANNULImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTech,
            String fieldClair, String fieldDateHeure,
            String fieldSoldeAvantRembours, String fieldMontantRembours,
            String fieldSoldeApresRembours, String fieldInfo) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTech = fieldTech;
        this.fieldClair = fieldClair;
        this.fieldDateHeure = fieldDateHeure;
        this.fieldSoldeAvantRembours = fieldSoldeAvantRembours;
        this.fieldMontantRembours = fieldMontantRembours;
        this.fieldSoldeApresRembours = fieldSoldeApresRembours;
        this.fieldInfo = fieldInfo;
    }
    
    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventPASPANNULFields.Tech).addText(
                    getFieldTech());
            root.addElement(EventPASPANNULFields.Clair).addText(
                    getFieldClair());
            root.addElement(EventPASPANNULFields.DateHeure).addText(
                    getFieldDateHeure());
            root.addElement(EventPASPANNULFields.SoldeAvantRembours).addText(
                    getFieldSoldeAvantRembours());
            root.addElement(EventPASPANNULFields.MontantRembours).addText(
                    getFieldMontantRembours());
            root.addElement(EventPASPANNULFields.SoldeApresRembours).addText(
                    getFieldSoldeApresRembours());
            root.addElement(EventPASPANNULFields.Info).addText(
                    getFieldInfo());
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
    public String getFieldSoldeAvantRembours() {
        return fieldSoldeAvantRembours;
    }

    @Override
    public void setFieldSoldeAvantRembours(String fieldSoldeAvantRembours) {
        this.fieldSoldeAvantRembours = fieldSoldeAvantRembours;
    }

    @Override
    public String getFieldMontantRembours() {
        return fieldMontantRembours;
    }

    @Override
    public void setFieldSoldeMontantRembours(String fieldMontantRembours) {
        this.fieldMontantRembours = fieldMontantRembours;
    }

    @Override
    public String getFieldSoldeApresRembours() {
        return fieldSoldeApresRembours;
    }

    @Override
    public void setFieldSoldeApresRembours(String fieldSoldeApresRembours) {
        this.fieldSoldeApresRembours = fieldSoldeApresRembours;
    }

    @Override
    public String getFieldInfo() {
        return fieldInfo;
    }

    @Override
    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    @Override
    public String getFieldDateHeure() {
        return fieldDateHeure;
    }

    @Override
    public void setFieldDateHeure(String fieldDateHeure) {
        this.fieldDateHeure = fieldDateHeure;
    }

}
