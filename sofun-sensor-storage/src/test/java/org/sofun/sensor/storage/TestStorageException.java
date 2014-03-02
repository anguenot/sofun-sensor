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

package org.sofun.sensor.storage;

import junit.framework.TestCase;

import org.junit.Test;
import org.sofun.sensor.storage.api.exception.StorageException;

/**
 * Storage Exception unit tests.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 *
 */
public class TestStorageException extends TestCase {

    @Test
    public void testConstructor() {
     
        StorageException e1 = new StorageException();
        assertTrue(e1 instanceof Exception);
        
        final String msg = "An error occured";
        StorageException e2 = new StorageException(msg);
        assertEquals(msg, e2.getMessage());
        
        StorageException e3 = new StorageException(new Exception(msg));
        assertEquals(msg, e3.getMessage());
        
    }
    
}
