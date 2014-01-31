///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2004 Jason Baldridge, Gann Bierner, and Tom Morton
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

package ch.epfl.bbp.shaded.opennlp.tools.lang.spanish;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelReader;
import ch.epfl.bbp.shaded.opennlp.tools.ngram.Dictionary;
import ch.epfl.bbp.shaded.opennlp.tools.postag.DefaultPOSContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.postag.POSDictionary;
import ch.epfl.bbp.shaded.opennlp.tools.postag.POSTaggerME;
import ch.epfl.bbp.shaded.opennlp.tools.postag.TagDictionary;


/**
 * A part of speech tagger that uses a model trained on Spanish data.
 */

public class PosTagger extends POSTaggerME {

  public PosTagger(String modelFile, Dictionary dict, TagDictionary tagdict) {
      super(getModel(modelFile), new DefaultPOSContextGenerator(dict),tagdict);
  }

  public PosTagger(String modelFile, TagDictionary tagdict) {
    super(getModel(modelFile), new DefaultPOSContextGenerator(null),tagdict);
  }
  
  public PosTagger(String modelFile) {
    super(getModel(modelFile), new DefaultPOSContextGenerator(null));
  }

  private static MaxentModel getModel(String name) {
    try {
      return new SuffixSensitiveGISModelReader(new File(name)).getModel();
    }
    catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * <p>Part-of-speech tag a string passed in on the command line. For
   * example: 
   *
   * <p>java opennlp.tools.lang.spanish.PosTagger -test "Sr. Smith da el auto a sus hermano en Lunes."
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage: PosTaggerME [-td tagdict] model < tokenized_sentences");
      System.err.println("       PosTaggerME -test model \"sentence\"");
      System.exit(1);
    }
    int ai=0;
    boolean test = false;
    String tagdict = null;
    while(ai < args.length && args[ai].startsWith("-")) {
      if (args[ai].equals("-test")) {
        ai++;
        test=true;
      }
      else if (args[ai].equals("-td")) {
        tagdict = args[ai+1];
        ai+=2;
      }
    }
    POSTaggerME tagger;
    String modelFile = args[ai++];
    if (tagdict != null) {
      tagger = new PosTagger(modelFile, new POSDictionary(tagdict));
    }
    else {
      tagger = new PosTagger(modelFile);
    }
    if (test) {
      System.out.println(tagger.tag(args[ai]));
    }
    else {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in,"ISO-8859-1"));
      PrintStream out = new PrintStream(System.out,true,"ISO-8859-1");
      for (String line = in.readLine(); line != null; line = in.readLine()) {
        out.println(tagger.tag(line));
      }
    }
  }
}