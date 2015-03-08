package ch.epfl.bbp.nlp;

import org.junit.Assert;
import org.junit.Test;

import ch.epfl.bbp.nlp.res.sentence.PennBioResource;

public class TestPennBioResource {

    @Test
    public final void testGetModelFromName() throws ModelProxyException {
        ModelStream stream = ModelProxy
                .getStream("ch.epfl.bbp.nlp.res.sentence.PennBioResource");
        Assert.assertNotNull("Resource is not null", stream);
    }

    @Test
    public final void testGetModelFromInstance() throws ModelProxyException {
        ModelStream stream = ModelProxy.getStream(new PennBioResource());
        Assert.assertNotNull("Resource is not null", stream);
    }

}
