package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueUimaHelper.SERVICE_ROOT;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.laucher.AnnotatorService;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.Measure;

public class AnnotatorServiceTest {

    @Test
    public void testFast() throws Exception {
        AnnotatorService aService = new AnnotatorService(new File(SERVICE_ROOT
                + "specific/20130723_extract_brainregions_bams.pipeline"), null);
        System.out.println("loaded");
        aService.start();
        System.out.println("started");

        for (int i = 0; i < 10; i++) {
            System.out.println("annotating");
            JCas jCas = aService.annotate("asfdnowein brain asdfnoiwe");
            Collection<BrainRegionDictTerm> brs = select(jCas,
                    BrainRegionDictTerm.class);
            assertEquals(1, brs.size());
        }
    }

    @Test
    @Ignore
    public void test() throws Exception {
        AnnotatorService aService = new AnnotatorService(new File(
                BlueUimaHelper.SERVICE_ROOT
                        + "full/complete_with_includes.pipeline"), null);

        JCas jCas = aService.annotate("asfdnowein midbrain asdfnoiwe");
        Collection<BrainRegionDictTerm> brs = select(jCas,
                BrainRegionDictTerm.class);
        assertEquals(1, brs.size());

        jCas = aService.annotate("asfdnowein 121 mV asdfnoiwe");
        Collection<Measure> m = select(jCas, Measure.class);
        assertEquals(1, m.size());

        // TODO jCas =
        // aService.annotate("The lateral hypothalamus (LH) is a brain region. LH plays an important role in memory.");
        // Collection<Abbreviation> a= select(jCas, Abbreviation.class);
        // assertEquals(1, a.size());
    }
}
