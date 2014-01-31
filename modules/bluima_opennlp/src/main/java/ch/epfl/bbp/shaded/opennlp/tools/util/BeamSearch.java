///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2003 Gann Bierner and Thomas Morton
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
package ch.epfl.bbp.shaded.opennlp.tools.util;

import java.util.Arrays;
import java.util.List;

import ch.epfl.bbp.shaded.opennlp.maxent.MaxentModel;


/** Performs k-best search over sequence.  This is besed on the description in
  * Ratnaparkhi (1998), PhD diss, Univ. of Pennsylvania. 
 */
public class BeamSearch {

  protected MaxentModel model;
  protected BeamSearchContextGenerator cg;
  protected int size;
  private static final Object[] EMPTY_ADDITIONAL_CONTEXT = new Object[0];
  private double[] probs;
  private Cache contextsCache;
  private static final int zeroLog = -100000;

  /** Creates new search object. 
   * @param size The size of the beam (k).
   * @param cg the context generator for the model. 
   * @param model the model for assigning probabilities to the sequence outcomes.
   */
  public BeamSearch(int size, BeamSearchContextGenerator cg, MaxentModel model) {
    this(size,cg,model,0);
  }
  
  public BeamSearch(int size, BeamSearchContextGenerator cg, MaxentModel model, int cacheSize) {
    this.size = size;
    this.cg = cg;
    this.model = model;
    this.probs = new double[model.getNumOutcomes()];
    if (cacheSize > 0) {
      contextsCache = new Cache(cacheSize);
    }
  }
  
  public Sequence[] bestSequences(int numSequences, Object[] sequence, Object[] additionalContext) {
    return bestSequences(numSequences, sequence, additionalContext, zeroLog);
  }

  /** Returns the best sequence of outcomes based on model for this object.
   * @param numSequences The maximum number of sequences to be returned.
   * @param sequence The input sequence.
   * @param additionalContext An Object[] of additional context.  This is passed to the context generator blindly with the assumption that the context are appropiate.
   * @param minSequenceScore A lower bound on the score of a returned sequence. 
   * @return An array of the top ranked sequences of outcomes.
   */
  public Sequence[] bestSequences(int numSequences, Object[] sequence, Object[] additionalContext, double minSequenceScore) {
    int n = sequence.length;
    Heap prev = new ListHeap(size);
    Heap next = new ListHeap(size);
    Heap tmp;
    prev.add(new Sequence());
    if (additionalContext == null) {
      additionalContext = EMPTY_ADDITIONAL_CONTEXT;
    }
    for (int i = 0; i < n; i++) {
      int sz = Math.min(size, prev.size());
      int sc = 0;
      for (; prev.size() > 0 && sc < sz; sc++) {
        Sequence top = (Sequence) prev.extract();
        List tmpOutcomes = top.getOutcomes();
        String[] outcomes = (String[]) tmpOutcomes.toArray(new String[tmpOutcomes.size()]);
        String[] contexts = cg.getContext(i, sequence, outcomes, additionalContext);
        double[] scores;
        if (contextsCache != null) {
          scores = (double[]) contextsCache.get(contexts);
          if (scores == null) {
            scores = model.eval(contexts, probs);
            contextsCache.put(contexts,scores);
          }
        }
        else {
          scores = model.eval(contexts, probs);
        }
        double[] temp_scores = new double[scores.length];
        for (int c = 0; c < scores.length; c++) {
          temp_scores[c] = scores[c];
        }
        Arrays.sort(temp_scores);
        double min = temp_scores[Math.max(0,scores.length-size)];
        for (int p = 0; p < scores.length; p++) {
          if (scores[p] < min)
            continue; //only advance first "size" outcomes
          String out = model.getOutcome(p);
          if (validSequence(i, sequence, outcomes, out)) {
            Sequence ns = new Sequence(top, out, scores[p]);
            if (ns.getScore() > minSequenceScore) {
              next.add(ns);
            }
          }
        }
        if (next.size() == 0) {//if no advanced sequences, advance all valid
          for (int p = 0; p < scores.length; p++) {
            String out = model.getOutcome(p);
            if (validSequence(i, sequence, outcomes, out)) {
              Sequence ns = new Sequence(top, out, scores[p]);
              if (ns.getScore() > minSequenceScore) {
                next.add(ns);
              }
            }
          }
        }
      }
      //    make prev = next; and re-init next (we reuse existing prev set once we clear it)
      prev.clear();
      tmp = prev;
      prev = next;
      next = tmp;
    }
    int numSeq = Math.min(numSequences, prev.size());
    Sequence[] topSequences = new Sequence[numSeq];
    int seqIndex = 0;
    for (; seqIndex < numSeq; seqIndex++) {
      topSequences[seqIndex] = (Sequence) prev.extract();
    }
    return topSequences;
  }

  /** Returns the best sequence of outcomes based on model for this object.
   * @param sequence The input sequence.
   * @param additionalContext An Object[] of additional context.  This is passed to the context generator blindly with the assumption that the context are appropiate.
   * @return The top ranked sequence of outcomes.
   */
  public Sequence bestSequence(List sequence, Object[] additionalContext) {
    return bestSequences(1, sequence.toArray(), additionalContext)[0];
  }
  
  /** Returns the best sequence of outcomes based on model for this object.
    * @param sequence The input sequence.
    * @param additionalContext An Object[] of additional context.  This is passed to the context generator blindly with the assumption that the context are appropiate.
    * @return The top ranked sequence of outcomes.
    */
  public Sequence bestSequence(Object[] sequence, Object[] additionalContext) {
    return bestSequences(1, sequence, additionalContext,zeroLog)[0];
  }

  /** Determines wheter a particular continuation of a sequence is valid.  
   * This is used to restrict invalid sequences such as thoses used in start/continure tag-based chunking 
   * or could be used to implement tag dictionary restrictions.
   * @param i The index in the input sequence for which the new outcome is being proposed.
   * @param inputSequence The input sequnce.
   * @param outcomesSequence The outcomes so far in this sequence.
   * @param outcome The next proposed outcome for the outcomes sequence.
   * @return true is the sequence would still be valid with the new outcome, false otherwise.
   */
  protected boolean validSequence(int i, List inputSequence, Sequence outcomesSequence, String outcome) {
    return true;
  }

  /** Determines wheter a particular continuation of a sequence is valid.  
     * This is used to restrict invalid sequences such as thoses used in start/continure tag-based chunking 
     * or could be used to implement tag dictionary restrictions.
     * @param i The index in the input sequence for which the new outcome is being proposed.
     * @param inputSequence The input sequnce.
     * @param outcomesSequence The outcomes so far in this sequence.
     * @param outcome The next proposed outcome for the outcomes sequence.
     * @return true is the sequence would still be valid with the new outcome, false otherwise.
     */
  protected boolean validSequence(int i, Object[] inputSequence, String[] outcomesSequence, String outcome) {
    return true;
  }

}