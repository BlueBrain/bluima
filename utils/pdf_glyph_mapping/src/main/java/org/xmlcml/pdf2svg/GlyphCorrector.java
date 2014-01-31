package org.xmlcml.pdf2svg;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.xmlcml.font.*;

/**
 * Maps wrong characters to the correct ones
 * 
 * @author orianne.rollier@epfl.ch
 * @author renaud.richardet@epfl.ch *
 */
public class GlyphCorrector {

    /** table for conversion from {@link CodePoint} to name "adobeGlyphList" */
    private Map<Integer, String> charNameByCodePoint;

    private final FontFamilySet standardUnicodeFontFamilySet;
    private final FontFamilySet standardNonUnicodeFontFamilySet;
    private final FontFamilySet nonStandardFontFamilySet;

    public GlyphCorrector() {

        createNameToCodePointMap();

        standardUnicodeFontFamilySet = FontFamilySet
                .readFontFamilySet(FontFamilySet.STANDARD_UNICODE_FONT_FAMILY_SET_XML);
        standardNonUnicodeFontFamilySet = FontFamilySet
                .readFontFamilySet(FontFamilySet.STANDARD_NON_UNICODE_FONT_FAMILY_SET_XML);
        nonStandardFontFamilySet = FontFamilySet
                .readFontFamilySet(FontFamilySet.NON_STANDARD_FONT_FAMILY_SET_XML);
    }

   // public AMIFontFamily get(String fontFamily) {
        public NonStandardFontFamily get(String fontFamily) {

         NonStandardFontFamily f = standardUnicodeFontFamilySet
                .getFontFamilyByName(fontFamily);
        if (f != null)
            return f;

        f = standardNonUnicodeFontFamilySet.getFontFamilyByName(fontFamily);
        if (f != null)
            return f;

        f = nonStandardFontFamilySet.getFontFamilyByName(fontFamily);
        if (f != null)
            return f;

        return null;
    }

    /**
     * Takes in argument a codepoint. If for the given police the codepoint
     * doesn't correspond to what the font actually displays, the conversion is
     * made. Otherwise, the old codepoint is return.
     * 
     * @param fontName
     *            name of the font of the character in the pdf
     * @param oldCodePoint
     *            unicode point in the original pdf
     * @return
     */
    public int codePointConversion(String fontName, int codePoint) {

        NonStandardFontFamily nonStandardFontFamily = get(fontName);
        if (nonStandardFontFamily == null)
            return codePoint;

        CodePointSet codePointSet = nonStandardFontFamily.getCodePointSet();
        if (codePointSet == null)
            return codePoint;

        CodePoint decimalCodePoint = codePointSet.getByDecimal(codePoint);
        if (decimalCodePoint != null)
            // Fetches the correct unicode point
            return decimalCodePoint.getUnicodeDecimal();

        CodePoint nameCodePoint = codePointSet.getByName(charNameByCodePoint
                .get(codePoint));
        if (nameCodePoint != null)
            return nameCodePoint.getUnicodeDecimal();

        return codePoint;
    }

    /**
     * Create the map that takes the wrong unicode point and returns the name of
     * the wrong character associated. This has to be done because in some XML
     * files the wrong characters are only described using this name. The names
     * are given in "codepoint/defacto/adobeGraphlist.txt" and
     * "codepoint/defacto/additional_glyphlist.txt".
     * 
     * @param codepointsFolder
     * @throws FileNotFoundException
     */
    private void createNameToCodePointMap() {
        charNameByCodePoint = new HashMap<Integer, String>();

        for (String glyphListFile : new String[] { "adobeGlyphlist.txt",
                "additional_glyphlist.txt" }) {

            Scanner scanner = new Scanner(this.getClass().getResourceAsStream(
                    glyphListFile));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.charAt(0) != '#') {
                    String split[] = line.split(";");
                    // keeps only Unicode with single point, cf.
                    // corresponding XML file
                    if (split[1].split(" ").length == 1) {
                        charNameByCodePoint.put(
                                Integer.decode("0x" + split[1]), split[0]);
                    }
                }
            }
            scanner.close();
        }
    }
}