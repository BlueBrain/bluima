package ch.epfl.bpp.nlp.res.tag;

import ch.epfl.bbp.nlp.ModelResource;

/**
 * Genia Resource
 */
public class GeniaResource implements ModelResource {
    public String getResourcePath() {
        return "/Tagger_Genia.bin.gz";
    }
}

