package ch.epfl.bbp.uima.ae;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

/**
 * Lets you print a message to sysout
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PrintCommentAnnotator extends JCasAnnotator_ImplBase {

    public static final String MSG_STRING = "message";
    @ConfigurationParameter(name = MSG_STRING, mandatory = true, description = "a message to print out")
    private String msg;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        System.out.println(">> " + msg);
    }
}