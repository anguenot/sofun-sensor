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

package org.sofun.sensor.arjel.api;

import java.io.Serializable;
import java.util.Map;

import org.sofun.sensor.arjel.api.event.XMLTrace;

/**
 * ARJEL Service.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface ARJELService extends Serializable {

    /**
     * Returns the unique identifier as provided by ARJEL. Uniqueness is
     * guaranteed by ARJEL in between operators.
     * 
     * <p>
     * 
     * Note in case of multiple safes, this identifier must remain the same.
     * 
     * @return a {@link String}
     */
    String getOperatorID();

    /**
     * Returns the identifier of the operator's safe. The first safe starts at
     * '1', the second '2' etc.
     * 
     * @return a {@link String}
     */
    String getSafeID();

    /**
     * Returns the type of agreement.
     * 
     * <p>
     * 
     * We only support one (1) type of agreement per sensor instance right now.
     * 
     * @return a {@link String} matching to ARJEL DET specifications.
     */
    String getTypeAg();

    /**
     * Returns an {@link XMLTrace} instance given an even type and parameters.
     * 
     * @param eventType: a {@link String}
     * @param playerIP: player remote IP address
     * @param sessionID: sensor session identifier
     * @param params: {@link Map} from key to value. Key is a field type.
     * @return an {@link XMLTrace} instance
     */
    XMLTrace getXMLTrace(String eventType, String playerIP, String sessionID,
            Map<String, String> params);
}
