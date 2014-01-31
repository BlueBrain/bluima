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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

import org.itc.irst.tcc.sre.data.ArgumentSet;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.kernel.expl.Mapping;
import org.itc.irst.tcc.sre.kernel.expl.MappingFactory;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.Vector;
import org.itc.irst.tcc.sre.util.svm_train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class Map
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>Map</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(Map.class.getName());

	//
	public static final int MAX_NUMBER_OF_CLASSES = 20;

	//
	private Properties parameter;

	//
	public Map(Properties parameter)
	{
		this.parameter = parameter;
	} // end constructor

	//
	public void run() throws Exception
	{
		logger.info("Map a relation extraction model");

		// read training set
		File trainingFile = new File(parameter.getProperty("train-file"));
		ExampleSet trainingSet = readDataSet(trainingFile);
		logger.info("input training set size: " + trainingSet.size());

		// read test set
		File testFile = new File(parameter.getProperty("test-file"));
		ExampleSet testSet = readDataSet(testFile);
		logger.info("input test set size: " + testSet.size());


		// get the class freq
		int[] freq = classFreq(trainingSet);

		// calculate the class weight
		double[] weight = classWeigth(freq);

		// find argument types
		ArgumentSet.getInstance().init(trainingSet);

		// set the relation type
		int count = trainingSet.getClassCount();
		//setRelationType(count);

		logger.debug("number of classes: " + count);
		//logger.info("learn " + (relationType == DIRECTED_RELATION ? "directed" : "undirected") + " relations (" + relationType + ")");

		// create the mapping factory
		MappingFactory mappingFactory = MappingFactory.getMappingFactory();
		Mapping mapping = mappingFactory.getInstance(parameter.getProperty("kernel-type"));

		// set the command line parameters
		mapping.setParameters(parameter);

		// get the number of subspaces
		int subspaceCount = mapping.subspaceCount();
		logger.debug("number of subspaces: " + subspaceCount);

		// create the index
		FeatureIndex[] index = createFeatureIndex(subspaceCount);

		// embed the input data into a feature space
		logger.info("embed the training set");
		ExampleSet outputSet = mapping.map(trainingSet, index);
		logger.debug("embedded training set size: " + outputSet.size());

		// if not specified, calculate SVM parameter C
		double c = calculateC(outputSet);
		logger.info("cost parameter C = " + c);

		// save the training set
		File training = saveExampleSet(outputSet);

		// save the indexes
		saveFeatureIndexes(index);

		// save param
		saveParameters();

		// Map the svm
		svmTrain(training, c, weight);


	} // end run

	// read the data set
	private ExampleSet readDataSet(File in) throws IOException
	{
		logger.info("read the example set");

		//
		ExampleSet trainingSet = new SentenceSetCopy();
		trainingSet.read(new BufferedReader(new FileReader(in)));

		String trainFrac = parameter.getProperty("Map-frac");
		if (trainFrac != null)
		{
			double f = Double.parseDouble(trainFrac);
			logger.info("training original size: " + trainingSet.size());
			logger.info("training fraction: " + (100 * f) + "%");
			return trainingSet.subSet(0, (int) (trainingSet.size() * f));
		}

		return trainingSet;
	}	// end readDataSet
/*
	// get the feature mapping function
	private AbstractMapping mappingFactory() throws KernelNotFoundException
	{
		logger.info("get the feature mapping function");

		// kernel factory
		AbstractMapping mapping = null;

		String kernelType = parameter.getProperty("kernel-type").toUpperCase();
		if (kernelType.equals(GLOBAL_CONTEXT_KERNEL))
			mapping = new GlobalContextMapping();
		else if (kernelType.equals(LOCAL_CONTEXT_KERNEL))
			mapping = new LocalContextMapping();
		else if (kernelType.equals(SHALLOW_LINGUISTIC_KERNEL))
			mapping = new ShallowLinguisticMapping();
		else
			throw new KernelNotFoundException(kernelType + " not found.");

		return mapping;
	} // end mappingFactory
*/

	// calculate parameter C of SVM
	//
	// To allow some flexibility in separating the categories,
	// SVM models have a cost parameter, C, that controls the
	// trade off between allowing training errors and forcing
	// rigid margins. It creates a soft margin that permits
	// some misclassifications. Increasing the value of C
	// increases the cost of misclassifying points and forces
	// the creation of a more accurate model that may not
	// generalize well
	private double calculateC(ExampleSet data) //throws Exception
	{
		String svmCost = parameter.getProperty("svm-cost");
		if (svmCost != null)
			return Integer.parseInt(svmCost);

		logger.info("calculate default SVM cost parameter C");

		//double c = 1;
		double avr = 0;

		// the example set is normalized
		// all vectors have the same norm
		for (int i=0;i<data.size();i++)
		{
			Vector v = (Vector) data.x(i);
			double norm = v.norm();
			//logger.info(i + ", norm = " + norm);
			//if (norm > c)
			//	c = norm;
			avr += norm;
		} // end for i

		return 1/Math.pow(avr / data.size(), 2);
	} // end calculateC

	// create feature index
	private FeatureIndex[] createFeatureIndex(int subspaceCount) //throws Exception
	{
		logger.info("create feature index");

		FeatureIndex[] index = new FeatureIndex[subspaceCount];
		for (int i=0;i<subspaceCount;i++)
			index[i] = new FeatureIndex(false, 1);

		return index;
	} // end createFeatureIndex

	// save feature index
	private void saveFeatureIndexes(FeatureIndex[] index) throws IOException
	{
		logger.info("save feature index (" + index.length + ")");

		// save the indexes
		for (int i=0;i<index.length;i++)
		{
			logger.debug("dic" + i + " size " + index[i].size());

			File tmp = File.createTempFile("dic" + i, null);
			tmp.deleteOnExit();

			BufferedWriter bwd = new BufferedWriter(new FileWriter(tmp));
			index[i].write(bwd);
			bwd.close();

		} // end for
	} // end saveFeatureIndexes

	// save the embedded training set
	private File saveExampleSet(ExampleSet outputSet) throws IOException
	{
		logger.info("save the embedded training set");

		//
		File tmp = File.createTempFile("Map", null);
		tmp.deleteOnExit();
		//File tmp = new File("examples/Map");

		BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
		outputSet.write(out);
		out.close();

		return tmp;
	} // end saveExampleSet

	// save parameters
	private void saveParameters() throws IOException
	{
		logger.info("save parameters");

		// save param
		File paramFile = File.createTempFile("param", null);
		paramFile.deleteOnExit();

		parameter.store(new FileOutputStream(paramFile), null);

	} // end saveParameters

	// run svm
	private void svmTrain(File svmTrainingFile, double c, double[]  weight) throws Exception
	{
		logger.info("run svm Map");

		File svmModelFile = File.createTempFile("model", null);
		svmModelFile.deleteOnExit();

		//int[] label = null;
		//double[] weight = null;

		//
		int cache = 128;
		if (parameter.getProperty("cache-size") != null)
		  cache = Integer.parseInt(parameter.getProperty("cache-size"));
		new svm_train().run(svmTrainingFile, svmModelFile, c, cache, weight);

	} //end svmTrain

	//
	private int[] classFreq(ExampleSet set) throws IOException
	{
		// small example set can have only one class
		if (set.getClassCount() == 1)
			return new int[]{1, 1, 1};

		logger.debug("class count: " + set.getClassCount());
		//int[] c = new int[set.getClassCount()];
		int[] c = new int[MAX_NUMBER_OF_CLASSES];

		Iterator it = set.classes();
		while (it.hasNext())
		{
			String y = (String) it.next();
			logger.debug("class " + y);

			int f = set.classFreq(y);
			logger.debug("freq " + f);
			c[Integer.parseInt(y)] = f;
			logger.debug("class " + y + " : " + f);
		} // end while

		return c;
	} // end classFreq

	//
	private double[] classWeigth(int[] c)
	{
		double[] w = new double[c.length];
		for (int i=1;i<c.length;i++)
		{
			if (c[i] != 0)
				w[i] = (double) c[0] / c[i];
			logger.debug("weight[" + i + "] = " + w[i]);
		}
		return w;
	} // end classWeigth

//	//
//	public static void main(String args[]) throws Exception
//	{
//		String logConfig = System.getProperty("log-config");
//		if (logConfig == null)
//			logConfig = "log-config.txt";
//
//		PropertyConfigurator.configure(logConfig);
//
//		CommandLineParameters  parameter = new CommandLineParameters();
//		parameter.parse(args);
//		logger.debug(parameter);
//
//		Map Map = new Map(parameter);
//		Map.run();
//
//	} // end main

	//
	static class CommandLineParameters extends Properties
	{
		//
		public CommandLineParameters() throws IOException
		{
			// load default parameters
			//load(new FileInputStream("default-parameters.properties"));

			//setProperty("help", "false");
			setProperty("cache-size", "128");
			setProperty("kernel-type", "SL");
			setProperty("n-gram", "3");
			setProperty("window-size", "2");
			//setProperty("use-tf", "false");
			//setProperty("stemmer-type", null);
			//setProperty("svm-cost", "-1");
			//setProperty("Map-frac", null);


		} // end constructor

		// parse command line
		public void parse(String[] args)
		{
			if (args.length < 2)
			{
				System.err.println(getHelp());
				System.exit(-1);
			}

			setProperty("train-file", args[args.length - 2]);
			setProperty("test-file", args[args.length - 1]);


			// set parameters
			for (int i=0;i<args.length;i++)
			{
				if (args[i].equals("-h") || args[i].equals("--help"))
					setProperty("help", args[i+1]);
				else if (args[i].equals("-m") || args[i].equals("--cache-size"))
					setProperty("cache-size", args[i+1]);
				else if (args[i].equals("-k") || args[i].equals("--kernel-type"))
					setProperty("kernel-type", args[i+1]);
				else if (args[i].equals("-n") || args[i].equals("--n-gram"))
					setProperty("n-gram", args[i+1]);
				else if (args[i].equals("-w") || args[i].equals("--window-size"))
					setProperty("window-size", args[i+1]);
				//else if (args[i].equals("-tf") || args[i].equals("--use-tf"))
				//	setProperty("use-tf", "true");
				//else if (args[i].equals("-s") || args[i].equals("--stemmer-type"))
				//	setProperty("stemmer-type", args[i+1]);
				////else if (args[i].equals("-p") || args[i].equals("--param-file"))
				////	setProperty("param-file", args[i+1]);
				else if (args[i].equals("-c") || args[i].equals("--svm-cost"))
					setProperty("svm-cost", args[i+1]);
				else if (args[i].equals("-f") || args[i].equals("--train-frac"))
					setProperty("train-frac", args[i+1]);

			} // end for

		} // end constructor

		/**
			* Returns a command-line help.
		 *
		 * return a command-line help.
		 */
		private String getHelp()
		{
			StringBuffer sb = new StringBuffer();

			// SRE
			sb.append("\njSRE: Simple Relation Extraction V1.10\t 30.08.06\n");
			sb.append("developed by Claudio Giuliano (giuliano@itc.it)\n\n");

			// License
			sb.append("Copyright 2005 FBK-irst (http://www.fbk.eu)\n");
			sb.append("\n");
			sb.append("Licensed under the Apache License, Version 2.0 (the \"License\");\n");
			sb.append("you may not use this file except in compliance with the License.\n");
			sb.append("You may obtain a copy of the License at\n");
			sb.append("\n");
			sb.append("    http://www.apache.org/licenses/LICENSE-2.0\n");
			sb.append("\n");
			sb.append("Unless required by applicable law or agreed to in writing, software\n");
			sb.append("distributed under the License is distributed on an \"AS IS\" BASIS,\n");
			sb.append("WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n");
			sb.append("See the License for the specific language governing permissions and\n");
			sb.append("limitations under the License.\n\n");

			// Usage
			sb.append("Usage: java -mx1024M org.itc.irst.tcc.sre.Map [options] train-file test-file\n\n");

			// Arguments
			sb.append("Arguments:\n");
			sb.append("\ttrain-file\t-> file with training data (SRE format)\n");
			sb.append("\ttest-file\t-> file with training data (SRE format)\n");

			sb.append("Options:\n");
			sb.append("\t-h\t\t-> this help\n");
			sb.append("\t-k string\t-> set type of kernel function (default SL):\n");
			sb.append("\t\t\t\tLC: Local Context Kernel\n");
			sb.append("\t\t\t\tGC: Global Context Kernel\n");
			sb.append("\t\t\t\tSL: Shallow Linguistic Context Kernel\n");

			sb.append("\t-n [1..]\t-> set the parameter n-gram of kernels SL and GC  (default 3)\n");
			sb.append("\t-w [0..]\t-> set the window size of kernel LC (default 2)\n");
			sb.append("\t-c [0..]\t-> set the trade-off between training error and margin (default 1/[avg. x*x'])\n");

			sb.append("\t-f\t-> fraction of training set (default 1)\n");
			sb.append("\t-m int\t\t-> set cache memory size in MB (default 128)\n");

			return sb.toString();
		} // end getHelp

		//
		public String toString()
		{
			StringWriter sw = new StringWriter();
			list(new PrintWriter(sw));

			return sw.toString();
		} // end toString

		//
		class IllegalParameterException extends IllegalArgumentException
		{
			public IllegalParameterException(String s)
			{
				super(s);
			} // end constructor

		} // end IllegalParameterException

	} // end class CommandLineParameters

} // end class Map
