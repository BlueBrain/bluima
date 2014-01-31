package ch.epfl.bbp.uima.ae.output;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.impl.XCASSerializer;
import org.apache.uima.cas.impl.XmiCasSerializer;
import org.apache.uima.fit.component.JCasConsumer_ImplBase;
import org.apache.uima.fit.component.xwriter.HeaderDocIdFileNamer;
import org.apache.uima.fit.component.xwriter.XWriterFileNamer;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.factory.ConfigurationParameterFactory;
import org.apache.uima.fit.factory.initializable.InitializableFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.utils.StructuredDirectory;

/**
 * 
 * A simple CAS consumer that generates zipped XCAS (XML representation of the
 * CAS) files in the filesystem.
 * 
 * @author Philip Ogren
 * @author renaud.richardet@epfl.ch
 */

@OperationalProperties(multipleDeploymentAllowed = true)
public class ZipXWriter extends JCasConsumer_ImplBase {

    /**
     * The parameter name for the configuration parameter that specifies the
     * output directory
     */
    @ConfigurationParameter(name = BlueUima.PARAM_OUTPUT_DIR, mandatory = true, description = "takes a path to directory into which output files will be written.")
    private String outputDirectoryName;

    /**
     * The parameter name for the configuration parameter that provides the name
     * of the XML scheme to use.
     */
    public static final String PARAM_XML_SCHEME_NAME = "xmlSchemeName";
    @ConfigurationParameter(mandatory = true, defaultValue = "XMI", description = "specifies the UIMA XML serialization scheme that should be used. "
            + "Valid values for this parameter are 'XMI' (default) and 'XCAS'.")
    private String xmlSchemeName;

    /**
     * The parameter name for the configuration parameter that specifies the
     * name of the class that implements the file namer
     */
    public static final String PARAM_FILE_NAMER_CLASS_NAME = "fileNamerClassName";
    @ConfigurationParameter(mandatory = true, description = "the class name of the XWriterFileNamer implementation to use", defaultValue = "org.apache.uima.fit.component.xwriter.IntegerFileNamer")
    protected String fileNamerClassName;

    /**
     * The parameter name for the configuration parameter that specifies the
     * name of the class that implements the file namer
     */
    public static final String PARAM_STRUCTURE_DIR = "dirStructure";
    @ConfigurationParameter(mandatory = false, description = "whether to output in a structure directory", defaultValue = "false")
    protected boolean dirStructure;

    /**
     * The name of the XMI XML scheme. This is a valid value for the parameter
     * {@value #PARAM_XML_SCHEME_NAME}
     */
    public static final String XMI = "XMI";

    /**
     * The name of the XCAS XML scheme. This is a valid value for the parameter
     * {@value #PARAM_XML_SCHEME_NAME}
     */
    public static final String XCAS = "XCAS";

    private File outputDirectory;

    private boolean useXMI = true;

    private XWriterFileNamer fileNamer;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        outputDirectory = new File(outputDirectoryName);
        if (!outputDirectory.exists()) {
            outputDirectory.mkdirs();
        }

        if (xmlSchemeName.equals(XMI)) {
            useXMI = true;
        } else if (xmlSchemeName.equals(XCAS)) {
            useXMI = false;
        } else {
            throw new ResourceInitializationException(String.format(
                    "parameter '%1$s' must be either '%2$s' or '%3$s'.",
                    PARAM_XML_SCHEME_NAME, XMI, XCAS), null);
        }

        fileNamer = InitializableFactory.create(context, fileNamerClassName,
                XWriterFileNamer.class);

    }

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
        String fileName = fileNamer.nameFile(jcas);
        try {
            if (useXMI) {
                zipXmi(jcas.getCas(), fileName);
            } else {
                zipXCas(jcas.getCas(), fileName);
            }
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        } catch (SAXException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    private void zipXCas(CAS aCas, String fileName) throws IOException,
            SAXException {

        // create file structure
        File outFile = null;
        if (dirStructure) {
            outFile = new File(outputDirectory,
                    StructuredDirectory.getFilePath(new Integer(fileName),
                            "xcas.zip"));
            outFile.getParentFile().mkdirs();
        } else {
            outFile = new File(outputDirectory, fileName + ".xcas.zip");
        }

        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        try {
            fos = new FileOutputStream(outFile);
            zos = new ZipOutputStream(fos);
            zos.setLevel(9);
            ZipEntry ze = new ZipEntry(fileName + ".xmi");
            zos.putNextEntry(ze);

            XCASSerializer ser = new XCASSerializer(aCas.getTypeSystem());
            XMLSerializer xmlSer = new XMLSerializer(zos, false);
            ser.serialize(aCas, xmlSer.getContentHandler());

        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    private void zipXmi(CAS aCas, String id) throws IOException, SAXException {

        // create file structure
        File outFile = null;
        if (dirStructure) {
            outFile = new File(outputDirectory,
                    StructuredDirectory.getFilePath(new Integer(id), "xmi.zip"));
            outFile.getParentFile().mkdirs();
        } else {
            outFile = new File(outputDirectory, id + ".xmi.zip");
        }

        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        try {
            fos = new FileOutputStream(outFile);
            zos = new ZipOutputStream(fos);
            zos.setLevel(9);
            ZipEntry ze = new ZipEntry(id + ".xmi");
            zos.putNextEntry(ze);

            XmiCasSerializer ser = new XmiCasSerializer(aCas.getTypeSystem());
            XMLSerializer xmlSer = new XMLSerializer(zos, false);
            ser.serialize(aCas, xmlSer.getContentHandler());

        } finally {
            if (zos != null) {
                zos.close();
            }
        }
    }

    public static AnalysisEngineDescription getAED(String outDir)
            throws ResourceInitializationException {
        return createEngineDescription(ZipXWriter.class,
                BlueUima.PARAM_OUTPUT_DIR, outDir, PARAM_FILE_NAMER_CLASS_NAME,
                HeaderDocIdFileNamer.class.getName());
    }
}
