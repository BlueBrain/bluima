package ch.epfl.bbp.uima.topicmodels.preprocessing
import java.io.File
import java.io.PrintStream
import org.apache.uima.resource.metadata.TypeSystemDescription
import org.apache.uima.util.CasCreationUtils
import org.apache.uima.fit.factory.CollectionReaderFactory
import ch.epfl.bbp.uima.typesystem.TypeSystem
import ch.epfl.bbp.uima.cr.TextArrayReader

/**
 * Dumps the entire Pubmed abstract corpus to a text file
 */
object PubmedDumper extends App {
  val outputFile = new File("/media/mz/New Volume/pubmedAbstracts_compl.txt")
  outputFile.createNewFile()

  val cr = CollectionReaderFactory.createReader(
//TODO    classOf[PubmedWholeDatabaseCR], TypeSystem.JULIE_TSD: TypeSystemDescription)
    classOf[TextArrayReader], TypeSystem.JULIE_TSD: TypeSystemDescription)

  var cnt = 0
  val cas = CasCreationUtils.createCas(cr.getProcessingResourceMetaData());
  val stream = new PrintStream(outputFile)
  while (cr.hasNext()) {
    cr.getNext(cas)
    stream.println(cas.getDocumentText())
    cas.getDocumentText()
    cas.reset()
    cnt += 1
    if (cnt % 100000 == 0) println("read " + cnt)
  }

  stream.close()
  println("Read documents" + cnt)
}