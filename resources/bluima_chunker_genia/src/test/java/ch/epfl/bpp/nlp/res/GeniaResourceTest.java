package ch.epfl.bpp.nlp.res;

import org.junit.Assert;
import org.junit.Test;

import ch.epfl.bpp.nlp.res.chunk.GeniaResource;
import ch.epfl.bbp.nlp.ModelProxy;
import ch.epfl.bbp.nlp.ModelProxyException;
import ch.epfl.bbp.nlp.ModelStream;

public class GeniaResourceTest {

    @Test
    public final void testGetModelFromName() throws ModelProxyException {
        ModelStream stream = ModelProxy
                .getStream("ch.epfl.bpp.nlp.res.chunk.GeniaResource");
        Assert.assertNotNull("Resource is not null", stream);
    }

    @Test
    public final void testGetModelFromInstance() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(new GeniaResource());
        Assert.assertNotNull("Resource is not null", stream);
    }
}

