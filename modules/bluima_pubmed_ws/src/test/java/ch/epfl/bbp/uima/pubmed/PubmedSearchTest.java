package ch.epfl.bbp.uima.pubmed;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;

import com.google.common.collect.Lists;

public class PubmedSearchTest {

    @Test
    public void testNeuron() throws Exception {
        List<PubmedArticleType> articles = Lists
                .newArrayList(new PubmedSearch().search("axon", 5));
        assertEquals(5, articles.size());
    }

    @Test
    public void testNeuron2() throws Exception {
        List<PubmedArticleType> articles = Lists
                .newArrayList(new PubmedSearch().search("mouse", 90));
        assertEquals(90, articles.size());
    }

    @Test
    @Ignore
    public void testNeurons() throws Exception {
        writePmIds("target/neuron.txt", newArrayList("neuron"));
    }

    @Test
    @Ignore
    public void testNeuronsMore() throws Exception {

        writePmIds("target/neurogliaform_cell.txt",
                newArrayList("neurogliaform cell[Title/Abstract]"));
        writePmIds("target/neurogliaform_neuron.txt",
                newArrayList("neurogliaform neuron[Title/Abstract]"));
        // writePmIds("target/basket_cell.txt",
        // newArrayList("basket cell[Title/Abstract]"));
        // writePmIds("target/basket_neuron.txt",
        // newArrayList("basket neuron[Title/Abstract]"));
        // writePmIds("target/axon_cell.txt",
        // newArrayList("axon cell[Title/Abstract]"));
        // writePmIds("target/axon_neuron.txt",
        // newArrayList("axon neuron[Title/Abstract]"));

        // writePmIds("target/pyramidal_cell.txt",
        // newArrayList("pyramidal cell[Title/Abstract]"));
        // writePmIds("target/pyramidal_neuron.txt",
        // newArrayList("pyramidal neuron[Title/Abstract]"));
        //
        // writePmIds("target/stellate_cell.txt",
        // newArrayList("stellate cell[Title/Abstract]"));
        // writePmIds("target/stellate_neuron.txt",
        // newArrayList("stellate neuron[Title/Abstract]"));
        //
        // writePmIds("target/martinotti.txt",
        // newArrayList("martinotti[Title/Abstract]"));
        //
        // writePmIds("target/bitufted_cell.txt",
        // newArrayList("bitufted cell[Title/Abstract]"));
        // writePmIds("target/bitufted_neuron.txt",
        // newArrayList("bitufted neuron[Title/Abstract]"));
        //
        // writePmIds("target/chandelier.txt",
        // newArrayList("chandelier[Title/Abstract]"));
        //
        // writePmIds("target/bouquet_cell.txt",
        // newArrayList("bouquet cell[Title/Abstract]"));
        // writePmIds("target/bouquet_neuron.txt",
        // newArrayList("bouquet neuron[Title/Abstract]"));
        //
        // writePmIds("target/bipolar_cell.txt",
        // newArrayList("bipolar cell[Title/Abstract]"));
        // writePmIds("target/bipolar_neuron.txt",
        // newArrayList("bipolar neuron[Title/Abstract]"));
        //
        // writePmIds("target/neurogliaform.txt",
        // newArrayList("neurogliaform[Title/Abstract]"));
    }

    @Test
    @Ignore
    public void testCellNeurons() throws Exception {
        writePmIds("target/cell_neurons.txt", newArrayList("neuron", "cell"));
    }

    private void writePmIds(String outFile, ArrayList<String> queries)
            throws IOException {
        TextFileWriter writer = new TextFileWriter(outFile);
        for (String query : queries) {
            List<Integer> pmIds = new PubmedSearch().searchIds(query);
            for (Integer pmId : pmIds) {
                writer.addLine(pmId + "");
            }
            writer.flush();
        }
        writer.close();
    }

    @Test
    public void testMesh() throws Exception {
        List<Integer> articles = new PubmedSearch().searchIds("Axons[mesh]");
        assertTrue(articles.size() > 10000);
    }

    @Test
    public void testPmid() throws Exception {
        List<Integer> articles = new PubmedSearch().searchIds("16381840[pmid]");
        assertTrue(articles.size() == 1);
        articles = new PubmedSearch().searchIds("16381840");
        assertTrue(articles.size() == 1);
        articles = new PubmedSearch().searchIds("17170002 16381840");
        assertTrue(articles.size() == 2);
    }

    @Test
    public void testPmidArticle() throws Exception {
        List<PubmedArticleType> articles = newArrayList(new PubmedSearch()
                .search("23447503[pmid]"));
        assertTrue(articles.size() == 1);
        assertEquals("23447503", articles.get(0).getMedlineCitation().getPMID()
                .toString());
        // String q =
        // " 23447502 23447501 23447500 23447499 23447498 23447497 23447496 23447495 23447494 23447493 23447492 23447491 23447490 23447489 23447488 23447487 23447486 23447485 23447484 23447483 23447482 23447481 23447480 23447479 23447478 23447477 23447476 23447475 23447474 23447473 23447472 23447471 23447470 23447469 23447468 23447467 23447466 23447465 23447464 23447463 23447462 23447461 23447460 23447459 23447458 23447457 23447456 23447455 23447454 23447453 23447452 23447451 23447450 23447449 23447448 23447447 23447446 23447445 23447444 23447443 23447442 23447441 23447440 23447439 23447438 23447437 23447436 23447435 23447434 23447433 23447432 23447431 23447430 23447429 23447428 23447427 23447426 23447425 23447424 23447423 23447422 23447421 23447420 23447419 23447418 23447417 23447416 23447415 23447414 23447413 23447412 23447410 23447409 23447408 23447407 23447406 23447405 23447404 23447403 23447402 23447401 23447400 23447399 23447398 23447397 23447396 23447395 23447394 23447393 23447392 23447391 23447390 23447389 23447388 23447387 23447386 23447385 23447384 23447383 23447382 23447381 23447380 23447379 23447378 23447377 23447376 23447375 23447374 23447373 23447372 23447371 23447370 23447369 23447368 23447367 23447366 23447365 23447364 23447363 23447362 23447361 23447360 23447359 23447358 23447357 23447356 23447355 23447354 23447353 23447352 23447351 23447350 23447349 23447348 23447347 23447346 23447345 23447344 23447343 23447342 23447341 23447340 23447339 23447338 23447337 23447336 23447335 23447334 23447333 23447332 23447331 23447145 23447144 23447143 23447142 23447141 23447140 23447139 23447138 23447137 23447136 23447135 23447134 23447133 23447132 23447131 23447130 23447129 23447128 23447127 23447126 23447125 23447124 23447123 23447122 23447121 23447120 23447119 23447118 23447117 23447116 23447115 23447114 23447113 23447112 23447111 23447110 23447109 23447108 23447107 23447106 23447105 23447104 23447103 23447102 23447101 23447100 23447099 23447098 23447097 23447096 23447095 23447094 23447093 23447092 23447091 23447090 23447089 23447088 23447087 23447086 23447085 23447084 23447083 23447082 23447081 23447080 23447079 23447078 23447077 23447076 23447075 23447074 23447073 23447072 23447071 23447070 23447069 23447068 23447067 ";
        // articles = newArrayList(new PubmedSearch()
        // .search(q+"[pmid]"));
        // assertEquals(250,articles.size());

    }

    @Test
    @Ignore
    public void testLayers() throws Exception {
        PubmedSearch searcher = new PubmedSearch();
        for (String layer : new LineReader(new FileInputStream(
                "src/test/resources/20130208_layers.txt"))) {
            searcher.search("\"" + layer + "\"", 1);
        }
    }

    @Test
    @Ignore
    public void testDaysBackIds() throws Exception {
        List<Integer> articles = newArrayList(new PubmedSearch2()
                .searchIdsDaysBack(1));
        assertTrue(articles.size() > 10);

        TextFileWriter writer = new TextFileWriter("target/last_day.txt");
        for (Integer article : articles) {
            writer.addLine(article + "");
        }
        writer.close();
    }

    @Test
    @Ignore
    public void testDaysBack() throws Exception {
        List<PubmedArticleType> articles = newArrayList(new PubmedSearch2()
                .searchDaysBack(1));
        assertTrue(articles.size() > 10);

        TextFileWriter writer = new TextFileWriter("target/last_day.txt");
        for (PubmedArticleType art : articles) {
            writer.addLine(art.getMedlineCitation().getPMID() + "\t"
                    + art.getMedlineCitation().getArticle().getArticleTitle());
        }
        writer.close();
    }
}
