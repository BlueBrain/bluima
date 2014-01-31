package ch.epfl.bbp.uima.ae;

import static java.lang.Runtime.getRuntime;
import static java.lang.Thread.currentThread;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;

/**
 * "Forces" to run the garbage collection every N document.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class GarbageCollectorAnnotator extends JCasAnnotator_ImplBase {

    private static final Runtime s_runtime = getRuntime();

    public static final String RUN_EVERY_N_DOCUMENTS = "runEveryNDocument";
    @ConfigurationParameter(name = RUN_EVERY_N_DOCUMENTS, //
    description = "", defaultValue = "10000")
    private int runEveryNDocument;

    private long counter = 0;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        if (counter++ % runEveryNDocument == 0) {
            try {
                runGC();
            } catch (Throwable t) {// nope
            }
        }
    }

    /**
     * "Forces" to run garbage collection by calling Runtime.gc() using several
     * method calls
     */
    public static void runGC() {
        for (int r = 0; r < 4; ++r)
            _runGC();
    }

    @SuppressWarnings("static-access")
    private static void _runGC() {
        long usedMem1 = usedMemory(), usedMem2 = Long.MAX_VALUE;
        for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++i) {
            s_runtime.runFinalization();
            s_runtime.gc();
            currentThread().yield();

            usedMem2 = usedMem1;
            usedMem1 = usedMemory();
        }
    }

    private static long usedMemory() {
        return s_runtime.totalMemory() - s_runtime.freeMemory();
    }

    public static long freeMemory() {
        return s_runtime.freeMemory();
    }

    public static long maxMemory() {
        return s_runtime.maxMemory();
    }
}
