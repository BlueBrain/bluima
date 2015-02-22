/** 
 * OpenNLPSentenceDetectorTest.java
 * 
 * Copyright (c) 2006, JULIE Lab. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0 
 *
 * Author: buyko
 * 
 * Current version: 2.0
 * Since version:   1.0
 *
 * Creation date: 30.01.2008 
 * 
 * Test for OpenNLP Sentence Annotator
 **/

package de.julielab.jules.ae.opennlp;

import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.shaded.opennlp.tools.util.Pair;
import ch.epfl.bbp.uima.ae.OpenNlpHelper;
import ch.epfl.bbp.uima.testutils.UimaTests;
import de.julielab.jules.types.Sentence;

public class SentenceAnnotatorTest {

    String text = "First sentence. Second sentence!";
    Pair offsets[] = { new Pair(0, 15), new Pair(16, 32) };

    @Test
    public void test() throws UIMAException {
        // Run the splitter annotator on `text`
        JCas jCas = UimaTests.getTestCas(text);
        runPipeline(jCas, OpenNlpHelper.getSentenceSplitter());

        Collection<Sentence> collection = JCasUtil.select(jCas, Sentence.class);
        Sentence sentences[] = new Sentence[collection.size()];
        collection.toArray(sentences);

        // Make sure the number of sentences and their offsets are corrects
        assertEquals(sentences.length, offsets.length);
        for (int i = 0; i < offsets.length; ++i) {
            Sentence sentence = sentences[i];
            Pair offset = offsets[i];

            assertEquals(sentence.getBegin(), offset.a);
            assertEquals(sentence.getEnd(), offset.b);
        }
    }
}
