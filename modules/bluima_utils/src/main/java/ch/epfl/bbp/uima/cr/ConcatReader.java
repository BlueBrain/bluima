package ch.epfl.bbp.uima.cr;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;

import com.google.common.collect.Lists;

/**
 * TODO
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ConcatReader extends JCasCollectionReader_ImplBase {

    SingleAbstractReader r1;
    SingleAbstractReader r2;
    List<CollectionReader> readers = Lists.newArrayList();
    private Iterator<CollectionReader> readersIt;
    private CollectionReader currentReader;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        SingleAbstractReader r1 = new SingleAbstractReader();
        r1.initialize(context);
        readers.add(r1);

        SingleAbstractReader r2 = new SingleAbstractReader();
        r2.initialize(context);
        readers.add(r2);

        readersIt = readers.iterator();
        currentReader = readersIt.next();
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {
        currentReader.getNext(jcas.getCas());
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {

        if (currentReader.hasNext())
            return true;
        else if (readersIt.hasNext()) {
            currentReader = readersIt.next();
            return hasNext();// recursive

        } else
            return false;
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }
}