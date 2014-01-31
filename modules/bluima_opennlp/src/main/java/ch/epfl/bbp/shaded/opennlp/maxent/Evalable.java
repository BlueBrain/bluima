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
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

import java.io.*;

/**
 * Interface for components which use maximum entropy models and can evaluate
 * the performace of the models using the TrainEval class.
 *
 * @author      Gann Bierner
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */

public interface Evalable {

    /**
     * The outcome that should be considered a negative result.  This is used
     * for computing recall.  In the case of binary decisions, this would be
     * the false one.
     *
     * @return the events that this EventCollector has gathered
     */
    public String getNegativeOutcome();

    /**
     * Returns the EventCollector that is used to collect all relevant
     * information from the data file.  This is used for to test the
     * predictions of the model.  Note that if some of your features are the
     * oucomes of previous events, this method will give you results assuming
     * 100% performance on the previous events.  If you don't like this, use
     * the localEval method.
     * 
     * @param r A reader containing the data for the event collector
     * @return an EventCollector
     */
    public EventCollector getEventCollector(Reader r);
    
    /**
     * If the -l option is selected for evaluation, this method will be
     * called rather than TrainEval's evaluation method.  This is good if
     * your features includes the outcomes of previous events.
     * 
     * @param model the maxent model to evaluate
     * @param r Reader containing the data to process
     * @param e The original Evalable.  Probably not relevant.
     * @param verbose a request to print more specific processing information
     */
    public void localEval(MaxentModel model, Reader r,
			  Evalable e, boolean verbose);
}
