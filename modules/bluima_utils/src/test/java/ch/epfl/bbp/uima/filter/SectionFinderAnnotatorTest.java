package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_ABSTRACT;
import static ch.epfl.bbp.uima.typesystem.ContentSection.SECTION_EMAIL;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

public class SectionFinderAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jCas = getTestCas();
        PipelineBuilder builder = new JcasPipelineBuilder(jCas);

        // a content-block
        createAnnot(jCas, DocumentBlock.class, 0, 10)
                .setLabel(SECTION_ABSTRACT);
        // with a Keep in it
        createAnnot(jCas, Keep.class, 1, 9);

        // a NON-content-block
        createAnnot(jCas, DocumentBlock.class, 11, 20)//
                .setLabel(SECTION_EMAIL);
        // with a Keep in it
        createAnnot(jCas, Keep.class, 12, 19);

        // process with Section modules
        builder.add(SectionRegexAnnotator.class);
        builder.add(SectionFilterAnnotator.class);
        builder.process();

        Collection<DocumentBlock> blocks = select(jCas, DocumentBlock.class);
        assertEquals("still 2 blocks", 2, blocks.size());
        Collection<Keep> keeps = select(jCas, Keep.class);
        assertEquals("only 1 keep remaining", 1, keeps.size());
        assertEquals("remaining keep is the one from the abstract section", 1,
                keeps.iterator().next().getBegin());
    }
}
