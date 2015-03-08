package ch.epfl.bbp.nlp;

import java.io.InputStream;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

public class ModelProxyTest {

    @Test
    public final void testGetModelFromName() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(DummyModelResource.KLASS);
        assertContentMatch(stream);
    }

    @Test
    public final void testGetModelFromInstance() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(new DummyModelResource());
        assertContentMatch(stream);
    }

    @Test
    public final void testGetModelFromClassAndPath() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(DummyModelResource.class,
                DummyModelResource.PATH);
        assertContentMatch(stream);
    }

    private final void assertContentMatch(ModelStream stream) {
        Assert.assertNotNull("No model found", stream);
        String content = readContent(stream);
        Assert.assertEquals("Model mismatch", DummyModelResource.CONTENT,
                content);
    }

    private final String readContent(InputStream input) {
        try (Scanner s = new Scanner(input)) {
            // Read until beginning (never reached -> read whole input)
            s.useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }

}
