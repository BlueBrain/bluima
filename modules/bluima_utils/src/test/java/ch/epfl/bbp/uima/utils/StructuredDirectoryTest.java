package ch.epfl.bbp.uima.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class StructuredDirectoryTest {

    @Test
    public void testGetSplit() {
        assertArrayEquals(new int[] { 2, 345, 678 },
                StructuredDirectory.getSplit(2345678));
        assertArrayEquals(new int[] { 22, 345, 678 },
                StructuredDirectory.getSplit(22345678));
        assertArrayEquals(new int[] { 0, 1, 234 },
                StructuredDirectory.getSplit(1234));
        assertArrayEquals(new int[] { 0, 0, 78 },
                StructuredDirectory.getSplit(78));
    }

    @Test
    public void testStoreFile() throws Exception {

        File outDir = new File("target/testStoreFile");

        // first test
        File inputFile = new File("target/2345678.txt");
        inputFile.createNewFile();

        File newFile = StructuredDirectory.storeFile(outDir, inputFile);

        String relativePath = newFile.getAbsolutePath().substring(
                outDir.getAbsolutePath().length());
        relativePath = relativePath.replaceAll("\\\\", "/");// win
        assertEquals("/2/345/678.txt", relativePath);

        // second test
        inputFile = new File("target/777.txt");
        inputFile.createNewFile();

        newFile = StructuredDirectory.storeFile(outDir, inputFile);

        relativePath = newFile.getAbsolutePath().substring(
                outDir.getAbsolutePath().length());
        relativePath = relativePath.replaceAll("\\\\", "/");// win
        assertEquals("/0/0/777.txt", relativePath);
    }

    @Test
    public void testDecodeFileName() throws Exception {

        File outDir = new File("target/testStoreFile2");

        File inputFile = new File("target/12345678.txt");
        inputFile.createNewFile();

        File newFile = StructuredDirectory.storeFile(outDir, inputFile);

        assertEquals("cannot decodeFileName", 12345678,
                StructuredDirectory.decodeFileName(newFile));

        assertEquals("cannot getFile", newFile, new File(outDir,
                StructuredDirectory.getFilePath(12345678, "txt")));

    }

    @Test
    public void testDecodeFileName2() throws Exception {
        assertEquals("", 12345678,
                StructuredDirectory.decodeFileName("12/345/678.txt"));
        assertEquals("", 12005008,
                StructuredDirectory.decodeFileName("12/5/8.txt"));
    }
}
