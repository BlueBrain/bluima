package ch.epfl.bbp.uima.topicmodels.annotators;

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import ch.epfl.bbp.uima.testutils.UimaTests.getTestCas
import org.apache.uima.jcas.cas.DoubleArray
import ch.epfl.bbp.uima.testutils.UimaTests
import ch.epfl.bbp.uima.types.Topic
import scala.Array
import ch.epfl.bbp.uima.topicmodels.annotators.DCATopicModelsAnnotator.topNTopics

@RunWith(classOf[JUnitRunner])
class DCATopicModelsAnnotatorTest extends FunSuite {

  test("topNTopics") {

    val jCas = UimaTests.getTestCas
    val topicAnn = new Topic(jCas)

    topicAnn.setScores(new DoubleArray(jCas, 8))
    topicAnn.setScores(0, 0.2)
    topicAnn.setScores(1, 1.2)
    topicAnn.setScores(2, 2.2)
    topicAnn.setScores(3, 3.2)
    topicAnn.setScores(4, 4.2)
    topicAnn.setScores(5, 5.2)
    topicAnn.setScores(6, 6.2)
    topicAnn.setScores(7, 7.2)

    val scores = topicAnn.getScores
    scores.toArray.foreach(println)

    var top = topNTopics(topicAnn.getScores(), 2, 5)
    assert(top === Array(7, 6), "top 2")

    top = topNTopics(topicAnn.getScores(), 2, 51)
    assert(top === Array(), "too high prob")
    
    top = topNTopics(topicAnn.getScores(), 0, 0)
    assert(top === Array(), "0 topN")

    top = topNTopics(topicAnn.getScores(), 12, 0)
    assert(top === Array(7, 6, 5, 4, 3, 2, 1, 0), "")
  }
}
