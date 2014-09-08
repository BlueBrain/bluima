package ch.epfl.bbp.uima.word2vec;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.sqrt;
import static java.lang.System.arraycopy;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.MissingUtils;
import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.word2vec.Word2Vec.WordEntry;

@Ignore
public class Word2Vec_MoreTest {

    private Word2Vec w2v;
    private final int topNSize = 40;

    @Before
    public void before() throws IOException {
        String MODEL_FILE;
        // MODEL_FILE =
        // "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/50_word2vec/word2vec_trunk/1m_ns-40.w2v.bin";
        MODEL_FILE = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/50_word2vec/word2vec_trunk/vectors.bin";
        // MODEL_FILE =
        // "/Volumes/HDD2/ren_data/data_hdd/word2vec_models/PubMed-w2v.bin";
        // MODEL_FILE =
        // "/Volumes/HDD2/ren_data/data_hdd/word2vec_models/GoogleNews-vectors-negative300.bin";
        // MODEL_FILE =
        // "/Volumes/HDD2/ren_data/data_hdd/word2vec_models/wikipedia-pubmed-and-PMC-w2v.bin";
        w2v = new Word2Vec();
        w2v.loadModel(MODEL_FILE);
        assertNotNull(w2v.getWordVector("brain"));
    }

    @Test
    @Ignore
    public void testWriteVocab() throws Exception {

        TextFileWriter wr = new TextFileWriter("dict.txt");
        for (String w : w2v.getWordMap().keySet()) {
            wr.addLine(w);
        }
        wr.close();
    }

    @Test
    public void testPrintSome() throws Exception {

        // for (String w : new String[] { "king", "queen", "prince",
        // "president",
        // "major", "man", "woman", "child" }) {
        for (String w : new String[] { "king", "queen", "woman", "prince",
                "princess", "son", "daughter", "father", "mother", "actor",
                "actress", "man" }) {

            System.out.print(w);
            for (float f : w2v.getWordVector(w)) {
                System.out.print("\t" + f);
            }
            System.out.println();
        }
    }

    @Test
    public void testPrintSimilar() throws Exception {

        for (String w : new String[] { "king", "queen", "prince", "president",
                "major", "man", "woman", "child" }) {

            System.out.println("---------------\n" + w);
            for (WordEntry we : w2v.distance(w)) {
                System.out.println(we);
            }
            System.out.println();
        }
    }

    @Test
    public void testAnalogy() throws Exception {

        String[][] as = new String[][] { { "king", "queen", "man" } //
                , { "bread", "flour", "sirup" } //
                , { "bread", "flour", "popcorn" } //
                , { "bread", "flour", "wine" } //
                , { "bread", "flour", "chocolate" } //
                , { "chocolate", "cocoa", "bread" } //
                , { "nail", "hammer", "thread" } //
                , { "hammer", "nail", "water" } //
                , { "nail", "metal", "bottle" } //
                , { "nail", "metal", "bottle" } //
        };

        for (String[] a : as) {

            int i = 3;
            MissingUtils.printf("{} -- {} -- {}", a[0], a[1], a[2]);
            try {

                for (WordEntry we : w2v.analogy(a[0], a[1], a[2])) {
                    if (i-- > 0)
                        System.out.println(we);
                }
            } catch (Exception e) {
                System.out.println("NOPE");
            }
        }
    }

    @Test
    public void testAnalogyCline() throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.println("\nENTER ANALOGY: ");
                String[] a = br.readLine().split(" ");
                int i = 3;
                MissingUtils.printf("'{}' -- '{}' -- '{}'", a[0], a[1], a[2]);

                for (WordEntry we : w2v.analogy(a[0], a[1], a[2])) {
                    if (i-- > 0)
                        System.out.println(we);
                }
            } catch (Exception e) {
                System.out.println("NOPE");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                System.out.println(sw.toString());
            }
        }
    }

    @Test
    public void testParser() throws Exception {

        // int j = 3;
        // for (WordEntry we : w2v.analogy("queen - king + man")) {
        // if (j-- > 0)
        // System.out.println(we);
        // }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                System.out.println("\nENTER QUERY: ");

                int i = 3;
                for (WordEntry we : w2v.analogy(br.readLine())) {
                    if (i-- > 0)
                        System.out.println(we);
                }
            } catch (Exception e) {
                System.out.println("NOPE");
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                System.out.println(sw.toString());
            }
        }
    }

    @Test
    public void test() throws Exception {

        int skipDim = 0;

        final Set<Entry<String, float[]>> entrySet = w2v.getWordMap()
                .entrySet();

        float[] wv2 = null;
        for (Entry<String, float[]> entry : entrySet) {

            final float[] wv1 = entry.getValue();
            System.out.println("\n--------------------------------------\n"
                    + entry.getKey());
            final float[] skipWv1 = skip(wv1, skipDim);

            List<WordEntry> topWords = newArrayList();

            for (Entry<String, float[]> ndEntry : entrySet) {
                wv2 = ndEntry.getValue();

                // normal distance
                float dist = 0;
                for (int i = 0; i < wv1.length; i++) {
                    dist += wv1[i] * wv2[i];
                }

                // skip distance
                float skipDist = 0f;
                float[] skipWv2 = skip(wv2, skipDim);
                for (int i = 0; i < skipWv1.length; i++) {
                    skipDist += wv1[i] * wv2[i];
                }
                skipDist /= norm(skipWv1) * norm(skipWv2);

                float wv1Skipped = wv1[skipDim];
                float wv2Skipped = wv2[skipDim];

                // DO SOMETHING MAGESTIC HERE

                float score = skipDist
                        / ((wv1Skipped - wv2Skipped) * (wv1Skipped - wv2Skipped));
                insertTopN(ndEntry.getKey(), score, topWords);
            }
            for (WordEntry we : new TreeSet<WordEntry>(topWords)) {
                System.out.println(we);
            }
        }
    }

    private float norm(float[] v) {
        float ret = 0f;
        for (int i = 0; i < v.length; i++) {
            ret += v[i] * v[i];
        }
        return (float) sqrt(ret);
    }

    @Test
    public void testSkip() throws Exception {
        float[] a = new float[] { 0f, 1f, 2f, 3f, 4f };
        assertTrue(Arrays.equals(new float[] { 0f, 1f, 3f, 4f }, skip(a, 2)));
        assertTrue(Arrays.equals(new float[] { 0f, 1f, 2f, 3f }, skip(a, 4)));
        assertTrue(Arrays.equals(new float[] { 1f, 2f, 3f, 4f }, skip(a, 0)));
    }

    private static float[] skip(final float[] v, int skip) {
        checkArgument(skip < v.length);
        float[] ret = new float[v.length - 1];
        arraycopy(v, 0, ret, 0, skip);
        arraycopy(v, skip + 1, ret, skip, v.length - skip - 1);
        return ret;
    }

    private void insertTopN(String name, float score,
            List<WordEntry> wordsEntrys) {
        if (wordsEntrys.size() < topNSize) {
            wordsEntrys.add(new WordEntry(name, score));
            return;
        }
        float min = Float.MAX_VALUE;
        int minOffe = 0;
        for (int i = 0; i < topNSize; i++) {
            WordEntry wordEntry = wordsEntrys.get(i);
            if (min > wordEntry.score) {
                min = wordEntry.score;
                minOffe = i;
            }
        }

        if (score > min) {
            wordsEntrys.set(minOffe, new WordEntry(name, score));
        }
    }

}
