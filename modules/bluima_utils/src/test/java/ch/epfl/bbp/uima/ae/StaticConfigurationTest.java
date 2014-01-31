package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.util.List;

import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;

import ch.epfl.bbp.uima.utils.StaticOption;

public class StaticConfigurationTest {

    @Test
    public void test() throws Exception {
        set("test1 integer 11");
        assertEquals(StaticOption.getInt("test1"), 11);
    }

    @Test
    public void test2() throws Exception {
        set("test2 dble 1.1");
        assertEquals(StaticOption.getDouble("test2"), 1.1, 0.0001d);
    }

    @Test
    public void test3() throws Exception {
        set("test1 integer 2", "test2 string bbb", "test3 string xxx");
        assertEquals(StaticOption.getInt("test1"), 2);
        assertEquals(StaticOption.getString("test2"), "bbb");
        assertEquals(StaticOption.getString("test3"), "xxx");
    }

    @Test(expected = ResourceInitializationException.class)
    public void testIndexTooLarge() throws Exception {
        set("test1 integer 1 2");
    }

    @Test(expected = ResourceInitializationException.class)
    public void testWrongTypeName() throws Exception {
        set("test1 afds 1");
    }

    @Test(expected = ResourceInitializationException.class)
    public void testWrongTypeOptions() throws Exception {
        set("test1 integer a");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void set(String... options) throws Exception {
        StaticOption.reset();

        List aeParams = newArrayList();
        int i = 1;
        for (String option : options) {
            aeParams.add("o" + i++);
            aeParams.add(option);
        }
        runPipeline(
                getTestCas("a"),
                createEngineDescription(StaticConfiguration.class,
                        aeParams.toArray()));
        StaticOption.print();
    }
}
