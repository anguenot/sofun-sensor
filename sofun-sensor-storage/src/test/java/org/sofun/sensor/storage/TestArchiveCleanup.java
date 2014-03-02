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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.sofun.sensor.storage.HostAndPortUtil.HostAndPort;
import org.sofun.sensor.storage.api.Archive;
import org.sofun.sensor.storage.api.StorageService;
import org.sofun.sensor.storage.api.exception.StorageException;
import org.sofun.sensor.storage.impl.ArchiveCleanup;
import org.sofun.sensor.storage.impl.StorageServiceImpl;

/**
 * Archive cleanup test case.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestArchiveCleanup extends AbstractTestCase {

    private static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);

    private StorageService storage;

    private ArchiveCleanup cleanup;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        storage = new StorageServiceImpl(hnp.host, hnp.port);
        cleanup = new ArchiveCleanup(storage);
    }

    @Test
    public void testCleanup() throws Exception {

        final String k1 = "A";
        final String k2 = "B";

        Archive a1 = getTestArchive(k1, "event0.xml");
        storage.set(a1);
        Archive a2 = getTestArchive(k2, "event1.xml");
        storage.set(a2);

        assertNotNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        cleanup.check();

        assertNotNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        a1.setSent(true);
        a1.setSafeAck(true);
        a1.setSafeArchived(true);
        storage.set(a1);

        assertNotNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        cleanup.check();

        assertNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        a2.setSent(true);
        a2.setSafeAck(true);
        a2.setSafeArchived(true);
        storage.set(a2);

        assertNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        cleanup.check();

        assertNull(storage.get(k1));
        assertNull(storage.get(k2));

        // try to remove 2 at once.
        storage.set(a1);
        storage.set(a2);

        assertNotNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        cleanup.check();

        assertNull(storage.get(k1));
        assertNull(storage.get(k2));

    }

    @Test
    public void testCheckConcurrency() throws Exception {

        ExecutorService exec = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 10000; i++) {
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    runCleanup();
                }
            });
        }
        exec.shutdown();
        exec.awaitTermination(50, TimeUnit.SECONDS);

    }

    /** Called in checkConcurrency() above */
    private void runCleanup() {
        try {
            cleanup.check();
        } catch (Throwable t) {
            // assert error
            assertFalse(true);
        }
    }

    @Test
    public void testEmptyConstructor() throws Exception {
        // Smoke test.
        new ArchiveCleanup();
    }

    @Test
    public void testExceptionWithinCheck() throws Exception {

        cleanup = new ArchiveCleanup(null);

        boolean exc = false;
        try {
            cleanup.check();
        } catch (StorageException e) {
            exc = true;
        }
        assertTrue(exc);

    }

    @Override
    protected void tearDown() throws Exception {
        cleanup = null;
        storage = null;
        super.tearDown();
    }

}
