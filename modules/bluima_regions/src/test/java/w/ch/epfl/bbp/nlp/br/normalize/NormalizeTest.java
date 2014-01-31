package w.ch.epfl.bbp.nlp.br.normalize;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NormalizeTest {

    @Test
    // see table 1: Modifier Descriptions and Examples
    public void testTable1() throws Exception {

        Normalize n = new Normalize();

        assertTrue(n.processMentionString(
                "Dorsal and posterior hypothalamic areas").contains(
                "dorsal hypothalamic areas"));

        assertTrue(n.processMentionString("Contralateral inferior olivary")
                .contains("inferior olivary"));

        assertTrue(n.processMentionString(
                "Secondary somatosensory (sii) cortex").contains(
                "secondary somatosensory cortex"));

        assertTrue(n.processMentionString(//
                "N. ambiguus").contains("nucleus ambiguus"));

        assertTrue(n.processMentionString(//
                "Medial portion of the entorhinal cortex").contains(
                "medial entorhinal cortex"));

        assertTrue(n.processMentionString(//
                "posterior cingulate region").contains("posterior cingulate"));

        assertTrue(n.processMentionString(//
                "Parvocellular red nucleus").contains("red nucleus"));

        assertTrue(n.processMentionString(//
                "Caudal cuneate nucleus").contains("cuneate nucleus"));

        assertTrue(n.processMentionString(//
                "Nucleus of the pontobulbar body").contains("pontobulbar body"));
    }
}
