package ch.epfl.bbp.uima.filter;

import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.typesystem.ContentSection;

/**
 * Removes {@link Keep} annotations that are located into a
 * {@link DocumentBlock} whose {@link ContentSection} is non-content, e.g.
 * bibliography, acknowledgment, ...
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SectionFilterAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (DocumentBlock block : select(jCas, DocumentBlock.class)) {

            if (ContentSection.isNonContentSection(block.getLabel())) {

                FilterFrameworkUtils.removeKeepsFrom(jCas, block);
            }
        }
    }
}