///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Jason Baldridge and Gann Bierner
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
package ch.epfl.bbp.shaded.opennlp.maxent;

/**
 * Interface for maximum entropy models.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.4 $, $Date: 2003/12/09 23:13:53 $
 **/
public interface MaxentModel {

  /**
   * Evaluates a context.
   *
   * @param context A list of String names of the contextual predicates
   *                which are to be evaluated together.
   * @return an array of the probabilities for each of the different
   *         outcomes, all of which sum to 1.
   *
   **/
  public double[] eval(String[] context);
  
  /**
     * Evaluates a context.
     *
     * @param context A list of String names of the contextual predicates
     *                which are to be evaluated together.
     * @param probs An array which is populated with the probabilities for each of the different
     *         outcomes, all of which sum to 1.
     * @return an array of the probabilities for each of the different
     *         outcomes, all of which sum to 1.  The <code>probs</code> is returned if it is appropiately sized. 
     **/
  public double[] eval(String[] context, double probs[]);

  /**
   * Simple function to return the outcome associated with the index
   * containing the highest probability in the double[].
   *
   * @param outcomes A <code>double[]</code> as returned by the
   *            <code>eval(String[] context)</code>
   *            method.
   * @return the String name of the best outcome
   **/
  public String getBestOutcome(double[] outcomes);

  /**
   * Return a string matching all the outcome names with all the
   * probabilities produced by the <code>eval(String[]
   * context)</code> method.
   *
   * @param outcomes A <code>double[]</code> as returned by the
   *            <code>eval(String[] context)</code>
   *            method.
   * @return    String containing outcome names paired with the normalized
   *            probability (contained in the <code>double[] ocs</code>)
   *            for each one.
   **/
  public String getAllOutcomes(double[] outcomes);

  /**
   * Gets the String name of the outcome associated with the index
   * i.
   *
   * @param i the index for which the name of the associated outcome is
   *          desired.
   * @return the String name of the outcome
   **/
  public String getOutcome(int i);

  /**
   * Gets the index associated with the String name of the given
   * outcome.
   *
   * @param outcome the String name of the outcome for which the
   *          index is desired
   * @return the index if the given outcome label exists for this
   * model, -1 if it does not.
   **/
  public int getIndex(String outcome);

  /**
   * Returns the data structures relevant to storing the model.
   **/
  public Object[] getDataStructures();

  /** Returns the number of outcomes for this model.
   *  @return The number of outcomes.
   **/
  public int getNumOutcomes();

}
