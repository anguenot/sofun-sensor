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
import org.sofun.sensor.arjel.api.Util;

/**
 * Test ARJEL util.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestUtil extends TestCase {

    @Test
    public void testEventDate() {
        String eventDate = Util.getEventDate();
        assertEquals(12, eventDate.length());
    }

}
