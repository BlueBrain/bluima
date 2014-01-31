package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.ae.GridSearchConfiguration.COMBINAISON_INDEX;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.util.List;

import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Test;

import ch.epfl.bbp.uima.utils.StaticOption;

public class GridSearchConfigurationTest {

    @Test
    public void test() throws Exception {
        get(0, "test1 integer 11 21");
        assertEquals(StaticOption.getInt("test1"), 11);
        assertEquals(StaticOption.getMaxCombinaisonIndex(), 2);
        get(1, "test1 integer 11 21");
        assertEquals(StaticOption.getInt("test1"), 21);

        get(6, "test1 integer 1 2", "test2 string a b", "test3 string x y");
        // choosen options: test1::2 test2::b test3::x
        assertEquals(StaticOption.getInt("test1"), 2);
        assertEquals(StaticOption.getString("test2"), "b");
        assertEquals(StaticOption.getString("test3"), "x");
        assertEquals(StaticOption.getMaxCombinaisonIndex(), 8);

        get(2, "test1 integer 1 2", "test2 string a b", "test3 string x y");
        // choosen options: test1::1 test2::b test3::x
        assertEquals(StaticOption.getInt("test1"), 1);
        assertEquals(StaticOption.getString("test2"), "b");
        assertEquals(StaticOption.getString("test3"), "x");

        get(0, "test1 integer 1 2", "test2 string a b", "test3 string x y");
        // choosen options: test1::1 test2::a test3::x
        assertEquals(StaticOption.getInt("test1"), 1);
        assertEquals(StaticOption.getString("test2"), "a");
        assertEquals(StaticOption.getString("test3"), "x");

        get(7, "test1 integer 1 2", "test2 string a b", "test3 string x y");
        // choosen options: test1::2 test2::b test3::y
        assertEquals(StaticOption.getInt("test1"), 2);
        assertEquals(StaticOption.getString("test2"), "b");
        assertEquals(StaticOption.getString("test3"), "y");

        get(4, "test1 integer 1 3", "test2 string a b c");
        // choosen options: test1::3 test2::b
        assertEquals(StaticOption.getInt("test1"), 3);
        assertEquals(StaticOption.getString("test2"), "b");
        assertEquals(StaticOption.getMaxCombinaisonIndex(), 6);

        get(4, "test1 dble 1.0 1.3", "test2 string a b c");
        // choosen options: test1::1.3 test2::b
        assertEquals(StaticOption.getDouble("test1"), 1.3d, 0.001d);
        assertEquals(StaticOption.getString("test2"), "b");
        assertEquals(StaticOption.getMaxCombinaisonIndex(), 6);
    }

    @Test(expected = ResourceInitializationException.class)
    public void testIndexTooLarge() throws Exception {
        get(6, "test1 integer 1 2", "test2 string a b");
    }

    @Test(expected = ResourceInitializationException.class)
    public void testWrongTypeName() throws Exception {
        get(6, "test1 afds 1 2");
    }

    @Test(expected = ResourceInitializationException.class)
    public void testWrongTypeOptions() throws Exception {
        get(6, "test1 integer 1 a 2");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void get(int index, String... options) throws Exception {
        StaticOption.reset();

        List aeParams = newArrayList(COMBINAISON_INDEX, index);
        int i = 1;
        for (String option : options) {
            aeParams.add("o" + i++);
            aeParams.add(option);
        }
        runPipeline(
                getTestCas("a"),
                createEngineDescription(GridSearchConfiguration.class,
                        aeParams.toArray()));
        StaticOption.print();
    }
}
