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

public class RegionSuffixRemover implements MentionEditor {
    public Set<String> editMention( String mention ) {
        Set<String> result = new HashSet<String>();
        Set<String> suffixes = new HashSet<String>();
        suffixes.add( " region" );
        suffixes.add( " regions" );
        for ( String suffix : suffixes ) {
            if ( mention.endsWith( suffix ) ) {
                String news = mention.substring( 0, mention.length() - suffix.length() );
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

    public String getName() {
        return "Region[s] suffix remover";
    }
}
