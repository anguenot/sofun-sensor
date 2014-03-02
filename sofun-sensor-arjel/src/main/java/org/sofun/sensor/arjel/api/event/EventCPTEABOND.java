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
 * Event type CPTEABOND.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public interface EventCPTEABOND extends XMLTrace {

    static final String TYPE = "CPTEABOND";
    
    static final String TypeAbondementOuverture = "Ouverture";
    
    static final String TypeAbondementRakeBack = "RakeBack";
    
    static final String TypeAbondementHautFait = "HautFait";
    
    static final String TypeAbondementCode = "Code";
    
    static final String TypeAbondementOffre = "Offre";
    
    String getFieldTypAg();

    void setFieldTypAg(String fieldTypAg);
    
    String getFieldSoldeAvant();

    void setFieldSoldeAvant(String fieldSoldeAvant);
    
    String getFieldMontAbond();

    void setFieldMontAbond(String fieldMontAbond);
    
    String getFieldSoldeApres();

    void setFieldSoldeApres(String fieldSoldeApres);
    
    String getFieldInfo();

    void setFieldInfo(String fieldInfo);
    
    String getFieldTypeAbondement();

    void setFieldTypeAbondement(String fieldTypeAbondement);
    
    

}
