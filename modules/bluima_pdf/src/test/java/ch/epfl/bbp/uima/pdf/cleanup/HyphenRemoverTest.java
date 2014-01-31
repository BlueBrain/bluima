package ch.epfl.bbp.uima.pdf.cleanup;

import static ch.epfl.bbp.uima.pdf.cleanup.HyphenRemover.dehyphenate;
import static org.junit.Assert.*;

import org.junit.Test;

public class HyphenRemoverTest {

    @Test
    public void testDehyphenate() {
        assertEquals("Not yet implemented\n",
                dehyphenate("Not yet implem-\nented", "a"));

        assertEquals("Not yet implemented\n",
                dehyphenate("Not yet implem-\nented", "a"));

        assertEquals("Not implemented\n\nfor now\n",
                dehyphenate("Not implemented\n\nfor now", "a"));

        assertEquals("Not implemented\n--\nfor now\n",
                dehyphenate("Not implemented\n--\nfor now", "a"));

        assertEquals("Not implemented\nAA\nfor now\n",
                dehyphenate("Not implemented\nAA\nfor now", "a"));
    }

    @Test
    public void testDehyphenate2() {
        assertEquals("The basal nucleus of the hypocampus\n",
                dehyphenate("The basal nu-\ncleus of the hypocampus", "a"));
    }
    
    @Test
    public  void testMu(){
        // http://blogs.msdn.com/b/michkap/archive/2012/04/25/10297456.aspx
        assertNotEquals("µ","μ");
        assertEquals("µ".replaceAll("\u00b5", "\u03bc"),"μ");
    }
}
