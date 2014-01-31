package ch.epfl.bbp.uima.jython;

import java.io.InputStream;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.ResourceHelper;

/**
 * Allows Python scripts to be used in UIMA through Jython
 * (http://www.jython.org/)
 * 
 * @author renaud.richardet@epfl.ch
 */
// @TypeCapability
public class JythonAnnotator extends JCasAnnotator_ImplBase {

	/** UIMA initialization parameter for the Path to Python script */
	public static final String SCRIPT_PATH = "script_path";
	@ConfigurationParameter(name = SCRIPT_PATH)
	private String scriptPath;

	private PythonInterpreter interp;
	private InputStream scriptFileIs;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		interp = new PythonInterpreter(null, new PySystemState());
		// LATER allow for nonstandard Python modules
		// PySystemState sys = Py.getSystemState();
		// sys.path.append(new PyString(rootPath));
		// sys.path.append(new PyString(modulesDir));

		try {
			scriptFileIs = ResourceHelper.getInputStream(scriptPath);
		} catch (Exception e) {
			throw new ResourceInitializationException(
					ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
					new Object[] { scriptPath });
		}
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		try {
			interp.set("jcas", jcas); // make jcas available in script
			interp.execfile(scriptFileIs);
		} catch (Exception e) {
			throw new AnalysisEngineProcessException(
					AnalysisEngineProcessException.ANNOTATOR_EXCEPTION,
					new Object[] {}, e);
		}
	}
}
