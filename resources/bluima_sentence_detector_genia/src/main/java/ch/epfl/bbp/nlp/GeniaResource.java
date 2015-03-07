package ch.epfl.bbp.nlp;

/**
 * Genia model for sentence splitter annotator
 */
public class GeniaResource implements ModelResource {
    public String getResourcePath() {
        return "/SentDetectGenia.bin.gz";
    }
}
