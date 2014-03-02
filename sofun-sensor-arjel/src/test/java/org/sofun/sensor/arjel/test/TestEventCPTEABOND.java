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
import org.sofun.sensor.arjel.api.event.EventCPTEABOND;
import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.arjel.impl.event.EventCPTEABONDImpl;

/**
 * Test event CPTEABOND XML generation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestEventCPTEABOND extends AbstractARJELTestCase {

    @Test
    public void testValidEvent() throws Exception {

        final String operatorID = "48";

        final String safeID = "1";

        final String eventId = "1";

        final String eventDate = "100412103320";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        final String fieldTypAg = "PS";

        final String fieldSoldeAvant = "100";

        final String fieldMontAbond = "10";

        final String fieldSoldeApres = "110";

        final String fieldInfo = "Free Text Here";

        XMLTrace trace = new EventCPTEABONDImpl(operatorID, safeID, eventId,
                eventDate, playerID, playerIP, sessionID, fieldTypAg,
                fieldSoldeAvant, fieldMontAbond, fieldSoldeApres, fieldInfo,
                EventCPTEABOND.TypeAbondementOuverture);

        trace.validate();
        String xml = trace.getXML();
        assertEquals(getXMLFrom("event/CPTEABOND.xml"), xml);

        xml = trace.translate();
        assertEquals(getRootXMLFrom("event/CPTEABOND_TR.xml"), xml);

    }

}
