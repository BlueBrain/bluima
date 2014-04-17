package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderSource;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.AbbreviationsAnnotator;
import ch.epfl.bbp.uima.typesystem.Prin;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import de.julielab.jules.types.Abbreviation;

public class AbbreviationsAnnotatorTest extends JCasAnnotator_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(AbbreviationsAnnotatorTest.class);

    // String txt =
    // "RMI 61 140 , RMI 61 144 and RMI 61 280 are newly synthetized N- [ 8-R-dibenzo(b , f)oxepin-10-yl ] -N ' -methyl-piperazine-maleates which show interesting psychopharmacologic effects This work contains the results of a study performed with these three compounds , in order to demonstrate their neuropsycholeptic activity in comparison with chloropromazine (CPZ) and chlordiazepoxide (CPD) The inhibition of motility observed in mice shows that the compounds reduce the normal spontaneous motility as well as the muscle tone The central-depressant activity is evidenced by increased barbiturate-induced sleep and a remarkable eyelid ptosis can also be observed Our compounds do not show any activity on electroshock just as do CPZ and CPD As to the antipsychotic outline , our compounds show strong reduction of lethality due to amphetamine in grouped mice and a strong antiapomorphine activity They show also an antiaggressive effect and an inhibitory activity on avoidance behaviour much stronger than CPZ We have also found extrapyramidal effects , as catalepsy , common to many tranquillizers of the kind of the standards used by us As for vegetative phenomena , the compounds show hypotensive dose related action ranging from moderate to strong , probably due to an a-receptor inhibition Adrenolytic activity against lethal doses of adrenaline , antiserotonin and antihistaminic effects , as well as other actions (hypothermia , analgesia , etc . ) confirm that RMI 61 140 , RMI 61 144 and RMI 61 280 are endowed with pharmacologic properties similar and more potent than those of CPZ Studies on the metabolism of brain catecholamines show that they are similar to CPZ , although with less effect on dopamine level ";

    public static final String SAMPLE_TXT = "AIM:The balance of excitation and inhibition of neurons and neuronal network "
            + "is very important to perform complete neuronal function. Damage or loss of inhibitory"
            + " γ-aminobutyric acid (GABA)-ergic interneuron is associated with impaired inhibitory"
            + " control of cortical pyramidal neurons, leading to hyperexcitability and "
            + "epileptogenesis. Ectopic neurons in the basal ganglia are to be one of the "
            + "pathological features of epileptogenesis. In the present study, we investigated "
            + "distribution of interneuron subtypes between neocortex and caudate nucleus. "
            + "METHODS:We performed immunohistochemistry of GABA, glutamic acid decarboxylase (GAD), "
            + "calretinin (CR), calbindin (CB), parvalbumin (PV) and neuropeptide. We used surgical "
            + "materials of four focal cortical dysplasia (FCD) cases, having lesions of neocortex "
            + "and caudate nucleus, and eight age-matched autopsy controls. RESULTS:The pathology "
            + "showed three FCD IIa, containing dysmorphic neurons, and one FCD IIb, balloon cells. "
            + "In the neocortex, the concentrations (each positive cell number/all cell numbers in "
            + "the evaluated field) of GAD+, CR+ and CB+ cells were significantly lower in FCD "
            + "than in controls. On the contrary, in the caudate nucleus those of CR+ and CB+ "
            + "cells were significantly more in FCD than in controls. CONCLUSION:The interneuron "
            + "imbalance between the neocortex and basal ganglia may affect the epileptogenesis of FCD.";

    public static final String SAMPLE_TXT2 = "The present study was undertaken to establish the precise "
            + "anatomical relationship of the subthalamic nucleus (STh) with limbic lobe-afferented parts of the basal ganglia in the rat. The anterograde tracer Phaseolus vulgaris-leucoagglutinin (PHA-L), injected in the STh, the globus pallidus, the ventral pallidum, the ventral striatum, and the parafascicular thalamic nucleus, and the retrograde tracers Fluoro-Gold (FG) and cholera toxin B (CTb), injected in the globus pallidus, the ventral pallidum, the ventral striatum, and the ventral mesencephalon, were used for this purpose. The results of these tracing experiments confirm the general notion of reciprocal connections between the STh and pallidal areas. Thus the dorsomedial part of the STh is connected with the subcommisural ventral pallidum, whereas a more ventral and lateral part of the medial STh is related to the medial globus pallidus. The lateral hypothalamic area, directly adjacent to the STh, containing neurons with a morphology quite similar to those in the STh, projects to parts of the ventral pallidum related to the olfactory tubercle. The reciprocal projection from this pallidal area to subthalamic regions appears to be very sparse. The medial STh sends strong projections to the medial part of the entopeduncular nucleus and the adjacent lateral hypothalamic area. Sparser projections from the medial STh reach the rostral and medial part of the caudate-putamen and the nucleus accumbens. The nucleus accumbens sends a very sparse projection back to the medial STh. The projections of the medial STh to the ventral mesencephalon appear also to be topographically organized. The lateral hypothalamus and a few cells in the most medial part of the STh project to the ventral tegmental area, whereas progressively more lateral parts of the ventral mesencephalon, in particular the substantia nigra, receive input from successively more lateral and caudal parts of the STh. In addition, a number of STh fibers reach the midbrain extrapyramidal area. The lateral part of the parafascicular thalamic nucleus projects to the lateral part of the STh, whereas parafascicular neurons medial to the fasciculus retroflexus project to the dorsomedial portion of the STh. The medial part of the STh and the adjacent lateral hypothalamus are intimately connected with limbic parts of the basal ganglia in a way similar and parallel to the connections of the lateral STh with motor-related parts of the basal ganglia. These findings suggest a role for the STh in nonmotor functions of the basal ganglia.";

    @Test
    public void testAbrevs() throws Exception {

        JCas jCas = getTestCas(SAMPLE_TXT);

        PipelineBuilder p = new JcasPipelineBuilder(jCas);
        p.add(AbbreviationsAnnotator.class);
        p.process();

        Collection<Abbreviation> abrevs = select(jCas, Abbreviation.class);
        for (Abbreviation abrev : abrevs) {
            Prin.t(abrev);
        }
        assertEquals(15, abrevs.size());
        // TODO more assertions
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        LOG.warn("pdf:: " + getHeaderSource(jCas));
        Collection<Abbreviation> abrevs = select(jCas, Abbreviation.class);
        for (Abbreviation abrev : abrevs) {
            LOG.warn(To.string(abrev));
        }
    }
}
