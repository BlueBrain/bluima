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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.itc.irst.tcc.sre.data.ArgumentSet;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.kernel.expl.AbstractMapping;
import org.itc.irst.tcc.sre.kernel.expl.ContextMappingFactory;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class CreateTestSet {
    /**
     * Define a static logger variable so that it references the Logger instance
     * named <code>CreateTestSet</code>.
     */
    static Logger logger = LoggerFactory.getLogger(CreateTestSet.class
            .getName());

    //
    private File inputFile;

    //
    private File outputDir;

    //
    private File outputFile;

    //
    private String kernel;

    //
    private int relationType;

    //
    private Properties parameter;

    //
    private String path;

    //
    public CreateTestSet(File inputFile, File outputDir) {
        this.inputFile = inputFile;
        this.outputDir = outputDir;

        path = outputDir.getAbsolutePath();
        if (!path.endsWith(File.separator))
            path += File.separator;

        logger.info("dir " + path);
        /*
         * if (!outputDir.endsWith(File.separator)) outputDir += File.separator;
         * 
         * if (!new File(outputDir).mkdir()) { logger.error("cannot find dir " +
         * outputDir); System.exit(-1); }
         */
        parameter = new Properties();
    } // end constructor

    // read parameters
    private void readParameters() throws IOException {
        logger.info("read parameters");

        // get the param model
        File paramFile = new File(path + "param");

        parameter.load(new FileInputStream(paramFile));
    } // end readParameters

    // read the data set
    private ExampleSet readDataSet(File in) throws IOException {
        logger.info("read the example set");

        //
        logger.info("read data set");
        ExampleSet inputSet = new SentenceSetCopy();
        inputSet.read(new BufferedReader(new FileReader(in)));

        return inputSet;
    } // end readDataSet

    //
    private FeatureIndex[] readFeatureIndex(int subspaceCount)
            throws IOException {
        logger.info("read feature index");

        FeatureIndex[] index = new FeatureIndex[subspaceCount];
        for (int i = 0; i < subspaceCount; i++) {
            index[i] = new FeatureIndex(true, 1);

            File dicFile = new File(path + "dic" + i);
            // File dicFile = model.get("dic" + i);

            BufferedReader br = new BufferedReader(new FileReader(dicFile));
            index[i].read(br);
            br.close();

            logger.debug("dic" + i + ", " + dicFile + ", " + index[i].size());
        } // end for

        return index;
    } // end readFeatureIndexes

    // save the embedded test set
    private File saveExampleSet(ExampleSet outputSet) throws IOException {
        logger.info("save the embedded test set");

        //
        // File tmp = File.createTempFile("test", null);
        // tmp.deleteOnExit();
        File tmp = new File(path + "test");

        BufferedWriter out = new BufferedWriter(new FileWriter(tmp));
        outputSet.write(out);
        out.close();

        return tmp;
    } // end saveExampleSet

    //
    public void run() throws Exception {
        logger.info("mapper relations");

        // open zip archive
        // UnZipModel model = new UnZipModel(outputDir);

        // get the param model
        readParameters();
        logger.info(parameter + "");

        // read data set
        ExampleSet inputSet = readDataSet(inputFile);

        // find argument types
        ArgumentSet.getInstance().init(inputSet);

        // create the mapping factory
        // AbstractMapping contextMapping = mappingFactory();
        // set the parameters
        // contextMapping.setParameters(parameter);

        ContextMappingFactory contextMappingFactory = ContextMappingFactory
                .getContextMappingFactory();
        AbstractMapping contextMapping = contextMappingFactory
                .getInstance(parameter.getProperty("kernel-type"));
        // set the parameters
        contextMapping.setParameters(parameter);

        // get the number of subspaces
        int subspaceCount = contextMapping.subspaceCount();

        // read the index
        FeatureIndex[] index = readFeatureIndex(subspaceCount);

        // embed the input data into a feature space
        logger.info("embed the test set");
        ExampleSet outputSet = contextMapping.map(inputSet, index);
        logger.debug("embedded test set size: " + outputSet.size());

        // save the test set
        File svmTestFile = saveExampleSet(outputSet);

    } // end main

    //
    public static void main(String args[]) throws Exception {

        int parm = 2;

        if (args.length != parm) {
            System.out.println(getHelp());
            System.exit(-1);
        }

        File inputFile = new File(args[args.length - 2]);
        File outputDir = new File(args[args.length - 1]);

        CreateTestSet mapper = new CreateTestSet(inputFile, outputDir);
        mapper.run();
    } // end main

    /**
     * Returns a command-line help.
     * 
     * return a command-line help.
     */
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
        sb.append("Usage: java org.itc.irst.tcc.sre.CreateTestSet [options] example-file output-dir\n\n");

        // Arguments
        sb.append("Arguments:\n");
        sb.append("\ttest-file\t-> file with test data (SRE format)\n");
        sb.append("\toutput-dir\t-> directory in which to store resulting files\n");
        // sb.append("\toutput-file\t-> file in which to store resulting output\n");

        sb.append("Options:\n");
        sb.append("\t-h\t\t-> this help\n");

        return sb.toString();
    } // end getHelp

} // end class CreateTestSet
