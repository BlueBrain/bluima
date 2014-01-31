package ch.epfl.bbp.uima.topicmodels.mallet
import java.io.File
import java.io.FileFilter
import java.util.regex.Pattern
import java.util.ArrayList
import scala.collection.JavaConversions._
import cc.mallet.pipe.iterator.FileIterator
import cc.mallet.pipe.CharSequence2TokenSequence
import cc.mallet.pipe.CharSequenceLowercase
import cc.mallet.pipe.Pipe
import cc.mallet.pipe.SerialPipes
import cc.mallet.pipe.TokenSequence2FeatureSequence
import cc.mallet.pipe.TokenSequenceRemoveStopwords
import cc.mallet.topics.ParallelTopicModel
import cc.mallet.types.FeatureSequence
import cc.mallet.types.Instance
import cc.mallet.types.InstanceList
import java.io.PrintWriter
import cc.mallet.types.TokenSequence
import cc.mallet.types.Token
import scala.io.Source
import ch.epfl.bbp.uima.topicmodels.mallet.StringIterator
import java.net.URI
import java.io.PrintStream
import java.util.zip.GZIPOutputStream
import java.io.BufferedOutputStream
import java.io.FileOutputStream

/**
 * Some code to train 20 newsgroups corpus with Mallet.
 * 
 * For experimental purposes.
 */
object Mallet20Newsgroups extends App {
  private val dirPath: String = "/home/mz/Documents/epfl/projet/corpora/twenty_newsgroups"
  private val numberOfTopics: Int = 30

  // numberOfTopics/10.0
  private val model = new ParallelTopicModel(numberOfTopics, numberOfTopics, ParallelTopicModel.DEFAULT_BETA)
  //private val model = ParallelTopicModel.read(new File(dirPath + "/mallet_model"))
  
  private val pipeList = Seq(
    new CharSequence2TokenSequence(Pattern.compile("[^\\s]*\\s")), //Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")),
    //new PrintPipe(),
    new TokenSequence2FeatureSequence())

  //train
  //println(test)
  
  analyseMalletFile()   
    
  
  private def analyseMalletFile() {
    val instances = InstanceList.load(new File("/home/mz/Documents/epfl/projet/external_src/mallet/mallet/test.mallet"))
    println(instances.size())
    var cnt = 0
    
    instances.take(5).foreach(x => println(x.getData().asInstanceOf[FeatureSequence]))
    
    instances.foreach(in => cnt += in.getData().asInstanceOf[FeatureSequence].size())
    println("tokens " + cnt)
  }
  
  private def readCorpus(file : File) = {
	val instances = new InstanceList(new SerialPipes(pipeList))
    println("Reading 20 Newsgroups files from  " + file.getAbsolutePath())

    val lines = Source.fromFile(file).getLines()
    instances.addThruPipe(new StringIterator(lines))
    println("Read " + instances.size + " instances")

    instances
  }

  def train() {
    val instances = readCorpus( new File(dirPath + "/training.txt"))
    model.addInstances(instances)
    
    val it = 1000
    model.setNumThreads(4)
    model.setNumIterations(it)
    model.setBurninPeriod(it+1)
    //model.saveSampleInterval
    
    //model.setSaveState(25, dirPath + "/mallet_states/iteration")
    model.estimate()
    val stateFile = new File(dirPath + "/malletStateFile.gz")
    val out = new PrintStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(stateFile))))
    model.printState(out)
    out.close()

    print("alpha: ")
    model.alpha.take(10).foreach(i => print(i + " "))
    println()
    println("oktens " + model.totalTokens)
    println("Model likelihood " + model.modelLogLikelihood())
    println("LL/token " + (model.modelLogLikelihood()/model.totalTokens) )
    model.write(new File(dirPath + "/mallet_model"))
  }

  def test() = {
     val estimator = model.getProbEstimator()

     println("getTokensPerTopic " + estimator.getTokensPerTopic().length)
     println("getTypeTopicCounts " + estimator.getTypeTopicCounts().length)
     val arr = estimator.getTypeTopicCounts()
     val x = arr.map(l => l.apply(0))
   
     estimator.getTokensPerTopic().take(10).foreach(println(_))
     println("asf " + arr.map(l => l(0)).sum)
     
     arr.take(10).foreach(l => {
       l.foreach(e => print( "[" + (  e & model.topicMask ) + " " + (  e >> model.topicBits ) + "]"))
       println()
       })
     
     println("max len " + arr.map(x => x.length).max)
     
     println("getTypeTopicCounts ss " + estimator.getTypeTopicCounts().apply(0).length)

     val instances = readCorpus( new File(dirPath + "/test.txt"))
     val si = instances(0)
     println("DATA0 " + si.getData())
     println("DATA1 " + instances(1).getData())
     println("DATA2 " + instances(2).getData())
     
    System.out.println("estimating likelihoods")
    val out = new PrintStream(new File(dirPath + "/mallet_evaluation.txt"))

    val totalLogLikelihood = estimator.evaluateLeftToRight(instances, 20, true, out)
    System.out.println("estimating likelihoods end")

    val perplexity = Math.pow(2.0, -(totalLogLikelihood / instances.size()))
    val tokens : Int = estimator.getTokensPerTopic().sum
    out.println("total number of tokens\t " +  tokens)
    out.println("total log likelihood\t" + totalLogLikelihood)
    out.println("LL/token\t " + (tokens/totalLogLikelihood*1.0))
    out.println("perplexity\t" + perplexity)
    out.close()
    perplexity 
  }
  
}

class TxtFilter extends FileFilter {
  override def accept(file: File): Boolean = file.getName().matches("\\d*")
}

class File2CharSequence extends Pipe {
  override def pipe(inst: Instance): Instance = {
    val f = inst.getData().asInstanceOf[File]
    val docText = scala.io.Source.fromFile(f).mkString
    inst.setData(docText)
    inst.setTarget(f.getParent()) // TODO del
    inst
  }
}

class PrintPipe extends Pipe {
  var cnt = 0
    override def pipe(inst: Instance): Instance = {
		  if(cnt < 10) {
		    val ts = inst.getData().asInstanceOf[TokenSequence]
		    print(ts.size() + " ")
		    ts.foreach(f => print(f.getText() + "|"))
		    println()
			  
		  }
      cnt += 1
      inst
    }
}
class StringIterator(it: Iterator[String]) extends Iterator[Instance] {
  var index = 0

  override def next() = {
    val n = it.next()  
    val uri = new URI("docId=" + index)
    index += 1
    new Instance (n, null, uri, null) //TODO ! danger
  }
  
  override def hasNext = it.hasNext
}
