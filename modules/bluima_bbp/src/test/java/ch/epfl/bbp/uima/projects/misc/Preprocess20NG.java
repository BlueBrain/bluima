package ch.epfl.bbp.uima.projects.misc;

import static ch.epfl.bbp.uima.BlueCasUtil.toList;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.ae.StatsAnnotatorPlus.PARAM_PRINT_EVERY;

import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import scala.collection.Iterator;
import scala.collection.immutable.List;
import ch.epfl.bbp.uima.TopicModelsHelper;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.ae.output.LdaCWriter;
import ch.epfl.bbp.uima.ae.output.LdaCWriter.Writer;
import ch.epfl.bbp.uima.topicmodels.preprocessing.PreprocessingEngine;
import ch.epfl.bbp.uima.topicmodels.readers.TwentyNewsgroupsCollectionReader;
import ch.epfl.bbp.uima.types.FeatureTokens;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import ch.epfl.bbp.uima.uimafit.SimplePipelineBuilder;

/**
 * Uses Mark's preprocessing pipeline
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Preprocess20NG extends JCasAnnotator_ImplBase {

    public static void main(String[] args) throws Exception {

        CollectionReaderDescription cr = CollectionReaderFactory
                .createReaderDescription(
                        TwentyNewsgroupsCollectionReader.class,
                        TypeSystem.JULIE_TSD,
                        TwentyNewsgroupsCollectionReader.CorpusDirPath(),
                        TopicModelsHelper.TOPIC_MODELS_TEST_ROOT()
                                + "20_newsgroups");

        List<AnalysisEngineDescription> preprocessing = PreprocessingEngine
                .getTwentyNewsgroupsPreprocessing(BLUE_UTILS_ROOT
                        + RESOURCES_PATH + "stoplists/mallet_stopwords_en.txt");

        SimplePipelineBuilder p = new SimplePipelineBuilder(cr);
        Iterator<AnalysisEngineDescription> iterator = preprocessing.iterator();
        while (iterator.hasNext()) {
            p.add(iterator.next());
        }
        p.add(Preprocess20NG.class); // this one
        p.add(StatsAnnotatorPlus.class, PARAM_PRINT_EVERY, 100);
        p.process();
    }

    private LdaCWriter.Writer writer;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            writer = new LdaCWriter.Writer(":", "target/_20ng.lda-c",
                    "target/_20ng.lda-c.vocab");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        FeatureTokens featureTokens = JCasUtil.selectSingle(jCas,
                FeatureTokens.class);
        writer.addDocument(toList(featureTokens.getTokens()));
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        try {
            writer.close();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}
