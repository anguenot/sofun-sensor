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

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.storage.api.Archive;
import org.sofun.sensor.storage.api.StorageService;
import org.sofun.sensor.storage.api.ejb.StorageServiceLocal;
import org.sofun.sensor.storage.api.exception.StorageException;

/**
 * Archives cleanup.
 * 
 * <p>
 * 
 * This singleton watches for sent, ack and archived archived and purges.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Singleton
public class ArchiveCleanup {

    private static final Log log = LogFactory.getLog(ArchiveCleanup.class);

    /** Lock in case computing takes more than 5 minutes */
    private boolean available = true;

    @EJB(
            beanName = "StorageServiceImpl",
            beanInterface = StorageServiceLocal.class)
    private StorageService storage;

    public ArchiveCleanup() {
    }

    public ArchiveCleanup(StorageService storage) {
        this.storage = storage;
    }

    @Timeout
    @Schedule(minute = "*/5", hour = "*", persistent = false)
    @Lock(LockType.READ)
    public void check() throws StorageException {
        if (!available) {
            return;
        } else {
            available = false;
        }
        try {
            List<Archive> archives = storage.getArchived();
            final int s = archives.size();
            if (s > 0) {
                log.info("Found "
                        + s
                        + " archives that can be purged (sent, ack and archived)"
                        + ". Processing.");
                int i = 1;
                for (Archive each : archives) {
                    storage.del(each.getKey());
                    log.info("Archive (key=" + each.getKey() + ")" + i + " of "
                            + s + " has been succefully purged.");
                    i++;
                }
            }
        } catch (Exception e) {
            throw new StorageException(e);
        } finally {
            available = true;
        }
    }

}
