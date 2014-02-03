package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static org.apache.uima.resource.ResourceInitializationException.COULD_NOT_INSTANTIATE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.xml.PmcNxmlHelper;
import ch.epfl.bbp.uima.xml.PmcNxmlParser;
import ch.epfl.bbp.uima.xml.archivearticle3.Article;
import de.julielab.jules.types.Header;

/**
 * Collection Reader for nxml files (http://dtd.nlm.nih.gov/).<br/>
 * . Handles most xml tags, but not all yet. Some documents come out empty, as
 * they are of type letter, or book review.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = HEADER)
public class NxmlCollectionReader extends AbstractFileReader {

	private PmcNxmlParser archiveArticleParser;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		fileExtensionFilter = "nxml"; // overwrite

		super.initialize(context);
		try {
			archiveArticleParser = new PmcNxmlParser();
		} catch (Exception e) {
			throw new ResourceInitializationException(COULD_NOT_INSTANTIATE,
					new Object[] { PmcNxmlParser.class });
		}
	}

	public void getNext(JCas jcas) throws IOException, CollectionException {

		File file = fileIterator.next();
		Article article;
		try {
			article = archiveArticleParser.parse(new FileInputStream(file));
		} catch (JAXBException e) {
			throw new IOException(e);
		}

		// text
		String text = PmcNxmlHelper.extractText(article);
		if (text.length() == 0) {
			LOG.info("article has empty text " + file);
		}
		jcas.setDocumentText(text);

		// add metadata
		Integer pmid = PmcNxmlHelper.extractPmid(article);
		// String doi = PmcNxmlHelper.extractDoi(nextArticle.article);
		// String title = PmcNxmlHelper.extractTitle(nextArticle.article);
		Header header = new Header(jcas);
		header.setDocId(pmid + "");
		header.setSource(file.getAbsolutePath());
		header.setComponentId(NxmlCollectionReader.class.getName());
		// TODO header.setDocType("PMC");
		// header.setTitle(title);
		// header.setDoi(doi);
		header.addToIndexes();

		System.out.println(pmid + "\t" + file.getPath());
	}
}
