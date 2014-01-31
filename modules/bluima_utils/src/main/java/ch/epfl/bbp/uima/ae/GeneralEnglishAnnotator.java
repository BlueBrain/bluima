package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.GENERAL_ENGLISH;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.types.GeneralEnglish;
import de.julielab.jules.types.Token;

/**
 * Annotates general English words. These are loaded from a list file, see
 * http://www.cs.cmu.edu/~chogan/BasicEnglish.html.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = TOKEN, outputs = GENERAL_ENGLISH)
public class GeneralEnglishAnnotator extends JCasAnnotator_ImplBase {

    private List<String> generalEnglish;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            String dict = BLUE_UTILS_ROOT + RESOURCES_PATH
                    + "generalEnglish/basic_english.txt";
            checkFileExists(dict);
            generalEnglish = linesFrom(dict);
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Token t : select(jCas, Token.class)) {
            String text = t.getCoveredText().trim();

            if (text.length() > 0 && generalEnglish.contains(text)) {

                new GeneralEnglish(jCas, t.getBegin(), t.getEnd())
                        .addToIndexes();
            }
        }
    }
}