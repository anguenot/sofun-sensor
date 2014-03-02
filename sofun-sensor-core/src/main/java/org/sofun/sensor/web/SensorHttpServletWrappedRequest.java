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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/**
 * Sensor HTTP Servlet Wrapped Request.
 * 
 * <p>
 * 
 * Allows one to read stream several times by storing a copy of the client input
 * stream. (getParameter() can be called several time with any interference)
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class SensorHttpServletWrappedRequest extends HttpServletRequestWrapper {

    private final HttpServletRequest req;

    private byte[] contentData;

    private HashMap<String, String[]> parameters;

    private static final String ENCODING_FALLBACK = "UTF-8";

    private SensorHttpServletWrappedRequest(HttpServletRequest request,
            byte[] contentData, HashMap<String, String[]> parameters) {
        super(request);
        req = request;
        this.contentData = contentData;
        this.parameters = parameters;
    }

    public SensorHttpServletWrappedRequest(HttpServletRequest request) {
        super(request);
        if (request == null)
            throw new IllegalArgumentException(
                    "The HttpServletRequest is null!");
        req = request;
    }

    @Override
    public HttpServletRequest getRequest() {
        try {
            parseRequest();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot parse the request!", e);
        }
        return new SensorHttpServletWrappedRequest(req, contentData, parameters);
    }

    public byte[] getContentData() {
        return contentData.clone();
    }

    public Map<String, String[]> getParameters() {
        HashMap<String, String[]> map = new HashMap<String, String[]>(
                parameters.size() * 2);
        for (String key : parameters.keySet()) {
            map.put(key, parameters.get(key).clone());
        }
        return map;
    }

    private void parseRequest() throws IOException {
        if (contentData != null)
            return; // already parsed

        byte[] data = new byte[req.getContentLength()];
        int len = 0, totalLen = 0;
        InputStream is = req.getInputStream();
        while (totalLen < data.length) {
            totalLen += (len = is.read(data, totalLen, data.length - totalLen));
            if (len < 1)
                throw new IOException("Cannot read more than " + totalLen
                        + (totalLen == 1 ? " byte!" : " bytes!"));
        }
        contentData = data;
        String enc = req.getCharacterEncoding();
        if (enc == null)
            enc = ENCODING_FALLBACK;
        String s = new String(data, enc), name, value;
        StringTokenizer st = new StringTokenizer(s, "&");
        int i;
        HashMap<String, LinkedList<String>> mapA = new HashMap<String, LinkedList<String>>(
                data.length * 2);
        LinkedList<String> list;
        boolean decode = req.getContentType() != null
                && req.getContentType().equals(
                        "application/x-www-form-urlencoded");
        while (st.hasMoreTokens()) {
            s = st.nextToken();
            i = s.indexOf("=");
            if (i > 0 && s.length() > i + 1) {
                name = s.substring(0, i);
                value = s.substring(i + 1);
                if (decode) {
                    try {
                        name = URLDecoder.decode(name, ENCODING_FALLBACK);
                    } catch (Exception e) {
                    }
                    try {
                        value = URLDecoder.decode(value, ENCODING_FALLBACK);
                    } catch (Exception e) {
                    }
                }
                list = mapA.get(name);
                if (list == null) {
                    list = new LinkedList<String>();
                    mapA.put(name, list);
                }
                list.add(value);
            }
        }
        HashMap<String, String[]> map = new HashMap<String, String[]>(
                mapA.size() * 2);
        for (Entry<String, LinkedList<String>> entry : mapA.entrySet()) {
            list = entry.getValue();
            map.put(entry.getKey(), list.toArray(new String[list.size()]));
        }
        parameters = map;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        parseRequest();
        return new SensorServletInputStream(contentData);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        parseRequest();

        String enc = req.getCharacterEncoding();
        if (enc == null)
            enc = ENCODING_FALLBACK;
        return new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream(contentData), enc));
    }

    @Override
    public String getParameter(String name) {
        try {
            parseRequest();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot parse the request!", e);
        }
        String[] values = parameters.get(name);
        if (values == null || values.length == 0)
            return null;
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        try {
            parseRequest();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot parse the request!", e);
        }
        return getParameters();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        try {
            parseRequest();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot parse the request!", e);
        }
        return new Enumeration<String>() {
            private final String[] arr = getParameters().keySet().toArray(
                    new String[0]);
            private int idx = 0;

            @Override
            public boolean hasMoreElements() {
                return idx < arr.length;
            }

            @Override
            public String nextElement() {
                return arr[idx++];
            }

        };
    }

    @Override
    public String[] getParameterValues(String name) {
        try {
            parseRequest();
        } catch (IOException e) {
            throw new IllegalStateException("Cannot parse the request!", e);
        }
        String[] arr = parameters.get(name);
        if (arr == null)
            return null;
        return arr.clone();
    }

    @Override
    public String getAuthType() {
        return req.getAuthType();
    }

    @Override
    public String getContextPath() {
        return req.getContextPath();
    }

    @Override
    public Cookie[] getCookies() {
        return req.getCookies();
    }

    @Override
    public long getDateHeader(String name) {
        return req.getDateHeader(name);
    }

    @Override
    public String getHeader(String name) {
        return req.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return req.getHeaderNames();
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return req.getHeaders(name);
    }

    @Override
    public int getIntHeader(String name) {
        return req.getIntHeader(name);
    }

    @Override
    public String getMethod() {
        return req.getMethod();
    }

    @Override
    public String getPathInfo() {
        return req.getPathInfo();
    }

    @Override
    public String getPathTranslated() {
        return req.getPathTranslated();
    }

    @Override
    public String getQueryString() {
        return req.getQueryString();
    }

    @Override
    public String getRemoteUser() {
        return req.getRemoteUser();
    }

    @Override
    public String getRequestURI() {
        return req.getRequestURI();
    }

    @Override
    public StringBuffer getRequestURL() {
        return req.getRequestURL();
    }

    @Override
    public String getRequestedSessionId() {
        return req.getRequestedSessionId();
    }

    @Override
    public String getServletPath() {
        return req.getServletPath();
    }

    @Override
    public HttpSession getSession() {
        return req.getSession();
    }

    @Override
    public HttpSession getSession(boolean create) {
        return req.getSession(create);
    }

    @Override
    public Principal getUserPrincipal() {
        return req.getUserPrincipal();
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return req.isRequestedSessionIdFromCookie();
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return req.isRequestedSessionIdFromURL();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isRequestedSessionIdFromUrl() {
        return req.isRequestedSessionIdFromUrl();
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return req.isRequestedSessionIdValid();
    }

    @Override
    public boolean isUserInRole(String role) {
        return req.isUserInRole(role);
    }

    @Override
    public Object getAttribute(String name) {
        return req.getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return req.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return req.getCharacterEncoding();
    }

    @Override
    public int getContentLength() {
        return req.getContentLength();
    }

    @Override
    public String getContentType() {
        return req.getContentType();
    }

    @Override
    public String getLocalAddr() {
        return req.getLocalAddr();
    }

    @Override
    public String getLocalName() {
        return req.getLocalName();
    }

    @Override
    public int getLocalPort() {
        return req.getLocalPort();
    }

    @Override
    public Locale getLocale() {
        return req.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return req.getLocales();
    }

    @Override
    public String getProtocol() {
        return req.getProtocol();
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getRealPath(String path) {
        return req.getRealPath(path);
    }

    @Override
    public String getRemoteAddr() {
        return req.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return req.getRemoteHost();
    }

    @Override
    public int getRemotePort() {
        return req.getRemotePort();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return req.getRequestDispatcher(path);
    }

    @Override
    public String getScheme() {
        return req.getScheme();
    }

    @Override
    public String getServerName() {
        return req.getServerName();
    }

    @Override
    public int getServerPort() {
        return req.getServerPort();
    }

    @Override
    public boolean isSecure() {
        return req.isSecure();
    }

    @Override
    public void removeAttribute(String name) {
        req.removeAttribute(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        req.setAttribute(name, value);
    }

    @Override
    public void setCharacterEncoding(String env)
            throws UnsupportedEncodingException {
        req.setCharacterEncoding(env);
    }

}
