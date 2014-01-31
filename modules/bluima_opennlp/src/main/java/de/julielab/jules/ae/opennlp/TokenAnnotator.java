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

package de.julielab.jules.ae.opennlp;

import java.io.IOException;


import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.shaded.opennlp.tools.util.Span;

import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

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
	
	/**
	 * instance of the OpenNLP Tokenizer
	 */
	private ch.epfl.bbp.shaded.opennlp.tools.lang.english.Tokenizer tokenizer;
	
	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {

		LOGGER.info("[OpenNLP Token Annotator] initializing OpenNLP Token Annotator ...");
		super.initialize(aContext);
		String modelFile;
		modelFile = (String) aContext.getConfigParameterValue("modelFile");

		try {
			tokenizer = new ch.epfl.bbp.shaded.opennlp.tools.lang.english.Tokenizer(modelFile);
		} catch (IOException e) {
			LOGGER.error("[OpenNLP Token Annotator] Could not load tokenizer model: "
							+ e.getMessage());
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void process(JCas aJCas) {

		//LOGGER.info ("[OpenNLP Token Annotator] processing document ...");
		AnnotationIndex sentenceIndex = (AnnotationIndex) aJCas
				.getJFSIndexRepository().getAnnotationIndex(Sentence.type);

		FSIterator sentenceIterator = sentenceIndex.iterator();
		while (sentenceIterator.hasNext()) {
			Sentence sentence = (Sentence) sentenceIterator.next();

			String text = sentence.getCoveredText();
			Span[] tokenSpans = tokenizer.tokenizePos(text);
			for (int i = 0; i < tokenSpans.length; i++) {
				Span span = tokenSpans[i];
				Token token = new Token(aJCas);
				token.setBegin(sentence.getBegin() + span.getStart());
				token.setEnd(sentence.getBegin() + span.getEnd());
				token.setComponentId(COMPONENT_ID);
				token.addToIndexes();
			}
		}

	}
}
