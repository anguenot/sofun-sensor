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

package org.sofun.sensor.arjel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.arjel.api.ARJELService;
import org.sofun.sensor.arjel.api.Configuration;
import org.sofun.sensor.arjel.api.Util;
import org.sofun.sensor.arjel.api.ejb.ARJELServiceLocal;
import org.sofun.sensor.arjel.api.ejb.ARJELServiceRemote;
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
import org.sofun.sensor.arjel.api.event.EventLOTNATURE;
import org.sofun.sensor.arjel.api.event.EventLOTNATUREFields;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSOFields;
import org.sofun.sensor.arjel.api.event.EventOKCONDGENE;
import org.sofun.sensor.arjel.api.event.EventOKCONDGENEFields;
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSOFields;
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRME;
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRMEFields;
import org.sofun.sensor.arjel.api.event.EventPASPANNUL;
import org.sofun.sensor.arjel.api.event.EventPASPANNULFields;
import org.sofun.sensor.arjel.api.event.EventPASPGAIN;
import org.sofun.sensor.arjel.api.event.EventPASPGAINFields;
import org.sofun.sensor.arjel.api.event.EventPASPMISE;
import org.sofun.sensor.arjel.api.event.EventPASPMISEFields;
import org.sofun.sensor.arjel.api.event.EventPASPMiseLig;
import org.sofun.sensor.arjel.api.event.EventPREFCPTE;
import org.sofun.sensor.arjel.api.event.EventPREFCPTEFields;
import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.arjel.impl.event.EventACCESREFUSEImpl;
import org.sofun.sensor.arjel.impl.event.EventAUTOINTERDICTIONImpl;
import org.sofun.sensor.arjel.impl.event.EventCLOTUREDEMImpl;
import org.sofun.sensor.arjel.impl.event.EventCPTEABONDImpl;
import org.sofun.sensor.arjel.impl.event.EventCPTEALIMImpl;
import org.sofun.sensor.arjel.impl.event.EventCPTEALIMOPEImpl;
import org.sofun.sensor.arjel.impl.event.EventCPTERETRAITImpl;
import org.sofun.sensor.arjel.impl.event.EventLOTNATUREImpl;
import org.sofun.sensor.arjel.impl.event.EventMODIFINFOPERSOImpl;
import org.sofun.sensor.arjel.impl.event.EventOKCONDGENEImpl;
import org.sofun.sensor.arjel.impl.event.EventOUVINFOPERSOImpl;
import org.sofun.sensor.arjel.impl.event.EventOUVOKCONFIRMEImpl;
import org.sofun.sensor.arjel.impl.event.EventPASPANNULImpl;
import org.sofun.sensor.arjel.impl.event.EventPASPGAINImpl;
import org.sofun.sensor.arjel.impl.event.EventPASPMISEImpl;
import org.sofun.sensor.arjel.impl.event.EventPASPMiseLigImpl;
import org.sofun.sensor.arjel.impl.event.EventPREFCPTEImpl;
import org.sofun.sensor.arjel.impl.event.XMLTraceImpl;

/**
 * ARJEL Service implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Stateless
@Local(ARJELServiceLocal.class)
@Remote(ARJELServiceRemote.class)
public class ARJELServiceImpl implements ARJELService {

    private static final long serialVersionUID = -1879133291543443938L;

    private static final Log log = LogFactory.getLog(ARJELServiceImpl.class);

    public ARJELServiceImpl() {
    }

    @Override
    public String getOperatorID() {
        final String key = "operator.id";
        return Configuration.getProperties().getProperty(key);
    }

    @Override
    public String getSafeID() {
        final String key = "safe.id";
        return Configuration.getProperties().getProperty(key);
    }

    @Override
    public String getTypeAg() {
        final String key = "operator.ag.type";
        return Configuration.getProperties().getProperty(key);
    }

    @Override
    public XMLTrace getXMLTrace(String eventType, String playerIP,
            String sessionID, Map<String, String> params) {

        // Safe will generate the actual event identifier in our case. We must
        // include it within the generated XML for validation purpose though.
        final String eventId = "0";

        final String eventDate = Util.getEventDate();
        final String fieldTypAg = getTypeAg();

        XMLTrace trace = null;

        if (params == null) {
            log.warn("No params received for XML Trace generation.");
            params = new HashMap<String, String>();
        }

        if (EventOKCONDGENE.TYPE.equals(eventType)) {

            trace = new EventOKCONDGENEImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate,
                    params.get(EventOKCONDGENEFields.Login), playerIP,
                    sessionID, fieldTypAg);

        } else if (EventOUVINFOPERSO.TYPE.equals(eventType)) {

            trace = new EventOUVINFOPERSOImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate,
                    params.get(EventOUVINFOPERSOFields.Login), playerIP,
                    sessionID, fieldTypAg,
                    params.get(EventOUVINFOPERSOFields.Login),
                    params.get(EventOUVINFOPERSOFields.Pseudo),
                    params.get(EventOUVINFOPERSOFields.Nom),
                    params.get(EventOUVINFOPERSOFields.Prenoms),
                    params.get(EventOUVINFOPERSOFields.Civilite),
                    params.get(EventOUVINFOPERSOFields.DateN),
                    params.get(EventOUVINFOPERSOFields.VilleN),
                    params.get(EventOUVINFOPERSOFields.DptN),
                    params.get(EventOUVINFOPERSOFields.PaysN),
                    params.get(EventOUVINFOPERSOFields.Ad),
                    params.get(EventOUVINFOPERSOFields.CP),
                    params.get(EventOUVINFOPERSOFields.Ville),
                    params.get(EventOUVINFOPERSOFields.Pays),
                    params.get(EventOUVINFOPERSOFields.TelFixe),
                    params.get(EventOUVINFOPERSOFields.TelMob),
                    params.get(EventOUVINFOPERSOFields.Email));

        } else if (EventPREFCPTE.TYPE.equals(eventType)) {

            String login = params.get(EventPREFCPTEFields.Login);

            if (login != null) {

                // null values not handled / required by Betkup

                final String fieldCompteMin = null;
                final String fieldCompteMax = params
                        .get(EventPREFCPTEFields.CompteMax);
                final String fieldMiseMaxMontantMM = params
                        .get(EventPREFCPTEFields.MiseMax);
                final String fieldMiseMaxPeriodeMM = params
                        .get(EventPREFCPTEFields.PeriodeMM);
                final String fieldDepotMaxMontantMM = params
                        .get(EventPREFCPTEFields.DepotMax);
                final String fieldDepotMaxPeriodeMM = params
                        .get(EventPREFCPTEFields.PeriodeMM);
                final String fieldMiseMaxTypeJeuMM = null;
                final String fieldDepotMaxTypeJeuMM = null;
                final String fieldModerateurLibelModer = null;
                final String fieldModerateurSeuilModer = null;
                final String fieldModerateurPeriodeModer = null;
                final String fieldModerateurTypeJeuModer = null;

                trace = new EventPREFCPTEImpl(getOperatorID(), getSafeID(),
                        eventId, eventDate, login, playerIP, sessionID,
                        fieldTypAg, fieldCompteMin, fieldCompteMax,
                        fieldMiseMaxMontantMM, fieldMiseMaxPeriodeMM,
                        fieldMiseMaxTypeJeuMM, fieldDepotMaxMontantMM,
                        fieldDepotMaxPeriodeMM, fieldDepotMaxTypeJeuMM,
                        fieldModerateurLibelModer, fieldModerateurSeuilModer,
                        fieldModerateurPeriodeModer,
                        fieldModerateurTypeJeuModer);

            }

        } else if (EventACCESREFUSE.TYPE.equals(eventType)) {

            String login = params.get(EventPREFCPTEFields.Login);

            final String fieldCauseRefus = params
                    .get(EventACCESREFUSEFields.CauseRefus);
            final String fieldTypeRefus = params
                    .get(EventACCESREFUSEFields.TypeRefus);

            trace = new EventACCESREFUSEImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, login, playerIP, sessionID, fieldTypAg,
                    fieldCauseRefus, fieldTypeRefus);

        } else if (EventOUVOKCONFIRME.TYPE.equals(eventType)) {

            final String login = params.get(EventOUVOKCONFIRMEFields.Login);
            if (login != null && !login.isEmpty()) {
                trace = new EventOUVOKCONFIRMEImpl(getOperatorID(),
                        getSafeID(), eventId, eventDate, login, playerIP,
                        sessionID, fieldTypAg);
            }
            // If no login parameter then it means no activation occurred. See
            // extraction side

        } else if (EventMODIFINFOPERSO.TYPE.equals(eventType)) {

            final String fieldPseudo = params
                    .get(EventMODIFINFOPERSOFields.Pseudo);
            final String fieldNom = params.get(EventMODIFINFOPERSOFields.Nom);
            final String fieldPrenoms = params
                    .get(EventMODIFINFOPERSOFields.Prenoms);
            final String fieldCivilite = params
                    .get(EventMODIFINFOPERSOFields.Civilite);
            final String fieldAd = params.get(EventMODIFINFOPERSOFields.Ad);
            final String fieldCP = params.get(EventMODIFINFOPERSOFields.CP);
            final String fieldVille = params
                    .get(EventMODIFINFOPERSOFields.Ville);
            final String fieldPays = params.get(EventMODIFINFOPERSOFields.Pays);
            final String fieldTelFixe = params
                    .get(EventMODIFINFOPERSOFields.TelFixe);
            final String fieldTelMob = params
                    .get(EventMODIFINFOPERSOFields.TelMob);
            final String fieldEmail = params
                    .get(EventMODIFINFOPERSOFields.Email);
            final String login = params.get(EventMODIFINFOPERSOFields.Login);
            if (login != null) {
                trace = new EventMODIFINFOPERSOImpl(getOperatorID(),
                        getSafeID(), eventId, eventDate, login, playerIP,
                        sessionID, fieldTypAg, fieldPseudo, fieldNom,
                        fieldPrenoms, fieldCivilite, fieldAd, fieldCP,
                        fieldVille, fieldPays, fieldTelFixe, fieldTelMob,
                        fieldEmail);
            }

        } else if (EventAUTOINTERDICTION.TYPE.equals(eventType)) {

            final String login = params.get(EventAUTOINTERDICTIONFields.Login);
            if (login != null) {
                final String duree = params
                        .get(EventAUTOINTERDICTIONFields.Duree);
                if (duree != null && !"0".equals(duree)) {
                    trace = new EventAUTOINTERDICTIONImpl(getOperatorID(),
                            getSafeID(), eventId, eventDate, login, playerIP,
                            sessionID, fieldTypAg,
                            params.get(EventAUTOINTERDICTIONFields.DateModif),
                            duree);
                }
            }

        } else if (EventCLOTUREDEM.TYPE.equals(eventType)) {

            trace = new EventCLOTUREDEMImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate,
                    params.get(EventCLOTUREDEMFields.Login), playerIP,
                    sessionID, fieldTypAg,
                    params.get(EventCLOTUREDEMFields.SoldeClos));

        } else if (EventCPTEALIM.TYPE.equals(eventType)) {

            final String playerID = params.get(EventCPTEALIMFields.Login);
            final String fieldDateDemande = params
                    .get(EventCPTEALIMFields.DateDemande);
            final String fieldDateEffective = params
                    .get(EventCPTEALIMFields.DateEffective);
            final String fieldSoldeAvant = params
                    .get(EventCPTEALIMFields.SoldeAvant);
            final String fieldSoldeApres = params
                    .get(EventCPTEALIMFields.SoldeApres);
            final String fieldSoldeMouvement = params
                    .get(EventCPTEALIMFields.SoldeMouvement);
            final String fieldMoyenPaiement = params
                    .get(EventCPTEALIMFields.MoyenPaiement);
            final String fieldTypeMoyenPaiement = params
                    .get(EventCPTEALIMFields.TypeMoyenPaiement);

            trace = new EventCPTEALIMImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID,
                    fieldTypAg, fieldDateDemande, fieldDateEffective,
                    fieldSoldeAvant, fieldSoldeMouvement, fieldSoldeApres,
                    fieldMoyenPaiement, fieldTypeMoyenPaiement);

        } else if (EventCPTERETRAIT.TYPE.equals(eventType)) {

            final String playerID = params.get(EventCPTEALIMFields.Login);

            final String fieldSoldeAvant = params
                    .get(EventCPTEALIMFields.SoldeAvant);
            final String fieldSoldeApres = params
                    .get(EventCPTEALIMFields.SoldeApres);
            final String fieldSoldeMouvement = params
                    .get(EventCPTEALIMFields.SoldeMouvement);

            trace = new EventCPTERETRAITImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID,
                    fieldTypAg, fieldSoldeAvant, fieldSoldeMouvement,
                    fieldSoldeApres);

        } else if (EventCPTEABOND.TYPE.equals(eventType)) {

            final String playerID = params.get(EventCPTEABONDFields.Login);

            final String fieldSoldeAvant = params
                    .get(EventCPTEABONDFields.SoldeAvant);
            final String fieldMontAbond = params
                    .get(EventCPTEABONDFields.MontAbond);
            final String fieldSoldeApres = params
                    .get(EventCPTEABONDFields.SoldeApres);
            final String fieldInfo = params.get(EventCPTEABONDFields.Info);
            final String fieldTypeAbondement = params
                    .get(EventCPTEABONDFields.TypeAbondement);

            trace = new EventCPTEABONDImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID,
                    fieldTypAg, fieldSoldeAvant, fieldMontAbond,
                    fieldSoldeApres, fieldInfo, fieldTypeAbondement);

        } else if (EventCPTEALIMOPE.TYPE.equals(eventType)) {

            final String playerID = params.get(EventCPTEALIMOPEFields.Login);

            final String fieldBonusAvant = params
                    .get(EventCPTEALIMOPEFields.BonusAvant);
            final String fieldBonusApres = params
                    .get(EventCPTEALIMOPEFields.BonusApres);
            final String fieldBonusMouvement = params
                    .get(EventCPTEALIMOPEFields.BonusMouvement);
            final String fieldBonusNom = params
                    .get(EventCPTEALIMOPEFields.BonusNom);

            trace = new EventCPTEALIMOPEImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID,
                    fieldTypAg, fieldBonusAvant, fieldBonusApres,
                    fieldBonusMouvement, fieldBonusNom);

        } else if (EventLOTNATURE.TYPE.equals(eventType)) {

            final String playerID = params.get(EventLOTNATUREFields.Login);
            final String fieldLotNNom = params
                    .get(EventLOTNATUREFields.LotNNom);
            final String fieldLotNValeur = params
                    .get(EventLOTNATUREFields.LotNValeur);

            trace = new EventLOTNATUREImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID,
                    fieldTypAg, fieldLotNNom, fieldLotNValeur);

        } else if (EventPASPMISE.TYPE.equals(eventType)) {

            final String playerID = params.get(EventPASPMISEFields.Login);

            final String fieldTech = params.get(EventPASPMISEFields.Tech);
            final String fieldClair = params.get(EventPASPMISEFields.Clair);
            final String fieldSoldeAvantMise = params
                    .get(EventPASPMISEFields.SoldeAvantMise);
            final String fieldMise = params.get(EventPASPMISEFields.Mise);
            final String fieldSoldeApresMise = params
                    .get(EventPASPMISEFields.SoldeApresMise);
            final String fieldMiseAbond = params
                    .get(EventPASPMISEFields.MiseAbond);
            final String fieldBonusAvantMise = params
                    .get(EventPASPMISEFields.BonusAvantMise);
            final String fieldBonusMouvement = params
                    .get(EventPASPMISEFields.BonusMouvement);
            final String fieldBonusApresMise = params
                    .get(EventPASPMISEFields.BonusApresMise);
            final String fieldBonusNom = params
                    .get(EventPASPMISEFields.BonusNom);
            final String fieldCombi = params.get(EventPASPMISEFields.Combi);

            final String startDate = params.get("startDate");

            final List<EventPASPMiseLig> ligs = new ArrayList<EventPASPMiseLig>();

            int nb = 0;

            final String se = params.get("se");
            if (se != null && !se.isEmpty()) {
                String[] choices = se.split(";");
                if (choices.length > 0) {
                    for (String choice : choices) {
                        String[] subs = choice.split(":");
                        if (subs.length == 2) {
                            String sub1 = subs[0];
                            sub1 = sub1.replace("\"", "");
                            String sub2 = subs[1];
                            sub1 = sub2.replace("\"", "");
                            ligs.add(new EventPASPMiseLigImpl(String
                                    .valueOf(nb), startDate, sub1, fieldClair,
                                    "SCORE EXACT", sub2, "1"));
                            nb++;
                        }
                    }
                }
            }
            final String q = params.get("q");
            if (q != null && !q.isEmpty()) {
                String[] choices = q.split(";");
                if (choices.length > 0) {
                    for (String choice : choices) {
                        String[] subs = choice.split(":");
                        if (subs.length == 2) {
                            String sub1 = subs[0];
                            sub1 = sub1.replace("\"", "");
                            String sub2 = subs[1];
                            sub1 = sub2.replace("\"", "");
                            ligs.add(new EventPASPMiseLigImpl(String
                                    .valueOf(nb), startDate, sub1, fieldClair,
                                    "QUESTION", sub2, "1"));
                            nb++;
                        }
                    }
                }
            }

            final String ic = params.get("ic");
            if (ic != null && !ic.isEmpty()) {
                String[] choices = ic.split(";");
                if (choices.length > 0) {
                    for (String choice : choices) {
                        String[] subs = choice.split(":");
                        if (subs.length == 2) {
                            String sub1 = subs[0];
                            sub1 = sub1.replace("\"", "");
                            String sub2 = subs[1];
                            sub1 = sub2.replace("\"", "");
                            ligs.add(new EventPASPMiseLigImpl(String
                                    .valueOf(nb), startDate, sub1, fieldClair,
                                    "1N2", sub2, "1"));
                            nb++;
                        }
                    }
                }
            }

            trace = new EventPASPMISEImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID,
                    fieldTech, fieldClair, fieldSoldeAvantMise, fieldMise,
                    fieldSoldeApresMise, fieldMiseAbond, fieldBonusAvantMise,
                    fieldBonusMouvement, fieldBonusApresMise, fieldBonusNom,
                    fieldCombi, ligs);

        } else if (EventPASPGAIN.TYPE.equals(eventType)) {

            final String playerID = params.get(EventPASPGAINFields.Login);
            final String fieldTech = params.get(EventPASPGAINFields.Tech);
            final String fieldClair = params.get(EventPASPGAINFields.Clair);
            final String fieldDateHeure = params
                    .get(EventPASPGAINFields.DateHeure);
            final String fieldSoldeAvantGain = params
                    .get(EventPASPGAINFields.SoldeAvantGain);
            final String fieldGain = params
                    .get(EventPASPGAINFields.Gain);
            final String fieldSoldeApresGain = params
                    .get(EventPASPGAINFields.SoldeApresGain);
            final String fieldGainAbond = params
                    .get(EventPASPGAINFields.GainAbond);
            final String fieldInfo = params
                    .get(EventPASPGAINFields.Info);

            trace = new EventPASPGAINImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID, fieldTech,
                    fieldClair, fieldDateHeure, fieldSoldeAvantGain, fieldGain,
                    fieldSoldeApresGain, fieldGainAbond, fieldInfo);

        } else if (EventPASPANNUL.TYPE.equals(eventType)) {

            final String playerID = params.get(EventPASPANNULFields.Login);
            final String fieldTech = params.get(EventPASPANNULFields.Tech);
            final String fieldClair = params.get(EventPASPANNULFields.Clair);
            final String fieldDateHeure = params
                    .get(EventPASPANNULFields.DateHeure);
            final String fieldSoldeAvantRembours = params
                    .get(EventPASPANNULFields.SoldeAvantRembours);
            final String fieldMontantRembours = params
                    .get(EventPASPANNULFields.MontantRembours);
            final String fieldSoldeApresRembours = params
                    .get(EventPASPANNULFields.SoldeApresRembours);
            final String fieldInfo = params.get(EventPASPANNULFields.Info);

            trace = new EventPASPANNULImpl(getOperatorID(), getSafeID(),
                    eventId, eventDate, playerID, playerIP, sessionID,
                    fieldTech, fieldClair, fieldDateHeure,
                    fieldSoldeAvantRembours, fieldMontantRembours,
                    fieldSoldeApresRembours, fieldInfo);

        } else {

            log.warn("Event Type not recognized. Generating header only trace.");

            // No recognized event type: only header generation in this case
            trace = new XMLTraceImpl(getOperatorID(), getSafeID(), eventType,
                    eventId, eventDate, "NOID", playerIP, sessionID);

        }
        return trace;

    }
}
