/*
 * Copyright 2005 FBK-irst (http://www.fbk.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.itc.irst.tcc.sre.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class Evaluator
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>Evaluator</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(Evaluator.class.getName());

	//
	public static final int MAX_NUMBER_OF_CLASSES = 20;

	//
	private double[] tp = new double[MAX_NUMBER_OF_CLASSES];

	//
	private double[] fp = new double[MAX_NUMBER_OF_CLASSES];

	//
	private double[] tn = new double[MAX_NUMBER_OF_CLASSES];

	//
	private double[] fn = new double[MAX_NUMBER_OF_CLASSES];

	//
	private	double microTP = 0;

	//
	private	double microFP = 0;

	//
	private	double microFN = 0;

	//
	private	double microTN = 0;

	//
	private int total = 0;

	//
	private int correct = 0;

	//
	DecimalFormat decFormatter;

	/**
	 * Creates a <code>Evaluator</code> object.
	 */
	public Evaluator()
	{
		//logger.debug("Evaluator.Evaluator");
		decFormatter = new DecimalFormat("0.000");
	} // end constructor

	/**
		* Creates a <code>Evaluator</code> object.
	 */
	public Evaluator(int tp, int fp, int fn, int total)
	{
		this();
		logger.debug("Evaluator");

		this.tp[1] = tp;
		this.fp[1] = fp;
		this.fn[1] = fn;
		this.total = total;
		this.tn[1] = total - tp - fp - fn;
		microTP = tp;
		microFP = fp;
		microFN = fn;
		microTN = tn[1];
		this.correct = (int) (tp + tn[1]);
	} // end constructor

	/**
	 * Creates a <code>Evaluator</code> object.
	 */
	public Evaluator(File f) throws IOException
	{
		this();
		read(new BufferedReader(new FileReader(f)));
	} // end constructor

	/**
	 * Creates a <code>Evaluator</code> object.
	 */
	public Evaluator(String ref, String pred) throws IOException, IndexOutOfBoundsException
	{
		this(new File(ref), new File(pred));
	} // end constructor

	/**
	 * Creates a <code>Evaluator</code> object.
	 */
	public Evaluator(File refFile, File predFile) throws IOException, IndexOutOfBoundsException
	{
		this();

		//uncomment here to evaluate y={+1,1}
		//List ref = readRef(refFile);
		//uncomment here to evaluate y={0,1,2,...}
		List ref = read(refFile);
		List pred = read(predFile);

		if (ref.size() != pred.size())
			throw new IndexOutOfBoundsException(ref.size() + " != " + pred.size());

		eval2(ref, pred);
	} // end constructor

	/**
	 * Creates a <code>Evaluator</code> object.
	 */
	public Evaluator(List ref, List pred) throws IndexOutOfBoundsException
	{
		//logger.debug("Evaluator.Evaluator");
		decFormatter = new DecimalFormat("0.000");

		if (ref.size() != pred.size())
			throw new IndexOutOfBoundsException(ref.size() + " != " + pred.size());

		eval(ref, pred);
	} // end constructor

	//
	public int getTN() {return (int) microTN;}

	//
	public int getTN(int c) {return (int) tn[c];}

	//
	public int getTP() {return (int) microTP;}

	//
	public int getTP(int c) {return (int) tp[c];}

	//
	public int getFP() {return (int) microFP;}

	//
	public int getFP(int c) {return (int) fp[c];}

	//
	public int getFN() {return (int) microFN;}

	//
	public int getFN(int c) {return (int) fn[c];}

	//
	public double getPrecision() {return microTP / (microTP + microFP);}

	//
	public double getPrecision(int i) {return tp[i] / (tp[i] + fp[i]);}

	//
	public double getRecall() {return microTP / (microTP + microFN);}

	//
	public double getRecall(int i) {return tp[i] / (tp[i] + fn[i]);}

	//
	public double getF1()
	{
		double prec = getPrecision();
		double recall = getRecall();
		return (2 * prec * recall) / (prec + recall);
	} // end getF1()

	//
	public double getF1(int i)
	{
		double prec = getPrecision(i);
		double recall = getRecall(i);
		return (2 * prec * recall) / (prec + recall);
	} // end getF1

	//
	public int getTotal() {return total;}

	//
	public int getCorrect() {return correct;}

	// case y = {-1,1}
	protected List readRef(File f) throws IOException
	{
		List list = new ArrayList();
		LineNumberReader lr = new LineNumberReader(new FileReader(f));
		String line = null;

		while ((line = lr.readLine()) != null)
		{
			String[] s = line.split("\t");
			if (s[0].equals("0"))
				list.add(new Double("-1"));
			else if (s[0].equals("1"))
				list.add(new Double("1"));

		}

		return list;
	} // end readRef

	//
	protected List read(File f) throws IOException
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
	protected void eval(List ref, List pred)
	{
		double target, v;

		for (int i=0;i<ref.size();i++)
		{
			target = ((Double) ref.get(i)).doubleValue();
			v = ((Double) pred.get(i)).doubleValue();

			if(v == target)
				++correct;

			// case y = {0, 1, 2, ...}

			if (v == 0)
			{
				if (target == v)
					microTN++;
				else
					microFN++;
			}
			else
			{
				if (target == 0)
					microFP++;
				else if (target == v)
					microTP++;
				else
				{
					microFP++;
					microFN++;
				}
			}

		// case y = {-1, +1}
/*
		if (v == 1)
			{
				if (target == 1)
					microTP++;
				else
					microFP++;
			}
			else
			{
				if (target == -1)
					microTN++;
				else
					microFN++;
			}
*/
			++total;
		} // end for

	} // end eval

	//
	protected void eval2(List ref, List pred)
	{
		double y, v;
		double maxY = 0;
		for (int i=0;i<ref.size();i++)
		{
			// y
			y = ((Double) ref.get(i)).doubleValue();
			// prediction
			v = ((Double) pred.get(i)).doubleValue();

			if (y > maxY)
				maxY = y;

			if(v == y)
				++correct;

			// case y = {0, 1, 2, ...}
			// 0
			if (v == 0)
			{
				if (y == v)
				{
					microTN++;
				}
				else
				{
					microFN++;
					fn[(int) y]++;
				}

			}
			else // 1, 2, 3
			{
				if (y == 0)
				{
					microFP++;
					fp[(int) v]++;
				}
				else if (y == v)
				{
					microTP++;
					tp[(int) v]++;
				}
				else
				{
					microFP++;
					microFN++;
					fp[(int) v]++;
					fn[(int) y]++;
				}
			}

			++total;
		} // end for

	} // end eval2

	//
	public void add(Evaluator eval)
	{
		microTP += eval.getTP();
		microTN += eval.getTN();
		microFP += eval.getFP();
		microFN += eval.getFN();

		total += eval.getTotal();
		correct += eval.getCorrect();

		for (int i=0;i<MAX_NUMBER_OF_CLASSES;i++)
		{
			tp[i] += eval.getTP(i);
			tn[i] += eval.getTN(i);
			fp[i] += eval.getFP(i);
			fn[i] += eval.getFN(i);


		} // end for i
	} // end add

	//
	public Evaluator get(int i)
	{
		return new Evaluator(getTP(i), getFP(i), getFN(i), total);
	} // end get

	//
	public void read(Reader r) throws IOException
	{
		LineNumberReader lr = new LineNumberReader(r);
		String line = null;

		// first line
		if ((line = lr.readLine()) == null)
			return;

		while ((line = lr.readLine()) != null)
		{
			String[] s = line.split("\t");

			if (s[0].equals("micro"))
			{

				microTP = Integer.parseInt(s[1]);
				microFP = Integer.parseInt(s[2]);
				microFN = Integer.parseInt(s[3]);
				total = Integer.parseInt(s[4]);
				microTN = total - microTP - microFP - microFN;

			}
			else
			{
				int i = Integer.parseInt(s[0]);
				tp[i] = Integer.parseInt(s[1]);
				fp[i] = Integer.parseInt(s[2]);
				fn[i] = Integer.parseInt(s[3]);
				total = Integer.parseInt(s[4]);
				tn[i] = total - tp[i] - fp[i] - fn[i];
				correct += tp[i] + tn[i];
			}
		}
	} // end read

	//
	public void write(Writer w) throws IOException
	{

		w.write("c\ttp\tfp\tfn\ttotal\tprec\trecall\tF1\n");

		int count = 0;
		for (int i=1;i<MAX_NUMBER_OF_CLASSES;i++)
		{
			if ((tp[i] != 0) || (fp[i] != 0) || (fn[i] != 0))
			{
				w.write(i + "\t" + (int) tp[i] + "\t" + (int) fp[i] + "\t" + (int) fn[i] + "\t" + (int) total + "\t" + decFormatter.format(getPrecision(i)) + "\t" + decFormatter.format(getRecall(i)) + "\t" + decFormatter.format(getF1(i)) + "\n");
				count++;
			}
		} // end for i
		if (count > 1)
		{
			//sb.append("\n");
			w.write("micro\t" + getTP() + "\t" + getFP() + "\t" + getFN() + "\t" + getTotal() + "\t" + decFormatter.format(getPrecision()) + "\t" + decFormatter.format(getRecall()) + "\t" + decFormatter.format(getF1()) + "\n");
		}

		w.flush();
	} // end write

	//
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("c\ttp\tfp\tfn\ttotal\tprec\trecall\tF1\n");

		int count = 0;
		for (int i=1;i<MAX_NUMBER_OF_CLASSES;i++)
		{
			if ((tp[i] != 0) || (fp[i] != 0) || (fn[i] != 0))
			{
				sb.append(i + "\t" + (int) tp[i] + "\t" + (int) fp[i] + "\t" + (int) fn[i] + "\t" + (int) total + "\t" + decFormatter.format(getPrecision(i)) + "\t" + decFormatter.format(getRecall(i)) + "\t" + decFormatter.format(getF1(i)) + "\n");
				count++;
			}
		} // end for i
		if (count > 1)
		{
			//sb.append("\n");
			sb.append("micro\t" + getTP() + "\t" + getFP() + "\t" + getFN() + "\t" + getTotal() + "\t" + decFormatter.format(getPrecision()) + "\t" + decFormatter.format(getRecall()) + "\t" + decFormatter.format(getF1()) + "\n");
		}

		return sb.toString();
	} // end toString

//	//
//	public static void main(String args[]) throws Exception
//	{
//		String logConfig = System.getProperty("log-config");
//		if (logConfig == null)
//			logConfig = "log-config.txt";
//
//		PropertyConfigurator.configure(logConfig);
//
//		if (args.length != 2)
//		{
//			System.err.println("java -mx512M org.itc.irst.tcc.sre.util.Evaluator reference-file answer-file");
//			System.exit(0);
//		}
//
//		File ref = new File(args[0]);
//		File ans = new File(args[1]);
//		Evaluator eval = new Evaluator(ref, ans);
//
//		//System.out.println("microTP\tfp\tfn\ttotal\tprec\trecall\tF1\tacc");
//		System.out.println(eval);
//
//	} // end main
} // end class Evaluator
