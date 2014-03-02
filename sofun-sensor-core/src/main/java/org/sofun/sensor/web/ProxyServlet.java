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

package org.sofun.sensor.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.api.Configuration;
import org.sofun.sensor.api.SensorException;
import org.sofun.sensor.api.routing.RoutingService;
import org.sofun.sensor.api.session.Session;
import org.sofun.sensor.api.session.SessionService;
import org.sofun.sensor.arjel.api.ARJELService;
import org.sofun.sensor.arjel.api.event.EventOUVOKCONFIRME;
import org.sofun.sensor.arjel.api.event.XMLTrace;
import org.sofun.sensor.arjel.api.exception.XMLValidationException;
import org.sofun.sensor.betkup.api.BetkupService;
import org.sofun.sensor.cecurity.api.CecurityService;

/**
 * Proxy Servlet.
 * 
 * <p>
 * 
 * Servlet acting as an entry point and proxy in between users and gaming
 * platform. Responsible to instrument ARJEL traces generations as well as
 * storage in a e-safe.
 * 
 * <p>
 * 
 * The life cycle is:
 * 
 * <ul>
 * <li>Routing</li>
 * <li>Extraction</li>
 * <li>Sessions</li>
 * <li>Traces generation</li>
 * <li>e-safe remote storage</li>
 * <li>Local traces storage</li>
 * </ul>
 * 
 * <p>
 * 
 * Implementation aimed at being generic enough so that we could use different
 * e-safe destination and / or extractor plugins (for differend gaming platform)
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class ProxyServlet extends HttpServlet {

    private static final long serialVersionUID = 5643512017492879513L;

    private static final Log log = LogFactory.getLog(ProxyServlet.class);

    /** Sensor session service */
    private SessionService sessions;

    /** Sensor routing service */
    private RoutingService routing;

    /** Sensor ARJEL service */
    private ARJELService arjel;

    /** Cecurity service */
    private CecurityService cecurity;

    /** Betkup Service */
    private BetkupService betkup;

    /**
     * Returns the sessions service. (EJB)
     * 
     * @return a {@link SessionService} instance
     * @throws SensorException if a NamingException occurred.
     */
    private SessionService getSessions() throws SensorException {
        if (sessions == null) {
            InitialContext ctx;
            try {
                ctx = new InitialContext();
                sessions = (SessionService) ctx
                        .lookup("sofun-sensor/SessionServiceImpl/local");
            } catch (NamingException e) {
                throw new SensorException(e);
            }
        }
        return sessions;
    }

    /**
     * Returns the routing service. (EJB)
     * 
     * @return a {@link RoutingService} instance
     * @throws SensorException if a NamingException occurred.
     */
    private RoutingService getRouting() throws SensorException {
        if (routing == null) {
            InitialContext ctx;
            try {
                ctx = new InitialContext();
                routing = (RoutingService) ctx
                        .lookup("sofun-sensor/RoutingServiceImpl/local");
            } catch (NamingException e) {
                throw new SensorException(e);
            }
        }
        return routing;
    }

    /**
     * Returns the ARJEL service. (EJB)
     * 
     * @return a {@link ARJELService} instance
     * @throws SensorException if a NamingException occurred.
     */
    private ARJELService getARJEL() throws SensorException {
        if (arjel == null) {
            InitialContext ctx;
            try {
                ctx = new InitialContext();
                arjel = (ARJELService) ctx
                        .lookup("sofun-sensor/ARJELServiceImpl/local");
            } catch (NamingException e) {
                throw new SensorException(e);
            }
        }
        return arjel;
    }

    /**
     * Returns the Cecurity service. (EJB)
     * 
     * @return a {@link CecurityService} instance
     * @throws SensorException if a NamingException occurred.
     */
    private CecurityService getCecurity() throws SensorException {
        if (cecurity == null) {
            InitialContext ctx;
            try {
                ctx = new InitialContext();
                cecurity = (CecurityService) ctx
                        .lookup("sofun-sensor/CecurityServiceImpl/local");
            } catch (NamingException e) {
                throw new SensorException(e);
            }
        }
        return cecurity;
    }

    /**
     * Returns the Betkup service. (EJB)
     * 
     * @return a {@link BetkupService} instance
     * @throws SensorException if a NamingException occurred.
     */
    private BetkupService getBetkup() throws SensorException {
        if (betkup == null) {
            InitialContext ctx;
            try {
                ctx = new InitialContext();
                betkup = (BetkupService) ctx
                        .lookup("sofun-sensor/BetkupServiceImpl/local");
            } catch (NamingException e) {
                throw new SensorException(e);
            }
        }
        return betkup;
    }

    /**
     * Returns a connection given a URL.
     * 
     * @param url: a {@link URL} instance
     * @return a {@link HttpURLConnection} or {@link HttpsURLConnection}
     * @throws IOException
     */
    private HttpURLConnection getConnection(URL url) throws IOException {
        if (url.toString().startsWith("https://")) {
            return (HttpsURLConnection) url.openConnection();
        }
        return (HttpURLConnection) url.openConnection();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        BufferedInputStream web2proxy = null;
        BufferedOutputStream proxy2client = null;
        HttpURLConnection connection = null;

        Session session = null;

        try {

            String urlString = Configuration.getWWWUrlString()
                    + request.getRequestURL().substring(
                            Configuration.getProxyURLString().length());
            String queryString = request.getQueryString();
            urlString += queryString == null ? "" : "?" + queryString;
            URL url = new URL(urlString);

            log.info("Fetching URL=" + url.toString());

            // Request extraction and XML trace generation *must* occur *from*
            // player *to* gaming platform per ARJEL specifications.
            final boolean isRouted = getRouting().isRouted(request);
            if (isRouted) {
                // Request wrapper instance required since we need to read
                // parameters before returning the request to player.
                // If we don't, then getParameter() interfere with stream coming
                // only once from client.
                request = new SensorHttpServletWrappedRequest(request);
                session = getSessions().createSessionFor(request);
                if (session != null) {
                    final String[] eventTypes = getRouting().getEventTypesFor(
                            request);
                    for (int i = 0; i < eventTypes.length; i++) {
                        final String eventType = eventTypes[i];
                        final Map<String, String> params = getBetkup()
                                .getParametersForEventType(eventType, request);
                        if (params.size() == 0) {
                            // Not all events are necessary to be handled for
                            // the same route. (for instance async
                            // notifications)
                            continue;
                        }
                        final XMLTrace trace = getARJEL().getXMLTrace(
                                eventType, session.getPlayerIP(),
                                session.getKey(), params);
                        // A given action path does not necessarily
                        // generates all associated events. It depends on
                        // the underlying application.
                        if (trace != null) {
                            // This is important to call a validate() here and
                            // let it fail if not valid so that the actual
                            // request does *not* go through as we won't be able
                            // to send this trace to the safe.
                            try {
                                trace.validate();
                            } catch (XMLValidationException e) {
                                throw new SensorException(e);
                            }
                            session.addTrace(trace);
                        }
                    }
                }
            }

            int statusCode;
            int oneByte;
            final String methodName = request.getMethod();

            connection = getConnection(url);
            if (Configuration.isDebugOn()) {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection)
                            .setHostnameVerifier(new CustomizedHostnameVerifier());
                }
            }

            connection.setRequestMethod(methodName);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            HttpsURLConnection.setFollowRedirects(false);
            connection.setUseCaches(true);

            for (Enumeration<String> e = request.getHeaderNames(); e
                    .hasMoreElements();) {
                String headerName = e.nextElement().toString();
                connection.setRequestProperty(headerName,
                        request.getHeader(headerName));
            }

            connection.connect();

            if (methodName.equals("POST")) {
                BufferedInputStream clientToProxyBuf = new BufferedInputStream(
                        request.getInputStream());
                BufferedOutputStream proxyToWebBuf = new BufferedOutputStream(
                        connection.getOutputStream());
                while ((oneByte = clientToProxyBuf.read()) != -1) {
                    proxyToWebBuf.write(oneByte);
                }
                proxyToWebBuf.flush();
                proxyToWebBuf.close();
                clientToProxyBuf.close();
            }

            statusCode = connection.getResponseCode();
            response.setStatus(statusCode);
            boolean opSuccess = false;
            boolean opActivationSuccess = false;
            for (Iterator<Map.Entry<String, List<String>>> i = connection
                    .getHeaderFields().entrySet().iterator(); i.hasNext();) {
                final Map.Entry<String, List<String>> mapEntry = i.next();
                final String key = mapEntry.getKey();
                if (key != null) {
                    final String value = mapEntry.getValue().get(0);
                    // Check if operations is reported as success platform side
                    if (Configuration.getOpSuccessFlag().equals(key)) {
                        opSuccess = true;
                        // We do not return this header to player
                    } else if (Configuration.getOpActivationSuccessFlag()
                            .equals(key)) {
                        opActivationSuccess = true;
                        // We do not return this header to player
                    } else {
                        response.setHeader(key, value);
                    }
                }
            }

            if (isRouted) {
                if (opSuccess) {
                    for (XMLTrace trace : session.getTraces()) {
                        boolean store = true;
                        // Special case.
                        if (EventOUVOKCONFIRME.TYPE
                                .equals(trace.getEventType())) {
                            if (!opActivationSuccess) {
                                store = false;
                            }
                        }
                        if (store) {
                            // If devel mode is on let's not try to connect to
                            // safe. We just display trace.
                            if (!Configuration.isDevelOn()) {
                                getCecurity().store(trace);
                            } else {
                                log.info("######### DEVEL MODE ##########");
                                log.info(trace.getXML());
                                log.info("######### DEVEL MODE ##########");
                            }
                        }
                    }
                }
                sessions.delSession(session);
            }

            web2proxy = new BufferedInputStream(connection.getInputStream());
            proxy2client = new BufferedOutputStream(response.getOutputStream());

            while ((oneByte = web2proxy.read()) != -1) {
                proxy2client.write(oneByte);
            }

            proxy2client.flush();

        } catch (FileNotFoundException fnf) {
            log.error("Request Page not Found:" + fnf.getMessage());
            if (log.isDebugEnabled()) {
                fnf.printStackTrace();
            }
        } catch (Exception e) {
            log.error("An internal error occured." + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (proxy2client != null) {
                    proxy2client.close();
                }
                if (web2proxy != null) {
                    web2proxy.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (Exception e) {
                log.error("Teardown internal error occured:" + e.getMessage());
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Customized Hostname verifier. (development / staging environ only!)
     * 
     * <p>
     * 
     * When you trying to connect to a server with untrusted SSL certificate,
     * you might encounter below mentioned exceptions:
     * java.security.cert.CertificateException: No subject alternative names
     * matching IP address xxx.xxx.xxx found or
     * java.security.cert.CertificateException: No subject alternative DNS name
     * matching hostname.com found.
     * 
     * <p>
     * 
     * The reason is because the certificate did not set the correct subject
     * alternative value correctly.
     * 
     * <p>
     * 
     * To be used only in development / staging environ only. Use the debug flag
     * within the sensor.properties file to enable or disable the usage of this.
     * 
     * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
     */
    private static final class CustomizedHostnameVerifier implements
            HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
