package ch.epfl.bbp.uima.ae

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import ch.epfl.bbp.uima.testutils.UimaTests.getTestCas
import ch.epfl.bbp.uima.TopicModelsHelper
import org.scalatest.FlatSpec

@RunWith(classOf[JUnitRunner])
class MergePreProcTest extends FlatSpec {

  val testFiles = TopicModelsHelper.TOPIC_MODELS_TEST_ROOT + "MergePreProc/"

  it should "Shortest path goes through annotations 'abc' and 'ef'" in {

    ch.epfl.bbp.uima.topicmodels.preprocessing.MergePreProc.merge(testFiles)
  }

}