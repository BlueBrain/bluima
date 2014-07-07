package ch.epfl.bbp.uima.topicmodels.exploitation
import java.io.File
import java.io.FileInputStream
import java.io.PrintStream

import scala.Array.canBuildFrom
import scala.io.Source

/**
 * Calculates the correlation of MeSH tags and LDA topics. See report for exact model.
 */
object MeshCorrelation extends App {

  // where all corpora are there ?
  val corporaRoot = "/home/mazimmer/private/corpora"
  //
  val corpusRoot = corporaRoot + "/pubmed_complete"

  val meshFile = corporaRoot + "/mesh.tsv" // path for file containing list of MeSHs for some PubMed documents. Independant of corpora
  val pmidsPath = corpusRoot + "/doc_ids_training.txt" // PubMed ids from documents in training set
  /**
   * topic distributions for documents in training set.
   * Order of documents should be the same as in pmidsPath
   * Command: mpupd -m 0 {STEM} > topic_assignments
   */
  val documentTopicsPath = corpusRoot + "/dca/topic_assignments"

  val matrixOut = corpusRoot + "/mesh_cooccurrences" // co-occurrence matrix (meshs by topics)
  /** mapping of index in co-occurrence matrix to mesh. One mesh term per line, line nr corresponds to line nr in matrix above */
  val meshIndicesOut = corpusRoot + "/mesh_indices"

  var distinctMeshs = 0
  val pmidToMesh = initializeMeshLookup(meshFile)

  var matrix: Array[Array[Double]] = null

  // process one document after the other
  for (p <- (topicDists zip pmidTraining)) {
    val (dist, pmid) = p

    if (matrix == null) // after reading the first line, we know the number of topics -> initialize matrix
      matrix = Array.fill(distinctMeshs)(Array.fill(dist.size)(0.0))

    if (dist.size != matrix(0).size)
      println("at pos " + dist + " not same size " + dist.size)

    pmidToMesh.get(pmid) match {
      case Some(meshList) => {
        // for each mesh associated to a document, update co-occurrence matrix accordingly 
        meshList.foreach(m =>
          matrix.update(m - 1, (matrix(m - 1) zip dist).map(pr => pr._1 + pr._2 / meshList.size)))
      }
      case None => println("Didn't find any meshs for PubMed id " + p._2.toInt)
    }
  }

  // output matrix
  val out = new PrintStream(new File(matrixOut))
  for (v <- matrix) {
    for (entry <- v) {
      out.print(entry + " ")
    }
    out.println
  }

  out.close

  private def topicDists: Iterator[Array[Double]] = {
    val src = Source.fromFile(new File(documentTopicsPath))
    src.getLines.map(s => s.split(' ').drop(2).map(_.toDouble))
  }

  private def pmidTraining: Iterator[Int] = {
    val src = Source.fromFile(new File(pmidsPath))
    src.getLines.map(s => s.trim.toInt)
  }

  /**
   * Reads the MeSH file and returns a map from PubMed ids to list of MeSHs (in integer form)
   */
  private def initializeMeshLookup(path: String): Map[Int, List[Int]] = {
    val meshToIndex = scala.collection.mutable.HashMap.empty[String, Int] // converts a MeSH string to an integer
    val meshCounts = scala.collection.mutable.HashMap.empty[Int, Int] // counts the number of occurrences of a mesh

    println("Reading mesh file...")
    val f = new File(path)
    if (!f.exists)
      println("File {} doesn't exist!", f.getAbsolutePath())

    val lines = Source.fromInputStream(new FileInputStream(path)).getLines

    val map = scala.collection.mutable.HashMap.empty[Int, List[Int]]
    var id_cnt = 0
    for (l <- lines) {
      val d = l.split('\t')
      val pmid = d(0).toInt

      // update meshToIndex if unseen MeSHs are encountered
      d.tail.foreach({
        m =>
          if (!meshToIndex.get(m).isDefined)
            meshToIndex.update(m, meshToIndex.keys.size + 1)
      })

      // update result map
      val intMeshs = d.tail.map(m => meshToIndex(m)).toList
      map.update(pmid, intMeshs)

      // update counts
      intMeshs.foreach({
        m =>
          if (!meshCounts.get(m).isDefined)
            meshCounts.update(m, 1)
          else
            meshCounts.update(m, meshCounts(m) + 1)
      })

      id_cnt += 1

      if (id_cnt % 10000 == 0)
        println("read " + id_cnt + "  " + java.lang.Runtime.getRuntime().freeMemory / (1024.0 * 1024) + " " + java.lang.Runtime.getRuntime().totalMemory / (1024 * 1024.0))
    }

    distinctMeshs = meshToIndex.keys.size
    println("Read " + id_cnt + " PubMed ids. Found " + distinctMeshs + " distinct meshs ")

    val out = new PrintStream(meshIndicesOut)
// TODO broken ren   meshToIndex.toList.sort((a, b) => a._2 < b._2)
//      .foreach(p => out.println(p._1))

    Map(map.toList: _*)
  }
}