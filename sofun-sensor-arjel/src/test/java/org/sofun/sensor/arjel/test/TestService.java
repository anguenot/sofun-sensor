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

import junit.framework.TestCase;

import org.junit.Test;
import org.sofun.sensor.arjel.api.ARJELService;
import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.arjel.impl.ARJELServiceImpl;

/**
 * Test ARJEL Service.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestService extends TestCase {

    private ARJELService service;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        service = new ARJELServiceImpl();
    }

    @Override
    protected void tearDown() throws Exception {
        service = null;
        super.tearDown();
    }

    @Test
    public void testProperties() {
        assertEquals("48", service.getOperatorID());
        assertEquals("1", service.getSafeID());
    }

    @Test
    public void testHeaderGeneration() throws Exception {

        final String type = "TEST";

        final String playerIP = "127.0.0.1";

        final String sessionID = "1";

        XMLTrace trace = service.getXMLTrace(type, playerIP, sessionID, null);

        String xml = trace.getXML();
        assertNotNull(xml);

    }

}
