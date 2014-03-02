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

package org.sofun.sensor.cecurity.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Timeout;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.cecurity.api.CecurityService;
import org.sofun.sensor.cecurity.api.ejb.CecurityServiceLocal;
import org.sofun.sensor.cecurity.api.exception.CecurityException;
import org.sofun.sensor.storage.api.Archive;
import org.sofun.sensor.storage.api.StorageService;
import org.sofun.sensor.storage.api.ejb.StorageServiceLocal;
import org.sofun.sensor.storage.api.exception.StorageException;

/**
 * Cecurity failures.
 * 
 * <p>
 * 
 * This singleton watches for failed attempt to send traces and resent messages
 * out.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Singleton
public class CecurityFailure {

    private static final Log log = LogFactory.getLog(CecurityFailure.class);

    /** Lock in case computing takes more than 5 minutes */
    private boolean available = true;

    @EJB(
            beanName = "StorageServiceImpl",
            beanInterface = StorageServiceLocal.class)
    private StorageService storage;

    @EJB(
            beanName = "CecurityServiceImpl",
            beanInterface = CecurityServiceLocal.class)
    private CecurityService cecurity;

    /**
     * Checks for unsent (failed) messages on regular basis.
     * 
     * <p>
     * 
     * This might happen if a network problem occurred or of the safe has
     * experienced issues.
     * 
     */
    @Timeout
    @Schedule(minute = "*/5", hour = "*", persistent = false)
    @Lock(LockType.READ)
    public void check() throws StorageException, CecurityException {
        if (!available) {
            return;
        } else {
            available = false;
        }
        try {
            // Unsent archives.
            List<Archive> archives = storage.getUnSentArchives();
            int s = archives.size();
            if (s > 0) {
                log.info("Found " + s
                        + " archives not sent to Cecurity. Processing.");
                int i = 1;
                for (Archive each : archives) {
                    cecurity.send(each);
                    log.info("Archive (key=" + each.getKey() + ")" + i + " of "
                            + s + " has been resend successfully.");
                    i++;
                }
            }
            // Sent but not ACK archived.
            archives = storage.getUnAckArchives();
            s = archives.size();
            if (s > 0) {
                log.info("Found " + s
                        + " archives not ACK by Cecurity. Processing.");
                int i = 1;
                for (Archive each : archives) {
                    cecurity.send(each);
                    log.info("Archive (key=" + each.getKey() + ")" + i + " of "
                            + s + " has been resend successfully.");
                    i++;
                }
            }
            // Sent, ACK but not archived.
            archives = storage.getUnAckArchArchives();
            s = archives.size();
            if (s > 0) {
                log.info("Found " + s
                        + " archives not ACKED as ARCHIVED by Cecurity. "
                        + "Processing.");
                int i = 1;
                for (Archive each : archives) {
                    cecurity.send(each);
                    log.info("Archive (key=" + each.getKey() + ")" + i + " of "
                            + s + " has been resend successfully.");
                    i++;
                }
            }
        } finally {
            available = true;
        }
    }

}
