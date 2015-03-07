package ch.epfl.bbp.nlp;

/**
 * PennBio model for sentence splitter annotator
 */
public class PennBioResource implements ModelResource {
    public String getResourcePath() {
        return "/SentDetectPennBio.bin.gz";
    }
}
