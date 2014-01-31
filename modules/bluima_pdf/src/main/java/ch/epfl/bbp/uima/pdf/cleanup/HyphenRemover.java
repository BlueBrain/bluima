package ch.epfl.bbp.uima.pdf.cleanup;

import static ch.epfl.bbp.StringUtils.print;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class allows to decide whether an hyphen should be removed from a line.
 * 
 * <h3>Rules</h3>
 * <ul>
 * <li>At the end of lines, there should be at least 3 characters behind and 2
 * forward</li>
 * <li>Ignore if numbers? (for the moment yes)</li>
 * <li>Ignore if capital letters forward? (for the moment no)</li>
 * </ul>
 * <h3>Comments</h3>
 * <ul>
 * <li>First rule not respected in PDFs, so cannot be applied (sticked to: 2
 * behind and 2 forward)</li>
 * <li>Be careful to remove ligatures before, because not in [A-Za-z]</li>
 * <li>Accents are not considered (in an other code, range [A-Za-z\300-\377])</li>
 * </ul>
 * 
 * <h3>Possible hyphens in English (see <a
 * href="http://www.unicode.org/versions/latest/ch06.pdf">Unicode, chapter 6,
 * Table 6-3</a> and http://en.wikipedia.org/wiki/Hyphens#Unicode)</h3>
 * <ul>
 * <li>U+002D HYPHEN-MINUS (mainly used)</li>
 * <li>U+00AD SOFT HYPHEN</li>
 * <li>U+2010 ‐ hyphen (HTML: &#8208;)</li>
 * </ul>
 * 
 * @author orianne.rollier@epfl.ch
 * @author renaud.richardet@epfl.ch
 */
public class HyphenRemover {
    private static Logger LOG = LoggerFactory.getLogger(HyphenRemover.class);

    /** Matches a dash (all kinds, see above) at the end of a line */
    public static final Pattern patternHyphen = Pattern
            .compile("[\\u002D\\u00AD\\u2010]\\s*$");

    private static final String WORD_SEPARATOR = "\\W+";
    private static final Pattern numberEnd = Pattern.compile("[0-9]-$");
    private static final Pattern numberStart = Pattern.compile("^[0-9]");
    private static final Pattern greekEnd = Pattern.compile("\\p{InGreek}-$");
    private static final Pattern greekStart = Pattern.compile("^\\p{InGreek}+");

    /**
     * Decides whether a word should be dehyphenated
     * 
     * @param w1
     *            word behind
     * @param w2
     *            word forward
     * @return returns true if the word should be dehyphenated
     */
    private static boolean shouldDehyphenate(String w1, String w2) {

        boolean dehyphenate = true;
        String rule = "DH"; // choice by default, dehyphenate

        if (w1.length() <= 1) {
            // w1 has only one char
            dehyphenate = false;
            rule = "N11";
        } else if (w2.length() <= 1) {
            // w2 has only one char
            dehyphenate = false;
            rule = "N12";
        } else if (numberEnd.matcher(w1).find()) {
            // number at the end of w1
            dehyphenate = false;
            rule = "N21";
        } else if (numberStart.matcher(w2).find()) {
            // number at the beginning of w2
            dehyphenate = false;
            rule = "N22";
        } else if (greekEnd.matcher(w1).find()) {
            // greek letter at the end of w1
            dehyphenate = false;
            rule = "N31";
        } else if (greekStart.matcher(w2).find()) {
            // greek letter at the beginning of w2
            dehyphenate = false;
            rule = "N32";
        }

        if (dehyphenate) {
            String word = w1.substring(0, w1.length() - 1) + w2;
            LOG.debug("\"" + w1 + "\",\"" + w2 + "\",\"" + rule + "\",\""
                    + word + "\"\n");
        } else {
            LOG.debug("\"" + w1 + "\",\"" + w2 + "\",\"" + rule + "\",\"" + w1
                    + w2 + "\"\n");
        }
        return dehyphenate;
    }

    /** Removes ligatures, multiple spaces and hypens from a text file */
    public static String dehyphenate(LineNumberReader reader, String docId) {

        String line;
        StringBuilder sb = new StringBuilder();

        try {
            while ((line = reader.readLine()) != null) {
                String lineOut = line;
                Matcher matcher = patternHyphen.matcher(line);

                // Allows to append consecutive lines with hyphens
                while (matcher.find() && ((line = reader.readLine()) != null)) {
                    String behind[] = lineOut.split(WORD_SEPARATOR);
                    String forward[] = line.trim().split(WORD_SEPARATOR);

                    if (behind.length == 0) {
                        lineOut = lineOut + "\n" + line;
                        continue;
                    }

                    String w1 = behind[(behind.length) - 1];
                    String w2 = forward[0];
                    if (shouldDehyphenate(w1, w2)) {
                        // Remove hyphen from first line and concatenates second
                        lineOut = lineOut.substring(0, lineOut.length() - 1)
                                .trim() + line.trim();
                    } else {
                        // just concanates the two lines
                        lineOut = lineOut + line;
                    }
                    matcher = patternHyphen.matcher(lineOut);
                }

                sb.append(lineOut.trim());
                sb.append("\n");
            }
        } catch (Throwable t) {
            //TODO happens sometimes, e.g. docId 1279669
            LOG.warn("failed to dehyphenate, docId " + docId, print(t)); 
        }
        return sb.toString();
    }

    /** Removes ligatures, multiple spaces and hypens from a text file */
    public static String dehyphenate(String text, String docId) {
        return dehyphenate(new LineNumberReader(new StringReader(text)), docId);
    }
}
