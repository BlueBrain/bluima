///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2002 Jason Baldridge and Gann Bierner
// 
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////

package ch.epfl.bbp.shaded.opennlp.tools.postag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import ch.epfl.bbp.shaded.opennlp.maxent.DataStream;
import ch.epfl.bbp.shaded.opennlp.maxent.Evalable;
import ch.epfl.bbp.shaded.opennlp.maxent.EventCollector;
import ch.epfl.bbp.shaded.opennlp.maxent.EventStream;
import ch.epfl.bbp.shaded.opennlp.maxent.GISModel;
import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream;
import ch.epfl.bbp.shaded.opennlp.maxent.TwoPassDataIndexer;
import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelWriter;
import ch.epfl.bbp.shaded.opennlp.tools.ngram.Dictionary;
import ch.epfl.bbp.shaded.opennlp.tools.ngram.MutableDictionary;
import ch.epfl.bbp.shaded.opennlp.tools.util.BeamSearch;
import ch.epfl.bbp.shaded.opennlp.tools.util.Pair;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;


/**
 * A part-of-speech tagger that uses maximum entropy.  Trys to predict whether
 * words are nouns, verbs, or any of 70 other POS tags depending on their
 * surrounding context.
 *
 * @author      Gann Bierner
 * @version $Revision: 1.16 $, $Date: 2005/11/14 19:50:43 $
 */
public class POSTaggerME implements Evalable, POSTagger {

  /**
   * The maximum entropy model to use to evaluate contexts.
   */
  protected MaxentModel _posModel;

  /**
   * The feature context generator.
   */
  protected POSContextGenerator _contextGen;

  /**
   * Tag dictionary used for restricting words to a fixed set of tags.
   */
  protected TagDictionary tagDictionary;
  
  protected Dictionary ngramDictionary;

  /**
   * Says whether a filter should be used to check whether a tag assignment
   * is to a word outside of a closed class.
   */
  protected boolean _useClosedClassTagsFilter = false;
  
  private static final int DEFAULT_BEAM_SIZE =3;

  /** The size of the beam to be used in determining the best sequence of pos tags.*/
  protected int size;

  private Sequence bestSequence;
  
  /** The search object used for search multiple sequences of tags. */
  protected  BeamSearch beam;

  public POSTaggerME(MaxentModel mod, Dictionary dict) {
    this(mod, new DefaultPOSContextGenerator(dict));
  }
  
  public POSTaggerME(MaxentModel mod,Dictionary dict,TagDictionary tagdict) {
      this(DEFAULT_BEAM_SIZE,mod, new DefaultPOSContextGenerator(dict),tagdict);
    }
  
  public POSTaggerME(MaxentModel mod, POSContextGenerator cg) {
    this(DEFAULT_BEAM_SIZE,mod,cg,null);
  }
  
  public POSTaggerME(MaxentModel mod, POSContextGenerator cg, TagDictionary dict) {
      this(DEFAULT_BEAM_SIZE,mod,cg,dict);
    }

  public POSTaggerME(int beamSize, MaxentModel mod, POSContextGenerator cg, TagDictionary tagdict) {
    size = beamSize;
    _posModel = mod;
    _contextGen = cg;
    beam = new PosBeamSearch(size, cg, mod);
    tagDictionary = tagdict;
  }

  public String getNegativeOutcome() {
    return "";
  }
  
  /**
   * Returns the number of different tags predicted by this model.
   * @return the number of different tags predicted by this model.
   */
  public int getNumTags() {
    return _posModel.getNumOutcomes();
  }

  public EventCollector getEventCollector(Reader r) {
    return new POSEventCollector(r, _contextGen);
  }

  public List tag(List sentence) {
    bestSequence = beam.bestSequence(sentence,null);
    return bestSequence.getOutcomes();
  }

  public String[] tag(String[] sentence) {
    List t = tag(Arrays.asList(sentence));
    return ((String[]) t.toArray(new String[t.size()]));
  }

  public void probs(double[] probs) {
    bestSequence.getProbs(probs);
  }

  public double[] probs() {
    return bestSequence.getProbs();
  }

  public String tag(String sentence) {
    ArrayList toks = new ArrayList();
    StringTokenizer st = new StringTokenizer(sentence);
    while (st.hasMoreTokens())
      toks.add(st.nextToken());
    List tags = tag(toks);
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < tags.size(); i++)
      sb.append(toks.get(i) + "/" + tags.get(i) + " ");
    return sb.toString().trim();
  }

  public void localEval(MaxentModel posModel, Reader r, Evalable e, boolean verbose) {

    _posModel = posModel;
    float total = 0, correct = 0, sentences = 0, sentsCorrect = 0;
    BufferedReader br = new BufferedReader(r);
    String line;
    try {
      while ((line = br.readLine()) != null) {
        sentences++;
        Pair p = POSEventCollector.convertAnnotatedString(line);
        List words = (List) p.a;
        List outcomes = (List) p.b;
        List tags = beam.bestSequence(words, null).getOutcomes();

        int c = 0;
        boolean sentOk = true;
        for (Iterator t = tags.iterator(); t.hasNext(); c++) {
          total++;
          String tag = (String) t.next();
          if (tag.equals(outcomes.get(c)))
            correct++;
          else
            sentOk = false;
        }
        if (sentOk)
          sentsCorrect++;
      }
    }
    catch (IOException E) {
      E.printStackTrace();
    }

    System.out.println("Accuracy         : " + correct / total);
    System.out.println("Sentence Accuracy: " + sentsCorrect / sentences);

  }

  private class PosBeamSearch extends BeamSearch {

    public PosBeamSearch(int size, POSContextGenerator cg, MaxentModel model) {
      super(size, cg, model);
    }
    
    public PosBeamSearch(int size, POSContextGenerator cg, MaxentModel model, int cacheSize) {
      super(size, cg, model, cacheSize);
    }

    
    protected boolean validSequence(int i, Object[] inputSequence, String[] outcomesSequence, String outcome) {
      if (tagDictionary == null) {
        return true;
      }
      else {
        String[] tags = tagDictionary.getTags(inputSequence[i].toString());
        if (tags == null) {
          return true;
        }
        else {
          return Arrays.asList(tags).contains(outcome);
        }
      }
    }
    
    protected boolean validSequence(int i, List inputSequence, Sequence outcomesSequence, String outcome) {
      if (tagDictionary == null) {
        return true;
      }
      else {
        String[] tags = tagDictionary.getTags(inputSequence.get(i).toString());
        if (tags == null) {
          return true;
        }
        else {
          return Arrays.asList(tags).contains(outcome);
        }
      }
    }
  }
  
  public String[] getOrderedTags(List words, List tags, int index) {
    return getOrderedTags(words,tags,index,null);
  }
  
  public String[] getOrderedTags(List words, List tags, int index,double[] tprobs) {
    double[] probs = _posModel.eval(_contextGen.getContext(index,words.toArray(),(String[]) tags.toArray(new String[tags.size()]),null));
    String[] orderedTags = new String[probs.length];
    for (int i = 0; i < probs.length; i++) {
      int max = 0;
      for (int ti = 1; ti < probs.length; ti++) {
        if (probs[ti] > probs[max]) {
          max = ti;
        }
      }
      orderedTags[i] = _posModel.getOutcome(max);
      if (tprobs != null){
        tprobs[i]=probs[max];
      }
      probs[max] = 0;
    }
    return (orderedTags);
  }

  public static GISModel train(EventStream es, int iterations, int cut) throws IOException {
    return ch.epfl.bbp.shaded.opennlp.maxent.GIS.trainModel(iterations, new TwoPassDataIndexer(es, cut));
  }
  
  private static void usage() {
    System.err.println("Usage: POSTaggerME [-encoding encoding] [-dict dict_file] training model [cutoff] [iterations]");
    System.err.println("This trains a new model on the specified training file and writes the trained model to the model file.");
    System.err.println("-encoding Specifies the encoding of the training file");
    System.err.println("-dict Specifies that a dictionary file should be created for use in distinguising between rare and non-rare words");
    System.exit(1);
  }

  /**
     * <p>Trains a new pos model.</p>
     *
     * <p>Usage: java opennlp.postag.POStaggerME [-encoding charset] [-d dict_file] data_file  new_model_name (iterations cutoff)?</p>
     *
     */
  public static void main(String[] args) throws IOException {
    if (args.length == 0){
      usage();
    }
    int ai=0;
    try {
      String encoding = null;
      String dict = null;
      while (args[ai].startsWith("-")) {
        if (args[ai].equals("-encoding")) {
          ai++;
          if (ai < args.length) {
            encoding = args[ai++];
          }
          else {
            usage();
          }
        }
        else if (args[ai].equals("-dict")) {
          ai++;
          if (ai < args.length) {
            dict = args[ai++];
          }
          else {
            usage();
          }
        }
        else {
          System.err.println("Unknown option "+args[ai]);
          usage();
        }
      }
      File inFile = new File(args[ai++]);
      File outFile = new File(args[ai++]);
      int cutoff = 5;
      int iterations = 100;
      if (args.length > ai) {
        cutoff = Integer.parseInt(args[ai++]);
        iterations = Integer.parseInt(args[ai++]);
      }
      GISModel mod;
      if (dict != null) {
        System.err.println("Building dictionary");
        MutableDictionary mdict = new MutableDictionary(cutoff);
        DataStream data = new ch.epfl.bbp.shaded.opennlp.maxent.PlainTextByLineDataStream(new java.io.FileReader(inFile));
        while(data.hasNext()) {
          String tagStr = (String) data.nextToken();
          String[] tt = tagStr.split(" ");
          String[] words = new String[tt.length];
          for (int wi=0;wi<words.length;wi++) {
            words[wi] = tt[wi].substring(0,tt[wi].lastIndexOf('_'));
          }
          mdict.add(words,1,true);
        }
        System.out.println("Saving the dictionary");
        mdict.persist(new File(dict));
      }
      EventStream es;
      if (encoding == null) {
        if (dict == null) {
          es = new POSEventStream(new PlainTextByLineDataStream(new InputStreamReader(new FileInputStream(inFile))));
        }
        else {
          es = new POSEventStream(new PlainTextByLineDataStream(new InputStreamReader(new FileInputStream(inFile))), new Dictionary(dict));
        }
      }
      else {
        if (dict == null) {
          es = new POSEventStream(new PlainTextByLineDataStream(new InputStreamReader(new FileInputStream(inFile),encoding)));
        }
        else {
          es = new POSEventStream(new PlainTextByLineDataStream(new InputStreamReader(new FileInputStream(inFile),encoding)), new Dictionary(dict));
        }
      }
      mod = train(es, iterations, cutoff);
      System.out.println("Saving the model as: " + outFile);
      new SuffixSensitiveGISModelWriter(mod, outFile).persist();

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
