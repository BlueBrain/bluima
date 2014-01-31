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
 * A reader for GIS models stored in plain text format.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */
public class PlainTextGISModelReader extends GISModelReader {
    private BufferedReader input;

    /**
     * Constructor which directly instantiates the BufferedReader containing
     * the model contents.
     *
     * @param br The BufferedReader containing the model information.
     */
    public PlainTextGISModelReader (BufferedReader br) {
	input = br;
    }

    /**
     * Constructor which takes a File and creates a reader for it. Detects
     * whether the file is gzipped or not based on whether the suffix contains
     * ".gz".
     *
     * @param f The File in which the model is stored.
     */
    public PlainTextGISModelReader (File f) throws IOException {

	if (f.getName().endsWith(".gz")) {
	    input = new BufferedReader(new InputStreamReader(
  	                 new GZIPInputStream(new FileInputStream(f))));
	}
	else {
	    input = new BufferedReader(new FileReader(f));
	}

    }

    protected int readInt () throws IOException {
	return Integer.parseInt(input.readLine());
    }

    protected double readDouble () throws IOException {
	return Double.parseDouble(input.readLine());
    }

    protected String readUTF () throws IOException {
	return input.readLine();
    }

}
