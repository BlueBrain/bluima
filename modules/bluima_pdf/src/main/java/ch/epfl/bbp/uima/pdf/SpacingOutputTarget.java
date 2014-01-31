package ch.epfl.bbp.uima.pdf;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.xmlcml.pdf2svg.GlyphCorrector;

import com.snowtide.pdf.layout.TextUnit;

/**
 * 2-state statemachine to record position of transitions in beginning/ending of
 * words. Used to track pdf tables.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SpacingOutputTarget extends GlyphOutputTarget {

    private List<Float> beg = newArrayList();
    private List<Float> end = newArrayList();

    public SpacingOutputTarget(Appendable apnd, GlyphCorrector corrector) {
        super(apnd, corrector);
    }

    // state values
    private boolean isInWord = false;
    private float lastEndX = 0;

    @Override
    public void spaces(int arg0) {
        super.spaces(arg0);
        end();
    }

    @Override
    public void linebreaks(int arg0) {
        super.linebreaks(arg0);
        end();
    }

    private void end() {
        if (isInWord == true) {
            // starting a new word
            end.add(lastEndX);
        }
        isInWord = false;
    }

    @Override
    public void textUnit(TextUnit tu) {
        super.textUnit(tu);

        if (isInWord == false) {
            // starting a new word
            beg.add(tu.xpos());
            isInWord = true;
        }
        lastEndX = tu.endxpos();
    }

    public List<Float> getBeginnings() {
        return beg;
    }

    public List<Float> getEndings() {
        return end;
    }
}
