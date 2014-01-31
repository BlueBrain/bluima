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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BracketRemoverMentionEditor implements MentionEditor {
    protected static Log log = LogFactory.getLog( BracketRemoverMentionEditor.class );

    public Set<String> editMention( String mention ) {
        String olds = mention;
        Set<String> result = new HashSet<String>();
        String regex = "\\s?\\((.*?)\\)\\s?";
        Pattern p = Pattern.compile( regex, Pattern.DOTALL );
        Matcher m = p.matcher( mention );

        if ( m.find() ) {
            mention = m.replaceAll( " " );
            // log.info( olds + " -> " + mention );
            result.add( mention );
        }
        return result;
    }
    
    public String getName() {
        return "Bracketed text remover";
    }


}
