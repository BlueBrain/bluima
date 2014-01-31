///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2005 Thomas Morton
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

/**
 * Class which associates a real valueed parameter or expected value with a particular contextual
 * predicate or feature.  This is used to store maxent model parameters as well as model and emperical
 * expected values.  
 * @author Tom Morton
 *
 */
public class Context {

  /** The real valued parameters or expected values for this context. */
  protected double[] parameters;
  /** The outcomes which occur with this context. */
  protected int[] outcomes;
  
  /**
   * Creates a new parametes object with the specifed parameters associated with the specified
   * outcome pattern.
   * @param outcomePattern Array of outcomes for which parameters exists for this context.
   * @param parameters Paramaters for the outcomes specified.
   */
  public Context(int[] outcomePattern, double[] parameters) {
    this.outcomes = outcomePattern;
    this.parameters = parameters;
  }
  
  /**
   * Returns the outcomes for which parameters exists for this context.
   * @return Array of outcomes for which parameters exists for this context.
   */
  public int[] getOutcomes() {
    return outcomes;
  }
  
  /**
   * Returns the paramaters or expected values for the outcomes which occur with this context.
   * @return Array of paramaters for the outcomes of this context.
   */
  public double[] getParameters() {
    return parameters;
  }
}
