package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.reverse;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import de.julielab.jules.types.Sentence;

/**
 * Writes out specified Annotations and its enclosing sentence to a specified
 * text file
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PrintAnnotationInSentenceWriter extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = false, //
    description = "outputfile, or null for sysout")
    private String outputFile;

    private boolean htmlOutput = false;

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, //
    mandatory = true, description = "the full name of the annotation class")
    private String annotationClassName;
    private Class<? extends Annotation> annotationClass;

    private PrintWriter writer;
    private String startTag = "{{", endTag = "}}";

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            if (outputFile == null || outputFile.equals("null"))
                writer = new PrintWriter(System.out, true);
            else
                writer = new PrintWriter(new BufferedWriter(new FileWriter(
                        new File(outputFile))));
            if (htmlOutput) {
                startTag = "<span class=\"an\">";
                endTag = "</span>";
                writer.append("<html><head><style>.an {color:black;background:lightgreen;}</style></head><body>\n");
            } else
                writer.append("pmid:sentenceId\ttxt\n");

            annotationClass = (Class<? extends org.apache.uima.jcas.tcas.Annotation>) Class
                    .forName(annotationClassName);

        } catch (ClassNotFoundException e1) {
            throw new ResourceInitializationException(new Exception(
                    "Could not load class for Sentence " + annotationClassName));
        } catch (Exception e) {
            throw new ResourceInitializationException(
                    NO_RESOURCE_FOR_PARAMETERS, new Object[] { outputFile }, e);
        }
    }

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
        String pmid = getHeaderDocId(jcas);
        try {
            int sentenceId = 0;
            for (Sentence s : select(jcas, Sentence.class)) {
                String txt = s.getCoveredText();

                // selects annots contained in this sentence, and adds {{ }}
                // around annot, in reverse order (so it starts at end)
                ArrayList<Annotation> annots = newArrayList(selectCovered(
                        annotationClass, s));
                if (!annots.isEmpty()) {
                    for (Annotation a : reverse(annots)) {

                        int begin = a.getBegin() - s.getBegin(), end = a
                                .getEnd() - s.getBegin();

                        txt = txt.substring(0, begin) + startTag
                                + a.getCoveredText() + endTag
                                + txt.substring(end);
                    }
                    // remove newlines & normalize space
                    txt.replaceAll("\\r\\n|\\r|\\n", " ").replaceAll(" +", " ");

                    if (htmlOutput) {
                        writer.append("<p><a href=\"http://www.ncbi.nlm.nih.gov/pubmed/"
                                + pmid
                                + "\">"
                                + pmid
                                + "["
                                + sentenceId
                                + "]</a> " + txt + "</p>\n");
                    } else
                        writer.append(pmid + ":" + sentenceId + "\t" + txt
                                + "\n");
                }
                sentenceId++;
            }
            writer.flush();
        } catch (Exception e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        if (htmlOutput)
            writer.append("</body></html>");
        closeQuietly(writer);
    }
}
