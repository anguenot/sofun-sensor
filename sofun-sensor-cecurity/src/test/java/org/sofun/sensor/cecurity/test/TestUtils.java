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

package org.sofun.sensor.cecurity.test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.sofun.sensor.cecurity.api.ClientIDGenerator;
import org.sofun.sensor.cecurity.api.Configuration;
import org.sofun.sensor.cecurity.api.TimeGenerator;
import org.sofun.sensor.cecurity.impl.ClientIDGeneratorImpl;
import org.sofun.sensor.cecurity.impl.TimeGeneratorImpl;

/**
 * Misc utils test cases.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestUtils extends TestCase {

    private static final String PREFIX = (String) Configuration.getProperties()
            .get("client.id.prefix");

    @Test
    public void testClientIDGenerator() throws Exception {

        ClientIDGenerator cidg = new ClientIDGeneratorImpl();
        final String cid = cidg.getUniqueClientID();
        assertTrue(cid.startsWith(PREFIX));

        Set<String> cids = new HashSet<String>();
        final int total = 500000; // 500000 generations
        for (int i = 0; i < total; i++) {
            cids.add(cidg.getUniqueClientID());
        }

        // Sets only stores unique value so we can't have duplicates.
        assertEquals(total, cids.size());

    }

    @Test
    public void testTimeGenerator() throws Exception {

        Calendar.getInstance().getTime();

        Thread.sleep(300);

        TimeGenerator timeGen = new TimeGeneratorImpl();
        Date gen = timeGen.getTime();

        Thread.sleep(300);

        Date d2 = Calendar.getInstance().getTime();

        // assertTrue(d1.before(gen));
        assertTrue(d2.after(gen));

    }

}
