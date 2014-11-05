package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderSource;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static ch.epfl.bbp.uima.typesystem.TypeSystemSemantics.NON_CONTENT_ANNOTATIONS_OR_TOKEN;
import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.uima.fit.util.CasUtil.indexCovering;
import static org.apache.uima.fit.util.JCasUtil.getType;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.ruta.type.BREAK;

import de.julielab.jules.types.Token;

/**
 * T\d+\t([A-Z]{3,111}|Token|CW)
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN })
public class HtmlViewerWriter extends JCasAnnotator_ImplBase {

    final String css = BLUE_UTILS_ROOT
            + "src/main/resources/html_viewer/style.css";

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, defaultValue = "target/htmlViewer/", //
    description = "output directory , defaults to target/htmlViewer")
    private String outputDir;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            forceMkdir(new File(outputDir));
            copyFile(new File(css), new File(outputDir + "style.css"));
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        try {
            String pmId = getHeaderDocId(jCas);
            if (pmId == null)
                pmId = getHeaderSource(jCas);
            Writer writer = new BufferedWriter(new FileWriter(outputDir + pmId
                    + ".html"));
            writer.append("<html><body><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\">"
                    + "<link href=\"http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css\" rel=\"stylesheet\">"
                    + "</head><body><div class=\"container\">\n");

            Map<AnnotationFS, Collection<AnnotationFS>> index = indexCovering(
                    jCas.getCas(), getType(jCas, Token.class),
                    getType(jCas, Annotation.class));

            Set<Integer> breaks = newHashSet();
            for (BREAK b : select(jCas, BREAK.class)) {
                breaks.add(b.getBegin());
            }

            Set<String> lastTokenClasses = newHashSet();
            for (Token t : select(jCas, Token.class)) {

                Set<String> annots = newHashSet();
                for (AnnotationFS a : index.get(t)) {
                    if (!NON_CONTENT_ANNOTATIONS_OR_TOKEN.contains(a.getType()
                            .getName())) {
                        annots.add(a.getType().getShortName());
                    }
                }

                // adding empty span, computing shared classes
                Set<String> shared = intersection(annots, lastTokenClasses);
                lastTokenClasses = annots;// update, for next token

                writer.append("<span  title=\"" + join(shared, ", ")
                        + "\" class=\"an " + join(shared, " ")
                        + "\">&ensp;</span>"); // breakable space

                // add (annot) span
                if (!annots.isEmpty()) {
                    writer.append("<span  title=\"" + join(annots, ", ")
                            + "\" class=\"an " + join(annots, " ") + "\">"
                            + t.getCoveredText() + "</span>");
                } else
                    writer.append(t.getCoveredText() + " ");

                // add <br/>?
                if (breaks.contains(t.getEnd())) {
                    writer.append("<br/>");
                    lastTokenClasses.clear();
                }
            }
            // writer.append("\n"
            // +
            // "<p style=\"padding-top:30px;\">Legend: <span class=\"Neuron\">Neuron</span> <span class=\"NeuronWithProperties\">NeuronWithProperties</span> <span class=\"Orientation\">Orientation</span> <span class=\"Morphology\">Morphology</span> <span class=\"Size\">Size</span> <span class=\"Protein\">Protein</span> <span class=\"BrainRegion\">BrainRegion</span> <span class=\"Neurotransmitter\">Neurotransmitter</span> <span class=\"Function\">Function</span> <span class=\"Layer\">Layer</span> <span class=\"Electrophysiology\">Electrophysiology</span></p>");
            writer.append("\n</div></body></html>");
            writer.close();

        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}