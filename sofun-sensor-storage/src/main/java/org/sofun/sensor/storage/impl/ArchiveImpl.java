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

package org.sofun.sensor.storage.impl;

import java.util.HashMap;
import java.util.Map;

import org.sofun.sensor.storage.api.Archive;

/**
 * Archive implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class ArchiveImpl implements Archive {

    private static final long serialVersionUID = -6229041409460530388L;

    private String key;

    private String xml;

    private boolean sent = false;

    private boolean ack = false;

    private boolean archived = false;

    private Map<String, String> properties = new HashMap<String, String>();

    public ArchiveImpl() {
    }

    public ArchiveImpl(String key, String xml,
            Map<String, String> properties) {
        this.key = key;
        this.xml = xml;
        if (properties != null) {
            this.properties = properties;
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Map<String, String> getProperties() {
        return properties;
    }

    @Override
    public String getXML() {
        return xml;
    }

    @Override
    public boolean isSafeAck() {
        return ack;
    }

    @Override
    public void setSafeAck(boolean ack) {
        this.ack = ack;
    }

    @Override
    public boolean isSafeArchived() {
        return archived;
    }

    @Override
    public void setSafeArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public boolean isSent() {
        return sent;
    }

    @Override
    public void setSent(boolean sent) {
        this.sent = sent;
    }

    @Override
    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public void setProperty(String key, String value) {
        getProperties().put(key, value);
    }

}
