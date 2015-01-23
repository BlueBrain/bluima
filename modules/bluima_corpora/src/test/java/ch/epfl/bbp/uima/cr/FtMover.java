package ch.epfl.bbp.uima.cr;

import static java.lang.System.currentTimeMillis;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.Iterator;

import org.slf4j.Logger;

import ch.epfl.bbp.io.LargeRecursiveDirectoryIterator;
import ch.epfl.bbp.uima.utils.StructuredDirectory;

public class FtMover {
    protected static final Logger LOG = getLogger(FtMover.class);

    public static void main(String[] args) throws Exception {

        File inDir = new File("/Volumes/scratch/richarde/pdfs/");
        File outDir = new File("/Volumes/simulation/nip/pubmed_xml/");

        Iterator<File> it = new LargeRecursiveDirectoryIterator(inDir, "pdf");

        int cnt = 0;
        long time = currentTimeMillis();
        while (it.hasNext()) {
            File pdfFile = it.next();
            try {

                StructuredDirectory.storeFile(outDir, pdfFile);
                pdfFile = null;
                if (cnt++ % 100 == 0) {
                    long elapsed = currentTimeMillis() - time;
                    time = currentTimeMillis();
                    System.out.println("copied " + cnt + " files in " + elapsed
                            + "ms");
                }
            } catch (Exception e) {
                System.out.println("cound not copy "
                        + pdfFile.getAbsolutePath());
            }
        }
    }
}
