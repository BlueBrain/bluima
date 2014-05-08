package ch.epfl.bbp.uima.uimafit;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.xml.sax.SAXException;

/**
 * {@link PipelineBuilder} that handles one or more {@link JCas}es. To handle
 * new {@link JCas}, call {@link #process(JCas)}
 * 
 * @author richarde
 */
public class JcasPipelineBuilder implements PipelineBuilder {

    private JCas jCas;
    private List<AnalysisEngineDescription> aeds = new ArrayList<AnalysisEngineDescription>();

    public JcasPipelineBuilder() {
    }

    public JcasPipelineBuilder(String txt) throws UIMAException {
        this.jCas = getTestCas(txt);
    }

    public JcasPipelineBuilder(JCas jCas) {
        this.jCas = jCas;
    }

    @Override
    public void setReader(CollectionReaderDescription aDesc)
            throws IOException, SAXException, CpeDescriptorException {
        // nope
    }

    @Override
    public void add(AnalysisEngineDescription aDesc) throws IOException,
            SAXException, CpeDescriptorException, InvalidXMLException {
        if (engines != null)
            throw new IllegalArgumentException(
                    "cannot add more engines after first call to process()");
        aeds.add(aDesc);
    }

    @Override
    public void add(Class<? extends JCasAnnotator_ImplBase> annotatorClass,
            Object... configurationData) throws InvalidXMLException,
            ResourceInitializationException, IOException, SAXException,
            CpeDescriptorException {
        if (engines != null)
            throw new IllegalArgumentException(
                    "cannot add more engines after first call to process()");
        add(AnalysisEngineFactory.createEngineDescription(annotatorClass,
                configurationData));
    }

    @Override
    public JCas process() throws CpeDescriptorException, UIMAException,
            IOException {
        if (engines == null) {
            engines = createEngines(aeds
                    .toArray(new AnalysisEngineDescription[aeds.size()]));
        }
        SimplePipeline.runPipeline(jCas, engines);
        return jCas;
    }

    public JCas process(JCas jcas_) throws CpeDescriptorException,
            UIMAException, IOException {
        this.jCas = jcas_;
        return process();
    }

    private AnalysisEngine[] engines = null;

    private static AnalysisEngine[] createEngines(
            AnalysisEngineDescription... descs) throws UIMAException {
        AnalysisEngine[] engines = new AnalysisEngine[descs.length];
        for (int i = 0; i < engines.length; ++i) {
            if (descs[i].isPrimitive()) {
                engines[i] = AnalysisEngineFactory.createEngine(descs[i]);
            } else {
                engines[i] = AnalysisEngineFactory.createEngine(descs[i]);
            }
        }
        return engines;
    }
}
