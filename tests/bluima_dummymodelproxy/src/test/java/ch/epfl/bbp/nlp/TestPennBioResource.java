package ch.epfl.bbp.nlp;

import org.junit.Assert;
import org.junit.Test;

public class TestPennBioResource {
    @Test
    public final void testGetModelFromName() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(new PennBioResource());
        Assert.assertNotNull("Resource is not null", stream);
    }
}
