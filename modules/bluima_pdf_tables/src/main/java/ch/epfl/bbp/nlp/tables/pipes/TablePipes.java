/**
 * 
 */
package ch.epfl.bbp.nlp.tables.pipes;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.compile;

import java.util.List;

import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.PrintInputAndTarget;
import cc.mallet.pipe.Target2LabelSequence;
import cc.mallet.pipe.TokenSequence2FeatureVectorSequence;
import cc.mallet.pipe.tsf.RegexMatches;
import cc.mallet.share.upenn.ner.FeatureWindow;

/**
 * 
 * TODO document
 * 
 * @author samuel
 * 
 */
public class TablePipes {

    final private static int window = 2;

    public static List<Pipe> getPipes() throws Exception {

        List<Pipe> pipes = newArrayList();

        pipes.add(new Jcas2TokenSequence());
        pipes.add(new Target2LabelSequence());

        addPipes(pipes);

        pipes.add(new FeatureWindow(window, window));

        // for debugging
     //   pipes.add(new PrintInputAndTarget());

        pipes.add(new TokenSequence2FeatureVectorSequence());
        return pipes;
    }

    private static void addPipes(List<Pipe> pipes) {

        /*
         * This pipe is used in order not to have an empty feature list It is
         * also used to see the texts that were misclassified
         */
        // pipes.add(new TokenText("text="));

        /*
         * This pipe is used in order not to have an empty feature list It is
         * used when the first one is not.
         */
         pipes.add(new RegexMatches("NoneEmpty_regex", compile(".*")));

        /*
         * This is used to check if the text line is the first one of the page.
         * this was initially included to cancel the headers.
         */
        // pipes.add(new FirstLinePipe("FirstLine"));

        /*
         * This is used to check the ratio of a certain pattern to the all text
         */
        pipes.add(new PatternRatioPipe("digit_regex", compile("\\d"), 0.7));
        pipes.add(new PatternRatioPipe("Whitespaceratio0.3_regex",
                compile("\\s"), 0.3));
        pipes.add(new PatternRatioPipe("Whitespaceratio0.5_regex",
                compile("\\s"), 0.5));

        /*
         * This is used to check the number of cells using Spaces as delimiters
         */
        pipes.add(new CellsPresencePipe("2plusSpace1Cells",
                compile("\\s{2,}[\\w,\\d]{1,}"), 1));
        pipes.add(new CellsPresencePipe("2plusSpace2Cells",
                compile("\\s{2,}[\\w,\\d]{1,}"), 2));
        pipes.add(new CellsPresencePipe("3plusSpace2Cells",
                compile("\\s{3,}[\\w,\\d]{1,}"), 2));
        pipes.add(new CellsPresencePipe("2plusSpace3Cells",
                compile("\\s{2,}[\\w,\\d]{1,}"), 3));
        pipes.add(new CellsPresencePipe("2plusSpace4Cells",
                compile("\\s{2,}[\\w,\\d]{1,}"), 4));
        pipes.add(new CellsPresencePipe("2plusSpace2Cellsd",
                compile("\\s{2,}[\\d]{1,}"), 1));
        pipes.add(new CellsPresencePipe("2plusSpace2Cellsd",
                compile("\\s{2,}[\\d]{1,}"), 2));
        pipes.add(new CellsPresencePipe("3plusSpace2Cellsd",
                compile("\\s{3,}[\\d]{1,}"), 2));
        pipes.add(new CellsPresencePipe("2plusSpace3Cellsd",
                compile("\\s{2,}[\\d]{1,}"), 3));
        pipes.add(new CellsPresencePipe("2plusSpace4Cellsd",
                compile("\\s{2,}[\\d]{1,}"), 4));
        pipes.add(new CellsPresencePipe("2plusSpace1Cellsw",
                compile("\\s{2,}[\\w]{1,}"), 1));
        pipes.add(new CellsPresencePipe("2plusSpace2Cellsw",
                compile("\\s{2,}[\\w]{1,}"), 2));
        pipes.add(new CellsPresencePipe("3plusSpace2Cellsw",
                compile("\\s{3,}[\\w]{1,}"), 2));
        pipes.add(new CellsPresencePipe("2plusSpace3Cellsw",
                compile("\\s{2,}[\\w]{1,}"), 3));
        pipes.add(new CellsPresencePipe("2plusSpace4Cellsw",
                compile("\\s{2,}[\\w]{1,}"), 4));

        // /*
        // * This is used to check the number of cells
        // * using Tabs as delimiters
        // * */
        // pipes.add(new CellsPresencePipe("2plusTab2Cellsd",
        // compile("\\t{1,}[\\d]{1,}"), 2));
        // pipes.add(new CellsPresencePipe("1plusTab2Cellsw",
        // compile("\\t{1,}[\\w]{1,}"), 1));
        // pipes.add(new CellsPresencePipe("1plusTab2Cellsw",
        // compile("\\t{1,}[\\w]{1,}"), 2));
        // pipes.add(new CellsPresencePipe("1plusTab3Cellsw",
        // compile("\\t{1,}[\\w]{1,}"), 3));

        /*
         * This is used to check the beginning of certain sections
         */
        pipes.add(new RegexMatches("Table_regex",
                compile("[(^Table.*)|(^TABLE.*)]")));
        pipes.add(new RegexMatches("Reference_regex",
                compile("^(References)|^(REFERENCES)")));

        /*
         * This is used to check if the beginning of a reference so they would
         * not be mistaken
         */
        pipes.add(new RegexMatches("Reference_regex_begin1",
                compile("^[A-Z]\\.")));
        // pipes.add(new RegexMatches("Reference_regex_begin2",
        // compile("^[A-Z]\\w{1,}\\s{1,}[A-Z]")));

        /*
         * This is used to check punctuation in a text line table lines tends
         * not to have punctuation
         */
        pipes.add(new RegexMatches("Punc1", compile(".*([*_]).*")));
        pipes.add(new RegexMatches("Punc2", compile("[\\w]{1,}([*_])[\\w]{1,}")));
        pipes.add(new RegexMatches("Punc3", compile("[\\d]{1,}([*_])[\\d]{1,}")));
        pipes.add(new RegexMatches("Punc4", compile(".*([-+]).*")));
        pipes.add(new RegexMatches("Punc5", compile("[\\d]{1,}([-+])[\\d]{1,}")));
        pipes.add(new RegexMatches("Punc6", compile("[\\w]{1,}([-+])[\\w]{1,}")));
        pipes.add(new RegexMatches("Punc7", compile("\\w\\.\\s.*")));
        pipes.add(new RegexMatches("Punc8", compile("[\\d]{1,}\\.[\\d]{1,}")));
        pipes.add(new RegexMatches("Punc9", compile("[\\w]{1,}\\.\\s[\\w]{1,}")));
        pipes.add(new RegexMatches("Punc10", compile("\\w[,]\\s.*")));
        pipes.add(new RegexMatches("Punc11", compile("[\\d]{1,}[,][\\d]{1,}")));
        pipes.add(new RegexMatches("Punc12", compile(".*\\s([=])\\s.*")));
        pipes.add(new RegexMatches("Punc13",
                compile("[\\w]{1,}\\s([=])\\s[\\w]{1,}")));
        pipes.add(new RegexMatches("Punc14",
                compile("[\\d]{1,}\\s([=])\\s[\\d]{1,}")));

        pipes.add(new RegexMatches("EndSentPunc1", compile(".*([.!?])$")));
        pipes.add(new RegexMatches("EndSentPunc2", compile(".*([.!?])\\s.*")));

        /*
         * This is used to investigate the white spaces contained in a line.
         */
        pipes.add(new RegexMatches("StartingWhitespace1_3_regex",
                compile("^\\s{1,3}.*")));
        pipes.add(new RegexMatches("StartingWhitespace4_10_regex",
                compile("^\\s{4,10}.*")));
        pipes.add(new RegexMatches("StartingWhitespace11_15_regex",
                compile("^\\s{11,15}.*")));
        pipes.add(new RegexMatches("StartingWhitespace16_regex",
                compile("^\\s{16,}.*")));
        pipes.add(new RegexMatches("More2_4Whitespace_regex",
                compile(".*\\s{2,4}.*")));
        pipes.add(new RegexMatches("More5_10Whitespace_regex",
                compile(".*\\s{5,10}.*")));
        pipes.add(new RegexMatches("More11_15Whitespace_regex",
                compile(".*\\s{11,15}.*")));
        pipes.add(new RegexMatches("More16Whitespace_regex",
                compile(".*\\s{16,}.*")));
        // pipes.add(new RegexMatches("BlankLine_regex", compile("^\\s*$")));
        // pipes.add(new RegexMatches("4dots_regex", compile(".*\\.{4}.*")));
    }
}
