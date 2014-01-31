package ch.epfl.bbp.uima.references;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.compile;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;
import ch.epfl.bbp.io.LineReader;

import com.google.common.collect.Maps;

/**
 * 
 */
public class MyInput2RegexTokens extends Pipe implements Serializable {
    @SuppressWarnings("unused")
    private static Logger LOG = getLogger(MyInput2RegexTokens.class);
    private static final long serialVersionUID = 1L;

    private Map<String, Pattern> regexes = Maps.newHashMap();

    public MyInput2RegexTokens() {

        // YEARS
        regexes.put("years", compile("19[56789]\\d|20[01]\\d"));
        regexes.put("years_abcd", compile("19[56789]\\d[abcd]|20[01]\\d[abcd]"));
        regexes.put("years_parenthesis",
                compile("\\((19[56789]\\d|20[01]\\d)\\)"));

        // VOLUMES, PAGES
        // 385-420
        regexes.put("volume", compile("\\d+ ?[–-] ?\\d+"));
        // Comp. Neurol. 167: 385-420
        regexes.put("volume_more", compile("\\d+: ?\\d{1,4} ?[–-] ?\\d{1,4}"));
        // pages
        regexes.put("pages", compile("p.? \\d+ [–-] \\\\d+"));

        // AUTHOR
        // Gurdjian, E. S.
        regexes.put("author1", compile("[A-Z]\\w+, [A-Z]\\."));
        // Beckstead RM (1979)
        regexes.put("author2", compile("[A-Z]\\w+ [A-Z][A-Z ,]"));
        // Newman, R., and S. S. Winans
        regexes.put("author3", compile(", and [A-Z]\\. [A-Z]"));
        // repetitions: Boussaoud D, Ungerleider LC, Desimone R
        regexes.put("author4", compile("(, [A-Z]\\w+ [A-Z]{1,2}){2,}"));
        // , {comma, name}
        regexes.put("author5", compile(", [A-Z]\\w+ [A-Z]"));
        // 4 Brodmann, K., V
        // 17. Sorensen OW,
        regexes.put("author6", compile("\\d{1,2}\\.? [A-Z]\\w+,? [A-Z]"));
        // S. Araki, Y. Tamori, M. Kawanishi, H. Shinoda, J. Masugi....
        regexes.put("author6",
                compile("(([A-Z]\\.)?[A-Z]\\. [A-Z]\\w+, ){2,10}"));
        // Diesmann, M., and Morrison, A.
        // Ferster, D., and Spruston, N.
        regexes.put("author7",
                compile("((and )?[A-Z]\\w+, ([A-Z]\\. ?){1,2},? ?){2,6}"));

        // DIV
        regexes.put("proceedings", compile("Proceedings of"));

        // ////////////////////
        // NEGATIVE EXAMPLES
        //
        // (Beckstead RM <-- parenthesis!
        regexes.put("neg_author_parenthesis",
                compile("\\([A-Z]\\w+ [A-Z][A-Z ,]"));
        // Gurdjian, E <-- parenthesis!
        regexes.put("neg_author_parenthesis2", compile("\\([A-Z]\\w+, [A-Z]"));
        // ng (Rosenmund et al., 1998; Smith and Howe, 2000), a
        regexes.put("neg_inline_ref", compile("\\([A-Z]\\w+.{3,40}\\d+\\)"));

        regexes.put("neg_figure", compile("^Fig(ure)?\\.? \\d+.*"));
        regexes.put("neg_table", compile("^Tab(le)?\\.? \\d+.*"));
        regexes.put("neg_copyright", compile("[Cc]opyright.{1,10}\\d{4}"));

    }

    public Instance pipe(Instance carrier) {

        if (carrier.getData() instanceof File) {
            try {
                // get file text
                File file = (File) carrier.getData();
                @SuppressWarnings("resource")
                String txt = new LineReader(new FileInputStream(file))
                        .getText("\n");
                // update instance values
                carrier.setData(new TokenSequence(addRegexes(txt)));
                carrier.setSource(txt + " [file:" + file.getName() + "]");
            } catch (java.io.IOException e) {
                throw new IllegalArgumentException("IOException " + e);
            }

        } else if (carrier.getData() instanceof String) {
            String txt = (String) carrier.getData();
            // update instance values
            carrier.setData(new TokenSequence(addRegexes(txt)));
            carrier.setSource(txt);

        } else {
            throw new IllegalArgumentException("must be file or string "
                    + carrier.getData());
        }
        return carrier;
    }

    public List<Token> addRegexes(String txt) {
        // LOG.debug("creating features for '{}'", txt);
        List<Token> data = newArrayList();

        // initialized to false
        boolean[] coveredPositions = new boolean[txt.length()];

        for (Entry<String, Pattern> regexEntry : regexes.entrySet()) {

            Matcher matcher = regexEntry.getValue().matcher(txt);
            while (matcher.find()) {

                for (int i = matcher.start(); i < matcher.end(); i++) {
                    coveredPositions[i] = true;
                }

                // LOG.debug("found feature {}: '{}' ", regexEntry.getKey(),
                // matcher.group());
                data.add(new Token(regexEntry.getKey()));
            }
        }
        

//        // add tokens not matching to compensate
//        int uncoveredCnt = 0;
//        for (boolean covered : coveredPositions) {
//            if (!covered)
//                uncoveredCnt++;
//        }
//        int ratio = 10 * uncoveredCnt / txt.length();
//        // assume words are on average 7 length
//        // int uncoveredWords = uncoveredCnt / 7;
//        for (int i = 0; i < ratio; i++) {
//            data.add(new Token("uncovered__"));
//        }

        return data;
    }
}