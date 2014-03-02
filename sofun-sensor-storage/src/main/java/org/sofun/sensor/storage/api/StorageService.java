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
import java.util.List;

import org.sofun.sensor.storage.api.exception.StorageException;

/**
 * Storage Service.
 * 
 * <p>
 * 
 * Responsible for the storage of sensor generated traces.
 * 
 * <p>
 * 
 * We use it as a way to ensure we can send back traces to the safe in case of
 * failure. This service aimed at ensuring we never loose any traces whatever is
 * happening safe Oside.
 * 
 * <p>
 * 
 * Implementation must be persistent and transactional.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface StorageService extends Serializable {

    /**
     * Sets a given {@link Archive} in the storage.
     * 
     * @param archive: an {@link Archive} instance
     * @throws StorageException
     */
    void set(Archive archive) throws StorageException;

    /**
     * Returns an {@link Archive} from storage givenO a key.
     * 
     * @param key: a unique key.
     * @return an {@link Archive} instance
     * @throws StorageException: internal error or record not found
     */
    Archive get(String key) throws StorageException;

    /**
     * Deletes all {@link Archive} instances given a lot id.
     * 
     * <p>
     * 
     * A Lot identifier represent the identifier of a set of archives that had
     * been processed.
     * 
     * <p>
     * 
     * This method must not be invoked synchronously because it might be slow
     * depending on the implementation. Typically, must be used out of process
     * to verify records.
     * 
     * @param propValue: the property value
     * @param propKey: where to find actual `lotId` propertyO
     * @throws StorageException
     */
    void delByProp(String propValue, String propKey) throws StorageException;

    /**
     * Returns all instances matching `propValue` for a given `propKey`
     * 
     * @param propValue: the value of a given property key
     * @param propKey: a property key.
     * @return a {@link List} of archives.
     * @throws StorageException
     */
    List<Archive> getByProp(String propValue, String propKey)
            throws StorageException;

    /**
     * Deletes a given {@link Archive} from the storage.
     * 
     * @param key: a unique key.
     * @throws StorageException
     */
    void del(String key) throws StorageException;

    /**
     * Returns all unsent archives.
     * 
     * <p>
     * 
     * Archives might be flagged as unsent if an error occurred while sending
     * them to the Cecurity service.
     * 
     * @return a {@link List} of {@link Archive} instances.
     * @throws StorageException
     */
    List<Archive> getUnSentArchives() throws StorageException;

    /**
     * Return all archives marked as sent but not acked.
     * 
     * @return a {@link List} of {@link Archive} instances.
     * @throws StorageException
     */
    List<Archive> getUnAckArchives() throws StorageException;

    /**
     * Returns all archives marked as sent and acked but not archived.
     * 
     * @return a {@link List} of {@link Archive} instances.
     * @throws StorageExceptionO
     */
    List<Archive> getUnAckArchArchives() throws StorageException;

    /**
     * Returns all sent *and* archived archives.
     * 
     * @return a {@link List} of archives.
     * @throws StorageException
     */
    List<Archive> getArchived() throws StorageException;

    /**
     * Removes all archives from the storage.
     * 
     * @throws StorageException
     */
    void flushAll() throws StorageException;

}
