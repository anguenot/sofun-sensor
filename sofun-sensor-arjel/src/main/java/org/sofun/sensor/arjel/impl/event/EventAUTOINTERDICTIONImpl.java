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
import org.sofun.sensor.arjel.api.event.EventAUTOINTERDICTION;
import org.sofun.sensor.arjel.api.event.EventAUTOINTERDICTIONFields;

/**
 * Event type AUTOINTERDICTION implementation
 *
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventAUTOINTERDICTIONImpl extends XMLTraceImpl implements
        EventAUTOINTERDICTION {

    private static final long serialVersionUID = 8015173387066458376L;

    private String fieldTypAg;

    private String fieldDateModif;

    private String fieldDuree;

    public EventAUTOINTERDICTIONImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldDateModif,
            String fieldDuree) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldDateModif = fieldDateModif;
        this.fieldDuree = fieldDuree;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventAUTOINTERDICTIONFields.TypAg).addText(
                    getFieldTypAg());
            root.addElement(EventAUTOINTERDICTIONFields.DateModif).addText(
                    getFieldDateModif());
            root.addElement(EventAUTOINTERDICTIONFields.Duree).addText(
                    getFieldDuree());
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
    public String getFieldDateModif() {
        return fieldDateModif;
    }

    @Override
    public void setFieldDateModif(String fieldDateModif) {
        this.fieldDateModif = fieldDateModif;
    }

    @Override
    public String getFieldDuree() {
        return fieldDuree;
    }

    @Override
    public void setFieldDuree(String fieldDuree) {
        this.fieldDuree = fieldDuree;
    }

}
