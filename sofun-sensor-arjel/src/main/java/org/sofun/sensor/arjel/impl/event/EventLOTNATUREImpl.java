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
import org.sofun.sensor.arjel.api.event.EventLOTNATURE;
import org.sofun.sensor.arjel.api.event.EventLOTNATUREFields;

/**
 * Event LOTNATURE implementation.
 * 
 * @author @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventLOTNATUREImpl extends XMLTraceImpl implements EventLOTNATURE {

    private static final long serialVersionUID = -3474051631030473683L;

    private String fieldTypAg;

    private String fieldLotNNom;

    private String fieldLotNValeur;

    public EventLOTNATUREImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldLotNNom,
            String fieldLotNValeur) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldLotNNom = fieldLotNNom;
        this.fieldLotNValeur = fieldLotNValeur;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventLOTNATUREFields.TypAg)
                    .addText(getFieldTypAg());
            Element l = root.addElement(EventLOTNATUREFields.LotN);
            l.addElement(EventLOTNATUREFields.LotNNom).addText(
                    getFieldLotNNom());
            l.addElement(EventLOTNATUREFields.LotNValeur).addText(
                    getFieldLotNValeur());

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
    public String getFieldLotNNom() {
        return fieldLotNNom;
    }

    @Override
    public void setFieldLotNNom(String fieldLotNNom) {
        this.fieldLotNNom = fieldLotNNom;
    }

    @Override
    public String getFieldLotNValeur() {
        return fieldLotNValeur;
    }

    @Override
    public void setFieldLotNValeur(String fieldLotNValeur) {
        this.fieldLotNValeur = fieldLotNValeur;
    }

}
