package ch.epfl.bbp.uima.pdf;

import static com.google.common.collect.Lists.newArrayList;

import java.awt.geom.Rectangle2D.Float;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.xmlcml.pdf2svg.GlyphCorrector;

import com.snowtide.pdf.OutputHandler;
import com.snowtide.pdf.OutputTarget;
import com.snowtide.pdf.Page;
import com.snowtide.pdf.layout.Block;
import com.snowtide.pdf.layout.Line;
import com.snowtide.pdf.layout.TextUnit;
import com.snowtide.pdf.layout.TextUnitImpl;

/**
 * Handles low-level PDF text extraction
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BlockHandler extends OutputHandler {

    /** corrects glyph extraction, esp. important for greek letters */
    private final GlyphCorrector glyphCorrector = new GlyphCorrector();

    private BDocument doc = new BDocument();

    int currBlockId = 0;
    int currPageId = -1;

    // compute block regions (somehow did not work otherwise)
    private double currBlockMaxX = -1f,
            currBlockMinX = java.lang.Float.MAX_VALUE, currBlockMaxY = -1f,
            currBlockMinY = java.lang.Float.MAX_VALUE;
    private List<BLine> currLines = newArrayList();
    private int currLineId = 0;

    @Override
    public void startBlock(Block b) {
        super.startBlock(b);
        // reinint
        currBlockMaxX = -1f;
        currBlockMinX = java.lang.Float.MAX_VALUE;
        currBlockMaxY = -1f;
        currBlockMinY = java.lang.Float.MAX_VALUE;
        currBlockHasBold = false;
        currBlockFontSizes = new DescriptiveStatistics();
    }

    @Override
    public void endBlock(Block b) {
        super.endBlock(b);

        float width = (float) (currBlockMaxX - currBlockMinX);
        float height = (float) (currBlockMaxY - currBlockMinY);
        boolean hasManyFontsizes = currBlockFontSizes.getMax() != currBlockFontSizes
                .getMin();
        double medianFontsize = currBlockFontSizes.getPercentile(50);

        BBlock block = new BBlock()
                .setRegion(
                        new Float((float) currBlockMinX, (float) currBlockMinY,
                                width, height)).setId(currBlockId)
                .setText(pipeText(b)).setHasBold(currBlockHasBold)
                .setHasManyFontsizes(hasManyFontsizes)
                .setMedianFontsize(medianFontsize).setPageId(currPageId)
                .setLines(currLines);
        currLines = newArrayList();// reset
        currLineId = 0;
        doc.addBlock(block);
        ++currBlockId;
    }

    @Override
    public void startPage(Page arg0) {
        super.startPage(arg0);
        currPageId++;
    }

    @Override
    public void startLine(Line l) {
        super.startLine(l);
        Float region = new Float();
        region.height = l.height();
        region.width = l.width();
        region.x = l.xpos();
        region.y = l.ypos();

        // Used for pdf table processing
        StringBuffer sb = new StringBuffer();
        SpacingOutputTarget oh = new SpacingOutputTarget(sb, glyphCorrector);
        try {
            l.pipe(oh);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        String lineText = sb.toString();
        List<java.lang.Float> beginnings = oh.getBeginnings();
        List<java.lang.Float> endings = oh.getEndings();

        currLines.add(new BLine().setRegion(region).setPageId(currPageId)
                .setBlockId(currBlockId).setLineId(currLineId++)
                .setText(lineText).setBeginnings(beginnings)
                .setEndings(endings));

        currBlockMaxX = Math.max(currBlockMaxX, l.endxpos());
        currBlockMinX = Math.min(currBlockMinX, l.xpos());
        currBlockMaxY = Math.max(currBlockMaxY, l.ypos());
        currBlockMinY = Math.min(currBlockMinY, l.ypos() - l.height());
    }

    public String pipeText(Block b) {
        StringBuffer sb = new StringBuffer();
        OutputTarget oh = new GlyphOutputTarget(sb, glyphCorrector);
        try {
            b.pipe(oh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private boolean currBlockHasBold;
    private DescriptiveStatistics currBlockFontSizes = new DescriptiveStatistics();

    public void textUnit(TextUnit tu) {
        TextUnitImpl t = (TextUnitImpl) tu;
        // LATER
        // float lineHeight = t.lineHeight();
        // boolean italic = t.getFont().isItalic();
        // String fontName = tu.getFont().getFontName();
        // float fontSize = t.getFontSize(); does not work, always 1
        boolean bold = t.getFont().isBold();

        currBlockFontSizes.addValue(t.lineHeight());
        if (bold)
            currBlockHasBold = true;
    }

    @Override
    public void endPDF(String arg0, File file) {
        super.endPDF(arg0, file);
        doc.processDuplicates(file);
    }

    public BDocument getDoc() {
        return doc;
    }
}
