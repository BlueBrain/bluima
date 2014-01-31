package ch.epfl.bbp.nlp.tables;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.epfl.bbp.io.SVReader.CSVReader;
import ch.epfl.bbp.uima.types.DocumentLine;

import com.snowtide.pdf.PDFTextStream;
import com.snowtide.pdf.layout.BaseLineImpl;

/**
 * DTO to hold table annotations, mapping fields from {@link PDFTextStream}'s
 * coordinates
 * 
 * @author renaud.richardet@epfl.ch
 */
public class GoldAnnotation {

    private int pageId;
    private float x, y, width, height;

    public int getPageId() {
        return pageId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    /** @return true if (pageId, x, y, width, height) match */
    public boolean equals(BaseLineImpl l, int pageId) {
        return (this.pageId == pageId && //
                l.xpos() == x && l.ypos() == y && //
                l.height() == height && l.width() == width);
    }

    /** @return true if (pageId, x, y, width, height) match */
    public boolean equals(DocumentLine l) {
        return (this.pageId == l.getPageId() && //
                l.getX() == x && l.getY() == y);// && //
        // TODO somehow height does not work ?!?
         //l.getHeight() == height && l.getWidth() == width);
    }

    @Override
    public String toString() {
        return "x:" + x + " y:" + y + " w:" + width + " h:" + height + " p:"
                + pageId;
    }

    /**
     * @param annotationFile
     *            format:
     * 
     *            <pre>
//TODO     * </pre>
     * @return a list of {@link GoldAnnotation}s, or null if the file is empty
     */
    public static List<GoldAnnotation> parse(File annotationFile)
            throws IOException {
        if (!annotationFile.exists())
            return null;

        List<GoldAnnotation> golds = newArrayList();
        for (List<String> line : new CSVReader(annotationFile, true)) {
            GoldAnnotation gold = new GoldAnnotation();
            gold.pageId = parseInt(line.get(0));
            gold.x = parseFloat(line.get(1));
            gold.y = parseFloat(line.get(2));
            gold.width = parseFloat(line.get(3));
            gold.height = parseFloat(line.get(4));
            golds.add(gold);
        }
        return golds;
    }
}
