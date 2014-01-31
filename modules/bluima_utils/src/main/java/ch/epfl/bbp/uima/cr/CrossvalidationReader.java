package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Lists.partition;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.shuffle;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.iteratePipeline;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.pipeline.JCasIterator;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.CasCopier;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.serialization.BinaryCasReader;
import ch.epfl.bbp.uima.ae.serialization.BinaryCasWriter;

/**
 * Wraps any {@link CollectionReader} to perform cross validation.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class CrossvalidationReader extends JCasCollectionReader_ImplBase {

    Logger LOG = LoggerFactory.getLogger(CrossvalidationReader.class);

    public static final String PARAM_WRAPPED_CR = "wrappedCollectionReader";
    @ConfigurationParameter(name = PARAM_WRAPPED_CR, description = "an array describing the wrapped collection reader. First object is the class, others are the params")
    private Object[] wrappedCr;

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, defaultValue = "target/")
    private String outputDir;

    public static final String PARAM_SEED = "seed";
    @ConfigurationParameter(name = PARAM_SEED, defaultValue = "17")
    private int seed;
    public static final String PARAM_SPLITS = "splits";
    @ConfigurationParameter(name = PARAM_SPLITS, defaultValue = "10")
    private int splitsCnt;
    public static final String PARAM_SLICE = "slice";
    @ConfigurationParameter(name = PARAM_SLICE, description = "which slice of the above split")
    private int slice;
    public static final String PARAM_MODE_EVAL = "modeEval";
    @ConfigurationParameter(name = PARAM_MODE_EVAL, description = "train (=returns e.g. 9 slices for training) or eval (=returns 1 slice for eval) ")
    private boolean modeEval;

    private Stack<JCas> corpus = new Stack<JCas>();

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {

        checkArgument(wrappedCr.length > 0,
                "wrappedCollectionReader should have at least one argument");
        checkArgument(
                (wrappedCr.length + 1) % 2 == 0,
                "wrappedCollectionReader should have an odd number of arguments: "
                        + "First object is the class, others are the params that come in pairs");
        try {
            @SuppressWarnings("unchecked")
            Class<? extends CollectionReader> crClass = (Class<? extends CollectionReader>) Class
                    .forName((String) wrappedCr[0]);
            File store = new File(outputDir + "/crossvalidation_"
                    + crClass.getSimpleName());

            if (!store.exists()) {
                LOG.info("creating Cross-Validation data in {}",
                        store.getPath());
                store.mkdirs();
                AnalysisEngine writer = createEngine(BinaryCasWriter.class,
                        PARAM_OUTPUT_DIR, store);
                if (wrappedCr.length > 0) {
                    Object[] crParams = copyOfRange(wrappedCr, 1,
                            wrappedCr.length);
                    runPipeline(createReader(crClass, JULIE_TSD, crParams),
                            writer);
                } else {
                    runPipeline(createReader(crClass, JULIE_TSD), writer);
                }
            }

            LOG.info("reading Cross-Validation data from {}", store.getPath());

            List<JCas> jCases = newLinkedList();
            JCasIterator it = iteratePipeline(
                    createReaderDescription(BinaryCasReader.class, JULIE_TSD,
                            PARAM_INPUT_DIRECTORY, store)).iterator();
            while (it.hasNext()) {
                jCases.add(it.next());
            }
            checkArgument(jCases.size() > 0, "could not find any documents in "
                    + store.getPath());
            LOG.info("done reading {} documents", jCases.size());

            shuffle(jCases, new Random(seed));
            List<List<JCas>> splits = partition(jCases, splitsCnt);

            if (modeEval) {
                // only add the ONE slice for training
                corpus.addAll(splits.get(slice));
            } else {// add all OTHER slices for training
                for (int i = 0; i < splits.size(); i++) {
                    if (i != slice) // do not add the slice for training
                        corpus.addAll(splits.get(i));
                }
            }
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return !corpus.empty();
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {
        CasCopier.copyCas(corpus.pop().getCas(), jCas.getCas(), true);
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }
}
