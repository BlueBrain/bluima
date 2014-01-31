package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.utils.LoadDataFileWriter;

/**
 * Count specified annotationS
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class CountAnnotations extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(CountAnnotations.class);

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE)
    private String outFile;

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASSES, //
    description = "annotation classes name to count")
    private String[] annotationClassesStr;
    private List<Class<? extends Annotation>> annotationClasses = newArrayList();

    private LoadDataFileWriter writer;
    private int docCnt = 0;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        checkArgument(annotationClassesStr.length > 0,
                "no annotations provided");
        try {
            List<String> fields = newArrayList();
            for (String c : annotationClassesStr) {
                try {
                    Class<? extends Annotation> ac = (Class<? extends Annotation>) Class
                            .forName(c);
                    annotationClasses.add(ac);
                    fields.add(ac.getSimpleName());
                } catch (Exception e) {
                    throw new Exception("Could not instantialize class " + c);
                }
            }

            LOG.info("writing LOAD DATA file to {}", outFile);
            writer = new LoadDataFileWriter(new File(outFile), "\t",
                    fields.toArray(new String[fields.size()]));

        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Object[] cnts = new Object[annotationClasses.size()];
        for (int i = 0; i < annotationClasses.size(); i++) {
            Class<? extends Annotation> ac = annotationClasses.get(i);

            Collection<? extends Annotation> matches = select(jCas, ac);
            if (matches != null && !matches.isEmpty())
                cnts[i] = matches.size();
            else
                cnts[i] = 0;
        }
        writer.addLoadLine(cnts);

        // FLUSH once in a while
        if (++docCnt % 1000 == 0) {
            try {
                writer.flush();
            } catch (IOException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
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
