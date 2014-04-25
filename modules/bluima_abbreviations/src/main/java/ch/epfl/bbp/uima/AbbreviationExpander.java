package ch.epfl.bbp.uima;

import static ch.epfl.bbp.StringUtils.snippetize;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.ae.AbbreviationsAnnotator.ABREVIATIONS_HOME;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static java.util.regex.Pattern.quote;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.collect.Sets;
import com.wcohen.ss.abbvGapsHmm.Acronym;
import com.wcohen.ss.abbvGapsHmm.AlignmentPredictionModel;

public class AbbreviationExpander {
    protected static final Logger LOG = getLogger(AbbreviationExpander.class);

    private AbbreviationExpander() { // singleton
    }

    private static AlignmentPredictionModel model = null;

    static private AlignmentPredictionModel getModel() throws IOException {
        if (model == null) {
            model = new AlignmentPredictionModel();
            String trainedModel = ABREVIATIONS_HOME + RESOURCES_PATH
                    + "model_trained";
            checkFileExists(trainedModel);
            model.setModelParamsFile(trainedModel);
            model.trainIfNeeded();
        }
        return model;
    }

    /**
     * @param txt
     *            the input text
     * @return the input text where all identified abbreviation have been
     *         expanded to their long forms
     */
    static public String expand(String txt) {
        return expand(txt, getAbbrevs(txt));
    }

    // code below is used by PdfCollection reader to expand abbrevs in 2 phases:
    // 1) getAbbrevs() to identify abbrevs
    // 2) expand() is called on every individual line to replace abbrevs

    public static class Abbrev {

        final String p1, p2, p3;
        final String sf, lf;

        private Abbrev(String longForm, String shortForm) {
            this.lf = longForm;
            this.sf = shortForm;

            // patterns:
            // 1: '(lf) sf'
            // 2: 'sf (lf)'
            // 3: 'lf (sf)', oh yeah
            // 4: 'sf', or without spaces if at end/beg of txt

            this.p1 = "(?i)\\(" + quote(lf) + "\\) " + quote(sf);
            this.p2 = "(?i)" + quote(sf) + " \\(" + quote(lf) + "\\)";
            this.p3 = "(?i)" + quote(lf) + " \\(" + quote(sf) + "\\)";
            // (?i) to turn case insensitivity
        }

        public String replace(String txt) {

            txt = txt.replaceAll(p1, lf).replaceAll(p2, lf).replaceAll(p3, lf);

            // pattern 4: just the sf, case-sensitive
            // check sf has NON-letter on the right AND left
            int idx = 0;
            while (txt.indexOf(sf, idx) > -1) {
                int at = txt.indexOf(sf, idx);
                if (notLetter(txt, at - 1) && notLetter(txt, at + sf.length())) {
                    txt = txt.substring(0, at) + lf
                            + txt.substring(at + sf.length());
                }
                idx = at + sf.length();
            }

            return txt;
        }

        // we just want to make sure that it's NOT a letter, BUT ok if out of
        // string
        private static boolean notLetter(String txt, int index) {
            return index < 0 || index >= txt.length()
                    || !java.lang.Character.isLetter(txt.charAt(index));
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Abbrev) {
                Abbrev oa = (Abbrev) o;
                if (oa.lf.equals(lf) && oa.sf.equals(sf)) {
                    return true;
                }
                return false;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return (lf + sf).hashCode();
        }
    }

    /**
     * @param txt
     *            the input text
     * @return a map of abbrev:false <-- not matched yet
     */
    static public Set<Abbrev> getAbbrevs(String txt) {
        Set<Abbrev> ret = Sets.newHashSet();
        try {
            for (Acronym a : getModel().predict(txt)) {
                // filtering
                if (a._longForm.indexOf('(') == -1 // parenthesis
                        && a._longForm.indexOf(')') == -1 // parenthesis
                        && !a._longForm.matches("et\\.? al") // citations
                        && a._shortForm.length() > 1 // shortform too short
                        && a._longForm.length() > 2) // longform too short
                {
                    // System.out.println(a._shortForm + " || " + a._longForm);
                    ret.add(new Abbrev(a._longForm, a._shortForm));
                }
            }
        } catch (IOException e) {
            LOG.warn(
                    "could not expand abbreviations for text "
                            + snippetize(txt, 20), e);

        }
        return ret;
    }

    /**
     * @return the input text where all identified abbreviation have been
     *         expanded to their long forms
     */
    static public String expand(String txt, Set<Abbrev> abbrevs) {
        for (Abbrev abbrev : abbrevs) {
            txt = abbrev.replace(txt);
        }
        return txt;
    }
}
