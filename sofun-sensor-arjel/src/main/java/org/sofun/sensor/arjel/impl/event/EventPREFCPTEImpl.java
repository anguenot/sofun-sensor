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
import org.sofun.sensor.arjel.api.event.EventPREFCPTE;
import org.sofun.sensor.arjel.api.event.EventPREFCPTEFields;

/**
 * Event type PREFCPTE implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventPREFCPTEImpl extends XMLTraceImpl implements EventPREFCPTE {

    private static final long serialVersionUID = -4438613723536524338L;

    private static final Log log = LogFactory.getLog(EventPREFCPTEImpl.class);

    private String fieldTypAg;

    private String fieldCompteMin;

    private String fieldCompteMax;

    private String fieldMiseMaxMontantMM;

    private String fieldMiseMaxPeriodeMM;

    private String fieldMiseMaxTypeJeuMM;

    private String fieldDepotMaxMontantMM;

    private String fieldDepotMaxPeriodeMM;

    private String fieldDepotMaxTypeJeuMM;

    private String fieldModerateurLibelModer;

    private String fieldModerateurSeuilModer;

    private String fieldModerateurPeriodeModer;

    private String fieldModerateurTypeJeuModer;

    public EventPREFCPTEImpl(String operatorID, String safeID, String eventId,
            String eventDate, String playerID, String playerIP,
            String sessionID, String fieldTypAg, String fieldCompteMin,
            String fieldCompteMax, String fieldMiseMaxMontantMM,
            String fieldMiseMaxPeriodeMM, String fieldMiseMaxTypeJeuMM,
            String fieldDepotMaxMontantMM, String fieldDepotMaxPeriodeMM,
            String fieldDepotMaxTypeJeuMM, String fieldModerateurLibelModer,
            String fieldModerateurSeuilModer,
            String fieldModerateurPeriodeModer,
            String fieldModerateurTypeJeuModer) {
        super(operatorID, safeID, TYPE, eventId, eventDate, playerID, playerIP,
                sessionID);
        this.fieldTypAg = fieldTypAg;
        this.fieldCompteMin = fieldCompteMin;
        this.fieldCompteMax = fieldCompteMax;
        this.fieldMiseMaxMontantMM = fieldMiseMaxMontantMM;
        this.fieldMiseMaxPeriodeMM = fieldMiseMaxPeriodeMM;
        this.fieldMiseMaxTypeJeuMM = fieldMiseMaxTypeJeuMM;
        this.fieldDepotMaxMontantMM = fieldDepotMaxMontantMM;
        this.fieldDepotMaxPeriodeMM = fieldDepotMaxPeriodeMM;
        this.fieldDepotMaxTypeJeuMM = fieldDepotMaxTypeJeuMM;
        this.fieldModerateurLibelModer = fieldModerateurLibelModer;
        this.fieldModerateurSeuilModer = fieldModerateurSeuilModer;
        this.fieldModerateurPeriodeModer = fieldModerateurPeriodeModer;
        this.fieldModerateurTypeJeuModer = fieldModerateurTypeJeuModer;

    }

    @Override
    protected Document getDocument() {
        if (document == null) {
            document = super.getDocument();
            Element root = document.getRootElement();

            // If values below are null we log the issue. The validation will
            // fail at proxy level.
            if (getFieldTypAg() == null) {
                log.error(EventPREFCPTEFields.TypAg + " is null");
            } else {
                root.addElement(EventPREFCPTEFields.TypAg).addText(
                        getFieldTypAg());
            }
            // Not mandatory
            if (getFieldCompteMin() != null || getFieldCompteMax() != null) {
                Element c = root.addElement(EventPREFCPTEFields.Compte);
                if (getFieldCompteMin() != null) {
                    c.add(df.createElement(EventPREFCPTEFields.Min).addText(
                            getFieldCompteMin()));
                }
                if (getFieldCompteMax() != null) {
                    c.add(df.createElement(EventPREFCPTEFields.Max).addText(
                            getFieldCompteMax()));
                }
            }
            Element mm = root.addElement(EventPREFCPTEFields.MiseMax);
            if (getFieldMiseMaxMontantMM() == null) {
                log.error(EventPREFCPTEFields.MontantMM + " is null");
            } else {
                mm.add(df.createElement(EventPREFCPTEFields.MontantMM).addText(
                        getFieldMiseMaxMontantMM()));
            }
            if (getFieldMiseMaxPeriodeMM() == null) {
                log.error(EventPREFCPTEFields.PeriodeMM + " is null");
            } else {
                mm.add(df.createElement(EventPREFCPTEFields.PeriodeMM).addText(
                        getFieldMiseMaxPeriodeMM()));
            }
            // Not mandatory
            if (getFieldMiseMaxTypeJeuMM() != null) {
                mm.add(df.createElement(EventPREFCPTEFields.TypeJeuMM).addText(
                        getFieldMiseMaxTypeJeuMM()));
            }
            Element dm = root.addElement(EventPREFCPTEFields.DepotMax);
            if (getFieldDepotMaxMontantMM() == null) {
                log.error(EventPREFCPTEFields.MontantMM + " is null");
            } else {
                dm.add(df.createElement(EventPREFCPTEFields.MontantMM).addText(
                        getFieldDepotMaxMontantMM()));
            }
            if (getFieldDepotMaxPeriodeMM() == null) {
                log.error(EventPREFCPTEFields.PeriodeMM + " is null");
            } else {
                dm.add(df.createElement(EventPREFCPTEFields.PeriodeMM).addText(
                        getFieldDepotMaxPeriodeMM()));
            }
            if (getFieldDepotMaxTypeJeuMM() != null) {
                dm.add(df.createElement(EventPREFCPTEFields.TypeJeuMM).addText(
                        getFieldDepotMaxTypeJeuMM()));
            }

            if (getFieldModerateurLibelModer() != null
                    && getFieldModerateurSeuilModer() != null
                    && getFieldModerateurPeriodeModer() != null
                    && getFieldModerateurTypeJeuModer() != null) {
                Element m = root.addElement(EventPREFCPTEFields.Moderateur);
                m.add(df.createElement(EventPREFCPTEFields.LibelModer).addText(
                        getFieldModerateurLibelModer()));
                m.add(df.createElement(EventPREFCPTEFields.SeuilModer).addText(
                        getFieldModerateurSeuilModer()));
                m.add(df.createElement(EventPREFCPTEFields.PeriodeModer)
                        .addText(getFieldModerateurPeriodeModer()));
                m.add(df.createElement(EventPREFCPTEFields.TypeJeuModer)
                        .addText(getFieldModerateurTypeJeuModer()));
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
    public String getFieldCompteMin() {
        if ("".equals(fieldCompteMin)) {
            fieldCompteMin = null;
        }
        return fieldCompteMin;
    }

    @Override
    public void setFieldCompteMin(String fieldCompteMin) {
        this.fieldCompteMin = fieldCompteMin;
    }

    @Override
    public String getFieldCompteMax() {
        if ("".equals(fieldCompteMax)) {
            fieldCompteMax = null;
        }
        return fieldCompteMax;
    }

    @Override
    public void setFieldCompteMax(String fieldCompteMax) {
        this.fieldCompteMax = fieldCompteMax;
    }

    @Override
    public String getFieldMiseMaxMontantMM() {
        return fieldMiseMaxMontantMM;
    }

    @Override
    public void setFieldMiseMaxMontantMM(String fieldMiseMaxMontantMM) {
        this.fieldMiseMaxMontantMM = fieldMiseMaxMontantMM;
    }

    @Override
    public String getFieldMiseMaxPeriodeMM() {
        return fieldMiseMaxPeriodeMM;
    }

    @Override
    public void setFieldMiseMaxPeriodeMM(String fieldMiseMaxPeriodeMM) {
        this.fieldMiseMaxPeriodeMM = fieldMiseMaxPeriodeMM;
    }

    @Override
    public String getFieldMiseMaxTypeJeuMM() {
        if ("".equals(fieldMiseMaxTypeJeuMM)) {
            fieldMiseMaxTypeJeuMM = null;
        }
        return fieldMiseMaxTypeJeuMM;
    }

    @Override
    public void setFieldMiseMaxTypeJeuMM(String fieldMiseMaxTypeJeuMM) {
        this.fieldMiseMaxTypeJeuMM = fieldMiseMaxTypeJeuMM;
    }

    @Override
    public String getFieldDepotMaxMontantMM() {
        return fieldDepotMaxMontantMM;
    }

    @Override
    public void setFieldDepotMaxMontantMM(String fieldDepotMaxMontantMM) {
        this.fieldDepotMaxMontantMM = fieldDepotMaxMontantMM;
    }

    @Override
    public String getFieldDepotMaxPeriodeMM() {
        return fieldDepotMaxPeriodeMM;
    }

    @Override
    public void setFieldDepotMaxPeriodeMM(String fieldDepotMaxPeriodeMM) {
        this.fieldDepotMaxPeriodeMM = fieldDepotMaxPeriodeMM;
    }

    @Override
    public String getFieldDepotMaxTypeJeuMM() {
        if ("".equals(fieldDepotMaxTypeJeuMM)) {
            fieldDepotMaxTypeJeuMM = null;
        }
        return fieldDepotMaxTypeJeuMM;
    }

    @Override
    public void setFieldDepotMaxTypeJeuMM(String fieldDepotMaxTypeJeuMM) {
        this.fieldDepotMaxTypeJeuMM = fieldDepotMaxTypeJeuMM;
    }

    public String getFieldModerateurLibelModer() {
        if ("".equals(fieldModerateurLibelModer)) {
            fieldModerateurLibelModer = null;
        }
        return fieldModerateurLibelModer;
    }

    public void setFieldModerateurLibelModer(String fieldModerateurLibelModer) {
        this.fieldModerateurLibelModer = fieldModerateurLibelModer;
    }

    public String getFieldModerateurSeuilModer() {
        if ("".equals(fieldModerateurSeuilModer)) {
            fieldModerateurSeuilModer = null;
        }
        return fieldModerateurSeuilModer;
    }

    public void setFieldModerateurSeuilModer(String fieldModerateurSeuilModer) {
        this.fieldModerateurSeuilModer = fieldModerateurSeuilModer;
    }

    public String getFieldModerateurPeriodeModer() {
        if ("".equals(fieldModerateurPeriodeModer)) {
            fieldModerateurPeriodeModer = null;
        }
        return fieldModerateurPeriodeModer;
    }

    public void setFieldModerateurPeriodeModer(
            String fieldModerateurPeriodeModer) {
        this.fieldModerateurPeriodeModer = fieldModerateurPeriodeModer;
    }

    public String getFieldModerateurTypeJeuModer() {
        if ("".equals(fieldModerateurTypeJeuModer)) {
            fieldModerateurTypeJeuModer = null;
        }
        return fieldModerateurTypeJeuModer;
    }

    public void setFieldModerateurTypeJeuModer(
            String fieldModerateurTypeJeuModer) {
        this.fieldModerateurTypeJeuModer = fieldModerateurTypeJeuModer;
    }

}
