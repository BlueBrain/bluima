package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.*;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.testutils.UimaTests;

/**
 * @author renaud.richardet@epfl.ch
 */
public class LanguageDetectionAnnotatorTest {

    @Test
    public void test() throws Exception {
        assertEquals("en", getLang(UimaTests.TEST_SENTENCE));
        assertEquals(
                "zh-tw",
                getLang("有一個很大的區別的頂部和底部的菜青蟲的藥用部位：根莖頂部的根有髓與結構的根莖;上三分之一擴大的根，根莖過渡區，還與髓;其他三分之二的擴大根的結構根無髓。藥用部分的菜青蟲adoxoides在不同發育階段的微觀結構提出了巨大的差距，結構定期增加植物的大小增加。這是第一次發現有結構的年輪在木質部的根及根莖的菜青蟲。"));
    }

    @Test
    public void testStandalone() throws Exception {

        assertEquals("en",
                LanguageDetectionAnnotator.detect(UimaTests.TEST_SENTENCE));
        assertEquals(
                "zh-tw",
                LanguageDetectionAnnotator
                        .detect("有一個很大的區別的頂部和底部的菜青蟲的藥用部位：根莖頂部的根有髓與結構的根莖;上三分之一擴大的根，根莖過渡區，還與髓;其他三分之二的擴大根的結構根無髓。藥用部分的菜青蟲adoxoides在不同發育階段的微觀結構提出了巨大的差距，結構定期增加植物的大小增加。這是第一次發現有結構的年輪在木質部的根及根莖的菜青蟲。"));
        assertEquals(
                "en",
                getLang("The CNS-depressant effect of ethanol was markedly reduced in mice by simultaneous intraperitoneal injection of taurine. Effect of taurine on ethanol-induced sleeping time in mice."));
    }

    @Test
    /** @see http://icwww.epfl.ch/~chappeli/tidt/TP/TP1.php?lang=en#langid */
    public void testColing() throws Exception {
        assertEquals("fr",
                getLang("Le bug dans le soft a engendré un crash du système"));
        assertNotSame("fr",
                getLang("Le xwt dans le xwt a xwtyxwy un xwt du wxt"));
        assertNotSame("fr", getLang("xwt xwt xwtyxwy xwt wxt"));
        // assertEquals("fr", getLang("bug soft engendré crash système"));
        assertEquals("en", getLang("computerish Rajmanism wisoriperaught "));
    }

    private Object getLang(String testSentence) throws UIMAException {
        JCas jCas = getTestCas(testSentence);
        AnalysisEngine ae = createEngine(LanguageDetectionAnnotator.class,
                LanguageDetectionAnnotator.MIN_TEXT_LENGTH, 1);
        ae.process(jCas);
        return jCas.getDocumentLanguage();
    }
}
