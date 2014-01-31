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
package org.itc.irst.tcc.sre.data;

import java.io.*;
import java.util.StringTokenizer;
import org.itc.irst.tcc.sre.util.Vector;
import org.itc.irst.tcc.sre.util.SparseVector;

/**
 * TO DO
 * 
 * @author Claudio Giuliano
 * @version %I%, %G%
 * @since 1.0
 */
public class VectorSet extends ExampleSet {
    //
    public ExampleSet subSet(int fromIndex, int toIndex) {
        ExampleSet sub = new VectorSet();
        for (int i = fromIndex; i < toIndex; i++) {
            sub.add(x(i), y(i), id(i));
        } // end for i
        return sub;
    } // end subSet

    //
    public void write(Writer out) throws IOException {
        PrintWriter pw = new PrintWriter(out);

        for (int i = 0; i < size(); i++) {
///ren            String y =  y(i).toString();
            String y =  y(i)+"";

            // uncomment here to output y={+1,-1}
            /*
             * if (y.equals("0")) pw.print("-1 " + x(i).toString() + "\n"); else
             * if (y.equals("1")) pw.print("+1 " + x(i).toString() + "\n");
             */
            // uncomment here to output y={0,1,2,...}
            pw.print(y(i) + " " + x(i).toString() + "\n");
        } //

        pw.flush();
        pw.close();

    } // end write

    //
    public void read(Reader in) throws IOException {
        // clear the example set
        clear();

        LineNumberReader lr = new LineNumberReader(in);
        String line = null;
        // Object x = null, y = null, id = null;
        int count = 0;
        while ((line = lr.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, " \t\n\r\f:");

            Object y = new Double(st.nextToken());
            int m = st.countTokens() / 2;
            Vector x = new SparseVector();
            for (int j = 0; j < m; j++) {
                int index = Integer.parseInt(st.nextToken());
                double value = Double.parseDouble(st.nextToken());
                x.add(index, value);
            } // end for j

            add(x, y, new Integer(count++));

        } // end while
    } // end read

    /**
     * Returns a shallow copy of this VectorSet instance: the instances, ids and
     * class labels themselves are not cloned.
     * 
     * @return a shallow copy of this example set.
     */
    public ExampleSet copy() {
        ExampleSet copy = new VectorSet();
        for (int i = 0; i < size(); i++)
            copy.add(x(i), y(i), id(i));

        return copy;
    } // end copy

} // end class VectorSet