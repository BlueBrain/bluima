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

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.BrainRegionsHelper.LEXICON_HOME;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirectionSplittingMentionEditor implements MentionEditor {
    protected static Log log = LogFactory
            .getLog(DirectionSplittingMentionEditor.class);

    Collection<String> directions;
    // delims from Ngrampipefactory
    public final static String delims = "~`!@#$%^&*()_-+={[}]|\\:;\"',<.>?/ \t\n\r";

    public DirectionSplittingMentionEditor() throws Exception {
        // load in directions
        directions = linesFrom(LEXICON_HOME + "directions.txt");

        directions.addAll(linesFrom(LEXICON_HOME + "extendedDirections.txt"));

        log.info("Loaded directions, size " + directions.size());
    }

    // split the mention using the directions
    public Set<String> editMention(String mention) {
        Set<String> result = new HashSet<String>();

        // tokenize
        StringTokenizer tokens = new StringTokenizer(mention, delims, false);
        List<String> tokenList = new LinkedList<String>();
        if (tokens.countTokens() < 3)
            return result;
        while (tokens.hasMoreTokens()) {
            tokenList.add(tokens.nextToken());
        }
        String first = tokenList.get(0);
        String second = tokenList.get(1);
        String third = tokenList.get(2);
        if (second.equals("and") || second.equals("or") || second.equals("to")) {
            if (directions.contains(first) && directions.contains(third)) {
                // log.info( first + " " + second + " " + third + " Full:" + s
                // );
                int spot = mention.indexOf(third) + third.length();
                result.add(first + mention.substring(spot));
                result.add(third + mention.substring(spot));
            }
        }
        return result;
    }

    public String getName() {
        return "Direction splitting editor";
    }

}
