/**
  Evaluate segmentation f1 for several different tags (marked in OIB format).
  For example, tags might be B-PERSON I-PERSON O B-LOCATION I-LOCATION O...

@author Andrew McCallum <a href="mailto:mccallum@cs.umass.edu">mccallum@cs.umass.edu</a>
 */

package cc.mallet.fst;

import java.text.DecimalFormat;

import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Sequence;

/**
 * Evaluates a transducer model, computes the precision, recall and F1 scores;
 * considers segments that span across multiple tokens.
 */
public class MultiSegmentationEvaluator extends TransducerEvaluator {

    // equals() is called on these objects to determine if this token is the
    // start or continuation of a segment.
    // A tag not equal to any of these is an "other".
    // is not part of the segment).
    Object[] segmentStartTags;
    Object[] segmentContinueTags;
    Object[] segmentStartOrContinueTags;

    public MultiSegmentationEvaluator(InstanceList[] instanceLists,
            String[] instanceListDescriptions, Object[] segmentStartTags,
            Object[] segmentContinueTags) {
        super(instanceLists, instanceListDescriptions);
        this.segmentStartTags = segmentStartTags;
        this.segmentContinueTags = segmentContinueTags;
        assert (segmentStartTags.length == segmentContinueTags.length);
    }
    


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
            for (int j = 0; j < trueOutput.size(); j++) {
                totalTokens++;
                if (trueOutput.get(j).equals(predOutput.get(j)))
                    numCorrectTokens++;
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
        }
        DecimalFormat f = new DecimalFormat("0.####");
        System.err.println(description + " tokenaccuracy="
                + f.format(((double) numCorrectTokens) / totalTokens));
        for (int n = 0; n < numCorrectSegments.length; n++) {
            System.err.println((n < allIndex ? segmentStartTags[n].toString()
                    : "OVERALL") + ' ');
            double precision = numPredictedSegments[n] == 0 ? 1
                    : ((double) numCorrectSegments[n])
                            / numPredictedSegments[n];
            double recall = numTrueSegments[n] == 0 ? 1
                    : ((double) numCorrectSegments[n]) / numTrueSegments[n];
            double f1 = recall + precision == 0.0 ? 0.0
                    : (2.0 * recall * precision) / (recall + precision);
            System.err.println(" " + description + " segments true="
                    + numTrueSegments[n] + " pred=" + numPredictedSegments[n]
                    + " correct=" + numCorrectSegments[n] + " misses="
                    + (numTrueSegments[n] - numCorrectSegments[n]) + " alarms="
                    + (numPredictedSegments[n] - numCorrectSegments[n]));
            System.err.println(" " + description + " precision="
                    + f.format(precision) + " recall=" + f.format(recall)
                    + " f1=" + f.format(f1));
        }

    }

    /**
     * Returns the number of incorrect segments in <code>predOutput</code>
     * 
     * @param trueOutput
     *            truth
     * @param predOutput
     *            predicted
     * @return number of incorrect segments
     */
    public int numIncorrectSegments(Sequence trueOutput, Sequence predOutput) {
        int numCorrectTokens, totalTokens;
        int[] numTrueSegments, numPredictedSegments, numCorrectSegments;
        int allIndex = segmentStartTags.length;
        numTrueSegments = new int[allIndex + 1];
        numPredictedSegments = new int[allIndex + 1];
        numCorrectSegments = new int[allIndex + 1];
        totalTokens = numCorrectTokens = 0;
        for (int n = 0; n < numTrueSegments.length; n++)
            numTrueSegments[n] = numPredictedSegments[n] = numCorrectSegments[n] = 0;
        assert (predOutput.size() == trueOutput.size());
        // -1 for non-start, otherwise index into segmentStartTag
        int trueStart, predStart;
        for (int j = 0; j < trueOutput.size(); j++) {
            totalTokens++;
            if (trueOutput.get(j).equals(predOutput.get(j)))
                numCorrectTokens++;
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
        int wrong = 0;
        for (int n = 0; n < numCorrectSegments.length; n++) {
            // incorrect segment is either false pos or false neg.
            wrong += numTrueSegments[n] - numCorrectSegments[n];
        }
        return wrong;
    }

}
