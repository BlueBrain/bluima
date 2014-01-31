package ch.epfl.bbp.uima.pdf.cleanup;

import static org.junit.Assert.*;

import java.text.Normalizer;
import java.text.Normalizer.Form;

import org.junit.Test;

public class LigaturesRemoverTest {

    @Test
    public void test() {
        assertEquals("fi", Normalizer.normalize("ﬁ", Form.NFKC));
        assertEquals("fi fl ff ffi ffl", Normalizer.normalize("ﬁ ﬂ ﬀ ﬃ ﬄ",Form.NFKC));
    }
}
