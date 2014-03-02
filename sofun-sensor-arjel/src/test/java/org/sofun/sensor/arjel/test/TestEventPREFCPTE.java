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

package org.sofun.sensor.arjel.test;

import org.junit.Test;
import org.sofun.sensor.arjel.api.event.EventPREFCPTE;
import org.sofun.sensor.arjel.impl.event.EventPREFCPTEImpl;

/**
 * Test event PREFCPTE XML generation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestEventPREFCPTE extends AbstractARJELTestCase {

    @Test
    public void testValidEvent() throws Exception {

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "1";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        final String fieldTypAg = "PS";

        final String fieldCompteMin = "100";

        final String fieldCompteMax = null;

        final String fieldMiseMaxMontantMM = "100";

        final String fieldMiseMaxPeriodeMM = "S";

        final String fieldMiseMaxTypeJeuMM = "";

        final String fieldDepotMaxMontantMM = "1000";

        final String fieldDepotMaxPeriodeMM = "S";

        final String fieldDepotMaxTypeJeuMM = "";

        final String fieldModerateurLibelModer = "";

        final String fieldModerateurSeuilModer = "";

        final String fieldModerateurPeriodeModer = "";

        final String fieldModerateurTypeJeuModer = "";

        EventPREFCPTE trace = new EventPREFCPTEImpl(operatorID, safeID,
                eventID, eventDate, playerID, playerIP, sessionID, fieldTypAg,
                fieldCompteMin, fieldCompteMax, fieldMiseMaxMontantMM,
                fieldMiseMaxPeriodeMM, fieldMiseMaxTypeJeuMM,
                fieldDepotMaxMontantMM, fieldDepotMaxPeriodeMM,
                fieldDepotMaxTypeJeuMM, fieldModerateurLibelModer,
                fieldModerateurSeuilModer, fieldModerateurPeriodeModer,
                fieldModerateurTypeJeuModer);

        trace.validate();

        String xml = trace.getXML();
        assertEquals(getXMLFrom("event/PREFCPTE.xml"), xml);

        xml = trace.translate();
        assertEquals(getRootXMLFrom("event/PREFCPTE_TR.xml"), xml);

    }

}
