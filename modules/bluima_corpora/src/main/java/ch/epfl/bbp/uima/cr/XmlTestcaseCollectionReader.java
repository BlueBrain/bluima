package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.types.CellType;
import ch.epfl.bbp.uima.types.CellTypeProteinConcentration;
import ch.epfl.bbp.uima.types.Concentration;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.xml.TestSuiteCorpusParser;
import ch.epfl.bbp.uima.xml.testcase.Input;
import ch.epfl.bbp.uima.xml.testcase.Output;
import ch.epfl.bbp.uima.xml.testcase.Result;
import ch.epfl.bbp.uima.xml.testcase.TestSuiteCorpus;

import com.google.common.base.Preconditions;

/**
 * Collection Reader for BBP's TestCase XML format. Creates {@link CAS} from the
 * XMLs and add the relevant annotations (protein, concentration, celltype).
 * This format is especially relevant for the validation of our first Research
 * Task (see confluence).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class XmlTestcaseCollectionReader extends JCasCollectionReader_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(XmlTestcaseCollectionReader.class);

    public static final String EXTENSION = "xml";

    @ConfigurationParameter(name = PARAM_INPUT_FILE)
    private String inputFile;

    private Iterator<Result> resultIt;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        // parse testcase xml
        try {
            LOG.debug("Using inputFile: " + inputFile);
            InputStream is = ResourceHelper.getInputStream(inputFile);
            Preconditions.checkArgument(is != null, "no inputFile at "
                    + inputFile);
            TestSuiteCorpus parse = new TestSuiteCorpusParser().parse(is);
            resultIt = parse.getResult().iterator();
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return resultIt.hasNext();
    }

    @Override
    public Progress[] getProgress() {
        return null;
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {

        Result result = resultIt.next();
        Input input = result.getInput();
        String rawContent = input.getRawContent();
        jCas.setDocumentText(rawContent);

        List<Output> outputs = result.getOutput();
        for (Output output : outputs) {

            Protein prot = new Protein(jCas);
            prot.setName(output.getProtein().getProteinName());
            prot.setId(output.getProtein().getId());
            // TODO Integer from =
            // output.getProtein().getTokens().getFrom()
            // .intValue();
            // BigInteger to = output.getProtein().getTokens().getTo();
            // if (to != null) {
            // prot.setTokenTo(to.intValue());// }
            prot.addToIndexes();

            Concentration conc = new Concentration(jCas);
            conc.setTextValue(output.getConcentration().getValue());
            // TODO conc.setUnit(output.getConcentration().getUnit());
            // conc.setValue(Float.parseFloat(output.getConcentration().getValue()));
            conc.addToIndexes();

            ch.epfl.bbp.uima.xml.testcase.CellType ct = output.getCellType();
            // TODO ct.getTokens().getFrom(),ct.getTokens().getTo()
            CellType cta = new CellType(jCas);
            cta.setName(ct.getCellTypeName());
            cta.setId(ct.getId());
            cta.addToIndexes();

            CellTypeProteinConcentration ctpct = new CellTypeProteinConcentration(
                    jCas);
            ctpct.setCelltype(cta);
            ctpct.setConcentration(conc);
            ctpct.setProtein(prot);
            ctpct.addToIndexes();
        }
    }
}