package cc.mallet.fst;

import java.util.LinkedList;
import java.util.List;

import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Sequence;

//import org.javatuples.Pair;

/**
 * Lenient (overlap) evaluation.<br>
 * Only works when evaluating one instancelist<br>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MyLenientMultiSegmentationEvaluator extends
        MyMultiSegmentationEvaluator {

    public MyLenientMultiSegmentationEvaluator(InstanceList[] il, String[] ild,
            Object[] sst, Object[] sct, boolean printMissclassified) {
        super(il, ild, sst, sct, printMissclassified);
    }

    @SuppressWarnings("rawtypes")
    public void evaluateInstanceList(TransducerTrainer tt, InstanceList data,
            String description) {
        Transducer model = tt.getTransducer();

        int trueCnt = 0;// int predCnt = 0;
        int tp = 0;
        int fp = 0;

        for (int i = 0; i < data.size(); i++) {
            Instance instance = data.get(i);
            Sequence input = (Sequence) instance.getData();

            Sequence trueOutput = (Sequence) instance.getTarget();
            Sequence predOutput = model.transduce(input);
            if ((input.size() != trueOutput.size()) || //
                    (predOutput.size() != trueOutput.size())) {
                throw new RuntimeException("inputs do not match");
            }

            List<Pair<Integer, Integer>> trueAnnots = new LinkedList<Pair<Integer, Integer>>(), predAnnots = new LinkedList<Pair<Integer, Integer>>();
            // -1 for non-start, otherwise start index
            int lastTrue = -1, lastPred = -1;

            StringBuilder sentencePrint = new StringBuilder();
            for (int j = 0; j < trueOutput.size(); j++) {

                // 1. Find out if this j is a BR (in true and pred)
                boolean isTrue = false, isPred = false;
                // Count true segment starts
                for (int n = 0; n < segmentStartTags.length; n++) {
                    if (segmentStartTags[n].equals(trueOutput.get(j))) {
                        isTrue = true;
                        break;
                    }
                }
                // Count predicted segment starts
                for (int n = 0; n < segmentStartTags.length; n++) {
                    if (segmentStartTags[n].equals(predOutput.get(j))) {
                        isPred = true;
                    }
                }
                // Count true segment continue
                for (int n = 0; n < segmentContinueTags.length; n++) {
                    if (segmentContinueTags[n].equals(trueOutput.get(j))) {
                        isTrue = true;
                        break;
                    }
                }
                // Count predicted segment continue
                for (int n = 0; n < segmentContinueTags.length; n++) {
                    if (segmentContinueTags[n].equals(predOutput.get(j))) {
                        isPred = true;
                    }
                }

                // 2. Do we start a new /already are in a BR (in true and pred)?
                // start new True?
                String prefix = " ";
                if (lastTrue == -1 && isTrue == true) {
                    prefix += "<G";
                    lastTrue = j; // just register for later

                } else if (lastTrue != -1 && isTrue == false) {
                    trueAnnots.add(new Pair<Integer, Integer>(lastTrue, j));
                    lastTrue = -1;
                    prefix += "G>";
                }
                // else do nothing (either it continues out or continues in

                // start new Pred?
                if (lastPred == -1 && isPred == true) {
                    prefix += "<S";
                    lastPred = j; // just register for later

                } else if (lastPred != -1 && isPred == false) {
                    predAnnots.add(new Pair<Integer, Integer>(lastPred, j));
                    lastPred = -1;
                    prefix += "S>";
                }
                // else do nothing (either it continues out or continues in

                // for printing
                sentencePrint.append(prefix + getText(input, j));
            }
            if (printMissclassified)
                System.out.println(sentencePrint);

            // 3. Count tp, fp; add to total counts
            int _truePositives = 0;
            for (Pair<Integer, Integer> expected : trueAnnots) {
                boolean _localFN = true;
                for (Pair<Integer, Integer> actual : predAnnots) {
                    if (areTheSame(expected, actual)) {
                        _truePositives++;
                        _localFN = false;
                        break;// external_loop;
                    }
                }
                if (_localFN && printMissclassified) {// false negative
                    System.out.println("FN: " + getText(input, expected));
                }
            }
            int _falsePositives = 0;
            for (Pair<Integer, Integer> actual : predAnnots) {
                boolean _localFP = false;
                for (Pair<Integer, Integer> expected : trueAnnots) {
                    if (areTheSame(expected, actual)) {
                        _localFP = true;
                        break;// external_loop;
                    }
                }
                if (!_localFP) {
                    _falsePositives++;
                    if (printMissclassified)
                        System.out.println("FP: " + getText(input, actual));
                }
            }

            tp += _truePositives;
            fp += _falsePositives;
            trueCnt += trueAnnots.size();
            // predCnt += predAnnots.size();
        }

        precision = (tp + 0d) / (tp + fp + 0d);
        recall = (tp + 0d) / (trueCnt + 0d);
        f1 = 2 * precision * recall / (precision + recall);
    }

    private String getText(@SuppressWarnings("rawtypes") Sequence input,
            Pair<Integer, Integer> annot) {
        StringBuilder sb = new StringBuilder();
        for (int i = annot.getKey(); i < annot.getValue(); i++) {
            sb.append(getText(input, i) + " ");
        }
        return sb.toString();
    }

    /** Here we do a lenient comparison */
    // TODO add more comparisons, see TestEvaluator
    protected boolean areTheSame_(Pair<Integer, Integer> expected,
            Pair<Integer, Integer> actual) {
        if (expected.getKey() < actual.getValue()
                && expected.getValue() > actual.getKey()) {
            return true;
        }
        return false;
    }

    /** Here we do a EXACT comparison */
    protected boolean areTheSame(Pair<Integer, Integer> expected,
            Pair<Integer, Integer> actual) {
        if (expected.getKey() == actual.getKey()
                && expected.getValue() == actual.getValue()) {
            return true;
        }
        return false;
    }
}
