package ch.epfl.bbp.nlp.res.chunk;

import ch.epfl.bbp.nlp.ModelResource;

/**
 * Genia Resource
 */
public class GeniaResource implements ModelResource {
    public String getResourcePath() {
        return "/Chunker_Genia.bin.gz";
    }
}

