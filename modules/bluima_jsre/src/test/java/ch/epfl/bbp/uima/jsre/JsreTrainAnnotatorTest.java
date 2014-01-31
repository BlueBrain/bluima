package ch.epfl.bbp.uima.jsre;

import static ch.epfl.bbp.uima.JsreHelper.TEST_PIPELINES_BASE;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;

import java.io.File;

public class JsreTrainAnnotatorTest {

    public static void main(String[] args) throws Exception {
        parse(new File(TEST_PIPELINES_BASE + "JsreTrainAnnotatorTest.pipeline"))
                .run();

    }
}
