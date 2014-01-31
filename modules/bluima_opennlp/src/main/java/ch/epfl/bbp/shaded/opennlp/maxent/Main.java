///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Jason Baldridge
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

/**
 * Main file for opennlp.maxent.  Right now just tells the user that
 * the executable jar doesn't actually execute anything but the
 * message telling the user that the jar doesn't execute anything
 * but...
 *
 * @author      Jason Baldridge
 * @version $Revision: 1.1 $, $Date: 2001/11/21 10:15:55 $ 
*/
public class Main {

    public static void main (String[] args) {
	System.out.println(
       "\n********************************************************************\n"
     + "The \"executable\" jar of OpenNLP Maxent does not currently execute\n"
     + "anything except this message.  It exists only so that there is a jar\n"
     + "of the package which contains all of the other jar dependencies\n"
     + "needed by Maxent so that users can download it and be able to use\n"
     + "it to build maxent applications without hunting down the other jars.\n"
     + "********************************************************************\n"
        );
    }
    
}
