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

package org.sofun.sensor.web;

import java.io.IOException;

import javax.servlet.ServletInputStream;

/**
 * Sensor Servlet Input Stream
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class SensorServletInputStream extends ServletInputStream {

    private final byte[] data;

    private int idx = 0;

    public SensorServletInputStream(byte[] data) {
        if (data == null)
            data = new byte[0];
        this.data = data;
    }

    @Override
    public int read() throws IOException {
        if (idx == data.length)
            return -1;
        return data[idx++];
    }

}
