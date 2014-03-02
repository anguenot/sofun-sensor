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

package org.sofun.sensor.api.routing;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.sofun.sensor.api.SensorException;

/**
 * Routing Service.
 * 
 * <p>
 * 
 * The routing service is responsible to decide if whether or not a request
 * should be extracted and logged in e-safe.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface RoutingService extends Serializable {

    /**
     * Should the request be routed?
     * 
     * <p>
     * 
     * An Apache filter *must* perform an initial routing (rewrite) up front.
     * This method must detect if the request, rewritten by Apache, must be
     * routed at the end. POST versus GET, parameters checks etc.
     * 
     * @param request:a {@link HttpServletRequest} instance
     * @return true or false
     * @throws SensorException
     */
    boolean isRouted(HttpServletRequest request) throws SensorException;

    /**
     * Returns all event types we need to generate depending on the type of
     * request. (i.e. player action)
     * 
     * <p>
     * 
     * Note, depending on the request we may have to generate several one (1) or
     * several ARJEL traces. This is gaming application specific.
     * 
     * @param request: an {@link HttpServletRequest} instance
     * @return an array of event identifiers as {@link String}. Never null
     *         (possibly empty)
     * @throws SensorException
     */
    String[] getEventTypesFor(HttpServletRequest request)
            throws SensorException;

}
