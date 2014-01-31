package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.isEmptyText;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_ABSTRACT;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_ACKNOWLEDGEMENTS;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_CITATION;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_DOWNLOADED;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_DOWNLOADED_ON;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_EMAIL;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_FIGURE;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_MAT_MET;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_RECEIVED;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_REFERENCES;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_SUMMARY;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_TABLE;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_URL_ON_A_LINE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.DOCUMENT_BLOCK;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.typesystem.ContentSection;

/**
 * Annotates {@link DocumentBlock}s with {@link ContentSection} like
 * Acknowlegments, Correspondance, etc. Uses regexes.<br/>
 * Patterns developped by analyzing a large document sample, with focus on
 * high-precision (at the cost of recall).
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { DOCUMENT_BLOCK }, outputs = { DOCUMENT_BLOCK })
public class SectionRegexAnnotator extends JCasAnnotator_ImplBase {
    @SuppressWarnings("unused")
    private static Logger LOG = LoggerFactory
            .getLogger(SectionRegexAnnotator.class);

    /** Order is important! */
    private final LinkedHashMap<String, Pattern> patterns = newLinkedHashMap();

    public void initialize(org.apache.uima.UimaContext context)
            throws ResourceInitializationException {

        // ORDER IS IMPORTANT!, as the content-annotations overwrite
        // the others non-content ones

        patterns.put(SECTION_CITATION, compile("^Citation(:|s) "));

        patterns.put(SECTION_DOWNLOADED, compile("^Downloaded from$"));

        patterns.put(
                SECTION_DOWNLOADED_ON,
                compile("on (April|August|"
                        + "December|February|January|July|June|March|May|November|October|September)"));

        patterns.put(
                SECTION_URL_ON_A_LINE,
                compile("^(http://)?(\\w{1,20}\\.){1,3}(com|net|edu|org|ch|de).{0,50}$"));

        /** Text after the pattern should be < 1200 long */
        patterns.put(
                SECTION_RECEIVED,
                compile("^.{0,20}[Rr]eceived.{0,20}(19[56789]|20[01])\\d.{0,10}[Aa]ccepted.{5,1200}"));

        patterns.put(SECTION_ACKNOWLEDGEMENTS, Pattern
                .compile("(^A(CKNOWLEDGMENTS|cknowledgments))|" + //
                        "(This (work|study) (is|was) supported by)|" + //
                        "([Ss]upported.{1,15} [gG]rant)|" + //
                        "We (would like to)? thank"));

        patterns.put(SECTION_EMAIL, //
                compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b"));

        patterns.put(
                SECTION_REFERENCES, //
                compile("^(B(ibliography|IBLIOGRAPHY)|LITERATURE CITED|References|REFERENCES)"));

        patterns.put(SECTION_MAT_MET, compile(//
                "^(Materials? and )?Methods", CASE_INSENSITIVE));

        patterns.put(SECTION_SUMMARY, compile("^S(ummary|UMMARY)"));

        patterns.put(SECTION_FIGURE, compile("^F(IG|ig)(URE|ure)?.{0,4}\\d"));

        patterns.put(SECTION_TABLE,
                compile("^table.{0,5}(\\d|I+)", CASE_INSENSITIVE));

        /** REM: Only ABSTRACT (uppercase) is allowed to float in text */
        patterns.put(SECTION_ABSTRACT, compile("(^Abstract|ABSTRACT)"));
    };

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        if (!isEmptyText(jCas)) {

            for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
                String txt = block.getCoveredText().replaceAll("\n", "");

                for (Entry<String, Pattern> element : patterns.entrySet()) {

                    if (element.getValue().matcher(txt).find()) {
                        block.setLabel(element.getKey());
                    }
                }
            }
        }
    }
}
