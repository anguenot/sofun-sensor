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

package org.sofun.sensor.arjel.test;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * Abstract ARJEL Test Case.
 * 
 * <p>
 * 
 * Bunch of utilities for ARJEL XML events test cases.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public abstract class AbstractARJELTestCase extends TestCase {

    protected Document getDocument(String path) throws Exception {
        SAXReader reader = new SAXReader();
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(path);
        File file = new File(url.getFile());
        return reader.read(file);
    }

    protected String getRootXMLFrom(String path) throws Exception {
        Document document = getDocument(path);
        return document.getRootElement().asXML();
    }

    protected String getXMLFrom(String path) throws Exception {
        Document document = getDocument(path);
        return document.asXML();
    }

}
