package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.AbbreviationsAnnotator;
import ch.epfl.bbp.uima.typesystem.Prin;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import de.julielab.jules.types.Abbreviation;

public class AbbreviationsAnnotatorTest {

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
}
