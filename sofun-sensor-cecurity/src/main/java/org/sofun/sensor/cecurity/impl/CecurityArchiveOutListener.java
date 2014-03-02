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

import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.cecurity.api.ArchiveOutHeaderField;
import org.sofun.sensor.cecurity.api.MessageOutHeaderField;
import org.sofun.sensor.storage.api.Archive;

/**
 * Event Out listener.
 * 
 * <p>
 * 
 * This listener waits for incoming messages from Cecurity that do acknowledge
 * messages archived OK in safe. This message acknowledge a set of messages
 * archived defined in what Cecurity calls a `Lot`. All local copies for all
 * messages in this `Lot` within the Sensor's storage will then be destroyed.
 * 
 * @see CecurityEventOutListener for previous (step) event injection ACK.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public final class CecurityArchiveOutListener extends AbstractCecurityListener {

    private static final Log log = LogFactory
            .getLog(CecurityArchiveOutListener.class);

    @Override
    public void onMessage(Message message) {

        try {

            final long archiveId = message
                    .getLongProperty(ArchiveOutHeaderField.ARCH_ID);
            final long archiveDate = message
                    .getLongProperty(ArchiveOutHeaderField.ARCH_DATE);

            log.info("Received archive message archiveId:" + archiveId
                    + "; archiveDate:" + archiveDate);

            logMessageDetails(message);

            final List<Archive> archives = getStorage().getByProp(
                    String.valueOf(archiveId), MessageOutHeaderField.LOT_ID);
            for (Archive archive : archives) {
                if (archive != null) {
                    archive.setSafeArchived(true);
                    getStorage().set(archive);
                    log.info("Archive with key=" + archive.getKey()
                            + " from lotId=" + archiveId
                            + " marked as archived.");
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
}
