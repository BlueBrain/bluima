package ch.epfl.bbp.uima.uimafit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.typesystem.TypeSystem;

public class SimplePipelineBuilder implements PipelineBuilder {

	private List<AnalysisEngineDescription> aeds = new ArrayList<AnalysisEngineDescription>();
	private CollectionReaderDescription readerDesc;

	public SimplePipelineBuilder() {
	}

	public SimplePipelineBuilder(CollectionReaderDescription aDesc)
			throws IOException, SAXException, CpeDescriptorException {
		setReader(aDesc);
	}

	public SimplePipelineBuilder(Class<? extends CollectionReader> readerClass,
			Object... configurationData) throws IOException, SAXException,
			CpeDescriptorException, ResourceInitializationException {
		setReader(CollectionReaderFactory.createReaderDescription(readerClass,
				TypeSystem.JULIE_TSD, configurationData));
	}

	@Override
	public void setReader(CollectionReaderDescription aDesc)
			throws IOException, SAXException, CpeDescriptorException {
		this.readerDesc = aDesc;
	}

	@Override
	public void add(AnalysisEngineDescription aDesc) throws IOException,
			SAXException, CpeDescriptorException, InvalidXMLException {
		aeds.add(aDesc);
	}

	@Override
	public void add(Class<? extends JCasAnnotator_ImplBase> annotatorClass,
			Object... configurationData) throws InvalidXMLException,
			ResourceInitializationException, IOException, SAXException,
			CpeDescriptorException {
		add(AnalysisEngineFactory.createEngineDescription(annotatorClass,
				configurationData));
	}

	@Override
	public Boolean process() throws CpeDescriptorException, UIMAException,
			IOException {
		SimplePipeline.runPipeline(readerDesc,
				aeds.toArray(new AnalysisEngineDescription[aeds.size()]));
		return true;
	}
}
