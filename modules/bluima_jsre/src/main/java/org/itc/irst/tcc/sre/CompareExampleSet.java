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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.itc.irst.tcc.sre.data.ArgumentSet;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.Sentence;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.kernel.expl.AbstractMapping;
import org.itc.irst.tcc.sre.kernel.expl.ContextMappingFactory;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.Node;
import org.itc.irst.tcc.sre.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class CompareExampleSet {

    static Logger logger = LoggerFactory.getLogger(CompareExampleSet.class
            .getName());

    private Properties parameter;
    private ExampleSet inputSet1, inputSet2;
    private ExampleSet outputSet1, outputSet2;

    //
    public CompareExampleSet(Properties parameter) {
        this.parameter = parameter;
    } // end constructor

    //
    public void run() throws Exception {
        logger.info("create a train set for relation extraction");

        // read data set
        File inputFile1 = new File(parameter.getProperty("example-file1"));
        inputSet1 = readDataSet(inputFile1);
        logger.debug("input training set size: " + inputSet1.size());

        File inputFile2 = new File(parameter.getProperty("example-file2"));
        inputSet2 = readDataSet(inputFile2);
        logger.debug("input training set size: " + inputSet2.size());

        // find argument types
        ArgumentSet.getInstance().init(inputSet1);

        // create the mapping factory
        ContextMappingFactory contextMappingFactory = ContextMappingFactory
                .getContextMappingFactory();
        AbstractMapping contextMapping = contextMappingFactory
                .getInstance(parameter.getProperty("kernel-type"));
        // set the command line parameters
        contextMapping.setParameters(parameter);

        // get the number of subspaces
        int subspaceCount = contextMapping.subspaceCount();
        logger.debug("number of subspaces: " + subspaceCount);

        // create the index
        FeatureIndex[] index = createFeatureIndex(subspaceCount);

        // embed the input data into a feature space
        logger.info("embed the training set");
        outputSet1 = contextMapping.map(inputSet1, index);
        logger.debug("embedded training set size: " + outputSet1.size());

        for (int i = 0; i < subspaceCount; i++)
            index[i].readOnly(true);

        // embed the input data into a feature space
        logger.info("embed the training set");
        outputSet2 = contextMapping.map(inputSet2, index);
        logger.debug("embedded training set size: " + outputSet2.size());

        compare();
    } // end run

    //
    private void compare() {
        double kernelThreshold = 0;
        if (parameter.getProperty("kernel-threshold") != null)
            kernelThreshold = Double.parseDouble(parameter
                    .getProperty("kernel-threshold"));

        logger.info("kernelThreshold: " + kernelThreshold);
        int cor = 0, total = 0, partial = 0, pc = 0;

        for (int i = 0; i < outputSet1.size(); i++) {
            for (int j = 0; j < outputSet2.size(); j++) {
                Vector v1 = (Vector) outputSet1.x(i);
                Vector v2 = (Vector) outputSet2.x(j);
                Sentence s1 = (Sentence) inputSet1.x(i);
                Sentence s2 = (Sentence) inputSet2.x(j);
                Node[] n1 = v1.toArray();
                Node[] n2 = v2.toArray();
                // logger.info("n1 " + n1.length);
                // logger.info("n2 " + n2.length);
                double dot = Node.dotProduct(n1, n2);

                if (dot >= kernelThreshold)
                // if (dot < kernelThreshold)
                {
                    total++;
                    partial++;
                    // logger.info("v1: " + v1);
                    // logger.info("v2: " + v2);
                    // logger.info(total + "> " + i + ",  " + j + ": " + dot +
                    // " " + s1 + " <> " + s2 + " " + inputSet1.y(i) + " <> " +
                    // inputSet2.y(j) + "\n");
                    // logger.info(i + ",  " + j + ": " + dot + " " + s2 +
                    // " <> " + inputSet2.y(j) + "\n");
                    // logger.info(dot + " " + v1 + "<>" + v2);

                    if (inputSet1.y(i).equals(inputSet2.y(j))) {
                        cor++;
                        pc++;
                    }

                }
            } // end for j
            logger.info(pc + " / " + partial + " = " + ((double) pc / partial));
            partial = 0;
            pc = 0;
        } // end for i
        logger.info(cor + " / " + total + " = " + ((double) cor / total));
    } // end comapare

    // read the data set
    private ExampleSet readDataSet(File in) throws IOException {
        logger.info("read the example set");

        //
        ExampleSet inputSet1 = new SentenceSetCopy();
        inputSet1.read(new BufferedReader(new FileReader(in)));

        return inputSet1;
    } // end readDataSet

    // create feature index
    private FeatureIndex[] createFeatureIndex(int subspaceCount) // throws
                                                                 // Exception
    {
        logger.info("create feature index");

        FeatureIndex[] index = new FeatureIndex[subspaceCount];
        for (int i = 0; i < subspaceCount; i++)
            index[i] = new FeatureIndex(false, 1);

        return index;
    } // end createFeatureIndex

    private static String getHelp() {
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
        sb.append("Usage: java -mx1024M org.itc.irst.tcc.sre.CompareExampleSet [options] example-file1 example-file2\n\n");

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
        // sb.append("\t-c [0..]\t-> set the trade-off between training error and margin (default 1/[avg. x*x'])\n");
        sb.append("\t-t [0..]\t-> kernel threshold\n");

        // sb.append("\t-f\t-> fraction of training set (default 3)\n");
        sb.append("\t-m int\t\t-> set cache memory size in MB (default 128)\n");

        return sb.toString();
    } // end getHelp

//    //
//    public static void main(String args[]) throws Exception {
//        String logConfig = System.getProperty("log-config");
//        if (logConfig == null)
//            logConfig = "log-config.txt";
//
//        PropertyConfigurator.configure(logConfig);
//
//        Properties parameter = new Properties();
//        parameter.setProperty("cache-size", "128");
//        parameter.setProperty("kernel-type", "SL");
//        parameter.setProperty("n-gram", "3");
//        parameter.setProperty("window-size", "2");
//        parameter.setProperty("kernel-threshold", "0");
//        // parameter.setProperty("use-tf", "false");
//        // parameter.setProperty("stemmer-type", "null");
//        // parameter.setProperty("svm-cost", "-1");
//
//        if (args.length < 2) {
//            System.err.println(getHelp());
//            System.exit(-1);
//        }
//
//        parameter.setProperty("example-file1", args[args.length - 2]);
//        parameter.setProperty("example-file2", args[args.length - 1]);
//
//        // set parameters
//        for (int i = 0; i < args.length; i++) {
//            if (args[i].equals("-h") || args[i].equals("--help"))
//                parameter.setProperty("help", args[i + 1]);
//            else if (args[i].equals("-m") || args[i].equals("--cache-size"))
//                parameter.setProperty("cache-size", args[i + 1]);
//            else if (args[i].equals("-k") || args[i].equals("--kernel-type"))
//                parameter.setProperty("kernel-type", args[i + 1]);
//            else if (args[i].equals("-n") || args[i].equals("--n-gram"))
//                parameter.setProperty("n-gram", args[i + 1]);
//            else if (args[i].equals("-w") || args[i].equals("--window-size"))
//                parameter.setProperty("window-size", args[i + 1]);
//            else if (args[i].equals("-t")
//                    || args[i].equals("--kernel-threshold"))
//                parameter.setProperty("kernel-threshold", args[i + 1]);
//
//            // else if (args[i].equals("-tf") || args[i].equals("--use-tf"))
//            // parameter.setProperty("use-tf", "true");
//            // else if (args[i].equals("-s") ||
//            // args[i].equals("--stemmer-type"))
//            // parameter.setProperty("stemmer-type", args[i+1]);
//            // //else if (args[i].equals("-p") ||
//            // args[i].equals("--param-file"))
//            // // parameter.setProperty("param-file", args[i+1]);
//
//        } // end for
//
//        CompareExampleSet createTraining = new CompareExampleSet(parameter);
//        createTraining.run();
//
//    } // end main

} // end class CompareExampleSet
