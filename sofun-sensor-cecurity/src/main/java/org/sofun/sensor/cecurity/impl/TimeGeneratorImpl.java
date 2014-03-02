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

package org.sofun.sensor.cecurity.impl;

import java.util.Date;

import org.sofun.sensor.cecurity.api.TimeGenerator;

/**
 * Simple Time Generator for outgoing messages.
 * 
 * <p>
 * 
 * This is the sensor's responsibility
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TimeGeneratorImpl implements TimeGenerator {

    @Override
    public Date getTime() {
        return new Date();
    }

}
