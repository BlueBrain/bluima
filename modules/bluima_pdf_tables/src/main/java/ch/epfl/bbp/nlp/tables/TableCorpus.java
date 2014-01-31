package ch.epfl.bbp.nlp.tables;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;
import static org.apache.commons.io.FilenameUtils.removeExtension;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.epfl.bbp.io.FileExtensionFilter;

/**
 * Interface for table_corpus
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TableCorpus {

    final static String CORPUS_ANNOTS = "/annotations/";
    final static String CORPUS_PDF = "/pdfs/";

    final static String NO_TABLES = "no_tables/";
    final static String WITH_TABLES = "with_tables/";

    final static String EXT_ANNOTS = ".annot";
    final static String EXT_PDFS = ".pdf";

    private TableCorpus() {

    }

    public static List<Integer> getPmIds(String rootPath) {
        List<Integer> pmIds = newArrayList();

        for (String pmIdStr : new File(rootPath + CORPUS_ANNOTS + WITH_TABLES)
                .list(new FileExtensionFilter(EXT_ANNOTS))) {
            pmIds.add(parseInt(removeExtension(pmIdStr)));
        }
        for (String pmIdStr : new File(rootPath + CORPUS_ANNOTS + NO_TABLES)
                .list(new FileExtensionFilter(EXT_ANNOTS.replaceAll("\\.", "")))) {
            pmIds.add(parseInt(removeExtension(pmIdStr)));
        }
        return pmIds;
    }

    public static List<GoldAnnotation> getAnnotation(String rootPath, int pmId)
            throws IOException {
        try {
            File annotationFile = new File(rootPath + CORPUS_ANNOTS + NO_TABLES
                    + pmId + EXT_ANNOTS);
            if (!annotationFile.exists()) {
                annotationFile = new File(rootPath + CORPUS_ANNOTS
                        + WITH_TABLES + pmId + EXT_ANNOTS);
                if (!annotationFile.exists()) {
                    throw new IOException("no annotation file for " + pmId);
                }
            }
            return GoldAnnotation.parse(annotationFile);

        } catch (Exception e) {
            throw new IOException("could not extract annotations from " + pmId,
                    e);
        }
    }

    public static File getPdf(String rootPath, int pmId) throws IOException {
        try {
            File pdf = new File(rootPath + CORPUS_ANNOTS + NO_TABLES + pmId
                    + EXT_PDFS);
            if (!pdf.exists()) {
                pdf = new File(rootPath + CORPUS_ANNOTS + WITH_TABLES + pmId
                        + EXT_PDFS);
                if (!pdf.exists()) {
                    throw new IOException("no annotation file for " + pmId);
                }
            }
            return pdf;

        } catch (Exception e) {
            throw new IOException("could not extract annotations from " + pmId,
                    e);
        }
    }
}
