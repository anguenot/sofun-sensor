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

package org.sofun.sensor.api.session;

import java.io.Serializable;
import java.util.Date;

import org.sofun.sensor.arjel.api.event.XMLTrace;

/**
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface Session extends Serializable {

    String getKey();

    void setKey(String key);

    Date getCreated();

    void setCreated(Date created);

    Date getExpire();

    void setExpire(Date expire);

    String getPlayerIP();

    void setPlayerIP(String playerIP);

    void addTrace(XMLTrace trace);

    XMLTrace[] getTraces();

    void delTraces();

    void delTrace(XMLTrace trace);

}
