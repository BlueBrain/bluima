package ch.epfl.bbp.uima.topicmodels.annotators
import org.apache.uima.UimaContext
import java.io.File
import de.julielab.jules.types.Token
import org.slf4j.LoggerFactory
import ch.epfl.bbp.uima.types.Keep
import org.apache.uima.jcas.tcas.Annotation

/**
 * Some helper functions implementing frequently occurring tasks in annotators.
 */
object AnnotatorUtils {

  def getStringParamFromContext(context: UimaContext, param: String, default: String, mandatory: Boolean = false): String = {
    getRawParmeterFromContext(context, param, default, mandatory, x => x.toString)
  }

  def getBooleanParamFromContext(context: UimaContext, param: String, default: Boolean, mandatory: Boolean = false): Boolean = {
    getRawParmeterFromContext(context, param, default, mandatory, x => x.toString.toBoolean)
  }

  def getIntParamFromContext(context: UimaContext, param: String, default: Int, mandatory: Boolean = false): Int = {
    getRawParmeterFromContext(context, param, default, mandatory, x => x.toString.toInt)
  }

  def getDoubleParamFromContext(context: UimaContext, param: String, default: Double, mandatory: Boolean = false): Double = {
    getRawParmeterFromContext(context, param, default, mandatory, x => x.toString.toDouble)
  }

  def getLongParamFromContext(context: UimaContext, param: String, default: Long, mandatory: Boolean = false): Long = {
    getRawParmeterFromContext(context, param, default, mandatory, x => x.toString.toLong)
  }

  private def getRawParmeterFromContext[T](context: UimaContext, param: String, default: T, mandatory: Boolean = false, conv: (java.lang.Object => T)): T = {
    val raw = context.getConfigParameterValue(param)

    if (raw != null) {
      conv(raw)
    } else {
      if (!mandatory) defaultLogger.info("Warning: No parameter found for " + param)
      else new RuntimeException("No parameter found for " + param)

      default
    }
  }

  def prepareFile(path: String) = { // TODO refactor
    val f = new File(path)

    defaultLogger.info("Opening file " + f.getAbsolutePath)

    if (!f.exists)
      new RuntimeException("File " + f.getAbsolutePath() + " does not exist!")
    if (!f.canRead)
      new RuntimeException("Cannot read file " + f.getAbsolutePath() + " !")

    scala.io.Source.fromFile(f)
  }

  private val defaultLogger = LoggerFactory.getLogger("TopicModels")
  def getLogger = {
    defaultLogger
  }
}