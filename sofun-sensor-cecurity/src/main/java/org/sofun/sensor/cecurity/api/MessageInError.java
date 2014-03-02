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
 * Application level error carried out by the incoming ACK message.
 * 
 * <p>
 * 
 * SSL and other internal errors are not defined here.
 * 
 * @author <a href="mailto:anguenot ">Julien Anguenot</a>
 * 
 */
public final class MessageInError {

    /** Message has been successfully received and will be archived. */
    public static final String OK = "CFJL_OK";

    /** Message is a duplicate of an already sent out event */
    public static final String DUPLICATE_EVENT = "CFJL_DUPLICATE_EVENT";

    /** Message is invalid: missing or wrong headers have been setup */
    public static final String INVALID_MESSAGE = "CFJL_INVALID_MESSAGE";

}
