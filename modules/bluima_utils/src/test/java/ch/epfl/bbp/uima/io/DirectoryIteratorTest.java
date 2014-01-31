package ch.epfl.bbp.uima.io;

import static ch.epfl.bbp.io.LineReader.asText;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang.SystemUtils.IS_OS_MAC;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import ch.epfl.bbp.uima.utils.Resources;

import com.google.common.collect.Lists;

public class DirectoryIteratorTest {

    @Test
    public void testDefault() throws Exception {

        File dir = Resources.getFile("directoryIterator/default");
        Iterator<File> it = DirectoryIterator.get(
                DefaultDirectoryIterator.class.getSimpleName(), dir, null,
                false);
        ArrayList<File> list = newArrayList(it);
        assertEquals(3, list.size());
        assertEquals("test", asText(list.get(0)).substring(0, 4));

        it = DirectoryIterator.get(
                DefaultDirectoryIterator.class.getSimpleName(), dir, "tm",
                false);
        list = Lists.newArrayList(it);
        assertEquals(1, list.size());
    }

    @Test
    public void testZip() throws Exception {

        File dir = Resources.getFile("directoryIterator/zipFileIterator.zip");
        Iterator<File> it = DirectoryIterator.get(
                ZipDirectoryIterator.class.getSimpleName(), dir, null, false);
        ArrayList<File> list = newArrayList(it);
        assertEquals(3, list.size());
        assertEquals("test", asText(list.get(0)).substring(0, 4));
    }

    @Test
    public void testLarge() throws Exception {

        if (IS_OS_LINUX || IS_OS_MAC) {

            File dir = Resources.getFile("directoryIterator/default");
            Iterator<File> it = DirectoryIterator.get(
                    LargeDirectoryIterator.class.getSimpleName(), dir, null,
                    false);
            ArrayList<File> list = newArrayList(it);
            assertEquals(3, list.size());
            assertEquals("test", asText(list.get(0)).substring(0, 4));

            it = DirectoryIterator.get(
                    LargeDirectoryIterator.class.getSimpleName(), dir, "tm",
                    false);
            list = newArrayList(it);
            assertEquals(1, list.size());
        }
    }
}
