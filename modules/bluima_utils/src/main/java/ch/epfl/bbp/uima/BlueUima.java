package ch.epfl.bbp.uima;

import java.io.File;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A place for constants.<strong> DO NOT CHANGE THEM BLINDLY, as they are
 * hardcoded (text) in pipeline scripts</strong>
 * 
 * @author renaud.richardet@epfl.ch
 */
public final class BlueUima {
	private static final Logger LOG = LoggerFactory.getLogger(BlueUima.class);

	public static final String PARAM_QUERY = "query";
	public static final String PARAM_AND_QUERY = "and_query";
	public final static String PARAM_MAX_NR_RESULTS = "maxNrResults";
	public static final String PARAM_BETWEEN = "between";
	public static final String PARAM_FEATURE_NAME = "featureName";

	public static final String PARAM_ANNOTATION_CLASS = "annotationClass";
	public static final String PARAM_ANNOTATION_CLASSES = "annotationClasses";
	public static final String PARAM_ANNOTATION_FIELD = "annotationField";
	public static final String PARAM_ANNOTATION_FIELDS = "annotationFields";
	public static final String PARAM_FIRST_ANNOT = "annot1";
	public static final String PARAM_SECOND_ANNOT = "annot2";
	public static final String PARAM_FIRST_ANNOT_ID_FIELD = "annot1IdField";
	public static final String PARAM_SECOND_ANNOT_ID_FIELD = "annot2IdField";
	public static final String PARAM_FIRST_ANNOT_TYPE = "annot1Type";
	public static final String PARAM_SECOND_ANNOT_TYPE = "annot2Type";
	public static final String PARAM_ENCLOSING_SCOPE = "enclosingScope";
	public static final String PARAM_COOCCURRENCE_TYPE = "cooccurrenceType";

	public static final String PARAM_MODEL_FILE = "modelFile";
	public static final String PARAM_MODEL = "model";

	public static final String PARAM_INPUT = "input";
	public static final String PARAM_INPUT_DIRECTORY = "inputDirectory";
	public static final String PARAM_INPUT_FILE = "inputFile";
	public static final String PARAM_OUTPUT_FILE = "outputFile";
	public static final String PARAM_OUTPUT_DIR = "outputDir";
	public static final String PARAM_PATH_PREFIX = "pathPrefix";
	public static final String PARAM_VERBOSE = "verbose";

	public static final String PARAM_CORPUS_PATH = "corpusFile";
	public static final String PARAM_CORPUS_ANNOTATIONS_PATH = "corpusAnnotationsFile";
	/** Corpus mode, either test or train or eval */
	public static final String PARAM_MODE = "corpus_mode";
	/** Root path of the corpus */
	public static final String PARAM_CORPUS_ROOT = "corpusRoot";

	public static final String PARAM_DIRECTORY_ITERATOR = "directoryIterator";
	public static final String PARAM_IS_RECURSIVE = "isRecursive";
	public static final String PARAM_FILE_EXTENSION_FILTER = "fileExtensionFilter";
	public static final String PARAM_FORMAT = "format";

	public static final String PARAM_DB_CONNECTION = "db_connection";
	public static final String PARAM_INDEX_NAME = "index_name";
	public static final String PARAM_FIELD_NAME = "field_name";
	/** Skip (PubMed) docs that have no (abstract)/text */
	public static final String PARAM_SKIP_EMPTY_DOCS = "skipEmptyDocs";
	public static final String PARAM_CASE_SENSITIVE = "caseSensitive";

	public static final String PARAM_VIEW = "view";
	public static final String VIEW_SYSTEM = "_InitialView";
	public static final String VIEW_GOLD = "view_gold";

	/** Path to NLP root folder on storage array */
	public static final String PATH_NFS4_NLP = "/nfs4/bbp.epfl.ch/simulation/nlp/";
	public static final String PATH_NFS4_EXTRACTED_PDFS = PATH_NFS4_NLP
			+ "/data/extracted_pdfs/";

	// FIXME remove
	public static final String SECTION_TYPE_REFERENCES = "references";
	public static final String SECTION_TYPE_ABSTRACT = "abstract";
	public static final String SECTION_TYPE_TITLE = "title";
	public static final String SECTION_TYPE_COPYRIGHT = "copyright";

	/**
	 * This is a central property, as many modules rely on it to find their
	 * resources (config files, models, etc). It can be set on the command line,
	 * e.g.:<br>
	 * <code>mvn exec:java \<br>
-Dexec.mainClass="ch.epfl.bbp.uima.MyClass" \<br>
-Dexec.classpathScope=runtime \<br>
<strong>-Dbluima_home=/path/to/bluima</strong></code><br>
	 * or as a system variable, e.g.:<br>
	 * <br>
	 * <ul>
	 * <li>OS X / Linux: add <code>export BLUIMA_HOME=/path/to/bluima</code> to
	 * your .bashrc</li>
	 * <li>Win7: Control Panel > System & Security > System > Environment
	 * Variables</li>
	 * </ul>
	 */
	// FIXME change to BLUIMA_HOME
	public static final String BLUE_UIMA_ROOT;
	static {
		String home = System.getProperty("bluima_home");
		if (home == null)
			home = System.getenv("BLUIMA_HOME");

		if (home != null && new File(home).exists()) {
			if (!home.endsWith("/"))
				home += "/";
			BLUE_UIMA_ROOT = home;
			LOG.info("Using resource path BLUE_UIMA_ROOT={} (resolves to {})",
					BLUE_UIMA_ROOT, new File(BLUE_UIMA_ROOT).getAbsolutePath());
		} else {
			BLUE_UIMA_ROOT = new File(".").getAbsolutePath();
			LOG.warn("Using default resource path BLUE_UIMA_ROOT={}",
					BLUE_UIMA_ROOT);
		}
	}

	// FIXME use everywhere
	/** Path to main resources */
	public static final String RESOURCES_PATH = "src/main/resources/";
	/** Path to test resources */
	public static final String TEST_RESOURCES_PATH = "src/test/resources/";

	public static final String BLUE_UTILS_ROOT = BLUE_UIMA_ROOT
			+ "modules/bluima_utils/";
	public static final String BLUE_UTILS_TEST_BASE = BLUE_UTILS_ROOT
			+ TEST_RESOURCES_PATH;

}
