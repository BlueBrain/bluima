package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.VIEW_GOLD;
import static ch.epfl.bbp.uima.ae.BrainRegionAnnotator.Mode.eval;
import static ch.epfl.bbp.uima.ae.BrainRegionAnnotator.Mode.infer;
import static ch.epfl.bbp.uima.ae.BrainRegionAnnotator.Mode.train;
import static ch.epfl.bbp.uima.ae.Jcas2TokenSequence.TARGET_I;
import static ch.epfl.bbp.uima.ae.Jcas2TokenSequence.TARGET_O;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAIN_REGION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.LINNAEUS_SPECIES;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.MEASURE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ubic.whitetext.BrainRegionPipes;
import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFTrainerByThreadedLabelLikelihood;
import cc.mallet.fst.Experiment;
import cc.mallet.fst.Experiment.Fold;
import cc.mallet.fst.Experiment.Trail;
import cc.mallet.fst.MultiSegmentationEvaluator;
import cc.mallet.fst.LenientMultiSegmentationEvaluator;
import cc.mallet.fst.MyMultiSegmentationEvaluator;
import cc.mallet.fst.Transducer;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Sequence;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Gold;
import ch.epfl.bbp.uima.utils.SilentMallet;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Annotator NER for brain regions mentions, modeled after <a
 * href="http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2741206/">Leon French's
 * NER</a>. Operates in 3 modes:<br>
 * <ul>
 * <li>train: trains a CFR sequence NER based on the provided gold
 * {@link BrainRegion} annotations, and writes out a model;</li>
 * <li>infer: annotates {@link JCas} with {@link BrainRegion}s, using a provided
 * model;</li>
 * <li>eval: performs cross validation on an annotated corpus.</li>
 * </ul>
 * 
 * @author renaud.richardet@epfl.ch
 */
// necessary for training and eval.
@OperationalProperties(multipleDeploymentAllowed = false)
@TypeCapability(inputs = { TOKEN, LINNAEUS_SPECIES, MEASURE }, outputs = { BRAIN_REGION })
public class BrainRegionAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(BrainRegionAnnotator.class);

    static final boolean SILENT_MALLET_LOGGER = true;
    static final boolean PRINT_MISSCLASSIFIED = false;

    static enum Mode {
        train, eval, infer
    };

    public static final String PARAM_MODE = "mode";
    @ConfigurationParameter(name = PARAM_MODE, defaultValue = "infer", mandatory = false,//
    description = "which mode (train, eval, infer)")
    private String modeStr;
    private Mode mode;

    public static final String PARAM_THREADS = "threads";
    @ConfigurationParameter(name = PARAM_THREADS, defaultValue = "3", mandatory = false,//
    description = "how many threads for eval and train")
    private int threads;

    public static final String PARAM_TRIALS = "trials";
    @ConfigurationParameter(name = PARAM_TRIALS, defaultValue = "1", mandatory = false,//
    description = "cross validation: how many trials")
    private int trials;

    @ConfigurationParameter(name = BlueUima.PARAM_MODEL_FILE, mandatory = false, //
    description = "the model file to write to or read from")
    private String modelFile; // for inference and train modes

    private InstanceList trainingInstanceList; // for training and eval modes
    private CRF inferenceCrf = null; // only for inference mode

    // overwrite Mallet default logs (tooo verbose)
    static {
        if (SILENT_MALLET_LOGGER) {
            new SilentMallet();
        }
    }

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            mode = Mode.valueOf(modeStr);
            LOG.debug("Running in {} mode", mode);

            if (mode.equals(infer)) {
                // load model for inference
                checkArgument(new File(modelFile).exists(),
                        "required for inference: no modelFile at " + modelFile);
                ObjectInputStream s = new ObjectInputStream(
                        new FileInputStream(modelFile));
                inferenceCrf = (CRF) s.readObject();
                s.close();
                checkArgument(inferenceCrf != null);
            } else {
                // create empty instanceList, init pipes
                trainingInstanceList = new InstanceList(//
                        new SerialPipes(BrainRegionPipes.getPipes()));
                if (mode.equals(train))
                    checkNotNull(modelFile, "missing model output file");
            }

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        if (mode.equals(infer)) {
            try {
                // create instanceList from model pipes
                InstanceList inferInstanceList = new InstanceList(
                        inferenceCrf.getInputPipe());
                inferInstanceList.addThruPipe(new Instance(jCas, null, pmId,
                        jCas));
                Iterator<Instance> instanceToInferIt = inferInstanceList
                        .iterator();
                for (Sentence s : select(jCas, Sentence.class)) {

                    // transduce
                    Instance instanceToInfer = instanceToInferIt.next();

                    // List<Sequence<Object>> sequences = inferenceCrf
                    // .getMaxLatticeFactory()
                    // .newMaxLattice(inferenceCrf,
                    // (Sequence) instanceToInfer.getData())
                    // .bestOutputSequences(5);

                    @SuppressWarnings({ "unchecked", "rawtypes" })
                    Sequence<String> labels = inferenceCrf
                            .transduce((Sequence) instanceToInfer.getData());

                    // post-aggregate I-labels of Tokens into BrainRegions
                    Integer begin = null, end = null; // 2-state FSA: O/I
                    List<Token> tokens = selectCovered(Token.class, s);
                    checkArgument(tokens.size() == labels.size(),
                            "hum, labels must match tokens");
                    int j = 0;
                    for (Token token : tokens) {
                        String label = labels.get(j++);
                        if (label.equals(TARGET_I) && begin == null) {
                            // entering stateI (a new annotation)
                            begin = token.getBegin();
                        } else if (label.equals(TARGET_O) && begin != null) {
                            // exiting state I --> create it
                            new BrainRegion(jCas, begin, end).addToIndexes();
                            begin = null;
                        }
                        end = token.getEnd();
                    }

                    if (begin != null) {
                        // exiting machine in state I --> create it
                        new BrainRegion(jCas, begin, end).addToIndexes();
                    }
                }
            } catch (Exception e) {
                LOG.warn("could not perform inference on sentence " + pmId, e);
            }

        } else { // train or eval

            // copy from gold BR to (defaultview) annotation called Gold
            try {
                if (exists(jCas, Gold.class))
                    throw new RuntimeException("has already Gold!");

                JCas goldView = jCas.getView(VIEW_GOLD);
                for (BrainRegion brGold : select(goldView, BrainRegion.class)) {
                    new Gold(jCas, brGold.getBegin(), brGold.getEnd())
                            .addToIndexes();
                }
            } catch (CASException e) {
                throw new RuntimeException(e);
            }

            Instance instance = new Instance(jCas, null, pmId, jCas);
            trainingInstanceList.addThruPipe(instance);
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();

        try {

            if (mode.equals(eval)) { // cross validate
                Experiment experiment = new Experiment();
                final int folds = 8;
                LOG.info("starting {} trials of {}-fold cross validation",
                        trials, folds);
                for (int t = 0; t < trials; t++) {
                    Trail trail = new Trail();
                    cc.mallet.types.InstanceList.CrossValidationIterator crossValidationIt = trainingInstanceList
                            .crossValidationIterator(folds,
                                    new Random().nextInt());
                    int f = 0;
                    while (crossValidationIt.hasNext()) {
                        LOG.info(">>>> Trial {} Fold {} >>>>>>>>>>>>", t, f);
                        InstanceList[] il = crossValidationIt.nextSplit();
                        CRF crf = new CRF(trainingInstanceList.getPipe(), null);
                        configure(crf, trainingInstanceList);
                        trail.add(evaluate(f++, crf, il[0], il[1], threads));
                    }
                    experiment.add(trail);
                }
                LOG.error("RRReport ", experiment.getReport());
                // / LOG.error("Done here, forcing exit");
                // / System.exit(0);

            } else if (mode.equals(train)) {
                // train model
                CRF crf = new CRF(trainingInstanceList.getPipe(), null);
                configure(crf, trainingInstanceList);
                CRFTrainerByThreadedLabelLikelihood trainer = new CRFTrainerByThreadedLabelLikelihood(
                        crf, threads);
                trainer.train(trainingInstanceList);
                LOG.info("done training CRF");
                // write model
                crf.write(new File(modelFile));
            }

        } catch (Exception e) {
            LOG.error("woops", e);
            throw new AnalysisEngineProcessException(e);
        }
    }

    private static void configure(CRF _crf, InstanceList trainingSet) {

        // crf.addStatesForLabelsConnectedAsIn(trainingSet);
        // CRFTrainerByLabelLikelihood trainer = new
        // CRFTrainerByLabelLikelihood(
        // crf);
        // trainer.setGaussianPriorVariance(1d);

        int[] orders = new int[] { 1 };
        Pattern forbiddenPat = Pattern.compile("\\s");
        Pattern allowedPat = Pattern.compile(".*");

        String outside = Jcas2TokenSequence.TARGET_O;
        String startName = _crf.addOrderNStates(trainingSet, orders, null,
                outside, forbiddenPat, allowedPat, true);
        // String startName = crf.addOrderNStates(trainingSet, orders, null,
        // null, null, null, true);

        for (int i = 0; i < _crf.numStates(); i++)
            _crf.getState(i).setInitialWeight(Transducer.IMPOSSIBLE_WEIGHT);
        _crf.getState(startName).setInitialWeight(0.0);
    }

    // private static void evaluate_old(int iterationId, CRF crf,
    // InstanceList trainingSet, InstanceList testingSet) {
    //
    // crf.addStatesForLabelsConnectedAsIn(trainingSet);
    // CRFTrainerByLabelLikelihood trainer = new CRFTrainerByLabelLikelihood(
    // crf);
    //
    // TokenAccuracyEvaluator evaluator = new TokenAccuracyEvaluator(
    // testingSet, "ttt");
    // trainer.addEvaluator(evaluator);
    // trainer.train(trainingSet);
    // // print results
    // LOG.info("iteration {}: accuracy={}", iterationId,
    // evaluator.getAccuracy("ttt"));
    // }

    /** MultiSegmentationEvaluator */
    private static Fold evaluate(int iterationId, CRF crf,
            InstanceList trainingSet, InstanceList testingSet, int threads) {

        // TODO 1 see if it works (better) with simpler setup

        CRFTrainerByThreadedLabelLikelihood trainer = new CRFTrainerByThreadedLabelLikelihood(
                crf, threads);
        // CRFTrainerByLabelLikelihood trainer = new
        // CRFTrainerByLabelLikelihood(crf);
        trainer.setGaussianPriorVariance(1);

        String[] tags = new String[] { Jcas2TokenSequence.TARGET_I };
        String[] continueTags = tags;

        trainer.train(trainingSet);

        MyMultiSegmentationEvaluator eval = new MyMultiSegmentationEvaluator(
                new InstanceList[] { testingSet },//
                new String[] { "TTesting" }, tags, continueTags,
                PRINT_MISSCLASSIFIED);
        eval.evaluate(trainer); // eval at end of training

//        MultiSegmentationEvaluator evalOrig = new MultiSegmentationEvaluator(
//                new InstanceList[] { testingSet },//
//                new String[] { "TTesting" }, tags, continueTags);
//        evalOrig.evaluate(trainer); // eval at end of training

        LenientMultiSegmentationEvaluator evalLenient = new LenientMultiSegmentationEvaluator(
                new InstanceList[] { testingSet },//
                new String[] { "TTesting" }, tags, continueTags,
                PRINT_MISSCLASSIFIED);
        evalLenient.evaluate(trainer);

        LOG.info("FOLD {} --> " + eval + " lenient: {}", iterationId,
                evalLenient);
        return new Fold(eval);

        // TODO trainer.trainWithFeatureInduction

        // TODO
        // if ( runner.isInduceFeatures() ) {
        // // Number of maximizer iterations between each call to the Feature
        // Inducer. (10 in simpletagger and TUI)
        // int numIterationsBetweenFeatureInductions = 10;
        //
        // // Maximum number of rounds of feature induction. (20 in
        // simpleTagger, 99 in TUI)
        // int numFeatureInductions = 20;
        //
        // // Maximum number of features to induce at each round of induction.
        // (500 in simpletagger, 200 in TUI)
        // int numFeaturesPerFeatureInduction = 300;
        // // splits = new double[] {.1, .2, .5, .7}
        //
        // crft.trainWithFeatureInduction( training, null, testing, eval,
        // iterations,
        // numIterationsBetweenFeatureInductions, numFeatureInductions,
        // numFeaturesPerFeatureInduction, 0.5,
        // false, null );
        // } else {
        // // before
        // converged = crft.train( training ); // , iterations );
        // }
    }
}
