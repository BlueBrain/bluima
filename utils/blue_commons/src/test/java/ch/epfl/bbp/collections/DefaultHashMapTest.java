package ch.epfl.bbp.collections;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class DefaultHashMapTest {

    @Test
    public void test() {
        Map<String, List<String>> dm = new DefaultHashMap<String, List<String>>(
                ArrayList.class);
        dm.get("nokey").add("one");
        dm.get("nokey").add("two");
        assertEquals(2, dm.get("nokey").size());
        assertEquals(0, dm.get("nokey2").size());
    }

    @Test
    public void testInt() {
        Map<String, Integer> dm = DefaultHashMap.intDefaultMap();
        assertEquals(new Integer(0), dm.get("nokey"));
        assertEquals(new Integer(0), dm.get("nokey2"));
        dm.put("nokey", 3);
        assertEquals(new Integer(0), dm.get("nokey2"));
        dm.put("nokey3", 3);
        assertEquals(new Integer(3), dm.get("nokey3"));
    }

    @Test
    public void testString() {
        Map<String, String> dm = DefaultHashMap.stringDefaultMap();
        assertEquals("", dm.get("nokey"));
        dm.put("nokey1", "mykey");
        assertEquals("mykey", dm.get("nokey1"));
    }
}
