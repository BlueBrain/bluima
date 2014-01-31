package ch.epfl.bbp.uima.pdf.cleanup;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * In writing and typography, a ligature occurs where two or more letters are
 * joined as a single glyph. For example ﬁ (or f‌i, rendered with two normal
 * letters) <br>
 * This class replaces ligatures by the (multiple) characters they are composed
 * of. The list of ligatures supported is equivalent to the pattern created at
 * instanciation.
 * 
 * @author orianne.rollier@epfl.ch
 * @author renaud.richardet@epfl.ch
 * 
 */
public class LigaturesRemover {

    private final Map<Pattern, String> patterns;

    public LigaturesRemover() {
        patterns = new HashMap<Pattern, String>();
        // substitution for the ligatures
        patterns.put(Pattern.compile(String.valueOf((char) 0xFB04)), "ffl");
        patterns.put(Pattern.compile(String.valueOf((char) 0xFB03)), "ffi");
        patterns.put(Pattern.compile(String.valueOf((char) 0xFB01)), "fi");
        patterns.put(Pattern.compile(String.valueOf((char) 0xFB02)), "fl");
        patterns.put(Pattern.compile(String.valueOf((char) 0xFB05)), "ſt");
        patterns.put(Pattern.compile(String.valueOf((char) 0xFB00)), "ff");
        patterns.put(Pattern.compile(String.valueOf((char) 0xFB06)), "st");
        patterns.put(Pattern.compile(String.valueOf((char) 0x00E6)), "ae");
        patterns.put(Pattern.compile(String.valueOf((char) 0x0133)), "ij");
        patterns.put(Pattern.compile(String.valueOf((char) 0x0153)), "oe");
    }

    /**
     * Takes a string and returns it without ligatures in it
     * 
     * @param txt
     *            line that is processed
     * @return cleaned line
     */
    public String remove(String txt) {
        for (Pattern p : patterns.keySet()) {
            System.out.println("p: "+p);
            System.out.println("b: "+txt);
            txt = p.matcher(txt).replaceAll(patterns.get(p));
            System.out.println("a: "+txt);
        }
        return txt;
    }
}
