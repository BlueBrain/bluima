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

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.*;

/**
 * An object that maps features to indexes.
 * A <code>FeatureIndex</code> cannot contain duplicate
 * features; each feature can map to at most one index.
 *
 * @author		Claudio Giuliano
 * @version 	%I%, %G%
 * @since			1.0
 * @see FeatureIndex
 */
public class FeatureIndex
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>FeatureIndex</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(FeatureIndex.class.getName());


	/**
	 * to do.
	 */
	private SortedMap map;

	/**
	 * to do.
	 */
	private int count;

	/**
	 * to do.
	 */
	private boolean readOnly;

	/**
	 * Constructs a <code>FeatureIndex</code> object.
	 */
	public FeatureIndex(boolean readOnly)
	{
		this(readOnly, 0);
	} // end constructor

	/**
	 * Constructs a <code>FeatureIndex</code> object.
	 */
	public FeatureIndex(boolean readOnly, int count)
	{
		//logger.info("FeatureIndex " + count);

		map = new TreeMap();
		//map = new HashMap();

		this.count = count;
		this.readOnly = readOnly;
	} // end constructor

	//
	public void readOnly(boolean b)
	{
		this.readOnly = readOnly;
	} // end readOnly

	/**
	 * Returns the <i>index</i> of the specified feature and adds
	 * the feature to the index if it is not present yet.
	 *
	 * @param feature	the feature.
	 * @return 			the <i>index</i> of the specified feature.
	 */
	public int put(String feature)
	{
		//logger.debug("FeatureIndex.put : " + feature + "(" + count + ")");
		Integer index = (Integer) map.get(feature);

		if (index == null)
		{
			if (readOnly)
				return -1;

			index = new Integer(count++);
			map.put(feature, index);
		}

		return index.intValue();
	} // end get

	//
	public int size()
	{
		return map.size();
	} // end size

	/**
	 * Returns the <i>index</i> of the specified feature and adds
	 * the feature to the index if it is not present yet.
	 *
	 * @param feature	the feature.
	 * @return 			the <i>index</i> of the specified feature.
	 */
	public int getIndex(String feature)
	{
		//logger.debug("FeatureIndex.get : " + feature + "(" + count + ")");
		Integer index = (Integer) map.get(feature);

		if (index == null)
			return -1;

		return index.intValue();
	} // end get

	//
	public Set featureSet()
	{
		return map.keySet();
	} // end featureSet

	//
	public void clear()
	{
		count = 0;
		map.clear();
	} // end clear

	/**
	 * Returns a <code>String</code> object representing this
	 * <code>Word</code>.
	 *
	 * @return a string representation of this object.
	 */
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		Iterator it = map.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry me = (Map.Entry) it.next();
			sb.append(me.getValue());
			sb.append("\t");
			sb.append(me.getKey());
			sb.append("\n");
		}
		return sb.toString();
	} // end toString

	/**
	 * Writes the feature index into the specified
	 * output stream in a format suitable for loading
	 * into a <code>Map</code> using the
	 * {@link #read(Reader) write} method.
	 *
	 * @param out						a <code>Writer</code> object to
	 *											provide the underlying stream.
	 * @throws IOException	if writing this feature index
	 *											to the specified  output stream
	 *											throws an <code>IOException</code>.
	 */
	public void write(Writer out) throws IOException
	{
		//logger.debug("writing feature index");

		PrintWriter pw = new PrintWriter(out);

		Iterator it = map.entrySet().iterator();
		//logger.debug("index size " + map.entrySet().size());

		while (it.hasNext())
		{
			Map.Entry me = (Map.Entry) it.next();
			// feature index
			pw.println(me.getValue() + "\t" + me.getKey());
		}
		pw.flush();
		pw.close();

	} // end read

	/**
	 * Reads the feature index from the specified input stream.
	 * <p>
	 * This method processes input in features of lines. A natural
	 * line of input is terminated either by a set of line
	 * terminator  characters (\n or \r or  \r\n) or by the end
	 * of the file. A natural line  may be either a blank line,
	 * a comment line, or hold some part  of a id-feature pair.
	 * Lines are read from the input stream until  end of file
	 * is reached.
	 * <p>
	 *  A natural line that contains only white space characters
	 * is  considered blank and is ignored. A comment line has
	 * an ASCII  '#' as its first non-white  space character;
	 * comment lines are also ignored and do not encode id-feature
	 * information.
	 * <p>
	 * The id contains all of the characters in the line starting
	 * with the first non-white space character and up to, but
	 * not  including, the first '\t'. All remaining characters
	 * on the line become part of  the associated feature string;
	 * if there are no remaining  characters, the feature is the
	 * empty string "".
	 *
	 * @param in						a <code>Reader</code> object to
	 *											provide the underlying stream.
	 * @throws IOException	if reading this feature index
	 *											from the specified  input stream
	 *											throws an <code>IOException</code>.
	 */
	public void read(Reader in) throws IOException
	{
		logger.debug("reading feature index...");

		LineNumberReader lnr = new LineNumberReader(in);
		map.clear();

		String line;
		String[] s;
		Integer id;
		while ((line = lnr.readLine()) != null)
		{
			//line = line.trim();
			//logger.debug("\"" +line + "\"");
			if (!line.startsWith("#"))
			{
				s = line.split("\t");

				if (s.length == 2)
				{
					// feature index
					map.put(s[1], new Integer(s[0]));

					// SET COUNT
					count++;
				}
				else
				{

				}
			}
		}
		lnr.close();


	} // end read

} // end class FeatureIndex
