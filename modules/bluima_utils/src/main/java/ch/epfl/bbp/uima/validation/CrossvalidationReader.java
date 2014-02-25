package ch.epfl.bbp.uima.validation;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_NAME;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MAX_NR_RESULTS;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.partition;
import static java.lang.Math.ceil;
import static java.util.Collections.shuffle;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.serialization.BinaryCasReader;

/**
 * Wraps any {@link CollectionReader} to perform cross validation.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class CrossvalidationReader extends JCasCollectionReader_ImplBase {

    Logger LOG = LoggerFactory.getLogger(CrossvalidationReader.class);

    @ConfigurationParameter(name = PARAM_CORPUS_NAME)
    private String corpusName;

    @ConfigurationParameter(name = PARAM_MAX_NR_RESULTS)
    private int maxDocs;

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
    @ConfigurationParameter(name = PARAM_MODE_EVAL, description = "true => eval (=returns 1 slice for eval);"
            + " false => train (=returns e.g. 9 slices for training)  ")
    private boolean modeEval;

    private File store;
    private Iterator<Integer> corpusIt;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {

        checkArgument(maxDocs > 0, "maxDocs should be >> 0");
        store = new File("target/crossvalidation_" + corpusName);
        if (!store.exists()) {
            throw new RuntimeException("no corpus exists at " + store.getPath());
        } else {
            LOG.warn("reading Cross-Validation data in {}", store.getPath());

            try {
                // using an auxiliary int array (representing document ids), to
                // benefit from Guava methods (partition, shuffle) and not load
                // docs in memory
                List<Integer> ids = newArrayList();
                for (int i = 0; i < maxDocs; i++) {
                    ids.add(i);
                }

                shuffle(ids, new Random(seed));
                int splitsSize = (int) ceil((maxDocs + 0d) / (splitsCnt + 0d));
                List<List<Integer>> splits = partition(ids, splitsSize);

                List<Integer> corpus = newArrayList();
                if (modeEval) {
                    // only add the ONE slice for training
                    corpus.addAll(splits.get(slice));
                } else {// add all OTHER slices for training
                    for (int i = 0; i < splits.size(); i++) {
                        if (i != slice) // do not add the slice for training
                            corpus.addAll(splits.get(i));
                    }
                }
                LOG.warn("using {} docs", corpus.size());
                corpusIt = corpus.iterator();
            } catch (Exception e) {
                throw new ResourceInitializationException(e);
            }
        }
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return corpusIt.hasNext();
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {

        Integer docId = corpusIt.next();
        File file = new File(store, docId + ".gz");
        checkFileExists(file);

        try {
            BinaryCasReader.deserialize(file, jCas);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }
}