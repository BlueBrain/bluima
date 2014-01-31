package ch.epfl.bbp.nlp.ie.proteinconc;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.typesystem.ContentSection;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import java.util.ArrayList;
import java.util.List;

import static org.apache.uima.fit.util.JCasUtil.select;

/**
 * Sets the confidence of the co-occurrences according to the
 * following rules:
 * <ul>
 * <li>a co-occurrence appearing in the result section
 * of a paper has a confidence of 1</li>
 * <li>a co-occurrence appearing in the section describing
 * the materials and method has a confidence of -1</li>
 * </ul>
 *
 * @author Philémon Favrod (philemon.favrod@epfl.ch)
 */
public class SectionBasedCoocConfidenceAnnotator extends JCasAnnotator_ImplBase {

    private static final String MATERIALS_AND_METHOD_LABEL = ContentSection.SECTION_MAT_MET;
    private static final String RESULTS_LABEL = ContentSection.SECTION_RESULTS;


    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        //first, beginning of the needed sections are detected
        int materialsAndMethodsBegin = -1;
        List<Integer> resultsBegins = new ArrayList<Integer>();

        for (DocumentBlock section : select(jCas, DocumentBlock.class)) {
            String labelForSection = section.getLabel();

            //System.out.println("TTT - " +section.getBegin()+ " - " + ((labelForSection == null) ? "no":labelForSection));
            if (MATERIALS_AND_METHOD_LABEL.equals(labelForSection)) {
                materialsAndMethodsBegin = section.getBegin();
            } else if (RESULTS_LABEL.equals(labelForSection)) {
                resultsBegins.add(section.getBegin());
            }
        }

        //we compute the position of the section following materials and method and results
        int sectionAfterMAMBegin = -1;
        List<Integer> sectionsAfterRes = new ArrayList<Integer>();
        for (int resultsBegin : resultsBegins) {
            int sectionAfterRes = -1;
            for (DocumentBlock section : select(jCas, DocumentBlock.class)) {
                int sectionBegin = section.getBegin();
                String label = section.getLabel();

                if (isLabelSignificant(label)) {
                    if (sectionBegin > materialsAndMethodsBegin) {
                        if (sectionAfterMAMBegin == -1 ||
                                (sectionBegin < sectionAfterMAMBegin)) {
                            sectionAfterMAMBegin = sectionBegin;
                        }
                    }

                    if (sectionBegin > resultsBegin) {
                        if (sectionAfterRes == -1 ||
                                (sectionBegin < sectionAfterRes)) {
                            sectionAfterRes = sectionBegin;
                        }
                    }
                }
            }
            sectionsAfterRes.add(sectionAfterRes);
        }

        //printForDebug(jCas, materialsAndMethodsBegin, resultsBegins, sectionAfterMAMBegin, sectionsAfterRes);


        //then, we set the confidence according to the different cases
        for (Cooccurrence coOccurrence : select(jCas, Cooccurrence.class)) {
            setConfidenceIfInResultSection(resultsBegins, sectionsAfterRes, coOccurrence);
            setConfidenceIfInMaterialsAndMethods(materialsAndMethodsBegin, sectionAfterMAMBegin, coOccurrence);
        }
    }


    private void setConfidenceIfInMaterialsAndMethods(int materialsAndMethodsBegin, int sectionAfterMAMBegin, Cooccurrence coOccurrence) {
        int coOccurrenceBegin = coOccurrence.getBegin();

        if (coOccurrenceBegin > materialsAndMethodsBegin &&
                coOccurrenceBegin < sectionAfterMAMBegin) {
            coOccurrence.setConfidence(-1);
        }
    }

    private void setConfidenceIfInResultSection(List<Integer> resultsBegins, List<Integer> sectionsAfterRes, Cooccurrence coOccurrence) {
        int coOccurrenceBegin = coOccurrence.getBegin();

        for (int i = 0; i < resultsBegins.size(); ++i) {
            int resultsBegin = resultsBegins.get(i);
            int sectionAfterRes = sectionsAfterRes.get(i);

            if (coOccurrenceBegin > resultsBegin && coOccurrenceBegin < sectionAfterRes) {
                coOccurrence.setConfidence(1);
            }
        }
    }



    private boolean isLabelSignificant(String label) {
        if (label == null || label.isEmpty()) {
            return false;
        }

        if (ContentSection.SECTION_REFERENCES.equals(label) ||
                ContentSection.SECTION_REFERENCES.equals(label)) {
            return true;
        }

        if (ContentSection.isNonContentSection(label)) {
            return false;
        }

        if (ContentSection.SECTION_TABLE.equals(label) ||
                ContentSection.SECTION_FIGURE.equals(label)) {
            return false;
        }

        return true;
    }


    // METHODS FOR DEBUG

    private void printSectionStart(String desc, JCas cas, int begin) {
        String str = cas.getDocumentText();
        if (begin < 0) {
            System.out.println("AAAA - " + desc + " - -1 - " + BlueCasUtil.getHeaderDocId(cas));
        } else if (begin < str.length()) {
            int end = begin + 50;
            if (end > str.length()) {
                end = str.length() - 1;
            }

            System.out.println("AAAA - "+desc+" ("+ BlueCasUtil.getHeaderDocId(cas)+":"+begin+"): "+str.substring(begin, end));
        }
    }

	@SuppressWarnings("unused")
	private void printForDebug(JCas jCas, int materialsAndMethodsBegin,
			List<Integer> resultsBegins, int sectionAfterMAMBegin,
			List<Integer> sectionsAfterRes) {
        for (int resultsBegin : resultsBegins)
            printSectionStart("RES SECTION", jCas, resultsBegin);
        for (int a : sectionsAfterRes)
            printSectionStart("SEC AFTER RES", jCas, a);
        printSectionStart("MaM", jCas, materialsAndMethodsBegin);
        printSectionStart("SEC AFTER MaM", jCas, sectionAfterMAMBegin);
    }
}
