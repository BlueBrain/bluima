package ch.epfl.bbp.uima.word2vec;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;

public class Word2VecUtils {
    private static Logger LOG = getLogger(Word2VecUtils.class);

    static final String MODEL_FILE = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/50_word2vec/word2vec_trunk/1m_ns-40.w2v.bin";// FIXME
    // static final String MODEL_FILE =
    // "/Users/richarde/dev/bluebrain/git/Bluima/vectors.bin";// FIXME

    private static Word2Vec w2v = new Word2Vec();
    static {
        try {
            w2v.loadModel(MODEL_FILE);
        } catch (IOException e) {
            LOG.error("could not load model", e);
        }
    }

    private Word2VecUtils() {// singleton
    }

    public static float[] getWordVector(String word) {
        return w2v.getWordVector(word.toLowerCase());
    }

    final static String MODEL_FILE_CLASS = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/50_word2vec/word2vec_trunk/1m_ns.w2v.100classes.bin";
    private static HashMap<String, Integer> w2c = null;

    public static int getClass(String word) {
        if (w2c == null) {
            try {
                w2c = Word2Vec.loadClassModel(MODEL_FILE_CLASS);
            } catch (IOException e) {
                System.err.println("could not load w2c");
            }
        }
        if (w2c.containsKey(word.toLowerCase())) {

            return w2c.get(word.toLowerCase());
        } else {
            return -1;
        }
    }
}
