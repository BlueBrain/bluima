package ch.epfl.bbp.uima.jython;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

/**
 * Allows Python scripts to be used in UIMA through Jython
 * (http://www.jython.org/)
 * 
 * @author renaud.richardet@epfl.ch
 */
// @TypeCapability
public class JythonAnnotator2 extends JCasAnnotator_ImplBase {

	/** UIMA initialization parameter for the Python script (as a string) */
	public static final String SCRIPT_STRING = "script_string";
	@ConfigurationParameter(name = SCRIPT_STRING, mandatory = true, description = "a String that contains a Python script")
	private String scriptString;

	private PythonInterpreter interp = new PythonInterpreter(null,
			new PySystemState());

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		try {
			interp.set("jcas", jcas); // make jcas available in script
			interp.exec(scriptString);
		} catch (Exception e) {
			throw new AnalysisEngineProcessException(
					AnalysisEngineProcessException.ANNOTATOR_EXCEPTION,
					new Object[] {}, e);
		}
	}
}