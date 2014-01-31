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
import java.text.DecimalFormat;
import java.util.Properties;

import org.itc.irst.tcc.sre.data.ArgumentSet;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.kernel.expl.Mapping;
import org.itc.irst.tcc.sre.kernel.expl.MappingFactory;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.Vector;
import org.itc.irst.tcc.sre.util.ZipModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class KernelMatrix
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>KernelMatrix</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(KernelMatrix.class.getName());

	//
	private Properties parameter;

	//
	public KernelMatrix(Properties parameter)
	{
		this.parameter = parameter;
	} // end constructor

	//
	public void run() throws Exception
	{
		logger.info("build the relation extraction kernel matrix");

		// create zip archive
		//ZipModel model = new ZipModel(parameter.modelFile());
		File modelFile = new File(parameter.getProperty("model-file"));
		ZipModel model = new ZipModel(modelFile);

		// read data set
		//ExampleSet inputSet = readDataSet(parameter.inputFile());
		File inputFile = new File(parameter.getProperty("example-file"));
		ExampleSet inputSet = readDataSet(inputFile);
		logger.debug("input training set size: " + inputSet.size());

		// find argument types
		ArgumentSet.getInstance().init(inputSet);

		// set the relation type
		int count = inputSet.getClassCount();

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
		ExampleSet outputSet = mapping.map(inputSet, index);
		logger.debug("embedded training set size: " + outputSet.size());

		// save the training set
		File training = saveExampleSet(outputSet, model);

		// save the indexes
		saveFeatureIndexes(index, model);

		// save param
		saveParameters(model);

		// calculate the kernel matrix
		calculateKernelMatrix(outputSet, model);


		// close the model
		model.close();
	} // end run

	// read the data set
	private ExampleSet readDataSet(File in) throws IOException
	{
		logger.info("read the example set");

		//
		ExampleSet inputSet = new SentenceSetCopy();
		inputSet.read(new BufferedReader(new FileReader(in)));

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
	private void saveFeatureIndexes(FeatureIndex[] index, ZipModel model) throws IOException
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

			// add the dictionary to the model
			model.add(tmp, "dic" + i);
		} // end for
	} // end saveFeatureIndexes

	// save the embedded training set
	private File saveExampleSet(ExampleSet outputSet, ZipModel model) throws IOException
	{
		logger.info("save the embedded training set");

		//
		File tmp = File.createTempFile("dataset", null);
		tmp.deleteOnExit();
		//File tmp = new File("examples/train");

		BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
		outputSet.write(out);
		out.close();

		// add the example set to the model
		model.add(tmp, "dataset");

		return tmp;
	} // end saveExampleSet

	// save parameters
	private void saveParameters(ZipModel model) throws IOException
	{
		logger.info("save parameters");

		// save param
		File paramFile = File.createTempFile("param", null);
		paramFile.deleteOnExit();

		//PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(paramFile)));
		//pw.println(parameter.getProperty("kernel-type"));
		//pw.println(relationType);
		//pw.close();

		//parameter.store(new FileOutputStream(paramFile), "model parameters");
		parameter.store(new FileOutputStream(paramFile), null);

		// add the param to the model
		model.add(paramFile, "param");
	} // end saveParameters

	// calculate kernel matrix
	private void calculateKernelMatrix(ExampleSet set, ZipModel model) throws Exception
	{
		// save kernel matrix
		File matrixFile = File.createTempFile("kernel-matrix", null);
		matrixFile.deleteOnExit();

		PrintWriter pw = new PrintWriter(new FileWriter(matrixFile));

		DecimalFormat	decFormatter = new DecimalFormat("0.0");
		logger.info("calculate kernel matrix");
		logger.debug("data set size " + set.size());
		int size = set.size();
		//double[][] matrix = new double[size][size];

		pw.println(size + " " + size);

		for (int i=0;i<size;i++)
		{
			Vector v1 = (Vector) set.x(i);
/*
			for (int j=0;j<i;j++)
			{
				matrix[i][j] = matrix[j][i];
				System.out.print(decFormatter.format(matrix[i][j]) + " ");
			}
	*/
			//for (int j=i;j<size;j++)
			for (int j=0;j<size;j++)
			{
				Vector v2 = (Vector) set.x(j);
				double dot = v1.dotProduct(v2);

				/*
				matrix[i][j] = v1.dotProduct(v2);
				if (matrix[i][j] < 0.3)
					matrix[i][j] = 0;
					*/
				//pw.print(decFormatter.format(matrix[i][j]) + " ");
				pw.print(dot + " ");
			}
			//logger.debug(i + " : " + set.y(i) + "\t" + set.id(i) + "\t" + set.x(i));
			pw.print("\n");
			pw.flush();
		}
		//pw.flush();
		pw.close();

		// add the kernel matrix to the model
		model.add(matrixFile, "kernel-matrix");

/*
		System.out.print("\n");

		//for (int i=0;i<set.size();i++)
		for (int i=0;i<size;i++)
		{
			Vector v1 = (Vector) set.x(i);
			//logger.info(i + " : " + v1);

			for (int j=0;j<i;j++)
				System.out.print(0.0 + " ");

			//for (int j=i;j<set.size();j++)
			for (int j=i;j<size;j++)
			{
				Vector v2 = (Vector) set.x(j);
				double dot = v1.dotProduct(v2);
				System.out.print(decFormatter.format(dot) + " ");
			}
			//logger.debug(i + " : " + set.y(i) + "\t" + set.id(i) + "\t" + set.x(i));
			System.out.print("\n");
		}

		System.out.print("\n");

		System.out.println(size + " " + size);

		//for (int i=0;i<set.size();i++)
		for (int i=0;i<size;i++)
		{
			Vector v1 = (Vector) set.x(i);
			//logger.info(i + " : " + v1);

			//for (int j=i;j<set.size();j++)
			for (int j=i;j<size;j++)
			{
				Vector v2 = (Vector) set.x(j);
				double dot = v1.dotProduct(v2);
				System.out.print(i + " " + j + " ");

				System.out.println(decFormatter.format(dot) + " ");
			}
			//logger.debug(i + " : " + set.y(i) + "\t" + set.id(i) + "\t" + set.x(i));
			System.out.print("\n");
		}
		*/
	} //end calculateKernelMatrix

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
//		KernelMatrix km = new KernelMatrix(parameter);
//		km.run();
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
			setProperty("kernel-type", "SL");
			setProperty("n-gram", "3");
			setProperty("window-size", "2");
			//setProperty("use-tf", "false");
			//setProperty("stemmer-type", "null");

		} // end constructor

		// parse command line
		public void parse(String[] args)
		{
			if (args.length < 2)
			{
				System.err.println(getHelp());
				System.exit(-1);
			}

			setProperty("example-file", args[args.length - 2]);
			setProperty("model-file", args[args.length - 1]);



			// set parameters
			for (int i=0;i<args.length;i++)
			{
				if (args[i].equals("-h") || args[i].equals("--help"))
					setProperty("help", args[i+1]);
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
			sb.append("Usage: java -mx1024M org.itc.irst.tcc.sre.KernelMatrix [options] example-file model\n\n");

			// Arguments
			sb.append("Arguments:\n");
			sb.append("\texample-file\t-> file with training data (SRE format)\n");
			sb.append("\tmodel-file\t-> file in which to store resulting model\n");

			sb.append("Options:\n");
			sb.append("\t-h\t\t-> this help\n");
			sb.append("\t-k string\t-> set type of kernel function (default SL):\n");
			sb.append("\t\t\t\tLC: Local Context Kernel\n");
			sb.append("\t\t\t\tGC: Global Context Kernel\n");
			sb.append("\t\t\t\tSL: Shallow Linguistic Context Kernel\n");

			sb.append("\t-n [1..]\t-> set the parameter n-gram of kernels SL and GC  (default 3)\n");
			sb.append("\t-w [0..]\t-> set the window size of kernel LC (default 2)\n");

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

} // end class KernelMatrix
