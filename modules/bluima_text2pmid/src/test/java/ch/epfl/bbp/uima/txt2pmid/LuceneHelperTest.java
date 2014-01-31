package ch.epfl.bbp.uima.txt2pmid;

import static ch.epfl.bbp.uima.txt2pmid.LuceneHelper.subsumes;
import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class LuceneHelperTest {
    private static final Logger LOG = getLogger(LuceneHelperTest.class);

    @Test
    public void test() {

        String haystack = "This is a remarkably good book on graphical models. Filled with lots of examples and really takes its time trying to build intuition. Seriously, consider this book if the idea of going through Koller’s 1400 page tome feels intimidating. The book is more gentle than the others listed here but still manages a remarkable amount. Note it says Bayesian in the title so non-Bayesian algorithms and models are mostly ignored. You will need to supplement with a book like EoSL or Murphy’s book. There is a free copy on his website so be sure to preview and see if its at the right level for you.";
        float s = LuceneHelper.subsumes(haystack, //
                "This is a remarkably good book on graphical models");
        LOG.debug("score {}", s);
        assertTrue(s > 0.1);

        s = subsumes(haystack, "This is  remarkably good book on  models");
        LOG.debug("score {}", s);
        assertTrue(s > 0.1);

        s = subsumes(haystack, "what a mistake to go fishing last saturday");
        assertTrue(s == 0.0);

        s = subsumes("", "what a mistake to go fishing last saturday");
        assertTrue(s == 0.0);
    }

    @Test
    public void test733() {

        String haystack = "Neurotransmitters and Temperature Regulation \nPETER LOMAX AND MARTIN DAVID GREEN\n\nDepartment of Pharmacology, School of Medicine and the Brain Research Institute,  University of\n                                                  California, Los Angeles, Calif. 90024 ( U . S . A . ) \nThe demonstration of relatively high concentrations of amines in the hypothalamus,\ncoupled with the elucidation of the role of this region of the brain in regulating body\ntemperature have suggested that these amines might play an important role as neuro-\ntransmitters in thermoregulation. Over the past decade or so there has been a con-\nsiderable body of research aimed at resolving these questions and several recent\nsymposia have adequately reviewed this work (Bligh and Moore, 1972; Schonbaum\nandLomax,1973;Lomaxetal.,1975).Itisnotpossible,atthepresenttime,tocon-\nstruct any unified view or general theory of the function of neurotransmitters in\nthermoregulatory mechanisms; although integrative models have frequently been\nproposed, such as those of Bligh (1972) or Myers (1975), these more often represent\nspecial cases arising from the author's own research rather than a synthesis of all\navailable data.\n    Many attempts have been made to describe the thermorzgulatory system in terms\nof cybernetic models of varying complexity (Hardy, 1972). The next step in the analysis\nof the system has been to propose neuronal networks to replace the black boxes,\nbased on experimental evidences (Hensel, 1973). One of the major controversies\nconcerns the nature of the reference point of the controller - the presence or absence\nof a temperature independent reference signal. Two major models can be derived:\none in which the controlled temperature is compared to a reference value (the “set-\npoint”) and appropriate responses are activated to correct any deviation of the\ncontrolled temperature from the reference; alternatively, two types of sensors can be\npostulated, responding to a deviation above or below the controlled temperature, the\nfeedbacks from these are compared and appropriate responses to correct the load\nerror are set in motion (Mitchell et al., 1972; Horowitz, 1975; Houdas and Guieu,\n1975). Schematic illustrations of these alterations are seen in Fig. 1. Whatever the\ndetailed nature of the controller, or its precise anatomical location, the net effect of\nits activity will be integrated to increase or decrease the frequency of firing of the\nthermoregulatory effector neurons. Whether or not an individual neuron does fire at\nany instant is a function of its membrane potential and this, in turn, will depend on\nthe actions of excitatory and inhibitory influences on it.\n    It is in this last respect that the major problems arise in relation to the role of the\nvarious neurotransmitters in that one cannot define the exact point in the neuronal\nloop at which the amine is acting in most studies. Thus, modification of neuro-\nReferencesP. 259-261";
        String needle = "Neurotransmitters and temperature (regulation).";
        float s = LuceneHelper.subsumes(haystack, needle);
        LOG.debug("score {}", s);

        haystack = "Neurotransmitters and Temperature Regulation \nPETER LOMAX AND MARTIN DAVID GREEN\n\nDepartment of Pharmacology, School of Medicine and the Brain Research Institute,  University of\n                                                  California, Los Angeles, Calif. 90024 ( U . S . A . ) \nThe demonstration of relatively high concentrations of amines in the hypothalamus,\ncoupled with the elucidation of the role of this region of the brain in regulating body\ntemperature have suggested that these amines might play an important role as neuro-\ntransmitters in thermoregulation. Over the past decade or so there has been a con-\nsiderable body of research aimed at resolving these questions and several recent\nsymposia have adequately reviewed this work (Bligh and Moore, 1972; Schonbaum\nandLomax,1973;Lomaxetal.,1975).Itisnotpossible,atthepresenttime,tocon-\nstruct any unified view or general theory of the function of neurotransmitters in\nthermoregulatory mechanisms; although integrative models have frequently been\nproposed, such as those of Bligh (1972) or Myers (1975), these more often represent\nspecial cases arising from the author's own research rather than a synthesis of all\navailable data.\n    Many attempts have been made to describe the thermorzgulatory system in terms\nof cybernetic models of varying complexity (Hardy, 1972). The next step in the analysis\nof the system has been to propose neuronal networks to replace the black boxes,\nbased on experimental evidences (Hensel, 1973). One of the major controversies\nconcerns the nature of the reference point of the controller - the presence or absence\nof a temperature independent reference signal. Two major models can be derived:\none in which the controlled temperature is compared to a reference value (the “set-\npoint”) and appropriate responses are activated to correct any deviation of the\ncontrolled temperature from the reference; alternatively, two types of sensors can be\npostulated, responding to a deviation above or below the controlled temperature, the\nfeedbacks from these are compared and appropriate responses to correct the load\nerror are set in motion (Mitchell et al., 1972; Horowitz, 1975; Houdas and Guieu,\n1975). Schematic illustrations of these alterations are seen in Fig. 1. Whatever the\ndetailed nature of the controller, or its precise anatomical location, the net effect of\nits activity will be integrated to increase or decrease the frequency of firing of the\nthermoregulatory effector neurons. Whether or not an individual neuron does fire at\nany instant is a function of its membrane potential and this, in turn, will depend on\nthe actions of excitatory and inhibitory influences on it.\n    It is in this last respect that the major problems arise in relation to the role of the\nvarious neurotransmitters in that one cannot define the exact point in the neuronal\nloop at which the amine is acting in most studies. Thus, modification of neuro-\nReferencesP. 259-261";
        needle = "Neurotransmitters and lorax regulation.";
        s = LuceneHelper.subsumes(haystack, needle);
        LOG.debug("score {}", s);
    }

    String haystack = "This is a remarkably good book on graphical models. Filled with lots of examples and really takes its time trying to build intuition. Seriously, consider this book if the idea of going through Koller’s 1400 page tome feels intimidating. The book is more gentle than the others listed here but still manages a remarkable amount. Note it says Bayesian in the title so non-Bayesian algorithms and models are mostly ignored. You will need to supplement with a book like EoSL or Murphy’s book. There is a free copy on his website so be sure to preview and see if its at the right level for you.";

    @Test
    @Ignore
    public void testSpeed() {

        long start = System.currentTimeMillis();
        float s = 0;
        for (int i = 0; i < 1000; i++) {
            s += LuceneHelper.subsumes(haystack, //
                    "This is a remarkably good book on graphical models");
        }
        long time = System.currentTimeMillis() - start;
        LOG.debug("took {}ms (score {})", time, s);
    }

    @Test
    public void testSimilarity() {

        Map<Integer, String> haystack = newHashMap();
        haystack.put(
                1,
                "This is a remarkably good book on graphical models. Filled with lots of examples and really takes its time trying to build intuition. Seriously, consider this book if the idea of going through Koller’s 1400 page tome feels intimidating. The book is more gentle than the others listed here but still manages a remarkable amount. Note it says Bayesian in the title so non-Bayesian algorithms and models are mostly ignored. You will need to supplement with a book like EoSL or Murphy’s book. There is a free copy on his website so be sure to preview and see if its at the right level for you.");
        haystack.put(2, "This is  remarkably good book on graphical models");
        haystack.put(3, "This is  remarkably good book on  models");
        haystack.put(4, "what a mistake to go fishing last saturday");

        Map<Integer, Float> similarities = LuceneHelper.computeSimilarity(
                haystack, "This is a remarkably good book on graphical models", 3);

        float s1 = similarities.get(1), s2 = similarities.get(2), s3 = similarities
                .get(3), s4 = similarities.get(4);
        LOG.debug("\ns1 {}\ns2 {}\ns3 {}\ns4 {}",
                new Object[] { s1, s2, s3, s4 });

        assertTrue("we have a match", s1 > 0);
        assertTrue("we have a match", s2 > 0);
        assertTrue("we have a match", s3 > 0);
        assertTrue("no match", s4 == 0);

        assertTrue(s2 > s1);
        assertTrue(s2 > s3);
    }
    @Test
    public void testSimilarity2() {
        
        Map<Integer, String> queries = newHashMap();
        queries.put(
                1,
                "This is a remarkably good book on graphical models. Filled with lots of examples and really takes its time trying to build intuition. Seriously, consider this book if the idea of going through Koller’s 1400 page tome feels intimidating. The book is more gentle than the others listed here but still manages a remarkable amount. Note it says Bayesian in the title so non-Bayesian algorithms and models are mostly ignored. You will need to supplement with a book like EoSL or Murphy’s book. There is a free copy on his website so be sure to preview and see if its at the right level for you.");
        queries.put(2, "This is  remarkably good book on graphical models");
        queries.put(3, "This is  remarkably good book on  models");
        queries.put(4, "what a mistake to go fishing last saturday");
        
        Map<Integer, Float> similarities = LuceneHelper.computeSimilarity2(
                queries, "This is a remarkably good book on graphical models", 3);
        
        float s1 = similarities.get(1), s2 = similarities.get(2), s3 = similarities
                .get(3), s4 = similarities.get(4);
        LOG.debug("\ns1 {}\ns2 {}\ns3 {}\ns4 {}",
                new Object[] { s1, s2, s3, s4 });
        
        assertTrue("we have a match", s1 > 0);
        assertTrue("we have a match", s2 > 0);
        assertTrue("we have a match", s3 > 0);
        assertTrue("no match", s4 == 0);
        
        assertTrue(s2 > s1);
        assertTrue(s2 > s3);
    }
}
