package ch.epfl.bbp.nlp.res.sentence;

import ch.epfl.bbp.nlp.ModelResource;

/**
 * Genia model for sentence splitter annotator
 */
public class GeniaResource implements ModelResource {
    public String getResourcePath() {
        return "/SentDetectGenia.bin.gz";
    }
}
