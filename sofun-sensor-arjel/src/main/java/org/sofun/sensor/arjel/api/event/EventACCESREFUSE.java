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

package org.sofun.sensor.arjel.api.event;

/**
 * Event type ACCESREFUSE.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventACCESREFUSE extends XMLTrace {

    static final String TYPE = "ACCESREFUSE";

    public static final String REASON_DELAIINTERDIT = "DelaiInterdit";

    public static final String REASON_REJETIDENTITE = "RejetIdentite";

    public static final String REASON_INTERDIT = "Interdit";

    public static final String REASON_AUTOINTEDIT = "AutoInterdit";

    public static final String REASON_OPVERROUILE = "OpVerrouile";

    public static final String REASON_VERROUILLE = "Verrouille";

    public static final String REASON_CLOTURE = "Cloture";

    public static final String[] REASONS = { REASON_DELAIINTERDIT,
            REASON_REJETIDENTITE, REASON_AUTOINTEDIT, REASON_AUTOINTEDIT,
            REASON_OPVERROUILE, REASON_VERROUILLE, REASON_CLOTURE };

    String getFieldCauseRefus();

    void setFieldCauseRefus(String fieldCauseRefus);

    String getFieldTypeRefus();

    void setFieldTypeRefus(String fieldTypeRefus);

}
