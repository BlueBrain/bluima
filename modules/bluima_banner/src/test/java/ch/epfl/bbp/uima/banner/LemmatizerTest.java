package ch.epfl.bbp.uima.banner;

import static ch.epfl.bbp.uima.ae.BannerHelper.BANNER_ROOT;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import dragon.nlp.tool.lemmatiser.EngLemmatiser;

public class LemmatizerTest {

    @Test
    public void test() throws Exception {

        File lemmatiserDataDirectory = new File(BANNER_ROOT
                + "src/main/resources/pear_resources/nlpdata/lemmatiser");
        assertTrue(lemmatiserDataDirectory.exists());

        EngLemmatiser lemmatiser = new EngLemmatiser(
                lemmatiserDataDirectory.getAbsolutePath(), false, true);

        String[] texts = { "gone", "went", "" };

        for (String text : texts) {

            String lemma = lemmatiser.lemmatize(text);
            System.out.println(text + "\t--> " + lemma);
        }
    }
}
