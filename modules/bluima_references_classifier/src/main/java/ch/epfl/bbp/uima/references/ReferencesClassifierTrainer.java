package ch.epfl.bbp.uima.references;

import static cc.mallet.pipe.iterator.FileIterator.LAST_DIRECTORY;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang.StringUtils.join;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;

import cc.mallet.classify.Classification;
import cc.mallet.classify.Classifier;
import cc.mallet.classify.ClassifierTrainer;
import cc.mallet.classify.MaxEntTrainer;
import cc.mallet.classify.Trial;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Label;
import cc.mallet.types.Labeling;
import cc.mallet.util.Randoms;
import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.uima.utils.SilentMallet;

public class ReferencesClassifierTrainer {
    private static Logger LOG = getLogger(ReferencesClassifierTrainer.class);

    // private final static File root = new
    // File("referenceClassifier/debugging/");
    final static File CORPUS = new File(ReferencesHelper.REFERENCES_HOME
            + "src/test/resources/referenceClassifier/corpus/annots1/");
    private final static int trials = 100;
    static {
        new SilentMallet();
    }

    public static void main(String[] args) {

        // pipe instances
        InstanceList instanceList = new InstanceList(
                new SerialPipes(getPipes()));
        FileIterator iterator = new FileIterator(new File[] { CORPUS },
                new TxtFilter(), LAST_DIRECTORY);
        instanceList.addThruPipe(iterator);

        // ////////////////////////////////////////////////////////////////
        // cross-validate
        System.out.println("trial\tprec\trecall\tF-score");
        double f1s = 0;
        for (int i = 0; i < trials; i++) {
            Trial trial = testTrainSplit(instanceList);
            System.out.println(join(new Object[] {//
                    i, trial.getPrecision(TESTING), trial.getRecall(TESTING),
                            trial.getF1(TESTING) }, "\t"));
            f1s += trial.getF1(TESTING);
        }
        System.out.println("mean F1 = " + (f1s / (trials + 0d)));

        // ////////////////////////////////////////////////////////////////
        // train
        ClassifierTrainer trainer = new MaxEntTrainer();
        Classifier c = trainer.train(instanceList);

        String txt = "in the entorhinal cortex of the rat\n"
                + "II: phase relations between unit discharges and theta field potentials.\n"
                + "J. Comp. Neurol. 67, 502–509.\n"
                + "Alonso, A., and Klink, R. (1993).\n"
                + "Differential electroresponsiveness of\n"
                + "stellate and pyramidal-like cells of\n"
                + "medial entorhinal cortex layer II.\n"
                + "J. Neurophysiol. 70, 128–143.\n"
                + "Alonso, A., and Köhler, C. (1984).\n"
                + "A study of the reciprocal connections between the septum and the\n"
                + "entorhinal area using anterograde\n"
                + "and retrograde axonal transport\n"
                + "methods in the rat brain. J. Comp.\n"
                + "Neurol. 225, 327–343.\n"
                + "Alonso, A., and Llinás, R. (1989).\n"
                + "Subthreshold sodium-dependent\n"
                + "theta-like rhythmicity in stellate\n"
                + "cells of entorhinal cortex layer II.\n"
                + "Nature 342, 175–177.\n"
                + "Amaral, D. G., and Kurz, J. (1985).\n"
                + "An analysis of the origins of\n" + "";
        Classification classification = c.classify(c.getInstancePipe()
                .instanceFrom(new Instance(txt, null, null, null)));
        System.out.println("LABELL " + classification.getLabeling());
        c.print();

        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("target/classifier_"
                            + currentTimeMillis() + ".model"));
            oos.writeObject(c);
            oos.close();
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        // //////////////////////////////////////////////////////////////////
        // train test
        for (String goldLabel : new String[] { "I", "O" }) {
            ClassifierTrainer trainer2 = new MaxEntTrainer();
            Classifier c2 = trainer2.train(instanceList);

            FileIterator iteratorI = new FileIterator(new File[] { new File(
                    CORPUS, "../annots1/" + goldLabel + "/") },
                    new TxtFilter(), LAST_DIRECTORY);
            Iterator<Instance> instancesI = c2.getInstancePipe()
                    .newIteratorFrom(iteratorI);

            Histogram<String> h = new Histogram<String>();
            while (instancesI.hasNext()) {
                Instance inst = instancesI.next();
                Labeling labeling = c2.classify(inst).getLabeling();
                Label bestLabel = labeling.getBestLabel();
                h.add(bestLabel.toString());

                // if (!bestLabel.toString().equals(goldLabel)) {
                // LOG.debug(
                // "\n\n\nMISSCLASSIFIED as {} but gold:{} :: "
                // + inst.getSource(), bestLabel, goldLabel);
                // }
            }
            System.out.println("\nlabel " + goldLabel + "\n" + h);
        }
    }

    static List<Pipe> getPipes() {

        List<Pipe> pipes = newArrayList();
        pipes.add(new Target2Label());
        pipes.add(new MyInput2RegexTokens());

        // pipes.add(new PrintInputAndTarget());

        pipes.add(new TokenSequence2FeatureSequence());
        pipes.add(new FeatureSequence2FeatureVector());
        return pipes;
    }

    static final int TRAINING = 0, TESTING = 1;

    public static Trial testTrainSplit(InstanceList instances) {

        InstanceList[] instanceLists = instances.split(new Randoms(),
                new double[] { 0.9, 0.1, 0.0 });

        // LOG.debug("{} training instance, {} testing instances",
        // instanceLists[0].size(), instanceLists[1].size());

        @SuppressWarnings("rawtypes")
        ClassifierTrainer trainer = new MaxEntTrainer();
        Classifier classifier = trainer.train(instanceLists[TRAINING]);
        return new Trial(classifier, instanceLists[TESTING]);
    }

    static class TxtFilter implements FileFilter {
        public boolean accept(File file) {
            return file.toString().endsWith(".txt");
        }
    }
}
