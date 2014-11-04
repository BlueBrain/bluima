package ch.epfl.bbp.uima.word2vec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class Word2VecMore extends Word2Vec {

    /** only counting negative OR POSITIVE distances */
    public Set<WordEntry> distanceNeg(String word) {
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

                float thisDist = wordVector[i] * tempVector[i];
                if (thisDist < 0) // '<' (lt) --> NEG
                    dist += thisDist;
            }
            insertTopN(name, dist, wordEntrys);
        }
        return new TreeSet<WordEntry>(wordEntrys);
    }

    /** only counting NUMBER OF negative OR POSITIVE distances */
    public Set<WordEntry> distanceNegCnt(String word) {
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
            int negatives = 0;
            tempVector = entry.getValue();
            for (int i = 0; i < wordVector.length; i++) {

                float thisDist = wordVector[i] * tempVector[i];
                if (thisDist < 0) // '<' (lt) --> NEG
                    negatives++;
            }
            insertTopN(name, -negatives, wordEntrys);
        }
        return new TreeSet<WordEntry>(wordEntrys);
    }

    /** FAIL only counting min + max distance */
    public Set<WordEntry> distanceMax(String word) {
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
            float minDist = 10, maxDist = -10;
            tempVector = entry.getValue();
            for (int i = 0; i < wordVector.length; i++) {

                maxDist = Math.max(wordVector[i] * tempVector[i], maxDist);
                minDist = Math.min(wordVector[i] * tempVector[i], minDist);
            }
            insertTopN(name, maxDist + minDist, wordEntrys);
        }
        return new TreeSet<WordEntry>(wordEntrys);
    }
}
