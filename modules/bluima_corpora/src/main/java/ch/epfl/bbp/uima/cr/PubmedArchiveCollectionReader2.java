package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.VFS;
import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.xml.PubmedXmlParser;
import ch.epfl.bbp.uima.xml.pubmed.Abstract;
import ch.epfl.bbp.uima.xml.pubmed.DateCreated;
import ch.epfl.bbp.uima.xml.pubmed.MedlineCitation;
import de.julielab.jules.types.Date;
import de.julielab.jules.types.Header;

/**
 * Collection Reader for Pubmed Gzipped XML dumps. These can be leased and
 * downloaded from Pubmed (http://www.nlm.nih.gov/databases/journal.html). There
 * are currently ~ 700 dump files. New dumps are added weekly.
 * 
 * New file format, I believe
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = HEADER)
public class PubmedArchiveCollectionReader2 extends AbstractFileReader {

    private PubmedXmlParser xmlArticleParser;
    private FileSystemManager fsManager;
    private Iterator<MedlineCitation> articlesIt = null;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        fileExtensionFilter = "xml.gz";
        isRecursive = false;
        super.initialize(context);
        try {
            fsManager = VFS.getManager();
            xmlArticleParser = new PubmedXmlParser();
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void getNext(JCas jcas) throws IOException, CollectionException {

        MedlineCitation article = articlesIt.next();

        // text
        Abstract abstrct = article.getArticle().getAbstract();
        if (abstrct != null)
            jcas.setDocumentText(abstrct.getAbstractText());

        // add metadata
        String title = article.getArticle().getArticleTitle().getvalue();
        Header header = new Header(jcas);
        header.setDocId(article.getPMID().getvalue().toString());
        header.setTitle(title);
        // header.setSource(nextArticle.file);
        header.setComponentId(PubmedArchiveCollectionReader2.class.getName());
        header.addToIndexes();

        DateCreated dateCreated = article.getDateCreated(); //FIXME use dateCompleted
        Date pubDateA = new Date(jcas);
        pubDateA.setDay(parseInt(dateCreated.getDay().getvalue()));
        pubDateA.setMonth(parseInt(dateCreated.getMonth().getvalue()));
        pubDateA.setYear(parseInt(dateCreated.getYear().getvalue()));
        pubDateA.addToIndexes();
    }

    public boolean hasNext() throws IOException, CollectionException {
        if (articlesIt != null && articlesIt.hasNext()) {
            return true;
        } else {
            if (super.hasNext()) {
                // try to fetch
                File f = fileIterator.next();
                FileObject archive = fsManager.resolveFile("gz:file://"
                        + f.getAbsolutePath());
                FileObject fo = archive.getChildren()[0];
                LOG.debug("extracted file {} from archive {}", fo.getName(),
                        f.getName());
                if (fo.isReadable() && fo.getType() == FileType.FILE) {
                    FileContent fc = fo.getContent();
                    List<MedlineCitation> articles = xmlArticleParser
                            .parseAsArticles(fc.getInputStream());
                    articlesIt = articles.iterator();
                    if (articlesIt.hasNext()) {
                        return true;
                    } else { // empty, try next file
                        return hasNext();
                    }
                }
            }
        }
        return false;
    }

    public Progress[] getProgress() {// nope
        return null;
    }

    @Override
    public void close() throws IOException {// nope
    }
}
