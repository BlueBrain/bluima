package ch.epfl.bbp.uima.ae
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import ch.epfl.bbp.uima.testutils.UimaTests._
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder
import ch.epfl.bbp.uima.topicmodels.annotators.StopwordsAnnotator
import ch.epfl.bbp.uima.topicmodels.annotators.TokenFrequencyFilterAnnotator
import ch.epfl.bbp.uima.TopicModelsHelper
import ch.epfl.bbp.uima.types.Keep
import de.julielab.jules.types.Token
import org.apache.uima.jcas.JCas
import org.apache.uima.fit.factory.AnalysisEngineFactory
import ch.epfl.bbp.uima.typesystem.TypeSystem
import org.apache.uima.resource.metadata.TypeSystemDescription
import org.apache.uima.fit.util.JCasUtil._
import ch.epfl.bbp.uima.types.Punctuation
import ch.epfl.bbp.uima.types.Measure
import de.julielab.jules.types.Protein
import ch.epfl.bbp.uima.types.FeatureTokens

@RunWith(classOf[JUnitRunner])
class FeatureTokensExtractionAnnotator2Test extends FunSuite {

  test("dryrun") {
    val jCas = getTestCas("abc")

    val s1 = createAnnot(jCas, classOf[Keep], 0, 1)
    s1.setEnclosedAnnot(
      createAnnot(jCas, classOf[Token], 0, 1))
    s1.setNormalizedText("a")

    val s2 =
      createAnnot(jCas, classOf[Keep], 2, 3)
    s2.setEnclosedAnnot(
      createAnnot(jCas, classOf[Protein], 2, 3))
    s2.setNormalizedText("c")

    val builder = new JcasPipelineBuilder(jCas)
    builder.add(AnalysisEngineFactory.createEngineDescription(classOf[FeatureTokensExtractionAnnotator2]))
    builder.process()

    assert(2 === select(jCas, classOf[Keep]).size)

    assert(1 === select(jCas, classOf[FeatureTokens]).size)
    val ft = selectSingle(jCas, classOf[FeatureTokens])

    assert(2 === ft.getBeginnings().size)
    assert(0 === ft.getBeginnings(0))
    assert(1 === ft.getEndings(0))
    assert(2 === ft.getBeginnings(1))
    assert(3 === ft.getEndings(1))

  }
}
