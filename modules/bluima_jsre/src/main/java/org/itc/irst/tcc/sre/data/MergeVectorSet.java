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

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.itc.irst.tcc.kre.util.Array;

/**
 * Loads an example set for relation extraction.
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class MergeVectorSet
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>MergeVectorSet</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(MergeVectorSet.class.getName());

	//
	public MergeVectorSet(File f1, File f2) throws Exception
	{
		//double sum = 0;
		//List l1 = new ArrayList();
		//List l2 = new ArrayList();
		LineNumberReader r1 = new LineNumberReader(new FileReader(f1));
		LineNumberReader r2 = new LineNumberReader(new FileReader(f2));
		String s1 = null, s2 = null;
		while (((s1 = r1.readLine()) != null) && ((s2 = r2.readLine()) != null))
		{
			//l1.add(s1);
			String[] s = s2.split("\t");
			// tf1 tf2 tf3 n
			double tf1 = Double.parseDouble(s[0]);
			double tf2 = Double.parseDouble(s[1]);
			double tf3 = Double.parseDouble(s[2]);
			double n = Double.parseDouble(s[3]);

			//sum += Math.pow(tf3, 2);
			//l2.add(new Double(tf3));
			if (tf1 != tf2)
			{
				if (tf3 >= 50)
					System.out.println(s1 + " 200000:1");/*
				if (tf3 >= 100)
					System.out.println(s1 + " 200000:1");
				else if (tf3 >= 50)
					System.out.println(s1 + " 200000:0.5");
				else if (tf3 >= 25)
					System.out.println(s1 + " 200000:0.25");
				else
					System.out.println(s1 + " 200000:0");*/
			}
			else
				System.out.println(s1 + " 200000:0");
			//System.out.println(s1 + " 200000:" + tf3);
			//System.out.println(l1.charAt(0) + " 200000:" + tf3);
			//System.out.println(l1 + " 200000:" + l1.charAt(0));
		}
		r1.close();
		r2.close();
/*
		sum = Math.sqrt(sum);
		for (int i=0;i<l1.size();i++)
		{
			double d = ((Double) l2.get(i)).doubleValue() / sum;

			System.out.println(l1.get(i) + " 200000:" + d);
		}
		*/
	} // end constructor

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
//			System.err.println("java -mx512M org.itc.irst.tcc.sre.data.MergeVectorSet f1 f2");
//			System.exit(-1);
//		}
//
//		File f1 = new File(args[0]);
//		File f2 = new File(args[1]);
//
//		new MergeVectorSet(f1, f2);
//	} // end main
} // end class MergeVectorSet
