///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2003 Thomas Morton
//
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.tools.lang.english;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelReader;
import ch.epfl.bbp.shaded.opennlp.tools.parser.Parse;
import ch.epfl.bbp.shaded.opennlp.tools.parser.ParserME;
import ch.epfl.bbp.shaded.opennlp.tools.util.Span;


/**
 * Class for performing full parsing on English text. 
 */
public class TreebankParser {

  private static Pattern untokenizedParenPattern1 = Pattern.compile("([^ ])([({)}])");
  private static Pattern untokenizedParenPattern2 = Pattern.compile("([({)}])([^ ])");
  public static ParserME getParser(String dataDir, boolean useTagDictionary, boolean useCaseSensitiveTagDictionary, int beamSize, double advancePercentage) throws IOException {
    if (useTagDictionary) {
      return new ParserME(
        new SuffixSensitiveGISModelReader(new File(dataDir + "/build.bin.gz")).getModel(),
        new SuffixSensitiveGISModelReader(new File(dataDir + "/check.bin.gz")).getModel(),
        new ParserTagger(dataDir + "/tag.bin.gz", dataDir + "/tagdict", useCaseSensitiveTagDictionary ),//, new Dictionary(dataDir+"/dict.bin.gz")),
        new ParserChunker(dataDir + "/chunk.bin.gz"),
        new HeadRules(dataDir + "/head_rules"),beamSize,advancePercentage);
    }
    else {
      return new ParserME(
        new SuffixSensitiveGISModelReader(new File(dataDir + "/build.bin.gz")).getModel(),
        new SuffixSensitiveGISModelReader(new File(dataDir + "/check.bin.gz")).getModel(),
        new ParserTagger(dataDir + "/tag.bin.gz",null), //new Dictionary(dataDir+"/dict.bin.gz")),
        new ParserChunker(dataDir + "/chunk.bin.gz"),
        new HeadRules(dataDir + "/head_rules"),beamSize,advancePercentage);
    }
  }
  
  public static ParserME getParser(String dataDir) throws IOException {
    return getParser(dataDir,true,true,ParserME.defaultBeamSize,ParserME.defaultAdvancePercentage);
  }
  
  private static String convertToken(String token) {
    if (token.equals("(")) {
      return "-LRB-";
    }
    else if (token.equals(")")) {
      return "-RRB-";
    }
    else if (token.equals("{")) {
      return "-LCB-";
    }
    else if (token.equals("}")) {
      return "-RCB-";
    }
    return token;
  }
  
  public static Parse[] parseLine(String line, ParserME parser, int numParses) {
    line = untokenizedParenPattern1.matcher(line).replaceAll("$1 $2");
    line = untokenizedParenPattern2.matcher(line).replaceAll("$1 $2");
    StringTokenizer str = new StringTokenizer(line);
    StringBuffer sb = new StringBuffer();
    List tokens = new ArrayList();
    while (str.hasMoreTokens()) {
      String tok = convertToken(str.nextToken());
      tokens.add(tok);
      sb.append(tok).append(" ");
    }
    String text = sb.substring(0, sb.length() - 1).toString();
    Parse p = new Parse(text, new Span(0, text.length()), "INC", 1, null);
    int start = 0;
    for (Iterator ti = tokens.iterator(); ti.hasNext();) {
      String tok = (String) ti.next();
      p.insert(new Parse(text, new Span(start, start + tok.length()), ParserME.TOK_NODE, 0));
      start += tok.length() + 1;
    }
    Parse[] parses;
    if (numParses == 1) {
      parses = new Parse[] { parser.parse(p)};
    }
    else {
      parses = parser.parse(p,numParses);
    }
    return parses;
  }

  private static void usage() {
    System.err.println("Usage: TreebankParser -[id] -bs -ap dataDirectory < tokenized_sentences");
    System.err.println("dataDirectory: Directory containing parser models.");
    System.err.println("-d: Use tag dictionary.");
    System.err.println("-i: Case insensitive tag dictionary.");
    System.err.println("-bs 20: Use a beam size of 20.");
    System.err.println("-ap 0.95: Advance outcomes in with at least 95% of the probability mass.");
    System.err.println("-k 5: Show the top 5 parses.  This will also display their log-probablities.");
    System.exit(1);
  }
  

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      usage();
    }
    boolean useTagDictionary = false;
    boolean caseSensitiveTagDictionary = true;
    boolean showTopK = false;
    int numParses = 1;
    int ai = 0;
    int beamSize = ParserME.defaultBeamSize;
    double advancePercentage = ParserME.defaultAdvancePercentage;
    while (args[ai].startsWith("-")) {
      if (args[ai].equals("-d")) {
        useTagDictionary = true;
      }
      else if (args[ai].equals("-i")) {
        caseSensitiveTagDictionary = false;
      }
      else if (args[ai].equals("-bs")) {
      	if (args.length > ai+1) {
          try {
            beamSize=Integer.parseInt(args[ai+1]);
            ai++;
          }
          catch(NumberFormatException nfe) {
            System.err.println(nfe);
            usage();
          }
      	}
      	else {
      	  usage();
      	}
      }
      else if (args[ai].equals("-ap")) {
        if (args.length > ai+1) {
          try {
            advancePercentage=Double.parseDouble(args[ai+1]);
            ai++;
          }
          catch(NumberFormatException nfe) {
            System.err.println(nfe);
            usage();
          }
      	}
      	else {
      	  usage();
      	}
      }
      else if (args[ai].equals("-k")) {
        showTopK = true;
        if (args.length > ai+1) {
          try {
            numParses=Integer.parseInt(args[ai+1]);
            ai++;
          }
          catch(NumberFormatException nfe) {
            System.err.println(nfe);
            usage();
          }
      	}
      	else {
      	  usage();
      	}
      }
      else if (args[ai].equals("--")) {
      	ai++;
        break;
      }
      ai++;
    }
    ParserME parser;
    if (!caseSensitiveTagDictionary) {
      parser = TreebankParser.getParser(args[ai++], true, false,beamSize,advancePercentage);
    }
    else if (useTagDictionary) {
      parser = TreebankParser.getParser(args[ai++], true, true,beamSize,advancePercentage);
    }
    else {
      parser = TreebankParser.getParser(args[ai++], false, false,beamSize,advancePercentage);
    }
    BufferedReader in;
    if (ai == args.length) {
      in = new BufferedReader(new InputStreamReader(System.in));
    }
    else {
      in = new BufferedReader(new FileReader(args[ai]));
    }
    String line;
    try {
      while (null != (line = in.readLine())) {
        if (line.length() == 0) {
          System.out.println();          
        }
        else {
          Parse[] parses = parseLine(line, parser, numParses);
          for (int pi=0,pn=parses.length;pi<pn;pi++) {
            if (showTopK) {
              System.out.print(pi+" "+parses[pi].getProb()+" ");
            }
            parses[pi].show();
          }
        }
      }
    }
    catch (IOException e) {
      System.err.println(e);
    }
  }
}