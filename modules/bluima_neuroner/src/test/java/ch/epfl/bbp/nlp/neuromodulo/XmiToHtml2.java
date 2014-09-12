package ch.epfl.bbp.nlp.neuromodulo;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.DELETE_FROM;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.FROM_ANNOTATION;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.TO_ANNOTATION;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineFromPath;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import neuroner.NeuroNER.Missing;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.ruta.type.W;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator;
import ch.epfl.bbp.uima.ae.output.AnnotationTypeWriter;
import ch.epfl.bbp.uima.ae.output.HtmlViewerWriter;
import de.julielab.jules.types.Token;

public class XmiToHtml2 {
    protected static Logger LOG = LoggerFactory
            .getLogger(XmiToHtml2.class);

    static final String ROOT = "/Users/richarde/dev/bluebrain/git/neuromodulo/";

    private AnalysisEngine ruta;
    private AnalysisEngine copyTokens;
    private AnalysisEngine longest;

    @Before
    public void beforeTest() throws Exception {
        ruta = createEngineFromPath(ROOT
                + "descriptor/neuromodulo/NeuromoduloEngine.xml");
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

        JCas jCas = getTestCas("zero one two three four parasol ganglion neuron ahah");

        runPipeline(
                jCas,
                ruta,
                longest,
                createEngine(getSentenceSplitter()),
                copyTokens,
                createEngine(HtmlViewerWriter.class),
                // createEngine(PrintMissingTest.class),//
                createEngine(
                        AnnotationTypeWriter.class,//
                        PARAM_ANNOTATION_CLASS, Missing.class,
                        PARAM_OUTPUT_FILE, "System"));
    }
}