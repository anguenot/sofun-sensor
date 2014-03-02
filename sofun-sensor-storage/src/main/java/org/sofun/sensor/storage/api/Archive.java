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

package org.sofun.sensor.storage.api;

import java.io.Serializable;
import java.util.Map;

/**
 * Archive.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface Archive extends Serializable {

    /**
     * Returns the archive unique key.
     * 
     * @return a {@link String} unique key
     */
    String getKey();

    /**
     * Returns the archive's properties.
     * 
     * @return a {@link Map} from prop key to value
     */
    Map<String, String> getProperties();

    /**
     * Sets the archive's properties.
     * 
     * @param properties: a {@link Map} from prop key to value
     */
    void setProperties(Map<String, String> properties);

    /**
     * Set an archive property.
     * 
     * @param key
     * @param value
     */
    void setProperty(String key, String value);

    /**
     * Returns the actual XML content
     * 
     * @return a {@link String}
     */
    String getXML();

    /**
     * Has it been sent out to the safe yet?
     * 
     * @return true of false
     */
    boolean isSent();

    /**
     * Sets the sent status.
     * 
     * @param sent: true or false
     */
    void setSent(boolean sent);

    /**
     * Has it been injected in safe yet?
     * 
     * @return true or false
     */
    boolean isSafeAck();

    /**
     * Sets the injection status
     * 
     * @param ack: true or false
     */
    void setSafeAck(boolean ack);

    /**
     * Has it been archived by the safe yet?
     * 
     * @return true or false
     */
    boolean isSafeArchived();

    /**
     * Sets the archive status.
     * 
     * @param archived: true or false
     */
    void setSafeArchived(boolean archived);

}
