package ch.epfl.bbp.uima.validation;

import static java.lang.String.format;

public class TestResult {

    private double precision;
    private double recall;
    //private int sizeExpected;
    //private int sizeActual;

    public TestResult(int sizeExpected, int sizeActual, double precision,
            double recall) {
        // this.sizeExpected = sizeExpected;
        //  this.sizeActual = sizeActual;
        this.precision = precision;
        this.recall = recall;
    }

    public double getPrecision() {
        return precision;
    }

    public double getRecall() {
        return recall;
    }

    public double getF1() {
        return 2 * precision * recall / (precision + recall);
    }

    @Override
    public String toString() {
        return format(
                "TestResult:\n\tprecision:\t%.3f\n\trecall:\t%.3f\n\tF1:\t%.3f",
                precision, recall, getF1());
    }
}
