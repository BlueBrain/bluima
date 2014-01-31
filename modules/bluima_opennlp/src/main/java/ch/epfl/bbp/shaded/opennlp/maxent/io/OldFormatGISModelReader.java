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

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import ch.epfl.bbp.shaded.opennlp.maxent.Context;
import ch.epfl.bbp.shaded.opennlp.maxent.TIntParamHashMap;


/**
 * A reader for GIS models stored in the format used in v1.0 of Maxent. It
 * extends the PlainTextGISModelReader to read in the info and then overrides
 * the getParameters method so that it can appropriately read the binary file
 * which stores the parameters.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.4 $, $Date: 2005/10/06 11:04:16 $
 */
public class OldFormatGISModelReader extends PlainTextGISModelReader {
    DataInputStream paramsInput;

    /**
     * Constructor which takes the name of the model without any suffixes,
     * such as ".mei.gz" or ".mep.gz".
     */
    public OldFormatGISModelReader(String modelname)
	throws IOException {
	super(new File(modelname+".mei.gz"));
	paramsInput = new DataInputStream(new GZIPInputStream(
		          new FileInputStream(modelname+".mep.gz")));
    }

    /**
     * Reads the parameters from a file and populates an array of context objects.
     * @param outcomePatterns The outcomes patterns for the model.  The first index refers to which 
     * outcome pattern (a set of outcomes that occurs with a context) is being specified.  The
     * second index specifies the number of contexts which use this pattern at index 0, and the
     * index of each outcomes which make up this pattern in indicies 1-n.  
     * @return An array of context objects.
     * @throws java.io.IOException when the model file does not match the outcome patterns or can not be read.
     */
    protected Context[] getParameters (int[][] outcomePatterns) throws java.io.IOException {
      Context[] params = new Context[NUM_PREDS];
      int pid=0;
      for (int i=0; i<outcomePatterns.length; i++) {
        //construct outcome pattern
        int[] outcomePattern = new int[outcomePatterns[i].length-1];
        for (int k=1; k<outcomePatterns[i].length; k++) {
          outcomePattern[k-1] = outcomePatterns[i][k];
        }
        //populate parameters for each context which uses this outcome pattern. 
        for (int j=0; j<outcomePatterns[i][0]; j++) {
          double[] contextParameters = new double[outcomePatterns[i].length-1];
          for (int k=1; k<outcomePatterns[i].length; k++) {
            contextParameters[k-1] = readDouble();
          }
          params[pid] = new Context(outcomePattern,contextParameters);
          pid++;
        }
      }
      return params;
    }

    /**
     * Convert a model created with Maxent 1.0 to a format used with
     * Maxent 1.2.
     *
     * <p>Usage: java opennlp.maxent.io.OldFormatGISModelReader model_name_prefix (new_model_name)");
     *
     * <p>If the new_model_name is left unspecified, the new model will be saved
     * in gzipped, binary format as "<model_name_prefix>.bin.gz".
     */
    public static void main (String[] args) throws IOException {
	if (args.length < 1) {
	    System.out.println("Usage: java opennlp.maxent.io.OldFormatGISModelReader model_name_prefix (new_model_name)");
	    System.exit(0);
	}

	int nameIndex = 0;

	String infilePrefix = args[nameIndex];
	String outfile;

	if (args.length > nameIndex)
	    outfile = args[nameIndex+1];
	else
	    outfile = infilePrefix + ".bin.gz";


	GISModelReader reader = new OldFormatGISModelReader(infilePrefix);

	new SuffixSensitiveGISModelWriter(reader.getModel(),
					  new File(outfile)).persist();
				   
    }
    
}
