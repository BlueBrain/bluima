package ch.epfl.bbp.uima.ae;

import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.ae.cleanup.DisambiguatorAnnotator;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

import de.julielab.jules.types.Token;

//because of instance fields:
@OperationalProperties(multipleDeploymentAllowed = false)
public class EvaluateCoverageAnnotator extends JCasAnnotator_ImplBase {

    static int tokenNotCovered = 0, partialCnt = 0, tokenCovered = 0,
            hasAbstract = 0, noAbstract = 0;
    static Histogram<String> textHistogram = new Histogram<String>();

    // @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // HAS ABSTRACT?
        if (jCas.getDocumentText().length() == 0) {
            noAbstract++;
            return;
        } else {
            hasAbstract++;
        }

        for (Token t : JCasUtil.select(jCas, Token.class)) {

            // GET ALL ANNOT. THAT COVER THIS TOKEN
            List<Annotation> allCovering = JCasUtil.selectCovering(jCas,
                    Annotation.class, t.getBegin(), t.getEnd());
            Collection<String> coveringNames = Collections2.transform(
                    allCovering, new Function<Annotation, String>() {
                        @Override
                        public String apply(Annotation input) {
                            return input.getClass().getName();
                        }
                    });

            // REMOVE "UNWANTED/UNUSEFUL" ANNOTS
            try {
                coveringNames
                        .removeAll(DisambiguatorAnnotator.SKIP_ANNOTATIONS);
            } catch (Exception e) {// nope
            }

            // IF NO ANNOTS REMAIN, THEN THIS TOKEN IS NOT COVERED!
            if (coveringNames.isEmpty()
                    && !SkipSomePosAnnotator.POS_SKIP.contains(t.getPos())) {
                tokenNotCovered++;
                textHistogram.add(t.getCoveredText());
                System.out.println(BlueCasUtil.inspect(t));

                // // try partial coverage
                // boolean hasPartialCoverage = false;
                // FSIterator<Annotation> it = jCas.getAnnotationIndex()
                // .iterator();
                // while (it.hasNext()) {
                // Annotation a = it.next();
                // if (!DisambiguatorAnnotator.SKIP_ANNOTATIONS.contains(a
                // .getClass().getName())) {
                //
                // int coverage = 0;
                // if (a.getBegin() < t.getBegin()
                // && a.getEnd() > t.getBegin()) {
                // coverage = a.getEnd() - t.getBegin();
                //
                // } else if (a.getBegin() < t.getEnd()
                // && a.getEnd() > t.getEnd()) {
                // coverage = t.getEnd() - a.getBegin();
                // }
                //
                // if (coverage > 0) {
                // hasPartialCoverage = true;
                // float coverageRatio = (coverage + 0f)
                // / (t.getCoveredText().length() + 0f);
                // coverageRatio = (float) Math
                // .round(coverageRatio * 100) / 100;
                //
                // }
                // }
                // }
                // if (hasPartialCoverage) {
                // partialCnt++;
                // }

            } else {
                tokenCovered++;
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();

        System.out.println("empty   " + tokenNotCovered);
        System.out.println("covered " + tokenCovered);
        System.out
                .println("coverage "
                        + ((tokenCovered + 0d) / (tokenNotCovered
                                + tokenCovered + 0d)));
        // System.out.println("partialCnt " + partialCnt);
        System.out.println("abstr " + hasAbstract);
        System.out.println("no abst " + noAbstract);

        System.out.println("histogram:\n" + textHistogram);

    }

}
