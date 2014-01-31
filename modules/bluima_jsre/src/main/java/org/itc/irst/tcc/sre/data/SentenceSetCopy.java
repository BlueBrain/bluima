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
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.itc.irst.tcc.sre.data.context.AgentContext;
import org.itc.irst.tcc.sre.data.context.BetweenAfterContext;
import org.itc.irst.tcc.sre.data.context.BetweenContext;
import org.itc.irst.tcc.sre.data.context.ForeBetweenContext;
import org.itc.irst.tcc.sre.data.context.SecondTargetContext;
import org.itc.irst.tcc.sre.data.context.TargetContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class SentenceSetCopy extends ExampleSet
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>SentenceSetCopy</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(SentenceSetCopy.class.getName());

	//
	public void write(Writer out) throws IOException
	{
		PrintWriter pw = new PrintWriter(out);

		for (int i=0;i<size();i++)
			pw.print(y(i) + "\t" + id(i) + "\t" + x(i) + "\n");

		pw.flush();
		pw.close();

	} // end write

	//
	public ExampleSet subSet(int fromIndex, int toIndex)
	{
		ExampleSet sub = new SentenceSetCopy();
		for (int i=fromIndex;i<toIndex;i++)
		{
			sub.add(x(i), y(i), id(i));
		} // end for i
		return sub;
	} // end subSet

	//
	public void read(Reader in) throws IOException
	{
		//logger.info("loading " + f + "...");

		// clear the example set
		clear();

		LineNumberReader lr = new LineNumberReader(in);
		String line = null;
		Object x = null, y = null, id = null;

		while ((line = lr.readLine()) != null)
		{
			//logger.info(i + ": " + line);
			StringTokenizer st = new StringTokenizer(line, "\t");

			// class label
			if (st.hasMoreTokens())
				y = st.nextToken();

			// ID
			if (st.hasMoreTokens())
				id = st.nextToken();
	/*
			List list = null;
			// example
			if (st.hasMoreTokens())
				list = tokenize(st.nextToken());

			// new code
			Word[] sent = new Word[list.size()];
			for (int j=0;j<list.size();j++)
				sent[j] = (Word) list.get(j);
		*/
			Sentence sent = new Sentence(st.nextToken());
			add(sent, y, id);
			//add(new Sentence(sent), y, id);
		} // end while

	} // end read

	//
	private List tokenize(String t)
	{
		//logger.info("tokenize:  '" + t + "'");
		List list = new ArrayList();
		StringTokenizer st = new StringTokenizer(t, " ");
		String token;

		while (st.hasMoreTokens())
		{
			token = st.nextToken();
			//logger.info("'" + token + "'");

			try
			{
				Word feat = new Word(token);
				list.add(feat);
			}
			catch (IllegalArgumentException e)
			{
				logger.error(e + " : " + t + "(" + token + ")");
			}

		}

		return list;
	} // end tokenize

	/**
	 * Returns a shallow copy of this ExampleSet instance:
	 * the instances, ids and class labels themselves are
	 * not cloned.
	 *
	 * @return a shallow copy of this example set.
	 */
	public ExampleSet copy()
	{
		ExampleSet copy = new SentenceSetCopy();
		for (int i=0;i<size();i++)
			copy.add(x(i), y(i), id(i));

		return copy;
	} // end copy

//	//
//	public static void main(String args[]) throws Exception
//	{
//		String logConfig = System.getProperty("log-config");
//		if (logConfig == null)
//			logConfig = "log-config.txt";
//
//		PropertyConfigurator.configure(logConfig);
//
//		if (args.length != 1)
//		{
//			System.err.println("java -mx512M org.itc.irst.tcc.sre.data.SentenceSetCopy in");
//			System.exit(-1);
//		}
///*
//		for (int j=1;j<11;j++)
//		{
//		File f = new File("./aimed/v4/split/" + j + ".test");
//		//File f = new File(args[0]);
//		SentenceSetCopy inputSet = new SentenceSetCopy();
//		SentenceSetCopy outputSet = new SentenceSetCopy();
//		inputSet.read(new BufferedReader(new FileReader(f)));
//
//		System.out.println("inputSet.size: " + inputSet.size());
//
//		System.out.println("outputSet.size: " + outputSet.size());
//
//		String name = f.getName();
//		//String parent = f.getParent();
//		//File g = new File("./aimed/v5/split" + name);
//		File g = new File("./aimed/v5/split/" + (j-1) + ".test");
//		BufferedWriter out = new BufferedWriter(new FileWriter(g));
//		outputSet.write(out);
//		out.close();
//		}
//*/
//		SentenceSetCopy inputSet = new SentenceSetCopy();
//		inputSet.read(new BufferedReader(new FileReader(args[0])));
//
//		//Word[] words = (Word[]) inputSet.x(10);
//		//Sentence sent = new Sentence(words);
//		Sentence sent = (Sentence) inputSet.x(0);
//		//Sentence frag = sent.fragment(4, 100);
//
//		System.out.println("size: " + sent.length());
//		System.out.println( inputSet.id(0) + ": \"" + sent + "\"\n");
//
//		ForeBetweenContext fbc = ForeBetweenContext.getInstance();
//		Sentence fb = fbc.filter(sent);
//		System.out.println("size: " + fb.length());
//		System.out.println("FB:\"" + fb + "\"\n");
//
//		BetweenContext bc = BetweenContext.getInstance();
//		Sentence b = bc.filter(sent);
//		System.out.println("size: " + b.length());
//		System.out.println("B:\"" + b + "\"\n");
//
//		BetweenAfterContext bac = BetweenAfterContext.getInstance();
//		Sentence ba = bac.filter(sent);
//		System.out.println("size: " + ba.length());
//		System.out.println("BA:\"" + ba + "\"\n");
//
//		TargetContext tc = TargetContext.getInstance();;
//		Sentence t = tc.filter(sent);
//		if (t != null)
//		{
//			System.out.println("size: " + t.length());
//			System.out.println("T:\"" + t + "\"\n");
//		}
//
//		SecondTargetContext stc = SecondTargetContext.getInstance();;
//		Sentence st = stc.filter(sent);
//		if (st != null)
//		{
//			System.out.println("size: " + st.length());
//			System.out.println("ST:\"" + st + "\"\n");
//		}
//
//		AgentContext ac = AgentContext.getInstance();;
//		Sentence a = ac.filter(sent);
//		if (a != null)
//		{
//			System.out.println("size: " + a.length());
//			System.out.println("A:\"" + a + "\"\n");
//		}
//	} // end main

} // end class SentenceSetCopy
