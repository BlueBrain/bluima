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
package ch.epfl.bbp.shaded.opennlp.maxent;

/** Object which compresses events in memory and performs feature selection.
 */
public interface DataIndexer {
  /**
   * Returns the array of predicates seen in each event. 
   * @return a 2-D array whose first dimenstion is the event index and array this refers to contains
   * the contexts for that event. 
   */
  public int[][] getContexts();
  
  /**
   * Returns an array indicating the number of times a particular event was seen.
   * @return an array indexed by the event index indicating the number of times a particular event was seen.
   */
  public int[] getNumTimesEventsSeen();
  
  /**
   * Returns an array indicating the number of outcomes found with a particular event.
   * @return an array indexed by event index  indicating the number of outcomes found with a particular event.
   */
  public int[] getOutcomeList();
  
  /**
   * Returns an array of predicate/context names.
   * @return an array of predicate/context names indexed by context index.  These indices are the
   * value of the array returned by <code>getContexts</code>.
   */
  public String[] getPredLabels();
  
  /**
    * Returns an array of outcome names.
    * @return an array of outcome names indexed by outcome index.
    */
  public String[] getOutcomeLabels(); 
}