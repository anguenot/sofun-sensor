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
 * Event type CPTEALIMOPE.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventCPTEALIMOPE extends XMLTrace {

    static final String TYPE = "CPTEALIMOPE";

    String getFieldTypAg();

    void setFieldTypAg(String fieldTypAg);

    String getFieldBonusAvant();

    void setFieldBonusAvant(String fieldBonusAvant);

    String getFieldBonusApres();

    void setFieldBonusApres(String fieldBonusApres);

    String getFieldBonusMouvement();

    void setFieldBonusMouvement(String fieldBonusMouvement);

    String getFieldBonusNom();

    void setFieldBonusNom(String fieldBonusNom);

}
