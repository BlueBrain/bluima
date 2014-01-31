package ch.epfl.bbp.uima.pdf;

import static ch.lambdaj.Lambda.max;
import static ch.lambdaj.Lambda.on;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_VALUE;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static org.slf4j.LoggerFactory.getLogger;

import java.awt.geom.Rectangle2D.Float;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.xmlcml.pdf2svg.GlyphCorrector;

import com.google.common.collect.Maps;

/**
 * DTO for PDF document.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BDocument {
    private static final Logger LOG = getLogger(GlyphCorrector.class);

    List<BBlock> blocks = new ArrayList<BBlock>();

    public int getNumPages() {
        return max(blocks, on(BBlock.class).getPageId()); // nice!
    }

    public List<BBlock> getBlocks() {
        return blocks;
    }

    public void addBlock(BBlock block) {
        this.blocks.add(block);
    }

    // private static final String TERMINAISON = "___...____";
    // private static final String PAGE_NRS = "page_nrs" + TERMINAISON;
    // private static final String TEXT_W_NRS = "text_n_nrs" + TERMINAISON;

    // void processDuplicates_old() {// package visibility
    //
    // Multimap<String, BBlock> blocksMap = HashMultimap.create();
    // for (BBlock l : blocks) {
    //
    // String text = l.getText().trim();
    //
    // // handle single numbers, that could be page nrs
    // Integer tryInteger = Ints.tryParse(text);
    // if (tryInteger != null) {
    // blocksMap.put(l.getRegion().x + "-" + l.getRegion().y + " "
    // + PAGE_NRS, l);
    //
    // } else if (text.matches("^.{3,}\\d$")) {// ends with nr,
    // // with at least 3 chars before --> remove the number
    // // 1st grp is reluctant!
    // String replacedText = text.replaceAll("^(.*?)(?:\\d+)$", "$1 "
    // + TEXT_W_NRS);
    // blocksMap.put(l.getRegion().x + "-" + l.getRegion().y + " "
    // + replacedText, l);
    //
    // } else if (text.matches("^\\d+....*$")) {// STARTS w. at least a nr
    // // --> remove the number
    // String replacedText = text.replaceFirst("^(\\d{1,})(.*)", "$2 "
    // + TEXT_W_NRS);
    // blocksMap.put(l.getRegion().x + "-" + l.getRegion().y + " "
    // + replacedText, l);
    //
    // } else {
    // blocksMap.put(
    // l.getRegion().x + "-" + l.getRegion().y + " "
    // + l.getText(), l);
    // }
    // }
    //
    // for (String mapKeys : blocksMap.keySet()) {
    // Collection<BBlock> maybeDupes = blocksMap.get(mapKeys);
    //
    // int minOccurences = Math.max(2, getNumPages() / 2);
    // if (maybeDupes.size() >= minOccurences) {
    //
    // // remove dupes
    // List<BBlock> areDupes = new ArrayList<BBlock>();
    // for (BBlock dupe : maybeDupes) {
    // LOG.debug("dupes {}\t\t{}", dupe.getRegion(),
    // dupe.getText());
    // areDupes.add(dupe);
    // }
    // for (BBlock areDupe : areDupes) {
    // blocks.remove(areDupe);
    // }
    // }
    // }
    // }

    void processDuplicates(File file) {
        try {
            // sort by page
            // pageId, List<blocks>, treemap so it's ordered by page id
            Map<Integer, List<BBlock>> pages = Maps.newTreeMap();
            int maxPages = -1;
            // max and min Y to find page boundaries
            double maxY = -1, minY = Integer.MAX_VALUE;
            for (BBlock l : blocks) {
                int pageId = l.getPageId();
                maxPages = max(maxPages, pageId);
                maxY = max(maxY, l.getRegion().getMaxY());
                minY = Math.min(minY, l.getRegion().getMinY());
                List<BBlock> regions = pages.get(pageId);
                if (regions == null)
                    regions = newArrayList();
                regions.add(l);
                pages.put(pageId, regions);
            }

            // iterate all pages, and all blocks
            List<Float> regionsForDuplicates = newArrayList();
            for (int i = 0; i < maxPages; i++) {
                if (pages.get(i) != null) {
                    Iterator<BBlock> blockIt = pages.get(i).iterator();
                    while (blockIt.hasNext()) {
                        BBlock bBlock = blockIt.next();

                        boolean isSimilarWithAnotherRegion = false;

                        // compare each block with the blocks in subsequent
                        // pages
                        for (int j = i + 1; j < maxPages; j++) {
                            if (pages.get(j) != null) {
                                Iterator<BBlock> subsequentIt = pages.get(j)
                                        .iterator();
                                while (subsequentIt.hasNext()) {
                                    BBlock subsequentBlock = subsequentIt
                                            .next();

                                    // System.out.println("\ncompare: " + bBlock
                                    // +
                                    // "\nAND\n"
                                    // + subsequentBlock);
                                    if (isSimilar(bBlock, subsequentBlock)) {
                                        // System.err
                                        // .println("---> SIMILAR *************************");
                                        // System.out.println("SIMILAR:\n" +
                                        // bBlock
                                        // + "\nAND\n" + subsequentBlock +
                                        // "\n");
                                        // LOG.error("similar: {}\nAND\n{}",
                                        // bBlock.getText(),
                                        // subsequentBlock.getText());
                                        isSimilarWithAnotherRegion = true;
                                        subsequentIt.remove();
                                        regionsForDuplicates
                                                .add(subsequentBlock
                                                        .getRegion());
                                        remove(subsequentBlock, blocks);
                                    }
                                }
                            }
                        }
                        if (isSimilarWithAnotherRegion) {
                            blockIt.remove();
                            remove(bBlock, blocks);
                            regionsForDuplicates.add(bBlock.getRegion());
                        }
                    }
                }
            }

            // segregate the Rectangle of removed region into header/footer
            // using these as examples, define 2 limits for header/footer
            // then remove all remaining regions within these limits
            final double range = maxY - minY;
            // cutoff for segregation, +5% from header top or footer bottom
            final double headerMax = minY + 0.05 * range;
            final double footerMin = maxY - 0.05 * range;

            // System.out.println("FFF " + minY + "---" + maxY);
            List<Float> footerRegions = newArrayList(), headerRegions = newArrayList();
            for (Float f : regionsForDuplicates) {

                if (f.getMaxY() < headerMax)
                    headerRegions.add(f);
                else if (f.getMinY() > footerMin)
                    footerRegions.add(f);

                // System.out.println("regions " + f.getMaxY() + "---" +
                // f.getMinY());
            }

            // the 2 limits
            double minThreshold = MIN_VALUE, maxThreshold = MAX_VALUE;
            for (Float h : headerRegions) {
                minThreshold = Math.max(minThreshold, h.getMaxY());
            }
            for (Float f : footerRegions) {
                maxThreshold = Math.min(maxThreshold, f.getMinY());
            }
            // System.out.println("Limits " + minThreshold + "---" +
            // maxThreshold);

            Iterator<BBlock> bIt = blocks.iterator();
            while (bIt.hasNext()) {
                BBlock b = bIt.next();

                if (b.getRegion().getMaxY() < minThreshold + range * 0.01) {
                    // System.out.println("Remove footer: " + b);
                    bIt.remove();
                } else if (b.getRegion().getMinY() > maxThreshold - range
                        * 0.01) {
                    // System.out.println("Remove header: " + b);
                    bIt.remove();
                }
            }
        } catch (Throwable t) {
            LOG.error("could not process duplicates for " + file, t);
        }

    }

    private void remove(BBlock block, List<BBlock> blockList) {
        Iterator<BBlock> it = blockList.iterator();
        while (it.hasNext()) {
            BBlock bBlock = it.next();
            if (bBlock.equals(block))
                it.remove();
        }
    }

    private static final int TEXT_LENGTH_THRESHOLD = 10;
    private static final double POSITION_THRESHOLD = 5d;
    private static final int EDIT_DISTANCE_THRESHOLD = 5;

    private boolean isSimilar(BBlock b1, BBlock b2) {

        // rough text lengh comparison
        String txt1 = b1.getText(), txt2 = b2.getText();
        if (abs(txt1.length() - txt2.length()) > TEXT_LENGTH_THRESHOLD) {
            // System.out.println("TEXT_LENGTH_THRESHOLD");
            return false;
        }

        // position comparison
        java.awt.geom.Rectangle2D.Float r1 = b1.getRegion(), r2 = b2
                .getRegion();
        double diff = abs(r1.getX() - r2.getX()) + //
                abs(r1.getY() - r2.getY()) + //
                abs(r1.getWidth() - r2.getWidth()) + //
                abs(r1.getHeight() - r2.getHeight());
        if (diff > POSITION_THRESHOLD) {
            // System.out.println("POSITION_THRESHOLD");
            return false;
        }

        // text comparison (Levenshtein)
        if (getLevenshteinDistance(txt1, txt2, EDIT_DISTANCE_THRESHOLD) == -1) {
            // System.out.println("EDIT_DISTANCE_THRESHOLD");
            return false;
        }

        return true;
    }

    // current Lang 2.4 does not have threshold, so pasting 3.1 below
    /**
     * <p>
     * Find the Levenshtein distance between two Strings if it's less than or
     * equal to a given threshold.
     * </p>
     * 
     * <p>
     * This is the number of changes needed to change one String into another,
     * where each change is a single character modification (deletion, insertion
     * or substitution).
     * </p>
     * 
     * <p>
     * This implementation follows from Algorithms on Strings, Trees and
     * Sequences by Dan Gusfield and Chas Emerick's implementation of the
     * Levenshtein distance algorithm from <a
     * href="http://www.merriampark.com/ld.htm"
     * >http://www.merriampark.com/ld.htm</a>
     * </p>
     * 
     * <pre>
     * StringUtils.getLevenshteinDistance(null, *, *)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance(*, null, *)             = IllegalArgumentException
     * StringUtils.getLevenshteinDistance(*, *, -1)               = IllegalArgumentException
     * StringUtils.getLevenshteinDistance("","", 0)               = 0
     * StringUtils.getLevenshteinDistance("aaapppp", "", 8)       = 7
     * StringUtils.getLevenshteinDistance("aaapppp", "", 7)       = 7
     * StringUtils.getLevenshteinDistance("aaapppp", "", 6))      = -1
     * StringUtils.getLevenshteinDistance("elephant", "hippo", 7) = 7
     * StringUtils.getLevenshteinDistance("elephant", "hippo", 6) = -1
     * StringUtils.getLevenshteinDistance("hippo", "elephant", 7) = 7
     * StringUtils.getLevenshteinDistance("hippo", "elephant", 6) = -1
     * </pre>
     * 
     * @param s
     *            the first String, must not be null
     * @param t
     *            the second String, must not be null
     * @param threshold
     *            the target threshold, must not be negative
     * @return result distance, or {@code -1} if the distance would be greater
     *         than the threshold
     * @throws IllegalArgumentException
     *             if either String input {@code null} or negative threshold
     */
    public static int getLevenshteinDistance(CharSequence s, CharSequence t,
            int threshold) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }
        if (threshold < 0) {
            throw new IllegalArgumentException("Threshold must not be negative");
        }

        /*
         * This implementation only computes the distance if it's less than or
         * equal to the threshold value, returning -1 if it's greater. The
         * advantage is performance: unbounded distance is O(nm), but a bound of
         * k allows us to reduce it to O(km) time by only computing a diagonal
         * stripe of width 2k + 1 of the cost table. It is also possible to use
         * this to compute the unbounded Levenshtein distance by starting the
         * threshold at 1 and doubling each time until the distance is found;
         * this is O(dm), where d is the distance.
         * 
         * One subtlety comes from needing to ignore entries on the border of
         * our stripe eg. p[] = |#|#|#|* d[] = *|#|#|#| We must ignore the entry
         * to the left of the leftmost member We must ignore the entry above the
         * rightmost member
         * 
         * Another subtlety comes from our stripe running off the matrix if the
         * strings aren't of the same size. Since string s is always swapped to
         * be the shorter of the two, the stripe will always run off to the
         * upper right instead of the lower left of the matrix.
         * 
         * As a concrete example, suppose s is of length 5, t is of length 7,
         * and our threshold is 1. In this case we're going to walk a stripe of
         * length 3. The matrix would look like so:
         * 
         * 1 2 3 4 5 1 |#|#| | | | 2 |#|#|#| | | 3 | |#|#|#| | 4 | | |#|#|#| 5 |
         * | | |#|#| 6 | | | | |#| 7 | | | | | |
         * 
         * Note how the stripe leads off the table as there is no possible way
         * to turn a string of length 5 into one of length 7 in edit distance of
         * 1.
         * 
         * Additionally, this implementation decreases memory usage by using two
         * single-dimensional arrays and swapping them back and forth instead of
         * allocating an entire n by m matrix. This requires a few minor
         * changes, such as immediately returning when it's detected that the
         * stripe has run off the matrix and initially filling the arrays with
         * large values so that entries we don't compute are ignored.
         * 
         * See Algorithms on Strings, Trees and Sequences by Dan Gusfield for
         * some discussion.
         */

        int n = s.length(); // length of s
        int m = t.length(); // length of t

        // if one string is empty, the edit distance is necessarily the length
        // of the other
        if (n == 0) {
            return m <= threshold ? m : -1;
        } else if (m == 0) {
            return n <= threshold ? n : -1;
        }

        if (n > m) {
            // swap the two strings to consume less memory
            CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }

        int p[] = new int[n + 1]; // 'previous' cost array, horizontally
        int d[] = new int[n + 1]; // cost array, horizontally
        int _d[]; // placeholder to assist in swapping p and d

        // fill in starting table values
        int boundary = Math.min(n, threshold) + 1;
        for (int i = 0; i < boundary; i++) {
            p[i] = i;
        }
        // these fills ensure that the value above the rightmost entry of our
        // stripe will be ignored in following loop iterations
        Arrays.fill(p, boundary, p.length, Integer.MAX_VALUE);
        Arrays.fill(d, Integer.MAX_VALUE);

        // iterates through t
        for (int j = 1; j <= m; j++) {
            char t_j = t.charAt(j - 1); // jth character of t
            d[0] = j;

            // compute stripe indices, constrain to array size
            int min = Math.max(1, j - threshold);
            int max = Math.min(n, j + threshold);

            // the stripe may lead off of the table if s and t are of different
            // sizes
            if (min > max) {
                return -1;
            }

            // ignore entry left of leftmost
            if (min > 1) {
                d[min - 1] = Integer.MAX_VALUE;
            }

            // iterates through [min, max] in s
            for (int i = min; i <= max; i++) {
                if (s.charAt(i - 1) == t_j) {
                    // diagonally left and up
                    d[i] = p[i - 1];
                } else {
                    // 1 + minimum of cell to the left, to the top, diagonally
                    // left and up
                    d[i] = 1 + Math.min(Math.min(d[i - 1], p[i]), p[i - 1]);
                }
            }

            // copy current distance counts to 'previous row' distance counts
            _d = p;
            p = d;
            d = _d;
        }

        // if p[n] is greater than the threshold, there's no guarantee on it
        // being the correct
        // distance
        if (p[n] <= threshold) {
            return p[n];
        } else {
            return -1;
        }
    }

}
