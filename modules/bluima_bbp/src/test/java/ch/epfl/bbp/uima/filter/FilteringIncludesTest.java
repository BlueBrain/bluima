package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.TEST_RESOURCES_PATH;
import static ch.epfl.bbp.uima.BlueUimaHelper.BLUE_UIMA_MODULE_HOME;
import static ch.epfl.bbp.uima.BlueUimaHelper.SCRIPT_ROOT;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.cr.SingleFileReader;
import ch.epfl.bbp.uima.laucher.Pipeline;
import ch.epfl.bbp.uima.pdf.cr.PdfCollectionAnnotator;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.typesystem.ContentSection;

/**
 * Integration test for filtering framework.<br/>
 * Runs the filtering.incl pipeline on a PDF and make assertions on the
 * {@link ContentSection}s of each {@link DocumentBlock}, against a gold list of
 * {@link ContentSection}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class FilteringIncludesTest extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(FilteringIncludesTest.class);

    @Test
    public void test() throws Exception {

        // create pipeline with a PDF document
        Pipeline pipeline = new Pipeline();
        pipeline.setCr(createReaderDescription(SingleFileReader.class,
                JULIE_TSD, //
                PARAM_INPUT_FILE, BLUE_UIMA_MODULE_HOME + TEST_RESOURCES_PATH
                        + "bams/1/10842230.pdf"));
        pipeline.addAe(createEngineDescription(PdfCollectionAnnotator.class));

        // add includes
        Pipeline include = parse(new File(SCRIPT_ROOT
                + "includes/filtering.incl"));
        pipeline.addAesFrom(include);

        // add this class as AE, for assertions
        pipeline.addAe(createEngineDescription(this.getClass()));

        pipeline.run();
    }

    /**
     * make assertions on the {@link ContentSection}s of each
     * {@link DocumentBlock}.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        // System.out.println(jCas.getDocumentText());

        // gold from file
        List<String> gold = null;
        try {
            gold = LineReader.linesFrom(BLUE_UIMA_MODULE_HOME
                    + TEST_RESOURCES_PATH + "filter/10842230.sections.txt");
        } catch (FileNotFoundException e) {
            throw new AnalysisEngineProcessException(e);
        }

        // system from filtering framework
        List<Pair<String, String>> system = newArrayList();
        for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
            system.add(new Pair(block.getLabel(), block.getCoveredText()
                    .replaceAll("[\r\n]", "")));
        }

        // atomic comparison & assertion
        int lineNr = 1;
        Iterator<Pair<String, String>> systemI = system.iterator();
        Iterator<String> goldI = gold.iterator();
        while (goldI.hasNext()) {
            String g = (String) goldI.next();
            if (g.equals("null"))
                g = null;
            Pair<String, String> s = systemI.next();

            LOG.debug("l"+
                    lineNr++ + " gold: '{}' system: '{}' text = '"
                            + s.getValue() + "'", g, s.getKey());
            assertEquals(g, s.getKey());
        }
        assertFalse("no more elements", systemI.hasNext());
    }
}
