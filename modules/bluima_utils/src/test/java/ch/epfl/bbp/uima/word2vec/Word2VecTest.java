package ch.epfl.bbp.uima.word2vec;

import org.junit.Ignore;
import org.junit.Test;

public class Word2VecTest {

    @Test
    @Ignore
    public void test() throws Exception {
        Word2Vec vec = new Word2Vec();
        vec.loadModel("vectors.bin");
        System.out.println(vec.distance("neuron"));
        for (float weight : vec.getWordVector("neuron"))
        System.out.println(weight);
        // System.out.println(vec.analogy());
    }
}
