package ch.epfl.bbp.uima.cr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.CollectionReader_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.ConfigurationParameterSettings;
import org.apache.uima.resource.metadata.ResourceMetaData;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.Progress;
import org.apache.uima.fit.factory.CollectionReaderFactory;

/**
 * Aggregates several collection reader. There is no way you could describe the
 * resulting collection reader using a descriptor file or even a
 * CollectionReaderDescription such that you could reinstantiate it again. All
 * that to caution that its a quick and dirty solution.
 * 
 * @author https://code.google.com/p/uimafit/issues/detail?id=17
 */
public class AggregateCollectionReader extends CollectionReader_ImplBase {

    private Iterator<CollectionReader> readerIterator;
    private List<CollectionReader> readers;
    private CollectionReader currentReader;

    public AggregateCollectionReader(List<CollectionReader> readers,
	    TypeSystemDescription tsd) {
	try {
	    CollectionReaderDescription crd = CollectionReaderFactory
		    .createReaderDescription(AggregateCollectionReader.class, tsd);
	    ResourceMetaData metaData = crd.getMetaData();
	    ConfigurationParameterSettings paramSettings = metaData
		    .getConfigurationParameterSettings();
	    Map<String, Object> additionalParameters = new HashMap<String, Object>();
	    additionalParameters
		    .put(CollectionReader.PARAM_CONFIG_PARAM_SETTINGS,
			    paramSettings);
	    initialize(crd, additionalParameters);

	    this.readers = readers;
	    this.readerIterator = this.readers.iterator();
	    currentReader = this.readerIterator.next();
	} catch (ResourceInitializationException rie) {
	    throw new RuntimeException(rie);
	}
    }

    public void getNext(CAS aCAS) throws IOException, CollectionException {
	currentReader.getNext(aCAS);
    }

    public void close() throws IOException {
	for (CollectionReader reader : readers) {
	    reader.close();
	}
    }

    public Progress[] getProgress() {
	return new Progress[0];
    }

    public boolean hasNext() throws IOException, CollectionException {
	if (currentReader.hasNext()) {
	    return true;
	} else {
	    if (readerIterator.hasNext()) {
		currentReader = readerIterator.next();
		return hasNext();
	    }
	}
	return false;
    }
}
