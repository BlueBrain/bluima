/** 
 * OpenNLPParser.java
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
 * Wrapper for the OpenNLP Parser. Constituency-based parsing.
 **/

package de.julielab.jules.ae.opennlp;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;


import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.annotator.AnnotatorConfigurationException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.shaded.opennlp.tools.lang.english.TreebankParser;
import ch.epfl.bbp.shaded.opennlp.tools.parser.Parse;
import ch.epfl.bbp.shaded.opennlp.tools.parser.ParserME;
import ch.epfl.bbp.shaded.opennlp.tools.util.Span;

import de.julielab.jules.types.Constituent;
import de.julielab.jules.types.GENIAConstituent;
import de.julielab.jules.types.PTBConstituent;
import de.julielab.jules.types.PennBioIEConstituent;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;
import de.julielab.jules.utility.AnnotationTools;

public class ParseAnnotator extends JCasAnnotator_ImplBase {

	/**
	 * Logger for this class
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParseAnnotator.class);

	/**
	 * component id for CAS
	 */
	private static final String COMPONENT_ID = "de.julielab.jules.ae.OpenNLPParser";

	/**
	 * Instance of the Opennlp Parser
	 * 
	 */
	private ParserME parser;

	/**
	 * Directory containing parser models
	 */
	private String model;

	/**
	 * TagSet (e.c. PennConsituent,GeniaCOnstituent)
	 */
	private String tagSet;

	/**
	 * True if dictionary should be used
	 */
	private boolean useTagdict;

	/**
	 * True if dictionary is case sensitive
	 */
	private boolean caseSensitive;

	/**
	 * Beam size
	 */
	private int beamSize = ParserME.defaultBeamSize;

	/**
	 * Amount of probability mass required of advanced outcomes
	 */
	private double advancePercentage = ParserME.defaultAdvancePercentage;

	/**
	 * True if parsing with functional tags (e.g. subj, obj)
	 */
	private boolean fun;

	/**
	 * Mappings between CAS constitunet tags and OpenNLP Parser tags
	 */
	private String[] mappings;

	/**
	 * characters to be escaped
	 */
	private Hashtable<String, String> escMap = new Hashtable<String, String>();

	/**
	 * offset maping between CAS indexes and OpenNLP Parser indexes
	 */
	private OffsetMapping offsetMap = new OffsetMapping();

	/**
	 * mapping table between OpenNLP cons. tags and CAS tags
	 */
	private Hashtable<String, String> mapTable = new Hashtable<String, String>();

	/**
	 * fun tags mapping table, for examle ADV is a funFormDisc
	 */
	private Hashtable<String, String> funTable = new Hashtable<String, String>();

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {

		System.out.println("initializing OpenNLP Parse Annotator ...");
		super.initialize(aContext);

		/**
		 * Initialize parameters
		 * 
		 */
		model = (String) aContext.getConfigParameterValue("modelDir");
		tagSet = (String) aContext.getConfigParameterValue("tagset");
		useTagdict = ((Boolean) aContext.getConfigParameterValue("useTagdict"))
				.booleanValue();
		caseSensitive = ((Boolean) aContext
				.getConfigParameterValue("caseSensitive")).booleanValue();

		fun = ((Boolean) aContext.getConfigParameterValue("fun"))
				.booleanValue();

		mappings = (String[]) aContext.getConfigParameterValue("mappings");

		loadMappings();

		if (aContext.getConfigParameterValue("beamSize") != null)
			beamSize = ((Integer) aContext.getConfigParameterValue("beamSize"))
					.intValue();

		if (aContext.getConfigParameterValue("advancePercentage") != null)
			advancePercentage = new Double((String) aContext
					.getConfigParameterValue("advancePercentage"))
					.doubleValue();

		initBracketMap();
		initFunMap();

		// Initialize Parser
		try {
			parser = TreebankParser.getParser(model, useTagdict, caseSensitive,
					beamSize, advancePercentage);
		} catch (IOException e) {
			LOGGER.error("[OpenNLP Parser] Could not load Parser models: "
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {

		//LOGGER.info("[OpenNLP Parser]: processing  document...");
		ArrayList<Token> tokenList = new ArrayList<Token>();
		ArrayList<String> tokenTextList = new ArrayList();
		ArrayList<String> tagTextList = new ArrayList<String>();

		AnnotationIndex sentenceIndex = (AnnotationIndex) aJCas
				.getJFSIndexRepository().getAnnotationIndex(Sentence.type);
		AnnotationIndex tokenIndex = (AnnotationIndex) aJCas
				.getJFSIndexRepository().getAnnotationIndex(Token.type);

		FSIterator sentenceIterator = sentenceIndex.iterator();
		StringBuffer senBuf = new StringBuffer();
		// Iterate over Sentences
		while (sentenceIterator.hasNext()) {
			senBuf.setLength(0);
			tokenList.clear();
			tokenTextList.clear();
			tagTextList.clear();
			Sentence sentence = (Sentence) sentenceIterator.next();

			// index for mapping of spans
			int mapId = 0;

			// Iterate over Tokens
			FSIterator tokenIterator = tokenIndex.subiterator(sentence);
			while (tokenIterator.hasNext()) {
				Token token = (Token) tokenIterator.next();
				tokenList.add(token);
				String tokenText = escapeToken(token.getCoveredText());

				// Mapping of offsets
				int start = senBuf.length();
				int end = start + tokenText.length();
				int origId = token.getBegin();
				for (mapId = start; mapId <= end; mapId++) {
					offsetMap.putMapping(mapId, origId);
					if (origId < token.getEnd())
						origId++;
				}

				senBuf.append(tokenText + " ");
				tokenTextList.add(tokenText);
			}

			// Concatenate CAS-Tokens in line for the Parser-Input
			StringBuffer line = new StringBuffer();
			Iterator i = tokenTextList.iterator();
			while (i.hasNext())
				line.append((String) (i.next() + " "));

			String line2parse = line.substring(0, line.length() - 1);

			// Create new Parse
			Parse parse = new Parse(line2parse,
					new Span(0, line2parse.length()), "INC", 1, null);

			int tokenStart = 0;
			int tokenEnd = 0;
			Iterator tokenIt = tokenTextList.iterator();

			// insert Tokens in Parse
			while (tokenIt.hasNext()) {
				String tok = (String) tokenIt.next();
				tokenEnd = tokenStart + tok.length();
				parse.insert(new Parse(line2parse, new Span(tokenStart,
						tokenEnd), ParserME.TOK_NODE, 0));
				tokenStart = tokenEnd + 1;
			}

			// start Parsing
			parse = parser.parse(parse);
			try {
				annotate(parse, aJCas, null);
			} catch (SecurityException e) {
				LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
				e.printStackTrace();
			} catch (InstantiationException e) {
				LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
				e.printStackTrace();
			}

		}

	}

	/**
	 * load mappings of constituent names
	 * 
	 */
	private void loadMappings() {

		for (int i = 0; i < mappings.length; i++) {

			String[] pair = mappings[i].split(";");
			if (pair.length < 2) {
				try {
					throw new AnnotatorConfigurationException();
				} catch (AnnotatorConfigurationException e) {
					LOGGER.error("[OpenNLP Parser: ]" + e.getMessage());
					e.printStackTrace();
				}
			} else {
				String consTag = pair[0];
				String casTag = pair[1];
				mapTable.put(consTag, casTag);
			}
		}

	}

	/**
	 * provide annotations from Parse result to CAS object
	 */
	private void annotate(Parse parse, JCas cas, Constituent parent)
			throws SecurityException, IllegalArgumentException,
			ClassNotFoundException, NoSuchMethodException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException {
		Constituent cons = null;
		Span span = parse.getSpan();
		String label = parse.getType();
		String labelCat;
		String[] funCatlabels = null;

		if (!label.equals(ParserME.TOK_NODE)) {
			int start = offsetMap.getMapping(span.getStart());
			int end = offsetMap.getMapping(span.getEnd());

			if (fun == true && label.contains("-")) {
				funCatlabels = label.split("-");
				labelCat = funCatlabels[0];

			} else
				labelCat = label;

			String casLabel = mapTable.get(labelCat);
			// if there is mapping between OpenNLP Parser constituent label and
			// CAS label

			if (casLabel != null) {
				cons = (Constituent) AnnotationTools.getAnnotationByClassName(
						cas, tagSet);

				cons.setBegin(start);
				cons.setEnd(end);
				cons.setComponentId(COMPONENT_ID);
				cons.setCat(casLabel);
				Class c = cons.getClass();

				makeFunAnnotations(cons, funCatlabels);

				if (parent != null)
					cons.setParent(parent);

				cons.addToIndexes();
			}

			Parse[] children = parse.getChildren();

			for (int i = 0; i < children.length; i++) {
				annotate(children[i], cas, cons);
			}

		}

	}

	/**
	 * make Fun annotations
	 */
	private void makeFunAnnotations(Constituent cons, String[] funCatLabels) {

		if (funCatLabels != null && funCatLabels.length > 1) {
			String labeltype;
			for (int i = 1; i < funCatLabels.length; i++) {
				labeltype = funTable.get(funCatLabels[i]);
				if (labeltype != null) {
					Class c = cons.getClass();
					if (labeltype.equals("formFuncDisc")) {
						if (c.equals(GENIAConstituent.class))
							((GENIAConstituent) cons)
									.setFormFuncDisc(funCatLabels[i]);
						if (c.equals(PennBioIEConstituent.class))
							((PennBioIEConstituent) cons)
									.setFormFuncDisc(funCatLabels[i]);
						if (c.equals(PTBConstituent.class))
							((PTBConstituent) cons)
									.setFormFuncDisc(funCatLabels[i]);
					}

					if (labeltype.equals("gramRole")) {
						if (c.equals(GENIAConstituent.class))
							((GENIAConstituent) cons)
									.setGramRole(funCatLabels[i]);
						if (c.equals(PennBioIEConstituent.class))
							((PennBioIEConstituent) cons)
									.setGramRole(funCatLabels[i]);
						if (c.equals(PTBConstituent.class))
							((PTBConstituent) cons)
									.setGramRole(funCatLabels[i]);
					}

					if (labeltype.equals("adv")) {
						if (c.equals(GENIAConstituent.class))
							((GENIAConstituent) cons).setAdv(funCatLabels[i]);
						if (c.equals(PennBioIEConstituent.class))
							((PennBioIEConstituent) cons)
									.setAdv(funCatLabels[i]);
						if (c.equals(PTBConstituent.class))
							((PTBConstituent) cons).setAdv(funCatLabels[i]);
					}

					if (labeltype.equals("misc")) {
						if (c.equals(GENIAConstituent.class))
							((GENIAConstituent) cons).setMisc(funCatLabels[i]);
						if (c.equals(PennBioIEConstituent.class))
							((PennBioIEConstituent) cons)
									.setMisc(funCatLabels[i]);
						if (c.equals(PTBConstituent.class))
							((PTBConstituent) cons).setMisc(funCatLabels[i]);
					}

					if (labeltype.equals("syn")) {
						if (c.equals(GENIAConstituent.class))
							((GENIAConstituent) cons).setSyn(funCatLabels[i]);

					}

				}
			}
		}

	}

	/**
	 * init table with charactets to be escaped
	 * 
	 */
	private void initBracketMap() {
		escMap.put("(", "-LRB-");
		escMap.put(")", "-RRB-");
		escMap.put("{", "-LCB-");
		escMap.put("}", "-RCB-");
		escMap.put("[", "-LSB-");
		escMap.put("]", "-RSB-");
	}

	/**
	 * init table wit fun tags and corresponfing attributes of a Constituent
	 * 
	 */
	private void initFunMap() {
		funTable.put("ADV", "formFuncDisc");
		funTable.put("NOM", "formFuncDisc");

		funTable.put("DTV", "gramRole");
		funTable.put("LGS", "gramRole");
		funTable.put("PRD", "gramRole");
		funTable.put("PUT", "gramRole");
		funTable.put("SBJ", "gramRole");
		funTable.put("VOC", "gramRole");

		funTable.put("BNF", "adv");
		funTable.put("DIR", "adv");
		funTable.put("EXT", "adv");
		funTable.put("LOC", "adv");
		funTable.put("MNR", "adv");
		funTable.put("PRP", "adv");
		funTable.put("TMP", "adv");

		funTable.put("CLR", "misc");
		funTable.put("CLF", "misc");
		funTable.put("HLN", "misc");
		funTable.put("TTL", "misc");

		funTable.put("COOD", "syn");

	}

	/**
	 * escape tokens (brackets)
	 * 
	 * @param token
	 * @return
	 */
	private String escapeToken(String token) {
		String newToken = (String) escMap.get(token);
		if (newToken == null)
			return token;
		return newToken;
	}

}
