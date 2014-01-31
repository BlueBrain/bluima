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
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Properties;

import org.itc.irst.tcc.sre.data.ArgumentSet;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.kernel.expl.AbstractMapping;
import org.itc.irst.tcc.sre.kernel.expl.ContextMappingFactory;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class CreateTrainingSet
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>CreateTrainingSet</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(CreateTrainingSet.class.getName());

	//
	public static final int MAX_NUMBER_OF_CLASSES = 20;

	//
	private Properties parameter;

	//
	public CreateTrainingSet(Properties parameter)
	{
		this.parameter = parameter;
	} // end constructor

	//
	public void run() throws Exception
	{
		logger.info("create a train set for relation extraction");

		//
		String outputDir = parameter.getProperty("output-dir");
		if (!outputDir.endsWith(File.separator))
			outputDir += File.separator;

		if (!new File(outputDir).mkdir())
		{
			logger.error("cannot create dir " + outputDir);
			System.exit(-1);
		}

		// read data set
		//ExampleSet inputSet = readDataSet(parameter.inputFile());
		File inputFile = new File(parameter.getProperty("example-file"));
		ExampleSet inputSet = readDataSet(inputFile);
		logger.info("input training set size: " + inputSet.size());

		// get the class freq
		int[] freq = classFreq(inputSet);

		// calculate the class weight
		double[] weight = classWeigth(freq);

		// find argument types
		ArgumentSet.getInstance().init(inputSet);

		// create the mapping factory
		//AbstractMapping mapping = mappingFactory();
		ContextMappingFactory contextMappingFactory = ContextMappingFactory.getContextMappingFactory();
		AbstractMapping contextMapping = contextMappingFactory.getInstance(parameter.getProperty("kernel-type"));
		// set the command line parameters
		contextMapping.setParameters(parameter);

		// get the number of subspaces
		int subspaceCount = contextMapping.subspaceCount();
		logger.debug("number of subspaces: " + subspaceCount);

		// create the index
		FeatureIndex[] index = createFeatureIndex(subspaceCount);

		// embed the input data into a feature space
		logger.info("embed the training set");
		ExampleSet outputSet = contextMapping.map(inputSet, index);
		logger.debug("embedded training set size: " + outputSet.size());

		// if not specified, calculate SVM parameter C
		double c = calculateC(outputSet);
		logger.info("cost parameter C = " + c);

		// save the training set
		File training = saveExampleSet(outputSet, outputDir);

		// save the indexes
		saveFeatureIndexes(index, outputDir);

		// save param
		saveParameters(outputDir);

		// save command line
		saveCommandLine(outputDir, c, weight);
	} // end run

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

	// read the data set
	private ExampleSet readDataSet(File in) throws IOException
	{
		logger.info("read the example set");

		//
		ExampleSet inputSet = new SentenceSetCopy();
		inputSet.read(new BufferedReader(new FileReader(in)));

		String trainFrac = parameter.getProperty("train-frac");
		if (trainFrac != null)
		{
			double f = Double.parseDouble(trainFrac);
			logger.info("training original size: " + inputSet.size());
			logger.info("training fraction: " + (100 * f) + "%");
			return inputSet.subSet(0, (int) (inputSet.size() * f));
		}

		return inputSet;
	}	// end readDataSet

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
	private void saveFeatureIndexes(FeatureIndex[] index, String outputDir) throws IOException
	{
		logger.info("save feature index");

		// save the indexes
		for (int i=0;i<index.length;i++)
		{
			logger.debug("dic" + i + " size " + index[i].size());

			File tmp = new File(outputDir + "dic" + i);
			//File tmp = File.createTempFile("dic" + i, null);
			//tmp.deleteOnExit();

			BufferedWriter bwd = new BufferedWriter(new FileWriter(tmp));
			index[i].write(bwd);
			bwd.close();

			// add the dictionary to the model
			//model.add(tmp, "dic" + i);
		} // end for
	} // end saveFeatureIndexes

	// save the embedded training set
	private File saveExampleSet(ExampleSet outputSet, String outputDir) throws IOException
	{
		logger.info("save the embedded training set");

		//
		//File tmp = File.createTempFile("train", null);
		//tmp.deleteOnExit();
		File tmp = new File(outputDir + "train");

		BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
		outputSet.write(out);
		out.close();

		// add the example set to the model
		//model.add(tmp, "train");

		return tmp;
	} // end saveExampleSet

	// save the embedded training set
	private File saveCommandLine(String outputDir, double c, double[] weight) throws IOException
	{
		logger.info("save the command line");

		//
		DecimalFormat format = new DecimalFormat("0.000");

		File tmp = new File(outputDir + "command-line");

		PrintWriter pw = new PrintWriter(new FileWriter(tmp));

		pw.print("svm-train -t 0 -c " + c);
		for (int i=0;i<weight.length;i++)
		{
			if (weight[i] != 0)
				pw.print(" -w" + i + " " + format.format(weight[i]));
		} // for i

		pw.print("\n");
		pw.flush();
		pw.close();

		return tmp;
	} // end saveExampleSet

	// save parameters
	private void saveParameters(String outputDir) throws IOException
	{
		logger.info("save parameters");

		// save param
		File paramFile = new File(outputDir + "param");
		//File paramFile = File.createTempFile("param", null);
		//paramFile.deleteOnExit();

		//parameter.store(new FileOutputStream(paramFile), "model parameters");
		parameter.store(new FileOutputStream(paramFile), null);

		// add the param to the model
		//model.add(paramFile, "param");
	} // end saveParameters

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

	/**
	 * Returns a command-line help.
	 *
	 * return a command-line help.
	 */
	private static String getHelp()
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
		sb.append("Usage: java -mx1024M org.itc.irst.tcc.sre.CreateTrainingSet [options] example-file output-dir\n\n");

		// Arguments
		sb.append("Arguments:\n");
		sb.append("\texample-file\t-> file with training data (SRE format)\n");
		sb.append("\toutput-dir\t-> directory in which to store resulting files\n");

		sb.append("Options:\n");
		sb.append("\t-h\t\t-> this help\n");
		sb.append("\t-k string\t-> set type of kernel function (default SL):\n");
		sb.append("\t\t\t\tLC: Local Context Kernel\n");
		sb.append("\t\t\t\tGC: Global Context Kernel\n");
		sb.append("\t\t\t\tSL: Shallow Linguistic Context Kernel\n");

		sb.append("\t-n [1..]\t-> set the parameter n-gram of kernels SL and GC  (default 3)\n");
		sb.append("\t-w [0..]\t-> set the window size of kernel LC (default 2)\n");
		//sb.append("\t-c [0..]\t-> set the trade-off between training error and margin (default 1/[avg. x*x'])\n");

		sb.append("\t-f\t-> fraction of training set (default 1)\n");
		sb.append("\t-m int\t\t-> set cache memory size in MB (default 128)\n");

		return sb.toString();
	} // end getHelp

//	//
//	public static void main(String args[]) throws Exception
//	{
//		String logConfig = System.getProperty("log-config");
//		if (logConfig == null)
//			logConfig = "log-config.txt";
//
//		PropertyConfigurator.configure(logConfig);
//
//		Properties parameter = new Properties();
//		parameter.setProperty("cache-size", "128");
//		parameter.setProperty("kernel-type", "SL");
//		parameter.setProperty("n-gram", "3");
//		parameter.setProperty("window-size", "2");
//		//parameter.setProperty("use-tf", "false");
//		//parameter.setProperty("stemmer-type", "null");
//		//parameter.setProperty("svm-cost", "-1");
//
//		if (args.length < 2)
//		{
//			System.err.println(getHelp());
//			System.exit(-1);
//		}
//
//		parameter.setProperty("example-file", args[args.length - 2]);
//		parameter.setProperty("output-dir", args[args.length - 1]);
//
//
//		// set parameters
//		for (int i=0;i<args.length;i++)
//		{
//			if (args[i].equals("-h") || args[i].equals("--help"))
//				parameter.setProperty("help", args[i+1]);
//			else if (args[i].equals("-m") || args[i].equals("--cache-size"))
//				parameter.setProperty("cache-size", args[i+1]);
//			else if (args[i].equals("-k") || args[i].equals("--kernel-type"))
//				parameter.setProperty("kernel-type", args[i+1]);
//			else if (args[i].equals("-n") || args[i].equals("--n-gram"))
//				parameter.setProperty("n-gram", args[i+1]);
//			else if (args[i].equals("-w") || args[i].equals("--window-size"))
//				parameter.setProperty("window-size", args[i+1]);
//			else if (args[i].equals("-f") || args[i].equals("--train-frac"))
//				parameter.setProperty("train-frac", args[i+1]);
//
//
//		} // end for
//
//		CreateTrainingSet createTraining = new CreateTrainingSet(parameter);
//		createTraining.run();
//
//	} // end main

} // end class CreateTrainingSet

/*
	//
	public static void main(String args[]) throws Exception
	{
		String logConfig = System.getProperty("log-config");
		if (logConfig == null)
			logConfig = "log-config.txt";

		PropertyConfigurator.configure(logConfig);


    Options options = new Options();
    options.addOption("h", false, "this help");
    options.addOption(OptionBuilder.withArgName("int")
    														.withValueSeparator(' ')
                                .hasArg()
                                .withDescription("set cache memory size in MB (default 128)")
                                .create('m'));
    options.addOption(OptionBuilder.withArgName("GC|LC|SL")
    														.withValueSeparator(' ')
                                .hasArg()
                                .withDescription("set type of kernel function (default SL)")
                                .create('k'));
    options.addOption(OptionBuilder.withArgName("n")
    														.withValueSeparator(' ')
                                .hasArg()
                                .withDescription("set the parameter n-gram of kernels SL and GC  (default 3)")
                                .create('n'));
    options.addOption(OptionBuilder.withArgName("w")
    														.withValueSeparator(' ')
                                .hasArg()
                                .withDescription("set the window size of kernel LC (default 2)")
                                .create('w'));

	// create the command line parser
	CommandLineParser parser = new PosixParser();


	try
	{
    // parse the command line arguments
    CommandLine line = parser.parse(options, args);

		//
		String[] arg = line.getArgs();
		if (arg.length < 2)
		{
			//System.err.println(getHelp());
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(180, "java -mx128M org.itc.irst.tcc.sre.CreateTrainingSet [options] example-file model-file\n\n", "header", options, "footer");
			System.exit(-1);
		}

	//	setProperty("example-file", args[args.length - 2]);
		//setProperty("model-file", args[args.length - 1]);

    // validate that block-size has been set
    if(line.hasOption("k"))
    {
			// print the value of block-size
			System.out.println(line.getOptionValue("k"));
    }
	}
	catch( ParseException exp )
	{
    System.out.println("Unexpected exception:" + exp.getMessage());
	}

 	} // end main

*/
