package ch.epfl.bbp.nlp.tables;

import static ch.epfl.bbp.nlp.tables.TableAnnotator.Mode.eval;
import static ch.epfl.bbp.nlp.tables.TableAnnotator.Mode.infer;
import static ch.epfl.bbp.nlp.tables.TableAnnotator.Mode.train;
import static ch.epfl.bbp.nlp.tables.TableAnnotator.Mode.valueOf;
import static ch.epfl.bbp.nlp.tables.TableCorpus.getAnnotation;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderSource;
import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MODE;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFTrainerByThreadedLabelLikelihood;
import cc.mallet.fst.Experiment;
import cc.mallet.fst.Experiment.Fold;
import cc.mallet.fst.Experiment.Trail;
import cc.mallet.fst.MyMultiSegmentationEvaluator;
import cc.mallet.fst.Transducer;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Sequence;
import ch.epfl.bbp.nlp.tables.pipes.Jcas2TokenSequence;
import ch.epfl.bbp.nlp.tables.pipes.TablePipes;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.DataTable;
import ch.epfl.bbp.uima.types.DocumentLine;
import ch.epfl.bbp.uima.types.DocumentPage;
import ch.epfl.bbp.uima.utils.SilentMallet;

/**
 * Table detection. Operates in 3 modes:<br>
 * <ul>
 * <li>train: trains a CFR sequence based on the provided gold;</li>
 * <li>infer: ;</li>
 * <li>eval: performs cross validation on an annotated corpus.</li>
 * </ul>
 * 
 * @author Samuel.Kimoto@epfl.ch
 * @author renaud.richardet@epfl.ch
 */
public class TableAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(TableAnnotator.class);

    static final boolean SILENT_MALLET_LOGGER = true;
    static final boolean PRINT_MISSCLASSIFIED = true;

    public static final String TARGET_I = "I";
    public static final String TARGET_O = "O";

    static enum Mode {
        train, eval, infer
    };

    @ConfigurationParameter(name = PARAM_CORPUS_ROOT, description = "path to 'table_corpus' root ")
    private String corpusRoot;

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

    // overwrite Mallet default logs (tooo verbose)
    static {
        if (SILENT_MALLET_LOGGER) {
            new SilentMallet();
        }
    }

    private InstanceList trainingInstanceList; // for training and eval modes
    private CRF inferenceCrf = null; // only for inference mode

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        mode = valueOf(modeStr);
        checkNotNull(mode);
        // LOG.debug("Running in {} mode", mode);

        if (mode.equals(infer)) {
            // load model for inference
            checkFileExists(modelFile);
            try {
                ObjectInputStream s = new ObjectInputStream(
                        new FileInputStream(modelFile));
                inferenceCrf = (CRF) s.readObject();
                s.close();
            } catch (Exception e) {
                throw new ResourceInitializationException(e);
            }
            checkNotNull(inferenceCrf != null);
        } else {
            // create empty instanceList, init pipes
            try {
                trainingInstanceList = new InstanceList(//
                        new SerialPipes(TablePipes.getPipes()));
            } catch (Exception e) {
                throw new ResourceInitializationException(e);
            }
            checkFileExists(corpusRoot);
            if (mode.equals(train))
                checkFileExists(modelFile);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        int pmId = getHeaderIntDocId(jCas);
        String source = getHeaderSource(jCas);

        if (mode.equals(infer)) {

            try {
                InstanceList inferInstanceList = new InstanceList(
                        inferenceCrf.getInputPipe());
                inferInstanceList.addThruPipe(new Instance(jCas, null, pmId,
                        source));

                Iterator<Instance> instanceToInferIt = inferInstanceList
                        .iterator();

                // LATER maybe faster Set<Entry<DocumentPage,
                // Collection<DocumentLine>>> pages = indexCovered(
                // jCas, DocumentPage.class, DocumentLine.class)
                // .entrySet();

                for (DocumentPage page : select(jCas, DocumentPage.class)) {

                    // transduce
                    Instance instanceToInfer = instanceToInferIt.next();
                    @SuppressWarnings({ "unchecked", "rawtypes" })
                    Sequence<String> labels = inferenceCrf
                            .transduce((Sequence) instanceToInfer.getData());

                    // post-aggregate I-labels of DocumentLine into DataTable
                    Integer begin = null, end = null; // 2-state FSA: O/I
                    List<DocumentLine> lines = selectCovered(
                            DocumentLine.class, page);
                    checkArgument(lines.size() == labels.size(),
                            "hum, #labels must match #lines");
                    int j = 0;
                    for (DocumentLine line : lines) {
                        String label = labels.get(j++);
                        if (label.equals(TARGET_I) && begin == null) {
                            // entering stateI (a new annotation)
                            begin = line.getBegin();
                        } else if (label.equals(TARGET_O) && begin != null) {
                            // exiting state I --> create annot
                            new DataTable(jCas, begin, end).addToIndexes();
                            begin = null;
                        }
                        end = line.getEnd();
                    }

                    if (begin != null) {
                        // exiting machine in state I --> create annot
                        new DataTable(jCas, begin, end).addToIndexes();
                    }
                }
            } catch (Exception e) {
                LOG.warn("could not perform inference on Document " + pmId, e);
            }

        } else { // train or eval

            try {
                List<GoldAnnotation> goldAnnotations = getAnnotation(
                        corpusRoot, pmId);

                Instance instance = new Instance(jCas, goldAnnotations, pmId,
                        source);
                trainingInstanceList.addThruPipe(instance);
            } catch (IOException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {

        if (mode.equals(eval)) { // cross validate
            Experiment experiment = new Experiment();
            final int folds = 8;

            for (int t = 0; t < trials; t++) {
                Trail trail = new Trail();
                cc.mallet.types.InstanceList.CrossValidationIterator crossValidationIt = trainingInstanceList
                        .crossValidationIterator(folds, new Random().nextInt());
                int f = 0;
                while (crossValidationIt.hasNext()) {
                    InstanceList[] il = crossValidationIt.nextSplit();
                    CRF crf = new CRF(trainingInstanceList.getPipe(), null);
                    configure(crf, trainingInstanceList);
                    trail.add(evaluate(f++, crf, il[0], il[1], threads));
                }
                experiment.add(trail);
            }
            // System.exit(0);

        } else if (mode.equals(train)) {
            // train model
            CRF crf = new CRF(trainingInstanceList.getPipe(), null);
            configure(crf, trainingInstanceList);
            CRFTrainerByThreadedLabelLikelihood trainer = new CRFTrainerByThreadedLabelLikelihood(
                    crf, threads);
            trainer.train(trainingInstanceList);
            // write model
            crf.write(new File(modelFile));
        }
    }

    private static void configure(CRF _crf, InstanceList trainingSet) {

        int[] orders = new int[] { 1 };
        Pattern forbiddenPat = Pattern.compile("\\s");
        Pattern allowedPat = Pattern.compile(".*");

        String outside = Jcas2TokenSequence.TARGET_O;
        String startName = _crf.addOrderNStates(trainingSet, orders, null,
                outside, forbiddenPat, allowedPat, true);

        for (int i = 0; i < _crf.numStates(); i++)
            _crf.getState(i).setInitialWeight(Transducer.IMPOSSIBLE_WEIGHT);
        _crf.getState(startName).setInitialWeight(0.0);
    }

    /** MultiSegmentationEvaluator */
    private static Fold evaluate(int iterationId, CRF crf,
            InstanceList trainingSet, InstanceList testingSet, int threads) {

        CRFTrainerByThreadedLabelLikelihood trainer = new CRFTrainerByThreadedLabelLikelihood(
                crf, threads);
        trainer.setGaussianPriorVariance(1);

        String[] tags = new String[] { Jcas2TokenSequence.TARGET_I };
        String[] continueTags = tags;

        MyMultiSegmentationEvaluator eval = new MyMultiSegmentationEvaluator(
                new InstanceList[] { testingSet },//
                new String[] { "TTesting" }, tags, continueTags,
                PRINT_MISSCLASSIFIED);
        trainer.train(trainingSet);
        eval.evaluate(trainer); // eval at end of training
        LOG.warn(eval.getReport());

        return new Fold(eval);
    }
}
