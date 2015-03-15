package ch.epfl.bbp.nlp.res;

import org.junit.Assert;
import org.junit.Test;

import ch.epfl.bbp.nlp.res.tag.GeniaResource;
import ch.epfl.bbp.nlp.ModelProxy;
import ch.epfl.bbp.nlp.ModelProxyException;
import ch.epfl.bbp.nlp.ModelStream;

public class GeniaResourceTest {

    @Test
    public final void testGetModelFromName() throws ModelProxyException {
        ModelStream stream = ModelProxy
                .getStream("ch.epfl.bbp.nlp.res.tag.GeniaResource");
        Assert.assertNotNull("Resource is not null", stream);
    }

    @Test
    public final void testGetModelFromInstance() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(new GeniaResource());
        Assert.assertNotNull("Resource is not null", stream);
    }
}

