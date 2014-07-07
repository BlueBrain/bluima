package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static org.apache.commons.lang.StringEscapeUtils.unescapeCsv;

import java.io.IOException;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import de.julielab.jules.types.Header;

/**
 * {@link CollectionReader} that takes a simple text file as input. Each line is
 * turned into a new {@link CAS}. Format is:<br>
 * <code>docId{tab}"title"{tab}"abstract"</code><br>
 * Lines starting with '#' are ignored.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class OneDocPerLineReader2 extends OneDocPerLineReader {

    public void getNext(JCas jCas) throws IOException, CollectionException {

        String[] split = nextLine.split("\t");
        checkEquals(3, split.length, "pmid" + split[0]);

        String pmid = unescapeCsv(split[0]), title = unescapeCsv(split[1]), txt = unescapeCsv(split[2]);

        Header header = new Header(jCas);
        header.setDocId(pmid);
        header.setTitle(title);
        header.addToIndexes();

        jCas.setDocumentText(title + (title.endsWith(".") ? " " : ". ") + txt);

        jCas.setDocumentLanguage("en");
    }
}
