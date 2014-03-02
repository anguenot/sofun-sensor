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
 * JMS Message headers for outgoing messages.
 * 
 * <p>
 * 
 * Each JMS message to be sent out must includes the following properties. Case
 * matters.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public final class MessageInHeaderField {

    /** `SHA-1`, `SHA-256`, `SHA-384` or XML Digital Signature identifier */
    public static final String DIGEST_ALGO = "DigestAlgo";

    /** Sensor generated identifier */
    public static final String CLIENT_ID = "ClientId";

    /** Hexa encoded hash of the payload (binary) */
    public static final String JH_DIGEST = "Digest";

    /** Sensor generated timestamp related to event */
    public static final String TIME_STAMP = "Timestamp";

}
