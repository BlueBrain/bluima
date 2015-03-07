package ch.epfl.bbp.nlp.neuroner;

import static ch.epfl.bbp.uima.BlueUima.PARAM_AND_QUERY;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.DELETE_FROM;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.FROM_ANNOTATION;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.TO_ANNOTATION;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter;
import static ch.epfl.bbp.uima.ae.StatsAnnotatorPlus.PARAM_PRINT_EVERY;
import static ch.epfl.bbp.uima.ae.output.BartWriter.PARAM_BATCH_PREFIX;
import static ch.epfl.bbp.uima.cr.PubmedWholeDatabaseCR.MESH;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineFromPath;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import neuroner.NeuroNER.Missing;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.ruta.type.W;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator;
import ch.epfl.bbp.uima.ae.KeepLargestAnnotationAnnotator;
import ch.epfl.bbp.uima.ae.NewlineSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.ae.output.AnnotationTypeWriter;
import ch.epfl.bbp.uima.ae.output.AnnotationTypeWriter2;
import ch.epfl.bbp.uima.ae.output.BartWriter;
import ch.epfl.bbp.uima.ae.output.HtmlViewerWriter;
import ch.epfl.bbp.uima.cr.PubmedWholeDatabaseCR;
import ch.epfl.bbp.uima.cr.TextFileReader;
import de.julielab.jules.types.Token;

@Ignore
public class PrintMissingTest extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(PrintMissingTest.class);

    static final String ROOT = "/Users/richarde/dev/bluebrain/git/neuroNER/";

    private AnalysisEngine ruta;
    private AnalysisEngine copyTokens;
    private AnalysisEngine longest;

    @Before
    public void beforeTest() throws Exception {
        ruta = createEngineFromPath(ROOT
                + "descriptor/neuroner/NeuroNEREngine.xml");
        longest = createEngine(KeepLargestAnnotationAnnotator.class,
                PARAM_ANNOTATION_CLASS, Missing.class.getName());
        // important for HtmlViewer
        copyTokens = createEngine(CopyAnnotationsAnnotator.class,
                FROM_ANNOTATION, W.class.getName(), TO_ANNOTATION,
                Token.class.getName(), DELETE_FROM, false);
    }

    @Test
    public void test() throws Exception {

        // JCas jCas =
        // createJCas(createTypeSystemDescriptionFromPath(ROOT+"descriptor/neuroner/MainTypeSystem.xml"));
        // jCas.setDocumentText("three four parasol ganglion cells ahah");

        JCas jCas = getTestCas("zero one large horizontal three four parasol ganglion neuron ahah. "
                + "zero one inverted premier deuxieme ganglion neuron ahah. ");
        // +
        // "But some more inverted premier second parasol ganglion neuron aha.");

        runPipeline(
                jCas,
                ruta,
                longest,
                createEngine(getSentenceSplitter()),
                copyTokens,
                createEngine(HtmlViewerWriter.class),
                // createEngine(PrintMissingTest.class),//
                createEngine(
                        AnnotationTypeWriter2.class,//
                        PARAM_ANNOTATION_CLASS, Missing.class,
                        PARAM_OUTPUT_FILE, "System"));
    }

    @Test
    public void testNeuroNerDocs() throws Exception {

        runPipeline(
                createReader(TextFileReader.class, PARAM_INPUT_DIRECTORY, ROOT //
                        + "input/"),//
                // + "input/articles_neurelectro/"),//
                ruta, longest//
                // createEngine(getSentenceSplitter())//
                // createEngine(NewlineSentenceSplitterAnnotator.class)//
                , copyTokens, createEngine(HtmlViewerWriter.class) //
        // ,
        // createEngine(BartWriter.class, PARAM_BATCH_PREFIX,
        // "neuroelectro_docs")//
        );
    }

    @Test
    public void testPubmedNs() throws Exception {

        runPipeline(
                createReader(PubmedWholeDatabaseCR.class, PARAM_DB_CONNECTION,
                        new String[] { "127.0.0.1", "bb_pubmed", "root", "" },
                        PARAM_AND_QUERY, MESH + " AND abstrct LIKE '%neuron%' "),//
                ruta, //
                longest//
                ,
                createEngine(NewlineSentenceSplitterAnnotator.class)//
                ,
                copyTokens,
                createEngine(HtmlViewerWriter.class)//
                ,
                createEngine(BartWriter.class, PARAM_BATCH_PREFIX,
                        "neuroelectro_docs")//
                ,
                createEngine(AnnotationTypeWriter.class,//
                        PARAM_ANNOTATION_CLASS, Missing.class)//
                ,
                createEngine(StatsAnnotatorPlus.class, PARAM_PRINT_EVERY, 500));
    }

    @Test
    /** 
     * Runs on a 'LIKE %neuron%' subset of PubMed abstracts. 
     * MySQL takes some initial time but eventually it runs at ~15docs/s
     */
    public void testPubmedNs2() throws Exception {

        runPipeline(
                createReader(PubmedWholeDatabaseCR.class, PARAM_DB_CONNECTION,
                        new String[] { "127.0.0.1", "bb_pubmed", "root", "" },
                        PARAM_AND_QUERY, MESH + " AND abstrct LIKE '%neuron%' "),//
                ruta,
                longest,
                createEngine(getSentenceSplitter()),
                copyTokens,
                createEngine(HtmlViewerWriter.class),
                createEngine(
                        AnnotationTypeWriter2.class,//
                        PARAM_ANNOTATION_CLASS, Missing.class,
                        PARAM_OUTPUT_FILE, "20140830_testPubmedNs.tsv"),
                createEngine(StatsAnnotatorPlus.class, PARAM_PRINT_EVERY, 500));
    }

    // @Override
    public void process_old(JCas jCas) throws AnalysisEngineProcessException {
        FSIterator<Annotation> it = jCas.getAnnotationIndex().iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Annotation a = it.next();
            System.out.println(a.getType().getName());
            sb.append(a.getCoveredText() + '\n');
            a.prettyPrint(2, 2, sb, false);
            sb.append('\n');
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        StringBuffer sb = new StringBuffer();
        for (Missing m : select(jCas, Missing.class)) {

            // List<NeuronProperty> covered = selectCovered(jCas,
            // NeuronProperty.class, m);
            sb.append(m.getCoveredText() + '\n');
            m.prettyPrint(2, 2, sb, false);
            sb.append('\n');
        }
        System.out.println(sb);
    }
}
