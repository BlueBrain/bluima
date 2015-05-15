package ch.epfl.bbp;

import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;

public class TestWithBluimaResource {

    protected static final String BLUIMA_RESOURCE_DIR = System
            .getProperty("BLUIMA_RESOURCE_DIR");

    @BeforeClass
    public static void setup() {
        assertNotNull("BLUIMA_RESOURCE_DIR system property is not set",
                BLUIMA_RESOURCE_DIR);
    }

}

