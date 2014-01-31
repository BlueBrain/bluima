/** 
 * OpenNLPTokenizerAnnotatorTest.java
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
 * /Test for OpenNLP Token Annotator
 **/

package de.julielab.jules.ae.opennlp;

import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class TokenAnnotatorTest {//TODO extends TestCase {

	private static final Logger LOGGER = LoggerFactory
	.getLogger(TokenAnnotatorTest.class);
	
	private static final String LOGGER_PROPERTIES = "src/test/java/log4j.properties";
	
	String offsets = "0-4;4-5;6-8;9-12;13-18;18-19;20-22;23-24;25-28" ;

	public void initCas(JCas jcas) {
		jcas.reset();
		jcas.setDocumentText("CD44, at any stage, is a XYZ");
		Sentence s1 = new Sentence(jcas);
		s1.setBegin(0);
		s1.setEnd(28);
		s1.addToIndexes();
	}
	
	public void testProcess() {

		boolean annotationsOK = true;

		XMLInputSource tokenXML = null;
		ResourceSpecifier tokenSpec = null;
		AnalysisEngine tokenAnnotator = null;

		try {
			tokenXML = new XMLInputSource(
					"src/test/resources/TokenAnnotatorTest.xml");
			tokenSpec = UIMAFramework.getXMLParser().parseResourceSpecifier(
					tokenXML);
			tokenAnnotator = UIMAFramework
					.produceAnalysisEngine(tokenSpec);
		} catch (Exception e) {
			LOGGER.error("testProcess()"+ e.getMessage());
		}


			JCas jcas = null;
			try {
				jcas = tokenAnnotator.newJCas();
			} catch (ResourceInitializationException e) {
				LOGGER.error("testProcess()" + e.getMessage());
			}

			// get test cas with sentence annotation
			initCas(jcas);
			
			try {
				tokenAnnotator.process(jcas, null);
			} catch (Exception e) {
				LOGGER.error("testProcess()" + e.getMessage());
			}

			// get the offsets of the sentences
			JFSIndexRepository indexes = jcas.getJFSIndexRepository();
			Iterator tokIter = indexes.getAnnotationIndex(Token.type)
					.iterator();

			String predictedOffsets = "";

			while (tokIter.hasNext()) {
				Token t = (Token) tokIter.next();
				System.out.println("OUT: " + t.getCoveredText() + ": " + t.getBegin()
						+ " - " + t.getEnd());
				predictedOffsets += (predictedOffsets.length() > 0) ? ";" : "";
				predictedOffsets += t.getBegin() + "-" + t.getEnd();
			}

			LOGGER.debug("\npredicted: " + predictedOffsets);
			LOGGER.debug("   wanted: " + offsets);

			// compare offsets
			if (!predictedOffsets.equals(offsets)) {
				annotationsOK = false;
			}


	TestCase.	assertTrue(annotationsOK);

	}
}
