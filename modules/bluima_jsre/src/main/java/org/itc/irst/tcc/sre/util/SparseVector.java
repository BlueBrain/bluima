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

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class SparseVector implements Vector {

    static Logger logger = LoggerFactory
            .getLogger(SparseVector.class.getName());

    //
    private Entry head;

    //
    private Entry tail;

    //
    private int count;

    //
    private int size;

    //
    public SparseVector() {
        count = 0;
        size = 0;
        head = null;
        tail = null;
    } // end constructor

    public Node[] toArray() {
        // logger.info("count " + count);
        Node[] array = new Node[count];
        int i = 0;

        // System.out.print("node:");
        Iterator it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            // System.out.print(" " + e.getIndex());
            array[i] = new Node();
            array[i].index = e.getIndex();
            array[i].value = e.getValue();
            i++;
        }
        // System.out.print("\n");
        return array;
    } // end toArray

    //
    public void add(int index, double value) throws IndexOutOfBoundsException {
        if (index < 0)
            throw new IndexOutOfBoundsException();

        if (index > size)
            size = index;

        Entry newEntry = new Entry(index, value);
        // count++;

        if (head == null) {
            // first element
            // logger.debug("first element " + newEntry);
            head = newEntry;
            tail = newEntry;
            newEntry.setNext(null);
            newEntry.setPrev(null);
            count++;
        } else {
            for (Entry entry = head; entry != null; entry = entry.getNext()) {

                Entry prevEntry = entry.getPrev();
                if (entry.getIndex() > index) {

                    newEntry.setPrev(prevEntry);
                    newEntry.setNext(entry);
                    entry.setPrev(newEntry);
                    if (prevEntry != null)
                        prevEntry.setNext(newEntry);
                    if (entry == head) {
                        head = newEntry;
                        // logger.debug("first element " + newEntry);
                    }
                    // else
                    // logger.debug("middle element " + newEntry);
                    count++;
                    return;
                } else if (entry.getIndex() == index) {
                    // logger.debug("same element " + newEntry);
                    newEntry.setPrev(entry.getPrev());
                    newEntry.setNext(entry.getNext());
                    if (prevEntry != null)
                        prevEntry.setNext(newEntry);
                    if (entry == head)
                        head = newEntry;
                    if (entry == tail)
                        tail = newEntry;
                    return;
                }
            } // end for

            // logger.debug("last element " + newEntry);
            newEntry.setPrev(tail);
            newEntry.setNext(null);
            tail.setNext(newEntry);
            tail = newEntry;
            count++;
        }

    } // end add

    //
    public double get(int index) throws IndexOutOfBoundsException {
        // logger.info("get " + index);

        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();

        Entry entry = find(index);

        if (entry != null)
            return entry.getValue();

        return 0;
    } // end get

    //
    public boolean existsIndex(int index) {
        // logger.debug("existsIndex: " + index);

        Entry entry = find(index);

        if (entry != null)
            return true;

        return false;
    } // end existsIndex

    //
    public void set(int index, double value) throws IndexOutOfBoundsException {
        // logger.debug("set " + index + ", " + value);

        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();

        Entry entry = find(index);

        if (entry != null) {
            entry.setValue(value);
        }

    } // end set

    //
    public int size() {
        return size;
    } // end size

    //
    public int elementCount() {
        return count;
    } // end elementCount

    /**
     * Returns the <code>Entry</code> specified by this index
     * 
     * @param index
     *            index of element to return.
     * @return an <code>Entry</code> if and only if the index exists;
     *         <code>null</code> otherwise
     */
    private Entry find(int index) // throws IndexOutOfBoundsException
    {
        // if (index < 0 || index > size())
        // throw new IndexOutOfBoundsException();

        // System.out.print("find: " + index + " (");
        for (Entry entry = head; entry != null; entry = entry.getNext()) {
            // System.out.print(entry.getIndex() + ":" + entry.getValue() +
            // " ");
            if (entry.getIndex() == index)
                return entry;
        } // end for

        // System.out.print(")\n");
        return null;
    } // end find

    //
    public class Entry {
        //
        private Entry prev;

        //
        private Entry next;

        //
        private int index;

        //
        private double value;

        //
        public Entry(int index, double value) {
            this.index = index;
            this.value = value;
        } // end constructor

        //
        public int getIndex() {
            return index;
        } // end getIndex

        //
        public double getValue() {
            return value;
        } // end getValue

        //
        public void setValue(double value) {
            this.value = value;
        } // end setValue

        //
        public Entry getPrev() {
            return prev;
        } // end getPrev

        //
        public Entry getNext() {
            return next;
        } // end getNext

        //
        public Entry setPrev(Entry e) {
            return prev = e;
        } // end setPrev

        //
        public Entry setNext(Entry e) {
            return next = e;
        } // end setNext

        //
        public String toString() {
            return index + ":" + value;
            // return index + "";
        } // end toString

    } // end class Entry

    //
    private final class VectorIterator implements Iterator {
        //
        private Entry next;

        //
        VectorIterator() {
            next = head;
        } // end constructor

        //
        public boolean hasNext() {
            return next != null;
        }

        //
        public Object next() {
            Entry e = next;

            next = e.getNext();
            return e;
        }

        //
        public void remove() {
        }

    } // class VectorIterator

    //
    public Iterator iterator() {
        return new VectorIterator();
    } // end iterator

    //
    public String toString() {
        // logger.info("SparseVector.toString");
        StringBuffer sb = new StringBuffer();
        Iterator it = iterator();

        // first element
        if (it.hasNext())
            sb.append(it.next());

        while (it.hasNext()) {
            sb.append(" ");
            sb.append(it.next());
        } // end while

        return sb.toString();
    } // end toString

    //
    public double dotProduct(Vector v) {
        double d = 0;
        int c = 0;
        // int count = 0;
        Entry e1 = null, e2 = null;
        Iterator it1 = iterator();
        Iterator it2 = v.iterator();
        while (true) {
            if (c == 0) {
                if (it1.hasNext())
                    e1 = (Entry) it1.next();
                else
                    break;
                if (it2.hasNext())
                    e2 = (Entry) it2.next();
                else
                    break;
            } else if (c == 1) {
                if (it1.hasNext())
                    e1 = (Entry) it1.next();
                else
                    break;
            } else if (c == 2) {
                if (it2.hasNext())
                    e2 = (Entry) it2.next();
                else
                    break;
            }

            // logger.debug(count + ", " + e1.getIndex() + ":" + e1.getValue() +
            // ", " + e2.getIndex() + ":" + e2.getValue());

            if (e1.getIndex() == e2.getIndex()) {
                d += e1.getValue() * e2.getValue();
                c = 0;

            } else if (e1.getIndex() < e2.getIndex()) {
                c = 1;
            } else {
                c = 2;
            }

            // logger.debug("case " + c);
        } // end for;

        return d;
    } // end dotProduct

    //
    public double norm() {
        double norm = 0;

        Iterator it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            norm += Math.pow(e.getValue(), 2);
        }

        return Math.sqrt(norm);
    } // end normalize

    //
    public void normalize() {
        double norm = norm();
        // logger.debug("norm before = " + norm);

        // CHECK THIS!
        if (norm == 0)
            return;

        Iterator it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            e.setValue(e.getValue() / norm);
        }

        // logger.debug("norm after = " + norm());
    } // end normalize

    //
    public boolean check() {
        // logger.debug("check");
        Entry last = null;
        Iterator it = iterator();

        // skip the first entry
        if (it.hasNext())
            last = (Entry) it.next();

        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            // System.out.print(" " + e);

            if (e.getIndex() < last.getIndex()) {
                logger.error(e + " < " + last);
                return false;
            }

            last = e;
        }
        // System.out.print("\n");

        return true;
    } // end check

//    //
//    public static void main(String args[]) throws Exception {
//
//        long begin = System.currentTimeMillis();
//        /*
//         * SparseVector vec = new SparseVector(); // java
//         * org.itc.irst.tcc.sre.util.SparseVector
//         * 
//         * int max = 50000; for (int i=0;i<max;i++) { double r = Math.random();
//         * int j = (int) (r * max); //System.out.println(i + ":" + j + ":" + r);
//         * if (r < 0.0005) vec.add(i, r);
//         * 
//         * } // end for i
//         * 
//         * 
//         * System.out.println(vec); System.out.println(vec.size());
//         */
//        Vector v1 = new SparseVector();
//        v1.add(0, 1);
//        v1.add(5, 1);
//        v1.add(7, 1);
//        v1.add(42, 1);
//        v1.add(12, 1);
//
//        Vector v2 = new SparseVector();
//        v2.add(0, 1);
//        v2.add(5, 1);
//        v2.add(8, 1);
//        v2.add(42, 1);
//        v2.add(13, 1);
//        v2.add(12, 1);
//
//        logger.info("v1: " + v1);
//        logger.info("v2: " + v2);
//
//        logger.info(v1 + " * " + v2 + " = " + v1.dotProduct(v2));
//        long end = System.currentTimeMillis();
//        System.out.println("time " + (end - begin) + " ms");
//    } // end main
} // end class SparseVector
