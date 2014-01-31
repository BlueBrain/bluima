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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OfTheRemoverMentionEditor implements MentionEditor {
    protected static Log log = LogFactory
            .getLog(OfTheRemoverMentionEditor.class);

    List<String> toRemove;

    public OfTheRemoverMentionEditor() throws Exception {
        toRemove = new LinkedList<String>();

        for (String word : linesFrom(LEXICON_HOME + "PartsOfTheWords.txt")) {
            // look for "of the" before "of"
            toRemove.add(word + " of the ");
            toRemove.add(word + " of ");
        }
    }

    public Set<String> editMention(String mention) {
        Set<String> result = new HashSet<String>();
        // include the original
        // result.add( mention );

        for (String remove : toRemove) {
            if (mention.contains(remove)) {
                mention = mention.replace(remove, "");
                result.add(mention);
            }
        }

        if (mention.contains("of the ")) {
            mention = mention.replace("of the ", "");
            result.add(mention);
        }
        return result;
    }

    public String getName() {
        return "Remover of \"of the\" type phrases";
    }

    public static void main(String args[]) throws Exception {
        OfTheRemoverMentionEditor f = new OfTheRemoverMentionEditor();
        log.info(f.editMention("middle part of the tectum"));
    }
}
