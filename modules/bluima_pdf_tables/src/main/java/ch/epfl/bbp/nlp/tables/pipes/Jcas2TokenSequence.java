package ch.epfl.bbp.nlp.tables.pipes;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FloatArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;
import ch.epfl.bbp.nlp.tables.GoldAnnotation;
import ch.epfl.bbp.uima.types.DocumentLine;

import com.google.common.collect.Sets;
import com.snowtide.pdf.Page;
import com.snowtide.pdf.layout.Block;
import com.snowtide.pdf.layout.Line;
import com.snowtide.pdf.layout.TextUnit;

/**
 * Creates a {@link cc.mallet.types.TokenSequence} from a {@link PdfDocument},
 * reusing the {@link Block}s, {@link Line}s and {@link TextUnit}s. Sets the
 * (true) label based on manually annotated lines.
 * 
 * @author samuel.kimoto@epfl.ch
 * @author renaud.richardet@epfl.ch
 */
public class Jcas2TokenSequence extends Pipe {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory
            .getLogger(Jcas2TokenSequence.class);

    public static final String TARGET_I = "I";// TODO
    public static final String TARGET_O = "O";

    /**
     * IN: one {@link JCas} <br/>
     * data : 1 {@link JCas}<br>
     * targ : 1 {@link List} of {@link GoldAnnotation}s, each corresponds to a
     * line representing a part of a table<br>
     * name : the pmId<br>
     * srce : the pdf {@link File} (not used)<br>
     * <br>
     * OUT: {@link Instance}s, each corresponding to a {@link Page}<br/>
     * data : a {@link TokenSequence}, one {@link cc.mallet.types.Token} per
     * PDFTextStream {@link Line}<br>
     * targ : labels for training (if any)<br>
     * name : the pdf name + page nr<br>
     * srce : a {@link List} of {@link Line}s, corresponding to the tokens<br>
     * <br>
     * Note: this {@link Pipe} creates more {@link Instance} than it gets (one
     * output {@link Instance} per input {@link Page}).
     */
    @Override
    public Iterator<Instance> newIteratorFrom(Iterator<Instance> source) {
        List<Instance> output = new LinkedList<Instance>();

        while (source.hasNext()) {
            Instance carrier = (Instance) source.next();
            JCas jCas = (JCas) carrier.getData();
            int pmId = (Integer) carrier.getName();

            @SuppressWarnings("unchecked")
            // null means that there are no annotations, so we are in infer mode
            List<GoldAnnotation> goldAnnotations = (List<GoldAnnotation>) carrier
                    .getTarget();
            int notMatched = goldAnnotations.size();

            // we create a new Mallet Instance for each page
            List<cc.mallet.types.Token> data = newArrayList();
            TokenSequence target = new TokenSequence();
            List<Line> instanceSource = newArrayList();
            int currPageId = 0;
            DocumentLine previousLine = null;
            for (DocumentLine line : select(jCas, DocumentLine.class)) {

                Token token = new Token(line.getLineText());// TODO a workaround

                // set label on lines with gold annots
                if (isGold(line, goldAnnotations)) {
                    target.add(TARGET_I);
                    line.setLabel(TARGET_I);// FIXME should I?
                    notMatched--;
                    // /System.out.print("^I^");
                } else {
                    target.add(TARGET_O);
                    line.setLabel(TARGET_O);// FIXME should I?
                    // /System.out.print("^O^");
                }

                // ADD FEATURES
                addAlignmentFeatures(line, previousLine, token);

                data.add(token);

                // new page -> new Mallet Instance
                if (line.getPageId() > currPageId) {
                    // add instance
                    output.add(new Instance(new TokenSequence(data), target,
                            pmId + "_" + currPageId, instanceSource));
                    // for next instance
                    currPageId = line.getPageId();
                    data = newArrayList();
                    target = new TokenSequence();
                    instanceSource = newArrayList();
                    previousLine = null;
                } else {
                    previousLine = line;
                }
            }
            // add last instance
            output.add(new Instance(new TokenSequence(data), target, pmId + "_"
                    + currPageId, instanceSource));
            if (notMatched > 0) {
                LOG.warn(
                        "pmId {}did not manage to match all training annotations, {} left",
                        pmId, notMatched);
            }
        }
        return output.iterator();
    }

    private static boolean isGold(DocumentLine line,
            List<GoldAnnotation> goldAnnotations) {
        if (goldAnnotations == null)
            return false;
        boolean found = false;

        // System.out.println(" p:" + line.getPageId() + " x:" + line.getX()
        // + " y:" + line.getY() + " w:" + line.getWidth() + " h:"
        // + line.getHeight());

        for (GoldAnnotation goldAnnotation : goldAnnotations) {
            if (goldAnnotation.equals(line)) {
                found = true;
                break;
            }
        }
        return found;
    }

    // //////////////////////////////////////
    // FEATURES code
    //

    static final String ALIGNMENT = "alignmnt_";

    private static void addAlignmentFeatures(DocumentLine line,
            DocumentLine previousLine, Token token) {

        if (previousLine == null)
            return;

        FloatArray prevBegins = previousLine.getBeginnings();
        FloatArray prevEnds = previousLine.getEndings();
        FloatArray currBegins = line.getBeginnings();
        FloatArray currEnds = line.getEndings();

        double begIntersect = intersectCnt(prevBegins, currBegins);
        double endIntersect = intersectCnt(prevEnds, currEnds);

        double b = begIntersect / (currBegins.size() + 0d);
        double e = endIntersect / (currEnds.size() + 0d);

        boolean b2 = currBegins.size() > 3 && b > 0.4;
        boolean e2 = currEnds.size() > 3 && e > 0.4;

        boolean b3 = currBegins.size() > 2 && b > 0.6;
        boolean e3 = currEnds.size() > 2 && e > 0.6;

        if (b2)
            token.setFeatureValue(ALIGNMENT + "beg_2", 1);
        if (e2)
            token.setFeatureValue(ALIGNMENT + "end_2", 1);
        if (b3)
            token.setFeatureValue(ALIGNMENT + "beg_3", 1);
        if (e3)
            token.setFeatureValue(ALIGNMENT + "end_3", 1);

        // String r = String.format("\t%.2f/%d\t%.2f/%d", begIntersect,
        // currBegins.size(), endIntersect, currEnds.size());
        // System.out.println("\t" + b2 + "\t" + e2 + "\t@3\t" + b3 + "\t" + e3
        // + r + "\t" + line.getLineText()//
        // + "\t" + Arrays.toString(currBegins.toArray())//
        // + "\t" + Arrays.toString(currEnds.toArray()));
        
    }

    private static int intersectCnt(FloatArray a, FloatArray b) {

        int intersect = 0;
        HashSet<Double> a_s = Sets.newHashSet();
        for (int i = 0; i < a.size(); i++) {
            a_s.add(Math.round(a.get(i) * 100.0) / 100.0);
        }

        for (int i = 0; i < b.size(); i++) {
            if (a_s.contains(Math.round(b.get(i) * 100.0) / 100.0))
                intersect++;
        }

        return intersect;
    }

}
