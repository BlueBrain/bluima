/*
 * The WhiteText project
 * 
 * Copyright (c) 2012 University of British Columbia
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package ubic.pubmedgate.resolve.mentionEditors;

import java.util.LinkedList;

public class NucleusOfTheRemoverMentionEditor extends OfTheRemoverMentionEditor {
    public NucleusOfTheRemoverMentionEditor() throws Exception {
        toRemove = new LinkedList<String>();
        toRemove.add( "nucleus of the " );
        toRemove.add( "nucleus of " );
        toRemove.add( "subnucleus of the " );
        toRemove.add( "subnucleus of " );
        toRemove.add( "nuclei of the " );
        toRemove.add( "nuclei of " );
        toRemove.add( "subnuclei of the " );
        toRemove.add( "subnuclei of " );
    }

    public String getName() {
        return "Remover of \"nuclues of the\" phrase";
    }

}
