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

import java.io.*;
import java.util.zip.*;

import ch.epfl.bbp.shaded.opennlp.maxent.*;

/**
 * Model writer that saves models in binary format.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.2 $, $Date: 2004/08/29 18:59:35 $
 */
public class BinaryGISModelWriter extends GISModelWriter {
    DataOutputStream output;

   /**
     * Constructor which takes a GISModel and a File and prepares itself to
     * write the model to that file. Detects whether the file is gzipped or not
     * based on whether the suffix contains ".gz".
     *
     * @param model The GISModel which is to be persisted.
     * @param f The File in which the model is to be persisted.
     */
    public BinaryGISModelWriter (GISModel model, File f) throws IOException {

	super(model);
	
	if (f.getName().endsWith(".gz")) {
	    output = new DataOutputStream(
		         new GZIPOutputStream(new FileOutputStream(f)));
	}
	else {
	    output = new DataOutputStream(new FileOutputStream(f));
	}
    }

   /**
     * Constructor which takes a GISModel and a DataOutputStream and prepares
     * itself to write the model to that stream.
     *
     * @param model The GISModel which is to be persisted.
     * @param dos The stream which will be used to persist the model.
     */
    public BinaryGISModelWriter (GISModel model, DataOutputStream dos) {
	super(model);
	output = dos;
    }

    protected void writeUTF (String s) throws java.io.IOException {
	output.writeUTF(s);
    }

    protected void writeInt (int i) throws java.io.IOException {
	output.writeInt(i);
    }
    
    protected void writeDouble (double d) throws java.io.IOException {
	output.writeDouble(d);
    }

    protected void close () throws java.io.IOException {
	output.flush();
	output.close();
    }

}
