package ch.epfl.bbp.uima.cr;

import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.io.DefaultDirectoryIterator;
import ch.epfl.bbp.uima.io.DirectoryIterator;
import ch.epfl.bbp.uima.utils.StructuredDirectory;

public class AbstractReader {
    protected static final Logger LOG = getLogger(AbstractReader.class);

    public static void main(String[] args) throws Exception {

        FileSystemManager fsManager = VFS.getManager();
        final Pattern pmidP = Pattern
                .compile("<PMID Version=\"\\d+\">(.*?)</PMID>");

        DirectoryIterator it = new DefaultDirectoryIterator();
        it.setDirectory(new File(
        // "/Volumes/HDD2/ren_scratch/Dropbox/dev_shared/tmp3/"));
                "/Volumes/simulation/nip/pubmed_gzip/medline14/"));
        it.setExtensionFilter("xml.gz");
        it.setRecursive(false);
        Iterator<File> fit = it.iterator();

        while (fit.hasNext()) {
            File f = fit.next();

            // "medline14n0456.xml.gz" --> 456
            int archiveId = parseInt(f.getName().substring(10, 14));
            if (archiveId > 755) {

                try {
                    FileObject archive = fsManager.resolveFile("gz:file://"
                            + f.getAbsolutePath());
                    FileObject fo = archive.getChildren()[0];
                    LOG.debug("extracted file {} from archive {}",
                            fo.getName(), f.getName());
                    if (fo.isReadable() && fo.getType() == FileType.FILE) {
                        FileContent fc = fo.getContent();

                        String articles = IOUtils.toString(fc.getInputStream(),
                                "UTF-8");
                        String[] split = articles.split("<MedlineCitation");

                        for (int i = 1; i < split.length - 1; i++) {

                            String article = "<MedlineCitation" + split[i];
                            if (!article.startsWith("<MedlineCitationSet>")) {

                                Matcher matcher = pmidP.matcher(article);
                                if (matcher.find()) {
                                    int pmid = Integer.parseInt(matcher
                                            .group(1));
                                    String filePath = StructuredDirectory
                                            .getFilePath(pmid, "xml");
                                    File file = new File(
                                    // "/Volumes/HDD2/ren_scratch/Dropbox/dev_shared/tmp3/xml/"
                                            "/Volumes/simulation/nip/pubmed_xml/"
                                                    + filePath);
                                    file.getParentFile().mkdirs();

                                    // System.out.println(pmid);
                                    FileOutputStream out = new FileOutputStream(
                                            file);
                                    IOUtils.write(article, out);
                                    IOUtils.closeQuietly(out);
                                } else {
                                    System.err
                                            .println("could not extract pmid from "
                                                    + article);
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    LOG.error("error with " + f.getName(), e);
                }
            }
        }
    }
}
