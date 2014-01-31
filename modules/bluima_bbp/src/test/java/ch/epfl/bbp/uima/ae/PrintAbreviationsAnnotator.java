package ch.epfl.bbp.uima.ae;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.utils.LoadDataFileWriter;
import de.julielab.jules.types.Abbreviation;

/**
 * @author renaud.richardet@epfl.ch
 */
public class PrintAbreviationsAnnotator extends JCasAnnotator_ImplBase {

    private LoadDataFileWriter writer;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            writer = new LoadDataFileWriter(new File("abrevs.loaddata"));
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String docId = BlueCasUtil.getHeaderDocId(jCas);
        for (Abbreviation a : select(jCas, Abbreviation.class)) {
            try {
                String abrevTxt = a.getCoveredText();
                String longTxt = a.getExpan();
                writer.addLoadLine(docId, abrevTxt, longTxt);
            } catch (Exception e) {
                System.err.println("could not extract abrev from "+docId);
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        try {
            writer.close();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}