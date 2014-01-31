package ch.epfl.bbp.uima.integration_tests;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ch.epfl.bbp.uima.pear.ComponentManager;

/**
 * @author renaud.richardet@epfl.ch
 */
public class PearRepositoryTest {

    @Test
    public void test() throws Exception {
	assertThat("we can find some PEARs", ComponentManager.getComponents()
		.size(), greaterThan(0));
    }
}
