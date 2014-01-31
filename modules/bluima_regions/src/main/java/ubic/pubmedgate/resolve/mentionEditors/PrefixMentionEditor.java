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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class PrefixMentionEditor implements MentionEditor {
    protected static Log log = LogFactory.getLog( PrefixMentionEditor.class );
    Collection<String> prefixes;

    public Set<String> editMention( String mention ) {
        Set<String> result = new HashSet<String>();
        for ( String prefix : prefixes ) {
            if ( mention.startsWith( prefix + " " ) ) {
                String news = mention.substring( prefix.length() + 1 );
                news = news.trim();
                // log.info( mention + "->" + news );
                if ( !news.equals( "nucleus" ) && !news.equals( "region" ) && !news.equals( "regions" ) ) {
                    // special case, don't allow the result to end up as just 'region' or 'nucleus'
                    result.add( news );
                }
            }
        }
        return result;
    }

}
