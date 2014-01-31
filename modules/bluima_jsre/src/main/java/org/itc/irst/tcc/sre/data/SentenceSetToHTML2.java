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
package org.itc.irst.tcc.sre.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads an example set for relation extraction.
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class SentenceSetToHTML2
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>SentenceSetToHTML2</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(SentenceSetToHTML2.class.getName());

	//
	private ExampleSet inputSet;

	//
	public SentenceSetToHTML2(File in, File out, File ans) throws Exception
	{

		//
		inputSet = new SentenceSetCopy();
		inputSet.read(new BufferedReader(new FileReader(in)));


		String[] a = readAnswer(ans, inputSet.size());

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(out)));
		toHtml(pw, a);
		pw.flush();
		pw.close();
	} // end constructor

	//
	public SentenceSetToHTML2(File in, File out) throws Exception
	{

		//
		inputSet = new SentenceSetCopy();
		inputSet.read(new BufferedReader(new FileReader(in)));


		String[] a = new String[inputSet.size()];
		for (int i=0;i<a.length;i++)
			a[i] = "0";

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(out)));
		toHtml(pw, a);
		pw.flush();
		pw.close();
	} // end constructor

	//
	private void toHtml(PrintWriter pw, String[] a)
	{
		DecimalFormat myFormatter = new DecimalFormat("0.000");

		double tp = 0, fp = 0, fn = 0, tn = 0, total = 0, baseline = 0;

		pw.print("<html>");
		pw.print("<head>");
		pw.print("<style type=\"text/css\">");
		pw.print("b.target{color:red;}");
		pw.print("b.agent{color:blue;}");
		pw.print("em.loc{color:red}");
		pw.print("em.per{color:green}");
		pw.print("em.org{color:blue}");
		pw.print(".dark{background:#C0C0C0}");
		pw.print(".light{background:#E0E0E0}");
		pw.print(".header{background:#A0A0A0}");
		pw.print("<style>");
		pw.print("</style>");
		pw.print("</head>");
		pw.print("<body>");
		pw.print("<table border=\"1\">");

		pw.print("<tr class=\"header\">");
		pw.print("<th>i</th><th>id</th><th>y</th><th>a</th><th>c</th><th>x</th>");
		pw.print("</tr>");
		String oldID = "";
		boolean idClass = true;
		for (int i=0;i<inputSet.size();i++)
		{
			total++;
			//logger.info("i=" + i + ", " + ((String) inputSet.id(i)));
			if (!((String) inputSet.id(i)).startsWith(oldID))
				idClass = !idClass;

			if (idClass)
				pw.print("<tr class=\"light\">");
			else
				pw.print("<tr class=\"dark\">");

			pw.print("<td>" + (i + 1) + "</td>");
			pw.print("<td>" + inputSet.id(i) + "</td>");
			pw.print("<td>" + inputSet.y(i) + "</td>");
			pw.print("<td>" + a[i] + "</td>");

			double v = Double.parseDouble(a[i]);
			//double target = ((Double) inputSet.y(i)).doubleValue();
			double target = Double.parseDouble((String) inputSet.y(i));

			if (target == 1.0)
			{
				baseline++;
			}
			/*
			if (target == v)
			{
				pw.print("<td>V</td>");
				//total++;

			}
			else
			{
				pw.print("<td>X</td>");
			}
			*/
		if (v == 0)
		{
			if (target == 1)
			{
				fn++;
				pw.print("<td>fn</td>");
			}
			else if (target == 2)
			{
				fn++;
				pw.print("<td>fn</td>");
			}
			else
			{
				tn++;
				pw.print("<td>tn</td>");
			}

		}
		else if (v == 1)
		{
			if (target == 0)
			{
				fp++;
				pw.print("<td>fp</td>");
			}
			else if (target == 1)
			{
				tp++;
				pw.print("<td>tp</td>");
			}
			else if (target == 2)
			{
				fp++;
				fn++;
				pw.print("<td>fpn</td>");
			}
		}
		else if (v == 2)
		{
			if (target == 0)
			{
				fp++;
				pw.print("<td>fp</td>");
			}
			else if (target == 1)
			{
				fp++;
				fn++;
				pw.print("<td>fpn</td>");
			}
			else if (target == 2)
			{
				tp++;
				pw.print("<td>tp</td>");
			}

		}



			pw.print("<td><p>");
			Sentence sent = (Sentence) inputSet.x(i);
			for (int j=0;j<sent.length();j++)
			{
				Word f = (Word) sent.wordAt(j);

				if (f.getRole().equals(Word.AGENT_LABEL))
				{
					pw.print("<sub>[" + f.getOffset() + "]</sub>");
					pw.print("<b class=\"agent\">");
					pw.print(f.getForm(true) + "<sub>[" + f.getType() + "]</sub></em>");
					pw.print("</b>");
									}

				else if (f.getRole().equals(Word.TARGET_LABEL))
				{
					pw.print("<sub>[" + f.getOffset() + "]</sub>");
					pw.print("<b class=\"target\">");
					pw.print(f.getForm(true) + "<sub>[" + f.getType() + "]</sub></em>");
					pw.print("</b>");
				}
				else
					pw.print(f.getForm(true));

			/*
				if (f.getType().equals("PER"))
					pw.print("<em class=\"per\">" + f.getForm(true) + "<sub>[" + f.getType() + "]</sub></em>");
				else if (f.getType().equals("LOC"))
					pw.print("<em class=\"loc\">" + f.getForm(true) + "<sub>[" + f.getType() + "]</sub></em>");
				else if (f.getType().equals("ORG"))
					pw.print("<em class=\"org\">" + f.getForm(true) + "<sub>[" + f.getType() + "]</sub></em>");
				else
					pw.print(f.getForm(true));*/
			 /*
				if (!f.getType().equals("O"))
					pw.print("<em class=\"per\">" + f.getForm(true) + "<sub>[" + f.getType() + "]</sub></em>");
				else
					pw.print(f.getForm(true));
					*/


					/*
				if (f.getRole().equals(Word.AGENT_LABEL))
					pw.print("</b>");
				else if (f.getRole().equals(Word.TARGET_LABEL))
					pw.print("</b>");
*/
				pw.print(" ");
			} // end for j


			int k = ((String) inputSet.id(i)).indexOf('-');
			oldID = ((String) inputSet.id(i)).substring(0, k);

			pw.print("</p></td>");
			pw.print("</tr>");
			pw.flush();
		} // end for i

		double p = tp / (tp + fp);
		double r = tp / (tp + fn);
		double f1 = (2 * p * r) / (p + r);
		double acc = (tp + tn) / (tp + tn + fp + fn);
		baseline /= (tp + tn + fp + fn);
		pw.print("</table>");

		pw.print("<table border=\"1\">");

		pw.print("<tr>");
		//objects answers tp      fp      fn      recall  precision       f-score
		pw.print("<th>tp</th><th>fp</th><th>fn</th><th>total</th><th>recall</th><th>precision</th><th>f-score</th><th>acc</th><th>baseline</th>");
		pw.print("</tr>");
		pw.print("<tr class=\"header\">");
		//objects answers tp      fp      fn      recall  precision       f-score
		pw.print("<td>" + (int) tp + "</td><td>" + (int) fp + "</td><td>" + (int) fn + "</td><td>" + total + "</td><td>" + myFormatter.format(r) + "</td><td>" + myFormatter.format(p) + "</td><td>" + myFormatter.format(f1) + "</td><td>" + myFormatter.format(acc) + "</td><td>" + myFormatter.format(baseline) + "</td>");
		pw.print("</tr>");
		pw.print("</table>");

		pw.print("</body>");
		pw.print("</html>");
	} // end toHtml

	//
	private String[] readAnswer(File ans, int size) throws Exception
	{
		LineNumberReader lr = new LineNumberReader(new FileReader(ans));
		int i = 0;
		String line = null;
		String[] a = new String[size];
		while ((line = lr.readLine()) != null)
			a[i++] = line;

		return a;
	} // end readAnswer

//	//
//	public static void main(String args[]) throws Exception
//	{
//		String logConfig = System.getProperty("log-config");
//		if (logConfig == null)
//			logConfig = "log-config.txt";
//
//		PropertyConfigurator.configure(logConfig);
//
//		if (args.length < 1)
//		{
//			System.err.println("java -mx512M org.itc.irst.tcc.sre.data.SentenceSetToHTML2 test-file [ans-file]");
//			System.exit(-1);
//		}
//
//		File in = new File(args[0]);
//		File out = new File(args[0] + ".html");
//
//		if (args.length == 1)
//		{
//			new SentenceSetToHTML2(in, out);
//		}
//		else if (args.length == 2)
//		{
//			File ans = new File(args[1]);
//			new SentenceSetToHTML2(in, out, ans);
//		}
//
//
//	} // end main
} // end class SentenceSetToHTML2
