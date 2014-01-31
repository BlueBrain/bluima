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
//GNU Lesser General Public License for more details.
// 
//You should have received a copy of the GNU Lesser General Public
//License along with this program; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////
package ch.epfl.bbp.shaded.opennlp.tools.lang.english;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;
import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelReader;
import ch.epfl.bbp.shaded.opennlp.tools.namefind.NameContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.namefind.NameFinderME;
import ch.epfl.bbp.shaded.opennlp.tools.parser.Parse;
import ch.epfl.bbp.shaded.opennlp.tools.util.Span;


/**
 * Class is used to create a name finder for English.
 */
public class NameFinder extends NameFinderME {
  
  public static String[] NAME_TYPES = {"person", "organization", "location", "date", "time", "percentage", "money"};

  /** Creates an English name finder using the specified model.
   * @param mod The model used for finding names.
   */
  public NameFinder(MaxentModel mod) {
    super(mod);
  }

  /** Creates an English name finder using the specified model and context generator.
   * @param mod The model used for finding names.
   * @param cg The context generator used with the specified model.
   */
  public NameFinder(MaxentModel mod, NameContextGenerator cg) {
    super(mod, cg);
  }

  /** Creates an English name finder using the specified model and context generator 
   * which will be decoded using the specified beamSize. 
   * @param mod The model used for finding names.
   * @param cg The context generator used with the specified model.
   * @param beamSize The size of the beam used for decoding.
   */
  public NameFinder(MaxentModel mod, NameContextGenerator cg, int beamSize) {
    super(mod, cg, beamSize);
  }

  public static Span[] tokenizeToSpans(String s) {
    CharacterEnum charType = CharacterEnum.WHITESPACE;
    CharacterEnum state = charType;

    List tokens = new ArrayList();
    int sl = s.length();
    int start = -1;
    char pc = 0;
    for (int ci = 0; ci < sl; ci++) {
      char c = s.charAt(ci);
      if (Character.isWhitespace(c)) {
        charType = CharacterEnum.WHITESPACE;
      }
      else if (Character.isLetter(c)) {
        charType = CharacterEnum.ALPHABETIC;
      }
      else if (Character.isDigit(c)) {
        charType = CharacterEnum.NUMERIC;
      }
      else {
        charType = CharacterEnum.OTHER;
      }
      if (state == CharacterEnum.WHITESPACE) {
        if (charType != CharacterEnum.WHITESPACE) {
          start = ci;
        }
      }
      else {
        if (charType != state || (charType == CharacterEnum.OTHER && c != pc)) {
          tokens.add(new Span(start, ci));
          start = ci;
        }
      }
      state = charType;
      pc = c;
    }
    if (charType != CharacterEnum.WHITESPACE) {
      tokens.add(new Span(start, sl));
    }
    return ((Span[]) tokens.toArray(new Span[tokens.size()]));
  }

  public static String[] spansToStrings(Span[] spans, String s) {
    String[] tokens = new String[spans.length];
    for (int si = 0, sl = spans.length; si < sl; si++) {
      tokens[si] = s.substring(spans[si].getStart(), spans[si].getEnd());
    }
    return tokens;
  }

  public static String[] tokenize(String s) {
    return spansToStrings(tokenizeToSpans(s), s);
  }
  
  private static void addNames(String tag, List names, Parse[] tokens) {
    for (int ni=0,nn=names.size();ni<nn;ni++) {
      Span nameTokenSpan = (Span) names.get(ni);
      Parse startToken = tokens[nameTokenSpan.getStart()];
      Parse endToken = tokens[nameTokenSpan.getEnd()];
      Parse commonParent = startToken.getCommonParent(endToken);
      //System.err.println("addNames: "+startToken+" .. "+endToken+" commonParent = "+commonParent);
      if (commonParent != null) {
        Span nameSpan = new Span(startToken.getSpan().getStart(),endToken.getSpan().getEnd());
        if (nameSpan.equals(commonParent.getSpan())) {
          commonParent.insert(new Parse(commonParent.getText(),nameSpan,tag,1.0));
        }
        else {
          Parse[] kids = commonParent.getChildren();
          boolean crossingKids = false;
          for (int ki=0,kn=kids.length;ki<kn;ki++) {
            if (nameSpan.crosses(kids[ki].getSpan())){
              crossingKids = true;
            }
          }
          if (!crossingKids) {
            commonParent.insert(new Parse(commonParent.getText(),nameSpan,tag,1.0));
          }
          else {
            if (commonParent.getType().equals("NP")) {
              Parse[] grandKids = kids[0].getChildren();
              if (grandKids.length > 1 && nameSpan.contains(grandKids[grandKids.length-1].getSpan())) {
                commonParent.insert(new Parse(commonParent.getText(),commonParent.getSpan(),tag,1.0));
              }
            }
          }
        }
      }
    }
  }
  
  private static Map[] createPrevTokenMaps(NameFinder[] finders){
    Map[] prevTokenMaps = new HashMap[finders.length];
    for (int fi = 0, fl = finders.length; fi < fl; fi++) {
      prevTokenMaps[fi]=new HashMap();
    }
    return prevTokenMaps;
  }
  
  private static void clearPrevTokenMaps(Map[] prevTokenMaps) {
    for (int mi = 0, ml = prevTokenMaps.length; mi < ml; mi++) {
      prevTokenMaps[mi].clear();
    }
  }
  
  private static void updatePrevTokenMaps(Map[] prevTokenMaps, Object[] tokens, String[][] finderTags) {
    for (int mi = 0, ml = prevTokenMaps.length; mi < ml; mi++) {
      for (int ti=0,tn=tokens.length;ti<tn;ti++) {
        //System.err.println("updatePrevTokenMaps:"+tokens[ti]+" -> "+finderTags[mi][ti]);
        prevTokenMaps[mi].put(tokens[ti],finderTags[mi][ti]);
      }
    }
  }


  private static void processParse(NameFinder[] finders, String[] tags, BufferedReader input) throws IOException {
    String[][] finderTags = new String[finders.length][];
    Map[] prevTokenMaps = createPrevTokenMaps(finders);
    for (String line = input.readLine(); null != line; line = input.readLine()) {
      if (line.equals("")) {
        System.out.println();
        clearPrevTokenMaps(prevTokenMaps);
        continue;
      }
      Parse p = Parse.parseParse(line);
      Parse[] tokens = p.getTagNodes();
      //System.err.println(java.util.Arrays.asList(tokens));
      for (int fi = 0, fl = finders.length; fi < fl; fi++) {
        finderTags[fi] = finders[fi].find(tokens, prevTokenMaps[fi]);
        //System.err.println("EnglishNameFinder.processParse: "+tags[fi] + " " + java.util.Arrays.asList(finderTags[fi]));
      }
      updatePrevTokenMaps(prevTokenMaps,tokens,finderTags);
      for (int fi = 0, fl = finders.length; fi < fl; fi++) {
        int start = -1;
        List names = new ArrayList(5);
        for (int ti = 0, tl = tokens.length; ti < tl; ti++) {
          if ((finderTags[fi][ti].equals(NameFinderME.START) || finderTags[fi][ti].equals(NameFinderME.OTHER))) {
            if (start != -1) {
              names.add(new Span(start,ti-1));
            }
            start = -1;
          }
          if (finderTags[fi][ti].equals(NameFinderME.START)) {
            start = ti;
          }
        }
        if (start != -1) {
          names.add(new Span(start,tokens.length-1));
        }
        addNames(tags[fi],names,tokens);
      }
      p.show();
    }
  }
      
  /**
   * Adds sgml style name tags to the specified input buffer and outputs this information to stdout. 
   * @param finders The name finders to be used.
   * @param tags The tag names for the coresponding name finder.
   * @param input The input reader.
   * @throws IOException
   */
  private static void processText(NameFinder[] finders, String[] tags, BufferedReader input) throws IOException {
    String[][] finderTags = new String[finders.length][];
    Map[] prevTokenMaps = createPrevTokenMaps(finders); 
    for (String line = input.readLine(); null != line; line = input.readLine()) {
      if (line.equals("")) {
        clearPrevTokenMaps(prevTokenMaps);
        System.out.println();
        continue;
      }
      Span[] spans = tokenizeToSpans(line);
      String[] tokens = spansToStrings(spans,line);
      for (int fi = 0, fl = finders.length; fi < fl; fi++) {
        finderTags[fi] = finders[fi].find(tokens, prevTokenMaps[fi]);
        //System.err.println("EnglighNameFinder.processText: "+tags[fi] + " " + java.util.Arrays.asList(finderTags[fi]));
      }
      updatePrevTokenMaps(prevTokenMaps,tokens,finderTags);
      for (int ti = 0, tl = tokens.length; ti < tl; ti++) {
        for (int fi = 0, fl = finders.length; fi < fl; fi++) {
          //check for end tags
          if (ti != 0) {
            if ((finderTags[fi][ti].equals(NameFinderME.START) || finderTags[fi][ti].equals(NameFinderME.OTHER)) && (finderTags[fi][ti - 1].equals(NameFinderME.START) || finderTags[fi][ti - 1].equals(NameFinderME.CONTINUE))) {
              System.out.print("</" + tags[fi] + ">");
            }
          }
        }
        if (ti > 0 && spans[ti - 1].getEnd() < spans[ti].getStart()) {
          System.out.print(line.substring(spans[ti - 1].getEnd(), spans[ti].getStart()));
        }
        //check for start tags
        for (int fi = 0, fl = finders.length; fi < fl; fi++) {
          if (finderTags[fi][ti].equals(NameFinderME.START)) {
            System.out.print("<" + tags[fi] + ">");
          }
        }
        System.out.print(tokens[ti]);
      }
      //final end tags
      if (tokens.length != 0) {
        for (int fi = 0, fl = finders.length; fi < fl; fi++) {
          if (finderTags[fi][tokens.length - 1].equals(NameFinderME.START) || finderTags[fi][tokens.length - 1].equals(NameFinderME.CONTINUE)) {
            System.out.print("</" + tags[fi] + ">");
          }
        }
      }
      if (tokens.length != 0) {
        if (spans[tokens.length - 1].getEnd() < line.length()) {
          System.out.print(line.substring(spans[tokens.length - 1].getEnd()));
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.err.println("Usage NameFinder -[parse] model1 model2 ... modelN < sentences");
      System.err.println(" -parse: Use this option to find names on parsed input.  Un-tokenized sentence text is the default.");
      System.exit(1);
    }
    int ai = 0;
    boolean parsedInput = false;
    while (args[ai].startsWith("-") && ai < args.length) {
      if (args[ai].equals("-parse")) {
        parsedInput = true;
      }
      else {
        System.err.println("Ignoring unknown option "+args[ai]);
      }
      ai++;
    }
    NameFinder[] finders = new NameFinder[args.length-ai];
    String[] names = new String[args.length-ai];
    for (int fi=0; ai < args.length; ai++,fi++) {
      String modelName = args[ai];
      finders[fi] = new NameFinder(new SuffixSensitiveGISModelReader(new File(modelName)).getModel());
      int nameStart = modelName.lastIndexOf(System.getProperty("file.separator")) + 1;
      int nameEnd = modelName.indexOf('.', nameStart);
      if (nameEnd == -1) {
        nameEnd = modelName.length();
      }
      names[fi] = modelName.substring(nameStart, nameEnd);
    }
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    if (parsedInput) {
      processParse(finders,names,in);
    }
    else {
      processText(finders,names,in);
    }
  }
}

class CharacterEnum {
  public static final CharacterEnum WHITESPACE = new CharacterEnum("whitespace");
  public static final CharacterEnum ALPHABETIC = new CharacterEnum("alphabetic");
  public static final CharacterEnum NUMERIC = new CharacterEnum("numeric");
  public static final CharacterEnum OTHER = new CharacterEnum("other");

  private String name;

  private CharacterEnum(String name) {
    this.name = name;
  }

  public String toString() {
    return name;
  }
}
