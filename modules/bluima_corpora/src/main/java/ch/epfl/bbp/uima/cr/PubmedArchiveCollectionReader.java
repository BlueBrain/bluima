package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.xml.PmcNxmlHelper;
import ch.epfl.bbp.uima.xml.PmcNxmlParser;
import ch.epfl.bbp.uima.xml.archivearticle3.Article;
import de.julielab.jules.types.Header;

/**
 * Collection Reader for Pubmed Gzipped XML dumps. These can be leased and
 * downloaded from Pubmed (http://www.nlm.nih.gov/databases/journal.html). There
 * are currently ~ 700 dump files. New dumps are added weekly.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = HEADER)
public class PubmedArchiveCollectionReader extends
		JCasCollectionReader_ImplBase {
	Logger LOG = LoggerFactory.getLogger(PubmedArchiveCollectionReader.class);

	public static final String EXTENSION = ".zip";

	@ConfigurationParameter(name = BlueUima.PARAM_INPUT_DIRECTORY)
	private String inputDirectory;

	private Iterator<File> directoryIterator;
	private PmcNxmlParser archiveArticleParser;
	private FileSystemManager fsManager;
	private NextArticle nextArticle = null;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {

		try {

			File dir = ResourceHelper.getFile(inputDirectory);
			directoryIterator = FileUtils.iterateFiles(dir,
					new String[] { "gz" }, true);
			archiveArticleParser = new PmcNxmlParser();

			fsManager = VFS.getManager();

			// pre-fetch first article (bcse some archives are corrupted/empty)
			nextArticle = getNextArticle();

		} catch (FileNotFoundException e) {
			throw new ResourceInitializationException(
					ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS,
					new Object[] { inputDirectory });
		} catch (IOException e) {
			throw new ResourceInitializationException(
					ResourceInitializationException.COULD_NOT_ACCESS_DATA,
					new Object[] { PmcNxmlParser.class });
		} catch (Exception e) {
			throw new ResourceInitializationException(
					ResourceInitializationException.COULD_NOT_INSTANTIATE,
					new Object[] { PmcNxmlParser.class });
		}
	}

	public void getNext(JCas jcas) throws IOException, CollectionException {

		// text
		String text = PmcNxmlHelper.extractText(nextArticle.article);
		if (text.length() == 0) {
			LOG.info("article has empty text " + nextArticle);
		}
		jcas.setDocumentText(text);

		// add metadata
		Integer pmid = PmcNxmlHelper.extractPmid(nextArticle.article);
		String doi = PmcNxmlHelper.extractDoi(nextArticle.article);
		String title = PmcNxmlHelper.extractTitle(nextArticle.article);
		Header header = new Header(jcas);
		header.setDocId(pmid + "");
		header.setSource(nextArticle.file);
		header.setComponentId(PubmedArchiveCollectionReader.class.getName());
		// TODO header.setDocType("PMC");
		// header.setTitle(title);
		// header.setDoi(doi);
		header.addToIndexes();

		nextArticle = getNextArticle();
	}

	private NextArticle getNextArticle() throws IOException {

		while (directoryIterator.hasNext()) {
			File f = directoryIterator.next();
			LOG.debug("extracting " + f.getAbsolutePath());

			try {
				FileObject archive = fsManager.resolveFile("tgz:file://"
						+ f.getAbsolutePath());

				// List the children of the archive file
				FileObject[] children = archive.getChildren()[0].getChildren();
				for (int i = 0; i < children.length; i++) {
					FileObject fo = children[i];
					if (fo.isReadable() && fo.getType() == FileType.FILE
							&& fo.getName().getExtension().equals("nxml")) {
						FileContent fc = fo.getContent();
						Article article = archiveArticleParser.parse(fc
								.getInputStream());

						NextArticle nextArticle = new NextArticle();
						nextArticle.article = article;
						nextArticle.file = f.getAbsolutePath();

						return nextArticle;
					}
				}
			} catch (Exception e) {
				LOG.error("Error extracting " + f.getAbsolutePath() + ", " + e);
			}
		}
		return null;
	}

	public boolean hasNext() throws IOException, CollectionException {
		return nextArticle != null;
	}

	public Progress[] getProgress() {// nope
		return null;
	}

	@Override
	public void close() throws IOException {// nope
	}

	private class NextArticle {
		Article article;
		String file;
	}

	public static CollectionReader getCR(String path)
			throws ResourceInitializationException, FileNotFoundException {
		File testcaseDir = ResourceHelper.getFile(path);

		return CollectionReaderFactory.createReader(
				PubmedArchiveCollectionReader.class,
				BlueUima.PARAM_INPUT_DIRECTORY, testcaseDir.getAbsolutePath());
	}
}
