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

import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.cecurity.api.MessageInError;
import org.sofun.sensor.cecurity.api.MessageOutHeaderField;
import org.sofun.sensor.storage.api.Archive;

/**
 * Event Out listener.
 * 
 * <p>
 * 
 * This listener waits for incoming messages from Cecurity that do acknowledge
 * outgoing messages sent by the sensor. It will then keep track of properties
 * and check status as reported by the safe. Stored copy's meta data will be
 * updated sensor's side while waiting for the Archive.<N>.out message
 * confirming archive operation status next.
 * 
 * @see CecurityArchiveOutListener for archive operation ACK.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public final class CecurityEventOutListener extends AbstractCecurityListener {

    private static final Log log = LogFactory
            .getLog(CecurityEventOutListener.class);

    @Override
    public void onMessage(Message message) {

        try {

            final String status = message
                    .getStringProperty(MessageOutHeaderField.CFJLSTATUS);
            final long idLot = message
                    .getLongProperty(MessageOutHeaderField.LOT_ID);
            final String clientId = message
                    .getStringProperty(MessageOutHeaderField.CLIENTID);
            final String digest = message
                    .getStringProperty(MessageOutHeaderField.DIGEST);
            final String digestAlgo = message
                    .getStringProperty(MessageOutHeaderField.DIGEST_ALGO);
            final String jelSerial = message
                    .getStringProperty(MessageOutHeaderField.JELSERIAL);

            log.info("Received message id:" + clientId + "; status:" + status
                    + "; lot id:" + idLot + " ; CJFL serial: " + jelSerial);

            logMessageDetails(message);

            if (MessageInError.DUPLICATE_EVENT.equals(status)) {
                log.error("Event marked as DUPLICATE by Cecurity."
                        + " Something went wrong sensor side...");
                log.info("Removing duplicate event if not done already");
                getStorage().del(clientId);
            } else if (MessageInError.INVALID_MESSAGE.equals(status)) {
                log.error("Event marked as INVALID by Cecurity. "
                        + "Something went VERY wrong sensor side..."
                        + " Manual cleanup required.");
            } else if (MessageInError.OK.equals(status)) {
                log.debug("Event marked as OK by Cecurity.");
            } else {
                log.warn("Event marked with an UNKNOWN status by Cecurity."
                        + " status=" + status);
            }

            Archive archive = getStorage().get(clientId);
            if (archive != null) {
                log.debug("Marking Archive with key=" + archive.getKey()
                        + " as acknowledge by Cecurity. Updating properties.");
                archive.setProperty(MessageOutHeaderField.CFJLSTATUS, status);
                archive.setProperty(MessageOutHeaderField.LOT_ID,
                        String.valueOf(idLot));
                archive.setProperty(MessageOutHeaderField.CLIENTID, clientId);
                archive.setProperty(MessageOutHeaderField.DIGEST, digest);
                archive.setProperty(MessageOutHeaderField.DIGEST_ALGO,
                        digestAlgo);
                archive.setProperty(MessageOutHeaderField.JELSERIAL, jelSerial);
                if (MessageInError.OK.equals(status)) {
                    archive.setSafeAck(true);
                }
                getStorage().set(archive);
            } else {
                if (MessageInError.OK.equals(status)) {
                    log.error("We have a *BIG* problem: cannot find message with key="
                            + clientId
                            + "  within sensor storage. That should never be happening");
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

    }
}
