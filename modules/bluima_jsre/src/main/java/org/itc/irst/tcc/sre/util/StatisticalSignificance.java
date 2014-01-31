/*
 * StatisticalSignificance.java 1.0 01/06/2004
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
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StatisticalSignificance {

    static Logger logger = LoggerFactory
            .getLogger(StatisticalSignificance.class.getName());

    private int iterations;
    private double confidence;
    private DecimalFormat formatter = new DecimalFormat("###,###.####");
    private String stat;

    public StatisticalSignificance(File goldFile, File baselineFile,
            File preferredFile, int n, double c, String stat)
            throws IOException, IndexOutOfBoundsException {
        this.stat = stat;
        logger.debug("StatisticalSignificance.StatisticalSignificance: ");
        logger.debug("gold:" + goldFile);
        logger.debug("baseline:" + baselineFile);
        logger.debug("preferred:" + preferredFile);

        iterations = n;
        confidence = c;

        List gold = read(goldFile);
        List baseline = read(baselineFile);
        List preferred = read(preferredFile);

        logger.info("gold.size:" + gold.size());
        logger.debug("baseline.size:" + baseline.size());
        logger.debug("preferred.size:" + preferred.size());

        double t = test(baseline, preferred, gold);

        if (t < confidence)
            logger.info(t + " < " + confidence
                    + " reject null hyphotesis (significant)");
        else
            logger.info(t + " >= " + confidence
                    + " accept null hyphotesis (insignificant)");

    }

    private List read(File f) throws IOException {
        List list = new ArrayList();
        LineNumberReader lr = new LineNumberReader(new FileReader(f));
        String line = null;

        while ((line = lr.readLine()) != null) {
            String[] s = line.split("\t");
            // logger.debug((i++) + " " + s[0]);
            list.add(new Double(s[0]));
        }
        lr.close();
        return list;
    }

    private List copy(List in) {
        List out = new ArrayList();

        for (int i = 0; i < in.size(); i++)
            out.add(in.get(i));

        return out;
    }

    private double test(List baseline, List preferred, List gold) {
        double bs = 0, ps = 0;
        if (stat.toUpperCase().equals("F1")) {
            bs = new Evaluator(gold, baseline).getF1();
            ps = new Evaluator(gold, preferred).getF1();
        } else if (stat.toUpperCase().equals("P")) {
            bs = new Evaluator(gold, baseline).getPrecision();
            ps = new Evaluator(gold, preferred).getPrecision();
        } else if (stat.toUpperCase().equals("R")) {
            bs = new Evaluator(gold, baseline).getRecall();
            ps = new Evaluator(gold, preferred).getRecall();
        }
        double d = Math.abs(ps - bs);
        double mean = 0;
        double variance = 0;
        double sum = 0;
        double ssum = 0;
        logger.info(stat + ": original score bs, ps,d: "
                + formatter.format(bs * 100) + "%, "
                + formatter.format(ps * 100) + "%, "
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
            List baselineCopy = copy(baseline);
            List preferredCopy = copy(preferred);

            swap(baselineCopy, preferredCopy, new Random(i * 27));

            if (stat.toUpperCase().equals("F1")) {
                bs = new Evaluator(gold, baselineCopy).getF1();
                ps = new Evaluator(gold, preferredCopy).getF1();
            } else if (stat.toUpperCase().equals("P")) {
                bs = new Evaluator(gold, baselineCopy).getPrecision();
                ps = new Evaluator(gold, preferredCopy).getPrecision();
            } else if (stat.toUpperCase().equals("R")) {
                bs = new Evaluator(gold, baselineCopy).getRecall();
                ps = new Evaluator(gold, preferredCopy).getRecall();
            }

            double di = Math.abs(ps - bs);
            sum += di;
            ssum += Math.pow(di, 2);
            if (di >= d) {
                c++;

                // logger.info("score at " + i + " c, bs, ps,d: " + c + ", " +
                // formatter.format(bs * 100) + "%, " + formatter.format(ps *
                // 100) + "%, " + formatter.format(di * 100) + "%, (" +
                // formatter.format(d * 100) + "%)");
            }
        } // end for i

        mean = sum / iterations;
        variance = (iterations * ssum - Math.pow(sum, 2)) / iterations
                * (iterations - 1);

        p = (double) (c + 1) / (iterations + 1);
        logger.info("mean " + formatter.format(mean) + ", "
                + formatter.format(Math.sqrt(variance)));
        logger.info(p + " = (" + c + " + 1) / (" + iterations + " +  1)");

        return p;
    }

    private void swap(List y, List z, Random rdm) {
        for (int i = 0; i < y.size(); i++) {
            double p = rdm.nextDouble();
            if (p < 0.5) {
                Object t = y.get(i);
                y.set(i, z.get(i));
                z.set(i, t);
            }
        }
    }
}
