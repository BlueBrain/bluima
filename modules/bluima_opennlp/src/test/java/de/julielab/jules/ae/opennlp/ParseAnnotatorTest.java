/** 
 * OpenNLPParserAnnotatorTest.java
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
 * Test class for OpenNLP Parser Wrapper
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

import de.julielab.jules.types.Constituent;
import de.julielab.jules.types.GENIAConstituent;
import de.julielab.jules.types.PTBConstituent;
import de.julielab.jules.types.PennBioIEConstituent;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class ParseAnnotatorTest {//TODOextends TestCase {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ParseAnnotatorTest.class);

	private static final String LOGGER_PROPERTIES = "src/test/java/log4j.properties";

	String text = "A study on the Prethcamide hydroxylation system in rat hepatic microsomes .";

	String wantedCons = "NP NP PP NP NP PP NP ";

	public void initCas(JCas jcas) {

		jcas.reset();
		jcas.setDocumentText(text);

		Sentence s = new Sentence(jcas);
		s.setBegin(0);
		s.setEnd(text.length());
		s.addToIndexes(jcas);

		String[] tokens = text.split(" ");
		int j = 0;
		for (int i = 0; i < tokens.length; i++) {
			Token token = new Token(jcas);
			token.setBegin(j);
			token.setEnd(j + tokens[i].length());
			j = j + tokens[i].length() + 1;
			token.addToIndexes(jcas);
		}
	}

	public void testProcess() {

		boolean annotationsOK = true;

		XMLInputSource parserXML = null;
		ResourceSpecifier parserSpec = null;
		AnalysisEngine parserAnnotator = null;

		try {
			parserXML = new XMLInputSource(
					"src/test/resources/ParseAnnotatorTest.xml");
			parserSpec = UIMAFramework.getXMLParser().parseResourceSpecifier(
					parserXML);
			parserAnnotator = UIMAFramework.produceAnalysisEngine(parserSpec);
		} catch (Exception e) {
			LOGGER.error("[testProcess]" + e.getMessage());
			e.printStackTrace();
		}

		JCas jcas = null;
		try {
			jcas = parserAnnotator.newJCas();
		} catch (ResourceInitializationException e) {
			LOGGER.error("[testProcess]" + e.getMessage());
			e.printStackTrace();
		}

		// get test cas with sentence/token/pos annotation
		initCas(jcas);

		try {
			parserAnnotator.process(jcas, null);
		} catch (Exception e) {
			LOGGER.error("[testProcess]" + e.getMessage());
			e.printStackTrace();
		}

		// get the offsets of the sentences
		JFSIndexRepository indexes = jcas.getJFSIndexRepository();
		Iterator consIter = indexes.getAnnotationIndex(Constituent.type)
				.iterator();

		StringBuffer predictedCons = new StringBuffer();
		while (consIter.hasNext()) {
			String label = null;
			Constituent cons = (Constituent) consIter.next();
			Class c = cons.getClass();
			if (c.equals(GENIAConstituent.class))
				label = ((GENIAConstituent) cons).getCat();
			if (c.equals(PennBioIEConstituent.class))
				label = ((PennBioIEConstituent) cons).getCat();
			if (c.equals(PTBConstituent.class))
				label = ((PTBConstituent) cons).getCat();

			predictedCons = predictedCons.append(label + " ");

		}

		if (wantedCons.equals(predictedCons.toString()))
			TestCase.assertTrue(annotationsOK);
		LOGGER.debug("[testProcess: ]wantedCons: " + wantedCons + "\n"
				+ "predictedCons: " + predictedCons);

	}

}
