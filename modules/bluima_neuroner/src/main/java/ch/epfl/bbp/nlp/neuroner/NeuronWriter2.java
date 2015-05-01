package ch.epfl.bbp.nlp.neuroner;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static java.lang.String.format;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.uima.fit.util.CasUtil.indexCovered;
import static org.apache.uima.fit.util.JCasUtil.getType;
import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

import neuroner.NeuroNER.Neuron;
import neuroner.NeuroNER.NeuronProperty;
import neuroner.NeuroNER.Neuron;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.julielab.jules.types.Sentence;

/**
 * Writes neurons to a file that can be imported in a db. Neuron format:<br>
 * <code>pmId, neuron_id, sentence_id, begin, end, type, text</code>
 * 
 * @author renaud.richardet@epfl.ch
 */
// because of instance fields:
@OperationalProperties(multipleDeploymentAllowed = false)
public class NeuronWriter2 extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(NeuronWriter2.class);

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, defaultValue = "out.txt", //
    description = "outputfile, or System for sysout")
    protected String outputFile;

    private int sentence_id = 1, neuron_id = 1;
    private PrintWriter writer, sentenceWriter;

    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {

            writer = new PrintWriter(new BufferedWriter(new FileWriter(
                    new File(outputFile))));
            sentenceWriter = new PrintWriter(new BufferedWriter(new FileWriter(
                    new File(outputFile + "_sentence"))));

        } catch (Exception e) {
            throw new ResourceInitializationException(
                    NO_RESOURCE_FOR_PARAMETERS, new Object[] { outputFile }, e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {

        int pmId = getHeaderIntDocId(jCas);

        try {
            Map<AnnotationFS, Collection<AnnotationFS>> idxSentences = indexCovered(
                    jCas.getCas(), //
                    getType(jCas, Sentence.class),
                    getType(jCas, Neuron.class));

            Map<AnnotationFS, Collection<AnnotationFS>> idxProperties = indexCovered(
                    jCas.getCas(), //
                    getType(jCas, Neuron.class),
                    getType(jCas, NeuronProperty.class));
            Map<AnnotationFS, Collection<AnnotationFS>> idxNeurons = indexCovered(
                    jCas.getCas(), //
                    getType(jCas, Neuron.class),
                    getType(jCas, Neuron.class));

            for (Sentence s : JCasUtil.select(jCas, Sentence.class)) {

                for (AnnotationFS nwp_ : idxSentences.get(s)) {
                    Neuron nwp = (Neuron) nwp_;

                    // properties
                    for (AnnotationFS np : idxProperties.get(nwp)) {
                        String type = np.getType().getShortName();
                        writer.append(format("%d\t%d\t%d\t%d\t%d\t%s\t%s\n",//
                                pmId, neuron_id, sentence_id, np.getBegin(), np
                                        .getEnd(), type, np.getCoveredText()
                                        .replaceAll("[\t\r\n]", "")));
                    }

                    // neuron
                    Neuron n = ((Neuron) (idxNeurons.get(nwp).iterator().next()));
                    writer.append(format("%d\t%d\t%d\t%d\t%d\t%s\t%s\n",//
                            pmId, neuron_id, sentence_id, n.getBegin(), n
                                    .getEnd(), "Neuron", n.getCoveredText()
                                    .replaceAll("[\t\r\n]", "")));
                    neuron_id++;

                }
                // sentence
                sentenceWriter
                        .append(sentence_id
                                + "\t"
                                + s.getCoveredText()
                                        .replaceAll("[\t\r\n]", " ") + "\n");
                sentence_id++;
            }

            writer.flush();
            sentenceWriter.flush();

        } catch (Exception e) {
            LOG.warn("could not process " + pmId, e);
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        closeQuietly(writer);
        closeQuietly(sentenceWriter);
    }
}
