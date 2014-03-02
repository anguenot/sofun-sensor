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

package org.sofun.sensor.arjel.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sofun.sensor.arjel.api.event.EventACCESREFUSE;
import org.sofun.sensor.arjel.api.event.EventAUTOINTERDICTION;
import org.sofun.sensor.arjel.api.event.EventCLOTUREDEM;
import org.sofun.sensor.arjel.api.event.EventCPTEABOND;
import org.sofun.sensor.arjel.api.event.EventCPTEALIM;
import org.sofun.sensor.arjel.api.event.EventCPTEALIMOPE;
import org.sofun.sensor.arjel.api.event.EventCPTERETRAIT;
import org.sofun.sensor.arjel.api.event.EventLOTNATURE;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventOKCONDGENE;
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRME;
import org.sofun.sensor.arjel.api.event.EventPASPANNUL;
import org.sofun.sensor.arjel.api.event.EventPASPGAIN;
import org.sofun.sensor.arjel.api.event.EventPASPMISE;
import org.sofun.sensor.arjel.api.event.EventPREFCPTE;

/**
 * ARJEL Event Categories.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public final class EventCategory {

    /** Player Account category events */
    private static final String[] playerAccount = new String[] {
            EventOUVINFOPERSO.TYPE, EventPREFCPTE.TYPE, EventOKCONDGENE.TYPE,
            EventOUVOKCONFIRME.TYPE, EventACCESREFUSE.TYPE,
            EventMODIFINFOPERSO.TYPE, EventAUTOINTERDICTION.TYPE,
            EventCLOTUREDEM.TYPE };

    /** Banking category events */
    private static final String[] banking = new String[] { EventCPTEALIM.TYPE,
            EventCPTEABOND.TYPE, EventCPTERETRAIT.TYPE, EventCPTEALIMOPE.TYPE,
            EventLOTNATURE.TYPE };

    /** Betting category events */
    private static final String[] betting = new String[] { EventPASPMISE.TYPE,
            EventPASPGAIN.TYPE, EventPASPANNUL.TYPE };

    /**
     * Returns the list of Player Account category events
     * 
     * @return a unmodifiable list of events as {@link String}
     */
    public static List<String> getPlayerAcccountEvents() {
        return Collections.unmodifiableList(Arrays.asList(playerAccount));
    }

    /**
     * Returns the list of Banking category events
     * 
     * @return a unmodifiable list of events as {@link String}
     */
    public static List<String> getBankingEvents() {
        return Collections.unmodifiableList(Arrays.asList(banking));
    }

    /**
     * Returns the list if Betting category events
     * 
     * @return a unmodifiable list of events as {@link String}
     */
    public static List<String> getBettingEvents() {
        return Collections.unmodifiableList(Arrays.asList(betting));
    }

}
