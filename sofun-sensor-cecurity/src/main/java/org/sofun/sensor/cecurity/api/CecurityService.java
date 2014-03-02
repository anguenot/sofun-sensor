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

import java.io.Serializable;

import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.cecurity.api.exception.CecurityException;
import org.sofun.sensor.cecurity.impl.CecurityFailure;
import org.sofun.sensor.storage.api.Archive;

/**
 * Cecurity Service
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface CecurityService extends Serializable {

    /**
     * XML trace pre-processing.
     * 
     * <p>
     * 
     * Handle cecurity e-safe specifics. See implementation details for further
     * information.
     * 
     * @param trace: an {@link XMLTrace} document
     * @return a {@link XMLTrace}
     */
    XMLTrace preProcess(XMLTrace trace);

    /**
     * Send a trace to be stored in e-safe
     * 
     * <p>
     * 
     * Trace *must* not be translated.
     * 
     * @param trace: an {@link XMLTrace} document
     */
    void store(XMLTrace trace) throws CecurityException;

    /**
     * Resends an archive.
     * 
     * <p>
     * 
     * Invoked in case of previous failure to send trace to Cecurity.
     * 
     * @see CecurityFailure#check()
     * 
     * @param archive: an {@link Archive} instance.
     * @throws CecurityException
     */
    void send(Archive archive) throws CecurityException;

    /**
     * Fixtures
     */
    void init();

}
