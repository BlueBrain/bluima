///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2000 Jason Baldridge and Gann Bierner
// 
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////

package ch.epfl.bbp.shaded.opennlp.maxent.io;

import java.io.*;
import java.util.zip.*;

/**
 * A program to convert from java binary doubles to ascii.  With the new
 * conversion utililities provided in Maxent 1.2 this probably won't be
 * necessary, but it doesn't do any harm to keep it around for now.
 *
 * @author      Jason Baldridge and Gann Bierner
 * @version     $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */

public class BinToAscii {

	public static void main(String[] args) throws IOException {
		PrintWriter out =
			new PrintWriter(new OutputStreamWriter(
				new GZIPOutputStream(
					new FileOutputStream(args[1]))));
		DataInputStream in =
			new DataInputStream(new GZIPInputStream(
				new FileInputStream(args[0])));

		double d;
		try {
			while(true)
				out.println(in.readDouble());
		} catch (Exception E) {}
		out.close();
		in.close();
	}

}
