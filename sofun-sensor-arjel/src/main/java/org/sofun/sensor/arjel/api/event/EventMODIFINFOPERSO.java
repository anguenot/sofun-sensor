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
 * Event type MODIFINFOPERSO.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventMODIFINFOPERSO extends XMLTrace {

    static final String TYPE = "MODIFINFOPERSO";

    String getFieldTypAg();

    void setFieldTypAg(String fieldTypAg);

    String getFieldPseudo();

    void setFieldPseudo(String fieldPseudo);

    String getFieldNom();

    void setFieldNom(String fieldNom);

    String getFieldPrenoms();

    void setFieldPrenoms(String fieldPrenoms);

    String getFieldCivilite();

    void setFieldCivilite(String fieldCivilite);

    String getFieldAd();

    void setFieldAd(String fieldAd);

    String getFieldCP();

    void setFieldCP(String fieldCP);

    String getFieldVille();

    void setFieldVille(String fieldVille);

    String getFieldPays();

    void setFieldPays(String fieldPays);

    String getFieldTelFixe();

    void setFieldTelFixe(String fieldTelFixe);

    String getFieldTelMob();

    void setFieldTelMob(String fieldTelMob);

    String getFieldEmail();

    void setFieldEmail(String fieldEmail);

}
