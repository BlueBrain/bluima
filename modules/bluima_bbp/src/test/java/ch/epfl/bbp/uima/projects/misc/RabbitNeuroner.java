package ch.epfl.bbp.uima.projects.misc;

import java.io.File;

import org.junit.Test;

import ch.epfl.bbp.nlp.rabbit.Rabbitify;
import ch.epfl.bbp.nlp.rabbit.Rabbitify.Mode;

public class RabbitNeuroner {

    @Test
    public void testNeurons() throws Exception {

        final String NEURON = "neuron";

        File script = new File(
                "/Users/richarde/dev/bluebrain/git/Bluima/modules/bluima_bbp/pipelines/projects/extract_neurons/20140909_neuroner_3nd_scaleout.pipeline");
        String[] args = {};

        Rabbitify.run(script, args, Mode.sender, NEURON, 10);
//        Rabbitify.run(script, args, Mode.slave, NEURON, 10);
//        Rabbitify.run(script, args, Mode.receiver, NEURON, 10);
    }
}
