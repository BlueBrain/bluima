package ch.epfl.bbp.uima.jsre;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MODEL_FILE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAINREGION_DICT;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAIN_REGION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Double.valueOf;
import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.kernel.expl.Mapping;
import org.itc.irst.tcc.sre.kernel.expl.MappingFactory;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.UnZipModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.JsreHelper;
import ch.epfl.bbp.uima.jsre.JsreTrainAnnotator.SentenceExample;
import ch.epfl.bbp.uima.types.Cooccurrence;

import com.google.common.base.Preconditions;

/**
 * Filters {@link Cooccurrence}s. Uses SLK, see <br>
 * <i> Claudio Giuliano, Alberto Lavelli, Lorenza Romano. 2006. Exploiting
 * Shallow Linguistic Information for Relation Extraction from Biomedical
 * Literature. </i><br>
 * and<br>
 * <i>French 2012</i>
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN, SENTENCE, COOCCURRENCE })
public class JsreFilterAnnotator extends JCasAnnotator_ImplBase {
	private static Logger LOG = LoggerFactory
			.getLogger(JsreFilterAnnotator.class);

	@ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, defaultValue = BRAIN_REGION,//
	description = "the annotation class for the brain region. Can be "
			+ BRAINREGION_DICT + " as well.")
	protected String brClassStr;
	protected Class<? extends Annotation> brClass;

	@ConfigurationParameter(name = PARAM_MODEL_FILE, mandatory = false, //
	description = "the trained model file")
	private String modelFile;

	private FeatureIndex[] index;
	private Mapping mapping;
	private svm_model model;
	private UnZipModel unZipModel;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		try {

			if (modelFile == null) {
				modelFile = JsreHelper.JSRE_HOME
				// + "resources/models/20131203_model.zip";
				// + "resources/models/20131224_model.zip";
						+ "resources/models/20140211_model.zip";
			}

			// open zip archive
			checkFileExists(modelFile);
			unZipModel = new UnZipModel(new File(modelFile));
			// get the param model
			File paramFile = unZipModel.get("param");
			checkFileExists(paramFile);
			Properties parameter = new Properties();
			parameter.load(new FileInputStream(paramFile));
			// create the mapping factory
			MappingFactory mappingFactory = MappingFactory.getMappingFactory();
			mapping = mappingFactory.getInstance(parameter
					.getProperty("kernel-type"));
			// set the parameters
			mapping.setParameters(parameter);
			// get the number of subspaces
			int subspaceCount = mapping.subspaceCount();
			// read the index
			index = readFeatureIndex(subspaceCount, unZipModel);

			// get the svm model
			File svmModelFile = unZipModel.get("model");
			model = svm.svm_load_model(svmModelFile.getAbsolutePath());

			brClass = (Class<? extends Annotation>) Class.forName(brClassStr);

		} catch (Exception e) {
			throw new ResourceInitializationException(e);
		}
	}

	private static FeatureIndex[] readFeatureIndex(int subspaceCount,
			UnZipModel model) throws IOException {
		FeatureIndex[] index = new FeatureIndex[subspaceCount];
		for (int i = 0; i < subspaceCount; i++) {
			index[i] = new FeatureIndex(true, 1);
			File dicFile = model.get("dic" + i);
			BufferedReader br = new BufferedReader(new FileReader(dicFile));
			index[i].read(br);
			br.close();
			LOG.debug("featureIndex: dic" + i + ", " + dicFile + ", "
					+ index[i].size());
		}
		return index;
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		// convert to SVM sentences
		Pair<List<Cooccurrence>, List<SentenceExample>> toSvm = JsreTrainAnnotator
				.getSvmSentences(jCas, brClass);
		ExampleSet inputSet = new SentenceSetCopy();
		for (SentenceExample se : toSvm.getRight()) {
			inputSet.add(se.s, se.classz, se.id);
		}

		// embed the input data into a feature space
		ExampleSet featureSpaceSet = mapping.map(inputSet, index);

		// predict
		Pair<List<Integer>, List<Double>> predicted = predict(featureSpaceSet,
				model);
		List<Integer> predictedLabels = predicted.getLeft();
		List<Double> predictedProbs = predicted.getRight();

		// remove if not predicted
		List<Cooccurrence> coocs = toSvm.getKey();
		Cooccurrence[] array = coocs.toArray(new Cooccurrence[coocs.size()]);
		Preconditions.checkArgument(predictedLabels.size() == coocs.size(),
				"pmid" + getHeaderDocId(jCas)
						+ " should have same # of elems, but was: coocs="
						+ coocs.size() + " and predictedLabels="
						+ predictedLabels.size());
		for (int i = 0; i < predictedLabels.size(); i++) {
			if (predictedLabels.get(i) == 0) {// 0=>no rel
				array[i].removeFromIndexes();
				// TODO array[i].setHasInteraction(false);
			} else {
				array[i].setConfidence(new Double(predictedProbs.get(i))
						.floatValue());
				array[i].setHasInteraction(true);
			}
		}
	}

	/** labels, prob of label */
	private static Pair<List<Integer>, List<Double>> predict(
			ExampleSet featureSpaceSet, svm_model model) {
		List<Integer> predictedLabels = newArrayList();
		List<Double> predictedProbs = newArrayList();

		for (int i = 0; i < featureSpaceSet.size(); i++) {

			// double target = atof(featureSpaceSet.y(i) + "");
			// Object id = featureSpaceSet.id(i);
			Object a2 = featureSpaceSet.x(i);

			StringTokenizer st = new StringTokenizer(a2 + "", " \t\n\r\f:");

			int m = st.countTokens() / 2;
			svm_node[] x = new svm_node[m];
			for (int j = 0; j < m; j++) {
				x[j] = new svm_node();
				x[j].index = parseInt(st.nextToken());
				x[j].value = valueOf(st.nextToken());
			}

			// 0 for no rel, 2 for relation
			int predictedLabel = (int) svm.svm_predict(model, x);
			double[] probs = new double[x.length];
			svm.svm_predict_probability(model, x, probs);

			predictedLabels.add(predictedLabel);
			predictedProbs.add(probs[predictedLabel]);
		}
		return Pair.of(predictedLabels, predictedProbs);
	}
}
