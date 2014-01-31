package ch.epfl.bbp.uima.examples;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.pear.ComponentManager;
import de.julielab.jules.types.Gene;

public class B_UsingPears_Example {
    private static Logger LOG = LoggerFactory
	    .getLogger(B_UsingPears_Example.class);

//FIXME    @Test
    public void test() throws Exception {
	

	CollectionReader cr = ComponentManager.getCR("Biocreative2GeneCR",
		"corpus_mode", "train");

	AnalysisEngine ss = AnalysisEngineFactory
		.createEngine(NaiveSentenceSplitterAnnotator.class);

	XMLInputSource in = new XMLInputSource(
		"/Users/richarde/dev/bluebrain/2_process/banner_trunk/desc/BANNERAE.xml");
	ResourceSpecifier specifier = UIMAFramework.getXMLParser()
		.parseResourceSpecifier(in);
	Map<String, Object> params = new HashMap<String, Object>();
	params.put(
		"configFile",
		"/Users/richarde/dev/bluebrain/2_process/banner_trunk/desc/banner_UIMA_TEST.xml");
	AnalysisEngine banner = UIMAFramework.produceAnalysisEngine(specifier,
		params);

	JCasIterable jcasIt = new JCasIterable(cr, ss, banner);
	while (jcasIt.hasNext()) {
	    JCas jcas = jcasIt.next();
	    LOG.debug(jcas.getDocumentText());

	    Collection<Gene> cts = JCasUtil.select(jcas, Gene.class);
	    for (Gene ct : cts) {

		LOG.debug("sf: " + ct.getCoveredText() + "[" + ct.getId() + "]");
	    }
	}
    }
}
