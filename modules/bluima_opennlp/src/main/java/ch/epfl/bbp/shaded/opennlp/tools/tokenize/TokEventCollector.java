///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2000 Jason Baldridge and Gann Bierner
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

package ch.epfl.bbp.shaded.opennlp.tools.tokenize;


import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;

import ch.epfl.bbp.shaded.opennlp.maxent.ContextGenerator;
import ch.epfl.bbp.shaded.opennlp.maxent.Event;
import ch.epfl.bbp.shaded.opennlp.maxent.EventCollector;
import ch.epfl.bbp.shaded.opennlp.tools.util.ObjectIntPair;

/**
 * Generate event contexts for maxent decisions for tokenization detection.
 * This procedure isn't currrently used for tokenization event creation.
 *
 * @author      Jason Baldridge
 * @version     $Revision: 1.4 $, $Date: 2004/11/29 02:52:11 $
 */

public class TokEventCollector implements EventCollector {
    private static final ContextGenerator cg = new TokContextGenerator();
    private BufferedReader br;
    
    /** 
     * Class constructor.
     */
    public TokEventCollector(Reader data) {
	br = new BufferedReader(data);
    }

    
    /**
     * Builds up the list of features using the Reader as input. For now, this
     * should only be used to create training data.
     */
  public Event[] getEvents() {
    ArrayList elist = new ArrayList();

    try {
      String s = br.readLine();
      while (s != null) {
        String[] spaceToks = s.split(" ");
        for (int tok = 0; tok < spaceToks.length; tok++) {
          StringBuffer sb = new StringBuffer(spaceToks[tok]);
          if (TokenizerME.alphaNumeric.matcher(spaceToks[tok]).matches()) {
            int lastIndex = sb.length() - 1;
            for (int id = 0; id < sb.length(); id++) {
              String[] context = cg.getContext(new ObjectIntPair(sb, id));
              if (id == lastIndex) {
                elist.add(new Event("T", context));
              }
              else {
                elist.add(new Event("F", context));
              }
            }
          }
        }
        s = br.readLine();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

	Event[] events = new Event[elist.size()];
	elist.toArray(events);
        return events;

    }

    public Event[] getEvents(boolean evalMode) {
	return getEvents();
    }
    
}

