package ch.epfl.bpp.nlp.res.token;

import ch.epfl.bbp.nlp.ModelResource;

/**
 * Genia Resource
 */
public class GeniaResource implements ModelResource {
    public String getResourcePath() {
        return "/TokenizerGenia.bin.gz";
    }
}

