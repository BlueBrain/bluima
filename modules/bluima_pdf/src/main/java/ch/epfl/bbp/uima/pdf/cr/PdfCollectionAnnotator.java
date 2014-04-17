package ch.epfl.bbp.uima.pdf.cr;

import static ch.epfl.bbp.io.IOUtils.unzipUniqueFileAsStream;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static org.apache.commons.io.FilenameUtils.removeExtension;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.uima.UIMAException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.cr.FileReader;
import ch.epfl.bbp.uima.pdf.BlockHandler;
import ch.epfl.bbp.uima.typesystem.TypeSystem;

import com.snowtide.pdf.PDFTextStream;

import de.julielab.jules.types.Header;
import edu.psu.seersuite.extractors.tableextractor.extraction.IPdfParser;
import edu.psu.seersuite.extractors.tableextractor.extraction.PdfBoxParser;
import edu.psu.seersuite.extractors.tableextractor.extraction.TableExtractor;

/**
 * Extracts the text of a PDF file using Snowtide's PDFTextSteam
 * (http://www.snowtide.com/) and optionally the tables found in the PDF using
 * TableSeer (http://sourceforge.net/projects/tableseer/). <br/>
 * This AE is used for local multithreaded processing, and relies on a
 * {@link FileReader} to set the file path in the {@link Header}
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = true)
@TypeCapability(outputs = { HEADER })
public class PdfCollectionAnnotator extends JCasAnnotator_ImplBase {
    Logger LOG = LoggerFactory.getLogger(PdfCollectionAnnotator.class);

    public static final String COMPONENT_ID = PdfCollectionAnnotator.class
            .getName();

    public static final String PARAM_EXTRACT_TABLES = "extractTables";
    @ConfigurationParameter(name = PARAM_EXTRACT_TABLES, defaultValue = "false", //
    description = "whether to extract tables")
    private boolean extractTables;

    public static final String PARAM_EXPAND_ABBREVIATIONS = "expandAbbrevs";
    @ConfigurationParameter(name = PARAM_EXPAND_ABBREVIATIONS, defaultValue = "false", //
    description = "whether to expand Abbreviations")
    private boolean expandAbbrevs;

    private TableExtractor tableExtractor;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            if (extractTables) {
                tableExtractor = new TableExtractor();
                IPdfParser parser = new PdfBoxParser();
                tableExtractor.setParser(parser);
            }
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Header header = selectSingle(jCas, Header.class);
        File pdfFile = new File(header.getSource());
        checkFileExists(pdfFile);
        LOG.debug("extracting {}", pdfFile.getName());

        try {
            PDFTextStream pdf;
            if (pdfFile.getName().endsWith("zip")) {
                InputStream is = unzipUniqueFileAsStream(pdfFile);
                pdf = new PDFTextStream(is, removeExtension(pdfFile.getName()));
            } else {
                pdf = new PDFTextStream(pdfFile);
            }
            BlockHandler blueHandler = new BlockHandler();
            pdf.pipe(blueHandler);
            pdf.close();

            PdfCollectionReader.extractText(jCas, blueHandler.getDoc(),
                    header.getDocId(), expandAbbrevs);

            if (extractTables)
                PdfCollectionReader
                        .extractTables(tableExtractor, pdfFile, jCas);

            // if (extractReferences)
            // extractReferences(f, jcas);

        } catch (Throwable t) {
            LOG.error("error extracting " + header.getSource(), t);
            // throw new AnalysisEngineProcessException(e);
        }
    }

    public static JCas newCasFromFile(String filePath, String docId)
            throws UIMAException {
        JCas jCas = JCasFactory.createJCas(TypeSystem.JULIE_TSD);
        Header header = new Header(jCas);
        header.setDocId(docId);
        header.setSource(filePath);
        header.addToIndexes();
        return jCas;
    }

    public static AnalysisEngineDescription getAED(boolean extractTables)
            throws ResourceInitializationException {
        return AnalysisEngineFactory.createEngineDescription(
                PdfCollectionAnnotator.class, PARAM_EXTRACT_TABLES,
                extractTables);
    }
}
