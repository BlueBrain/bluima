package ch.epfl.bbp.uima.word2vec;

import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.parseInt;
import static java.lang.Math.sqrt;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Lists;

public class Word2Vec {

    private HashMap<String, float[]> vocabulary = new HashMap<String, float[]>();

    private int vocabSize;
    private int vectorSize;
    private int topNSize = 40;

    public Word2Vec loadModel(String path) throws IOException {
        DataInputStream dis = null;
        BufferedInputStream bis = null;
        double len = 0;
        float vector = 0;
        checkFileExists(path);
        try {
            bis = new BufferedInputStream(new FileInputStream(path));
            dis = new DataInputStream(bis);
            vocabSize = parseInt(readString(dis));
            vectorSize = parseInt(readString(dis));

            String word;
            float[] vectors = null;
            for (int i = 0; i < vocabSize; i++) {
                word = readString(dis);
                vectors = new float[vectorSize];
                len = 0;
                for (int j = 0; j < vectorSize; j++) {
                    vector = readFloat(dis);
                    len += vector * vector;
                    vectors[j] = (float) vector;
                }
                len = sqrt(len);

                for (int j = 0; j < vectors.length; j++) {
                    vectors[j] = (float) (vectors[j] / len);
                }
                vocabulary.put(word, vectors);
                dis.read();
            }

        } finally {
            bis.close();
            dis.close();
        }
        return this;
    }

    private static final int MAX_SIZE = 50;

    public Set<WordEntry> distance(String word) {
        float[] wordVector = getWordVector(word);
        if (wordVector == null) {
            return null;
        }
        Set<Entry<String, float[]>> entrySet = vocabulary.entrySet();
        float[] tempVector = null;
        List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
        for (Entry<String, float[]> entry : entrySet) {
            String name = entry.getKey();
            if (name.equals(word)) {
                continue;
            }
            float dist = 0;
            tempVector = entry.getValue();
            for (int i = 0; i < wordVector.length; i++) {
                dist += wordVector[i] * tempVector[i];
            }
            insertTopN(name, dist, wordEntrys);
        }
        return new TreeSet<WordEntry>(wordEntrys);
    }

    public TreeSet<WordEntry> analogy(String word0, String word1, String word2) {
        float[] wv0 = getWordVector(word0);
        float[] wv1 = getWordVector(word1);
        float[] wv2 = getWordVector(word2);

        if (wv1 == null || wv2 == null || wv0 == null) {
            return null;
        }
        float[] wordVector = new float[vectorSize];
        for (int i = 0; i < vectorSize; i++) {
            wordVector[i] = wv1[i] - wv0[i] + wv2[i];
        }
        float[] tempVector;
        String name;
        List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
        for (Entry<String, float[]> entry : vocabulary.entrySet()) {
            name = entry.getKey();
            if (name.equals(word0) || name.equals(word1) || name.equals(word2)) {
                continue;
            }
            float dist = 0;
            tempVector = entry.getValue();
            for (int i = 0; i < wordVector.length; i++) {
                dist += wordVector[i] * tempVector[i];
            }
            insertTopN(name, dist, wordEntrys);
        }
        return new TreeSet<WordEntry>(wordEntrys);
    }

    public TreeSet<WordEntry> analogy(String query) {

        String[] split = query.split(" ");
        checkArgument(split.length % 2 == 1, "should be uneven nr: " + query);

        // true: +; false:-
        boolean[] operators = new boolean[(split.length - 1) / 2];
        for (int i = 0; i < (split.length - 1) / 2; i++) {
            if (split[2 * i + 1].equals("+"))
                operators[i] = true;
            else if (split[2 * i + 1].equals("-"))
                operators[i] = false;
            else
                throw new RuntimeException("illegal operator, was: '"
                        + operators[i]);
        }

        float[][] ww = new float[(split.length + 1) / 2][getSize()];
        List<String> queryNames = newArrayList();
        for (int i = 0; i < (split.length + 1) / 2; i++) {
            String word = split[2 * i];
            queryNames.add(word);
            ww[i] = getWordVector(word);
            if (ww[i] == null)
                throw new RuntimeException("no ww for: " + word);
        }
        checkEquals(ww.length - 1, operators.length);

        System.out.print("'" + queryNames.get(0) + "' ");
        for (int i = 0; i < operators.length; i++) {
            System.out.print("'" + operators[i] + "' ");
            System.out.print("'" + queryNames.get(i + 1) + "' ");
        }
        System.out.println();

        List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
        float[] wordVector = new float[vectorSize];
        for (int i = 0; i < vectorSize; i++) {

            float val = ww[0][i];

            for (int j = 0; j < operators.length; j++) {
                if (operators[j]) {
                    val += ww[j + 1][i];
                } else {
                    val -= ww[j + 1][i];
                }
            }
            wordVector[i] = val;
        }
        float[] tempVector;
        String name;
        for (Entry<String, float[]> entry : vocabulary.entrySet()) {
            name = entry.getKey();

            if (queryNames.contains(name)) {
                continue;
            }
            float dist = 0;
            tempVector = entry.getValue();
            for (int i = 0; i < wordVector.length; i++) {
                dist += wordVector[i] * tempVector[i];
            }
            insertTopN(name, dist, wordEntrys);
        }

        return new TreeSet<WordEntry>(wordEntrys);
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

    public static class WordEntry implements Comparable<WordEntry> {
        public String name;
        public float score;

        public WordEntry(String name, float score) {
            this.name = name;
            this.score = score;
        }

        @Override
        public String toString() {
            return this.name + "\t" + score;
        }

        @Override
        public int compareTo(WordEntry o) {
            if (this.score > o.score) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public float[] getWordVector(String word) {
        return vocabulary.get(word);
    }

    private static float readFloat(InputStream is) throws IOException {
        byte[] bytes = new byte[4];
        is.read(bytes);
        return getFloat(bytes);
    }

    private static float getFloat(byte[] b) {
        int accum = 0;
        accum = accum | (b[0] & 0xff) << 0;
        accum = accum | (b[1] & 0xff) << 8;
        accum = accum | (b[2] & 0xff) << 16;
        accum = accum | (b[3] & 0xff) << 24;
        return Float.intBitsToFloat(accum);
    }

    private static String readString(DataInputStream dis) throws IOException {
        byte[] bytes = new byte[MAX_SIZE];
        byte b = dis.readByte();
        int i = -1;
        StringBuilder sb = new StringBuilder();
        while (b != 32 && b != 10) {
            i++;
            bytes[i] = b;
            b = dis.readByte();
            if (i == 49) {
                sb.append(new String(bytes));
                i = -1;
                bytes = new byte[MAX_SIZE];
            }
        }
        sb.append(new String(bytes, 0, i + 1));
        return sb.toString();
    }

    public int getTopNSize() {
        return topNSize;
    }

    public HashMap<String, float[]> getWordMap() {
        return vocabulary;
    }

    public int getVocabSize() {
        return vocabSize;
    }

    public int getSize() {
        return vectorSize;
    }

    public List<Integer> getMostFrequentTopics(String word, float threshold) {
        List<Integer> ret = new ArrayList<Integer>();
        float[] wordVector = getWordVector(word);
        for (int i = 0; i < wordVector.length; i++)
            if (wordVector[i] > threshold)
                ret.add(i);
        return ret;
    }

    public static HashMap<String, Integer> loadClassModel(String path)
            throws IOException {
        checkFileExists(path);
        HashMap<String, Integer> wordMap = newHashMap();
        DataInputStream dis = null;
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(path));
            dis = new DataInputStream(bis);
            while (true) {
                wordMap.put(readString(dis), parseInt(readString(dis)));
            }
        } catch (Exception e) {// nope
        } finally {
            bis.close();
            dis.close();
        }
        return wordMap;
    }
}
