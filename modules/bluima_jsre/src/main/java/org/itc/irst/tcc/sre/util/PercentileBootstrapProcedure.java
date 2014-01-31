/*
 * PercentileBootstrapProcedure.java 1.0 01/06/2004
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
public class PercentileBootstrapProcedure {
    /**
     * Define a static logger variable so that it references the Logger instance
     * named <code>PercentileBootstrapProcedure</code>.
     */
    static Logger logger = LoggerFactory
            .getLogger(PercentileBootstrapProcedure.class.getName());

    //
    private int iterations;

    //
    private double confidence;

    //
    private DecimalFormat formatter = new DecimalFormat("00.0");

    //
    private DecimalFormat formatter1 = new DecimalFormat("#0.0");

    /**
     * Creates a <code>PercentileBootstrapProcedure</code> object.
     */
    public PercentileBootstrapProcedure(File goldFile, File preferredFile,
            int n, double c) throws IOException, IndexOutOfBoundsException {
        logger.debug("PercentileBootstrapProcedure.PercentileBootstrapProcedure: ");
        logger.debug("gold:" + goldFile);
        logger.debug("preferred:" + preferredFile);
        confidence = c;
        iterations = n;

        List gold = new Output1(goldFile).getList();
        List preferred = new Output1(preferredFile, goldFile).getList();

        logger.debug("gold.size:" + gold.size());
        logger.debug("preferred.size:" + preferred.size());

        Evaluator[] preferredEval = evalAll(gold, preferred);

        double ps = calculateScore(preferredEval);
        logger.info("original score, size : " + formatter.format(ps * 100)
                + ", " + preferredEval.length);

        // logger.debug("preferred.length:" + preferredEval.length);

        double[] s = test(preferredEval);

        // for (int i=0;i<s.length;i++)
        // logger.info(i + " : " + s[i]);
        new Quicksort().sort(s);

        // logger.info("sorting");
        // for (int i=0;i<s.length;i++)
        // logger.info(i + " : " + s[i]);

        double l = lowerBound(s);
        double u = upperBound(s);

        // logger.info(l + ", " + u);
        logger.info(formatter.format(l * 100) + " <= "
                + formatter.format(ps * 100) + " <= "
                + formatter.format(u * 100));

        logger.info(formatter.format(ps * 100) + " +"
                + formatter1.format((u - ps) * 100) + " -"
                + formatter1.format((ps - l) * 100));
    } // end constructor

    //
    private double lowerBound(double[] s) {
        double l = (confidence / 2) * iterations;
        // logger.info("lowerBound.l= " + l);
        int i = (int) l;
        // logger.info("lowerBound.i= " + i);
        double f = l - i;
        // logger.info("l= " + l);

        if (f == 0)
            return s[i];

        return s[i + 1];
    } // end lowerBound

    //
    private double upperBound(double[] s) {
        double l = (1 - (confidence / 2)) * iterations;
        // logger.info("upperBound.l= " + l);
        int i = (int) l;
        // logger.info("upperBound.i= " + i);
        double f = l - i;

        if ((i + 1) >= s.length)
            return s[s.length - 1];

        if (f == 0)
            return s[i];

        return s[i + 1];
    } // end upperBound

    //
    private double[] test(Evaluator[] preferredEval) {
        double ps = calculateScore(preferredEval);
        double[] s = new double[iterations];
        // int n = preferredEval.length;
        // double mean = 0;
        // double variance = 0;
        // double sum = 0;
        // double ssum = 0;

        // double p = 0;

        for (int i = 0; i < iterations; i++) {
            Evaluator[] sampledEval = sampling(preferredEval, i);
            s[i] = calculateScore(sampledEval);
            // logger.debug("score at " + i + " bs, ps,d: " +
            // formatter.format(bs) + ", " + formatter.format(ps) + ", " +
            // formatter.format(di) + ", (" + formatter.format(d) + ")");

        } // end for i

        // mean = sum / iterations;
        // variance = (iterations * ssum - Math.pow(sum, 2)) / iterations *
        // (iterations - 1);

        // p = (double) (c + 1) / (iterations + 1);
        // logger.info("mean " + mean + ", " + Math.sqrt(variance));
        // logger.info(p + " = (" + c + " + 1) / (" + iterations + " +  1)");

        return s;
    } // end test

    //
    private Evaluator[] sampling(Evaluator[] preferredEval, int iteration) {
        Random rdm = new Random(iteration);
        Evaluator[] sampledEval = new Evaluator[preferredEval.length];
        // int iterations = 20000;
        // Evaluator[] sampledEval = new Evaluator[iterations];
        for (int i = 0; i < preferredEval.length; i++)
        // for (int i=0;i<iterations;i++)
        {
            int r = rdm.nextInt(preferredEval.length);
            sampledEval[i] = preferredEval[r];
        } // end for i

        return sampledEval;
    } // end sampling
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
        // return p;
    } // end calculateScore

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

        if (args.length != 4) {
            System.err
                    .println("java -mx512M org.itc.irst.tcc.sre.util.PercentileBootstrapProcedure gold preferred iterations confidence-interval");
            System.exit(0);
        }

        String g = args[0];
        String p = args[1];
        int n = Integer.parseInt(args[2]);
        double c = Double.parseDouble(args[3]);
        new PercentileBootstrapProcedure(new File(g), new File(p), n, c);

        //
        // end = System.currentTimeMillis();
        // logger.info("evaluation done in " + (end - begin) + " ms");

    } // end main
} // end class PercentileBootstrapProcedure

//
class Output1 {
    /**
     * Define a static logger variable so that it references the Logger instance
     * named <code>Output1</code>.
     */
    static Logger logger = LoggerFactory.getLogger(Output1.class.getName());

    //
    private SortedMap map;

    //
    Output1(File ans) throws IOException {
        map = new TreeMap();
        read(ans);
    } // end constructor

    // pref gold
    Output1(File ans, File ref) throws IOException {
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

} // end class Output1

class Quicksort {
    public static final Random RND = new Random();

    private void swap(double[] array, int i, int j) {
        double tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    private int partition(double[] array, int begin, int end) {
        int index = begin + RND.nextInt(end - begin + 1);
        double pivot = array[index];
        swap(array, index, end);
        for (int i = index = begin; i < end; ++i) {
            // if (cmp.compare(array[i], pivot) <= 0) {
            if (array[i] <= pivot) {
                swap(array, index++, i);
            }
        }
        swap(array, index, end);
        return (index);
    }

    private void qsort(double[] array, int begin, int end) {
        if (end > begin) {
            int index = partition(array, begin, end);
            qsort(array, begin, index - 1);
            qsort(array, index + 1, end);
        }
    }

    public void sort(double[] array) {
        qsort(array, 0, array.length - 1);
    }
} // end class Quicksort
