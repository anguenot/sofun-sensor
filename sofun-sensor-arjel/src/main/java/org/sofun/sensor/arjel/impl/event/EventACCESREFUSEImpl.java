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
import org.sofun.sensor.arjel.api.event.EventACCESREFUSE;
import org.sofun.sensor.arjel.api.event.EventACCESREFUSEFields;

/**
 * Event type ACCESREFUSE implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventACCESREFUSEImpl extends XMLTraceImpl implements
        EventACCESREFUSE {

    private static final long serialVersionUID = 3078932458935515150L;

    private String fieldTypAg;

    private String fieldCauseRefus;

    private String fieldTypeRefus;

    public EventACCESREFUSEImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldCauseRefus,
            String fieldTypeRefus) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldCauseRefus = fieldCauseRefus;
        this.fieldTypeRefus = fieldTypeRefus;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventACCESREFUSEFields.TypAg).addText(
                    getFieldTypAg());
            if (getFieldCauseRefus() != null && !getFieldCauseRefus().isEmpty()) {
                root.addElement(EventACCESREFUSEFields.CauseRefus).addText(
                        getFieldCauseRefus());
            }
            if (getFieldTypeRefus() != null && !getFieldTypeRefus().isEmpty()) {
                root.addElement(EventACCESREFUSEFields.TypeRefus).addText(
                        getFieldTypeRefus());
            }
        }
        return document;
    }

    public String getFieldTypAg() {
        return fieldTypAg;
    }

    public void setFieldTypAg(String fieldTypAg) {
        this.fieldTypAg = fieldTypAg;
    }

    @Override
    public String getFieldCauseRefus() {
        return fieldCauseRefus;
    }

    @Override
    public void setFieldCauseRefus(String fieldCauseRefus) {
        this.fieldCauseRefus = fieldCauseRefus;
    }

    @Override
    public String getFieldTypeRefus() {
        return fieldTypeRefus;
    }

    @Override
    public void setFieldTypeRefus(String fieldTypeRefus) {
        this.fieldTypeRefus = fieldTypeRefus;
    }

}
