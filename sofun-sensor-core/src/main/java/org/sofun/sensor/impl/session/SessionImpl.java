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

package org.sofun.sensor.impl.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.sofun.sensor.api.session.Session;
import org.sofun.sensor.arjel.api.event.XMLTrace;

/**
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class SessionImpl implements Session {

    private static final long serialVersionUID = -6411366076681019992L;

    private static final int TTL = 5 * 60 * 3600;

    private String key;

    private Date created;

    private Date expire;

    private String playerIP;

    private final List<XMLTrace> traces = new ArrayList<XMLTrace>();

    public SessionImpl() {
        Calendar cal = Calendar.getInstance();
        this.created = cal.getTime();
        cal.add(Calendar.MILLISECOND, TTL);
        this.expire = cal.getTime();
    }

    public SessionImpl(String key) {
        this();
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public Date getExpire() {
        if (expire != null) {
            return (Date) expire.clone();
        }
        return null;
    }

    @Override
    public void setExpire(Date expire) {
        if (expire != null) {
            this.expire = (Date) expire.clone();
        } else {
            this.expire = null;
        }
    }

    @Override
    public Date getCreated() {
        if (created != null) {
            return (Date) created.clone();
        }
        return null;
    }

    @Override
    public void setCreated(Date created) {
        if (created != null) {
            this.created = (Date) created.clone();
        } else {
            this.created = null;
        }
    }

    @Override
    public String getPlayerIP() {
        return playerIP;
    }

    @Override
    public void addTrace(XMLTrace trace) {
        traces.add(trace);
    }

    @Override
    public XMLTrace[] getTraces() {
        final XMLTrace[] array = new XMLTrace[traces.size()];
        return traces.toArray(array);
    }

    @Override
    public void delTraces() {
        traces.clear();
    }

    @Override
    public void delTrace(XMLTrace trace) {
        traces.remove(trace);
    }

    @Override
    public void setPlayerIP(String playerIP) {
        this.playerIP = playerIP;

    }

}
