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

import java.io.Serializable;

/**
 * Event type PASPMISE `Lig` Element constants.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventPASPMiseLig extends Serializable {

    public static final String No = "No";

    public static final String DateHeure = "DateHeure";

    public static final String Tech = "Tech";

    public static final String Clair = "Clair";

    public static final String Type = "Type";

    public static final String Pari = "Pari";

    public static final String PariChoix = "Choix";

    public static final String PariCote = "Cote";

    String getFieldNo();

    void setFieldNo(String fieldNo);

    String getFieldDateHeure();

    void setFieldDateHeure(String fieldDateHeure);

    String getFieldTech();

    void setFieldTech(String fieldTech);

    String getFieldClair();

    void setFieldClair(String fieldClair);

    String getFieldType();

    void setFieldType(String fieldType);

    String getFieldPariChoix();

    void setFieldPariChoix(String fieldPariChoix);

    String getFieldPariCote();

    void setFieldPariCote(String fieldPariCote);

}
