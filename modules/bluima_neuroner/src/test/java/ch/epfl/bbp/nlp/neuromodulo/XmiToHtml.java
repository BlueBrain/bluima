package ch.epfl.bbp.nlp.neuromodulo;

import static ch.epfl.bbp.nlp.neuroner.NeuronerHelper.NEURONER_HOME;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUimaHelper.SCRIPT_ROOT;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.DELETE_FROM;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.FROM_ANNOTATION;
import static ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator.TO_ANNOTATION;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.File;

import neuromodulo.Neuromodulo.Sentence;

import org.apache.uima.ruta.type.W;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.nlp.neuroner.NeuronerHelper;
import ch.epfl.bbp.uima.ae.CopyAnnotationsAnnotator;
import ch.epfl.bbp.uima.ae.EnsureDocHasHeader;
import ch.epfl.bbp.uima.ae.LinnaeusAnnotator;
import ch.epfl.bbp.uima.ae.output.AnnotationHistogramAnnotator;
import ch.epfl.bbp.uima.ae.output.HtmlViewerWriter;
import ch.epfl.bbp.uima.cr.XCollectionReader;
import ch.epfl.bbp.uima.laucher.Pipeline;
import de.julielab.jules.types.Token;

public class XmiToHtml {
    protected static Logger LOG = LoggerFactory.getLogger(XmiToHtml.class);

    static final String ROOT = "/Users/richarde/dev/bluebrain/git/neuromodulo/";

    public static void main_(String[] args) throws Exception {

        runPipeline(
                createReader(XCollectionReader.class, JULIE_TSD,
                        PARAM_INPUT_DIRECTORY, ROOT + "/output/"),
                createEngine(EnsureDocHasHeader.class),

                createEngine(CopyAnnotationsAnnotator.class, FROM_ANNOTATION,
                        W.class.getName(), TO_ANNOTATION,
                        Token.class.getName(), DELETE_FROM, false),

                createEngine(LinnaeusAnnotator.class),

                createEngine(HtmlViewerWriter.class));
    }

    public static void main(String[] args) throws Exception {

        Pipeline pipe = new Pipeline().setThreads(3);
        pipe.setCr(createReaderDescription(XCollectionReader.class, JULIE_TSD,
                PARAM_INPUT_DIRECTORY, ROOT + "/output/"));

        pipe.add(EnsureDocHasHeader.class);
        pipe.add(CopyAnnotationsAnnotator.class, FROM_ANNOTATION,
                W.class.getName(), TO_ANNOTATION, Token.class.getName(),
                DELETE_FROM, false);
        pipe.add(CopyAnnotationsAnnotator.class, FROM_ANNOTATION,
                Sentence.class.getName(), TO_ANNOTATION,
                de.julielab.jules.types.Sentence.class.getName(), DELETE_FROM,
                false);

        //pipe.addAesFrom(parse(new File(SCRIPT_ROOT + "includes/ners.incl")));
        pipe.addAesFrom(parse(new File(NEURONER_HOME + "src/main/resources/ners.incl")));

        pipe.add(HtmlViewerWriter.class);
        pipe.add(AnnotationHistogramAnnotator.class);
        // pipe.add(SplitInPages.class);
        // pipe.add(BartWriter.class, BartWriter.SPLIT_PER_PAGE, true,
        // BartWriter.PARAM_BATCH_PREFIX, "neuromorpho");
        pipe.run();
    }
}