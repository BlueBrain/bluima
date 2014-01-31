package ch.epfl.bbp.uima.references;

import static ch.epfl.bbp.io.TextFileWriter.write;
import static ch.epfl.bbp.uima.BlueUima.SECTION_TYPE_REFERENCES;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectFollowing;
import static org.apache.uima.fit.util.JCasUtil.selectPreceding;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;

import ch.epfl.bbp.uima.types.DocumentBlock;
import de.julielab.jules.types.Section;

/**
 * Prints the first section AFTER the reference. Used to create a corpus for
 * References.
 * 
 * @author richarde
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class ReferencesStatsAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(ReferencesStatsAnnotator.class);

    final String base = "target/";
    final String inside = base + "I/", outside = base + "O/";

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        new File(inside).mkdir();
        new File(outside).mkdir();
    }

    int i = 0;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        try {
            final Collection<Section> sections = select(jCas, Section.class);
            if (sections != null && !sections.isEmpty()) {
                for (Section s : sections) {
                    if (s.getSectionType().equals(SECTION_TYPE_REFERENCES)) {

                        // OK, we have a ref title; get the next block of refs
                        List<DocumentBlock> refs = selectFollowing(jCas,
                                DocumentBlock.class, s, 1);
                        if (refs != null && !refs.isEmpty()) {

                            // write ref text
                            String refTxt = refs.get(0).getCoveredText();
                            write(new File(inside + i + ".txt"), refTxt);

                            // write another random block before this one
                            List<DocumentBlock> beforeRef = selectPreceding(
                                    jCas, DocumentBlock.class, s, 1000);
                            Collections.shuffle(beforeRef);
                            if (beforeRef != null && !beforeRef.isEmpty()) {
                                int j = 0;
                                // for (int j = 0; j < min(3, beforeRef.size());
                                // j++) {
                                write(new File(outside + i + "_" + j + ".txt"),
                                        beforeRef.get(j).getCoveredText());

                                // }
                            }
                            i++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOG.warn("bummer: ", e);
        }
    }
}
