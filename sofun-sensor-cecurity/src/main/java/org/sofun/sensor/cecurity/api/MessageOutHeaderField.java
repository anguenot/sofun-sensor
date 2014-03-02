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

package org.sofun.sensor.cecurity.api;

/**
 * JMS Message headers for incoming ACK messages.
 * 
 * <p>
 * 
 * Each JMS message to be received includes the following properties. Case
 * matters.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public final class MessageOutHeaderField {

    /** Response request status */
    public static final String CFJLSTATUS = "CFJLStatus";

    /** Hexa encoded hash of archived event including its O */
    public static final String DIGEST = "CFJLDigest";

    /** `SHA-1`, `SHA-256`, `SHA-384` or XML Digital Signature identifier */
    public static final String DIGEST_ALGO = "CFJLDigestAlgo";

    /** Unique sequential event identifier */
    public static final String JELSERIAL = "CFJLJELSerial";

    /** Lot unique identifier part of which the event had been archived */
    public static final String LOT_ID = "CFJLLotId";

    /** Sensor generated identifier for this event */
    public static final String CLIENTID = "ClientId";

}
