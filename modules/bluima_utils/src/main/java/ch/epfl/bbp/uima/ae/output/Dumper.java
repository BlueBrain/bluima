package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * Dumps it all to sysout
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Dumper extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        String pmId = getHeaderDocId(jCas);
        System.out.println(pmId + " -----------------------------");
        FSIterator<Annotation> it = jCas.getAnnotationIndex().iterator();
        StringBuffer sb = new StringBuffer();
        while (it.hasNext()) {
            Annotation a = it.next();
            sb.append(a.getCoveredText() + '\n');
            a.prettyPrint(2, 2, sb, false);
            sb.append('\n');

        }
        System.out.println(sb);
    }
}