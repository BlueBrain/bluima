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
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.itc.irst.tcc.sre.data.Sentence;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.data.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class RelationExtractor {

    static Logger logger = LoggerFactory.getLogger(RelationExtractor.class
            .getName());

    //
    private SentenceSetCopy sentenceSet;

    //
    List pred;

    /**
     * Creates a <code>RelationExtractor</code> object.
     */
    public RelationExtractor(File refFile, File predFile) throws IOException {
        // logger.debug("RelationExtractor.RelationExtractor");

        // read the data set
        logger.info("read the data set");
        sentenceSet = new SentenceSetCopy();
        sentenceSet.read(new BufferedReader(new FileReader(refFile)));

        // read the prediction file
        logger.info("read the prediction file");
        pred = readPredictionFile(predFile);

    } // end constructor

    //
    public RMap extract() {
        RMap relationMap = new RMap();

        for (int i = 0; i < pred.size(); i++) {
            Double p = (Double) pred.get(i);

            Sentence sent = (Sentence) sentenceSet.x(i);
            Object id = sentenceSet.id(i);
            Object y = sentenceSet.y(i);
            String sentID = getSentenceID(id);

            Relation rel = findCandidate(sent);
            rel.setType(p.intValue());
            rel.setTrueType(Integer.parseInt((String) y));

            if (p.intValue() != 0) {
                // logger.debug(i + " " + id + " " + rel);
                relationMap.put(sentID, rel);
            }

        } // end for i

        return relationMap;
    } // end extract

    //
    private String getSentenceID(Object id) {
        String s = (String) id;
        int i = s.indexOf('-');
        return s.substring(0, i);
    } // end getSentenceID

    //
    private Relation findCandidate(Sentence sent) {
        boolean b = true;
        int first = 0, second = 0;

        for (int i = 0; i < sent.length(); i++) {
            if (!sent.wordAt(i).getRole().equals(Word.OTHER_LABEL)) {
                if (b) {
                    first = i;
                } else {
                    second = i;
                    break;
                }
                b = false;
            }
        }

        Word w1 = sent.wordAt(first);
        Word w2 = sent.wordAt(second);
        return new Relation(w1, w2);
    } // end findCandidate

    // read predictions
    private List readPredictionFile(File pred) throws IOException {
        List list = new ArrayList();
        LineNumberReader lr = new LineNumberReader(new FileReader(pred));
        String line = null;

        //
        while ((line = lr.readLine()) != null) {
            // logger.debug(line);
            list.add(new Double(line.trim()));
        } // end while

        lr.close();

        return list;
    } // end readPredictionFile

    //
    private List read(File f) throws IOException {
        logger.debug(f.getName());

        int i = 0;
        List list = new ArrayList();
        LineNumberReader lr = new LineNumberReader(new FileReader(f));
        String line = null;

        while ((line = lr.readLine()) != null) {
            String[] s = line.split("\t");
            // logger.debug((i++) + " " + s[0]);
            list.add(new Double(s[0]));
        }

        return list;
    } // end read

    //
    public String toString() {
        return "";
    } // end toString

    //
    public class Relation {
        //
        private Word w1;

        //
        private Word w2;

        //
        private int type;

        //
        private int trueType;

        //
        public Relation(Word w1, Word w2) {
            this.w1 = w1;
            this.w2 = w2;
        } // end constructor

        //
        public void setTrueType(int type) {
            this.trueType = type;
        } // end setTrueType

        //
        public void setType(int type) {
            this.type = type;
        } // end setType

        //
        public int getType(int type) {
            return type;
        } // end getType

        //
        public String toString() {
            if (type == 1)
                return w1.getForm(false) + " ===> " + w2.getForm(false)
                        + "  (1)";// , true label " + trueType;
            // return w1.getForm(false) + "\t" + w2.getForm(false) + "  (1)";//,
            // true label " + trueType;
            else if (type == 2)
                return w1.getForm(false) + " <=== " + w2.getForm(false)
                        + " (2)";// , true label" + trueType;
            // return w1.getForm(false) + "\t" + w2.getForm(false) + " (2)";//,
            // true label" + trueType;

            return "no relation(" + w1.getForm(false) + "," + w2.getForm(false)
                    + ") 0 (" + trueType + ")";
        } // end toString

    } // end Relation

    //
    public class RMap {
        //
        private HashMap relationMap;

        public RMap() {
            relationMap = new HashMap();
        } // end constructor

        //
        public List get(Object id) {
            return (List) relationMap.get(id);
            // return null;
        } // end get

        //
        public Object put(Object id, Relation rel) {

            List list = (List) relationMap.get(id);

            if (list == null) {
                list = new ArrayList();
                list.add(rel);
                return relationMap.put(id, list);
            }

            list.add(rel);
            return list;

            // return null;
        } // end put

        //
        public Set idSet() {
            return relationMap.keySet();
            // return null;
        } // end idSet

    } // end RMap

//    //
//    public static void main(String args[]) throws Exception {
//        String logConfig = System.getProperty("log-config");
//        if (logConfig == null)
//            logConfig = "log-config.txt";
//
//        PropertyConfigurator.configure(logConfig);
//
//        if (args.length != 2) {
//            System.err
//                    .println("java -mx512M org.itc.irst.tcc.sre.RelationExtractor test-file answer-file");
//            System.exit(0);
//        }
//
//        File ref = new File(args[0]);
//        File pred = new File(args[1]);
//        RelationExtractor extractor = new RelationExtractor(ref, pred);
//        RMap relationMap = extractor.extract();
//
//        int count = 0;
//        Iterator it = relationMap.idSet().iterator();
//        while (it.hasNext()) {
//            Object id = it.next();
//            List list = relationMap.get(id);
//
//            logger.info("\n" + list.size() + " relations found in sentence "
//                    + id);
//            for (int i = 0; i < list.size(); i++) {
//                logger.info((count++) + " " + list.get(i));
//            } // end for i
//        } // end while
//
//    } // end main
} // end class RelationExtractor
