package ch.epfl.bbp.uima.laucher;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.System.currentTimeMillis;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.TypeOrFeature;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.Capability;
import org.apache.uima.resource.metadata.ResourceMetaData;
import org.apache.uima.util.InvalidXMLException;
import org.slf4j.Logger;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.uimafit.CpeBuilder;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

/**
 * Abstraction to encapsulate a {@link CollectionReaderDescription} and several
 * {@link AnalysisEngineDescription}s, and run them
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Pipeline {
    private static Logger LOG = getLogger(Pipeline.class);

    CollectionReaderDescription crd = null;
    List<AnalysisEngineDescription> aeds = newArrayList();
    private int threads = 2;
    private int maxErrors = 0;

    /**
     * The output types (Annotation class names) outputted by this pipeline, as
     * described in the @TypeCapability of {@link AnalysisEngine}s
     */
    List<String> outputTypes = newArrayList();

    public CollectionReaderDescription getCr() {
        return crd;
    }

    public void setCr(CollectionReaderDescription crd) {
        this.crd = crd;
        addCapabilities(crd);
    }

    public void addAe(AnalysisEngineDescription description) {
        aeds.add(description);
        checkAndAddCapabilities(description);
    }

    public void add(Class<? extends JCasAnnotator_ImplBase> annotatorClass,
            Object... configurationData) throws InvalidXMLException,
            ResourceInitializationException, IOException, SAXException,
            CpeDescriptorException {
        addAe(createEngineDescription(annotatorClass, configurationData));
    }

    /** Add Aes from @param p to the current pipeline */
    public void addAesFrom(Pipeline p) {
        for (AnalysisEngineDescription aed : p.getAeds()) {
            addAe(aed);
        }
    }

    public List<AnalysisEngineDescription> getAeds() {
        return aeds;
    }

    public JcasPipelineBuilder getJCasBuilder() throws UIMAException,
            IOException, SAXException, CpeDescriptorException {
        return (JcasPipelineBuilder) build(new JcasPipelineBuilder());
    }

    public void run() throws UIMAException, IOException {

        if (threads == 1) {
            // some scripts (RUTA) only work with 1 thread ATM (because of
            // relative paths to typesystem) ...
            long start = currentTimeMillis();
            aeds.add(createEngineDescription(PrintNrDocs.class)); // stats
            SimplePipeline.runPipeline(crd,
                    aeds.toArray(new AnalysisEngineDescription[aeds.size()]));
            LOG.info("Processing took " + (currentTimeMillis() - start) / 1000
                    + "s in total");
        } else {
            try {
                CpeBuilder cpeBuilder = (CpeBuilder) build(new CpeBuilder(
                        this.crd)//
                        .setMaxProcessingUnitThreatCount(threads)//
                        .setMaxErrors(maxErrors));
                cpeBuilder.process();
            } catch (Exception e) {
                throw new UIMAException(e);
            }
        }
    }

    public static class PrintNrDocs extends JCasAnnotator_ImplBase {

        private int nrDocs = 0;

        @Override
        public void process(JCas aJCas) throws AnalysisEngineProcessException {
            nrDocs++;
        }

        @Override
        public void collectionProcessComplete()
                throws AnalysisEngineProcessException {
            LOG.info("Processed " + nrDocs + " documents");
        }
    }

    public void run(JCas jCas) throws UIMAException, IOException {
        try {
            JcasPipelineBuilder builder = new JcasPipelineBuilder(jCas);
            build(builder);
            builder.process();
        } catch (Exception e) {
            throw new UIMAException(e);
        }
    }

    private PipelineBuilder build(PipelineBuilder builder)
            throws InvalidXMLException, IOException, SAXException,
            CpeDescriptorException {

        Set<String> cpeNames = newHashSet();

        for (AnalysisEngineDescription aed : this.aeds) {

            // workaround to use multiple cpe with the same name
            String name = aed.getMetaData().getName();
            if (cpeNames.contains(name)) {
                ResourceMetaData metaData = aed.getMetaData();
                String newName = name + System.currentTimeMillis();
                metaData.setName(newName);
                aed.setMetaData(metaData);
                cpeNames.add(newName);
            } else {
                cpeNames.add(name);
            }
            builder.add(aed);
        }
        return builder;
    }

    public Pipeline setThreads(int threads) {
        this.threads = threads;
        return this;
    }

    public Pipeline setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
        return this;
    }

    /** Add this crd's capabilities for other downstream aeds. */
    private void addCapabilities(CollectionReaderDescription crd) {

        for (Capability capability : crd.getCollectionReaderMetaData()
                .getCapabilities()) {
            for (TypeOrFeature output : capability.getOutputs()) {
                // LOG.info("add @TypeCapability: " + output.getName());
                outputTypes.add(output.getName());
            }
        }
    }

    /**
     * Checks that this @param aed is provided with the right {@link Annotation}
     * s in the upstream of this pipeline, and prints an error log otherwise.
     * Add this aed's capabilities for other downstream aeds.
     */
    private void checkAndAddCapabilities(AnalysisEngineDescription aed) {
        for (Capability capability : aed.getAnalysisEngineMetaData()
                .getCapabilities()) {
            for (TypeOrFeature input : capability.getInputs())
                if (!outputTypes.contains(input.getName()))
                    LOG.error("AnalysisEngine "
                            + aed.getAnnotatorImplementationName()
                            + " is missing input @TypeCapability: "
                            + input.getName());
        }
        for (Capability capability : aed.getAnalysisEngineMetaData()
                .getCapabilities()) {
            for (TypeOrFeature output : capability.getOutputs()) {
                // LOG.info("add @TypeCapability: " + output.getName());
                outputTypes.add(output.getName());
            }
        }
    }

    public List<String> getOutputTypes() {
        return outputTypes;
    }

    public void addAesTo(PipelineBuilder builder) throws InvalidXMLException,
            IOException, SAXException, CpeDescriptorException {
        for (AnalysisEngineDescription aDesc : getAeds()) {
            builder.add(aDesc);
        }
    }
}
