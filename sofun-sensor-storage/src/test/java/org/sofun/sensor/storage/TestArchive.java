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

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.sofun.sensor.storage.api.Archive;
import org.sofun.sensor.storage.api.ArchivePersistence;
import org.sofun.sensor.storage.impl.ArchiveImpl;

/**
 * XML Trace Archive Test Case.
 * 
 * @author <a href="mailto:julien@anguenot.org">Julien Anguenot</a>
 * 
 */
public class TestArchive extends AbstractTestCase {

    @Test
    public void testSerializationEmpty() throws Exception {

        Archive a = new ArchiveImpl();
        final String s = ArchivePersistence.toString(a);
        Archive n = (Archive) ArchivePersistence.fromString(s);
        assertNotNull(n);

    }

    @Test
    public void testSerializationWithXMLContent() throws Exception {

        final String xml = getRootXMLFrom("event0.xml");
        final String key = "K-123";
        final Map<String, String> properties = new HashMap<String, String>();

        Archive a = new ArchiveImpl(key, xml, properties);
        assertEquals(xml, a.getXML());

        final String s = ArchivePersistence.toString(a);
        Archive n = (Archive) ArchivePersistence.fromString(s);
        assertEquals(xml, n.getXML());

    }

    @Test
    public void testSerializationEncoding() throws Exception {

        final String xml = getRootXMLFrom("event1.xml");
        final String key = "K-123";
        final Map<String, String> properties = new HashMap<String, String>();

        Archive a = new ArchiveImpl(key, xml, properties);
        assertEquals(xml, a.getXML());

        // First serialization
        String s = ArchivePersistence.toString(a);
        Archive n = (Archive) ArchivePersistence.fromString(s);
        assertEquals(xml, n.getXML());

        // Second serialization
        s = ArchivePersistence.toString(n);
        n = (Archive) ArchivePersistence.fromString(s);
        assertEquals(xml, n.getXML());

    }

    @Test
    public void testSerializationWithProperties() throws Exception {

        final String xml = getRootXMLFrom("event1.xml");
        final String key = "K-123";
        final Map<String, String> properties = new HashMap<String, String>();
        properties.put("A", "AA");
        properties.put("B", "BB");

        Archive a = new ArchiveImpl(key, xml, properties);
        assertEquals(xml, a.getXML());

        String s = ArchivePersistence.toString(a);
        Archive n = (Archive) ArchivePersistence.fromString(s);
        assertEquals(xml, n.getXML());
        assertEquals("BB", n.getProperties().get("B"));
        assertEquals("AA", n.getProperties().get("A"));

        // Modify props and serialize / deserialize again

        n.setProperty("C", "CC");
        s = ArchivePersistence.toString(n);
        n = (Archive) ArchivePersistence.fromString(s);
        assertEquals(xml, n.getXML());
        assertEquals("BB", n.getProperties().get("B"));
        assertEquals("AA", n.getProperties().get("A"));
        assertEquals("CC", n.getProperties().get("C"));

    }

    @Test
    public void testEmptyPropConstructor() throws Exception {

        // Verify null as properties does not override default data structure
        Archive a = new ArchiveImpl("A", "", null);
        assertEquals(0, a.getProperties().size());

    }

    @Test
    public void testSetProperties() throws Exception {

        Archive a = new ArchiveImpl("A", "", null);
        assertEquals(0, a.getProperties().size());

        Map<String, String> props = new HashMap<String, String>();
        props.put("a", "a");
        props.put("b", "b");
        a.setProperties(props);

        assertEquals(2, a.getProperties().size());
        assertEquals("a", a.getProperties().get("a"));
        assertEquals("b", a.getProperties().get("b"));

    }
    
    @Test
    public void testDefaults() throws Exception {
        
        Archive a = new ArchiveImpl("A", "", null);
        assertFalse(a.isSent());
        assertFalse(a.isSafeAck());
        assertFalse(a.isSafeArchived());
        
    }

}
