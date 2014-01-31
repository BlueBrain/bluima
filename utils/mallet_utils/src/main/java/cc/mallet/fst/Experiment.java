package cc.mallet.fst;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Experiment {

    private static final DecimalFormat f = new DecimalFormat("0.####");

    private List<Experiment> experiments = new ArrayList<Experiment>();

    public void add(Experiment experiment) {
        experiments.add(experiment);
    }

    public double getPrecision() {
        double p = 0d;
        for (Experiment experiment : experiments) {
            p += experiment.getPrecision();
        }
        return p / experiments.size();
    }

    public double getRecall() {
        double r = 0d;
        for (Experiment experiment : experiments) {
            r += experiment.getRecall();
        }
        return r / experiments.size();
    }

    public double getF1() {
        double f = 0d;
        for (Experiment experiment : experiments) {
            f += experiment.getF1();
        }
        return f / experiments.size();
    }

    public String getReport() {
        return "for " + getName() + ": precision=" + f.format(getPrecision())
                + " recall=" + f.format(getRecall()) + " f1="
                + f.format(getF1());
    }

    public String getName() {
        return "experiment";
    }

    public static class Trail extends Experiment {

        public String getName() {
            return "trail";
        }
    }

    public static class Fold extends Experiment {
        double precision, recall, f1;

        public Fold(MyMultiSegmentationEvaluator eval) {
            precision = eval.getPrecision();
            recall = eval.getRecall();
            f1 = eval.getF1();
        }

        public double getPrecision() {
            return precision;
        }

        public void setPrecision(double precision) {
            this.precision = precision;
        }

        public double getRecall() {
            return recall;
        }

        public void setRecall(double recall) {
            this.recall = recall;
        }

        public double getF1() {
            return f1;
        }

        public void setF1(double f1) {
            this.f1 = f1;
        }

        public String getName() {
            return "fold";
        }
    }
}
