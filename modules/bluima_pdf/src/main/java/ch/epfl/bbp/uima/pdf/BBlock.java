package ch.epfl.bbp.uima.pdf;

import static ch.epfl.bbp.StringUtils.snippetize;

import java.awt.geom.Rectangle2D;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * DTO for block in PDF document.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BBlock {

    int id;
    int pageId;
    Rectangle2D.Float region;
    String text = "";
    boolean hasBold;
    boolean hasManyFontsizes;
    double medianFontsize;
    List<BLine> lines = Lists.newArrayList();

    public int getId() {
        return id;
    }

    public BBlock setId(int id) {
        this.id = id;
        return this;
    }

    public int getPageId() {
        return pageId;
    }

    public BBlock setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    public Rectangle2D.Float getRegion() {
        return region;
    }

    public BBlock setRegion(Rectangle2D.Float region) {
        this.region = region;
        return this;
    }

    public String getText() {
        return text;
    }

    public BBlock setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isHasBold() {
        return hasBold;
    }

    public BBlock setHasBold(boolean hasBold) {
        this.hasBold = hasBold;
        return this;
    }

    public boolean isHasManyFontsizes() {
        return hasManyFontsizes;
    }

    public BBlock setHasManyFontsizes(boolean hasManyFontsizes) {
        this.hasManyFontsizes = hasManyFontsizes;
        return this;
    }

    public double getMedianFontsize() {
        return medianFontsize;
    }

    public BBlock setMedianFontsize(double medianFontsize) {
        this.medianFontsize = medianFontsize;
        return this;
    }

    public BBlock setLines(List<BLine> lines) {
        this.lines = lines;
        return this;
    }

    public BBlock addLine(BLine l) {
        lines.add(l);
        return this;
    }

    public List<BLine> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return "id" + id + " p" + pageId + " txt:" + snippetize(getText(), 70);
    }
}