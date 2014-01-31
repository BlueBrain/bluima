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
package org.itc.irst.tcc.sre.kernel.expl;

import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.data.VectorSet;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.SparseVector;
import org.itc.irst.tcc.sre.util.Vector;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public abstract class AbstractMapping implements Mapping {
    static Logger LOG = LoggerFactory
            .getLogger(AbstractMapping.class.getName());

    protected static final int SUBSPACE_DIMENSION = 25000;

    // TO DO
    public abstract Vector[] map(Object x, Object id, FeatureIndex[] index)
            throws IllegalArgumentException;

    protected Vector mergeSubspaces(Vector[] subspaces) {
        // logger.debug("sub space dimension: " + dim);

        Vector result = new SparseVector();

        for (int j = 0; j < subspaces.length; j++) {
            Iterator it = subspaces[j].iterator();
            while (it.hasNext()) {
                SparseVector.Entry e = (SparseVector.Entry) it.next();
                result.add((j * SUBSPACE_DIMENSION + e.getIndex()),
                        e.getValue());
            }
        }

        // add a costant value
        // result.add(1000000, 10);

        // logger.debug("merged space norm " + result.norm());
        return result;
    }

    // return the number of nonzero elements
    /*
     * protected int maxDimension(SparseVector[] subspaces) { int max = 0; for
     * (int i=0;i<subspaces.length;i++) { if (subspaces[i].size() > max) max =
     * subspaces[i].size(); } // end for i
     * 
     * return max; } // end maxDimension
     */

    public VectorSet map(ExampleSet data, FeatureIndex[] index)
            throws IllegalArgumentException {
        // logger.debug("AbstractMapping.map");

        boolean b = (data instanceof SentenceSetCopy);
        if (!b) {
            throw new IllegalArgumentException();
        }

        VectorSet result = new VectorSet();

        //
        for (int i = 0; i < data.size(); i++) {
            Object x = data.x(i);
            Object y = data.y(i);
            Object id = data.id(i);

            //
            Vector[] subspaces = map(x, id, index);

            // merge subspaces
            Vector space = mergeSubspaces(subspaces);

            // normalize feature space
            // space.normalize();

            // add space
            result.add(space, y, id);
        }
        return result;
    }

    public String toString() {
        return "AbstractMapping";
    }
}