package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.isEmptyText;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.BlueUima;
import de.julielab.jules.types.Section;

/**
 * Creates (at most) one {@link Section} annotation if a "References"
 * section-title is found. Uses a simple regex.
 * 
 * @author renaud.richardet@epfl.ch
 */
// TODO:
// LITERATURE CITED
// LITERATURE CITED
// relat literatur
// bibliographi
// This work is supported by Pu
// This study was supported by the Paulo Foundatio
// Supported in part by Grant FISS-89/0058
// This work was supported by Wake Fo
// Acknowledgements-Suported by a grant
//
@Deprecated
public class ReferencesFinderAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(ReferencesFinderAnnotator.class);

    // ^(bibliographi|LITERATURE CITED|references)$

    private static final Pattern REFS = Pattern.compile(
            "\\n(bibliography|LITERATURE CITED|references)\\n",
            CASE_INSENSITIVE);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        if (!isEmptyText(jCas)) {
            String text = jCas.getDocumentText();
            int pmid = getHeaderIntDocId(jCas);

            List<Section> sections = newArrayList();

            Matcher m = REFS.matcher(text);
            while (m.find()) {
                int end = jCas.getDocumentText().length();// m.end()
                // LATER section's end is set to document's end. This should be
                // improved, e.g. in the case of additional material located
                // after the reference section
                Section section = new Section(jCas, m.start(), end);
                section.setSectionType(BlueUima.SECTION_TYPE_REFERENCES);
                sections.add(section);
            }

            if (!sections.isEmpty()) {
                // add last occurrence
                sections.get(sections.size() - 1).addToIndexes();
                LOG.trace(pmid + "\t{}REFERENCES\t", sections.size());

            } else
                LOG.trace(pmid + "\tNO REFERENCES");
        }
    }
}
