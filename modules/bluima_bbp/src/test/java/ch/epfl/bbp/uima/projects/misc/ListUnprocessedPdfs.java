package ch.epfl.bbp.uima.projects.misc;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.python.google.common.collect.Maps;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.io.LargeDirectoryIterator;
import ch.epfl.bbp.uima.utils.StructuredDirectory;

import com.google.common.collect.Sets;

/**
 * Whithin {@link #PDF_ROOT}, how many have not been successfully preprocessed
 * in {@link #PREPROCESSED}.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ListUnprocessedPdfs {

//    public static String PDF_ROOT = "/Volumes/scratch/richarde/pdfs/";
//    public static String PREPROCESSED = "/Volumes/simulation/nlp/data/20131120_preprocessed/";
//
//    public static void main_(String[] args) throws Exception {
//
//        Iterator<File> it = new LargeDirectoryIterator()//
//                .setDirectory(new File(PDF_ROOT))//
//                .setExtensionFilter("pdf")//
//                .setRecursive(true).iterator();
//
//        while (it.hasNext()) {
//            File pdf = it.next();
//            // System.out.println("processing " + pdf.getName());
//
//            int docId = parseInt(pdf.getName().replace(".pdf", ""));
//            String filePath = StructuredDirectory.getFilePath(docId, "gz");
//            if (!new File(PREPROCESSED + "/" + filePath).exists()) {
//                System.err.println(pdf.getAbsolutePath().replace(PDF_ROOT, ""));
//            }
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//
//        Set<Integer> processed = Sets.newHashSet();
//        int gzTot = 0;
//        for (String gz : LineReader.linesFrom(PREPROCESSED
//                + "20131209_list.txt")) {
//            gzTot++;
//            // System.out.println(gz.substring(2));
//            processed.add(StructuredDirectory.decodeFileName(gz.substring(2)));
//        }
//
//        int pdfTot = 0;
//        Map<Integer, String> missed = Maps.newHashMap();
//        for (String pdf : LineReader.linesFrom(PDF_ROOT + "20131209_list.txt")) {
//            pdfTot++;
//
//            String name = pdf.substring(1 + pdf.lastIndexOf("/"),
//                    pdf.length() - 4);
//            int pmId = Integer.parseInt(name);
//            if (!processed.contains(pmId))
//                missed.put(pmId, pdf);// System.out.println(pdf);
//        }
//        System.out.println("gzTOT " + gzTot);
//        System.out.println("pdfTot " + pdfTot);
//        System.out.println("missed " + missed.size());
//    }
}
