package ch.epfl.bbp.uima.validation;

import java.util.Collection;

import org.apache.uima.jcas.tcas.Annotation;

public class TestEvaluator<T extends Annotation, U extends Annotation> {

    private final Comparator<T, U> comparator;

    private int expectedCnt = 0, actualCnt = 0, truePositives = 0,
            falseNegatives = 0, falsePositives = 0;

    public TestEvaluator(Comparator<T, U> comparator) {
        this.comparator = comparator;
    }

    public Comparator<T, U> getComparator() {
        return comparator;
    }

    public String add(Collection<T> expecteds, Collection<U> actuals,
            String pmId) {
        StringBuilder sb = new StringBuilder();

        int _truePositives = 0;
        int _falseNegatives = 0;
        // int _falsePositives = 0;
        int expectedsCnt = 0; // a counter
        for (T expected : expecteds) {
            boolean _localFN = true;
            for (U actual : actuals) {
                if (comparator.areTheSame(expected, actual)) {
                    _truePositives++;
                    _localFN = false;
                    break;// external_loop;
                }
            }
            if (_localFN) {// false negative
                _falseNegatives++;
                sb.append("fn: " + expected.getCoveredText() + "\n");
            }

            // System.out.println("XX\t" + pmId + "\t" + expectedsCnt++ + "\t"
            // + (_localFN ? "fn" : "ok\n"));

        }
        int _falsePositives = 0;
        for (U actual : actuals) {
            boolean _localFP = false;
            for (T expected : expecteds) {
                if (comparator.areTheSame(expected, actual)) {
                    _localFP = true;
                    break;// external_loop;
                }
            }
            if (!_localFP == true) {
                _falsePositives++;
                sb.append("fp: " + actual.getCoveredText() + "\n");

                /*-
                // // to print nice output of false positives
                try {
                    JCas jCas = actual.getCAS().getJCas();
                    Sentence sentence = JCasUtil
                            .selectCovering(jCas, Sentence.class, actual)
                            .iterator().next();
                // Cooccurrence c = ((Cooccurrence) actual);
                // String snippet = WriteCoocurrencesToLoadfile2.snippet(jCas,
                //      sentence.getBegin(), sentence.getEnd(),
                //      c.getFirstEntity(), c.getSecondEntity());
                    String snippet = BlueCasUtil.inspect(actual);
                    MissingUtils
                            .printf("<p>{} <a href='http://www.ncbi.nlm.nih.gov/pubmed/?term={}'>PubMed</a></p>",
                                    snippet, pmId);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        }

        truePositives += _truePositives;
        falsePositives += _falsePositives;
        falseNegatives += _falseNegatives;
        expectedCnt += expecteds.size();
        actualCnt += actuals.size();
        sb.append("tp:" + _truePositives + " fp:" + _falsePositives + " fn:"
                + _falseNegatives + "\n");
        return sb.toString();
    }

    public TestResult compare() {
        if (expectedCnt == 0 || actualCnt == 0) {
            return new TestResult(0, 0, 0, 0);
        }

        double precision = (truePositives + 0d)
                / (truePositives + falsePositives + 0d);
        double recall = (truePositives + 0d) / (expectedCnt + 0d);

        return new TestResult(expectedCnt, actualCnt, precision, recall);
    }

    /**
     * Compares the start/end on the same annotation.
     * 
     * @param annotation
     * @return
     */
    public static <A extends Annotation> TestEvaluator<A, A> getSimpleEvaluator() {

        Comparator<A, A> simpleComparator = new Comparator<A, A>() {
            @Override
            public boolean areTheSame(A expected, A actual) {
                if (expected.getBegin() == actual.getBegin()
                        && expected.getEnd() == actual.getEnd()) {
                    return true;
                }
                return false;
            }
        };
        return new TestEvaluator<A, A>(simpleComparator);
    }

    /** Compares the start/end on the same annotation */
    public static <A extends Annotation, B extends Annotation> TestEvaluator<A, B> getExactEvaluator() {

        Comparator<A, B> simpleComparator = new Comparator<A, B>() {
            @Override
            public boolean areTheSame(A expected, B actual) {
                if (expected.getBegin() == actual.getBegin()
                        && expected.getEnd() == actual.getEnd()) {
                    return true;
                }
                return false;
            }
        };
        return new TestEvaluator<A, B>(simpleComparator);
    }

    /** 'gold' is at least covered by 'system'; probably too lenient */
    public static <A extends Annotation, B extends Annotation> TestEvaluator<A, B> getAtLeastCoveredEvaluator() {
        Comparator<A, B> simpleComparator = new Comparator<A, B>() {
            @Override
            public boolean areTheSame(A expected, B actual) {
                if (expected.getBegin() >= actual.getBegin()
                        && expected.getEnd() <= actual.getEnd()) {
                    return true;
                }
                return false;
            }
        };
        return new TestEvaluator<A, B>(simpleComparator);
    }

    /** 'gold' is covering 'system' */
    public static <A extends Annotation, B extends Annotation> TestEvaluator<A, B> getAtLeastCoveringEvaluator() {
        Comparator<A, B> simpleComparator = new Comparator<A, B>() {
            @Override
            public boolean areTheSame(A expected, B actual) {
                if (expected.getBegin() <= actual.getBegin()
                        && expected.getEnd() >= actual.getEnd()) {
                    return true;
                }
                return false;
            }
        };
        return new TestEvaluator<A, B>(simpleComparator);
    }

    public static <A extends Annotation, B extends Annotation> TestEvaluator<A, B> getOverlapEvaluator() {
        Comparator<A, B> simpleComparator = new Comparator<A, B>() {
            @Override
            public boolean areTheSame(A expected, B actual) {
                if (expected.getBegin() < actual.getEnd()
                        && expected.getEnd() > actual.getBegin()) {
                    return true;
                }
                return false;
            }
        };
        return new TestEvaluator<A, B>(simpleComparator);
    }
}
