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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import biz.source_code.base64Coder.Base64Coder;

/**
 * Archive persistence mechanism.
 * 
 * <p>
 * 
 * Used by the storage to serialize and deserialize {@link Archive} instances.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public final class ArchivePersistence {

    // "Coverage As" trick to get 100% w/ static classes
    static {
        new ArchivePersistence();
    }

    private ArchivePersistence() {
        // Static class
    }

    /**
     * Serialize a {@link Archive} instance.
     * 
     * @param o: an {@link Archive} instance
     * @return a Base64 encoded {@link String} representation of the given
     *         {@link Archive} instance.
     * @throws IOException
     */
    public static String toString(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return new String(Base64Coder.encode(baos.toByteArray()));
    }

    /**
     * Deserialize {@link Archive} instance.
     * 
     * @param s: a Base64 encoded {@link String} representation of a given
     *        {@link Archive} instance.
     * @return a {@link Archive} instance
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object fromString(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64Coder.decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(
                data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }

}
