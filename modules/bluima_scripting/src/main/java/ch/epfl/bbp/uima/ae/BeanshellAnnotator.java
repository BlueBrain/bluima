package ch.epfl.bbp.uima.ae;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Allows BeanShell Java scripts to be used in UIMA. Sets jCas as available
 * variable.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BeanshellAnnotator extends JCasAnnotator_ImplBase {
	private static Logger LOG = LoggerFactory
			.getLogger(BeanshellAnnotator.class);

	// some common imports, to simplify scripts...
	static final String HEADER = "import ch.epfl.bbp.uima.*;\n"//
			//+ "import de.julielab.jules.types.*;\n"//
			+ "import ch.epfl.bbp.uima.types.*;\n"//
			+ "import ch.epfl.bbp.uima.ae.*;\n"//
			+ "import ch.epfl.bbp.uima.BlueUima.*;\n"//
			+ "import ch.epfl.bbp.uima.typesystem.TypeSystem.*;\n"//
			+ "import org.apache.uima.fit.util.JCasUtil.*;\n"//
			+ "import java.io.*;\n"//
			+ "import java.util.*;\n";

	/** UIMA initialization parameter for the Java script (as a string) */
	public static final String SCRIPT_STRING = "script_string";
	@ConfigurationParameter(name = SCRIPT_STRING, mandatory = true, description = "a String that contains a Beanshell Java script")
	private String scriptString;

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		Interpreter i = new Interpreter(); // Construct an interpreter

		// Evaluate the script
		try {
			i.set("jCas", jCas);
			i.eval(HEADER + scriptString);

		} catch (EvalError e) {
			LOG.warn("cannot compile and run Beanshell script {}", scriptString);
			throw new AnalysisEngineProcessException(e);
		}
	}
}