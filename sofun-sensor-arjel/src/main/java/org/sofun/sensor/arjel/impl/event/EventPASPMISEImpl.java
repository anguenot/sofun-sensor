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

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.sofun.sensor.arjel.api.event.EventPASPMISE;
import org.sofun.sensor.arjel.api.event.EventPASPMISEFields;
import org.sofun.sensor.arjel.api.event.EventPASPMiseLig;

/**
 * Event type PASPMISE implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventPASPMISEImpl extends XMLTraceImpl implements EventPASPMISE {

    private static final long serialVersionUID = -1094932774605046258L;

    private static final String DescPari = "DescPari";

    private static final String Combi = "Combi";

    private static final String Lig = "Lig";

    private String fieldTech;

    private String fieldClair;

    private String fieldSoldeAvantMise;

    private String fieldMise;

    private String fieldSoldeApresMise;

    private String fieldMiseAbond;

    private String fieldBonusAvantMise;

    private String fieldBonusMouvement;

    private String fieldBonusApresMise;

    private String fieldBonusNom;

    private String fieldCombi;

    private List<EventPASPMiseLig> ligs = new ArrayList<EventPASPMiseLig>();

    public EventPASPMISEImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTech, String fieldClair,
            String fieldSoldeAvantMise, String fieldMise,
            String fieldSoldeApresMise, String fieldMiseAbond,
            String fieldBonusAvantMise, String fieldBonusMouvement,
            String fieldBonusApresMise, String fieldBonusNom,
            String fieldCombi, List<EventPASPMiseLig> ligs) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTech = fieldTech;
        this.fieldClair = fieldClair;
        this.fieldSoldeAvantMise = fieldSoldeAvantMise;
        this.fieldMise = fieldMise;
        this.fieldSoldeApresMise = fieldSoldeApresMise;
        this.fieldMiseAbond = fieldMiseAbond;
        this.fieldBonusAvantMise = fieldBonusAvantMise;
        this.fieldBonusMouvement = fieldBonusMouvement;
        this.fieldBonusApresMise = fieldBonusApresMise;
        this.fieldBonusNom = fieldBonusNom;
        this.fieldCombi = fieldCombi;
        this.ligs = ligs;
    }

    @Override
    protected Document getDocument() {

        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();

            root.addElement(EventPASPMISEFields.Tech).addText(getFieldTech());
            root.addElement(EventPASPMISEFields.Clair).addText(getFieldClair());

            Element descPari = root.addElement(DescPari);
            descPari.addElement(Combi).addText(getFieldCombi());

            for (EventPASPMiseLig lig : getEventPASPMiseLigs()) {

                Element l = descPari.addElement(Lig);
                l.addElement(EventPASPMiseLig.No).addText(lig.getFieldNo());
                l.addElement(EventPASPMiseLig.DateHeure).addText(
                        lig.getFieldDateHeure());
                l.addElement(EventPASPMiseLig.Tech).addText(lig.getFieldTech());
                l.addElement(EventPASPMiseLig.Clair).addText(
                        lig.getFieldClair());
                l.addElement(EventPASPMiseLig.Type).addText(lig.getFieldType());
                Element p = l.addElement(EventPASPMiseLig.Pari);
                p.addElement(EventPASPMiseLig.PariChoix).addText(
                        lig.getFieldPariChoix());
                p.addElement(EventPASPMiseLig.PariCote).addText(
                        lig.getFieldPariCote());

            }

            if (getFieldSoldeAvantMise() != null
                    && !getFieldSoldeAvantMise().isEmpty()) {
                root.addElement(EventPASPMISEFields.SoldeAvantMise).addText(
                        getFieldSoldeAvantMise());
            }
            if (getFieldMise() != null && !getFieldMise().isEmpty()) {
                root.addElement(EventPASPMISEFields.Mise).addText(
                        getFieldMise());
            }
            if (getFieldSoldeApresMise() != null
                    && !getFieldSoldeApresMise().isEmpty()) {
                root.addElement(EventPASPMISEFields.SoldeApresMise).addText(
                        getFieldSoldeApresMise());
            }
            if (getFieldMiseAbond() != null && !getFieldMiseAbond().isEmpty()) {
                root.addElement(EventPASPMISEFields.MiseAbond).addText(
                        getFieldMiseAbond());
            }
            if (getFieldBonusAvantMise() != null
                    && !getFieldBonusAvantMise().isEmpty()) {
                root.addElement(EventPASPMISEFields.BonusAvantMise).addText(
                        getFieldBonusAvantMise());
            }
            if (getFieldBonusMouvement() != null
                    && !getFieldBonusMouvement().isEmpty()) {
                root.addElement(EventPASPMISEFields.BonusMouvement).addText(
                        getFieldBonusMouvement());
            }
            if (getFieldBonusApresMise() != null
                    && !getFieldBonusApresMise().isEmpty()) {
                root.addElement(EventPASPMISEFields.BonusApresMise).addText(
                        getFieldBonusApresMise());
            }
            if (getFieldBonusNom() != null && !getFieldBonusNom().isEmpty()) {
                root.addElement(EventPASPMISEFields.BonusNom).addText(
                        getFieldBonusNom());
            }

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
    public String getFieldSoldeAvantMise() {
        return fieldSoldeAvantMise;
    }

    @Override
    public void setFieldSoldeAvantMise(String fieldSoldeAvantMise) {
        this.fieldSoldeAvantMise = fieldSoldeAvantMise;
    }

    @Override
    public String getFieldMise() {
        return fieldMise;
    }

    @Override
    public void setFieldMise(String fieldMise) {
        this.fieldMise = fieldMise;
    }

    @Override
    public String getFieldSoldeApresMise() {
        return fieldSoldeApresMise;
    }

    @Override
    public void setFieldSoldeApresMise(String fieldSoldeApresMise) {
        this.fieldSoldeApresMise = fieldSoldeApresMise;
    }

    @Override
    public String getFieldMiseAbond() {
        return fieldMiseAbond;
    }

    @Override
    public void setFieldMiseAbond(String fieldMiseAbond) {
        this.fieldMiseAbond = fieldMiseAbond;
    }

    @Override
    public String getFieldBonusAvantMise() {
        return fieldBonusAvantMise;
    }

    @Override
    public void setFieldBonusAvantMise(String fieldBonusAvantMise) {
        this.fieldBonusAvantMise = fieldBonusAvantMise;
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
    public String getFieldBonusApresMise() {
        return fieldBonusApresMise;
    }

    @Override
    public void setFieldBonusApresMise(String fieldBonusApresMise) {
        this.fieldBonusApresMise = fieldBonusApresMise;
    }

    @Override
    public String getFieldBonusNom() {
        return fieldBonusNom;
    }

    @Override
    public void setFieldBonusNom(String fieldBonusNom) {
        this.fieldBonusNom = fieldBonusNom;
    }

    @Override
    public String getFieldCombi() {
        return fieldCombi;
    }

    @Override
    public void setFieldCombi(String fieldCombi) {
        this.fieldCombi = fieldCombi;
    }

    @Override
    public List<EventPASPMiseLig> getEventPASPMiseLigs() {
        return ligs;
    }

    @Override
    public void setEventPASPMiseLigs(List<EventPASPMiseLig> ligs) {
        this.ligs = ligs;
    }

    @Override
    public void addEventPASPMiseLig(EventPASPMiseLig lig) {
        if (lig != null) {
            getEventPASPMiseLigs().add(lig);
        }
    }

}
