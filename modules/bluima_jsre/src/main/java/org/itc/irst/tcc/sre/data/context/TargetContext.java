/*
 * Copyright 2005 FBK-irst (http://www.fbk.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.itc.irst.tcc.sre.data.context;

import org.itc.irst.tcc.sre.data.Sentence;
import org.itc.irst.tcc.sre.data.Word;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class TargetContext extends LocalContext {

    static Logger logger = LoggerFactory.getLogger(TargetContext.class
            .getName());

    /** Singleton */
    private static TargetContext context;

    private TargetContext() {
        context = this;
    }

    /**
     * Returns <code>TargetContext</code> object; only one
     * <code>TargetContext</code> instance can exist.
     * 
     * @return <code>TargetContext</code> object
     */
    public static synchronized TargetContext getInstance() {
        if (context == null) {
            context = new TargetContext();
        }
        return context;
    }

    public Sentence filter(Sentence sent) {
        // logger.debug("\"" + Array.toString(sent) + "\"");
        int start = 0, end = sent.length() - 1;
        boolean b = false;
        for (int i = 0; i < sent.length(); i++) {
            if (sent.wordAt(i).getRole().equals(Word.TARGET_LABEL)) {
                start = i - getSize();
                b = true;
                break;
            }
        }

        if (!b)
            return null;

        end = start + 2 * getSize() + 1;

        if (start < 0)
            start = 0;

        if (end > sent.length())
            end = sent.length();

        return sent.fragment(start, end);
    }

    public int index(Sentence sent) {
        // logger.debug("\"" + Array.toString(sent) + "\"");
        for (int i = 0; i < sent.length(); i++) {
            if (sent.wordAt(i).getRole().equals(Word.TARGET_LABEL)) {
                return i;
            }
        }
        return -1;
    }

    public Word word(Sentence sent) {
        // logger.debug("\"" + Array.toString(sent) + "\"");
        for (int i = 0; i < sent.length(); i++) {
            Word w = sent.wordAt(i);
            if (w.getRole().equals(Word.TARGET_LABEL)) {
                return w;
            }
        }
        return null;
    }
}