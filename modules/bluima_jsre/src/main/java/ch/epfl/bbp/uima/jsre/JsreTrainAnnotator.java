package ch.epfl.bbp.uima.jsre;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAINREGION_DICT;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAIN_REGION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static com.google.common.collect.Lists.newArrayList;
import static java.io.File.createTempFile;
import static java.lang.Integer.parseInt;
import static org.apache.uima.fit.util.JCasUtil.indexCovered;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.itc.irst.tcc.sre.data.ArgumentSet;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.data.Word;
import org.itc.irst.tcc.sre.kernel.expl.Mapping;
import org.itc.irst.tcc.sre.kernel.expl.MappingFactory;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.PorterStemmer;
import org.itc.irst.tcc.sre.util.Vector;
import org.itc.irst.tcc.sre.util.ZipModel;
import org.itc.irst.tcc.sre.util.svm_train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.typesystem.To;

import com.google.common.collect.Lists;

import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Trains a SVM to filter Cooccurrences. Uses SLK, see <br>
 * <i> Claudio Giuliano, Alberto Lavelli, Lorenza Romano. Exploiting Shallow
 * Linguistic Information for Relation Extraction from Biomedical Literature.
 * </i>
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN, SENTENCE, COOCCURRENCE })
public class JsreTrainAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(JsreTrainAnnotator.class);

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, defaultValue = BRAIN_REGION,//
    description = "the annotation class for the brain region. Can be "
            + BRAINREGION_DICT + " as well.")
    protected String brClassStr;
    protected Class<? extends Annotation> brClass;

    private ExampleSet inputSet;

    private String modelFilePath = "target/model.zip";

    private Properties parameter;
    private static final String BR_LABEL = "BR";

    // private static final int OFFSET = 0;
    private static final int FORM = 1;
    private static final int LEMMA = 2;
    private static final int POS = 3;
    // entity type (e.g. PER, ORG, LOC)
    private static final int ENTITY_TYPE = 4;
    // candidate enity role (e.g. T, A, O)
    private static final int LABEL = 5;
    private static final int STEM = 6;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        inputSet = new SentenceSetCopy();
        parameter = new Properties();
        // rem: these are the defaults from Train.main
        parameter.setProperty("cache-size", "128");
        parameter.setProperty("kernel-type", "SL");
        parameter.setProperty("n-gram", "3");
        parameter.setProperty("window-size", "2");

        try {
            brClass = (Class<? extends Annotation>) Class.forName(brClassStr);
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Pair<List<Cooccurrence>, List<SentenceExample>> s = getSvmSentences(
                jCas, brClass);
        for (SentenceExample se : s.getRight()) {
            inputSet.add(se.s, se.classz, se.id);
        }
    }

    public static class SentenceExample {
        org.itc.irst.tcc.sre.data.Sentence s;
        int classz;
        String id;

        public SentenceExample(org.itc.irst.tcc.sre.data.Sentence s,
                int classz, String id) {
            this.s = s;
            this.classz = classz;
            this.id = id;
        }
    }

    /**
     * Converts a jCas' {@link Cooccurrence}s to
     * {@link org.itc.irst.tcc.sre.data.Sentence}s
     * 
     * @param <brClass>
     */
    static <B extends Annotation> Pair<List<Cooccurrence>, List<SentenceExample>> getSvmSentences(
            JCas jCas, final Class<B> brClass) {
        List<Cooccurrence> retCooc = newArrayList();
        List<SentenceExample> retSentences = newArrayList();

        String pmId = getHeaderDocId(jCas);
        int sentenceId = 0;

        Map<Sentence, Collection<B>> brIdx = indexCovered(jCas, Sentence.class,
                brClass);

        Map<Sentence, Collection<Token>> tokenIdx = indexCovered(jCas,
                Sentence.class, Token.class);

        for (Entry<Sentence, Collection<Cooccurrence>> uSentences : indexCovered(
                jCas, Sentence.class, Cooccurrence.class).entrySet()) {
            // System.out.println(pmId + " " + sentenceId
            // + "-----------------------------------------------");

            Sentence uSentence = uSentences.getKey();
            Collection<Token> tokens = tokenIdx.get(uSentence);

            for (Cooccurrence c : uSentences.getValue()) {
                // System.out.println(To.string(c)
                // + "####################################");

                // all BRs of this sentence, ordered by br.getBegin()
                List<B> a = Lists.newArrayList(brIdx.get(uSentence));
                Collections.sort(a, new java.util.Comparator<B>() {
                    @Override
                    public int compare(B br1, B br2) {
                        return new Integer(br1.getBegin()).compareTo(br2
                                .getBegin());
                    }
                });
                Annotation[] allBrs = a.toArray(new Annotation[a.size()]);

                Annotation br1 = c.getFirstEntity(), br2 = c.getSecondEntity();
                boolean matchedBr1 = false, matchedBr2 = false;

                // Prin.t(c);
                List<Word> words = newArrayList();
                int tokenId = 0;
                Iterator<Token> tokenIt = tokens.iterator();
                while (tokenIt.hasNext()) {
                    Token token = tokenIt.next();
                    // System.out.println(To.string(token));

                    String[] feats = new String[7];
                    /*-
                    see Word code:
                    tokenid      incremental position of the token in the sentence
                    token        surface form "Also" "being" "Ralph_K._Winter"
                    lemma        lemma        "also" "be"    "Ralph_K._Winter"
                    POS          POS tag      "RB"   "VBG"    "NNP"
                    entity_type  possible type of the token (LOC, PER, ORG) "O" for token that are not entities
                    entity_label A|T|O this attribute is to label the candidate pair. Each example in the jSRE 
                                 file has two entities labelled respectively A (agent, first argument) and T 
                                 (target, second argument) of the relation, they are the candidate entities 
                                 possibly relating, any other entity is labelled "O".
                     */

                    // check if token covered by one br
                    Annotation coveringBr = null;
                    for (int i = 0; i < allBrs.length; i++) {
                        if (allBrs[i] != null
                                && token.getEnd() > allBrs[i].getBegin()) {
                            coveringBr = allBrs[i];
                            allBrs[i] = null;
                        }
                    }
                    // then, skip these covered tokens
                    // iter tokens until we have reached the end of the BR
                    if (coveringBr != null) {
                        boolean endOfBR = false;
                        while (!endOfBR && tokenIt.hasNext()) {
                            Token nextT = tokenIt.next();
                            if (nextT.getEnd() >= coveringBr.getEnd())
                                endOfBR = true;
                        }
                    }

                    // covered by a BR from this sentence
                    if (coveringBr != null) {
                        // System.out.println("BR:" + To.string(coveringBr));

                        feats[FORM] = coveringBr.getCoveredText();
                        feats[LEMMA] = "NN";// FIXME right?
                        feats[POS] = token.getPos();
                        feats[ENTITY_TYPE] = BR_LABEL;

                        // matched by one of the BR from this Cooc?
                        boolean matched = false;
                        if (coveringBr.equals(br1)) {
                            matched = true;
                            matchedBr1 = true;
                        } else if (coveringBr.equals(br2)) {
                            matched = true;
                            matchedBr2 = true;
                        }
                        if (matched) {
                            feats[LABEL] = Word.TARGET_LABEL;
                        } else {
                            feats[LABEL] = Word.OTHER_LABEL;
                        }

                    } else { // a token
                        feats[FORM] = token.getCoveredText();
                        feats[LEMMA] = token.getLemmaStr();// FIXME ensure lemma
                        feats[POS] = token.getPos();
                        feats[ENTITY_TYPE] = Word.OTHER_LABEL;
                        feats[LABEL] = Word.OTHER_LABEL;
                    }

                    feats[STEM] = PorterStemmer.getStemmer().stem(feats[FORM]);
                    Word w = new Word(feats, tokenId++);
                    words.add(w);

                }

                boolean parsedOk = true;
                // check both BR have been found
                if (!matchedBr1) {
                    parsedOk = false;
                    LOG.error("did not match br1: " + To.string(br1));
                    // throw new RuntimeException("did not match br1: "
                    // + To.string(br1));
                } else if (!matchedBr2) {
                    parsedOk = false;
                    LOG.error("did not match br2: " + To.string(br2));
                    // throw new RuntimeException("did not match br2: "
                    // + To.string(br2));
                }
                // check all other BRs have been matched
                for (int i = 0; i < allBrs.length; i++) {
                    if (allBrs[i] != null) {
                        parsedOk = false;
                        LOG.error("did not match br: " + To.string(allBrs[i]));
                        // throw new RuntimeException("did not match br: "
                        // + To.string(allBrs[i]));
                    }
                }

                if (parsedOk) {
                    // add libsvm sentence
                    org.itc.irst.tcc.sre.data.Sentence sentence = new org.itc.irst.tcc.sre.data.Sentence(
                            words);
                    boolean label = c.getConfidence() == 1.0f ? true : false;
                    int classz = label ? 2 : 0;
                    String id = pmId + "_" + sentenceId++;

                    retCooc.add(c);
                    retSentences.add(new SentenceExample(sentence, classz, id));
                }
            }
        }
        return Pair.of(retCooc, retSentences);
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        try {

            LOG.info("train a relation extraction model");

            // create zip archive
            // ZipModel model = new ZipModel(parameter.modelFile());
            File modelFile = new File(modelFilePath);
            ZipModel model = new ZipModel(modelFile);

            // get the class freq
            int[] freq = classFreq(inputSet);

            // calculate the class weight
            double[] weight = classWeigth(freq);

            // find argument types
            ArgumentSet.getInstance().init(inputSet);

            // set the relation type
            int count = inputSet.getClassCount();
            // setRelationType(count);

            LOG.debug("number of classes: " + count);
            // LOG.info("learn " + (relationType == DIRECTED_RELATION ?
            // "directed" : "undirected") + " relations (" + relationType +
            // ")");

            // create the mapping factory
            MappingFactory mappingFactory = MappingFactory.getMappingFactory();
            Mapping mapping = mappingFactory.getInstance(parameter
                    .getProperty("kernel-type"));

            // set the command line parameters
            mapping.setParameters(parameter);

            // get the number of subspaces
            int subspaceCount = mapping.subspaceCount();
            LOG.debug("number of subspaces: " + subspaceCount);

            // create the index
            FeatureIndex[] index = new FeatureIndex[subspaceCount];
            for (int i = 0; i < subspaceCount; i++)
                index[i] = new FeatureIndex(false, 1);

            // embed the input data into a feature space
            LOG.info("embed the training set");
            ExampleSet outputSet = mapping.map(inputSet, index);
            LOG.debug("embedded training set size: " + outputSet.size());

            // if not specified, calculate SVM parameter C
            double c = calculateC(outputSet);
            LOG.info("cost parameter C = " + c);

            // save the training set
            File training = saveExampleSet(outputSet, model);

            // save the indexes
            saveFeatureIndexes(index, model);

            // train the svm
            svmTrain(training, c, weight, model);

            // save param
            saveParameters(model);

            model.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new AnalysisEngineProcessException(e);
        }
    }

    public static final int MAX_NUMBER_OF_CLASSES = 20;

    private static int[] classFreq(ExampleSet set) throws IOException {
        // small example set can have only one class
        if (set.getClassCount() == 1)
            return new int[] { 1, 1, 1 };

        LOG.debug("class count: " + set.getClassCount());
        // int[] c = new int[set.getClassCount()];
        int[] c = new int[MAX_NUMBER_OF_CLASSES];

        Iterator<?> it = set.classes();
        while (it.hasNext()) {
            int y = Integer.parseInt(it.next().toString());
            int f = set.classFreq(y);
            c[y] = f;
            LOG.info("class " + y + "  freq " + f);
        }
        return c;
    }

    private static double[] classWeigth(int[] c) {
        double[] w = new double[c.length];
        for (int i = 1; i < c.length; i++) {
            if (c[i] != 0)
                w[i] = (double) c[0] / c[i];
            LOG.debug("weight[" + i + "] = " + w[i]);
        }
        return w;
    }

    // calculate parameter C of SVM
    //
    // To allow some flexibility in separating the categories,
    // SVM models have a cost parameter, C, that controls the
    // trade off between allowing training errors and forcing
    // rigid margins. It creates a soft margin that permits
    // some misclassifications. Increasing the value of C
    // increases the cost of misclassifying points and forces
    // the creation of a more accurate model that may not
    // generalize well
    private static double calculateC(ExampleSet data) {
        // String svmCost = parameter.getProperty("svm-cost");
        // if (svmCost != null)
        // return Integer.parseInt(svmCost);

        LOG.info("calculate default SVM cost parameter C");

        // double c = 1;
        double avr = 0;

        // the example set is normalized
        // all vectors have the same norm
        for (int i = 0; i < data.size(); i++) {
            Vector v = (Vector) data.x(i);
            double norm = v.norm();
            // logger.info(i + ", norm = " + norm);
            // if (norm > c)
            // c = norm;
            avr += norm;
        }

        return 1 / Math.pow(avr / data.size(), 2);
    }

    // save the embedded training set
    private static File saveExampleSet(ExampleSet outputSet, ZipModel model)
            throws IOException {
        LOG.info("save the embedded training set");

        File tmp = createTempFile("train", null);
        tmp.deleteOnExit();

        BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
        outputSet.write(out);
        out.close();

        // add the example set to the model
        model.add(tmp, "train");

        return tmp;
    }

    // save feature index
    private static void saveFeatureIndexes(FeatureIndex[] index, ZipModel model)
            throws IOException {
        LOG.info("save feature index (" + index.length + ")");

        // save the indexes
        for (int i = 0; i < index.length; i++) {
            LOG.debug("dic" + i + " size " + index[i].size());

            File tmp = createTempFile("dic" + i, null);
            tmp.deleteOnExit();

            BufferedWriter bwd = new BufferedWriter(new FileWriter(tmp));
            index[i].write(bwd);
            bwd.close();

            // add the dictionary to the model
            model.add(tmp, "dic" + i);
        }
    }

    private void saveParameters(ZipModel model) throws IOException {

        File paramFile = createTempFile("param", null);
        paramFile.deleteOnExit();

        // parameter.store(new FileOutputStream(paramFile), "model parameters");
        parameter.store(new FileOutputStream(paramFile), null);

        // add the param to the model
        model.add(paramFile, "param");
    }

    private void svmTrain(File svmTrainingFile, double c, double[] weight,
            ZipModel model) throws Exception {
        LOG.info("train SVM model");

        File svmModelFile = createTempFile("model", null);
        svmModelFile.deleteOnExit();

        int cache = 128;
        if (parameter.getProperty("cache-size") != null)
            cache = parseInt(parameter.getProperty("cache-size"));

        new svm_train().run(svmTrainingFile, svmModelFile, c, cache, weight);

        // add the data set to the model
        model.add(svmModelFile, "model");
    }
}
