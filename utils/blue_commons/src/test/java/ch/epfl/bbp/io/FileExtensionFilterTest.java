package ch.epfl.bbp.io;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class FileExtensionFilterTest {

    @Test
    public void test() throws Exception {
        new File("target/FileExtensionFilter-testfile.sfdaafsd")
                .createNewFile();
        String[] files = new File("target").list(new FileExtensionFilter(
                "sfdaafsd", "asdfsfasdf"));
        assertEquals("1 match!", 1, files.length);

        files = new File("target").list(new FileExtensionFilter(".sfdaafsd",
                ".asdfsfasdf"));
        assertEquals("works with dots", 1, files.length);

        files = new File("target").list(new FileExtensionFilter("aa", "bb"));
        assertEquals("should not match, wrong extensions", 0, files.length);
    }

}
