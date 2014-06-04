package ch.epfl.bbp.uima.projects.dca;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * This class is used for calculating similarity between two vectors, finding
 * the closest vector within a library of vectors and such, it can be used for
 * any distance metric calculations
 * 
 * @author Barkin Aygun
 * 
 */
public class Similarity {
    
    public static final int COSINE = 0;
    public static final int L2 = 1;
    public static final int L1 = 2;

    /**
     * Calculates similarity of type given between two vectors <br>
     * COSINE similarity - normalized dot product
     * 
     * L2 metric - root of sum of squares of differences
     * 
     * L1 metric - sum of differences
     * 
     * @param vec1
     *            Array of doubles for first vector
     * @param vec2
     *            Array of doubles for second vector
     * @param type
     *            Distance Metric for similarity (COSINE/L1/L2)
     * @return the distance between the two vectors
     */
    public static double calculateSimilarity(double[] vec1, double[] vec2,
            int type) {
        double similarity = 0;
        assert (vec1.length == vec2.length);
        for (int i = 0; i < vec1.length; i++) {
            switch (type) {
            case (COSINE):
                similarity += vec1[i] * vec2[i];
                break;
            case (L2):
                similarity += Math.pow(vec1[i] - vec2[i], 2);
                break;
            case (L1):
                similarity += Math.abs(vec1[i] - vec2[i]);
                break;
            }
        }
        if (type == COSINE)
            similarity = similarity / (vectorLength(vec1) * vectorLength(vec2));
        else if (type == L2)
            similarity = Math.sqrt(similarity);
        return similarity;
    }

    /**
     * Calculates the length of the given vector
     * 
     * @param vec
     *            double array of the vector
     * @return length of the vector as a double
     * 
     */
    private static double vectorLength(double[] vec) {
        double len = 0;
        for (int i = 0; i < vec.length; i++) {
            len += vec[i] * vec[i];
        }
        len = Math.sqrt(len);
        return len;
    }

    /**
     * Override for calculateSimilarity using L2 metric as default metric
     * 
     * @param vec1
     *            Array of doubles for first vector
     * @param vec2
     *            Array of doubles for second vector
     * @return the distance between the two vectors
     */
    public static double calculateSimilarity(double[] vec1, double[] vec2) {
        return calculateSimilarity(vec1, vec2, L2);
    }

    /**
     * Finds the most similar vector to the given index vector in the vectors
     * using the given distance metric, returns the indexes of vectors in
     * descending order, closest first
     * 
     * @param vectors
     *            2-D array of doubles representing all vectors
     * @param index
     *            Index of the vector to find most similar to
     * @param type
     *            Distance metric
     * @return array of integers of indices of closest to furthest vector
     */
    public static int[] similarVectors(double[][] vectors, int index, int type) {
        int[] indices = new int[vectors.length - 1];
        int ind = 0;
        TreeMap<Integer, Double> unsortedSimilarities = new TreeMap<Integer, Double>();
        for (int i = 0; i < vectors.length; i++) {
            if (i == index)
                continue;
            unsortedSimilarities.put(i,
                    calculateSimilarity(vectors[index], vectors[i], type));
        }

        TreeMap<Integer, Double> sortedSimilarities = new TreeMap<Integer, Double>(
                new ValueComparer(unsortedSimilarities));
        sortedSimilarities.putAll(unsortedSimilarities);
        for (Iterator<Integer> it = sortedSimilarities.keySet().iterator(); it
                .hasNext();) {
            indices[ind++] = it.next();
        }
        return indices;
    }

    /**
     * Override for similarVectors using COSINE similarity as default metric
     * 
     * @param vectors
     *            2-D array of doubles representing all vectors
     * @param index
     *            Index of the vector to find most similar to
     * @return array of integeres of indices of closest to furthest vector
     */
    public static int[] similarVectors(double[][] vectors, int index) {
        return similarVectors(vectors, index, COSINE);
    }

    /**
     * Override for similarVectors using an ArrayList of vectors instead of 2-D
     * array
     * 
     * @param vectors
     *            ArrayList of double arrays representing all vectors
     * @param index
     *            Index of the vector to find most similar to
     * @param type
     *            Distance metric to be used
     * @return array of integers of indices of closest to furthest vector
     */
    public static int[] similarVectors(ArrayList<double[]> vectors, int index,
            int type) {
        int[] indices = new int[vectors.size() - 1];
        int ind = 0;
        TreeMap<Integer, Double> unsortedSimilarities = new TreeMap<Integer, Double>();
        for (int i = 0; i < vectors.size(); i++) {
            if (i == index)
                continue;
            unsortedSimilarities.put(
                    i,
                    calculateSimilarity(vectors.get(index), vectors.get(i),
                            type));
        }

        TreeMap<Integer, Double> sortedSimilarities = new TreeMap<Integer, Double>(
                new ValueComparer(unsortedSimilarities));
        sortedSimilarities.putAll(unsortedSimilarities);
        for (Iterator<Integer> it = sortedSimilarities.keySet().iterator(); it
                .hasNext();) {
            indices[ind++] = it.next();
        }
        return indices;
    }

    /**
     * Override for similarVectors using the COSINE metric as default for
     * ArrayList
     * 
     * @param vectors
     *            ArrayList of double arrays representing all vectors
     * @param index
     *            Index of the vector to find most similar to
     * @return array of integers of indices of closest to furthest vector
     */
    public static int[] similarVectors(ArrayList<double[]> vectors, int index) {
        return similarVectors(vectors, index, COSINE);
    }

    /**
     * Saves the given vector library to a file for further use
     * 
     * @param vectors
     *            ArrayList of double arrays representing all vectors
     * @param filename
     *            Location of the savefile
     */
    public static void saveVectorsToFile(ArrayList<double[]> vectors,
            String filename) {
        FileWriter fw;
        double[] vector;
        try {
            fw = new FileWriter(filename);
            for (int i = 0; i < vectors.size(); i++) {
                vector = vectors.get(i);
                for (int j = 0; j < vector.length; j++) {
                    fw.write(Double.toString(vector[j]) + " ");
                }
                fw.write("\n");
            }
            fw.flush();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the vectors from a file
     * 
     * @param filename
     *            Location of the vector file
     * @return ArrayList of double arrays representing all vectors
     */
    public static ArrayList<double[]> loadVectorsFromFile(String filename) {
        BufferedReader fr;
        String line;
        String[] values;
        double[] vector;
        ArrayList<double[]> vectors = new ArrayList<double[]>();
        try {
            fr = new BufferedReader(new FileReader(filename));
            line = fr.readLine();
            while (line != null) {
                values = line.split(" ");
                vector = new double[values.length];
                for (int i = 0; i < values.length; i++) {
                    vector[i] = Double.parseDouble(values[i]);
                }
                vectors.add(vector);
                line = fr.readLine();
            }
            fr.close();
            return vectors;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The value comparer class that allows for sorting the vectors according to
     * distance
     * 
     * @author Barkin Aygun
     * 
     */
    private static class ValueComparer implements Comparator<Integer> {
        private TreeMap<Integer, Double> _data = null;

        public ValueComparer(TreeMap<Integer, Double> data) {
            super();
            _data = data;
        }

        public int compare(Integer o1, Integer o2) {
            double e1 = _data.get(o1);
            double e2 = _data.get(o2);
            if (e1 > e2)
                return -1;
            if (e1 == e2)
                return 0;
            if (e1 < e2)
                return 1;
            return 0;
        }
    }

}
