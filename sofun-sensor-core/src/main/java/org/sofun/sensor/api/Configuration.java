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

package org.sofun.sensor.api;

import java.io.InputStream;
import java.util.Properties;

import org.sofun.sensor.web.ProxyServlet;

/**
 * Sensor Component Configuration.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public final class Configuration {

    /** ARJEL properties defined within arjel.properties */
    private static Properties properties = new Properties();

    /**
     * Returns component level properties.
     * 
     * <p>
     * 
     * sensor.properties file is deployed at package level.
     * 
     * @return a {@link Properties} instance.
     */
    public static Properties getProperties() {
        final String propertiesFile = "sensor.properties";
        if (properties.size() == 0) {
            InputStream raw = null;
            try {
                raw = Thread.currentThread().getContextClassLoader()
                        .getResourceAsStream(propertiesFile);
                if (raw != null) {
                    properties.load(raw);
                    raw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * Returns the Web Site URL of the instance behind this sensor instance.
     * 
     * <p>
     * Typically included inside a configuration file.
     * 
     * @return a {@link String}
     */
    public static String getWWWUrlString() {
        final String wwwUrlKey = "www.url";
        return Configuration.getProperties().getProperty(wwwUrlKey);
    }

    /**
     * Is the sensor instance in debug mode ?
     * 
     * <p>
     * 
     * Debug mode here has nothing to do with logging or app server. This is an
     * application level (sensor level) flag.
     * 
     * @return true or false
     */
    public static boolean isDebugOn() {
        final String sensorDebug = "sensor.debug";
        if (Configuration.getProperties()
                .getProperty(sensorDebug.toLowerCase()).equals("on")) {
            return true;
        }
        return false;
    }

    /**
     * Is the sensor instance in debug mode ?
     * 
     * <p>
     * 
     * Debug mode here has nothing to do with logging or app server. This is an
     * application level (sensor level) flag.
     * 
     * @return true or false
     */
    public static boolean isDevelOn() {
        final String sensorDebug = "sensor.devel";
        if (Configuration.getProperties()
                .getProperty(sensorDebug.toLowerCase()).equals("on")) {
            return true;
        }
        return false;
    }

    /**
     * Returns the HTTP header flag added by gaming platform to notify a
     * successful operation gaming platform side.
     * 
     * @return a {@link String}
     */
    public static String getOpSuccessFlag() {
        final String opSuccessFlagKey = "op.success.flag";
        return Configuration.getProperties().getProperty(opSuccessFlagKey);
    }

    /**
     * Returns the HTTP header flag added by gaming platform to notify a
     * successful activation operation gaming platform side.
     * 
     * <p>
     * 
     * Special case for betkup here since the form is mixed up with edit.
     * 
     * @return a {@link String}
     */
    public static String getOpActivationSuccessFlag() {
        final String opSuccessFlagKey = "op.activation.flag";
        return Configuration.getProperties().getProperty(opSuccessFlagKey);
    }

    /**
     * Returns the proxy instance (this) URL in from of the Betkup.fr instance.
     * 
     * <p>
     * Typically included inside a configuration file.
     * 
     * @see ProxyServlet#getProperties()
     * 
     * @return a {@link String}
     */
    public static String getProxyURLString() {
        final String proxyUrlKey = "proxy.url";
        return Configuration.getProperties().getProperty(proxyUrlKey);
    }

}
