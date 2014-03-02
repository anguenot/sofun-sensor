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
 * Event type PASPGAIN.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventPASPGAIN extends XMLTrace {

    static final String TYPE = "PASPGAIN";

    String getFieldTech();

    void setFieldTech(String fieldTech);

    String getFieldClair();

    void setFieldClair(String fieldClair);

    String getFieldDateHeure();

    void setFieldDateHeure(String fieldDateHeure);

    String getFieldSoldeAvantGain();

    void setFieldSoldeAvantGain(String fieldSoldeAvantGain);

    String getFieldGain();

    void setFieldGain(String fieldGain);

    String getFieldSoldeApresGain();

    void setFieldSoldeApresGain(String fieldSoldeApresGain);

    String getFieldGainAbond();

    void setFieldGainAbond(String fieldGainAbond);

    String getFieldInfo();

    void setFieldInfo(String fieldInfo);

}
