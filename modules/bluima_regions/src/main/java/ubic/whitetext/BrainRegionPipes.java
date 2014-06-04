package ubic.whitetext;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.BrainRegionsHelper.LEXICON_HOME;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.Target2LabelSequence;
import cc.mallet.pipe.TokenSequence2FeatureVectorSequence;
import cc.mallet.pipe.tsf.LexiconMembership;
import cc.mallet.pipe.tsf.RegexMatches;
import cc.mallet.pipe.tsf.TokenText;
import cc.mallet.pipe.tsf.TrieLexiconMembership;
import cc.mallet.share.upenn.ner.FeatureWindow;
import cc.mallet.share.upenn.ner.LengthBins;
import cc.mallet.share.upenn.ner.LongRegexMatches;
import cc.mallet.share.upenn.ner.LongRegexSpaced;
import ch.epfl.bbp.uima.ae.Jcas2TokenSequence;

/**
 * Mallet {@link Pipe}s for feature extraction.
 * 
 * @author Leon French
 * @author renaud.richardet@epfl.ch
 */
public class BrainRegionPipes {

    final private static int window = 2; // /StaticOption.getInt("Window");

    public static List<Pipe> getPipes() throws Exception {

        List<Pipe> pipes = newArrayList();

        pipes.add(new Jcas2TokenSequence());
        pipes.add(new Target2LabelSequence());

        // more piiiiipes
        addAllGoodPipes(pipes);

        pipes.add(new FeatureWindow(window, window));
        // for debugging pipes.add(new PrintInputAndTarget());
        pipes.add(new TokenSequence2FeatureVectorSequence());
        return pipes;
    }

    private static final boolean ignoreCase = true;

    public static void addAllGoodPipes(List<Pipe> pipes) throws Exception {

        List<String> usedPipeNames = new LinkedList<String>();

        // / if (StaticOption.getBoolean("TextPipe"))
        addTextPipe(usedPipeNames, pipes);

        // ren/ addOriginalMarkupPipes();
        addAreaRegexPipes(usedPipeNames, pipes);
        // this catches tracts, change?
        // /if (StaticOption.getBoolean("SubstringRegexPipes"))
        addSubstringRegexPipes(usedPipeNames, pipes);
        addSpineRegexPipes(usedPipeNames, pipes);

        // /if (StaticOption
        // .getBoolean("SmallLexicons_TextPressoPipes_BrainRegionLexicons_AbbreviationLexiconPipes_AreaLexicons"))
        addSmallLexicons(usedPipeNames, pipes, ignoreCase);
        addTextPressoPipes(usedPipeNames, pipes, ignoreCase);
        addBrainRegionLexicons(usedPipeNames, pipes, ignoreCase);
        // ren/ addPigeonLexicon(usedPipeNames, pipes, ignoreCase);
        addAbbreviationLexiconPipes(usedPipeNames, pipes);
        addAreaLexicons(usedPipeNames, pipes, ignoreCase);

        addLengthPipes(usedPipeNames, pipes);

        if (Jcas2TokenSequence.NEW_FEATURES)
            addFullTextPipes(usedPipeNames, pipes);

        // / if (StaticOption.getBoolean("HandMadeRegexPipes_MalletNEPipes")) {
        addHandMadeRegexPipes(usedPipeNames, pipes);
        addMalletNEPipes(usedPipeNames, pipes);
    }

    /** Pipes added based on experience with full text */
    private static void addFullTextPipes(List<String> usedPipeNames,
            List<Pipe> pipes) {

        // blabla 24 24
        pipes.add(new LongRegexSpaced("digit_then_other_then_digit", Pattern
                .compile("\\d+[^\\d]+\\d+"), 2, 4));

        // 30 mM K SO , 5 mM MgCl 6H O, 10 mM 24 24 22 HEPES
        pipes.add(new LongRegexSpaced(
                "digit_then_other_then_digit_then_other_then_digit", Pattern
                        .compile(".*\\d+[^\\d\\n]+\\d+[^\\d\\n]+\\d+.*"), 4, 9));

        // n 19
        // n 5
        pipes.add(new LongRegexSpaced("n_space_digit", Pattern
                .compile("n \\d+"), 2, 2));
        pipes.add(new LongRegexSpaced("parenthesis_n_space_digit_parenthesis",
                Pattern.compile("\\( n \\d+ \\)"), 3, 4));
        pipes.add(new LongRegexSpaced("n_space_digit_parenthesis", Pattern
                .compile("n \\d+ \\)"), 3, 4));
        pipes.add(new LongRegexSpaced("parenthesis_n_space_digit", Pattern
                .compile("\\( n \\d+"), 3, 4));

        // Fig is never found in any lexicon
        pipes.add(new RegexMatches("Figure", Pattern.compile(".*Fig.*")));
    }

    private static void addAbbreviationLexiconPipes(List<String> usedPipeNames,
            List<Pipe> pipes) throws IOException {

        usedPipeNames.add("AbbrevLex");
        File ratMouse = new File(LEXICON_HOME + "NN2007RatMouseAbbrev.txt");
        File human = new File(LEXICON_HOME + "NN2002HumanAbbrev.txt");
        boolean ignoreCase = true;
        // should be one word only but may not..
        pipes.add(new TrieLexiconMembership("NNHumanAbbrev", human, ignoreCase));
        pipes.add(new TrieLexiconMembership("NNRatMouseAbbrev", ratMouse,
                ignoreCase));

        addPrefixPipes(pipes, ratMouse, "NNHumanAbbrevPrefix");
        addPrefixPipes(pipes, human, "NNRatMouseAbbrevPrefix");
    }

    public static void addPrefixPipes(List<Pipe> pipes, File file, String name)
            throws IOException {
        for (String line : linesFrom(file.getAbsolutePath())) {
            pipes.add(new RegexMatches(name, compile("(" + line.trim()
                    + ".{1,3})", CASE_INSENSITIVE)));
        }
    }

    public static void addTextPipe(List<String> usedPipeNames, List<Pipe> pipes)
            throws Exception {
        usedPipeNames.add("Text");
        pipes.add(new TokenText("text="));
    }

    public static void addAreaRegexPipes(List<String> usedPipeNames,
            List<Pipe> pipes) {
        usedPipeNames.add("Area regexes");

        pipes.add(new LongRegexSpaced("Brodmann", Pattern
                .compile("areas? \\d+((, ?\\d)*,? (or|and) \\d+)?"), 2, 9));

        // a looser version that allows just letters
        pipes.add(new LongRegexSpaced(
                "Areas",
                compile("areas? (\\p{Upper}|\\d)+((, ?(\\p{Upper}|\\d))*,? (or|and) (\\p{Upper}|\\d)+)?"),
                2, 9));

    }

    public static void addSubstringRegexPipes(List<String> usedPipeNames,
            List<Pipe> pipes) throws Exception {
        usedPipeNames.add("Substring regexes");

        // "thalamic" and nuclie are probably in the 1-grams
        for (String substring : new String[] { "cortic", "cerebel" }) {
            pipes.add(new RegexMatches(substring + "Regex", compile(".*"
                    + substring + ".*", CASE_INSENSITIVE)));
        }
    }

    public static void addSpineRegexPipes(List<String> usedPipeNames,
            List<Pipe> pipes) throws Exception {
        usedPipeNames.add("SpineRegex");
        // T1-T12
        // L1-L5
        // S1-S5
        // C1-C8
        pipes.add(new LongRegexMatches("SpinalParts", Pattern
                .compile("([LS][1-5])|T((1[0-2]?)|[2-9])|(C[1-8])"), 1, 2));

    }

    public static void addSmallLexicons(List<String> usedPipeNames,
            List<Pipe> pipes, boolean ignoreCase) throws FileNotFoundException {
        usedPipeNames.add("SmallLex");
        pipes.add(new LexiconMembership("chudlerListWord", new File(
                LEXICON_HOME + "chudler.txt"), ignoreCase));
        pipes.add(new LexiconMembership("directionWord", new File(LEXICON_HOME
                + "directions.txt"), ignoreCase));
        pipes.add(new LexiconMembership("extendedDirectionWord", new File(
                LEXICON_HOME + "extendedDirections.txt"), ignoreCase));
        pipes.add(new LexiconMembership("stopWord", new File(LEXICON_HOME
                + "stop.txt"), ignoreCase));
    }

    public static void addTextPressoPipes(List<String> usedPipeNames,
            List<Pipe> pipes, boolean ignoreCase) throws FileNotFoundException,
            Exception {
        usedPipeNames.add("TextPresso");
        // TEXTPRESSO files, files are split by how many tokens
        for (int i = 1; i < 8; i++) {
            pipes.add(new TrieLexiconMembership("textPresso" + i, new File(
                    LEXICON_HOME + "TextPresso-wordLength-" + i + ".txt"),
                    ignoreCase));
        }
        pipes.add(new TrieLexiconMembership("textPressoAll", new File(
                LEXICON_HOME + "TextPresso-all.txt"), ignoreCase));

        pipes.addAll(NGramPipeFactory.getAllGramsPipes("textPressoAll",
                new File(LEXICON_HOME + "TextPresso-all.txt"), ignoreCase));
    }

    public static void addBrainRegionLexicons(List<String> usedPipeNames,
            List<Pipe> pipes, boolean ignoreCase) throws FileNotFoundException,
            Exception {
        usedPipeNames.add("BrainRegions");
        // BRAINREGION Lexicons
        pipes.add(new TrieLexiconMembership("NNHu", new File(LEXICON_HOME
                + "NN2002Human.txt"), ignoreCase));
        pipes.add(new TrieLexiconMembership("NNMouseRat", new File(LEXICON_HOME
                + "NN2007RatMouse.txt"), ignoreCase));
        pipes.add(new TrieLexiconMembership("Allen", new File(LEXICON_HOME
                + "Allen.txt"), ignoreCase));
        pipes.add(new TrieLexiconMembership("BAMS", new File(LEXICON_HOME
                + "BAMS.txt"), ignoreCase));
        pipes.add(new TrieLexiconMembership("AllRegions", new File(LEXICON_HOME
                + "AllRegions.txt"), ignoreCase));

        pipes.addAll(NGramPipeFactory.getAllGramsPipes("AllRegions", new File(
                LEXICON_HOME + "AllRegions.txt"), ignoreCase));
    }

    public static void addAreaLexicons(List<String> usedPipeNames,
            List<Pipe> pipes, boolean ignoreCase) throws FileNotFoundException {
        usedPipeNames.add("Areawords");
        pipes.add(new LexiconMembership("areawords", new File(LEXICON_HOME
                + "areawords.txt"), ignoreCase));
    }

    public static void addHandMadeRegexPipes(List<String> usedPipeNames,
            List<Pipe> pipes) throws Exception {
        usedPipeNames.add("Handmade regexes");
        pipes.add(new LongRegexSpaced("of_The", compile("of the"), 2, 2));
        pipes.add(new LongRegexSpaced("part_Of", compile("part of"), 2, 2));
        pipes.add(new LongRegexSpaced("neurnEnd", compile("(.* neurons)"), 2, 3));
        pipes.add(new LongRegexSpaced("nucleiEnd", compile("(.* nuclei)"), 2, 3));
        pipes.add(new LongRegexSpaced("nclusEnd", compile("(.* nucleus)"), 2, 5));
        pipes.add(new LongRegexSpaced("fieldEnd", compile("(.* field)"), 2, 4));
        pipes.add(new LongRegexSpaced("cortexEnd", compile("(.* cortex)"), 2, 3));
        pipes.add(new LongRegexSpaced("areaEnd", compile("(.* area)"), 2, 4));
        pipes.add(new LongRegexSpaced("territoryEnd",
                compile("(.* territory)|(.* territories)"), 2, 4));
    }

    public static void addLengthPipes(List<String> usedPipeNames,
            List<Pipe> pipes) throws Exception {
        usedPipeNames.add("Length");
        // length feature - binary bins
        pipes.add(new LengthBins("Length", new int[] { 1, 2, 3, 5, 8, 11, 14,
                18, 22 }));

        // from some calcs the average brain token is 6.92 while the outside is
        // 4.64 (~3.55 stdev)
        pipes.add(new LengthBins("LengthThreshold", new int[] { 6 }));
    }

    public static void addMalletNEPipes(List<String> usedPipeNames,
            List<Pipe> pipes) throws Exception {
        usedPipeNames.add("Mallet NE");
        // random pipes from general NER
        pipes.addAll(new NEPipes().pipes());
    }
}
