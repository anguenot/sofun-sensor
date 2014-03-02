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

package org.sofun.sensor.impl.session;

import java.security.SecureRandom;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sofun.sensor.api.SensorException;
import org.sofun.sensor.api.session.Session;
import org.sofun.sensor.api.session.SessionService;
import org.sofun.sensor.api.session.ejb.SessionServiceLocal;
import org.sofun.sensor.api.session.ejb.SessionServiceRemote;

/**
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
@Stateless
@Local(SessionServiceLocal.class)
@Remote(SessionServiceRemote.class)
public class SessionServiceImpl implements SessionService {

    private static final long serialVersionUID = -1705794715234081402L;

    private static final Log log = LogFactory.getLog(SessionServiceImpl.class);

    private final SecureRandom randomGenerator = new SecureRandom();

    private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";

    public SessionServiceImpl() {

    }

    /**
     * Return the remote Address given a request.
     * 
     * <p>
     * 
     * Checks if the request went through a proxy with X-FORWARDED-FOR header
     * 
     * @param request: an {@link HttpServletRequest} insstance
     * @return IP addr as a {@link String}
     */
    private static String getRemoteAddr(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String x;
        if ((x = request.getHeader(HEADER_X_FORWARDED_FOR)) != null) {
            remoteAddr = x;
            int idx = remoteAddr.indexOf(',');
            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);
            }
        }
        return remoteAddr;
    }

    @Override
    public Session createSessionFor(HttpServletRequest request)
            throws SensorException {
        final String key = String.valueOf(randomGenerator.nextInt(1000000));
        final String playerIP = getRemoteAddr(request);
        Session session = new SessionImpl(key);
        session.setPlayerIP(playerIP);
        return session;
    }

    @Override
    public void delSession(Session session) throws SensorException {
        if (session != null) {
            log.debug("Deleting session with key=" + session.getKey());
            session = null;
        }

    }

}
