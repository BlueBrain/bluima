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

/**
 * A reader for GIS models stored in binary format.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */
public class BinaryGISModelReader extends GISModelReader {
    private DataInputStream input;

    /**
     * Constructor which directly instantiates the DataInputStream containing
     * the model contents.
     *
     * @param dis The DataInputStream containing the model information.
     */
    public BinaryGISModelReader (DataInputStream dis) {
	input = dis;
    }

    /**
     * Constructor which takes a File and creates a reader for it. Detects
     * whether the file is gzipped or not based on whether the suffix contains
     * ".gz" 
     *
     * @param f The File in which the model is stored.
     */
    public BinaryGISModelReader (File f) throws IOException {

	if (f.getName().endsWith(".gz")) {
	    input = new DataInputStream(
		         new GZIPInputStream(new FileInputStream(f)));
	}
	else {
	    input = new DataInputStream(new FileInputStream(f));
	}

    }

    protected int readInt () throws java.io.IOException {
	return input.readInt();
    }

    protected double readDouble () throws java.io.IOException {
	return input.readDouble();
    }

    protected String readUTF () throws java.io.IOException {
	return input.readUTF();
    }

}
