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
import org.sofun.sensor.arjel.api.event.EventCPTEALIM;
import org.sofun.sensor.arjel.api.event.EventCPTEALIMFields;

/**
 * Event type CPTEALIM implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventCPTEALIMImpl extends XMLTraceImpl implements EventCPTEALIM {

    private static final long serialVersionUID = -7861871416699114416L;

    private String fieldTypAg;

    private String fieldDateDemande;

    private String fieldDateEffective;

    private String fieldSoldeAvant;

    private String fieldSoldeMouvement;

    private String fieldSoldeApres;

    private String fieldMoyenPaiement;

    private String fieldTypeMoyenPaiement;

    public EventCPTEALIMImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldDateDemande,
            String fieldDateEffective, String fieldSoldeAvant,
            String fieldSoldeMouvement, String fieldSoldeApres,
            String fieldMoyenPaiement, String fieldTypeMoyenPaiement) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldDateDemande = fieldDateDemande;
        this.fieldDateEffective = fieldDateEffective;
        this.fieldSoldeAvant = fieldSoldeAvant;
        this.fieldSoldeMouvement = fieldSoldeMouvement;
        this.fieldSoldeApres = fieldSoldeApres;
        this.fieldMoyenPaiement = fieldMoyenPaiement;
        this.fieldTypeMoyenPaiement = fieldTypeMoyenPaiement;
    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();
            root.addElement(EventCPTEALIMFields.TypAg).addText(getFieldTypAg());
            if (getFieldDateDemande() != null) {
                root.addElement(EventCPTEALIMFields.DateDemande).addText(
                        getFieldDateDemande());
            }
            if (getFieldDateEffective() != null) {
                root.addElement(EventCPTEALIMFields.DateEffective).addText(
                        getFieldDateEffective());
            }
            root.addElement(EventCPTEALIMFields.SoldeAvant).addText(
                    getFieldSoldeAvant());
            root.addElement(EventCPTEALIMFields.SoldeMouvement).addText(
                    getFieldSoldeMouvement());
            root.addElement(EventCPTEALIMFields.SoldeApres).addText(
                    getFieldSoldeApres());
            root.addElement(EventCPTEALIMFields.MoyenPaiement).addText(
                    getFieldMoyenPaiement());
            root.addElement(EventCPTEALIMFields.TypeMoyenPaiement).addText(
                    getFieldTypeMoyenPaiement());

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
    public String getFieldDateDemande() {
        return fieldDateDemande;
    }

    @Override
    public void setFieldDateDemande(String fieldDateDemande) {
        this.fieldDateDemande = fieldDateDemande;
    }

    @Override
    public String getFieldDateEffective() {
        return fieldDateEffective;
    }

    @Override
    public void setFieldDateEffective(String fieldDateEffective) {
        this.fieldDateEffective = fieldDateEffective;
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
    public String getFieldSoldeMouvement() {
        return fieldSoldeMouvement;
    }

    @Override
    public void setFieldSoldeMouvement(String fieldSoldeMouvement) {
        this.fieldSoldeMouvement = fieldSoldeMouvement;
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
    public String getFieldMoyenPaiement() {
        return fieldMoyenPaiement;
    }

    @Override
    public void setFieldMoyenPaiement(String fieldMoyenPaiement) {
        this.fieldMoyenPaiement = fieldMoyenPaiement;
    }

    @Override
    public String getFieldTypeMoyenPaiement() {
        return fieldTypeMoyenPaiement;
    }

    @Override
    public void setFieldTypeMoyenPaiement(String fieldTypeMoyenPaiement) {
        this.fieldTypeMoyenPaiement = fieldTypeMoyenPaiement;
    }

}
