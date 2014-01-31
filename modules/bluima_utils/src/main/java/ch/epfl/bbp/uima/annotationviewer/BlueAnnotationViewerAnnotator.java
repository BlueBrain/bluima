package ch.epfl.bbp.uima.annotationviewer;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import de.julielab.jules.types.Header;

/**
 * Creates an html view of the annotations in the {@link CAS}es.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = {})
public class BlueAnnotationViewerAnnotator extends JCasAnnotator_ImplBase {
    Logger LOG = LoggerFactory.getLogger(BlueAnnotationViewerAnnotator.class);

    @ConfigurationParameter(name = BlueUima.PARAM_OUTPUT_DIR, defaultValue = "annotationViewer/")
    private String outputDir;

    public static final String STYLE_MAP = "styleMap";
    @ConfigurationParameter(name = STYLE_MAP, defaultValue = "viewer/blueStyleMap.xml")
    private String styleMap;

    private BlueAnnotationViewer annotationViewer;
    private File styleMapFile;
    private int i = 0;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            styleMapFile = ResourceHelper.getFile(styleMap);
        } catch (FileNotFoundException e) {
            throw new ResourceInitializationException(
                    ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
                    new Object[] { styleMap });
        }

        // set to /tmp by default
        annotationViewer = new BlueAnnotationViewer(new File("/tmp"));
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        if (BlueCasUtil.isEmptyText(jCas)) {
            LOG.warn("skipping document, text is empty");
        } else {

            // get output directory
            File outputFile;
            try {
                Header header = JCasUtil.selectSingle(jCas, Header.class);
                if (header.getDocId() == null
                        || header.getDocId().equals("null")) {
                    String generatedFilename = outputDir + "unkn" + (i++);
                    LOG.warn("no docId found in CAS, using "
                            + generatedFilename);
                    outputFile = new File(generatedFilename);
                } else {
                    outputFile = new File(outputDir + header.getDocId());
                }
            } catch (Exception e) {
                String generatedFilename = outputDir + "unkn" + (i++);
                LOG.warn("no docId found in CAS, using " + generatedFilename);
                outputFile = new File(generatedFilename);
            }
            LOG.debug("writing AnnotationViewer in "
                    + outputFile.getAbsolutePath());

            try {
                FileUtils.forceMkdir(outputFile);
                annotationViewer.createHtml(jCas, jCas.getTypeSystem(),
                        styleMapFile, outputFile);
            } catch (IOException e) {
                LOG.warn("error createHtml", e);
            }
        }
    }

    public static AnalysisEngine getAE() throws FileNotFoundException,
            ResourceInitializationException {
        return AnalysisEngineFactory.createEngine(getAED());
    }

    public static AnalysisEngineDescription getAED()
            throws FileNotFoundException, ResourceInitializationException {
        return createEngineDescription(BlueAnnotationViewerAnnotator.class);
    }
}
