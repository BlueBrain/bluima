package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static org.apache.uima.fit.util.JCasUtil.*;

import java.util.Collection;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.Keep;
import de.julielab.jules.types.Sentence;

/**
 * Dumps all {@link Keep}s to sysout, useful for debugging
 * 
 * @author renaud.richardet@epfl.ch
 */
public class KeepsDumper extends JCasAnnotator_ImplBase {

	 @ConfigurationParameter(name = "printCoveredText", defaultValue = "false",//
		        description = "Prints the covered text of every Keep")
	 private boolean printCoveredText;
		        
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        String pmID = null; 
        String keepText = null;
        
        // Print docID
        pmID = getHeaderDocId(jCas);
        System.out.println("Doc " + pmID + " --------------------------------------------");

        Map<Sentence, Collection<Keep>> sentences = indexCovered(jCas, //
                Sentence.class, Keep.class);

        for (Sentence s : JCasUtil.select(jCas, Sentence.class)) {

            for (Keep k : sentences.get(s)) { // Each Keep in a Sentence.
            	keepText = k.getNormalizedText().replace(' ', '_');
            	if(printCoveredText){
            		keepText += "["+k.getCoveredText()+"]";
            	}
            	
                System.out.print(keepText + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}