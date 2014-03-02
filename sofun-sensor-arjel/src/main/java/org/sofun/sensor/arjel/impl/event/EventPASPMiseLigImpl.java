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

package org.sofun.sensor.arjel.impl.event;

import org.sofun.sensor.arjel.api.event.EventPASPMiseLig;

/**
 * Event type PASPMISE, PASPMiseLig element implementation
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class EventPASPMiseLigImpl implements EventPASPMiseLig {

    private static final long serialVersionUID = 8775233942005447130L;

    private String fieldNo;

    private String fieldDateHeure;

    private String fieldTech;

    private String fieldClair;

    private String fieldType;

    private String fieldPariChoix;

    private String fieldPariCote;

    public EventPASPMiseLigImpl(String fieldNo, String fieldDateHeure,
            String fieldTech, String fieldClair, String fieldType,
            String fieldPariChoix, String fieldPariCote) {
        this.fieldNo = fieldNo;
        this.fieldDateHeure = fieldDateHeure;
        this.fieldTech = fieldTech;
        this.fieldClair = fieldClair;
        this.fieldType = fieldType;
        this.fieldPariChoix = fieldPariChoix;
        this.fieldPariCote = fieldPariCote;
    }

    @Override
    public String getFieldNo() {
        return fieldNo;
    }

    @Override
    public void setFieldNo(String fieldNo) {
        this.fieldNo = fieldNo;
    }

    @Override
    public String getFieldDateHeure() {
        return fieldDateHeure;
    }

    @Override
    public void setFieldDateHeure(String fieldDateHeure) {
        this.fieldDateHeure = fieldDateHeure;
    }

    @Override
    public String getFieldTech() {
        return fieldTech;
    }

    @Override
    public void setFieldTech(String fieldTech) {
        this.fieldTech = fieldTech;
    }

    @Override
    public String getFieldClair() {
        return fieldClair;
    }

    @Override
    public void setFieldClair(String fieldClair) {
        this.fieldClair = fieldClair;
    }

    @Override
    public String getFieldType() {
        return fieldType;
    }

    @Override
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String getFieldPariChoix() {
        return fieldPariChoix;
    }

    @Override
    public void setFieldPariChoix(String fieldPariChoix) {
        this.fieldPariChoix = fieldPariChoix;
    }

    @Override
    public String getFieldPariCote() {
        return fieldPariCote;
    }

    @Override
    public void setFieldPariCote(String fieldPariCote) {
        this.fieldPariCote = fieldPariCote;
    }

}
