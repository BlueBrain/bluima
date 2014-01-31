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

package org.itc.irst.tcc.sre.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class Node {

    static Logger logger = LoggerFactory
            .getLogger(SparseVector.class.getName());

    //
    public int index;

    //
    public double value;

    /**
     * Returns an iterator over the elements in this vector in proper sequence
     * (optional operation).
     * 
     * @return an iterator over the elements in this vector in proper sequence.
     */
    public static double dotProduct(Node[] n1, Node[] n2) {
        // logger.info("n1 " + n1.length);
        // logger.info("n2 " + n2.length);

        double sum = 0;
        int i = 0, j = 0;
        while (i < n1.length && j < n2.length) {
            if (n1[i] == null)
                break;
            // logger.error("n1" + n1[i] + " " + i);

            if (n2[j] == null)
                break;
            // logger.error("n2" + n2[j] + " " + j);

            // logger.info(n1[i].index + " == " + n2[j].index);
            if (n1[i].index == n2[j].index) {
                // logger.info(n1[i].index + " == " + n2[j].index);
                sum += n1[i].value * n2[j].value;
                ++i;
                ++j;
            } else {
                if (n1[i].index > n2[j].index) {
                    // logger.info(n1[i].index + " > " + n2[j].index);
                    ++j;
                } else {
                    // logger.info(n1[i].index + " <= " + n2[j].index);
                    ++i;
                }
            }
        }
        return sum;
    } // end for

    public static void nodeToString(Node[] x) {

    }

} // end class Node
