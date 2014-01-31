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
package ch.epfl.bbp.shaded.opennlp.maxent.io;

import gnu.trove.*;
import java.io.*;
import java.util.*;

import ch.epfl.bbp.shaded.opennlp.maxent.*;

/**
 * Abstract parent class for GISModel writers.  It provides the persist method
 * which takes care of the structure of a stored document, and requires an
 * extending class to define precisely how the data should be stored.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.6 $, $Date: 2005/10/06 11:04:16 $
 */
public abstract class GISModelWriter {
    protected Context[] PARAMS;
    protected String[] OUTCOME_LABELS;
    protected int CORRECTION_CONSTANT;
    protected double CORRECTION_PARAM;
    protected String[] PRED_LABELS;

    public GISModelWriter (GISModel model) {
      
      Object[] data = model.getDataStructures();
      
      PARAMS = (Context[]) data[0];
      TObjectIntHashMap pmap = (TObjectIntHashMap)data[1];
      OUTCOME_LABELS = (String[])data[2];
      CORRECTION_CONSTANT = ((Integer)data[3]).intValue();
      CORRECTION_PARAM = ((Double)data[4]).doubleValue();
      
      PRED_LABELS = new String[pmap.size()];
      pmap.forEachEntry(new TObjectIntProcedure() {
        public boolean execute (Object pred, int index) {
          PRED_LABELS[index] = (String)pred;
          return true;
        }
      });
    }

    protected abstract void writeUTF (String s) throws java.io.IOException;
    protected abstract void writeInt (int i) throws java.io.IOException;
    protected abstract void writeDouble (double d) throws java.io.IOException;
    protected abstract void close () throws java.io.IOException;

    /**
     * Writes the model to disk, using the <code>writeX()</code> methods
     * provided by extending classes.
     *
     * <p>If you wish to create a GISModelWriter which uses a different
     * structure, it will be necessary to override the persist method in
     * addition to implementing the <code>writeX()</code> methods.
     */
    public void persist() throws IOException {

	// the type of model (GIS)
	writeUTF("GIS");

	// the value of the correction constant
	writeInt(CORRECTION_CONSTANT);

	// the value of the correction constant	
	writeDouble(CORRECTION_PARAM);

	// the mapping from outcomes to their integer indexes
	writeInt(OUTCOME_LABELS.length);

	for (int i=0; i<OUTCOME_LABELS.length; i++)
	    writeUTF(OUTCOME_LABELS[i]); 

	// the mapping from predicates to the outcomes they contributed to.
	// The sorting is done so that we actually can write this out more
	// compactly than as the entire list.
	ComparablePredicate[] sorted = sortValues();
	List compressed = compressOutcomes(sorted);
	
	writeInt(compressed.size());

	for (int i=0; i<compressed.size(); i++) {
	    List a = (List)compressed.get(i);
	    writeUTF(a.size()
		     + ((ComparablePredicate)a.get(0)).toString());
	}	
	
	// the mapping from predicate names to their integer indexes
	writeInt(PARAMS.length);
	
	String pred;
	for (int i=0; i<sorted.length; i++)
	    writeUTF(sorted[i].name); 

	// write out the parameters
	for (int i=0; i<sorted.length; i++)
	    for (int j=0; j<sorted[i].params.length; j++)
		writeDouble(sorted[i].params[j]);

	close();
    }


    protected ComparablePredicate[] sortValues () {
	
        ComparablePredicate[] sortPreds =
            new ComparablePredicate[PARAMS.length];

        int numParams = 0;	
        for (int pid=0; pid<PARAMS.length; pid++) {
            int[] predkeys = PARAMS[pid].getOutcomes();
            //Arrays.sort(predkeys);
            int numActive = predkeys.length;
            int[] activeOutcomes = predkeys;
            double[] activeParams = PARAMS[pid].getParameters();

            numParams += numActive;
            /*
            double[] activeParams = new double[numActive];
            
            int id = 0;	   
            for (int i=0; i < predkeys.length; i++) {
                int oid = predkeys[i];
                activeOutcomes[id] = oid;
                activeParams[id] = PARAMS[pid].getParams(oid);
                id++;
            }
            */
            sortPreds[pid] = new ComparablePredicate(PRED_LABELS[pid],
                                                     activeOutcomes,
                                                     activeParams);
        }
		
        Arrays.sort(sortPreds);
        return sortPreds;
    }
    
    protected List compressOutcomes (ComparablePredicate[] sorted) {

	ComparablePredicate cp = sorted[0];
	List outcomePatterns = new ArrayList();
	List newGroup = new ArrayList();
	for (int i=0; i<sorted.length; i++) {
	    if (cp.compareTo(sorted[i]) == 0) {
		newGroup.add(sorted[i]);
	    } else {	    
		cp = sorted[i];
		outcomePatterns.add(newGroup);
		newGroup = new ArrayList();
		newGroup.add(sorted[i]);
	    }	    
	}
	outcomePatterns.add(newGroup);

	return outcomePatterns;
    }

    
}
