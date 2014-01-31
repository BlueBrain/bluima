package ch.epfl.bbp.uima.pdf;

import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * DTO for lines in PDF document.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BLine implements Comparable<BLine> {

    public enum Signal {
        FOOTER_HEADER, //
        WIDE_LINE, //
        FIGURE,
    };

    private Rectangle2D.Float region;
    private String text;
    private boolean bold;
    private float height;
    private float width;
    private Set<Signal> signals = new HashSet<Signal>();
    private int lineId;
    private int blockId;
    private int pageId;
    private List<Float> beginnings ;
    private List<Float> endings ;

    public Rectangle2D.Float getRegion() {
        return region;
    }

    public BLine setRegion(Rectangle2D.Float region) {
        this.region = region;
        return this;
    }

    public String getText() {
        return text;
    }

    public BLine setText(String text) {
        this.text = text;
        return this;
    }

    public boolean hasSignal(Signal s) {
        return signals.contains(s);
    }

    public Set<Signal> getSignals() {
        return signals;
    }

    public BLine addSignal(Signal s) {
        signals.add(s);
        return this;
    }

    public BLine setBold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public boolean isBold() {
        return bold;
    }

    public float getHeight() {
        return height;
    }

    public BLine setHeight(float height) {
        this.height = height;
        return this;
    }

    public float getWidth() {
        return width;
    }

    public BLine setWidth(float width) {
        this.width = width;
        return this;
    }

    public boolean hasSignals() {
        return signals != null && !signals.isEmpty();
    }

    public BLine setBlockId(int blockId) {
        this.blockId = blockId;
        return this;
    }

    public int getBlockId() {
        return blockId;
    }

    public BLine setPageId(int pageId) {
        this.pageId = pageId;
        return this;
    }

    public int getPageId() {
        return pageId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BLine) {
            BLine other = (BLine) obj;
            return other.region.equals(this.region);
        }
        return false;
    }

    /** compare by page, x,y */
    @Override
    public int compareTo(BLine other) {

        int pageCompare = new Integer(pageId).compareTo(other.pageId);
        if (pageCompare != 0) {
            return pageCompare;
        }

        int xCompare = new Float(region.x).compareTo(other.region.x);
        if (xCompare != 0) {
            return xCompare;
        }
        int yCompare = new Float(region.y).compareTo(other.region.y);
        return yCompare;
    }

    public BLine setLineId(int currLineId) {
        this.lineId = currLineId;
        return this;
    }

    public int getLineId() {
        return lineId;
    }

    public List<Float> getBeginnings() {
        return beginnings;
    }

    public BLine setBeginnings(List<Float> beginnings) {
        this.beginnings = beginnings;
        return this;
    }

    public List<Float> getEndings() {
        return endings;
    }

    public BLine setEndings(List<Float> endings) {
        this.endings = endings;
        return this;
    }
}
