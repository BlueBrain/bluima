///////////////////////////////////////////////////////////////////////////////
//Copyright (C) 2005 Julien Nioche
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
package ch.epfl.bbp.shaded.opennlp.maxent.io;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectGISModelReader extends GISModelReader {

   protected ObjectInputStream input;
  
  /**
   * Constructor which directly instantiates the ObjectInputStream containing
   * the model contents.
   *
   * @param dis The DataInputStream containing the model information.
   */
  
  public ObjectGISModelReader(ObjectInputStream dis) {
    super();
    input = dis;
  }

  protected int readInt() throws IOException {
    return input.readInt();
  }

  protected double readDouble() throws IOException {
    return input.readDouble();
  }

  protected String readUTF() throws IOException {
    return input.readUTF();
  }

}
