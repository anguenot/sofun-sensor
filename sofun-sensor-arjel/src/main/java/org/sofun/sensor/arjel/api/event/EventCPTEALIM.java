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
 * Event type CPTEALIM.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventCPTEALIM extends XMLTrace {

    static final String TYPE = "CPTEALIM";

    String getFieldTypAg();

    void setFieldTypAg(String fieldTypAg);

    String getFieldDateDemande();

    void setFieldDateDemande(String fieldDateDemande);

    String getFieldDateEffective();

    void setFieldDateEffective(String fieldDateEffective);

    String getFieldSoldeAvant();

    void setFieldSoldeAvant(String fieldSoldeAvant);

    String getFieldSoldeMouvement();

    void setFieldSoldeMouvement(String fieldSoldeMouvement);

    String getFieldSoldeApres();

    void setFieldSoldeApres(String fieldSoldeApres);

    String getFieldMoyenPaiement();

    void setFieldMoyenPaiement(String fieldMoyenPaiement);

    String getFieldTypeMoyenPaiement();

    void setFieldTypeMoyenPaiement(String fieldTypeMoyenPaiement);

}
