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

package org.sofun.sensor.impl.routing;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.api.Configuration;
import org.sofun.sensor.api.SensorException;
import org.sofun.sensor.api.routing.RoutingService;
import org.sofun.sensor.api.routing.ejb.RoutingServiceLocal;
import org.sofun.sensor.api.routing.ejb.RoutingServiceRemote;
import org.sofun.sensor.arjel.api.event.EventACCESREFUSE;
import org.sofun.sensor.arjel.api.event.EventAUTOINTERDICTION;
import org.sofun.sensor.arjel.api.event.EventCLOTUREDEM;
import org.sofun.sensor.arjel.api.event.EventCPTEABOND;
import org.sofun.sensor.arjel.api.event.EventCPTEALIM;
import org.sofun.sensor.arjel.api.event.EventCPTEALIMOPE;
import org.sofun.sensor.arjel.api.event.EventCPTERETRAIT;
import org.sofun.sensor.arjel.api.event.EventLOTNATURE;
import org.sofun.sensor.arjel.api.event.EventMODIFINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventOKCONDGENE;
import org.sofun.sensor.arjel.api.event.EventOUVINFOPERSO;
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRME;
import org.sofun.sensor.arjel.api.event.EventPASPANNUL;
import org.sofun.sensor.arjel.api.event.EventPASPGAIN;
import org.sofun.sensor.arjel.api.event.EventPASPMISE;
import org.sofun.sensor.arjel.api.event.EventPREFCPTE;

/**
 * Routing Implementation.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Stateless
@Local(RoutingServiceLocal.class)
@Remote(RoutingServiceRemote.class)
public class RoutingServiceImpl implements RoutingService {

    private static final long serialVersionUID = -5907578022286404980L;

    private static final Log log = LogFactory.getLog(RoutingServiceImpl.class);

    private static final Properties properties = new Properties();

    public RoutingServiceImpl() {
    }

    /**
     * Returns routing properties.
     * 
     * <p>
     * 
     * routing.properties file is deployed at package level.
     * 
     * @return a {@link Properties} instance.
     */
    private static Properties getProperties() {
        final String propertiesFile = "routing.properties";
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
     * Returns all path that must be routed.
     * 
     * @return a array of {@link String} paths
     */
    private String[] getRoutingPaths() {
        return getProperties().getProperty("paths").split(",");
    }

    /**
     * Returns the action path for a given request.
     * 
     * <p>
     * 
     * For instance: /account/registerAdvanced
     * 
     * @param request: a {@link HttpServletRequest} instance
     * @return {@link String}
     */
    private String getActionPath(HttpServletRequest request) {
        final String proxyUrl = Configuration.getProxyURLString();
        final String requestUrl = request.getRequestURL().toString();
        return requestUrl.substring(proxyUrl.length(), requestUrl.length());
    }

    @Override
    public boolean isRouted(HttpServletRequest request) throws SensorException {
        // XXX enhance -> use proper REGEXP. The implementation below works
        // though.
        final String[] paths = getRoutingPaths();
        final String action = getActionPath(request);
        if (Arrays.asList(paths).contains(action)
                && request.getMethod().equals("POST")) {
            // Only POST requests must be routed. Rendering of the form versus
            // submit of the form.
            log.debug("action=" + action + " found as routed");
            return true;
        } else if (action.endsWith("/bet")
                && request.getMethod().equals("POST")) {
            // Only POST requests must be routed. Rendering of the form versus
            // submit of the form.
            log.debug("action=" + action + " found as routed");
            return true;
        } else if (action.startsWith("/account/credit")
                && request.getMethod().equals("POST")) {
            // Only POST requests must be routed. Rendering of the form versus
            // submit of the form.
            log.debug("action=" + action + " found as routed");
            return true;
        } else if (action.startsWith("/account/updateSimpleAccount")
                && request.getMethod().equals("POST")) {
            // Only POST requests must be routed. Rendering of the form versus
            // submit of the form.
            log.debug("action=" + action + " found as routed");
            return true;
        }
        return false;
    }

    @Override
    public String[] getEventTypesFor(HttpServletRequest request)
            throws SensorException {

        String[] actions = null;
        final String action = getActionPath(request);

        if (action.startsWith("/account/registerAdvanced")
                || action.startsWith("/account/updateSimpleAccount")) {
            actions = new String[] { EventOUVINFOPERSO.TYPE,
                    EventOKCONDGENE.TYPE, EventPREFCPTE.TYPE };
        } else if (action.startsWith("/account/editField")) {
            actions = new String[] { EventMODIFINFOPERSO.TYPE,
                    EventPREFCPTE.TYPE };
        } else if (action.startsWith("/account/status")) {
            actions = new String[] { EventOUVOKCONFIRME.TYPE,
                    EventPREFCPTE.TYPE, EventAUTOINTERDICTION.TYPE };
        } else if (action.startsWith("/account/login")
                || action.startsWith("/login")) {
            actions = new String[] { EventACCESREFUSE.TYPE };
        } else if (action.startsWith("/account/closeRequest")) {
            actions = new String[] { EventCLOTUREDEM.TYPE };
        } else if (action.startsWith("/account/credit")
                || action.startsWith("/account/registerCredit")) {
            actions = new String[] { EventCPTEALIM.TYPE };
        } else if (action.startsWith("/account/wire")) {
            actions = new String[] { EventCPTERETRAIT.TYPE };
        } else if (action.endsWith("/bet")) {
            actions = new String[] { EventPASPMISE.TYPE };
        } else if (action.startsWith("/account/notificationsPopup")) {
            actions = new String[] { EventOKCONDGENE.TYPE, EventPASPANNUL.TYPE,
                    EventPASPGAIN.TYPE, EventCPTEABOND.TYPE,
                    EventCPTEALIMOPE.TYPE, EventLOTNATURE.TYPE };
        } else {
            log.warn("Not event types found for routed action=" + action);
            actions = new String[0];
        }
        return actions;

    }
}
