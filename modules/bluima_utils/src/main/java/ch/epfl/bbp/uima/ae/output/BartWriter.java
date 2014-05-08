package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.filterStrict;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_ROOT;
import static ch.epfl.bbp.uima.typesystem.TypeSystemSemantics.NON_CONTENT_ANNOTATIONS;
import static java.lang.System.out;
import static org.apache.commons.io.FileUtils.copyDirectory;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.forceMkdir;
import static org.apache.uima.fit.util.JCasUtil.indexCovered;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.DocumentPage;
import ch.epfl.bbp.uima.typesystem.To;
import de.julielab.jules.types.Sentence;

/**
 * A consumer that writes out specified Annotations into static files, for
 * BRAT's stand-off format (http://brat.nlplab.org/standoff.html)
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BartWriter extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(BartWriter.class);

    public static final String PARAM_ADD_BRAT_FILES = "addBratFiles";
    @ConfigurationParameter(name = PARAM_ADD_BRAT_FILES, defaultValue = "true", //
    description = "whether to add the complete, working brat server")
    private boolean addBratFiles;

    public static final String PARAM_BATCH_PREFIX = "batchPrefix";
    @ConfigurationParameter(name = PARAM_BATCH_PREFIX, defaultValue = "", //
    description = "prefix to add for each file in Brat; useful when comparing different batches")
    private String batchPrefix;

    public static final String PARAM_DEBUG = "debug";
    @ConfigurationParameter(name = PARAM_DEBUG, defaultValue = "false", //
    description = "prints out annotations to StdOut")
    private boolean debug;

    public static final String PARAM_WRITE_NOTES = "notes";
    @ConfigurationParameter(name = PARAM_WRITE_NOTES, defaultValue = "false", //
    description = "write notes (for hover)")
    private boolean writeNotes;

    public static final String SPLIT_PER_PAGE = "splitPerPage";
    @ConfigurationParameter(name = SPLIT_PER_PAGE, defaultValue = "false", //
    description = "splits out Bart output per page")
    private boolean splitPerPage;

    @ConfigurationParameter(name = BlueUima.PARAM_OUTPUT_DIR, defaultValue = "brat", mandatory = false)
    private String outputDir;
    private File dataDir;
    private int processedFiles = 0;

    /** to write Brat annotation set */
    private Set<String> annotationsSet = new HashSet<String>();

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            File outputDirFile = new File(outputDir);
            forceMkdir(outputDirFile);

            if (addBratFiles) {
                // copy brat server files
                copyDirectory(new File(BLUE_UTILS_ROOT
                        + "src/main/resources/bart/brat-v1.3_Crunchy_Frog"),
                        outputDirFile);
                // write annotations into 'data' dir of brat
                dataDir = new File(outputDirFile, "data");
            } else {
                dataDir = outputDirFile;
            }

            // write annotation conf
            TextFileWriter textWriter = new TextFileWriter(dataDir
                    + "/annotation.conf");
            textWriter.addLine("\n[entities]\n");
            for (String a : annotationsSet) {
                textWriter.addLine(a);
            }
            textWriter.addLine("\n[events]\n\n[relations]\n\n[attributes]\n\n");
            textWriter.close();
            // copy visual.conf
            copyFile(new File(BLUE_UTILS_ROOT
                    + "src/main/resources/bart/visual.conf"), new File(dataDir
                    + "/visual.conf"));

        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String pmId = getHeaderDocId(jCas);
        try {
            if (splitPerPage) {
                Map<DocumentPage, Collection<Annotation>> indexPerPage = indexCovered(
                        jCas, DocumentPage.class, Annotation.class);
                for (Entry<DocumentPage, Collection<Annotation>> page : indexPerPage
                        .entrySet()) {
                    write(pmId, jCas, getOutDir() + pmId + "-p"
                            + page.getKey().getPageId(), page.getValue());
                }

            } else {
                write(pmId, jCas, getOutDir() + pmId, jCas.getAnnotationIndex());
            }
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }

        processedFiles++;
    }

    private void write(String pmid, JCas jCas, String path,
            Iterable<Annotation> annotations) throws IOException {

        // write sentences
        TextFileWriter textWriter = new TextFileWriter(path + ".txt");
        for (Annotation s : filterStrict(annotations, Sentence.class)) {
            textWriter.addLine(s.getCoveredText());
        }
        textWriter.close();
        if (debug)
            System.out.println("\nAnnotations for " + pmid);

        // write annotations
        BufferedWriter annotWriter = new BufferedWriter(new FileWriter(
                new File(path + ".ann")));
        int id = 0;
        String notes = "";
        for (Annotation a : annotations) {
            String aName = a.getClass().getName();
            if (!NON_CONTENT_ANNOTATIONS.contains(aName)) {
                try {
                    annotationsSet.add(a.getClass().getSimpleName());
                    String annotStr = "T" + id + "\t"
                            + a.getClass().getSimpleName() + " " + a.getBegin()
                            + " " + a.getEnd() + "\t" + a.getCoveredText()
                            + "\n";
                    if (debug)
                        System.out.println(annotStr);
                    annotWriter.append(annotStr);
                    notes += "#" + id + "\tAnnotatorNotes T" + id + "\t"
                            + To.string(a) + "\n"; // TODO better
                    id++;
                } catch (Exception e) {
                    LOG.warn("could not extract " + aName + " from " + pmid
                            + " {}", e.getMessage());
                }
            }
        }
        if (writeNotes)
            annotWriter.append(notes);
        annotWriter.close();

        // TODO write relations
    }

    private String getOutDir() {
        int batchSuffix = processedFiles / 100;
        String outDir = dataDir + "/" + batchPrefix + "_" + batchSuffix + "/";
        new File(outDir).mkdirs();
        return outDir;
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        if (addBratFiles) {
            // print some info message
            out.println("\n###############################################################################");
            out.println("BRAT annotation viewer\n");
            out.println("The BRAT server can be found at:\n" + outputDir + "\n");
            out.println("To start the standalone server, type\npython standalone.py");
            out.println("And then open your browser at http://localhost:8001/index.xhtml");
            out.println("Tutorial is here:\nhttp://localhost:8001/index.xhtml#/tutorials_bio/000-introduction");
            out.println("###############################################################################\n\n");
        }
    }

}