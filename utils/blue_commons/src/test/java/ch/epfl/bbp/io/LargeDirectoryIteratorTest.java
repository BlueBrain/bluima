package ch.epfl.bbp.io;

import static ch.epfl.bbp.io.LineReader.asText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import ch.epfl.bbp.ResourceHelper;

public class LargeDirectoryIteratorTest {

    @Test
    public void test() throws Exception {

        if (File.separatorChar == '/') {// only nix

            File dir = ResourceHelper.getFile("directoryIterator/default");

            LargeDirectoryIterator iterator = new LargeDirectoryIterator(dir);

            for (int i = 0; i < 3; i++) {
                assertTrue("should have at least " + (i + 1) + " files",
                        iterator.hasNext());
                File file = iterator.next();
                assertEquals("test", asText(file).substring(0, 4));
            }
            assertFalse(iterator.hasNext());
        }
    }

    // @Test
    public void test2() throws Exception {

        if (File.separatorChar == '/') {// only nix

            File dir = ResourceHelper.getFile("directoryIterator/default");

            LargeDirectoryIterator iterator = new LargeDirectoryIterator(dir);

            for (int i = 0; i < 3; i++) {

                iterator.hasNext();
                File file = iterator.next();
                assertEquals("test", asText(file).substring(0, 4));
            }
            assertFalse(iterator.hasNext());
        }
    }
}
