package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static org.apache.uima.fit.util.CasUtil.indexCovering;
import static org.apache.uima.fit.util.JCasUtil.getType;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ae.cleanup.DisambiguatorAnnotator;
import de.julielab.jules.types.Token;

/**
 * @author renaud.richardet@epfl.ch
 */
public class HtmlViewerWriter extends JCasAnnotator_ImplBase {

    final String css = BlueUima.BLUE_UTILS_ROOT
            + "src/main/resources/html_viewer/style.css";

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, defaultValue = "target/htmlViewer/", //
    description = "output directory , defaults to target/htmlViewer")
    private String outputDir;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            FileUtils.forceMkdir(new File(outputDir));
            FileUtils
                    .copyFile(new File(css), new File(outputDir + "style.css"));
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        try {
            String pmId = BlueCasUtil.getHeaderDocId(jCas);
            Writer writer = new BufferedWriter(new FileWriter(outputDir + pmId
                    + ".html"));
            writer.append("<html><body><head><link href=\"style.css\" rel=\"stylesheet\" type=\"text/css\"></head>\n");

            Map<AnnotationFS, Collection<AnnotationFS>> index = indexCovering(
                    jCas.getCas(), getType(jCas, Token.class),
                    getType(jCas, Annotation.class));

            for (Token t : select(jCas, Token.class)) {

                Set<String> hasAnnot = new HashSet<String>();
                for (AnnotationFS a : index.get(t)) {
                    // if (BIO_ANNOTATIONS.contains(a.getClass())) {
                    if (!DisambiguatorAnnotator.SKIP_ANNOTATIONS.contains(a
                            .getClass().getName())) {
                        // Prin.t(a);
                        hasAnnot.add(a.getClass().getSimpleName());
                    }
                }
                if (!hasAnnot.isEmpty()) {
                    writer.append("<span  title=\""
                            + StringUtils.join(hasAnnot, ", ")
                            + "\" class=\"an "
                            + StringUtils.join(hasAnnot, " ") + "\">"
                            + t.getCoveredText() + "</span> ");
                } else
                    writer.append(t.getCoveredText() + " ");
            }
            writer.append("\n</body></html>");
            writer.close();

        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}