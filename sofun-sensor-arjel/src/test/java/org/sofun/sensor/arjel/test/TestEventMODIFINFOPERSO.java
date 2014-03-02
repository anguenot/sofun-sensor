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
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSO;
import org.sofun.sensor.arjel.impl.event.EventMODIFINFOPERSOImpl;

/**
 * Test event MODIFINFOPERSO XML generation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestEventMODIFINFOPERSO extends AbstractARJELTestCase {

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

        final String fieldPseudo = "JA";

        final String fieldNom = "Anguenot";

        final String fieldPrenoms = "Julien Jean André";

        final String fieldCivilite = "M";

        final String fieldAd = "30 rue blondel";

        final String fieldCP = "75002";

        final String fieldVille = "Paris";

        final String fieldPays = "France";

        final String fieldTelFixe = "";

        final String fieldTelMob = "";

        final String fieldEmail = "julien@anguenot.org";

        EventMODIFINFOPERSO trace = new EventMODIFINFOPERSOImpl(operatorID,
                safeID, eventID, eventDate, playerID, playerIP, sessionID,
                fieldTypAg, fieldPseudo, fieldNom, fieldPrenoms, fieldCivilite,
                fieldAd, fieldCP, fieldVille, fieldPays, fieldTelFixe,
                fieldTelMob, fieldEmail);

        trace.validate();

        String xml = trace.getXML();
        assertEquals(getXMLFrom("event/MODIFINFOPERSO.xml"), xml);

        xml = trace.translate();
        assertEquals(getRootXMLFrom("event/MODIFINFOPERSO_TR.xml"), xml);

    }

}
