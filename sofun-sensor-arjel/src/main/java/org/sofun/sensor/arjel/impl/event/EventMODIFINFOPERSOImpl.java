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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSOFields;

/**
 * Event type OUVINFOPERSO implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventMODIFINFOPERSOImpl extends XMLTraceImpl implements
        EventMODIFINFOPERSO {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory
            .getLog(EventMODIFINFOPERSOImpl.class);

    private String fieldTypAg;

    private String fieldPseudo;

    private String fieldNom;

    private String fieldPrenoms;

    private String fieldCivilite;

    private String fieldAd;

    private String fieldCP;

    private String fieldVille;

    private String fieldPays;

    private String fieldTelFixe;

    private String fieldTelMob;

    private String fieldEmail;

    public EventMODIFINFOPERSOImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldPseudo,
            String fieldNom, String fieldPrenoms, String fieldCivilite,
            String fieldAd, String fieldCP, String fieldVille,
            String fieldPays, String fieldTelFixe, String fieldTelMob,
            String fieldEmail) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldPseudo = fieldPseudo;
        this.fieldNom = fieldNom;
        this.fieldPrenoms = fieldPrenoms;
        this.fieldCivilite = fieldCivilite;
        this.fieldAd = fieldAd;
        this.fieldCP = fieldCP;
        this.fieldVille = fieldVille;
        this.fieldPays = fieldPays;
        this.fieldTelFixe = fieldTelFixe;
        this.fieldTelMob = fieldTelMob;
        this.fieldEmail = fieldEmail;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            // If values below are null we log the issue. The validation will
            // fail at proxy level.
            if (getFieldTypAg() == null) {
                log.error(EventMODIFINFOPERSOFields.TypAg + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.TypAg).addText(
                        getFieldTypAg());
            }
            if (getFieldPseudo() == null) {
                log.error(EventMODIFINFOPERSOFields.Pseudo + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.Pseudo).addText(
                        getFieldPseudo());
            }
            if (getFieldNom() == null) {
                log.error(EventMODIFINFOPERSOFields.Nom + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.Nom).addText(
                        getFieldNom());
            }
            if (getFieldPrenoms() == null) {
                log.error(EventMODIFINFOPERSOFields.Prenoms + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.Prenoms).addText(
                        getFieldPrenoms());
            }
            if (getFieldCivilite() == null) {
                log.error(EventMODIFINFOPERSOFields.Civilite + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.Civilite).addText(
                        getFieldCivilite());
            }
            // Not mandatory
            root.addElement(EventMODIFINFOPERSOFields.Ad).addText(getFieldAd());
            if (getFieldCP() == null) {
                log.error(EventMODIFINFOPERSOFields.CP + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.CP).addText(
                        getFieldCP());
            }
            if (getFieldVille() == null) {
                log.error(EventMODIFINFOPERSOFields.Ville + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.Ville).addText(
                        getFieldVille());
            }
            if (getFieldPays() == null) {
                log.error(EventMODIFINFOPERSOFields.Pays + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.Pays).addText(
                        getFieldPays());
            }
            // not mandatory
            if (getFieldTelFixe() != null && !getFieldTelFixe().equals("")) {
                root.addElement(EventMODIFINFOPERSOFields.TelFixe).addText(
                        getFieldTelFixe());
            }
            // not mandatory
            if (getFieldTelMob() != null && !getFieldTelMob().equals("")) {
                root.addElement(EventMODIFINFOPERSOFields.TelMob).addText(
                        getFieldTelMob());
            }
            if (getFieldEmail() == null) {
                log.error(EventMODIFINFOPERSOFields.Email + " is null");
            } else {
                root.addElement(EventMODIFINFOPERSOFields.Email).addText(
                        getFieldEmail());
            }
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
    public String getFieldPseudo() {
        return fieldPseudo;
    }

    @Override
    public void setFieldPseudo(String fieldPseudo) {
        this.fieldPseudo = fieldPseudo;
    }

    @Override
    public String getFieldNom() {
        return fieldNom;
    }

    @Override
    public void setFieldNom(String fieldNom) {
        this.fieldNom = fieldNom;
    }

    @Override
    public String getFieldPrenoms() {
        return fieldPrenoms;
    }

    @Override
    public void setFieldPrenoms(String fieldPrenoms) {
        this.fieldPrenoms = fieldPrenoms;
    }

    @Override
    public String getFieldCivilite() {
        return fieldCivilite;
    }

    @Override
    public void setFieldCivilite(String fieldCivilite) {
        this.fieldCivilite = fieldCivilite;
    }

    @Override
    public String getFieldAd() {
        return fieldAd;
    }

    @Override
    public void setFieldAd(String fieldAd) {
        this.fieldAd = fieldAd;
    }

    @Override
    public String getFieldCP() {
        return fieldCP;
    }

    @Override
    public void setFieldCP(String fieldCP) {
        this.fieldCP = fieldCP;
    }

    @Override
    public String getFieldVille() {
        return fieldVille;
    }

    @Override
    public void setFieldVille(String fieldVille) {
        this.fieldVille = fieldVille;
    }

    @Override
    public String getFieldPays() {
        return fieldPays;
    }

    @Override
    public void setFieldPays(String fieldPays) {
        this.fieldPays = fieldPays;
    }

    @Override
    public String getFieldTelFixe() {
        return fieldTelFixe;
    }

    @Override
    public void setFieldTelFixe(String fieldTelFixe) {
        this.fieldTelFixe = fieldTelFixe;
    }

    @Override
    public String getFieldTelMob() {
        return fieldTelMob;
    }

    @Override
    public void setFieldTelMob(String fieldTelMob) {
        this.fieldTelMob = fieldTelMob;
    }

    @Override
    public String getFieldEmail() {
        return fieldEmail;
    }

    @Override
    public void setFieldEmail(String fieldEmail) {
        this.fieldEmail = fieldEmail;
    }

}
