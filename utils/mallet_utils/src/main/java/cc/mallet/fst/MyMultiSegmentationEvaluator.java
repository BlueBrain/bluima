package cc.mallet.fst;

import java.text.DecimalFormat;

import cc.mallet.types.FeatureVector;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Sequence;

/**
 * Only works when evaluating one instancelist
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MyMultiSegmentationEvaluator extends MultiSegmentationEvaluator {
    // private static Logger LOG =
    // getLogger(MyMultiSegmentationEvaluator.class);

    protected static final DecimalFormat f = new DecimalFormat("0.####");

    protected boolean printMissclassified;

    public MyMultiSegmentationEvaluator(InstanceList[] il, String[] ild,
            Object[] sst, Object[] sct, boolean printMissclassified) {
        super(il, ild, sst, sct);
        if (il.length > 1) {
            throw new IllegalArgumentException(
                    "Only works when evaluating one instancelist");
        }
        this.printMissclassified = printMissclassified;
    }

    // stores the LAST values of:
    protected double tokenAccuracy, precision, recall, f1;

    public double getTokenAccuracy() {
        return tokenAccuracy;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    public double getF1() {
        return f1;
    }

    public String getReport() {
        // f.format(tokenAccuracy);
        return "precision=" + f.format(precision) + " recall="
                + f.format(recall) + " f1=" + f.format(f1);
    }

    @Override
    public String toString() {
        return getReport();
    }

    // copypaste from superclass, just changed last last part
    // it's bad, but mallet logging is worse
    @SuppressWarnings("rawtypes")
    public void evaluateInstanceList(TransducerTrainer tt, InstanceList data,
            String description) {
        Transducer model = tt.getTransducer();
        int numCorrectTokens, totalTokens;
        int[] numTrueSegments, numPredictedSegments, numCorrectSegments;
        int allIndex = segmentStartTags.length;
        numTrueSegments = new int[allIndex + 1];
        numPredictedSegments = new int[allIndex + 1];
        numCorrectSegments = new int[allIndex + 1];

        totalTokens = numCorrectTokens = 0;
        for (int n = 0; n < numTrueSegments.length; n++)
            numTrueSegments[n] = numPredictedSegments[n] = numCorrectSegments[n] = 0;
        for (int i = 0; i < data.size(); i++) {
            Instance instance = data.get(i);
            Sequence input = (Sequence) instance.getData();
            // String tokens = null;
            // if (instance.getSource() != null)
            // tokens = (String) instance.getSource().toString();
            Sequence trueOutput = (Sequence) instance.getTarget();
            assert (input.size() == trueOutput.size());
            Sequence predOutput = model.transduce(input);
            assert (predOutput.size() == trueOutput.size());
            int trueStart, predStart; // -1 for non-start, otherwise index into
                                      // segmentStartTag

            boolean hasIncorrect = false;
            StringBuilder sentencePrint = new StringBuilder();

            for (int j = 0; j < trueOutput.size(); j++) {
                totalTokens++;
                if (trueOutput.get(j).equals(predOutput.get(j))) {
                    numCorrectTokens++;
                    if (printMissclassified)
                        sentencePrint.append(getText(input, j) + " ");

                } else if (printMissclassified) {// not predicted correctly
                    hasIncorrect = true;
                    sentencePrint.append("{{" + getText(input, j) + "}} ");
                }

                trueStart = predStart = -1;
                // Count true segment starts
                for (int n = 0; n < segmentStartTags.length; n++) {
                    if (segmentStartTags[n].equals(trueOutput.get(j))) {
                        numTrueSegments[n]++;
                        numTrueSegments[allIndex]++;
                        trueStart = n;
                        break;
                    }
                }
                // Count predicted segment starts
                for (int n = 0; n < segmentStartTags.length; n++) {
                    if (segmentStartTags[n].equals(predOutput.get(j))) {
                        numPredictedSegments[n]++;
                        numPredictedSegments[allIndex]++;
                        predStart = n;
                    }
                }
                if (trueStart != -1 && trueStart == predStart) {
                    // Truth and Prediction both agree that the same segment
                    // tag-type is starting now
                    int m;
                    boolean trueContinue = false;
                    boolean predContinue = false;
                    for (m = j + 1; m < trueOutput.size(); m++) {
                        trueContinue = segmentContinueTags[predStart]
                                .equals(trueOutput.get(m));
                        predContinue = segmentContinueTags[predStart]
                                .equals(predOutput.get(m));
                        if (!trueContinue || !predContinue) {
                            if (trueContinue == predContinue) {
                                // They agree about a segment is ending somehow
                                numCorrectSegments[predStart]++;
                                numCorrectSegments[allIndex]++;
                            }
                            break;
                        }
                    }
                    // for the case of the end of the sequence
                    if (m == trueOutput.size()) {
                        if (trueContinue == predContinue) {
                            numCorrectSegments[predStart]++;
                            numCorrectSegments[allIndex]++;
                        }
                    }
                }
            }
            if (hasIncorrect && printMissclassified)
                System.out.println(sentencePrint.toString());
        }

        tokenAccuracy = ((double) numCorrectTokens) / totalTokens;
        for (int n = 0; n < numCorrectSegments.length; n++) {
            if (!(n < allIndex)) {// only "OVERALL"
                precision = numPredictedSegments[n] == 0 ? 1
                        : ((double) numCorrectSegments[n])
                                / numPredictedSegments[n];
                recall = numTrueSegments[n] == 0 ? 1
                        : ((double) numCorrectSegments[n]) / numTrueSegments[n];
                f1 = recall + precision == 0.0 ? 0.0
                        : (2.0 * recall * precision) / (recall + precision);
            }
        }
    }

    protected static String getText(
            @SuppressWarnings("rawtypes") Sequence input, int j) {
        FeatureVector fv = (FeatureVector) input.get(j);
        for (int idx : fv.getIndices()) {

            Object ooo = fv.getAlphabet().lookupObject(idx);
            if (ooo.toString().startsWith("text=")
                    && !ooo.toString().matches("^.+?\\/[-\\+]\\d$")) {
                return ooo.toString().substring(5);
            }
        }
        return "";
    }
}
