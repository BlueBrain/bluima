/** 
 * OpennlpSentenceDetector.java
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
 * This wrapper provides the interface to the OpenNLP Sentence Detector
 * @see http://opennlp.sourceforge.net/
 **/

package de.julielab.jules.ae.opennlp;

import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.julielab.jules.types.Sentence;

public class SentenceAnnotator extends JCasAnnotator_ImplBase {

	/**
	 * Logger for this class
	 */
    private static Logger LOG = LoggerFactory.getLogger(SentenceAnnotator.class);
	/**
	 * component id for CAS
	 */
	private static final String COMPONENT_ID = "de.julielab.types.OpenNLPSentenceDetector";

	/**
	 * OpenNLP SentenceDetector instance
	 */
	private ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector sentenceSplitter;

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {

		LOG.debug("initializing OpenNLP Sentence Annotator ...");

		super.initialize(aContext);
		String modelFile;
		modelFile = (String) aContext.getConfigParameterValue("modelFile");

		try {
			sentenceSplitter = new ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector(
					modelFile);
		} catch (IOException e) {
			LOG
					.error("[OpenNLP Sentence Annotator] Could not load sentence splitter model: "
							+ e.getMessage());
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void process(JCas aJCas) {
	//LOG.trace(" [OpenNLP Sentence Annotator] processing document ...");
		String text = aJCas.getDocumentText();
		int sentenceOffsets[] = sentenceSplitter.sentPosDetect(text);
		for (int i = 0; i < sentenceOffsets.length; i++) {

		}
		int begin = 0;
		int end = 0;
		for (int i = 0; i < sentenceOffsets.length; i++) {
			end = begin
					+ (text.substring(begin, sentenceOffsets[i]).trim())
							.length();
			Sentence sentence = new Sentence(aJCas, begin, end);
			begin = sentenceOffsets[i];
			sentence.setComponentId(COMPONENT_ID);
			sentence.addToIndexes();

		}
	}

}
