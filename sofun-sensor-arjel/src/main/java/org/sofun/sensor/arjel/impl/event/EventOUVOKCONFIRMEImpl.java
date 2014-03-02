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
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRME;
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRMEFields;

/**
 * Event type OUVOKCONFIRME implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventOUVOKCONFIRMEImpl extends XMLTraceImpl implements
        EventOUVOKCONFIRME {

    private static final long serialVersionUID = 2623241749052227743L;

    private String fieldTypAg;

    public EventOUVOKCONFIRMEImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventOUVOKCONFIRMEFields.TypAg).addText(
                    getFieldTypAg());
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

}
