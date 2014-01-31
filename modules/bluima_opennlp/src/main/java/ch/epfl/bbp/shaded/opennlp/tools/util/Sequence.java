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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Represents a weighted sequence of outcomes. */
public class Sequence implements Comparable {
  private double score;
  private List outcomes;
  private List probs;
  private static final Double ONE = new Double(1.0);

  /** Creates a new sequence of outcomes. */
  public Sequence() {
    outcomes = new ArrayList(1);
    probs = new ArrayList(1);
    score = 0;
  }

  public Sequence(Sequence s) {
    outcomes = new ArrayList(s.outcomes.size()+1);
    outcomes.addAll(s.outcomes);
    probs = new ArrayList(s.probs.size()+1);
    probs.addAll(s.probs);
    score = s.score;
  }
  
  public Sequence(Sequence s,String outcome, double p) {
      outcomes = new ArrayList(s.outcomes.size()+1);
      outcomes.addAll(s.outcomes);
      outcomes.add(outcome);
      probs = new ArrayList(s.probs.size()+1);
      probs.addAll(s.probs);
      probs.add(new Double(p));
      score = s.score+Math.log(p);
    }

  public Sequence(List outcomes) {
    this.outcomes = outcomes;
    this.probs = Collections.nCopies(outcomes.size(),ONE);
  }

  public int compareTo(Object o) {
    Sequence s = (Sequence) o;
    if (score < s.score)
      return 1;
    if (score > s.score)
      return -1;
    return 0;
  }

  /** Adds an outcome and probability to this sequence. 
   * @param outcome the outcome to be added.
   * @param p the probability associated with this outcome.
   */
  public void add(String outcome, double p) {
    outcomes.add(outcome);
    probs.add(new Double(p));
    score += Math.log(p);
  }

  /** Returns a list of outcomes for this sequence.
   * @return a list of outcomes.
   */
  public List getOutcomes() {
    return (outcomes);
  }

  /** Returns an array of probabilities associated with the outcomes of this sequence.
   * @return an array of probabilities.
   */
  public double[] getProbs() {
    double[] ps = new double[probs.size()];
    getProbs(ps);
    return (ps);
  }
  
  /**
   * Returns the score of this sequence. 
   * @return The score of this sequence.
   */
  public double getScore() {
    return score;
  }

  /** Populates  an array with the probabilities associated with the outcomes of this sequece.
   * @param ps a pre-allocated array to use to hold the values of the probabilities of the outcomes for this sequence.
   */
  public void getProbs(double[] ps) {
    for (int pi=0,pl=probs.size();pi<pl;pi++) {
      ps[pi] = ((Double) probs.get(pi)).doubleValue();
    }
  }

  public String toString() {
    return score + " "+outcomes;
  }
}