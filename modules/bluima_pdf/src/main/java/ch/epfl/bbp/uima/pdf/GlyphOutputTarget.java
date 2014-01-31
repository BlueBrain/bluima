package ch.epfl.bbp.uima.pdf;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xmlcml.pdf2svg.GlyphCorrector;

import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.layout.TextUnit;

/**
 * @author orianne.rollier@epfl.ch
 */
public class GlyphOutputTarget extends OutputTarget {

    // to find mismapped glyphs
    private static final boolean DEBUG = false;

    private GlyphCorrector corrector;
    // removes the subset of the font
    private final Pattern p = Pattern.compile("([^+]+\\+)*(.*)");

    public GlyphOutputTarget(Appendable apnd, GlyphCorrector corrector) {
        super(apnd);
        this.corrector = corrector;
    }

    @Override
    public void textUnit(TextUnit tu) {
        try {
            // retrives the font name
            String fontName = null;
            try {
                String tabFontName[] = tu.getFont().getFontName().split("/");
                fontName = tabFontName[1];
                Matcher m = p.matcher(fontName);
                if (m.find()) {
                    fontName = m.group(2);
                }
            } catch (Exception e) {// nope, use null as fontname
            }

            // does the conversion if needed
            if (tu.getCharacterSequence() == null) {
                int codepoint = tu.getCharCode();
                char c = (char) corrector.codePointConversion(fontName,
                        codepoint);
                if (DEBUG)
                    System.err.println(codepoint + "\t" + c + "\t" + fontName);
                write(c);
            } else {
                char[] characters = tu.getCharacterSequence();
                for (int i = 0; i < characters.length; i++) {
                    int codepoint = (int) characters[i];
                    char c = (char) corrector.codePointConversion(fontName,
                            codepoint);
                    if (DEBUG)
                        System.err.println(codepoint + "\t" + c + "\t"
                                + fontName);
                    write(c);
                }
            }
        } catch (IOException e) {// TODO
            e.printStackTrace();
        }
    }
}
