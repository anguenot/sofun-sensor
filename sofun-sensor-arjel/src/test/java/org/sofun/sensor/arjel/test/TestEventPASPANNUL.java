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
import org.sofun.sensor.arjel.api.event.EventPASPANNUL;
import org.sofun.sensor.arjel.impl.event.EventPASPANNULImpl;

/**
 * Test event PASPANNUL XML generation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestEventPASPANNUL extends AbstractARJELTestCase {

    @Test
    public void testValidEvent() throws Exception {

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "1";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";
        
        final String fieldTech = "41";

        final String fieldClair = "OM - Inter";

        final String fieldDateHeure = "100412103320";

        final String fieldSoldeAvantRembours = "99";

        final String fieldMontantRembours = "1";

        final String fieldSoldeApresRembours = "100";

        final String fieldInfo = "NA";

        EventPASPANNUL trace = new EventPASPANNULImpl(operatorID, safeID,
                eventID, eventDate, playerID, playerIP, sessionID, fieldTech,
                fieldClair, fieldDateHeure, fieldSoldeAvantRembours,
                fieldMontantRembours, fieldSoldeApresRembours, fieldInfo);

        trace.validate();

        String xml = trace.getXML();
        assertEquals(getXMLFrom("event/PASPANNUL.xml"), xml);

        xml = trace.translate();
        assertEquals(getRootXMLFrom("event/PASPANNUL_TR.xml"), xml);
        

    }

}
