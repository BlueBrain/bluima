package ch.epfl.bbp.nlp;

import org.junit.Assert;
import org.junit.Test;

public class TestGeniaResource {
    @Test
    public final void testGetModelFromName() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(new GeniaResource());
        Assert.assertNotNull("Resource is not null", stream);
    }
}
