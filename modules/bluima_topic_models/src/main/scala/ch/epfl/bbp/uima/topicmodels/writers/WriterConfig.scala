package ch.epfl.bbp.uima.topicmodels.writers
import scala.collection.immutable.HashMap

/**
 * Singleton object, storing a list of CasWriter instances used to write document features.
 * The CasWriter instances needed are meant to created by the main program and given to the WriterConfig (along with a String key).
 *   
 * These lists are then retrieved using a string key in the class writing the feature instances in the chain (at the moment only JCasWriterConsumer). 
 * 
 * This is more of a hack, since we didn't find a way to pass such objects directly in the UIMA context.
 */
object WriterConfig {
	private var confs = HashMap.empty[String, java.util.List[CasWriter]]
	
	def addConfig(key : String, list : java.util.List[CasWriter]) {
	  confs = confs.updated(key, list)
	}
	
	def getList(key : String) = {
	  confs.getOrElse(key, new java.util.ArrayList[CasWriter]())
	}
}