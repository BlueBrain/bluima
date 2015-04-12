/** 
 * OpennlpPosTagger.java
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
 * Analysis Engine that invokes the OpenNLP POS Tagger. This annotator assumes that
 * sentences and tokens have been annotated in the CAS. We iterate over sentences, 
 * then iterate over tokens in the current sentece to accumulate a list of tokens, then invoke the
 * OpenNLP POS Tagger on the list of tokens. 
 **/

package ch.epfl.bbp.uima.ae;

import static de.julielab.jules.utility.JulesTools.addToFSArray;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.shaded.opennlp.tools.lang.english.PosTagger;
import ch.epfl.bbp.shaded.opennlp.tools.ngram.Dictionary;
import ch.epfl.bbp.shaded.opennlp.tools.postag.POSDictionary;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ScriptingShortcut;
import de.julielab.jules.types.POSTag;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;
import de.julielab.jules.utility.AnnotationTools;

/**
 * @author renaud.richardet@epfl.ch
 *
 */
@ScriptingShortcut(shortcut="ch.epfl.bbp.uima.ae.OpenNlpHelper.getPosTagger()")
public class PosTagAnnotator extends JCasAnnotator_ImplBase {
	private static Logger LOG = LoggerFactory.getLogger(PosTagAnnotator.class);

	private PosTagger tagger;

	public static final String PARAM_CASE_SENSITIVE = "caseSensitive";
	@ConfigurationParameter(name = PARAM_CASE_SENSITIVE, defaultValue = "false", //
	description = "Is Tag Dictionary Use Case senstive")
	private Boolean caseSensitive;
	
	public static final String PARAM_TAG_SET = "tagset";
    @ConfigurationParameter(name = PARAM_TAG_SET, defaultValue = "de.julielab.jules.types.GeniaPOSTag", //
    description = "the used postagset")
    private String postagset;

	public static final String PARAM_USE_TAG_DICT = "useTagdict";
	@ConfigurationParameter(name = PARAM_USE_TAG_DICT, defaultValue = "true", //
	description = "true if a tag dictionary should be used, please consider data fields tagdict, caseSensitive")
	private Boolean useTagdict;

	public static final String PARAM_TAG_DICT = "tagDict";
	@ConfigurationParameter(name = PARAM_TAG_DICT, defaultValue = "pear_resources/models/postag/tagdict-genia", //
	description = "Path to tag Dictionary")
	private String tagdict;

	@ConfigurationParameter(name = BlueUima.PARAM_MODEL_FILE, defaultValue = "pear_resources/models/postag/Tagger_Genia.bin.gz", //
	description = "")
	private String modelFile;

	@Override
	public void initialize(UimaContext aContext)
			throws ResourceInitializationException {
		super.initialize(aContext);
		try {
			// Get OpenNLP POS Tagger, initialize with a model
			if (useTagdict)
				tagger = new PosTagger(modelFile, (Dictionary) null,
						new POSDictionary(tagdict, caseSensitive));
			else
				tagger = new PosTagger(modelFile, (Dictionary) null);
		} catch (Exception e) {
			throw new ResourceInitializationException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void process(JCas aJCas) {
		ArrayList<Token> tokenList = new ArrayList<Token>();
		ArrayList<String> tokenTextList = new ArrayList<String>();

		AnnotationIndex tokenIndex = (AnnotationIndex) aJCas
				.getJFSIndexRepository().getAnnotationIndex(Token.type);

		for (Sentence sentence : JCasUtil.select(aJCas, Sentence.class)) {
			tokenList.clear();
			tokenTextList.clear();

			// iterate over Tokens
			FSIterator tokenIterator = tokenIndex.subiterator(sentence);
			while (tokenIterator.hasNext()) {
				Token token = (Token) tokenIterator.next();
				tokenList.add(token);
				tokenTextList.add(token.getCoveredText());
			}

			try {
				List tokenTagList = tagger.tag(tokenTextList);

				for (int i = 0; i < tokenList.size(); i++) {
					Token token = (Token) tokenList.get(i);
					String posTag = (String) tokenTagList.get(i);
					token.setPos(posTag);
					
					POSTag pos = null;
					 try {
	                        pos = (POSTag) AnnotationTools
	                                .getAnnotationByClassName(aJCas, postagset);
	                        pos.setBegin(token.getBegin());
	                        pos.setEnd(token.getEnd());
	                        pos.setValue(posTag);
	                        //pos.setComponentId(COMPONENT_ID);
	                        pos.addToIndexes();
	                    } catch (Exception e1) {
	                        LOG.error("[OpenNLP POSTag Annotator]"
	                                + e1.getMessage());
	                    }

	                    FSArray postags;
	                    if (token.getPosTag() == null) {
	                        postags = new FSArray(aJCas, 1);
	                        try {
	                            postags.set(0, pos);
	                        } catch (CASRuntimeException e) {
	                            LOG.error("[OpenNLP POSTag Annotator]"
	                                    + e.getMessage());
	                        }
	                        postags.addToIndexes();
	                        token.setPosTag(postags);

	                    } else {
	                        postags = addToFSArray(token.getPosTag(), pos, 1);
	                    }
					
					
					
				}
			} catch (CASRuntimeException e) {
				String docId = BlueCasUtil.getHeaderDocId(aJCas);
				LOG.error(
						"[OpenNLP POSTag Annotator] {} on docId {} with sentence: "
								+ sentence.getCoveredText(), e.getMessage(),
						docId);
				LOG.error("[OpenNLP POSTag Annotator]  list of tags shorter than list of words");
			} catch (Throwable t) {
				String docId = BlueCasUtil.getHeaderDocId(aJCas);
				LOG.error(
						"[OpenNLP POSTag Annotator] {} on docId {} with sentence: "
								+ sentence.getCoveredText(), t.getMessage(),
						docId);
			}
		}
	}
}