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
import org.sofun.sensor.arjel.api.event.EventCPTEABOND;
import org.sofun.sensor.arjel.api.event.EventCPTEABONDFields;

/**
 * Event type CPTEABOND implementation.
 * 
 * @author @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventCPTEABONDImpl extends XMLTraceImpl implements EventCPTEABOND {

    private static final long serialVersionUID = -3535040497975358040L;

    private String fieldTypAg;

    private String fieldSoldeAvant;

    private String fieldMontAbond;

    private String fieldSoldeApres;

    private String fieldInfo;

    private String fieldTypeAbondement;

    public EventCPTEABONDImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldSoldeAvant,
            String fieldMontAbond, String fieldSoldeApres, String fieldInfo,
            String fieldTypeAbondement) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldSoldeAvant = fieldSoldeAvant;
        this.fieldMontAbond = fieldMontAbond;
        this.fieldSoldeApres = fieldSoldeApres;
        this.fieldInfo = fieldInfo;
        this.fieldTypeAbondement = fieldTypeAbondement;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventCPTEABONDFields.TypAg)
                    .addText(getFieldTypAg());
            root.addElement(EventCPTEABONDFields.SoldeAvant).addText(
                    getFieldSoldeAvant());
            root.addElement(EventCPTEABONDFields.MontAbond).addText(
                    getFieldMontAbond());
            root.addElement(EventCPTEABONDFields.SoldeApres).addText(
                    getFieldSoldeApres());
            root.addElement(EventCPTEABONDFields.Info).addText(getFieldInfo());
            root.addElement(EventCPTEABONDFields.TypeAbondement).addText(
                    getFieldTypeAbondement());
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
    public String getFieldSoldeAvant() {
        return fieldSoldeAvant;
    }

    @Override
    public void setFieldSoldeAvant(String fieldSoldeAvant) {
        this.fieldSoldeAvant = fieldSoldeAvant;
    }

    @Override
    public String getFieldMontAbond() {
        return fieldMontAbond;
    }

    @Override
    public void setFieldMontAbond(String fieldMontAbond) {
        this.fieldMontAbond = fieldMontAbond;
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
    public String getFieldInfo() {
        return fieldInfo;
    }

    @Override
    public void setFieldInfo(String fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

    @Override
    public String getFieldTypeAbondement() {
        return fieldTypeAbondement;
    }

    @Override
    public void setFieldTypeAbondement(String fieldTypeAbondement) {
        this.fieldTypeAbondement = fieldTypeAbondement;
    }

}
