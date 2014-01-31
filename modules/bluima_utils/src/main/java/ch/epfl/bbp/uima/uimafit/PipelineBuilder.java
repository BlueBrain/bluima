package ch.epfl.bbp.uima.uimafit;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.xml.sax.SAXException;

public interface PipelineBuilder {

    public abstract void setReader(CollectionReaderDescription aDesc)
            throws IOException, SAXException, CpeDescriptorException;

    public abstract void add(AnalysisEngineDescription aDesc)
            throws IOException, SAXException, CpeDescriptorException,
            InvalidXMLException;

    public abstract void add(
            Class<? extends JCasAnnotator_ImplBase> annotatorClass,
            Object... configurationData) throws InvalidXMLException,
            ResourceInitializationException, IOException, SAXException,
            CpeDescriptorException;

    public abstract Object process() throws ResourceInitializationException,
            CpeDescriptorException, UIMAException, IOException;

}