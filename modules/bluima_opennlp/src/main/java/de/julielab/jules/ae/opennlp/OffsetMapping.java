/** 
 * OffsetMapping.java
 * 
 * Copyright (c) 2007, JULIE Lab. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0 
 *
 * Author: buyko
 * 
 * Current version: 1.1.1	
 * Since version:   1.0
 *
 * Creation date: Mar 1, 2007 
 * 
 * //OffsetMapping class stores the mapping between original indexes from CAS and indexes from 
 * alternative data structures. Different spans can be reconstructed from these mappings.
 **/

package de.julielab.jules.ae.opennlp;

import java.util.ArrayList;

public class OffsetMapping extends ArrayList{
	
    public void putMapping(int index, int offset) {
        Integer element = new Integer(offset);

        if (index < size()) {
            set(index, element);
        } else {
            for (int i = size(); i < index; i++)
                add(null);
            add(element);
        }
    }
 
    public int getMapping(int index) {
        Integer element = (Integer) get(index);
        return element.intValue();
    }
}


