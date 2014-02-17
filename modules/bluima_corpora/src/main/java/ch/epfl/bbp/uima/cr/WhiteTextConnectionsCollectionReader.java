package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.maxEnd;
import static ch.epfl.bbp.uima.BlueCasUtil.minBegin;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MAX_NR_RESULTS;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.BRAIN_REGION;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.CorporaHelper;
import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Cooccurrence;
import de.julielab.jules.types.Header;
import de.julielab.jules.types.Sentence;

/**
 * Collection Reader for the WhiteText CONNECTION brain regions corpus, based on
 * the Airola XML for annotated set. The Airoal XML versions do not contain all
 * of the abstracts, sentences and entities, they only contain sentences with
 * two or more brain region mentions (http://www.chibi.ubc.ca/WhiteText/). <br>
 * 989 docs, 4338 sentences, 13429 brain regions, 3097 co-occurrences <br>
 * Paper: French L et al. (2012) "Application and evaluation of automated
 * methods to extract neuroanatomical connectivity statements from free text"<br>
 * 
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { SENTENCE, BRAIN_REGION, COOCCURRENCE })
public class WhiteTextConnectionsCollectionReader extends
		JCasCollectionReader_ImplBase {
	Logger LOG = LoggerFactory
			.getLogger(WhiteTextConnectionsCollectionReader.class);

	@ConfigurationParameter(name = PARAM_MAX_NR_RESULTS, //
	defaultValue = MAX_VALUE + "", mandatory = false)
	private int maxNrDocs;

	public final static String PARAM_ADD_NEG_PAIRS = "addNegativePairs";
	@ConfigurationParameter(name = PARAM_ADD_NEG_PAIRS, defaultValue = "false", //
	mandatory = false, description = "add all possible " + //
			"cooccurrences between the brain regions, or just the one that have an interaction")
	private boolean addNegativePairs;

	private int currentNrDocs = 0;
	private Iterator<Element> articleIt;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {

		try {
			String corpus = CorporaHelper.CORPORA_HOME
					+ "/src/main/resources/pear_resources/whitetext/WhiteTextNegFixFull.xml";
			checkFileExists(corpus);
			InputStream corpusIs = new FileInputStream(corpus);

			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(corpusIs);
			Element rootNode = doc.getRootElement();

			articleIt = rootNode.getChildren("document").iterator();

		} catch (Exception e) {
			throw new ResourceInitializationException(e);
		}
	}

	public void getNext(JCas jcas) throws IOException, CollectionException {

		Element articleE = articleIt.next();
		currentNrDocs++;

		String pmid = articleE.getAttributeValue("origID");
		LOG.trace("processing pmId {}", pmid);
		Header h = new Header(jcas);
		h.setDocId(pmid);
		h.addToIndexes();

		String text = addAnnotations(jcas, articleE.getChildren("sentence"));
		jcas.setDocumentText(text);
	}

	private String addAnnotations(JCas jcas, List<Element> sentences) {
		int i = 0;
		String text = "";
		for (Element sentence : sentences) {

			String sentenceText = sentence.getAttributeValue("text");
			new Sentence(jcas, i, i + sentenceText.length()).addToIndexes();
			text += sentenceText + " ";// add space at end

			/** to map ids in pairs */
			Map<String, BrainRegion> brs = newHashMap();
			// add brain regions
			for (Element entity : sentence.getChildren("entity")) {
				String[] fromTo = entity.getAttributeValue("charOffset").split(
						"-");
				checkEquals(2, fromTo.length);
				int begin = i + parseInt(fromTo[0]), end = i
						+ parseInt(fromTo[1]) + 1;
				BrainRegion br = new BrainRegion(jcas, begin, end);
				br.addToIndexes();
				checkEquals("annotation text do not match",
						entity.getAttributeValue("text"),
						text.substring(begin, end));
				String id = entity.getAttributeValue("id");
				brs.put(id, br);
			}
			// add co-occurrences
			for (Element pair : sentence.getChildren("pair")) {
				boolean hasInteraction = pair.getAttributeValue("interaction")
						.equals("True");
				BrainRegion br1 = brs.get(pair.getAttributeValue("e1"));
				BrainRegion br2 = brs.get(pair.getAttributeValue("e2"));
				Cooccurrence cooc = new Cooccurrence(jcas, minBegin(br1, br2),
						maxEnd(br1, br2));
				cooc.setFirstEntity(br1);
				cooc.setSecondEntity(br2);

				cooc.setHasInteraction(hasInteraction);
				if (addNegativePairs) {
					cooc.addToIndexes();
				} else if (hasInteraction) {
					cooc.addToIndexes();
				} // otherwise, does not get added
			}
			i += sentenceText.length() + 1;// added space
		}
		return text;
	}

	public boolean hasNext() throws IOException, CollectionException {
		return articleIt.hasNext() && (currentNrDocs < maxNrDocs);
	}

	public Progress[] getProgress() {// nope
		return null;
	}
}
