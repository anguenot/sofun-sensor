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

import java.util.List;

/**
 * Event type PASPMISE.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventPASPMISE extends XMLTrace {

    static final String TYPE = "PASPMISE";

    String getFieldTech();

    void setFieldTech(String fieldTech);

    String getFieldClair();

    void setFieldClair(String fieldClair);

    String getFieldSoldeAvantMise();

    void setFieldSoldeAvantMise(String fieldSoldeAvantMise);

    String getFieldMise();

    void setFieldMise(String fieldMise);

    String getFieldSoldeApresMise();

    void setFieldSoldeApresMise(String fieldSoldeApresMise);

    String getFieldMiseAbond();

    void setFieldMiseAbond(String fieldMiseAbond);

    String getFieldBonusAvantMise();

    void setFieldBonusAvantMise(String fieldBonusAvantMise);

    String getFieldBonusMouvement();

    void setFieldBonusMouvement(String fieldBonusMouvement);

    String getFieldBonusApresMise();

    void setFieldBonusApresMise(String fieldBonusApresMise);

    String getFieldBonusNom();

    void setFieldBonusNom(String fieldBonusNom);

    String getFieldCombi();

    void setFieldCombi(String fieldCombi);

    List<EventPASPMiseLig> getEventPASPMiseLigs();

    void setEventPASPMiseLigs(List<EventPASPMiseLig> ligs);

    void addEventPASPMiseLig(EventPASPMiseLig lig);

}
