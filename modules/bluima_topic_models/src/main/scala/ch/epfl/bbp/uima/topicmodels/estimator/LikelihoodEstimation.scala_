package ch.epfl.bbp.uima.topicmodels.estimator
import scala.io.Source
import java.io.File
import scopt.immutable.OptionParser
import scala.collection.immutable.HashMap
import java.io.PrintStream
import org.slf4j.LoggerFactory

/**
 * Trait which different front-ends have to implement.
 *  
 */
trait LikelihoodEstimation[C <: EstimationConfig] {
  protected val log = LikelihoodEstimation.getLogger
  
  /**
   * Parses arguments and instantiates estimation procedure.
   */
  def estimate(args : Array[String], c : C) = {
    val parser = argumentParser
    parser.parse(args, c) map { config =>
      val alpha = getAlpha(config)
      val matrix = getTermTopicMatrix(config)
      val docs = getTestDocs( config.testDocsPath )
      val dict = getTokenDict(config)
      
      val est = new MalletBasedEstimator(matrix, alpha, getBeta(config), docs, dict)

      log.info("Start estimation using " + config.estimationCycles + " cycles")
      log.info("Test corpus likelihood: " + est.estimateLikelihood(config.estimationCycles))
    } getOrElse {
      // arguments are bad, usage message will have been displayed
    }
    
  }

  /**
   * Gives back an OptionParser which handles the parsing of the command line arguments.
   */
  protected def argumentParser() : OptionParser[C]
  /**
   * Gets the values for the alpha prior used in the model
   */
  protected def getAlpha(c : C) : Array[Double]
  /**
   *  Gets the values for the beta prior used in the model
   */
  protected def getBeta(c : C) : Double
  /**
   * Gets the matrix of co-occurrence counts of terms and topics.
   * The rows represent terms, the columns topics.
   */
  protected def getTermTopicMatrix(c : C) : Array[Array[Int]]
  
  /**
   * Returns a dictionary for mapping strings in the pre-processed documents to its feature number. 
   */
  protected def getTokenDict(c : C) : Map[String, Int] = {
    val src = prepareFile(c.tokenFilePath)
    var map = HashMap.empty[String,Int]
    src.getLines()
    	.map(l => l.split(' '))
    	.map(x => (x(0), x(1).toInt))
    	.foreach(p => map += p)
    map
  }
  
  /**
   * Utility method to open a file for reading
   */
  protected def prepareFile(path : String) : Source = {
    val f = new File(path)
    if(!f.exists() || !f.canRead()) 
      new RuntimeException("Cannot open required file " + f.getAbsolutePath())
    log.info("Opening file " + f.getAbsolutePath + " for reading")
    Source.fromFile(f)
  }
  
  /**
   * Gets the test documents. A document is represented as a list of tokens.
   * 
   * Assume each document is written on one line and each token is separated by a space character
   */
  def getTestDocs(path : String) : List[List[String]] = {
    val src = prepareFile(path)
    src.getLines().map(s => s.split(' ').toList).toList
  }
}
object LikelihoodEstimation {
  private val defaultLogger = LoggerFactory.getLogger("Estimation")
  def getLogger = {
    defaultLogger
  }
}
