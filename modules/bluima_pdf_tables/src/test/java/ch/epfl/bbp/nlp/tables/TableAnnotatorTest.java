package ch.epfl.bbp.nlp.tables;

import static ch.epfl.bbp.nlp.tables.TableCorpusTest.CORPUS_PATH;
import static ch.epfl.bbp.uima.BlueUima.TEST_RESOURCES_PATH;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ch.epfl.bbp.uima.laucher.PipelineScriptParser;

//Running test cases in order of method names in ascending order
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableAnnotatorTest {

    private static String model;

    @BeforeClass
    public static void before() {
        model = "testTrain_" + System.currentTimeMillis() + ".model";
    }

    @Test
    //@Ignore
    public void testMalletEval() throws Exception {
        PipelineScriptParser.parse(
                new File(TEST_RESOURCES_PATH
                        + "pipelines/testMalletEval.pipeline"),//
                newArrayList(CORPUS_PATH)).run();
    }

    // /** train a model with test data (annotated pdfs) */
    // @Test
    // public void test1Train() throws Exception {
    //
    // TableAnnotator tableAnnotator = new TableAnnotator(Mode.train, 1, 1,
    // "target/" + model);
    //
    // for (File pdf : new File(TestHelper.ANNOTATED_PDFS)
    // .listFiles(new FileExtensionFilter("pdf"))) {
    //
    // File annotationFile = new File(pdf.getAbsolutePath().replaceAll(
    // ".pdf$", ".annot"));
    // assertTrue(annotationFile.exists());
    // tableAnnotator.process(pdf, annotationFile);
    // }
    //
    // tableAnnotator.processingComplete();
    // }
    //
    // @Test
    // public void test2Infer() {
    //
    // fail("Not yet implemented");// TODO
    // }
    //
    // @Test
    // public void testCellsPresencePipes() throws Exception {
    //
    // boolean test = false;
    // PdfDocument testDoc = TestPdf.getPdfDocument(//
    // newArrayList("12  textonline1"));
    //
    // InstanceList instanceList = new InstanceList(//
    // new SerialPipes(TablePipes.getPipes()));
    // Instance instance = new Instance(testDoc, null, "testDoc", null);
    // instanceList.addThruPipe(instance);
    //
    // Sequence sq = (Sequence) instanceList.get(0).getData();
    //
    // for (int i = 0; i < sq.size(); i++) {
    // FeatureVector fv = (FeatureVector) sq.get(i);
    // for (int idx : fv.getIndices()) {
    //
    // Object ooo = fv.getAlphabet().lookupObject(idx);
    // if (ooo.toString().equalsIgnoreCase("2plusSpace1Cells")){
    // assertTrue(fv.value(idx)==1.0);
    // test = true;
    // }
    // }
    // }
    // assertTrue(test);
    // }
    //
    // @Test
    // public void testPatternRatioPipes() throws Exception {
    //
    // boolean test = false;
    // PdfDocument testDoc = TestPdf.getPdfDocument(//
    // newArrayList("12 45 7 90"));
    //
    // InstanceList instanceList = new InstanceList(//
    // new SerialPipes(TablePipes.getPipes()));
    // Instance instance = new Instance(testDoc, null, "testDoc", null);
    // instanceList.addThruPipe(instance);
    //
    // Sequence sq = (Sequence) instanceList.get(0).getData();
    //
    // for (int i = 0; i < sq.size(); i++) {
    // FeatureVector fv = (FeatureVector) sq.get(i);
    // for (int idx : fv.getIndices()) {
    //
    // Object ooo = fv.getAlphabet().lookupObject(idx);
    // if (ooo.toString().equalsIgnoreCase("Whitespaceratio0.3_regex")){
    // assertTrue(fv.value(idx)==1.0);
    // test = true;
    // }
    // }
    // }
    // assertTrue(test);
    // }
    //
    // @Test
    // public void testFirstLinePipes() throws Exception {
    //
    // boolean test = false;
    // PdfDocument testDoc = TestPdf.getPdfDocument(//
    // newArrayList("12  textonline1"));
    //
    // InstanceList instanceList = new InstanceList(//
    // new SerialPipes(TablePipes.getPipes()));
    // Instance instance = new Instance(testDoc, null, "testDoc", null);
    // instanceList.addThruPipe(instance);
    //
    // Sequence sq = (Sequence) instanceList.get(0).getData();
    //
    // for (int i = 0; i < sq.size(); i++) {
    // FeatureVector fv = (FeatureVector) sq.get(i);
    // for (int idx : fv.getIndices()) {
    //
    // Object ooo = fv.getAlphabet().lookupObject(idx);
    // if (ooo.toString().equalsIgnoreCase("FirstLine")){
    // assertTrue(fv.value(idx)==1.0);
    // test = true;
    // }
    // }
    // }
    // assertTrue(test);
    //
    // }
    //
    // @Test
    // public void testEval() throws Exception {
    // TableAnnotator tableAnnotator = new TableAnnotator(Mode.eval, 1, 1,
    // null);
    //
    // for (File pdf : new File(TestHelper.ANNOTATED_PDFS)
    // .listFiles(new FileExtensionFilter("pdf"))) {
    //
    // File annotationFile = new File(pdf.getAbsolutePath().replaceAll(
    // ".pdf$", ".annot"));
    // assertTrue(annotationFile.exists());
    // tableAnnotator.process(pdf, annotationFile);
    // }
    //
    // tableAnnotator.processingComplete();
    // }
}
