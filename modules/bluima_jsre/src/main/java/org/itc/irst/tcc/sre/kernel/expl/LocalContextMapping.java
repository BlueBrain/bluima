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
package org.itc.irst.tcc.sre.kernel.expl;

import java.util.Properties;

import org.itc.irst.tcc.sre.data.Sentence;
import org.itc.irst.tcc.sre.data.Word;
import org.itc.irst.tcc.sre.data.context.AgentContext;
import org.itc.irst.tcc.sre.data.context.SecondTargetContext;
import org.itc.irst.tcc.sre.data.context.TargetContext;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.Orthographic;
import org.itc.irst.tcc.sre.util.SparseVector;
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
public class LocalContextMapping extends AbstractMapping implements
        ContextMapping {

    static Logger logger = LoggerFactory.getLogger(LocalContextMapping.class
            .getName());

    //
    // private int relationType;

    //
    private int windowSize;

    //
    private static final int NUMBER_OF_SUBSPACES = 2;
    // private static final int NUMBER_OF_SUBSPACES = 4;

    //
    private static final int TARGET_SPACE = 0;

    //
    private static final int AGENT_SPACE = 1;

    //
    public static final int DEFAULT_WINDOW_SIZE = 2;

    //
    private TargetContext targetContext;

    //
    private SecondTargetContext secondTargetContext;

    //
    private AgentContext agentContext;

    //

    public LocalContextMapping() {
        logger.debug("LocalContextMapping");

        // super(NUMBER_OF_SUBSPACES);
        // this.relationType = relationType;

        windowSize = DEFAULT_WINDOW_SIZE;

        targetContext = TargetContext.getInstance();
        secondTargetContext = SecondTargetContext.getInstance();
        agentContext = AgentContext.getInstance();

        // logger.debug("LocalContextMapping");
        // logger.debug("windowSize: " + windowSize);
        // logger.debug("relationType: " + relationType);

    } // end constructor

    //
    public void setParameters(Properties parameters) {
        logger.debug("LocalContextMapping.setParameters");
        String s = parameters.getProperty("window-size");
        if (s != null)
            windowSize = Integer.parseInt(s);

        targetContext.setSize(windowSize);
        secondTargetContext.setSize(windowSize);
        agentContext.setSize(windowSize);

        logger.debug("windowSize: " + windowSize);

    } // end setParameters

    //
    public int subspaceCount() {
        return NUMBER_OF_SUBSPACES;
    }

    public Vector[] map(Object x, Object id, FeatureIndex[] index)
            throws IllegalArgumentException {
        // logger.debug("LocalContextMapping.map");

        if (!(x instanceof Sentence))
            throw new IllegalArgumentException();
        Sentence sent = (Sentence) x;
        Vector[] subspaces = new SparseVector[NUMBER_OF_SUBSPACES];

        // local context
        Sentence target = null, agent = null;
        // Word wt = null, wa = null;

        target = targetContext.filter(sent);
        // wt = targetContext.word(sent);

        Sentence secondTarget = secondTargetContext.filter(sent);
        // Word wst = secondTargetContext.word(sent);

        if (secondTarget == null) {
            agent = agentContext.filter(sent);
            // wa = agentContext.word(sent);
        } else {
            agent = secondTarget;
            // wa = wst;
        }

        // logger.info("\ntarget: " + wa.getType());
        // logger.info("agent(second target): " + wt.getType());
        /*
         * if (wt.getType().equals("LOC")) { subspaces[TARGET_SPACE] =
         * createSubspace(target, index[TARGET_SPACE]); subspaces[AGENT_SPACE] =
         * createSubspace(agent, index[AGENT_SPACE]); } else {
         * subspaces[AGENT_SPACE] = createSubspace(target, index[TARGET_SPACE]);
         * subspaces[TARGET_SPACE] = createSubspace(agent, index[AGENT_SPACE]);
         * } subspaces[2] = createSubspace(target, index[TARGET_SPACE]);
         * subspaces[3] = createSubspace(agent, index[AGENT_SPACE]);
         */

        subspaces[TARGET_SPACE] = createSubspace(target, index[TARGET_SPACE]);
        subspaces[AGENT_SPACE] = createSubspace(agent, index[AGENT_SPACE]);
        /*
         * subspaces[TARGET_SPACE] = new SparseVector();
         * createSubspace(subspaces[TARGET_SPACE], target, index[TARGET_SPACE],
         * "target"); createSubspace(subspaces[TARGET_SPACE], agent,
         * index[TARGET_SPACE], "agent");
         * //createSubspace(subspaces[TARGET_SPACE], target, agent,
         * index[TARGET_SPACE]);
         */
        // normalize
        for (int j = 0; j < NUMBER_OF_SUBSPACES; j++)
            subspaces[j].normalize();

        //
        return subspaces;
    } // end map

    //
    protected Vector createSubspace(Sentence sent, FeatureIndex index) {
        Vector vec = new SparseVector();
        // logger.debug("sent: " + sent.length());
        //
        for (int i = 0; i < sent.length(); i++) {
            // TO DO: provare ad eliminare l'entita' dalle feature!

            // String lemma = sent.wordAt(i).getLemma();
            String form = sent.wordAt(i).getForm(false);
            String stem = sent.wordAt(i).getStem();
            String pos = sent.wordAt(i).getPos();
            // String type = sent.wordAt(i).getType();

            int offset = i - findTargetOffset(sent);
            /*
             * // ACE2005 String attr = sent.wordAt(i).getLemma(); String[] s =
             * attr.split("_"); if (s.length >= 3) { // head //addFeature(s[0] +
             * "_HEAD", offset, vec, index);
             * 
             * // addFeature(s[1] + "_MENTION_TYPE", offset, vec, index);
             * 
             * // addFeature(s[2] + "_ENTITY_TYPE", offset, vec, index);
             * 
             * // addFeature(s[3] + "_ENTITY_SUBTYPE", offset, vec, index);
             * 
             * }
             */
            // type: USED FOR SEMEVAL-RE
            // addFeature(type + Orthographic.TYPE, offset, vec, index);

            // lemma
            // addFeature(lemma + Orthographic.LEMMA, offset, vec, index);

            // Used in ACE2005, AImed, LLL challenge and Roth

            // word form
            addFeature(form + Orthographic.WORD_FORM, offset, vec, index);

            // word stem
            addFeature(stem + Orthographic.STEM, offset, vec, index);

            // pos
            addFeature(pos + Orthographic.PART_OF_SPEECH, offset, vec, index);

            // uppercase
            if (Orthographic.isUpperCase(form))
                // addFeature(form + Orthographic.form, offset, vec, index);
                addFeature(Orthographic.UPPER_CASE, offset, vec, index);

            // lowercase
            if (Orthographic.isLowerCase(form))
                // addFeature(form + Orthographic.UPPER_CASE, offset, vec,
                // index);
                addFeature(Orthographic.LOWER_CASE, offset, vec, index);

            // punctuation
            if (Orthographic.isPunctuation(form))
                // addFeature(form + Orthographic.PUNCTUATION, offset, vec,
                // index);
                addFeature(Orthographic.PUNCTUATION, offset, vec, index);

            // capitalized
            if (Orthographic.isCapitalized(form))
                // addFeature(form + Orthographic.CAPITALIZED, vec, index);
                addFeature(Orthographic.CAPITALIZED, offset, vec, index);

            // numeric
            if (Orthographic.isNumeric(form))
                // addFeature(form + Orthographic.NUMERIC, vec, index);
                addFeature(Orthographic.NUMERIC, offset, vec, index);

            // prefix
            // addFeature("", vec, index);

        } // end for i

        return vec;
    } // end createSubspace

    //
    protected void addFeature(String feat, int i, Vector vec, FeatureIndex index) {
        // logger.info("feat: " + feat);
        String f = feat;
        if (i > 0)
            f = feat + "+" + i;
        else if (i < 0)
            f = feat + i;

        int j = index.put(f);

        if (j != -1)
            vec.add(j, 1);

        // logger.debug("added feat: " + f);
    } // end addFeature

    //
    protected int findTargetOffset(Sentence sent) {
        for (int i = 0; i < sent.length(); i++) {
            if (sent.wordAt(i).getRole().equals(Word.TARGET_LABEL)
                    || sent.wordAt(i).getRole().equals(Word.AGENT_LABEL))
                return i;
        } // end for i

        return -1;
    } // end findTargetOffset

    //
    public String toString() {
        return "LocalContextMapping";
    } // end toString

    // /
    //
    protected void createSubspace(Vector vec, Sentence sent,
            FeatureIndex index, String prefix) {
        // Vector vec = new SparseVector();
        // logger.debug("sent: " + sent.length());
        //
        for (int i = 0; i < sent.length(); i++) {
            // TO DO: provare ad eliminare l'entita' dalle feature!

            // String lemma = sent.wordAt(i).getLemma();
            String form = sent.wordAt(i).getForm(false);
            String stem = sent.wordAt(i).getStem();
            String pos = sent.wordAt(i).getPos();
            // String type = sent.wordAt(i).getType();

            int offset = i - findTargetOffset(sent);
            // type: USED FOR SEMEVAL-RE
            // addFeature(type + Orthographic.TYPE, offset, vec, index);

            // lemma
            // addFeature(lemma + Orthographic.LEMMA, offset, vec, index);

            // Used in ACE2005, AImed, LLL challenge and Roth

            // word form
            addFeature(prefix + "_" + form + Orthographic.WORD_FORM, offset,
                    vec, index);

            // word stem
            addFeature(prefix + "_" + stem + Orthographic.STEM, offset, vec,
                    index);

            // pos
            addFeature(prefix + "_" + pos + Orthographic.PART_OF_SPEECH,
                    offset, vec, index);

            // uppercase
            if (Orthographic.isUpperCase(form))
                // addFeature(form + Orthographic.form, offset, vec, index);
                addFeature(prefix + "_" + Orthographic.UPPER_CASE, offset, vec,
                        index);

            // lowercase
            if (Orthographic.isLowerCase(form))
                // addFeature(form + Orthographic.UPPER_CASE, offset, vec,
                // index);
                addFeature(prefix + "_" + Orthographic.LOWER_CASE, offset, vec,
                        index);

            // punctuation
            if (Orthographic.isPunctuation(form))
                // addFeature(form + Orthographic.PUNCTUATION, offset, vec,
                // index);
                addFeature(prefix + "_" + Orthographic.PUNCTUATION, offset,
                        vec, index);

            // capitalized
            if (Orthographic.isCapitalized(form))
                // addFeature(form + Orthographic.CAPITALIZED, vec, index);
                addFeature(prefix + "_" + Orthographic.CAPITALIZED, offset,
                        vec, index);

            // numeric
            if (Orthographic.isNumeric(form))
                // addFeature(form + Orthographic.NUMERIC, vec, index);
                addFeature(prefix + "_" + Orthographic.NUMERIC, offset, vec,
                        index);

            // prefix
            // addFeature("", vec, index);

        }
        // return vec;
    }

    protected void createSubspace(Vector vec, Sentence st, Sentence sa,
            FeatureIndex index) {
        // Vector vec = new SparseVector();
        // logger.debug("sent: " + sent.length());
        //
        for (int i = 0; i < st.length(); i++) {

            int offset = i - findTargetOffset(st);
            for (int j = 0; j < sa.length(); j++) { // String lemma =
                                                    // sent.wordAt(i).getLemma();
                String form = st.wordAt(i).getForm(false) + "_"
                        + sa.wordAt(j).getForm(false);
                String stem = st.wordAt(i).getStem() + "_"
                        + sa.wordAt(j).getStem();
                String pos = st.wordAt(i).getPos() + "_"
                        + sa.wordAt(j).getPos();

                // word form
                addFeature(form + Orthographic.WORD_FORM, 0, vec, index);

                // word stem
                addFeature(stem + Orthographic.STEM, 0, vec, index);

                // pos
                addFeature(pos + Orthographic.PART_OF_SPEECH, 0, vec, index);
            }
        }
        // return vec;
    }
}
