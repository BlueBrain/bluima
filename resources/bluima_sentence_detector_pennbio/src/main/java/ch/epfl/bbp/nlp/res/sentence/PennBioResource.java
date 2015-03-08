package ch.epfl.bbp.nlp.res.sentence;

import ch.epfl.bbp.nlp.ModelResource;

/**
 * PennBio model for sentence splitter annotator
 */
public class PennBioResource implements ModelResource {
    public String getResourcePath() {
        return "/SentDetectPennBio.bin.gz";
    }
}
