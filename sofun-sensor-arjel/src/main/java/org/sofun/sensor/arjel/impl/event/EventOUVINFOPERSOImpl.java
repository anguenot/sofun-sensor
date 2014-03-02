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
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSOFields;

/**
 * Event type OUVINFOPERSO implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventOUVINFOPERSOImpl extends XMLTraceImpl implements
        EventOUVINFOPERSO {

    private static final long serialVersionUID = 1L;

    private String fieldTypAg;

    private String fieldLogin;

    private String fieldPseudo;

    private String fieldNom;

    private String fieldPrenoms;

    private String fieldCivilite;

    private String fieldDateN;

    private String fieldVilleN;

    private String fieldDptN;

    private String fieldPaysN;

    private String fieldAd;

    private String fieldCP;

    private String fieldVille;

    private String fieldPays;

    private String fieldTelFixe;

    private String fieldTelMob;

    private String fieldEmail;

    public EventOUVINFOPERSOImpl(String operatorID, String safeID,
            String eventId, String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldLogin,
            String fieldPseudo, String fieldNom, String fieldPrenoms,
            String fieldCivilite, String fieldDateN, String fieldVilleN,
            String fieldDptN, String fieldPaysN, String fieldAd,
            String fieldCP, String fieldVille, String fieldPays,
            String fieldTelFixe, String fieldTelMob, String fieldEmail) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldLogin = fieldLogin;
        this.fieldPseudo = fieldPseudo;
        this.fieldNom = fieldNom;
        this.fieldPrenoms = fieldPrenoms;
        this.fieldCivilite = fieldCivilite;
        this.fieldDateN = fieldDateN;
        this.fieldVilleN = fieldVilleN;
        this.fieldDptN = fieldDptN;
        this.fieldPaysN = fieldPaysN;
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
            root.addElement(EventOUVINFOPERSOFields.TypAg).addText(
                    getFieldTypAg());
            root.addElement(EventOUVINFOPERSOFields.Login).addText(
                    getFieldLogin());
            root.addElement(EventOUVINFOPERSOFields.Pseudo).addText(
                    getFieldPseudo());
            root.addElement(EventOUVINFOPERSOFields.Nom).addText(getFieldNom());
            root.addElement(EventOUVINFOPERSOFields.Prenoms).addText(
                    getFieldPrenoms());
            root.addElement(EventOUVINFOPERSOFields.Civilite).addText(
                    getFieldCivilite());
            root.addElement(EventOUVINFOPERSOFields.DateN).addText(
                    getFieldDateN());
            root.addElement(EventOUVINFOPERSOFields.VilleN).addText(
                    getFieldVilleN());
            root.addElement(EventOUVINFOPERSOFields.DptN).addText(
                    getFieldDptN());
            root.addElement(EventOUVINFOPERSOFields.PaysN).addText(
                    getFieldPaysN());
            root.addElement(EventOUVINFOPERSOFields.Ad).addText(getFieldAd());
            root.addElement(EventOUVINFOPERSOFields.CP).addText(getFieldCP());
            root.addElement(EventOUVINFOPERSOFields.Ville).addText(
                    getFieldVille());
            root.addElement(EventOUVINFOPERSOFields.Pays).addText(
                    getFieldPays());
            if (getFieldTelFixe() != null && !getFieldTelFixe().equals("")) {
                root.addElement(EventOUVINFOPERSOFields.TelFixe).addText(
                        getFieldTelFixe());
            }
            if (getFieldTelMob() != null && !getFieldTelMob().equals("")) {
                root.addElement(EventOUVINFOPERSOFields.TelMob).addText(
                        getFieldTelMob());
            }
            root.addElement(EventOUVINFOPERSOFields.Email).addText(
                    getFieldEmail());
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
    public String getFieldLogin() {
        return fieldLogin;
    }

    @Override
    public void setFieldLogin(String fieldLogin) {
        this.fieldLogin = fieldLogin;
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
    public String getFieldDateN() {
        return fieldDateN;
    }

    @Override
    public void setFieldDateN(String fieldDateN) {
        this.fieldDateN = fieldDateN;
    }

    @Override
    public String getFieldVilleN() {
        return fieldVilleN;
    }

    @Override
    public void setFieldVilleN(String fieldVilleN) {
        this.fieldVilleN = fieldVilleN;
    }

    @Override
    public String getFieldDptN() {
        return fieldDptN;
    }

    @Override
    public void setFieldDptN(String fieldDptN) {
        this.fieldDptN = fieldDptN;
    }

    @Override
    public String getFieldPaysN() {
        return fieldPaysN;
    }

    @Override
    public void setFieldPaysN(String fieldPaysN) {
        this.fieldPaysN = fieldPaysN;
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
