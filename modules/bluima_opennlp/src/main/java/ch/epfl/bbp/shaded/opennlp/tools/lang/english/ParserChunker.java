package ch.epfl.bbp.shaded.opennlp.tools.lang.english;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.shaded.opennlp.maxent.io.SuffixSensitiveGISModelReader;
import ch.epfl.bbp.shaded.opennlp.tools.chunker.ChunkerME;
import ch.epfl.bbp.shaded.opennlp.tools.parser.ChunkContextGenerator;
import ch.epfl.bbp.shaded.opennlp.tools.parser.ParserME;
import ch.epfl.bbp.shaded.opennlp.tools.util.Sequence;


public class ParserChunker extends ChunkerME implements ch.epfl.bbp.shaded.opennlp.tools.parser.ParserChunker {
  private static final int K = 10;
  private int beamSize;
  private Map continueStartMap;
  
  public ParserChunker(String modelFile) throws IOException {
    this(modelFile,K,K);
  }
  
  public ParserChunker(String modelFile, int beamSize, int cacheSize) throws IOException {
    super(new SuffixSensitiveGISModelReader(new File(modelFile)).getModel(), new ChunkContextGenerator(cacheSize), beamSize);
    continueStartMap = new HashMap(model.getNumOutcomes());
    for (int oi=0,on=model.getNumOutcomes();oi<on;oi++) {
      String outcome = model.getOutcome(oi);
      if (outcome.startsWith(ParserME.CONT)){
        continueStartMap.put(outcome,ParserME.START+outcome.substring(ParserME.CONT.length()));
      }
    }
    this.beamSize = beamSize;
  }

  public Sequence[] topKSequences(List sentence, List tags) {
    return beam.bestSequences(beamSize, sentence.toArray(), new Object[] { tags });
  }

  public Sequence[] topKSequences(String[] sentence, String[] tags, double minSequenceScore) {
    return beam.bestSequences(beamSize, sentence, new Object[] { tags },minSequenceScore);
  }

  protected boolean validOutcome(String outcome, String[] tagList) {
    if (continueStartMap.containsKey(outcome)) {
      int lti = tagList.length - 1;
      if (lti == -1) {
        return (false);
      }
      else {
        String lastTag = tagList[lti];
        if (lastTag.equals(outcome)) {
           return true;
        }
        if (lastTag.equals(continueStartMap.get(outcome))) {
          return true;
        }
        if (lastTag.equals(ParserME.OTHER)) {
          return (false);
        }
        return false;
      }
    }
    return (true);
  }
  
  protected boolean validOutcome(String outcome, Sequence sequence) {
    if (continueStartMap.containsKey(outcome)) {
      List tagList = sequence.getOutcomes();
      int lti = tagList.size() - 1;
      if (lti == -1) {
        return (false);
      }
      else {
        String lastTag = (String) tagList.get(lti);
        if (lastTag.equals(outcome)) {
           return true;
        }
        if (lastTag.equals(continueStartMap.get(outcome))) {
          return true;
        }
        if (lastTag.equals(ParserME.OTHER)) {
          return (false);
        }
        return false;
      }
    }
    return (true);
  }
}