package ch.epfl.bbp.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * <code>
 * new File("myfile").list(new FileExtensionFilter("pdf", "csv"));
 * <code>
 * 
 * @author ”renaud.richardet@epfl.ch”
 */
public class FileExtensionFilter implements FilenameFilter {
    private Set<String> exts = new HashSet<String>();

    /**
     * @param extensions
     *            a list of allowed extensions, without the dot, e.g.
     *            <code>"xml","html","rss"</code>. If it contains a dot, it will
     *            be removed.
     */
    public FileExtensionFilter(String... extensions) {
        for (String ext : extensions) {
            if (!ext.startsWith("."))
                ext = "." + ext;
            exts.add(ext.toLowerCase().trim());
        }
    }

    public boolean accept(File dir, String name) {
        final Iterator<String> extList = exts.iterator();
        while (extList.hasNext()) {
            if (name.toLowerCase().endsWith(extList.next())) {
                return true;
            }
        }
        return false;
    }
}
