package ch.epfl.bbp.uima.topicmodels.preprocessing

import org.apache.uima.analysis_engine.AnalysisEngineDescription
import org.apache.uima.fit.factory.AnalysisEngineFactory
import ch.epfl.bbp.uima.ae.PunctuationAnnotator
import ch.epfl.bbp.uima.ae.RegexTokenizerAnnotator
import ch.epfl.bbp.uima.topicmodels.annotators.featureextraction.FeatureTokensExtractionAnnotator
import ch.epfl.bbp.uima.topicmodels.annotators.featureextraction.FeatureTokensFilterAnnotator
import ch.epfl.bbp.uima.topicmodels.annotators.IllegalCharacterSequenceAnnotator
import ch.epfl.bbp.uima.topicmodels.annotators.SimpleStemmingAnnotator
import ch.epfl.bbp.uima.topicmodels.annotators.StopwordsAnnotator
import ch.epfl.bbp.uima.topicmodels.annotators.TokenFrequencyFilterAnnotator
import ch.epfl.bbp.uima.typesystem.TypeSystem
import ch.epfl.bbp.uima.ae.OpenNlpHelper

/**
 * Collects various UIMA components consisting of annotators to preprocess different corpora.
 */
object PreprocessingEngine {
  /**
   * Pre-processing chain (without Reader) for 20 Newsgroups
   */
  def getTwentyNewsgroupsPreprocessing(stopwordsFilePath: String): List[AnalysisEngineDescription] = {

    val tokenizer = AnalysisEngineFactory.createEngineDescription(classOf[RegexTokenizerAnnotator])
    val punctuation = AnalysisEngineFactory.createEngineDescription(classOf[PunctuationAnnotator])
    val stemmer = AnalysisEngineFactory.createEngineDescription(classOf[SimpleStemmingAnnotator])
    val stopwords = AnalysisEngineFactory.createEngineDescription(classOf[StopwordsAnnotator], StopwordsAnnotator.StopwordsFilePath, stopwordsFilePath)

    val blackList: Array[String] = Array("%", ")", "(", ".", ",", ";", "!", "?", "\"", "_", ">",
      "<", "#", "^", ":", "/", "\\", "=", "-", "+", "*", "$", "@", "`", "[", "]", "&", "{", "}", "|") ++
      (0 to 9).map(_.toString) // get also rid of numbers, normally another annotator would do this (MeasureRegexAnnotator doesn't work on these data)
    val illegalChars = AnalysisEngineFactory.createEngineDescription(classOf[IllegalCharacterSequenceAnnotator],
      IllegalCharacterSequenceAnnotator.CharacterBlackList, blackList,
      IllegalCharacterSequenceAnnotator.IncludeWhiteSpace, "true")

    val filter = AnalysisEngineFactory.createEngineDescription(classOf[FeatureTokensFilterAnnotator])
    val extractor = AnalysisEngineFactory.createEngineDescription(classOf[FeatureTokensExtractionAnnotator])

    tokenizer :: punctuation :: stemmer :: stopwords :: illegalChars :: filter :: extractor :: Nil
  }

  /**
   * Some default and rough pre-processing for PubMed corpora (testing purposes).
   *
   * Does tokenization and filters very frequent and seldom tokens as well as those in the given stopword list
   */
  def getPubmedPreprocessing(stopwordsFilePath: String, tokenFrequencies: String, minFreq: Int, maxFreq: Int): List[AnalysisEngineDescription] = {
    val tokenizer = OpenNlpHelper.getTokenizer()
    val sentenceSplitter = OpenNlpHelper.getSentenceSplitter()

    val tokenFrequencyFilter = AnalysisEngineFactory.createEngineDescription(classOf[TokenFrequencyFilterAnnotator],
      TokenFrequencyFilterAnnotator.TokenFrequencyFile, tokenFrequencies,
      TokenFrequencyFilterAnnotator.MinimumFrequency, minFreq.toString,
      TokenFrequencyFilterAnnotator.MaximumFrequency, maxFreq.toString)

    val stopwords = AnalysisEngineFactory.createEngineDescription(classOf[StopwordsAnnotator],
      StopwordsAnnotator.StopwordsFilePath, stopwordsFilePath)

    val filter = AnalysisEngineFactory.createEngineDescription(classOf[FeatureTokensFilterAnnotator])
    val extractor = AnalysisEngineFactory.createEngineDescription(classOf[FeatureTokensExtractionAnnotator])

    sentenceSplitter :: tokenizer :: tokenFrequencyFilter :: stopwords :: filter :: extractor :: Nil
  }

  /**
   * No processing other than just tokenization using the OpenNlp annotator
   */
  def getPubmedRawPreprocessing(): List[AnalysisEngineDescription] = {
    val tokenizer = OpenNlpHelper.getTokenizer()
    val sentenceSplitter = OpenNlpHelper.getSentenceSplitter()

    sentenceSplitter :: tokenizer :: Nil
  }
}
