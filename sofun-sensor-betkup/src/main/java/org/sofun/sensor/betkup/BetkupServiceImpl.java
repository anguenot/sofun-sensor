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

package org.sofun.sensor.betkup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.arjel.api.event.EventACCESREFUSE;
import org.sofun.sensor.arjel.api.event.EventACCESREFUSEFields;
import org.sofun.sensor.arjel.api.event.EventAUTOINTERDICTION;
import org.sofun.sensor.arjel.api.event.EventAUTOINTERDICTIONFields;
import org.sofun.sensor.arjel.api.event.EventCLOTUREDEM;
import org.sofun.sensor.arjel.api.event.EventCLOTUREDEMFields;
import org.sofun.sensor.arjel.api.event.EventCPTEABOND;
import org.sofun.sensor.arjel.api.event.EventCPTEABONDFields;
import org.sofun.sensor.arjel.api.event.EventCPTEALIM;
import org.sofun.sensor.arjel.api.event.EventCPTEALIMFields;
import org.sofun.sensor.arjel.api.event.EventCPTEALIMOPE;
import org.sofun.sensor.arjel.api.event.EventCPTEALIMOPEFields;
import org.sofun.sensor.arjel.api.event.EventCPTERETRAIT;
import org.sofun.sensor.arjel.api.event.EventCPTERETRAITFields;
import org.sofun.sensor.arjel.api.event.EventLOTNATURE;
import org.sofun.sensor.arjel.api.event.EventLOTNATUREFields;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSOFields;
import org.sofun.sensor.arjel.api.event.EventOKCONDGENE;
import org.sofun.sensor.arjel.api.event.EventOKCONDGENEFields;
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSOFields;
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRME;
import org.sofun.sensor.arjel.api.event.EventPASPANNUL;
import org.sofun.sensor.arjel.api.event.EventPASPANNULFields;
import org.sofun.sensor.arjel.api.event.EventPASPGAIN;
import org.sofun.sensor.arjel.api.event.EventPASPGAINFields;
import org.sofun.sensor.arjel.api.event.EventPASPMISE;
import org.sofun.sensor.arjel.api.event.EventPASPMISEFields;
import org.sofun.sensor.arjel.api.event.EventPREFCPTE;
import org.sofun.sensor.arjel.api.event.EventPREFCPTEFields;
import org.sofun.sensor.betkup.api.BetkupException;
import org.sofun.sensor.betkup.api.BetkupService;
import org.sofun.sensor.betkup.api.ejb.BetkupServiceLocal;
import org.sofun.sensor.betkup.api.ejb.BetkupServiceRemote;

import com.oreilly.servlet.Base64Decoder;

/**
 * Betkup Service Implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Stateless
@Local(BetkupServiceLocal.class)
@Remote(BetkupServiceRemote.class)
public class BetkupServiceImpl implements BetkupService {

    private static final long serialVersionUID = -726977571465762691L;

    private static final Log log = LogFactory.getLog(BetkupServiceImpl.class);

    public BetkupServiceImpl() {
    }

    private void traceRequest(HttpServletRequest request) {
        if (request != null) {
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                final String paramName = paramNames.nextElement();
                log.trace(paramName);
                String[] paramValues = request.getParameterValues(paramName);
                for (int i = 0; i < paramValues.length; i++) {
                    log.trace(paramValues[i]);
                }
            }
        }
    }

    @Override
    public Map<String, String> getParametersForEventType(String eventType,
            HttpServletRequest request) throws BetkupException {

        if (log.isTraceEnabled()) {
            traceRequest(request);
        }

        Map<String, String> map = new HashMap<String, String>();

        if (EventOUVINFOPERSO.TYPE.equals(eventType)) {

            String value = request.getParameter("login[accountEmail]");
            map.put(EventOUVINFOPERSOFields.Login, value);

            value = request.getParameter("information[accountPseudo]");
            map.put(EventOUVINFOPERSOFields.Pseudo, value);

            value = request.getParameter("information[accountLastname]");
            map.put(EventOUVINFOPERSOFields.Nom, value);

            value = request.getParameter("information[accountFirstname]");
            map.put(EventOUVINFOPERSOFields.Prenoms, value);

            value = request.getParameter("information[accountCivilite]");
            map.put(EventOUVINFOPERSOFields.Civilite, value);

            final String day = request
                    .getParameter("personal[accountBirthdate_1]");
            final String month = request
                    .getParameter("personal[accountBirthdate_2]");
            final String year = request
                    .getParameter("personal[accountBirthdate_3]");
            final String birthDateStr = year + month + day;
            map.put(EventOUVINFOPERSOFields.DateN, birthDateStr);

            value = request.getParameter("personal[accountBirthplace]");
            map.put(EventOUVINFOPERSOFields.VilleN, value);

            value = request.getParameter("personal[accountBirthregion]");
            map.put(EventOUVINFOPERSOFields.DptN, value);

            value = request.getParameter("personal[accountBirthcountry]");
            map.put(EventOUVINFOPERSOFields.PaysN, value);

            value = request.getParameter("information[accountAdresse]");
            map.put(EventOUVINFOPERSOFields.Ad, value);

            value = request.getParameter("information[accountCodezip]");
            map.put(EventOUVINFOPERSOFields.CP, value);

            value = request.getParameter("information[accountCity]");
            map.put(EventOUVINFOPERSOFields.Ville, value);

            value = request.getParameter("information[accountCountry]");
            map.put(EventOUVINFOPERSOFields.Pays, value);

            value = request.getParameter("login[accountEmail]");
            map.put(EventOUVINFOPERSOFields.Email, value);

            // Betkup does not request these fields
            map.put(EventOUVINFOPERSOFields.TelFixe, "");
            map.put(EventOUVINFOPERSOFields.TelMob, "");

        } else if (EventOKCONDGENE.TYPE.equals(eventType)) {

            if (request.getParameter("policyAcceptance") != null) {
                final String value = request
                        .getParameter("login[accountEmail]");
                map.put(EventOKCONDGENEFields.Login, value);
            }

        } else if (EventPREFCPTE.TYPE.equals(eventType)) {

            String value = request.getParameter("login[accountEmail]");

            // Account creation time.
            if (value != null) {

                map.put(EventPREFCPTEFields.Login, value);

                // Betkup proposes weekly limits.
                map.put(EventPREFCPTEFields.PeriodeMM, "S");

                // Selected values or user custom values (betkup form sucks...)
                value = request.getParameter("gold[accountMiseLimit]");
                if ("1".equals(value)) {
                    value = request
                            .getParameter("gold[accountLimiteMisePerso]");
                }
                map.put(EventPREFCPTEFields.MiseMax, value);

                // Selected values or user custom values (betkup form sucks...)
                value = request.getParameter("gold[accountDepotLimit]");
                if ("1".equals(value)) {
                    value = request
                            .getParameter("gold[accountLimiteDepotPerso]");
                }
                map.put(EventPREFCPTEFields.DepotMax, value);

            } else {

                if (request.getRequestURL().toString()
                        .contains("/account/status")) {

                    value = request.getParameter("email");
                    // /account/status case
                    if (value != null) {

                        map.put(EventPREFCPTEFields.Login, value);

                        // Betkup proposes weekly limits.
                        map.put(EventPREFCPTEFields.PeriodeMM, "S");

                        // Selected values or user custom values (betkup form
                        // sucks...)
                        value = request
                                .getParameter("limites[maxAmountBetWeekly]");
                        if (value != null) {
                            map.put(EventPREFCPTEFields.MiseMax, value);
                        } else {
                            return new HashMap<String, String>();
                        }

                        // Selected values or user custom values (betkup form
                        // sucks...)
                        value = request
                                .getParameter("limites[maxAmountCreditWeekly]");
                        if (value != null) {
                            map.put(EventPREFCPTEFields.DepotMax, value);
                        } else {
                            return new HashMap<String, String>();
                        }

                        // Max amount before automatic wire
                        value = request
                                .getParameter("limites[maxAmountAutomaticWire]");
                        if (value != null && !"0".equals(value)) {
                            map.put(EventPREFCPTEFields.CompteMax, value);
                        }

                    }

                } else {

                    value = request.getParameter("email");
                    map.put(EventPREFCPTEFields.Login, value);

                    // Betkup proposes weekly limits.
                    map.put(EventPREFCPTEFields.PeriodeMM, "S");

                    // Selected values or user custom values (betkup form
                    // sucks...)
                    value = request.getParameter("maxAmountBetWeekly");
                    map.put(EventPREFCPTEFields.MiseMax, value);

                    // Selected values or user custom values (betkup form
                    // sucks...)
                    value = request.getParameter("maxAmountCreditWeekly");
                    map.put(EventPREFCPTEFields.DepotMax, value);

                }

            }

        } else if (EventACCESREFUSE.TYPE.equals(eventType)) {

            String value = request.getParameter("email");

            if (value != null) {

                map.put(EventACCESREFUSEFields.Login, value);

                value = request.getParameter("deniedReason");
                map.put(EventACCESREFUSEFields.CauseRefus, value);

                value = request.getParameter("deniedReasonType");
                map.put(EventACCESREFUSEFields.TypeRefus, value);

            }

        } else if (EventOUVOKCONFIRME.TYPE.equals(eventType)) {

            // If a value is submitted then we handle this event.
            String value = request.getParameter("limites[activationKey]");
            if (value != null && !value.isEmpty()) {
                value = request.getParameter("email");
                map.put(EventOKCONDGENEFields.Login, value);
            }

        } else if (EventMODIFINFOPERSO.TYPE.equals(eventType)) {

            String value = request.getParameter("oldEmail");
            map.put(EventMODIFINFOPERSOFields.Login, value);
            map.put(EventMODIFINFOPERSOFields.Email, value);

            value = request.getParameter("oldLastName");
            map.put(EventMODIFINFOPERSOFields.Nom, value);

            value = request.getParameter("oldFirstName");
            map.put(EventMODIFINFOPERSOFields.Prenoms, value);

            value = request.getParameter("oldNickName");
            map.put(EventMODIFINFOPERSOFields.Pseudo, value);

            value = request.getParameter("oldTitle");
            // M, Mme, Mlle
            if ("MR".equals(value)) {
                value = "M";
            } else if ("MS".equals(value)) {
                value = "Mme";
            } else if ("MRS".equals(value)) {
                value = "Mlle";
            }
            map.put(EventMODIFINFOPERSOFields.Civilite, value);

            value = request.getParameter("oldAddress");
            map.put(EventMODIFINFOPERSOFields.Ad, value);

            value = request.getParameter("oldZip");
            map.put(EventMODIFINFOPERSOFields.CP, value);

            value = request.getParameter("oldCountry");
            map.put(EventMODIFINFOPERSOFields.Pays, value);

            value = request.getParameter("oldCity");
            map.put(EventMODIFINFOPERSOFields.Ville, value);

            // Betkup edit form proposes per field update to player.
            if (request.getParameter("monComptePseudo") != null) {
                map.put(EventMODIFINFOPERSOFields.Pseudo,
                        request.getParameter("monComptePseudo"));
            }
            if (request.getParameter("monComptePrenom") != null) {
                map.put(EventMODIFINFOPERSOFields.Prenoms,
                        request.getParameter("monComptePrenom"));
            }
            if (request.getParameter("monCompteNom") != null) {
                map.put(EventMODIFINFOPERSOFields.Nom,
                        request.getParameter("monCompteNom"));
            }
            if (request.getParameter("monCompteCodepostal") != null) {
                map.put(EventMODIFINFOPERSOFields.CP,
                        request.getParameter("monCompteCodepostal"));
            }
            if (request.getParameter("monCompteAdresse") != null) {
                map.put(EventMODIFINFOPERSOFields.Ad,
                        request.getParameter("monCompteAdresse"));
            }
            if (request.getParameter("monCompteVille") != null) {
                map.put(EventMODIFINFOPERSOFields.Ville,
                        request.getParameter("monCompteVille"));
            }
            if (request.getParameter("information[accountCountry]") != null) {
                map.put(EventMODIFINFOPERSOFields.Pays,
                        request.getParameter("information[accountCountry]"));
            }
            if (request.getParameter("edit[monCompteCivility]") != null) {
                map.put(EventMODIFINFOPERSOFields.Civilite,
                        request.getParameter("edit[monCompteCivility]"));
            }

        } else if (EventAUTOINTERDICTION.TYPE.equals(eventType)) {

            String value = request.getParameter("limites[autoExclusion]");
            if (value != null && !"0".equals(value)) {
                map.put(EventAUTOINTERDICTIONFields.Duree, value);

                value = request.getParameter("email");
                map.put(EventOKCONDGENEFields.Login, value);

                final Calendar cal = Calendar.getInstance();
                final Date now = cal.getTime();
                final SimpleDateFormat formatter = new SimpleDateFormat(
                        "yyMMddHHmm");
                final StringBuilder eventDate = new StringBuilder(
                        formatter.format(now));
                map.put(EventAUTOINTERDICTIONFields.DateModif,
                        eventDate.toString());
            }

        } else if (EventCLOTUREDEM.TYPE.equals(eventType)) {

            String value = request.getParameter("email");
            map.put(EventCLOTUREDEMFields.Login, value);

            value = request.getParameter("credit");
            map.put(EventCLOTUREDEMFields.SoldeClos, value);

        } else if (EventCPTEALIM.TYPE.equals(eventType)) {

            String value = request.getParameter("email");
            map.put(EventCPTEALIMFields.Login, value);

            value = request.getParameter("creditBefore");
            map.put(EventCPTEALIMFields.SoldeAvant, value);
            final float creditBefore = Float.valueOf(value);

            // Selected values or user custom values (betkup form sucks...)
            value = request.getParameter("credit_select[amountCreditSelect]");
            if ("1".equals(value)) {
                value = request
                        .getParameter("credit_amount[amountCreditPerso]");
            }
            map.put(EventCPTEALIMFields.SoldeMouvement, value);
            final float credit = Float.valueOf(value);

            final float creditAfter = creditBefore + credit;
            map.put(EventCPTEALIMFields.SoldeApres, String.valueOf(creditAfter));

            // Only CB available Betkup side for the moment
            final String fieldTypeMoyenPaiement = "CarteBancaire";
            map.put(EventCPTEALIMFields.TypeMoyenPaiement,
                    fieldTypeMoyenPaiement);
            map.put(EventCPTEALIMFields.MoyenPaiement, fieldTypeMoyenPaiement);

        } else if (EventCPTERETRAIT.TYPE.equals(eventType)) {

            String value = request.getParameter("email");
            map.put(EventCPTERETRAITFields.Login, value);

            value = request.getParameter("creditBefore");
            map.put(EventCPTERETRAITFields.SoldeAvant, value);
            final float creditBefore = Float.valueOf(value);

            // Selected values or user custom values (betkup form sucks...)
            value = request.getParameter("wire[montantRetrait]");
            map.put(EventCPTERETRAITFields.SoldeMouvement, value);
            final float credit = Float.valueOf(value);

            final float creditAfter = creditBefore - credit;
            map.put(EventCPTERETRAITFields.SoldeApres,
                    String.valueOf(creditAfter));

        } else if (EventCPTEALIMOPE.TYPE.equals(eventType)) {

            if (request.getParameter("bonus") != null) {

                String value = request.getParameter("login[accountEmail]");
                map.put(EventCPTEALIMOPEFields.Login, value);

                value = request.getParameter("bonusBefore");
                map.put(EventCPTEALIMOPEFields.BonusAvant, value);
                final float bonusBefore = Float.valueOf(value);

                value = request.getParameter("bonusAmount");
                map.put(EventCPTEALIMOPEFields.BonusMouvement, value);
                final float bonus = Float.valueOf(value);

                final float bonusAfter = bonusBefore + bonus;
                map.put(EventCPTEALIMOPEFields.BonusApres,
                        String.valueOf(bonusAfter));

                value = request.getParameter("bonusName");
                if (value == null) {
                    value = "NA";
                }
                map.put(EventCPTEALIMOPEFields.BonusNom, value);

            }

        } else if (EventCPTEABOND.TYPE.equals(eventType)) {

            if (request.getParameter("credits") != null) {

                String value = request.getParameter("email");
                map.put(EventCPTEABONDFields.Login, value);

                value = request.getParameter("creditBefore");
                map.put(EventCPTEABONDFields.SoldeAvant, value);
                final float creditBefore = Float.valueOf(value);

                value = request.getParameter("creditAmount");
                map.put(EventCPTEABONDFields.MontAbond, value);
                final float credit = Float.valueOf(value);

                final float creditAfter = creditBefore + credit;
                map.put(EventCPTEABONDFields.SoldeApres,
                        String.valueOf(creditAfter));

                value = request.getParameter("creditInfo");
                map.put(EventCPTEABONDFields.Info, value);

                value = request.getParameter("creditType");
                map.put(EventCPTEABONDFields.TypeAbondement, value);

            }

        } else if (EventLOTNATURE.TYPE.equals(eventType)) {

            if (request.getParameter("prizes") != null) {

                String value = request.getParameter("email");
                map.put(EventLOTNATUREFields.Login, value);

                value = request.getParameter("prizeName");
                map.put(EventLOTNATUREFields.LotNNom, value);

                value = request.getParameter("prizeValue");
                map.put(EventLOTNATUREFields.LotNValeur, value);

            }

        } else if (EventPASPMISE.TYPE.equals(eventType)) {

            String value = request.getParameter("information[email]");
            map.put(EventPASPMISEFields.Login, value);

            value = request.getParameter("information[betStakeKupUuid]");
            map.put(EventPASPMISEFields.Tech, value);

            // Always XY for Betkup
            map.put(EventPASPMISEFields.Combi, EventPASPMISEFields.CombiXY);

            value = request.getParameter("information[creditBefore]");
            map.put(EventPASPMISEFields.SoldeAvantMise, value);
            final float creditBefore = Float.valueOf(value);

            value = request.getParameter("information[betStakeValue]");
            map.put(EventPASPMISEFields.Mise, value);
            final float bet = Float.valueOf(value);

            final float creditAfter = creditBefore - bet;
            map.put(EventPASPMISEFields.SoldeApresMise,
                    String.valueOf(creditAfter));

            value = request.getParameter("information[betKupTitle]");
            map.put(EventPASPMISEFields.Clair, value);

            String ic = request.getParameter("information[predictionsIC]");
            if (ic != null && !ic.isEmpty()) {
                ic = Base64Decoder.decode(ic);
            }
            map.put("ic", ic);

            String se = request.getParameter("information[predictionsSE]");
            if (se != null && !se.isEmpty()) {
                se = Base64Decoder.decode(se);
            }
            map.put("se", se);
            String q = request.getParameter("information[predictionsQ]");
            if (q != null && !q.isEmpty()) {
                q = Base64Decoder.decode(q);
            }
            map.put("q", q);

            String startDateStr = request
                    .getParameter("information[betkupStartDate]");
            Date startDate = new java.util.Date(Long.valueOf(startDateStr));
            final SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyMMddHHmmss");
            final StringBuilder eventDate = new StringBuilder(
                    formatter.format(startDate));
            map.put("startDate", eventDate.toString());

        } else if (EventPASPGAIN.TYPE.equals(eventType)) {

            if (request.getParameter("winnings") != null) {

                String value = request.getParameter("login[accountEmail]");
                map.put(EventPASPGAINFields.Login, value);

                value = request.getParameter("winningsKupId");
                map.put(EventPASPGAINFields.Tech, value);

                value = request.getParameter("winningsKupDescription");
                map.put(EventPASPGAINFields.Clair, value);

                value = request.getParameter("winningsKupDescription");
                map.put(EventPASPGAINFields.Clair, value);

                value = request.getParameter("winningsKupEndDate");
                Date d = null;
                if (value != null) {
                    d = new java.util.Date(Long.valueOf(value));
                } else {
                    d = Calendar.getInstance().getTime();
                }
                final SimpleDateFormat formatter = new SimpleDateFormat(
                        "yyMMddHHmmss");
                final StringBuilder eventDate = new StringBuilder(
                        formatter.format(d));
                map.put(EventPASPGAINFields.DateHeure, eventDate.toString());

                value = request.getParameter("winningsBeforeGain");
                map.put(EventPASPGAINFields.SoldeAvantGain, value);

                value = request.getParameter("winningsGain");
                map.put(EventPASPGAINFields.Gain, value);

                value = request.getParameter("winningsAfterGain");
                map.put(EventPASPGAINFields.SoldeApresGain, value);

                value = request.getParameter("winningsBonus");
                if (value == null) {
                    value = "0";
                }
                map.put(EventPASPGAINFields.GainAbond, value);

                value = request.getParameter("winningsInformation");
                if (value == null) {
                    value = "NA";
                }
                map.put(EventPASPGAINFields.Info, value);

            }

        } else if (EventPASPANNUL.TYPE.equals(eventType)) {

            if (request.getParameter("cancelKups") != null) {

                String value = request.getParameter("email");
                map.put(EventPASPANNULFields.Login, value);

                value = request.getParameter("cancelKupId");
                map.put(EventPASPANNULFields.Tech, value);

                value = request.getParameter("cancelKupDescription");
                map.put(EventPASPANNULFields.Clair, value);

                value = request.getParameter("cancelKupDateHeure");
                Date d = new java.util.Date(Long.valueOf(value));
                final SimpleDateFormat formatter = new SimpleDateFormat(
                        "yyMMddHHmmss");
                final StringBuilder eventDate = new StringBuilder(
                        formatter.format(d));
                map.put(EventPASPANNULFields.DateHeure, eventDate.toString());

                value = request.getParameter("cancelKupCreditBefore");
                map.put(EventPASPANNULFields.SoldeAvantRembours, value);
                final float creditBefore = Float.valueOf(value);

                value = request.getParameter("cancelKupCreditBackAmount");
                map.put(EventPASPANNULFields.MontantRembours, value);
                final float amount = Float.valueOf(value);

                final float creditAfter = creditBefore + amount;
                map.put(EventPASPANNULFields.SoldeApresRembours,
                        String.valueOf(creditAfter));

                value = request.getParameter("cancelKupInfo");
                map.put(EventPASPANNULFields.Info, value);

            }

        } else {

            log.warn("Event type");

        }

        return map;
    }

}
