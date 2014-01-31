/** 
 * OpennlpChunkerTest.java
 * 
 * Copyright (c) 2007, JULIE Lab. 
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
 * /Test for OpenNLP Chunker
 **/

package de.julielab.jules.ae.opennlp;

import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.julielab.jules.types.Chunk;
import de.julielab.jules.types.PennBioIEPOSTag;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class ChunkAnnotatorTest {// extends TestCase {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ChunkAnnotatorTest.class);

	private static final String LOGGER_PROPERTIES = "src/test/java/log4j.properties";

	String text = "A study on the Prethcamide hydroxylation system in rat hepatic microsomes .";

	String pos_tags = "DT NN IN DT NN NN NN IN NN JJ NNS .";

	String chunks = "ChunkNP,ChunkPP,ChunkNP,ChunkPP,ChunkNP,";

	private void initCas(JCas jcas) {

		jcas.reset();
		jcas.setDocumentText(text);

		Sentence s = new Sentence(jcas);
		s.setBegin(0);
		s.setEnd(text.length());
		s.addToIndexes(jcas);

		String[] tokens = text.split(" ");
		String[] pos = pos_tags.split(" ");
		int j = 0;
		for (int i = 0; i < tokens.length; i++) {
			Token token = new Token(jcas);
			token.setBegin(j);
			token.setEnd(j + tokens[i].length());
			j = j + tokens[i].length() + 1;
			token.addToIndexes(jcas);
			PennBioIEPOSTag posTag = new PennBioIEPOSTag(jcas);
			posTag.setValue(pos[i]);
			posTag.addToIndexes(jcas);
			FSArray postags = new FSArray(jcas, 10);
			postags.set(0, posTag);
			postags.addToIndexes(jcas);
			token.setPosTag(postags);
		}
	}

	public void testProcess() {

		boolean annotationsOK = true;

		XMLInputSource chunkerXML = null;
		ResourceSpecifier chunkerSpec = null;
		AnalysisEngine chunkerAnnotator = null;

		try {
			chunkerXML = new XMLInputSource(
					"src/test/resources/ChunkAnnotatorTest.xml");
			chunkerSpec = UIMAFramework.getXMLParser().parseResourceSpecifier(
					chunkerXML);
			chunkerAnnotator = UIMAFramework.produceAnalysisEngine(chunkerSpec);
		} catch (Exception e) {

			LOGGER.error("[testProcess: ] " + e.getMessage());
			e.printStackTrace();
		}

		JCas jcas = null;
		try {
			jcas = chunkerAnnotator.newJCas();
		} catch (ResourceInitializationException e) {
			LOGGER.error("[testProcess: ] " + e.getMessage());
			e.printStackTrace();
		}

		// get test cas with sentence/token/pos annotation
		initCas(jcas);

		try {
			chunkerAnnotator.process(jcas, null);
		} catch (Exception e) {
			LOGGER.error("[testProcess: ] " + e.getMessage());
			e.printStackTrace();
		}

		// get the offsets of the sentences
		JFSIndexRepository indexes = jcas.getJFSIndexRepository();
		Iterator chunkIter = indexes.getAnnotationIndex(Chunk.type)
				.iterator();

		String predictedChunks = "";

		while (chunkIter.hasNext()) {
			Chunk t = (Chunk) chunkIter.next();

			predictedChunks = predictedChunks + t.getType().getShortName()
					+ ",";

		}
		LOGGER.debug("[testProcess: ] Wanted:" + chunks + "\nPredicted:"
				+ predictedChunks);

		// compare offsets
		if (!predictedChunks.equals(chunks)) {
			annotationsOK = false;
		}

		TestCase.assertTrue(annotationsOK);
	}

}
