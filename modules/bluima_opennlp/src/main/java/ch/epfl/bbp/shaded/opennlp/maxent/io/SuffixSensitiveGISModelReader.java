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
 * A reader for GIS models which inspects the filename and invokes the
 * appropriate GISModelReader depending on the filename's suffixes.
 *
 * <p>The following assumption are made about suffixes:
 *    <li>.gz  --> the file is gzipped (must be the last suffix)
 *    <li>.txt --> the file is plain text
 *    <li>.bin --> the file is binary
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.2 $, $Date: 2004/05/10 03:11:54 $
 */
public class SuffixSensitiveGISModelReader extends GISModelReader {
    private final GISModelReader suffixAppropriateReader;

    /**
     * Constructor which takes a File and invokes the GISModelReader
     * appropriate for the suffix.
     *
     * @param f The File in which the model is stored.
     */
    public SuffixSensitiveGISModelReader (File f) throws IOException {
	InputStream input;
	String filename = f.getName();
	
	// handle the zipped/not zipped distinction
	if (filename.endsWith(".gz")) {
	    input = new GZIPInputStream(new FileInputStream(f));
	    filename = filename.substring(0,filename.length()-3);
	}
	else {
	    input = new FileInputStream(f);
	}

	// handle the different formats
	if (filename.endsWith(".bin")) {
	    suffixAppropriateReader =
		new BinaryGISModelReader(new DataInputStream(input));
	}
	// add more else ifs here to add further Reader types, e.g.
	// else if (filename.endsWith(".xml"))
	//     suffixAppropriateReader = new XmlGISModelReader(input);
	// of course, a BufferedReader may not be what is wanted here,
	// so you might have to do a bit more to get
	// SuffixSensitiveGISModelReader to work for xml or other formats.
	// However, the default should be plain text (.txt).
	else {  // filename ends with ".txt"
	    suffixAppropriateReader =
		new PlainTextGISModelReader(
		    new BufferedReader(new InputStreamReader(input)));
	}
	
    }

    // activate this if adding another type of reader which can't read model
    // information in the way that the default getModel() method in
    // GISModelReader does.
    //public GISModel getModel () throws java.io.IOException {
    //    return suffixAppropriateReader.getModel();
    //}
    

    protected int readInt () throws IOException {
	return suffixAppropriateReader.readInt();
    }

    protected double readDouble () throws IOException {
	return suffixAppropriateReader.readDouble();
    }

    protected String readUTF () throws IOException {
	return suffixAppropriateReader.readUTF();
    }

    /**
     * To convert between different formats of the new style.
     * 
     * <p>java opennlp.maxent.io.SuffixSensitiveGISModelReader old_model_name new_model_name
     * 
     * <p>For example, to convert a model called "model.bin.gz" (which is thus
     * saved in gzipped binary format) to one in (unzipped) text format:
     * 
     * <p>java opennlp.maxent.io.SuffixSensitiveGISModelReader model.bin.gz model.txt
     * 
     * <p>This particular example would of course be useful when you generally
     * want to create models which take up less space (.bin.gz), but want to
     * be able to inspect a few of them as plain text files.
     */
    public static void main(String[] args) throws IOException {
	ch.epfl.bbp.shaded.opennlp.maxent.GISModel m =
	    new SuffixSensitiveGISModelReader(new File(args[0])).getModel();
	new SuffixSensitiveGISModelWriter(m, new File(args[1])).persist();
    }

}
