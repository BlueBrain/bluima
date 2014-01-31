/*
 * ApproximateRandomizationProcedure.java 1.0 01/06/2004
 *
 * Copyright 2002-2004 by  ITC-irst
 * via Sommarive 18 - Povo, 38050 Trento (I)
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of ITC-irst. ("Confidential Information").  You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with ITC-irst.
 */
package org.itc.irst.tcc.sre.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class ApproximateRandomizationProcedure {
    /**
     * Define a static logger variable so that it references the Logger instance
     * named <code>ApproximateRandomizationProcedure</code>.
     */
    static Logger logger = LoggerFactory
            .getLogger(ApproximateRandomizationProcedure.class.getName());

    //
    private int iterations;

    //
    private double confidence;

    //
    private DecimalFormat formatter = new DecimalFormat("###,###.####");

    /**
     * Creates a <code>ApproximateRandomizationProcedure</code> object.
     */
    public ApproximateRandomizationProcedure(File goldFile, File baselineFile,
            File preferredFile, int n, double c) throws IOException,
            IndexOutOfBoundsException {
        logger.debug("ApproximateRandomizationProcedure.ApproximateRandomizationProcedure: ");
        logger.debug("gold:" + goldFile);
        logger.debug("baseline:" + baselineFile);
        logger.debug("preferred:" + preferredFile);

        iterations = n;
        confidence = c;

        List gold = new Output(goldFile).getList();
        List baseline = new Output(baselineFile, goldFile).getList();
        List preferred = new Output(preferredFile, goldFile).getList();

        logger.debug("gold.size:" + gold.size());
        logger.debug("baseline.size:" + baseline.size());
        logger.debug("preferred.size:" + preferred.size());

        Evaluator[] baselineEval = evalAll(gold, baseline);
        Evaluator[] preferredEval = evalAll(gold, preferred);

        logger.debug("baseline.length:" + baselineEval.length);
        logger.debug("preferred.length:" + preferredEval.length);

        double t = test(baselineEval, preferredEval);

        if (t < confidence)
            logger.info("reject null hyphotesis (significant)");
        else
            logger.info("accept null hyphotesis (insignificant)");

    } // end constructor

    //
    private double test(Evaluator[] baselineEval, Evaluator[] preferredEval) {
        double bs = calculateScore(baselineEval);
        double ps = calculateScore(preferredEval);
        double d = Math.abs(ps - bs);
        double mean = 0;
        double variance = 0;
        double sum = 0;
        double ssum = 0;
        logger.info("original score bs, ps,d: " + formatter.format(bs * 100)
                + "%, " + formatter.format(ps * 100) + "%, "
                + formatter.format(d * 100) + "%");

        // p - p-value. In general, the lowest the p-value,
        // the less probable it is that that the null
        // hypothesis holds. That is, the two systems are
        // are significantly different.

        double p = 0;

        // c - number of times that the pseudostatistic is
        // greater or equal to the true statistic
        int c = 0;
        for (int i = 0; i < iterations; i++) {
            Evaluator[] baselineEvalCopy = new Evaluator[baselineEval.length];
            Evaluator[] preferredEvalCopy = new Evaluator[preferredEval.length];

            System.arraycopy(baselineEval, 0, baselineEvalCopy, 0,
                    baselineEval.length);
            System.arraycopy(preferredEval, 0, preferredEvalCopy, 0,
                    preferredEvalCopy.length);

            swap(baselineEvalCopy, preferredEvalCopy, new Random(i * 27));
            bs = calculateScore(baselineEvalCopy);
            ps = calculateScore(preferredEvalCopy);

            double di = Math.abs(ps - bs);
            sum += di;
            ssum += Math.pow(di, 2);
            // logger.debug("score at " + i + " bs, ps,d: " +
            // formatter.format(bs) + ", " + formatter.format(ps) + ", " +
            // formatter.format(di) + ", (" + formatter.format(d) + ")");

            if (di >= d)
                c++;

        } // end for i

        mean = sum / iterations;
        variance = (iterations * ssum - Math.pow(sum, 2)) / iterations
                * (iterations - 1);

        p = (double) (c + 1) / (iterations + 1);
        logger.info("mean " + mean + ", " + Math.sqrt(variance));
        logger.info(p + " = (" + c + " + 1) / (" + iterations + " +  1)");

        return p;
    } // end test

    //
    private double calculateScore(Evaluator[] m) {
        // logger.info("calculateScore");

        int obj = 0, ans = 0, tp = 0, fp = 0, fn = 0;

        for (int i = 0; i < m.length; i++) {
            // obj += m[i].getTotal();
            // ans += m[i].getAnswers();
            tp += m[i].getTP();
            fp += m[i].getFP();
            fn += m[i].getFN();
        } // end for i

        // precision
        double p = (double) tp / (tp + fp);

        // recall
        double r = (double) tp / (tp + fn);

        // f1
        double f1 = (2 * p * r) / (p + r);

        // logger.info(obj + " " + ans + " " + tp + " " + fp + " " + fn + " " +
        // r + " " + p + " " + f1);
        return f1;
    } // end calculateScore

    //
    private void swap(Evaluator[] y, Evaluator[] z, Random rdm) {
        // logger.info("swap");
        int count = 0;
        for (int i = 0; i < y.length; i++) {
            double p = rdm.nextDouble();
            if (p < 0.5) {
                Evaluator t = y[i];
                y[i] = z[i];
                z[i] = t;
                count++;
            }
        } // end for i

        // logger.info("swapped " + count + " out of " + y.length);
    } // end swap

    //
    private Evaluator[] evalAll(List gold, List other)
            throws IndexOutOfBoundsException {
        // logger.info("evalAll");
        Evaluator[] eval = new Evaluator[gold.size()];
        for (int i = 0; i < gold.size(); i++) {
            List x = (List) gold.get(i);
            List y = (List) other.get(i);
            eval[i] = new Evaluator(x, y);
            // logger.info(i + " " + eval[i]);
        } // end for i

        return eval;
    } // end evalAll

    //
    public static void main(String args[]) throws Exception {
        long begin, end;

        begin = System.currentTimeMillis();

        if (args.length < 5) {
            System.err
                    .println("java -mx512M org.itc.irst.tcc.sre.util.ApproximateRandomizationProcedure gold baseline preferred iterations confidence-level");
            System.exit(0);
        }

        String g = args[0];
        String b = args[1];
        String p = args[2];
        int n = Integer.parseInt(args[3]);
        double c = Double.parseDouble(args[4]);

        new ApproximateRandomizationProcedure(new File(g), new File(b),
                new File(p), n, c);

        //
        end = System.currentTimeMillis();
        logger.info("evaluation done in " + (end - begin) + " ms");

    } // end main
} // end class ApproximateRandomizationProcedure

//
class Output {
    /**
     * Define a static logger variable so that it references the Logger instance
     * named <code>Output</code>.
     */
    static Logger logger = LoggerFactory.getLogger(Output.class.getName());

    //
    private SortedMap map;

    //
    Output(File ans) throws IOException {
        map = new TreeMap();
        read(ans);
    } // end constructor

    //
    Output(File ans, File ref) throws IOException {
        map = new TreeMap();
        read(ans, ref);
    } // end constructor

    //
    private void read(File ans) throws IOException {
        LineNumberReader lr = new LineNumberReader(new FileReader(ans));
        String line = null;
        while ((line = lr.readLine()) != null) {
            String[] s = line.split("\t");
            String id = s[1].substring(0, s[1].indexOf('-'));
            put(new Integer(id), new Double(s[0]));
        } // end while

        lr.close();
    } // end read

    //
    private void read(File ans, File ref) throws IOException {
        LineNumberReader ar = new LineNumberReader(new FileReader(ans));
        LineNumberReader rr = new LineNumberReader(new FileReader(ref));

        String a = null, r = null;
        while (((a = ar.readLine()) != null) && ((r = rr.readLine()) != null)) {
            // logger.debug("a: " + a);
            // logger.debug("r: " + r);
            String[] s = r.split("\t");
            // logger.debug("s[1]: " + s[1]);
            String id = s[1].substring(0, s[1].indexOf('-'));
            // logger.debug("id: " + id);
            put(new Integer(id), new Double(a.trim()));
        } // end while

        ar.close();
        rr.close();
    } // end read

    //
    private void put(Integer sentID, Double pred) {
        List list = (List) map.get(sentID);
        if (list == null) {
            list = new ArrayList();
        }

        list.add(pred);
        map.put(sentID, list);
    } // end put

    //
    public List getList() {
        List result = new ArrayList();
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer id = (Integer) it.next();
            List list = (List) map.get(id);
            // logger.debug(id + " : " + list.size());
            result.add(list);
        }

        return result;
    } // end getList

} // end class Output
