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

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.sofun.sensor.arjel.api.event.EventPASPMISE;
import org.sofun.sensor.arjel.api.event.EventPASPMISEFields;
import org.sofun.sensor.arjel.api.event.EventPASPMiseLig;
import org.sofun.sensor.arjel.impl.event.EventPASPMISEImpl;
import org.sofun.sensor.arjel.impl.event.EventPASPMiseLigImpl;

/**
 * Test event PASPMISE XML generation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestEventPASPMISE extends AbstractARJELTestCase {

    @Test
    public void testValidEvent() throws Exception {

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "1";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        String fieldTech = "41";

        String fieldClair = "OM - Inter";

        String fieldSoldeAvantMise = "100";

        String fieldMise = "2";

        String fieldSoldeApresMise = "98";

        String fieldMiseAbond = null;

        String fieldBonusAvantMise = null;

        String fieldBonusMouvement = null;

        String fieldBonusApresMise = null;

        String fieldBonusNom = null;

        String fieldCombi = EventPASPMISEFields.CombiXY;

        List<EventPASPMiseLig> ligs = new ArrayList<EventPASPMiseLig>();

        String _fieldNo = "1";

        String _fieldDateHeure = "091130120000";

        String _fieldTech = "f9000";

        String _fieldClair = "OM - Inter";

        String _fieldType = "1N2";

        String _fieldPariChoix = "OM";

        String _fieldPariCote = "1";

        ligs.add(new EventPASPMiseLigImpl(_fieldNo, _fieldDateHeure,
                _fieldTech, _fieldClair, _fieldType, _fieldPariChoix,
                _fieldPariCote));

        _fieldNo = "2";

        _fieldDateHeure = "091130120000";

        _fieldTech = "q9000";

        _fieldClair = "Meilleur buteur match ?";

        _fieldType = "Question";

        _fieldPariChoix = "t6577";

        _fieldPariCote = "1";

        ligs.add(new EventPASPMiseLigImpl(_fieldNo, _fieldDateHeure,
                _fieldTech, _fieldClair, _fieldType, _fieldPariChoix,
                _fieldPariCote));

        EventPASPMISE trace = new EventPASPMISEImpl(operatorID, safeID,
                eventID, eventDate, playerID, playerIP, sessionID, fieldTech,
                fieldClair, fieldSoldeAvantMise, fieldMise,
                fieldSoldeApresMise, fieldMiseAbond, fieldBonusAvantMise,
                fieldBonusMouvement, fieldBonusApresMise, fieldBonusNom,
                fieldCombi, ligs);

        trace.validate();

        String xml = trace.getXML();
        assertEquals(getXMLFrom("event/PASPMISE.xml"), xml);

        xml = trace.translate();
        assertEquals(getRootXMLFrom("event/PASPMISE_TR.xml"), xml);

    }

}
