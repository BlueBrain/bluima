package ch.epfl.bbp.uima.utils;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;

public class MyDumpWriter {

    public static AnalysisEngineDescription getDumpWriterAED()
            throws ResourceInitializationException {
        return createEngineDescription(CasDumpWriter.class,
                CasDumpWriter.PARAM_OUTPUT_FILE, "-");
    }

    public static AnalysisEngine getDumpWriterAE()
            throws ResourceInitializationException {
        AnalysisEngineDescription dumpWriterAED = getDumpWriterAED();
        if (dumpWriterAED.isPrimitive()) {
            return AnalysisEngineFactory.createEngine(dumpWriterAED);
        } else {
            return AnalysisEngineFactory.createEngine(dumpWriterAED);
        }
    }

}
