package ubic.whitetext;

import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getOpenNlpTokenizedTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import cc.mallet.pipe.SerialPipes;
import cc.mallet.types.FeatureVector;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.FeatureVectorSequence.Iterator;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

/**
 * To test pipes.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BrainRegionPipesTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {

        // Tests my pipes
        pipe("blablabla (n 2) bliblibli",
                new ArrayList<String>(),
                newArrayList("parenthesis_n_space_digit_parenthesis"),
                newArrayList("n_space_digit",
                        "parenthesis_n_space_digit_parenthesis"));

        pipe("blablabla 30 mM K SO , 5 mM MgCl 6H O",
                new ArrayList<String>(),
                newArrayList("digit_then_other_then_digit_then_other_then_digit"));

        pipe("2.4", newArrayList("DotDecimal"));
        pipe("2 and 4", newArrayList("digit_then_other_then_digit"));

        pipe("blablabla n 2 bliblibli", new ArrayList<String>(),
                newArrayList("n_space_digit"));

        pipe("blablabla 24 24 bliblibli", new ArrayList<String>(),
                newArrayList("digit_then_other_then_digit"));

        // Tests NEPipes
        pipe("aaa 12% bbb", new ArrayList<String>(), newArrayList("Percent"));
        pipe("aaa 12/13 bbb", new ArrayList<String>(), newArrayList("Fraction"));
        pipe("aaa 'afd' bbb", new ArrayList<String>(), newArrayList("Quoted"));
        pipe("aaa A. bbb", new ArrayList<String>(), newArrayList("Initial"));
        pipe("aaa", newArrayList("AllLower"));

        // Test French pipes
        pipe("aaa mlfp bbb", new ArrayList<String>(),
                newArrayList("NNHumanAbbrev"));
        pipe("aaa T1 bbb", new ArrayList<String>(), newArrayList("SpinalParts"));
        pipe("area 12 blabla", newArrayList("Brodmann"));
        pipe("aaaa piriform-amygdalar area blabla", new ArrayList<String>(),
                newArrayList("Allen"), newArrayList("Allen"));
    }

    @SuppressWarnings("unchecked")
    @Test(expected = AssertionError.class)
    public void testSpaced() throws Exception {

        // only the first 2 tokens should get Brodmann's feature, not the 3rd
        pipe("area 12 blabla", newArrayList("Brodmann"),
                new ArrayList<String>(), newArrayList("Brodmann"));

    }

    @SuppressWarnings("unchecked")
    @Test(expected = AssertionError.class)
    public void testTest() throws Exception {
        // the testing framework should (work and) complain
        pipe("blabla", newArrayList("nope_i_wont_find_this_one"));
    }

    private void pipe(String txt, List<String>... features) throws Exception {
        // it might not have all the aes, though...
        JCas jCas = getOpenNlpTokenizedTestCas(txt);

        InstanceList il = new InstanceList(//
                new SerialPipes(BrainRegionPipes.getPipes()));

        Instance instance = new Instance(jCas, null, 1, jCas);
        il.addThruPipe(instance);

        Instance pipedInstance = il.iterator().next();
        FeatureVectorSequence data = (FeatureVectorSequence) pipedInstance
                .getData();

        java.util.Iterator<List<String>> featuresIt = asList(features)
                .iterator();
        Iterator it = data.iterator();
        while (it.hasNext()) {
            FeatureVector featureVector = it.next();

            if (featuresIt.hasNext()) {
                for (String expectedFeature : featuresIt.next()) {
                    assertTrue("could not find expected feature '"
                            + expectedFeature + "', FeatureVector = \n"
                            + featureVector,
                            featureVector.contains(expectedFeature));

                }
            }
        }
    }
}
