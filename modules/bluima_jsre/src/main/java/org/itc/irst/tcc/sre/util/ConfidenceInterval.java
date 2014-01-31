/*
 * ConfidenceInterval.java 1.0 01/06/2004
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
 * @author		Claudio Giuliano
 * @version 	%I%, %G%
 * @since			1.0
 */
public class ConfidenceInterval
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>ConfidenceInterval</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(ConfidenceInterval.class.getName());

	//
	private int iterations;

	//
	private double confidence;

	//
	private DecimalFormat formatter = new DecimalFormat("00.0");

	//
	private DecimalFormat formatter1 = new DecimalFormat("#0.0");

	//
	private String stat;

	/**
	 * Creates a <code>ConfidenceInterval</code> object.
	 */
	public ConfidenceInterval(File goldFile, File preferredFile, int n, double c, String stat) throws IOException, IndexOutOfBoundsException
	{
		this.stat = stat;
		logger.debug("ConfidenceInterval.ConfidenceInterval: ");
		logger.debug("gold:" + goldFile);
		logger.debug("preferred:" + preferredFile);

		iterations = n;
		confidence = c;

		List gold = read(goldFile);
		List preferred = read(preferredFile);

		logger.info("gold.size:" + gold.size());
		logger.info("preferred.size:" + preferred.size());

		List eval = evalList(gold, preferred);
		double[] s = test(eval);

		//for (int i=0;i<s.length;i++)
		//	logger.info(i + " : " + s[i]);
		new Quicksort1().sort(s);

		//logger.info("sorting");
		//for (int i=0;i<s.length;i++)
		//	logger.info(i + " : " + s[i]);


		Eval prefEval = new Eval();
		prefEval.addAll(eval);
		//prefEval.fn += 50;
		logger.info("pref P, R, F1: " + prefEval.precision() + ", " + prefEval.recall() + ", " + prefEval.f1() + "\n");

		double ps = 0;
			if (stat.toUpperCase().equals("F1"))
				ps = prefEval.f1();
			else if (stat.toUpperCase().equals("R"))
				ps = prefEval.recall();
			else if (stat.toUpperCase().equals("P"))
				ps = prefEval.precision();


		int l = (int) (c / 2 * n);
		int u = (int) ((1 - (c / 2)) * n);
		logger.info(l + ", " + u);
		logger.info(formatter.format(s[l] * 100) + " <= " + formatter.format(ps * 100) + " <= " + formatter.format(s[u] * 100));
		logger.info(formatter.format(ps * 100) + " +" + formatter1.format((s[u] - ps)  * 100) + " -" + formatter1.format((ps - s[l])  * 100));
		logger.info("(" + formatter.format(s[l] * 100) + ", " + formatter.format(s[u] * 100) + ")");

		double max = Math.max(Math.abs(ps - s[l]), Math.abs(ps - s[u]));
		logger.info("$" + formatter.format(ps * 100) + "\\pm" + formatter1.format(max * 100) + "$");
		/*
		double l = lowerBound(s);
		double u = upperBound(s);

		//logger.info(l + ", " + u);
		logger.info(formatter.format(l * 100) + " <= " + formatter.format(ps * 100) + " <= " + formatter.format(u * 100));

		logger.info(formatter.format(ps * 100) + " +" + formatter1.format((u - ps)  * 100) + " -" + formatter1.format((ps - l)  * 100));
*/
	} // end constructor

	//
	private List read(File f) throws IOException
	{
		List list = new ArrayList();
		LineNumberReader lr = new LineNumberReader(new FileReader(f));
		String line = null;

		while ((line = lr.readLine()) != null)
		{
			String[] s = line.split("\t");
			//logger.debug((i++) + " " + s[0]);
			list.add(new Double(s[0]));
		}

		return list;
	} // end read

	//
	private double[] test(List eval)
	{
		double[] s = new double[iterations];

		for (int i=0;i<iterations;i++)
		{
			List sample = sampling(eval, i);
			Eval sampleEval = new Eval();
			sampleEval.addAll(sample);
			//sampleEval.fn += 50;
			//logger.info("sample " + i + " P, R, F1: " + sampleEval.precision() + ", " + sampleEval.recall() + ", " + sampleEval.f1());
			if (stat.toUpperCase().equals("F1"))
				s[i] = sampleEval.f1();
			else if (stat.toUpperCase().equals("R"))
				s[i] = sampleEval.recall();
			else if (stat.toUpperCase().equals("P"))
				s[i] = sampleEval.precision();

		} // end for i


		return s;
	} // end test

	//
	private List sampling(List eval, int iteration)
	{
		int size = eval.size();
		int[] stat = new int[size];
		List sample = new ArrayList();
		Random rdm = new Random(iteration);
		for (int i=0;i<size;i++)
		{
			int r = rdm.nextInt(size);

			stat[r]++;
			//logger.info(i + " sampled " + r);
			sample.add(eval.get(r));
		} // end for i

		//for (int i=0;i<size;i++)
		//	logger.info(i + " " + stat[i]);

		return sample;
	} // end sampling

	//
	private class Eval
	{
		//
		double tn, fp, fn, tp;

		Eval()
		{
			tn = 0;
			fp = 0;
			fn = 0;
			tp = 0;
		} // end constructor

		void addAll(List list)
		{
			for (int i=0;i<list.size();i++)
			{
				Eval e = (Eval) list.get(i);
				//logger.info("adding " + i + " "+ e);
				tp += e.tp;
				fp += e.fp;
				fn += e.fn;
				tn += e.tn;
			}

			//logger.info(toString());
		} // sum

		//
		public double precision() {return tp / (tp + fp);}

		//
		public double recall() {return tp / (tp + fn);}

		//
		public double f1() {return (2 * precision() * recall()) / (precision() + recall());}

		//
		public String toString()
		{
			return (int) tp + " " + (int) fp + " " + (int) fn + " " + (int) tn + " " + precision() + " " + recall() + " " + f1();
			//return (int) tp + " " + (int) fp + " " + (int) fn +  " " + (int) tn;
		}
	} // end Eval

	//
	private Eval evalOne(double target, double v)
	{
		Eval eval = new Eval();
		if (v == 0)
		{
			if (target == v)
				eval.tn++;
			else
				eval.fn++;
		}
		else // v > 0
		{
			if (target == 0)
				eval.fp++;
			else if (target == v)
				eval.tp++;
			else
			{
				eval.fp++;
				eval.fn++;
			}
		}

		return eval;
	} // end getEval

	//
	private List evalList(List gold, List pref)
	{
		List list = new ArrayList();
		Eval total = new Eval();
		for (int i=0;i<gold.size();i++)
		{
			double target = ((Double) gold.get(i)).doubleValue();
			double v = ((Double) pref.get(i)).doubleValue();
			//Eval e = evalOne(target, v);
			//total.sum(e);
			Eval e = evalOne(target, v);
			//logger.info(i + " "+ e);
			list.add(e);
		}

		return list;
	} // end evalList

	//
//	public static void main(String args[]) throws Exception
//	{
//		long begin, end;
//
//		begin = System.currentTimeMillis();
//
//		
//
//		if (args.length != 5)
//		{
//			System.err.println("java -mx512M org.itc.irst.tcc.sre.util.ConfidenceInterval gold preferred iterations confidence-level stat");
//			System.exit(0);
//		}
//
//		String g = args[0];
//		String p = args[1];
//		int n = Integer.parseInt(args[2]);
//		double c = Double.parseDouble(args[3]);
//		String s = args[4];
//		new ConfidenceInterval(new File(g), new File(p), n, c, s);
//
//		//
//		end = System.currentTimeMillis();
//		logger.info("evaluation done in " + (end - begin) + " ms");
//
//	} // end main
} // end class ConfidenceInterval

class Quicksort1
{
	public static final Random RND = new Random();

	private void swap(double[] array, int i, int j)
	{
		double tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}

	private int partition(double[] array, int begin, int end)
	{
		int index = begin + RND.nextInt(end - begin + 1);
		double pivot = array[index];
		swap(array, index, end);
		for (int i = index = begin; i < end; ++ i)
		{
			//if (cmp.compare(array[i], pivot) <= 0) {
			if (array[i] <= pivot)
			{
				swap(array, index++, i);
			}
		}
		swap(array, index, end);
		return (index);
	}
	private void qsort(double[] array, int begin, int end)
	{
		if (end > begin) {
			int index = partition(array, begin, end);
			qsort(array, begin, index - 1);
			qsort(array, index + 1,  end);
		}
	}

	public void sort(double[] array)
	{
		qsort(array, 0, array.length - 1);
	}
} // end class Quicksort1
