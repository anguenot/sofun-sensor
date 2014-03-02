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
 * Event type PREFCPTE.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventPREFCPTE extends XMLTrace {

    static final String TYPE = "PREFCPTE";

    String getFieldTypAg();

    void setFieldTypAg(String fieldTypAg);

    String getFieldCompteMin();

    void setFieldCompteMin(String fieldCompteMin);

    String getFieldCompteMax();

    void setFieldCompteMax(String fieldCompteMax);

    String getFieldMiseMaxMontantMM();

    void setFieldMiseMaxMontantMM(String fieldMiseMaxMontantMM);

    String getFieldMiseMaxPeriodeMM();

    void setFieldMiseMaxPeriodeMM(String fieldMiseMaxPeriodeMM);

    String getFieldMiseMaxTypeJeuMM();

    void setFieldMiseMaxTypeJeuMM(String fieldMiseMaxTypeJeuMM);

    String getFieldDepotMaxMontantMM();

    void setFieldDepotMaxMontantMM(String fieldDepotMaxMontantMM);

    String getFieldDepotMaxPeriodeMM();

    void setFieldDepotMaxPeriodeMM(String fieldDepotMaxPeriodeMM);

    String getFieldDepotMaxTypeJeuMM();

    void setFieldDepotMaxTypeJeuMM(String fieldDepotMaxTypeJeuMM);

}
