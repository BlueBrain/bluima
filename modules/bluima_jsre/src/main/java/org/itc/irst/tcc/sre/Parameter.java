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
package org.itc.irst.tcc.sre;

import java.util.*;
import java.io.*;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

//
public class Parameter
{
	/**
 	* Define a static logger variable so that it references the
	 * Logger instance named <code>Parameter</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(Parameter.class.getName());

	/**
	 * A prototype for a global context such that only
	 * one instance class can ever exist.
	 */
	private static Parameter parameter;

	//
	public static final String DEFAULT_STEMMER = "Porter";

	//
	public static final String DEFAULT_KERNEL = "GC";

	//
	public static final int DEFAULT_CACHE_SIZE = 128;

	//
	public static final int DEFAULT_LOCAL_WINDOW_SIZE = 1;

	//
	public static final int DEFAULT_NGRAM = 3;

	//
	private int cacheSize;

	//
	private String kernel;

	//
	private int localSize;

	//
	private int ngram;

	//
	private String stemmer;

	//
	private boolean useTF;

	//
	private File paramFile;

	//
	private boolean printHelp;

	//
	private File inputFile;

	//
	private File modelFile;

	//
	private Parameter()
	{
		parameter = this;

		// default
		cacheSize = DEFAULT_CACHE_SIZE;
		kernel = DEFAULT_KERNEL;
		localSize = DEFAULT_LOCAL_WINDOW_SIZE;
		ngram = DEFAULT_NGRAM;
		stemmer = DEFAULT_STEMMER;
		useTF = false;
		printHelp = false;

	} // end constructor

	//
	public void parse(String[] args) throws IOException
	{
		inputFile = new File(args[args.length - 2]);
		modelFile = new File(args[args.length - 1]);


		// set parameters
		for (int i=0;i<args.length;i++)
		{
			if (args[i].equals("-h"))
				printHelp = true;
			else if (args[i].equals("-m"))
				cacheSize = Integer.parseInt(args[i+1]);
			else if (args[i].equals("-k"))
				kernel = args[i+1];
			else if (args[i].equals("-n"))
				ngram = Integer.parseInt(args[i+1]);
			else if (args[i].equals("-w"))
				localSize = Integer.parseInt(args[i+1]);
			else if (args[i].equals("-tf"))
				useTF = true;
			else if (args[i].equals("-st"))
				stemmer = args[i+1];
			else if (args[i].equals("-p"))
				paramFile = new File(args[i+1]);

		} // end for

	} // end constructor

	//
	public String kernelName()
	{
		return kernel;
	} // end kernelName

	//
	public String stemmerName()
	{
		return stemmer;
	} // end stemmerName

	//
	public int ngram()
	{
		return ngram;
	} // end ngram

	//
	public int localWindowSize()
	{
		return localSize;
	} // end localWindowSize

	//
	public int cacheSize()
	{
		return cacheSize;
	} // end cacheSize

	//
	public boolean useTF()
	{
		return useTF;
	} // end useTF

	//
	public boolean printHelp()
	{
		return printHelp;
	} // end printHelp

	//
	public File inputFile()
	{
		return inputFile;
	} // end inputFile

	//
	public File modelFile()
	{
		return modelFile;
	} // end modelFile

	//
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append("cacheSize  = " +  cacheSize + "\n");
		sb.append("kernel  = " +  kernel + "\n");
		sb.append("localSize  = " +  localSize + "\n");
		sb.append("ngram  = " +  ngram + "\n");
		sb.append("stemmer  = " +  stemmer + "\n");
		sb.append("useTF  = " +  useTF + "\n");
		sb.append("printHelp  = " +  printHelp + "\n");
		sb.append("inputFile  = " +  inputFile + "\n");
		sb.append("modelFile  = " +  modelFile + "\n");

		return sb.toString();
	} // end toString

	/**
	 * Returns <code>Parameter</code> object; only
 	* one <code>Parameter</code> instance can
 	* exist.
 	*
 	* @return	<code>Parameter</code> object
 	*/
	public static synchronized Parameter getInstance()
	{
		if (parameter == null)
		{
			parameter = new Parameter();
		}

		return parameter;
	} // end getInstance

} // end class Parameter
