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

import java.util.HashSet;
import java.util.Set;

public class TwiceDirectionRemovingMentionEditor extends DirectionRemoverMentionEditor {
    public TwiceDirectionRemovingMentionEditor() throws Exception {
        super();
    }

    public Set<String> editMention( String mention ) {
        Set<String> results = super.editMention( mention );
        Set<String> secondResult = new HashSet<String>();
        for ( String result : results ) {
            // do it again
            secondResult.addAll( super.editMention( result ) );
        }
        return secondResult;
    }
    
    public String getName() {
        return "Double direction remover";
    }

}
