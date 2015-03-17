/** 
 * OpennlpTokenizer.java
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
 * Analysis Engine that uses the OpenNLP Tokenizer.  This engine assumes
 * that sentences have been annotated in the CAS. 
 * It iterates over sentences and invoke the OpenNLP Tokenizer on each sentence. 
 **/

package ch.epfl.bbp.uima.ae;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.nlp.ModelProxy;
import ch.epfl.bbp.nlp.ModelProxyException;
import ch.epfl.bbp.nlp.ModelStream;
import ch.epfl.bbp.shaded.opennlp.tools.util.Span;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ScriptingShortcut;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

@ScriptingShortcut(shortcut = "ch.epfl.bbp.uima.ae.OpenNlpHelper.getTokenizer()")
@TypeCapability(inputs = { TypeSystem.SENTENCE }, outputs = { TypeSystem.TOKEN })
public class TokenAnnotator extends JCasAnnotator_ImplBase {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(TokenAnnotator.class);

    /**
     * component Id
     */
    public static final String COMPONENT_ID = "de.julielab.jules.OpenNLPTokenizer";

    @ConfigurationParameter(name = BlueUima.PARAM_MODEL)
    private String model;

    /**
     * instance of the OpenNLP Tokenizer
     */
    private ch.epfl.bbp.shaded.opennlp.tools.lang.english.Tokenizer tokenizer;

    @Override
    public void initialize(UimaContext aContext)
            throws ResourceInitializationException {
        super.initialize(aContext);

        try {
            ModelStream modelStream = ModelProxy.getStream(model);
            tokenizer = new ch.epfl.bbp.shaded.opennlp.tools.lang.english.Tokenizer(
                    modelStream);
        } catch (ModelProxyException | IOException e) {
            LOGGER.error("[OpenNLP Token Annotator] Could not load tokenizer model: "
                    + e.getMessage());
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) {

        BlueCasUtil.fixNoText(jCas);
        BlueCasUtil.fixNoSentences(jCas);

        for (Sentence sentence : select(jCas, Sentence.class)) {

            String text = sentence.getCoveredText();
            Span[] tokenSpans = tokenizer.tokenizePos(text);
            for (int i = 0; i < tokenSpans.length; i++) {
                Span span = tokenSpans[i];
                Token token = new Token(jCas);
                token.setBegin(sentence.getBegin() + span.getStart());
                token.setEnd(sentence.getBegin() + span.getEnd());
                token.setComponentId(COMPONENT_ID);
                token.addToIndexes();
            }
        }
    }
}
