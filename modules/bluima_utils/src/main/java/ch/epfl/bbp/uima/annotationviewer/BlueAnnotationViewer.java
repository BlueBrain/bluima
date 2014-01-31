package ch.epfl.bbp.uima.annotationviewer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.CasToInlineXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.output.BartWriter;

/**
 * Creates an html annotation view <br>
 * use {@link BartWriter} instead
 */
@Deprecated
public class BlueAnnotationViewer {
    Logger LOG = LoggerFactory.getLogger(BlueAnnotationViewer.class);

    protected BlueAnnotationViewGenerator generator;
    private String defaultCasViewName = CAS.NAME_DEFAULT_SOFA;

    public BlueAnnotationViewer(File outDir) {// TODO remove outdir
        generator = new BlueAnnotationViewGenerator(outDir);
    }

    public void createHtml(JCas jCas, TypeSystem typeSystem, File styleMapFile,
            File outputDirectory) throws IOException {
        try {

            FileUtils.forceMkdir(outputDirectory);
            generator.setOutputDirectory(outputDirectory);

            CAS cas = jCas.getCas();

            // get the specified view
            cas = cas.getView(this.defaultCasViewName);

            CAS defaultView = cas.getView(CAS.NAME_DEFAULT_SOFA);
            if (defaultView.getDocumentText() == null) {
                System.err
                        .println("The HTML and XML Viewers can only view the default text document, which was not found in this CAS.");
                return;
            }

            // generate inline XML
            File inlineXmlFile = new File(outputDirectory, "inline.xml");
            String xmlAnnotations = new CasToInlineXml()
                    .generateXML(defaultView);
            FileOutputStream outStream = new FileOutputStream(inlineXmlFile);
            outStream.write(xmlAnnotations.getBytes("UTF-8"));
            outStream.close();

            // generate HTML view
            // if (!styleMapFile.exists()) {
            // AnalysisEngineDescription aed = null;
            //
            // annotationViewGenerator.autoGenerateStyleMapFile(
            // aed.getAnalysisEngineMetaData(), styleMapFile);
            // }
            generator.processStyleMap(styleMapFile);
            generator.processDocument(inlineXmlFile);
            // File genFile = new File(viewerDirectory, "index.html");

        } catch (Exception ex) {
            throw new IOException("cannot create html annotationviewer", ex);
        }
    }
}
