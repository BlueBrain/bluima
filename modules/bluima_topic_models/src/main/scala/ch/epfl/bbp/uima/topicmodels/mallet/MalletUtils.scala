package ch.epfl.bbp.uima.topicmodels.mallet
import cc.mallet.types.InstanceList
import cc.mallet.types.LabelAlphabet
import cc.mallet.types.FeatureSequence
import cc.mallet.types.Instance
import org.slf4j.LoggerFactory

/**
 * Contains utils to convert diverse artifacts to the corresponding Mallet representation
 */
object MalletUtils {
  private val log = LoggerFactory.getLogger("MalletUtils")
  /**
   * Converts a document represented as a list of tokens to a Mallet instance
   */
  def createInstance(doc: List[String], alphabet: LabelAlphabet, dict: Map[String, Int], id: String = "0"): Instance = {
    val f = new FeatureSequence(alphabet, doc.size)
    doc.foreach(t => {
      if (dict.contains(t)) f.add(dict(t))
      else f.add(t) // out of vocabulary. Need add(object) method to add this new term into the alphabet
    })

    new Instance(f, null, "doc=" + id, null)
  }

  /**
   * Creates a Mallet InstanceList out of a list of documents
   */
  def createInstances(docs: List[List[String]], alphabet: LabelAlphabet, dict: Map[String, Int]): InstanceList = {
    val instances = new InstanceList()
    docs.zipWithIndex
      .map(p => createInstance(p._1, alphabet, dict, p._2.toString))
      .foreach(i => instances.add(i))
    instances
  }

  /**
   * Constructs a Mallet alphabet out of a mapping from string to feature numbers and a list of documents
   * The list of documents is used to add out of vocabulary terms to the alphabet.
   */
  def getAlphabet(dict: Map[String, Int], docs: List[List[String]]): LabelAlphabet = {
    val alph = new LabelAlphabet

    dict.toList.sortWith((a, b) => a._2 < b._2)
      .foreach(p => alph.lookupIndex(p._1, true))

    // just to verify that we have a reasonable alphabet
    assert(dict.forall(x => alph.lookupLabel(x._1).getIndex() == x._2))

    // add out of vocabulary tokens
    docs.foreach(d => d.foreach(token => alph.lookupIndex(token, true)))

    alph
  }

  /**
   * Converts a matrix of co-occurrences of terms and topics into a Mallet internal representation.
   * This is a sparse matrix where feature number and its number of occurrences are encoded into one integer using bitwise operators.
   */
  def getTypeTopicCounts(counts: Array[Array[Int]]): Array[Array[Int]] = {
    val tbits = topicBits(counts(0).size)

    def occurrencesToMask(p: (Int, Int)): Int = {
      if (p._1 > 0) (p._1 << tbits) + p._2
      else 0
    }

    val cnt = counts.map(row => row.zipWithIndex.sortWith((a, b) => if (a._1 != b._1) a._1 > b._1 else a._2 > b._2) // sort in descending order
      .map(p => occurrencesToMask(p))) // convert to Mallets special format
    cnt
  }

  /**
   * Gets bitmask to optain the number of topics
   * Mallet stores the token ID and its count in one integer using bitwise operators
   */
  def topicMask(nrTopics: Int) = {
    if (Integer.bitCount(nrTopics) == 1) nrTopics // exact power of 2
    else Integer.highestOneBit(nrTopics) * 2 - 1 // otherwise add an extra bit
  }

  def topicBits(nrTopics: Int) = Integer.bitCount(topicMask(nrTopics))

  def tokensPerTopic(counts: Array[Array[Int]]): Array[Int] = {
    val tokensCnt = new Array[Int](counts(1).length)
    for (l <- 0 to counts.length - 1) {
      for (t <- 0 to tokensCnt.length - 1) {
        tokensCnt(t) += counts(l)(t)
      }
    }
    return tokensCnt
  }
  //  def tokensPerTopic(counts: Array[Array[Int]]): Array[Int] = counts.transpose.map(_.sum)

  /**
   * Transforms a co-occurence matrix of terms and topics into counts, such that the entries in the matrix
   * add up corpusSize.
   *
   * With large matrices and a relatively small corpus size, the relative error might be large.
   */
  def convertProbabilityMatrixToCountsMatrix(m: Array[Array[Float]], corpusSize: Int): Array[Array[Int]] = {
    val msum = m.map(_.sum).sum
    val factor = corpusSize / msum
    val res = m.map(_.map(x => (x * factor).round.toInt))
    log.info("convertProbabilityMatrixToCountsMatrix: Sum of all entries before " + msum + ". Adjustment factor " + factor)
    log.info("Sum of all entries after " + res.map(_.sum).sum + ". Corpus size " + corpusSize + ". Relative error " + (res.map(_.sum).sum - corpusSize) / corpusSize.toDouble)
    res
  }
}