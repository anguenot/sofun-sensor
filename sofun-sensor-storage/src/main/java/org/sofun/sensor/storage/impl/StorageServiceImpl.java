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

package org.sofun.sensor.storage.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.storage.api.Archive;
import org.sofun.sensor.storage.api.ArchivePersistence;
import org.sofun.sensor.storage.api.StorageService;
import org.sofun.sensor.storage.api.ejb.StorageServiceLocal;
import org.sofun.sensor.storage.api.ejb.StorageServiceRemote;
import org.sofun.sensor.storage.api.exception.StorageException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Storage Service.
 * 
 * <p>
 * 
 * This implementation uses Redis as a key value persistent store.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Stateless
@Local(StorageServiceLocal.class)
@Remote(StorageServiceRemote.class)
public class StorageServiceImpl implements StorageService {

    private static final long serialVersionUID = -3663890617192260547L;

    private static final Log log = LogFactory.getLog(StorageServiceImpl.class);

    /** Thread-safe Jedis static pool */
    private static JedisPool pool = new JedisPool(new JedisPoolConfig(),
            "localhost");

    public StorageServiceImpl() {
    }

    public StorageServiceImpl(String redisHost, int redisPort) {
        StorageServiceImpl.setupPool(redisHost, redisPort);
    }

    public static void setupPool(String redisHost, int redisPort) {
        pool = new JedisPool(new JedisPoolConfig(), redisHost, redisPort, 2000);
    }

    @Override
    public void set(final Archive archive) throws StorageException {
        if (archive == null) {
            log.warn("Cannot store a null archive");
            return;
        }
        Jedis jedis = pool.getResource();
        try {
            jedis.set(archive.getKey(), ArchivePersistence.toString(archive));
            log.debug("Archive stored with key=" + archive.getKey());
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public Archive get(final String key) throws StorageException {
        Jedis jedis = pool.getResource();
        try {
            final String value = jedis.get(key);
            if (value == null) {
                return null;
            }
            Archive archive = (Archive) ArchivePersistence.fromString(value);
            log.debug("Retrieved from storage archive with key=" + key);
            return archive;
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void del(String key) throws StorageException {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(key);
            log.debug("Element with key=" + key + " has been deleted");
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public void delByProp(String propValue, String propKey)
            throws StorageException {
        if (propValue == null || propKey == null) {
            return;
        }
        Jedis jedis = pool.getResource();
        try {
            // XXX enhance -> optimization
            for (Archive archive : getByProp(propValue, propKey)) {
                jedis.del(archive.getKey());
                log.debug("Deleting archive with key=" + archive.getKey()
                        + " as part of lotId=" + propValue);
            }
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public List<Archive> getUnSentArchives() throws StorageException {
        List<Archive> results = new ArrayList<Archive>();
        Jedis jedis = pool.getResource();
        try {
            // XXX enhance -> optimization
            for (String key : jedis.keys("*")) {
                final String value = jedis.get(key);
                if (value == null) {
                    log.debug("Value is null for key=" + key);
                    continue;
                }
                Archive archive = null;
                try {
                    archive = (Archive) ArchivePersistence.fromString(value);
                } catch (Exception e) {
                    log.warn("Cannot deserialize archive with key=" + key
                            + " and value=" + value + " is it an archive?");
                    continue;
                }
                if (!archive.isSent()) {
                    results.add(archive);
                }
            }
        } finally {
            pool.returnResource(jedis);
        }
        return results;
    }

    @Override
    public void flushAll() throws StorageException {
        Jedis jedis = pool.getResource();
        try {
            jedis.flushAll();
            log.info("Flushing all archives from storage.");
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override
    public List<Archive> getByProp(String propValue, String propKey)
            throws StorageException {
        List<Archive> results = new ArrayList<Archive>();
        Jedis jedis = pool.getResource();
        try {
            // XXX enhance -> optimization
            for (String key : jedis.keys("*")) {
                final String value = jedis.get(key);
                if (value == null) {
                    log.debug("Value is null for key=" + key);
                    continue;
                }
                Archive archive = null;
                try {
                    archive = (Archive) ArchivePersistence.fromString(value);
                } catch (Exception e) {
                    log.warn("Cannot deserialize archive with key=" + key
                            + " and value=" + value + " is it an archive?");
                    continue;
                }
                final String archiveLotId = archive.getProperties()
                        .get(propKey);
                if (propValue.equals(archiveLotId)) {
                    results.add(archive);
                }
            }
        } finally {
            pool.returnResource(jedis);
        }
        return results;
    }

    @Override
    public List<Archive> getArchived() throws StorageException {
        List<Archive> results = new ArrayList<Archive>();
        Jedis jedis = pool.getResource();
        try {
            // XXX enhance -> optimization
            for (String key : jedis.keys("*")) {
                final String value = jedis.get(key);
                if (value == null) {
                    log.debug("Value is null for key=" + key);
                    continue;
                }
                Archive archive = null;
                try {
                    archive = (Archive) ArchivePersistence.fromString(value);
                } catch (Exception e) {
                    log.warn("Cannot deserialize archive with key=" + key
                            + " and value=" + value + " is it an archive?");
                    continue;
                }
                if (archive.isSafeArchived()) {
                    results.add(archive);
                }
            }
        } finally {
            pool.returnResource(jedis);
        }
        return results;
    }

    @Override
    public List<Archive> getUnAckArchives() throws StorageException {
        List<Archive> results = new ArrayList<Archive>();
        Jedis jedis = pool.getResource();
        try {
            // XXX enhance -> optimization
            for (String key : jedis.keys("*")) {
                final String value = jedis.get(key);
                if (value == null) {
                    log.debug("Value is null for key=" + key);
                    continue;
                }
                Archive archive = null;
                try {
                    archive = (Archive) ArchivePersistence.fromString(value);
                } catch (Exception e) {
                    log.warn("Cannot deserialize archive with key=" + key
                            + " and value=" + value + " is it an archive?");
                    continue;
                }
                if (archive.isSent() && !archive.isSafeAck()) {
                    results.add(archive);
                }
            }
        } finally {
            pool.returnResource(jedis);
        }
        return results;
    }

    @Override
    public List<Archive> getUnAckArchArchives() throws StorageException {
        List<Archive> results = new ArrayList<Archive>();
        Jedis jedis = pool.getResource();
        try {
            // XXX enhance -> optimization
            for (String key : jedis.keys("*")) {
                final String value = jedis.get(key);
                if (value == null) {
                    log.debug("Value is null for key=" + key);
                    continue;
                }
                Archive archive = null;
                try {
                    archive = (Archive) ArchivePersistence.fromString(value);
                } catch (Exception e) {
                    log.warn("Cannot deserialize archive with key=" + key
                            + " and value=" + value + " is it an archive?");
                    continue;
                }
                if (archive.isSafeAck() && !archive.isSafeArchived()) {
                    results.add(archive);
                }
            }
        } finally {
            pool.returnResource(jedis);
        }
        return results;
    }

}
