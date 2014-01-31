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

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelReader;
import ch.epfl.bbp.shaded.opennlp.tools.tokenize.TokenizerME;


/**
 * A tokenizer for Spanish text whish uses a maxent model.
 * @author  Jason Baldridge
 * @author Tom Morton
 */
public class Tokenizer extends TokenizerME  {
  public Tokenizer(String name) throws IOException  {
    super((new SuffixSensitiveGISModelReader(new File(name))).getModel());
    setAlphaNumericOptimization(true);
  }
  
  public Tokenizer(MaxentModel model) throws IOException {
    super(model);
    setAlphaNumericOptimization(true);
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage:  java opennlp.tools.lang.spanish.Tokenizer model < sentences");
      System.exit(1);
    }
    Tokenizer tokenizer = new Tokenizer(args[0]);
    java.io.BufferedReader inReader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in,"ISO-8859-1"));
    PrintStream out = new PrintStream(System.out,true,"ISO-8859-1");
    for (String line = inReader.readLine(); line != null; line = inReader.readLine()) {
      if (line.equals("")) {
        out.println();
      }
      else {
        String[] tokens = tokenizer.tokenize(line);
        if (tokens.length > 0) {
          out.print(tokens[0]);
        }
        for (int ti=1,tn=tokens.length;ti<tn;ti++) {
          out.print(" "+tokens[ti]);
        }
        out.println();
      }
    }
  }
}
