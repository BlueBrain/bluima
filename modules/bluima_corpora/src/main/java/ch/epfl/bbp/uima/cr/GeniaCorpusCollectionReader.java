package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_PATH;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.uima.resource.ResourceInitializationException.COULD_NOT_ACCESS_DATA;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.xml.GeniaCorpusParser;
import ch.epfl.bbp.uima.xml.genia.Article;
import ch.epfl.bbp.uima.xml.genia.Cons;
import ch.epfl.bbp.uima.xml.genia.Sentence;
import de.julielab.jules.types.BioEntityMention;
import de.julielab.jules.types.Header;

/**
 * CollectionReader for the GENIA annotated corpus
 * (http://www-tsujii.is.s.u-tokyo.ac.jp/~genia/topics/Corpus/). GENIA Corpus
 * Version 3.0x consists of 2000 abstracts. The base abstracts are selected from
 * the search results with keywords (MeSH terms) Human, Blood Cells, and
 * Transcription Factors.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = SENTENCE)
public class GeniaCorpusCollectionReader extends JCasCollectionReader_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(GeniaCorpusCollectionReader.class);

    public static final String COMPONENT_ID = GeniaCorpusCollectionReader.class
            .getName();

    @ConfigurationParameter(name = PARAM_CORPUS_PATH, mandatory = true, //
    defaultValue = "pear_resources/GENIAcorpus3.02/GENIAcorpus3.02.xml", description = "path to Genia xml corpus")
    private String corpusPath;

    private Iterator<Article> corpusIt;
    private int docId;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            LOG.debug("corpusPath is " + corpusPath);

            InputStream corpusIs = ResourceHelper.getInputStream(corpusPath);
            corpusIt = new GeniaCorpusParser().parse(corpusIs).getArticle()
                    .iterator();
            docId = 0;
        } catch (Exception e) {
            LOG.error(StringUtils.print(e));
            throw new ResourceInitializationException(COULD_NOT_ACCESS_DATA,
                    new Object[] { GeniaCorpusParser.class, e.getMessage() });
        }
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {

        Article article = corpusIt.next();

        StringBuilder sb = new StringBuilder();
        int start = 0;

        for (Object sentenceObj : article.getAbstract().getContent()) {
            if (sentenceObj instanceof Sentence) {
                Sentence sentence = (Sentence) sentenceObj;
                for (Object contentObj : sentence.getContent()) {
                    String parse = annotatateContent(jcas, contentObj, start);
                    start += parse.length();
                    sb.append(parse);
                }
            }
        }
        jcas.setDocumentText(sb.toString());

        Header h = new Header(jcas);
        h.setTitle(article.getTitle().toString());
        h.setDocId(docId++ + "");
        h.addToIndexes();
    }

    public boolean hasNext() throws IOException, CollectionException {
        return corpusIt.hasNext();
    }

    public Progress[] getProgress() {// nope
        return null;
    }

    @Override
    public void close() throws IOException {// nope
    }

    /** Recursive, since annotations can overlap */
    private static String annotatateContent(JCas jcas, Object contentObj,
            int start) {
        LOG.trace("annotate: " + reflectionToString(contentObj));

        int end = start;
        StringBuilder internalSb = new StringBuilder();

        if (contentObj instanceof String) {
            String c = (String) contentObj;
            internalSb.append(c);
            end += c.length();

        } else if (contentObj instanceof Cons) {
            Cons cons = (Cons) contentObj;

            for (Object o : cons.getContent()) {
                String parse = annotatateContent(jcas, o, end);
                end += parse.length();
                internalSb.append(parse);
            }

            BioEntityMention entity = new BioEntityMention(jcas, start, end);
            entity.setSpecificType(cons.getSem());
            entity.setComponentId(GeniaCorpusCollectionReader.COMPONENT_ID);
            entity.setTextualRepresentation(cons.getLex());
            entity.addToIndexes();
        }

        return internalSb.toString();
    }

    /** Filters mentions that are proteins */
    public static Collection<BioEntityMention> getProteins(
            Collection<BioEntityMention> mentions) {
        ArrayList<BioEntityMention> ret = new ArrayList<BioEntityMention>();
        for (BioEntityMention mention : mentions) {
            if (mention.getSpecificType() != null
                    && mention.getSpecificType().startsWith("G#protein"))
                ret.add(mention);
        }
        return ret;
    }

    // String[] types = { "G#amino_acid_monomer", "G#atom", "G#body_part",
    // "G#cell_component", "G#cell_line", "G#cell_type",
    // "G#DNA_domain_or_region", "G#DNA_family_or_group",
    // "G#DNA_molecule", "G#DNA_N/A", "G#DNA_substructure", "G#inorganic",
    // "G#lipid", "G#multi_cell", "G#nucleotide", "G#other_name",
    // "G#other_organic_compound", "G#peptide", "G#polynucleotide",
    // "G#protein_complex", "G#protein_domain_or_region",
    // "G#protein_family_or_group", "G#protein_molecule", "G#protein_N/A",
    // "G#protein_substructure", "G#protein_subunit",
    // "G#RNA_family_or_group", "G#RNA_molecule", "G#tissue", "G#virus" };
}
