package ch.epfl.bbp.uima.references;

import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_REFERENCES_ENTRY;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import cc.mallet.classify.Classification;
import cc.mallet.classify.Classifier;
import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.DocumentBlock;

/**
 * Identifies {@link DocumentBlock}s that are references (= bibliographical
 * entries), using a MaxEnt classifier (Mallet).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ReferencesClassifierAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(PrintStates.class);

    /** returned from Mallet, corresponds to corpus folder names */
    static final String LABEL_INSIDE = "I";
    /** returned from Mallet, corresponds to corpus folder names */
    static final String LABEL_OUTSIDE = "O";

    public static final String DEFAULT_MODEL_NAME = "references.model";

    @ConfigurationParameter(name = BlueUima.PARAM_MODEL, //
    defaultValue = DEFAULT_MODEL_NAME, //
    description = "the name of the classifier file (not path, just name) ")
    private String modelName;

    private Classifier classifier;
    private Pipe pipes;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            // load model for inference
            File modelfile = new File(ReferencesHelper.REFERENCES_RESOURCES
                    + "models/" + modelName);
            checkArgument(modelfile.exists(), "no modelFile at " + modelName);
            ObjectInputStream s = new ObjectInputStream(new FileInputStream(
                    modelfile));
            classifier = (Classifier) s.readObject();
            s.close();
            checkArgument(classifier != null);
            pipes = classifier.getInstancePipe();
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {

        try {
            for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
                String label = classify(block.getCoveredText());
                if (label.equals(LABEL_INSIDE)) {
                    block.setLabel(SECTION_REFERENCES_ENTRY);
                }
            }
        } catch (Exception e) {
            int pmId = BlueCasUtil.getHeaderIntDocId(jCas);
            LOG.warn("could not perform inference on " + pmId, e);
        }
    }

    public String classify(String txt) {
        Classification classification = classifier.classify(pipes
                .instanceFrom(new Instance(txt, null, null, null)));
        return classification.getLabeling().getBestLabel().toString();
    }
}
