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
import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.arjel.api.exception.XMLValidationException;
import org.sofun.sensor.arjel.impl.event.XMLTraceImpl;

/**
 * Test XML Trace generation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestXMLTrace extends AbstractARJELTestCase {

    @Test
    public void testHeaderGeneration() throws Exception {

        final String type = "OUVINFOPERSO";

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "1";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        XMLTrace trace = new XMLTraceImpl(operatorID, safeID, type, eventID,
                eventDate, playerID, playerIP, sessionID);

        String xml = trace.getXML();
        assertEquals(getXMLFrom("testheadergeneration.xml"), xml);

    }

    @Test
    public void testHeaderValidation() throws Exception {

        final String type = "OUVINFOPERSO";

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "1";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        XMLTrace trace = new XMLTraceImpl(operatorID, safeID, type, eventID,
                eventDate, playerID, playerIP, sessionID);

        try {
            trace.validate();
            // Can't validate since only headers are generated.
            assertFalse(true);
        } catch (XMLValidationException e) {
            assertEquals(
                    "cvc-complex-type.2.4.b: The content of element 'OUVINFOPERSO' is not complete. One of '{TypAg}' is expected.",
                    e.getMessage());
        }

    }

    @Test
    public void testHeaderTranslation() throws Exception {

        final String type = "OUVINFOPERSO";

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "1";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        XMLTrace trace = new XMLTraceImpl(operatorID, safeID, type, eventID,
                eventDate, playerID, playerIP, sessionID);

        // Before translation.
        assertFalse(trace.isTranslated());
        assertTrue(getXMLFrom("testheadergeneration.xml")
                .equals(trace.getXML()));

        // After translation
        assertEquals(getRootXMLFrom("testtranslatedheadergeneration.xml"),
                trace.translate());
        assertTrue(trace.isTranslated());
        assertFalse(getXMLFrom("testheadergeneration.xml").equals(
                trace.getXML()));

        // Try to translate a second time.
        assertEquals(getRootXMLFrom("testtranslatedheadergeneration.xml"),
                trace.translate());
        assertTrue(trace.isTranslated());
        assertFalse(getXMLFrom("testheadergeneration.xml").equals(
                trace.getXML()));

    }

    @Test
    public void testElementTextUpdate() throws Exception {

        final String type = "OUVINFOPERSO";

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "1";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        XMLTrace trace = new XMLTraceImpl(operatorID, safeID, type, eventID,
                eventDate, playerID, playerIP, sessionID);

        assertEquals("1", trace.getElementTextValue("IDEvt"));

        trace.setElementTextValue("IDEvt", "2");
        assertEquals("2", trace.getElementTextValue("IDEvt"));

    }

    @Test
    public void testHTMLCommentInsertion() throws Exception {

        final String type = "OUVINFOPERSO";

        final String operatorID = "48";

        final String safeID = "1";

        final String eventDate = "100412103320";

        final String eventID = "";

        final String playerID = "julien@anguenot.org";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        XMLTrace trace = new XMLTraceImpl(operatorID, safeID, type, eventID,
                eventDate, playerID, playerIP, sessionID);

        trace.addHTMLComment("IDEvt", "CDATA_COMMENT");
        String xml = trace.getXML();
        assertEquals(getXMLFrom("testheadergenerationcdata.xml"), xml);

    }

}
