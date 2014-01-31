///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Artifactus Ltd.
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
package ch.epfl.bbp.shaded.opennlp.maxent;

/**
 * A simple interface that represents a domain to which a particular maxent
 * model is primarily applicable. For instance, one might have a
 * part-of-speech tagger trained on financial text and another based on
 * children's stories.  This interface is used by the DomainToModelMap class
 * to allow an application to grab the models relevant for the different
 * domains.
 *
 * @author      Jason Baldridge
 * @version $Revision: 1.1.1.1 $, $Date: 2001/10/23 14:06:53 $
 */
public interface ModelDomain {

    /**
     * Get the name of this domain.
     *
     * @return The name of this domain.
     */
    public String getName ();

}
