package ch.epfl.bbp.uima.topicmodels.utils

import scala.io._
import java.io._
import ch.epfl.bbp.uima.topicmodels.annotators.AnnotatorUtils

/**
 * Merges files containing a list of tokens and their frequencies into one list
 *
 * Assume the files contain one token and its count per line, separated by a tab ('\t').
 * Assume that the lines are sorted in decreasing alphabetical order.
 */
object MergeMaps {
  private val log = AnnotatorUtils.getLogger

  def line2pair(s: String) = {
    val a = s.split('\t')
    (a(0), a(1).toInt)
  }

  /**
   * Merges files by considering the current top lines of each of the files. Merge the lines with the lexicographically
   * largest token, i.e. sum up all the counts and write result to new file and read the next lines.
   *
   * Example:
   * File 1	File 2	File 3   ----> output
   * z 10	z 3     y 3				z 13
   * y 1	a 3		b 2				y 4
   * r 2							r 2
   * 								b 2
   * 								a 3
   */
  def mergeFiles(stem: String, parts: Int) {
    // the file with the results
    val out = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(stem))))

    // open all input files simultaneously
    val files = for (i <- 0 until parts) yield {
      log.info("using file {} for merging", stem + i)
      Source.fromFile(new File(stem + i)).getLines()
    }

    val heads = Array.fill[(String, Int)](parts)(("", 0)) // contains the current top line of each file
    files.zipWithIndex.foreach(f =>
      if (f._1.hasNext) heads.update(f._2, line2pair(f._1.next)))

    val bottom = new String(Array.fill[Char](1)(0.toChar)) // lexicographically smallest string  

    while (files.exists(f => f.hasNext)) {
      val max_el = heads.map(_._1).max
      val no = files.zipWithIndex.find(f => f._1.hasNext)

      // merge lines with top token
      val toProcess = heads.zipWithIndex.filter(s => s._1._1 == max_el)
      out.println(max_el + "\t" + toProcess.map(_._1._2).sum)

      // update files which had a top token, i.e. read next line if available
      toProcess.map(_._2).foreach(index => {
        val f = files.apply(index)
        if (f.hasNext) {
          val next = f.next
          try {
            heads.update(index, line2pair(next))
          } catch {
            case _ => println("could not parse " + next)
          }
        } else heads.update(index, (bottom, 0)) // if we're done with a file, set head to bottom
      })
    }

    out.close();
  }

  def main(args: Array[String]) {
    if (args.size != 2) {
      println("Usage: MergeMaps [stem] [parts]")
      println("Will merge files stem0 until stem(parts-1) to stem")
    } else {
      val parts = args(1).toInt
      val stem = args(0)
      mergeFiles(stem, parts)
    }
  }
}
