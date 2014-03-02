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

package org.sofun.sensor.storage;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Protocol;

public class HostAndPortUtil {

    private static List<HostAndPort> hostAndPortList = new ArrayList<HostAndPortUtil.HostAndPort>(
            2);

    static {
        final HostAndPort defaulthnp1 = new HostAndPort();
        defaulthnp1.host = "localhost";
        defaulthnp1.port = Protocol.DEFAULT_PORT;
        hostAndPortList.add(defaulthnp1);

        final HostAndPort defaulthnp2 = new HostAndPort();
        defaulthnp2.host = "localhost";
        defaulthnp2.port = Protocol.DEFAULT_PORT + 1;
        hostAndPortList.add(defaulthnp2);

        final String envHosts = System.getProperty("redis-hosts");
        if (null != envHosts && 0 < envHosts.length()) {
            final String[] hostDefs = envHosts.split(",");
            if (null != hostDefs && 2 <= hostDefs.length) {
                hostAndPortList = new ArrayList<HostAndPortUtil.HostAndPort>(
                        hostDefs.length);
                for (String hostDef : hostDefs) {
                    final String[] hostAndPort = hostDef.split(":");
                    if (null != hostAndPort && 2 == hostAndPort.length) {
                        final HostAndPort hnp = new HostAndPort();
                        hnp.host = hostAndPort[0];
                        try {
                            hnp.port = Integer.parseInt(hostAndPort[1]);
                        } catch (final NumberFormatException nfe) {
                            hnp.port = Protocol.DEFAULT_PORT;
                        }
                        hostAndPortList.add(hnp);
                    }
                }
            }
        }
        final StringBuilder strb = new StringBuilder(
                "Redis hosts to be used : ");
        for (HostAndPort hnp : hostAndPortList) {
            strb.append('[').append(hnp.host).append(':').append(hnp.port)
                    .append(']').append(' ');
        }
        System.out.println(strb);
    }

    public static List<HostAndPort> getRedisServers() {
        return hostAndPortList;
    }

    public static class HostAndPort {
        public String host;
        public int port;
    }
}
