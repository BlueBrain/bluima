package ch.epfl.bbp.uima.uimafit;

/*******************************************************************************
 * Copyright 2011
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *   
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import static org.apache.uima.collection.impl.metadata.cpe.CpeDescriptorFactory.produceCollectionReader;
import static org.apache.uima.collection.impl.metadata.cpe.CpeDescriptorFactory.produceDescriptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.metadata.FixedFlow;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.collection.StatusCallbackListener;
import org.apache.uima.collection.impl.metadata.CpeDefaultValues;
import org.apache.uima.collection.impl.metadata.cpe.CpeDescriptionImpl;
import org.apache.uima.collection.impl.metadata.cpe.CpeDescriptorFactory;
import org.apache.uima.collection.metadata.CpeCasProcessor;
import org.apache.uima.collection.metadata.CpeCollectionReader;
import org.apache.uima.collection.metadata.CpeComponentDescriptor;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.collection.metadata.CpeInclude;
import org.apache.uima.collection.metadata.CpeIntegratedCasProcessor;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceManager;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.InvalidXMLException;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.utils.StopwatchUtils;

/**
 * Build a Collection Processing Engine from a
 * {@link CollectionReaderDescription} and a {@link AnalysisEngineDescription}.
 * If an aggregate analysis engine description is used, the builder will add
 * each child of the aggregate engine as one processor to the engine. This works
 * only for aggregate analysis engines using a {@link FixedFlow}.
 * 
 * @author Richard Eckart de Castilho
 */
public class CpeBuilder implements PipelineBuilder {
    protected final Log LOG = LogFactory.getLog(getClass());

    private static final String ACTION_ON_MAX_ERROR = "terminate";

    /**
     * used for calculating the CAS pool size which needs to be adjusted to the
     * number of parallel pipelines
     */
    private int maxProcessingUnitThreatCount = 1;

    private final CpeDescriptionImpl cpeDesc = (CpeDescriptionImpl) produceDescriptor();

    /** Default: no errors tolerated */
    private int maxErrors = 0;

    public CpeBuilder() {
    }

    public CpeBuilder(CollectionReaderDescription aDesc) throws IOException,
            SAXException, CpeDescriptorException {
        setReader(aDesc);
    }

    /** use default ctor and setters instead */
    @Deprecated
    public CpeBuilder(int aMaxProcessingUnitThreatCount,
            CollectionReaderDescription aDesc) throws IOException,
            SAXException, CpeDescriptorException {
        setReader(aDesc);
        setMaxProcessingUnitThreatCount(aMaxProcessingUnitThreatCount);
    }

    public CpeBuilder setMaxProcessingUnitThreatCount(
            int aMaxProcessingUnitThreatCount) {
        maxProcessingUnitThreatCount = aMaxProcessingUnitThreatCount;
        return this;
    }

    /**
     * @param maxErrors
     *            the max number of errors before terminating the CPE, or -1 to
     *            continue despite errors
     */
    public CpeBuilder setMaxErrors(int maxErrors) {
        this.maxErrors = maxErrors;
        return this;
    }

    @Override
    public void setReader(CollectionReaderDescription aDesc)
            throws IOException, SAXException, CpeDescriptorException {
        // Remove all collection readers
        cpeDesc.setAllCollectionCollectionReaders(new CpeCollectionReader[0]);
        URL descUrl = materializeDescriptor(aDesc).toURI().toURL();
        CpeCollectionReader reader = produceCollectionReader(descUrl.toString());
        cpeDesc.addCollectionReader(reader);
    }

    public void setAnalysisEngineDescription(AnalysisEngineDescription aDesc)
            throws IOException, SAXException, CpeDescriptorException,
            InvalidXMLException {

        // Remove all CAS processors
        cpeDesc.setCpeCasProcessors(null);

        add(aDesc);
    }

    @Override
    public void add(AnalysisEngineDescription aDesc) throws IOException,
            SAXException, CpeDescriptorException, InvalidXMLException {

        if (aDesc.isPrimitive()) {
            // For a primitive AE we just add it.
            CpeIntegratedCasProcessor proc = createProcessor(aDesc
                    .getMetaData().getName(), aDesc);
            cpeDesc.addCasProcessor(proc);
        } else {
            // For an aggregate AE we dive into the first aggregation level and
            // add each of the
            // contained AEs separately, thus allowing us to control their
            // properties separately
            Map<String, ResourceSpecifier> delegates = aDesc
                    .getDelegateAnalysisEngineSpecifiers();
            FixedFlow flow = (FixedFlow) aDesc.getAnalysisEngineMetaData()
                    .getFlowConstraints();
            for (String key : flow.getFixedFlow()) {
                AnalysisEngineDescription aeDesc = (AnalysisEngineDescription) delegates
                        .get(key);
                boolean multi = aeDesc.getAnalysisEngineMetaData()
                        .getOperationalProperties()
                        .isMultipleDeploymentAllowed();
                LOG.info("[" + key + "] runs "
                        + (multi ? "multi-threaded" : "single-threaded"));
                CpeIntegratedCasProcessor proc = createProcessor(key, aeDesc);
                cpeDesc.addCasProcessor(proc);
            }
        }
    }

    public CollectionProcessingEngine createCpe(StatusCallbackListener aListener)
            throws ResourceInitializationException, CpeDescriptorException {
        ResourceManager resMgr = UIMAFramework.newDefaultResourceManager();

        // max errors
        if (maxErrors != 0) {
            for (CpeCasProcessor cpeCasProcessor : cpeDesc
                    .getCpeCasProcessors().getAllCpeCasProcessors()) {
                if (maxErrors == -1) // infinite nr errors ok
                    cpeCasProcessor.setActionOnMaxError("continue");
                else if (maxErrors > 0)
                    cpeCasProcessor.setMaxErrorCount(maxErrors);
            }
        }

        // thread cnt
        if (maxProcessingUnitThreatCount == 0) {
            cpeDesc.getCpeCasProcessors().setPoolSize(3);
        } else {
            cpeDesc.getCpeCasProcessors().setPoolSize(
                    maxProcessingUnitThreatCount + 2);
            cpeDesc.setProcessingUnitThreadCount(maxProcessingUnitThreatCount);
        }

        CollectionProcessingEngine cpe = UIMAFramework
                .produceCollectionProcessingEngine(cpeDesc, resMgr, null);
        cpe.addStatusCallbackListener(aListener);
        return cpe;
    }

    /**
     * Writes a temporary file containing a xml descriptor of the given
     * resource. Returns the file.
     * 
     * @param resource
     *            A resource specifier that should we materialized.
     * @return The file containing the xml representation of the given resource.
     * @throws IOException
     * @throws SAXException
     */
    private static File materializeDescriptor(ResourceSpecifier resource)
            throws IOException, SAXException {
        File tempDesc = File.createTempFile("desc", ".xml");
        // tempDesc.deleteOnExit();

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(tempDesc), "UTF-8"));
        resource.toXML(out);
        out.close();

        return tempDesc;
    }

    private static CpeIntegratedCasProcessor createProcessor(String key,
            AnalysisEngineDescription aDesc) throws IOException, SAXException,
            CpeDescriptorException {
        URL descUrl = materializeDescriptor(aDesc).toURI().toURL();

        CpeInclude cpeInclude = UIMAFramework.getResourceSpecifierFactory()
                .createInclude();
        cpeInclude.set(descUrl.toString());

        CpeComponentDescriptor ccd = UIMAFramework
                .getResourceSpecifierFactory().createDescriptor();
        ccd.setInclude(cpeInclude);

        CpeIntegratedCasProcessor proc = CpeDescriptorFactory
                .produceCasProcessor(key);
        proc.setCpeComponentDescriptor(ccd);
        proc.setAttributeValue(CpeDefaultValues.PROCESSING_UNIT_THREAD_COUNT, 1);
        proc.setActionOnMaxError(ACTION_ON_MAX_ERROR);
        proc.setMaxErrorCount(0);

        return proc;
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
    public Boolean process() throws ResourceInitializationException,
            CpeDescriptorException {
        StatusCallbackListenerSlf4J listener = new StatusCallbackListenerSlf4J();
        CollectionProcessingEngine cpe = createCpe(listener);
        listener.setCpe(cpe);// workaround to get performanceReports

        cpe.process();
        StatusCallbackListenerSlf4J callback = (StatusCallbackListenerSlf4J) listener;
        while (!callback.isCollectionProcessComplete()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException("should not happen, though...");
            }
        }
        StopwatchUtils.closeAndPrint(); // close all stopwatches (for profiling)
        return true;
    }

    public void toXML(File out) throws SAXException, IOException {
        cpeDesc.toXML(new FileWriter(out));
    }

    public static void runPipeline(CollectionReaderDescription crd,
            AnalysisEngineDescription... aeds) throws IOException,
            SAXException, CpeDescriptorException, InvalidXMLException,
            ResourceInitializationException {
        CpeBuilder builder = new CpeBuilder(crd);
        for (AnalysisEngineDescription aed : aeds) {
            builder.add(aed);
        }
        builder.process();
    }
}
