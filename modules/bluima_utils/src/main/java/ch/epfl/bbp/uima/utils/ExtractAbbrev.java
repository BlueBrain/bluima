package ch.epfl.bbp.uima.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * The ExtractAbbrev class implements a simple algorithm for extraction of abbreviations and their definitions from biomedical text. Abbreviations
 * (short forms) are extracted from the input file, and those abbreviations for which a definition (long form) is found are printed out, along with
 * that definition, one per line.
 * 
 * A file consisting of short-form/long-form pairs (tab separated) can be specified in tandem with the -testlist option for the purposes of evaluating
 * the algorithm.
 * 
 * @see <a href="http://biotext.berkeley.edu/papers/psb03.pdf">A Simple Algorithm for Identifying Abbreviation Definitions in Biomedical Text</a>
 *      A.S. Schwartz, M.A. Hearst; Pacific Symposium on Biocomputing 8:451-462(2003) for a detailed description of the algorithm.
 * 
 * Software available here: http://biotext.berkeley.edu/code/abbrev/ExtractAbbrev.java
 * 
 * This version has been modified to allow String input. 08/19/08
 * 
 * @author Ariel Schwartz
 * @version 03/12/03
 * 
 */
public class ExtractAbbrev {

    HashMap mTestDefinitions = new HashMap();

    HashMap mStats = new HashMap();

    int truePositives = 0, falsePositives = 0, falseNegatives = 0, trueNegatives = 0;

    char delimiter = '\t';

    boolean testMode = false;

    public boolean isValidShortForm(String str) {
        return (hasLetter(str) && (Character.isLetterOrDigit(str.charAt(0)) || (str.charAt(0) == '(')));
    }

    public boolean hasLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetter(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCapital(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public void loadTrueDefinitions(String inFile) {
        String abbrString, defnString, str = "";
        Vector entry;
        HashMap definitions = mTestDefinitions;

        try {
            BufferedReader fin = new BufferedReader(new FileReader(inFile));
            while ((str = fin.readLine()) != null) {
                int j = str.indexOf(delimiter);
                abbrString = str.substring(0, j).trim();
                defnString = str.substring(j, str.length()).trim();
                entry = (Vector) definitions.get(abbrString);
                if (entry == null) {
                    entry = new Vector();
                }
                entry.add(defnString);
                definitions.put(abbrString, entry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(str);
        }
    }

    public boolean isTrueDefinition(String shortForm, String longForm) {
        Vector entry;
        Iterator itr;

        entry = (Vector) mTestDefinitions.get(shortForm);
        if (entry == null) {
            return false;
        }
        itr = entry.iterator();
        while (itr.hasNext()) {
            if (itr.next().toString().equalsIgnoreCase(longForm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method altered, 08/19/08. Processing code moved to extractAbbrPairs(Reader) to facilitate extraction of abbreviations directly from text (i.e.
     * a String not a file).
     * 
     * @param inFile
     * @return
     */
    public Map<String, String> extractAbbrPairs(String inFile) {
        FileReader fr;
        try {
            fr = new FileReader(inFile);
            return extractAbbrPairs(fr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new HashMap<String, String>();
        }
    }

    /**
     * Extract abbreviation pairs directly from input text.
     * 
     * Method added by University of Colorado, Center for Computational Pharmacology, 08/19/08
     * 
     * @param inputText
     * @return
     */
    public Map<String, String> extractAbbrPairs(String inputText, boolean isFileName) {

        if (isFileName) {
            return extractAbbrPairs(inputText);
        }

        StringReader sr = new StringReader(inputText);
        return extractAbbrPairs(sr);
    }

    /**
     * Processing code for extracting abbreviation pairs has been placed into a private method to facilitate extracting pairs directly from text (i.e.
     * from a String not a file).
     * 
     * @param r
     * @param candidates
     */
    private Map<String, String> extractAbbrPairs(Reader r) {
        Map<String, String> candidates = new HashMap<String, String>();
        String str, tmpStr, longForm = "", shortForm = "";
        String currSentence = "";
        int openParenIndex, closeParenIndex = -1, sentenceEnd, newCloseParenIndex, tmpIndex = -1;
        boolean newParagraph = true;
        StringTokenizer shortTokenizer;

        try {

            BufferedReader fin = new BufferedReader(r);
            while ((str = fin.readLine()) != null) {
                if (str.length() == 0 || newParagraph && !Character.isUpperCase(str.charAt(0))) {
                    currSentence = "";
                    newParagraph = true;
                    continue;
                }
                newParagraph = false;
                str += " ";
                currSentence += str;
                openParenIndex = currSentence.indexOf(" (");
                do {
                    if (openParenIndex > -1) {
                        openParenIndex++;
                    }
                    sentenceEnd = Math.max(currSentence.lastIndexOf(". "), currSentence.lastIndexOf(", "));
                    if ((openParenIndex == -1) && (sentenceEnd == -1)) {
                        // Do nothing
                    } else if (openParenIndex == -1) {
                        currSentence = currSentence.substring(sentenceEnd + 2);
                    } else if ((closeParenIndex = currSentence.indexOf(')', openParenIndex)) > -1) {
                        sentenceEnd = Math.max(currSentence.lastIndexOf(". ", openParenIndex), currSentence.lastIndexOf(", ",
                                openParenIndex));
                        if (sentenceEnd == -1) {
                            sentenceEnd = -2;
                        }
                        longForm = currSentence.substring(sentenceEnd + 2, openParenIndex);
                        shortForm = currSentence.substring(openParenIndex + 1, closeParenIndex);
                    }
                    if (shortForm.length() > 0 || longForm.length() > 0) {
                        if (shortForm.length() > 1 && longForm.length() > 1) {
                            if ((shortForm.indexOf('(') > -1)
                                    && ((newCloseParenIndex = currSentence.indexOf(')', closeParenIndex + 1)) > -1)) {
                                shortForm = currSentence.substring(openParenIndex + 1, newCloseParenIndex);
                                closeParenIndex = newCloseParenIndex;
                            }
                            if ((tmpIndex = shortForm.indexOf(", ")) > -1) {
                                shortForm = shortForm.substring(0, tmpIndex);
                            }
                            if ((tmpIndex = shortForm.indexOf("; ")) > -1) {
                                shortForm = shortForm.substring(0, tmpIndex);
                            }
                            shortTokenizer = new StringTokenizer(shortForm);
                            if (shortTokenizer.countTokens() > 2 || shortForm.length() > longForm.length()) {
                                // Long form in ( )
                                tmpIndex = currSentence.lastIndexOf(" ", openParenIndex - 2);
                                tmpStr = currSentence.substring(tmpIndex + 1, openParenIndex - 1);
                                longForm = shortForm;
                                shortForm = tmpStr;
                                if (!hasCapital(shortForm)) {
                                    shortForm = "";
                                }
                            }
                            if (isValidShortForm(shortForm)) {
                                List<String> list = extractAbbrPair(shortForm.trim(), longForm.trim());
                                if (list.size() == 2) {
                                    candidates.put(list.get(0), list.get(1));
                                }
                            }
                        }
                        currSentence = currSentence.substring(closeParenIndex + 1);
                    } else if (openParenIndex > -1) {
                        if ((currSentence.length() - openParenIndex) > 200) {
                            // Matching close paren was not found
                            currSentence = currSentence.substring(openParenIndex + 1);
                        }
                        break; // Read next line
                    }
                    shortForm = "";
                    longForm = "";
                } while ((openParenIndex = currSentence.indexOf(" (")) > -1);
            }
            fin.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
            System.out.println(currSentence);
            System.out.println(tmpIndex);
        }
        return candidates;
    }

    public String findBestLongForm(String shortForm, String longForm) {
        int sIndex;
        int lIndex;
        char currChar;

        sIndex = shortForm.length() - 1;
        lIndex = longForm.length() - 1;
        for (; sIndex >= 0; sIndex--) {
            currChar = Character.toLowerCase(shortForm.charAt(sIndex));
            if (!Character.isLetterOrDigit(currChar)) {
                continue;
            }
            while (((lIndex >= 0) && (Character.toLowerCase(longForm.charAt(lIndex)) != currChar))
                    || ((sIndex == 0) && (lIndex > 0) && (Character.isLetterOrDigit(longForm.charAt(lIndex - 1))))) {
                lIndex--;
            }
            if (lIndex < 0) {
                return null;
            }
            lIndex--;
        }
        lIndex = longForm.lastIndexOf(" ", lIndex) + 1;
        return longForm.substring(lIndex);
    }

    public List<String> extractAbbrPair(String shortForm, String longForm) {

        List<String> abbrPair = new ArrayList<String>();

        String bestLongForm;
        StringTokenizer tokenizer;
        int longFormSize, shortFormSize;

        if (shortForm.length() == 1) {
            return abbrPair;
        }
        bestLongForm = findBestLongForm(shortForm, longForm);
        if (bestLongForm == null) {
            return abbrPair;
        }
        tokenizer = new StringTokenizer(bestLongForm, " \t\n\r\f-");
        longFormSize = tokenizer.countTokens();
        shortFormSize = shortForm.length();
        for (int i = shortFormSize - 1; i >= 0; i--) {
            if (!Character.isLetterOrDigit(shortForm.charAt(i))) {
                shortFormSize--;
            }
        }
        if (bestLongForm.length() < shortForm.length() || bestLongForm.indexOf(shortForm + " ") > -1 || bestLongForm.endsWith(shortForm)
                || longFormSize > shortFormSize * 2 || longFormSize > shortFormSize + 5 || shortFormSize > 10) {
            return abbrPair;
        }

        if (testMode) {
            if (isTrueDefinition(shortForm, bestLongForm)) {
                System.out.println(shortForm + delimiter + bestLongForm + delimiter + "TP");
                truePositives++;
            } else {
                falsePositives++;
                System.out.println(shortForm + delimiter + bestLongForm + delimiter + "FP");
            }
        } else {
            // System.out.println(shortForm + delimiter + bestLongForm);
            abbrPair.add(shortForm);
            abbrPair.add(bestLongForm);
        }
        return abbrPair;
    }

    public static void usage() {
        System.err.println("Usage: ExtractAbbrev [-options] <filename>");
        System.err.println("       <filename> contains text from which abbreviations are extracted");
        System.err.println("       -testlist <file> = list of true abbreviation definition pairs");
        System.err.println("       -usage or -help = this message");
    }

    public static void main(String[] args) {
        String shortForm, longForm, defnString, str;
        ExtractAbbrev extractAbbrev = new ExtractAbbrev();
        Vector candidates;
        String[] candidate;
        String filename = null;
        String testList = null;

        // parse arguments
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-testlist")) {
                if (i == args.length - 1) {
                    usage();
                }
                testList = args[++i];
                extractAbbrev.testMode = true;
            } else if (args[i].equals("-usage")) {
                usage();
            } else if (args[i].equals("-help")) {
                usage();
            } else {
                filename = args[i];
                // Must be last arg
                if (i != args.length - 1) {
                    usage();
                }
            }
        }
        if (filename == null) {
            usage();
        }

        if (extractAbbrev.testMode) {
            extractAbbrev.loadTrueDefinitions(testList);
        }
        extractAbbrev.extractAbbrPairs(filename);
        if (extractAbbrev.testMode) {
            System.out.println("TP: " + extractAbbrev.truePositives + " FP: " + extractAbbrev.falsePositives + " FN: "
                    + extractAbbrev.falseNegatives + " TN: " + extractAbbrev.trueNegatives);
        }
    }
}
