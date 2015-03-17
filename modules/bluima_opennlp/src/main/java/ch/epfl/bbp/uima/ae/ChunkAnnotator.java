/** 
 * OpenNLPChunker.java
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
 * OpenNLP Chunker provides chunks to tokens in IOB format (e.g. B-NP, I-VP). 
 * This UIMA wrapper provides all needed input parameters to the OpenNLP Chunker and converts the IOB output of 
 * the OpenNLP Chunker in CAS. 
 **/

package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.CHUNK;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.annotator.AnnotatorConfigurationException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.nlp.ModelProxy;
import ch.epfl.bbp.nlp.ModelProxyException;
import ch.epfl.bbp.nlp.ModelStream;
import ch.epfl.bbp.shaded.opennlp.tools.lang.english.TreebankChunker;
import ch.epfl.bbp.uima.BlueUima;
import de.julielab.jules.types.Chunk;
import de.julielab.jules.types.POSTag;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;
import de.julielab.jules.utility.AnnotationTools;

//@ScriptingShortcut(shortcut = "ch.epfl.bbp.uima.ae.OpenNlpHelper.getTokenizer()")
@TypeCapability(inputs = { SENTENCE, TOKEN }, outputs = { CHUNK })
public class ChunkAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(PosTagAnnotator.class);

    /**
     * component Id
     */
    private static final String COMPONENT_ID = "de.julielab.jules.ae.OpenNLPChunker";

    /**
     * instance of the OpenNLP chunker
     */
    private TreebankChunker chunker;

    @ConfigurationParameter(name = BlueUima.PARAM_MODEL, defaultValue = "", //
    description = "")
    private String model;

    public static final String PARAM_posTagSetPreference = "posTagSetPreference";
    @ConfigurationParameter(name = PARAM_posTagSetPreference, defaultValue = "de.julielab.jules.types.GeniaPOSTag", //
    description = "the POS Tagset prefered by this Chunker. If Chunker is trained on\n"
            + "     * PennBioIE, it is recommendable to use PennBioIE POS Tagset")
    private String posTagSetPreference;

    /**
     * chunk tag mappings names
     */
    private String[] mappings = new String[] {
            "NP;de.julielab.jules.types.ChunkNP",//
            "PP;de.julielab.jules.types.ChunkPP",
            "VP;de.julielab.jules.types.ChunkVP",
            "ADJP;de.julielab.jules.types.ChunkADJP",
            "CONJP;de.julielab.jules.types.ChunkCONJP",
            "LST;de.julielab.jules.types.ChunkLST",
            "SBAR;de.julielab.jules.types.ChunkSBAR",
            "PRT;de.julielab.jules.types.ChunkPRT",
            "ADVP;de.julielab.jules.types.ChunkADVP" };

    /**
     * mappings from chunk tags to CAS
     */
    private Hashtable<String, String> mappingsTable = new Hashtable<String, String>();

    @Override
    public void initialize(UimaContext aContext)
            throws ResourceInitializationException {

        LOG.info("[OpenNLP Chunk Annotator] initializing OpenNLP Chunk Annotator ...");
        super.initialize(aContext);

        // String modelFile = (String) aContext
        // .getConfigParameterValue("modelFile");
        // mappings = (String[]) aContext.getConfigParameterValue("mappings");
        loadMappings();

        try {
            ModelStream modelStream = ModelProxy.getStream(model);
            chunker = new TreebankChunker(modelStream);
        } catch (IOException | ModelProxyException e) {
            LOG.error("[OpenNLP Chunk Annotator] could not load Chunk model: "
                    + e.getMessage());
            throw new ResourceInitializationException(e);
        }
    }

    // load mappings of chunk tag names
    private void loadMappings() {
        for (int i = 0; i < mappings.length; i++) {
            String[] pair = mappings[i].split(";");
            if (pair.length < 2) {
                try {
                    throw new AnnotatorConfigurationException();
                } catch (AnnotatorConfigurationException e) {
                    e.printStackTrace();
                }
            } else {
                String chunkTag = pair[0];
                String classTag = pair[1];
                mappingsTable.put(chunkTag, classTag);
            }
        }

    }

    @SuppressWarnings("rawtypes")
    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {

        // LOG.info("[OpenNLP Chunk Annotator] processing document ...");
        ArrayList<Token> tokenList = new ArrayList<Token>();
        ArrayList<String> tokenTextList = new ArrayList<String>();
        ArrayList<String> tagTextList = new ArrayList<String>();

        AnnotationIndex sentenceIndex = (AnnotationIndex) aJCas
                .getJFSIndexRepository().getAnnotationIndex(Sentence.type);
        AnnotationIndex tokenIndex = (AnnotationIndex) aJCas
                .getJFSIndexRepository().getAnnotationIndex(Token.type);

        FSIterator sentenceIterator = sentenceIndex.iterator();
        while (sentenceIterator.hasNext()) {
            tokenList.clear();
            tokenTextList.clear();
            tagTextList.clear();
            Sentence sentence = (Sentence) sentenceIterator.next();

            // iterate over Tokens
            FSIterator tokenIterator = tokenIndex.subiterator(sentence);
            while (tokenIterator.hasNext()) {
                Token token = (Token) tokenIterator.next();
                tokenList.add(token);
                String tokenText = token.getCoveredText();
                tokenTextList.add(tokenText);
                POSTag postag = null;
                String posValue = null;

                // if no preference for a POS Tag Set
                if (posTagSetPreference != null) {
                    // get a correspondent POSTag available in CAS
                    postag = getPrefPOSTag(token);

                } else if (posTagSetPreference == null || postag == null)
                    postag = token.getPosTag(0);
                posValue = postag.getValue();
                tagTextList.add(posValue);

            }
            // OpenNLP Chunker predicts
            Object[] tok = tokenTextList.toArray();
            String[] tags = new String[tagTextList.size()];
            tagTextList.toArray(tags);
            String[] chunks = chunker.chunk(tok, tags);
            Chunk chunk = null;
            String chunkValue = null;

            for (int ci = 0; ci < chunks.length; ci++) {

                // if chunk begins
                if (chunks[ci].startsWith("B-")) {
                    try {
                        // value of a chunk
                        chunkValue = chunks[ci].substring(2);

                        // look for a class name in mappingsTable
                        String className = (String) mappingsTable
                                .get(chunkValue);
                        // if chunk name is not in mappings
                        if (className == null) {
                            LOG.info("Chunk" + className
                                    + "is not in mapping file.");
                            chunk = new Chunk(aJCas);
                        } else {
                            chunk = (Chunk) AnnotationTools
                                    .getAnnotationByClassName(aJCas, className);
                        }
                        chunk.setComponentId(COMPONENT_ID);

                    } catch (SecurityException e) {
                        LOG.error("[OpenNLP Chunk Annotator]" + e.getMessage());
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        LOG.error("[OpenNLP Chunk Annotator]" + e.getMessage());
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        LOG.error("[OpenNLP Chunk Annotator]" + e.getMessage());
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        LOG.error("[OpenNLP Chunk Annotator]" + e.getMessage());
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        LOG.error("[OpenNLP Chunk Annotator]" + e.getMessage());
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        LOG.error("[OpenNLP Chunk Annotator]" + e.getMessage());
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        LOG.error("[OpenNLP Chunk Annotator]" + e.getMessage());
                        e.printStackTrace();
                    }

                    chunk.setBegin(tokenList.get(ci).getBegin());
                    chunk.addToIndexes();
                    if (ci < chunks.length - 1
                            && (chunks[ci + 1].startsWith("B-") || chunks[ci + 1]
                                    .startsWith("O")))
                        chunk.setEnd(tokenList.get(ci).getEnd());
                }

                // if chunk is inside
                if (chunks[ci].startsWith("I-")) {
                    if (ci < chunks.length - 1
                            && (chunks[ci + 1].startsWith("B-") || chunks[ci + 1]
                                    .startsWith("O")))
                        chunk.setEnd(tokenList.get(ci).getEnd());
                }

                // if the end of sentence is achieved
                if (ci == chunks.length) {
                    chunk.setEnd(tokenList.get(ci).getEnd());
                }
            }
        }
    }

    // the user could specify the posTagSet prefered for the Chunker (actual if
    // CAS contain POSTagSet annotations from different PosTagSets)
    private POSTag getPrefPOSTag(Token token) {
        POSTag tag = null;
        FSArray postags = token.getPosTag();
        for (int i = 0; i < postags.size(); i++) {
            POSTag fs = (POSTag) postags.get(i);
            // if there are POS Tags
            if (fs != null) {

                // compare to the desired type of POS Tag Set
                if (fs.getType().getName().equals(posTagSetPreference)) {

                    i = postags.size();
                    return (POSTag) fs;
                }
            }
        }
        return tag;
    }
}
