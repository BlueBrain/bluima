/*
 * The WhiteText project
 * 
 * Copyright (c) 2012 University of British Columbia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package ubic.whitetext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.tsf.TrieLexiconMembership;

/**
 * Given a lexicon file this class generates n-grams seen in the lexicon
 * 
 * @author Leon French
 */
public class NGramPipeFactory {
    public static Pipe getNGramPipe(String name, File inputFile,
            boolean ignoreCase, int gram) throws Exception {
        File tempFile = File.createTempFile("ngram", ".txt");
        // System.out.println( "Your temp file is " +
        // tempFile.getCanonicalPath() );
        // Arrange for it to be deleted at exit.
        tempFile.deleteOnExit();

        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        while (br.ready()) {
            String line = br.readLine().trim();
            if (line.equals(""))
                continue; // ignore blank lines
            // check null
            String[] gramStrings = getGrams(line, gram);
            if (gramStrings != null) {
                for (String gramString : gramStrings) {
                    bw.write(gramString);
                    // System.out.println(gramString);
                    bw.newLine();
                }
            }
        }
        bw.close();
        br.close();
        return new TrieLexiconMembership(name + "-" + gram + "-gram", tempFile,
                ignoreCase);
    }

    public static String[] getGrams(String s, int n) {
        // taken from mallet listmember
        StringTokenizer tokens = new StringTokenizer(s,
                "~`!@#$%^&*()_-+={[}]|\\:;\"',<.>?/ \t\n\r", false);

        // the token length is less than number of grams(n)
        if (tokens.countTokens() < n)
            return null;

        String[] tokensAR = new String[tokens.countTokens()];
        int j = 0;
        while (tokens.hasMoreElements()) {
            tokensAR[j++] = tokens.nextToken();
            // System.out.println( "|" + tokensAR[j - 1] + "|" );
        }

        String[] result = new String[tokensAR.length - n + 1];
        for (int i = 0; i < result.length; i++) {
            StringBuffer current = new StringBuffer();
            for (int gram = 0; gram < n; gram++) {
                current.append(" " + tokensAR[i + gram]);
            }
            result[i] = current.toString().trim();
        }
        return result;
    }

    /*
     * Get a list of pipes that correspond to lexicons for 1-6 grams of the
     * tokenized lexicon contained in the inputFile
     */
    public static List<Pipe> getAllGramsPipes(String name, File inputFile,
            boolean ignoreCase) throws Exception {
        return getAllGramsPipes(name, inputFile, ignoreCase, 1);
    }

    public static List<Pipe> getAllGramsPipes(String name, File inputFile,
            boolean ignoreCase, int startGramSize) throws Exception {
        // go up to seven?
        List<Pipe> pipes = new LinkedList<Pipe>();
        for (int i = startGramSize; i < 7; i++) {
            pipes.add(getNGramPipe(name, inputFile, ignoreCase, i));
        }
        return pipes;
    }

    public static void main(String[] args) throws Exception {
        File tempFile = File.createTempFile("ngram", "txt");
        // System.out.println( "Your temp file is " +
        // tempFile.getCanonicalPath() );
        // Arrange for it to be deleted at exit.
        // tempFile.deleteOnExit();

        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        bw.write("one two three four five six seven eight nine ten");
        bw.close();

        getAllGramsPipes("test", tempFile, true);

        bw = new BufferedWriter(new FileWriter(tempFile));
        // bw.write( "Parvicellular part of ventral anterior nucleus" );
        bw.write("ventral anterior nucleus");
        bw.close();

        getAllGramsPipes("test", tempFile, true);
    }
}
