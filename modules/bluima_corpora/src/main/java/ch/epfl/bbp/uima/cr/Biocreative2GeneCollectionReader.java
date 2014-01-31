package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_ANNOTATIONS_PATH;
import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_PATH;
import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MODE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BIO_ENTITY_MENTION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;
import static org.apache.uima.resource.ResourceInitializationException.RESOURCE_DATA_NOT_VALID;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.io.LineReader;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import de.julielab.jules.types.BioEntityMention;
import de.julielab.jules.types.Header;

/**
 * CollectionReader for the Biocreative2 (2006) annotated corpus
 * (http://www.biocreative.org/news/biocreative-ii/), containing 20000 sentences
 * from MEDLINE abstracts.
 * 
 * 
 * P00027739T0000|5 28|gamma glutamyltransferase P00027739T0000|0 28|Serum gamma
 * glutamyltransferase
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER, BIO_ENTITY_MENTION })
public class Biocreative2GeneCollectionReader extends
        JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory
            .getLogger(Biocreative2GeneCollectionReader.class);

    public static final String COMPONENT_ID = Biocreative2GeneCollectionReader.class
            .getName();

    @ConfigurationParameter(name = PARAM_MODE, mandatory = true, //
    defaultValue = "test", description = "mode, either test or train")
    private String mode;

    @ConfigurationParameter(name = PARAM_CORPUS_ROOT, mandatory = true, //
    defaultValue = "pear_resources/bc2geneMention/", description = "path to corpus")
    private String corpusRoot;

    public static final String INCLUDE_ALTERNATE = "include_alternate";

    @ConfigurationParameter(name = INCLUDE_ALTERNATE, //
    defaultValue = "true", description = "whether to include the alternate tagged forms")
    private boolean includeAlternate;

    /** allows to store multiple entries for the same map */
    private Multimap<String, CorpusEntryDTO> corpusAnnotations = HashMultimap
            .create();

    private Iterator<String> articleIt;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        // get the right paths and streams
        String corpusAnnotationsPath = null;
        String corpusAltAnnotationsPath = null;
        String corpusPath = null;
        LOG.debug("mode is " + mode);
        if (mode.equals("test")) {
            corpusAnnotationsPath = corpusRoot + "test/GENE.eval";
            corpusAltAnnotationsPath = corpusRoot + "test/ALTGENE.eval";
            corpusPath = corpusRoot + "test/test.in";

        } else if (mode.equals("train")) {
            corpusAnnotationsPath = corpusRoot + "train/GENE.eval";
            corpusAltAnnotationsPath = corpusRoot + "train/ALTGENE.eval";
            corpusPath = corpusRoot + "train/train.in";

        } else {
            throw new ResourceInitializationException(
                    NO_RESOURCE_FOR_PARAMETERS, new Object[] { "mode: " + mode
                            + ", only test and train available" });
        }

        // annotations
        LOG.debug("corpusAnnotationsPath is " + corpusAnnotationsPath);
        InputStream corpusIs;
        try {
            corpusIs = ResourceHelper.getInputStream(corpusAnnotationsPath);
            Preconditions.checkArgument(corpusIs != null);
        } catch (Exception e) {
            throw new ResourceInitializationException(RESOURCE_DATA_NOT_VALID,
                    new Object[] { corpusAnnotationsPath,
                            PARAM_CORPUS_ANNOTATIONS_PATH });
        }
        for (String line : new LineReader(corpusIs)) {
            CorpusEntryDTO c = new CorpusEntryDTO(line);
            corpusAnnotations.put(c.getDocId(), c);
        }
        closeQuietly(corpusIs);

        // alternate annotations
        if (includeAlternate) {
            LOG.debug("corpusAltAnnotationsPath is " + corpusAltAnnotationsPath);
            try {
                corpusIs = ResourceHelper
                        .getInputStream(corpusAltAnnotationsPath);
                Preconditions.checkArgument(corpusIs != null);
            } catch (Exception e) {
                throw new ResourceInitializationException(
                        RESOURCE_DATA_NOT_VALID, new Object[] {
                                corpusAnnotationsPath,
                                PARAM_CORPUS_ANNOTATIONS_PATH });
            }
            for (String line : new LineReader(corpusIs)) {
                CorpusEntryDTO c = new CorpusEntryDTO(line);
                corpusAnnotations.put(c.getDocId(), c);
            }
            closeQuietly(corpusIs);
        }

        // articles
        LOG.debug("corpusPath is " + corpusPath);
        InputStream articlesIs;
        try {
            articlesIs = ResourceHelper.getInputStream(corpusPath);
            Preconditions.checkArgument(articlesIs != null);
        } catch (Exception e) {
            throw new ResourceInitializationException(RESOURCE_DATA_NOT_VALID,
                    new Object[] { corpusPath, PARAM_CORPUS_PATH });
        }

        articleIt = new LineReader(articlesIs).iterator();

    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        // P00027739T0000 Serum gamma glutamyltransferase in the diagnosis...
        String article = articleIt.next();
        int firstSpace = article.indexOf(" ");
        String articleId = article.substring(0, firstSpace);
        String articleText = article.substring(firstSpace + 1);
        jcas.setDocumentText(articleText);

        Header header = new Header(jcas);
        header.setDocId(articleId);
        header.setComponentId(COMPONENT_ID);
        header.addToIndexes();

        Collection<CorpusEntryDTO> collection = corpusAnnotations
                .get(articleId);
        for (CorpusEntryDTO corpusEntry : collection) {

            // to correct for weird indexing...
            int start = articleText
                    .indexOf(corpusEntry.text, corpusEntry.start);

            BioEntityMention entity = new BioEntityMention(jcas, start, start
                    + corpusEntry.text.length());
            entity.setComponentId(COMPONENT_ID);
            entity.addToIndexes();
            assert (entity.getCoveredText().equals(corpusEntry.text));
        }
    }

    public boolean hasNext() throws IOException, CollectionException {
        return articleIt.hasNext();
    }

    public Progress[] getProgress() {// nope
        return null;
    }

    private static class CorpusEntryDTO {

        String docId;
        int start;
        String text;

        /**
         * <code>
         * P00027739T0000|0 28|Serum gamma glutamyltransferase<br>
         * P00027967A0207|11 31|secretory HI antibodies
         * </code>
         */
        CorpusEntryDTO(String line) {
            String[] split = line.split("\\|");
            docId = split[0];
            String[] startEnd = split[1].split(" ");
            start = new Integer(startEnd[0]);
            text = split[2];
        }

        String getDocId() {
            return docId;
        }
    }
}
