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

import org.junit.Test;
import org.sofun.sensor.storage.HostAndPortUtil.HostAndPort;
import org.sofun.sensor.storage.api.Archive;
import org.sofun.sensor.storage.api.StorageService;
import org.sofun.sensor.storage.api.exception.StorageException;
import org.sofun.sensor.storage.impl.StorageServiceImpl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Storage Service Test Case.
 * 
 * <p>
 * 
 * This test case require a Redis instance up and running on the host runnning
 * the tests.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestStorageService extends AbstractTestCase {

    private static HostAndPort hnp = HostAndPortUtil.getRedisServers().get(0);

    private StorageService storage;

    private static String[] keys = { "K-123", "K-132" };

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        storage = new StorageServiceImpl(hnp.host, hnp.port);
        // In case of
        cleanup();
    }

    @Override
    protected void tearDown() throws Exception {
        cleanup();
        super.tearDown();
    }

    private void cleanup() throws Exception {
        storage.flushAll();
    }

    @Test
    public void testServiceAPI() throws Exception {

        final String k1 = keys[0];
        final String k2 = keys[1];

        assertNull(storage.get(k1));

        Archive archive = getTestArchive(k1, "event0.xml");
        storage.set(archive);

        Archive retrieved = storage.get(k1);
        assertNotNull(retrieved);

        // Test delete by group.

        final String PROP_ID = "GROUP_ID";

        retrieved.setProperty(PROP_ID, "1");
        storage.set(retrieved);
        retrieved = storage.get(k1);

        assertEquals("1", retrieved.getProperties().get(PROP_ID));

        archive = getTestArchive(k2, "event1.xml");
        archive.setProperty(PROP_ID, "1");
        storage.set(archive);

        assertNotNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        storage.delByProp("1", PROP_ID);

        assertNull(storage.get(k1));
        assertNull(storage.get(k2));

        // Test null values
        storage.delByProp("1", null);
        storage.delByProp(null, PROP_ID);
        storage.delByProp(null, null);

    }

    @Test
    public void testUnsentArchives() throws Exception {

        assertEquals(0, storage.getUnSentArchives().size());

        final String k1 = keys[0];
        Archive a1 = getTestArchive(k1, "event0.xml");
        a1.setSent(false);
        storage.set(a1);

        final String k2 = keys[1];
        Archive a2 = getTestArchive(k2, "event1.xml");
        a2.setSent(true);
        storage.set(a2);

        assertEquals(1, storage.getUnSentArchives().size());

        a2.setSent(false);
        storage.set(a2);
        assertEquals(2, storage.getUnSentArchives().size());

        a1.setSent(true);
        storage.set(a1);

        a2.setSent(true);
        storage.set(a2);

    }

    @Test
    public void testUnAckArchives() throws Exception {

        assertEquals(0, storage.getUnAckArchives().size());

        final String k1 = keys[0];
        Archive a1 = getTestArchive(k1, "event0.xml");
        a1.setSent(true);
        storage.set(a1);

        final String k2 = keys[1];
        Archive a2 = getTestArchive(k2, "event1.xml");
        a2.setSent(true);
        storage.set(a2);

        assertEquals(2, storage.getUnAckArchives().size());

        a2.setSafeAck(true);
        storage.set(a2);
        assertEquals(1, storage.getUnAckArchives().size());

        a1.setSafeAck(true);
        storage.set(a1);
        assertEquals(0, storage.getUnAckArchives().size());

    }

    @Test
    public void testUnAckArchArchives() throws Exception {

        assertEquals(0, storage.getUnAckArchArchives().size());

        final String k1 = keys[0];
        Archive a1 = getTestArchive(k1, "event0.xml");
        storage.set(a1);

        final String k2 = keys[1];
        Archive a2 = getTestArchive(k2, "event1.xml");
        storage.set(a2);

        assertEquals(0, storage.getUnAckArchArchives().size());

        a1.setSafeAck(true);
        a2.setSafeAck(true);
        a2.setSafeArchived(true);
        storage.set(a1);
        storage.set(a2);
        assertEquals(1, storage.getUnAckArchArchives().size());

        a1.setSafeArchived(true);
        storage.set(a1);
        assertEquals(0, storage.getUnAckArchArchives().size());

    }

    @Test
    public void testSentAndArchives() throws Exception {

        assertEquals(0, storage.getArchived().size());

        final String k1 = keys[0];
        Archive a1 = getTestArchive(k1, "event0.xml");
        a1.setSent(false);
        storage.set(a1);

        final String k2 = keys[1];
        Archive a2 = getTestArchive(k2, "event1.xml");
        a2.setSent(false);
        storage.set(a2);

        a1.setSent(true);
        storage.set(a1);
        a2.setSent(true);
        storage.set(a2);
        assertEquals(0, storage.getArchived().size());

        a1.setSafeAck(true);
        storage.set(a1);
        a2.setSafeAck(true);
        storage.set(a2);
        assertEquals(0, storage.getArchived().size());
        a1.setSafeArchived(true);
        storage.set(a1);
        assertEquals(1, storage.getArchived().size());

        a2.setSafeArchived(true);
        storage.set(a2);
        assertEquals(2, storage.getArchived().size());

    }

    @Test
    public void testFlushAll() throws Exception {

        final String k1 = keys[0];
        Archive a1 = getTestArchive(k1, "event0.xml");
        storage.set(a1);

        final String k2 = keys[1];
        Archive a2 = getTestArchive(k2, "event1.xml");
        storage.set(a2);

        assertNotNull(storage.get(k1));
        assertNotNull(storage.get(k2));

        storage.flushAll();

        assertNull(storage.get(k1));
        assertNull(storage.get(k2));

    }

    @Test
    public void testDefaultConstructor() throws Exception {
        // Smoke test.
        new StorageServiceImpl();
    }

    @Test
    public void testUnserializationIssues() throws Exception {

        // Insert foreign (non-archives) objects in Redis and verify APIs
        JedisPool pool = new JedisPool(new JedisPoolConfig(), hnp.host,
                hnp.port, 2000);
        Jedis jedis = pool.getResource();
        try {
            jedis.set("Oups", "Non archive");
            jedis.set("Oups2", "AAAA");
        } finally {
            pool.returnResource(jedis);
        }

        assertEquals(0, storage.getUnSentArchives().size());
        assertEquals(0, storage.getByProp("A", "AAA").size());
        assertEquals(0, storage.getArchived().size());
        assertEquals(0, storage.getUnAckArchives().size());
        assertEquals(0, storage.getUnAckArchArchives().size());

        boolean exc = false;
        try {
            storage.get("Oups");
        } catch (StorageException e) {
            exc = true;
        }
        assertTrue(exc);

    }

    @Test
    public void testSet() throws Exception {
        // No exception should raise up.
        storage.set(null);
    }

}
