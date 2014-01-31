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

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import java.util.*;
import org.itc.irst.tcc.sre.util.FreqSet;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public abstract class ExampleSet implements ExampleSetReader, ExampleSetWriter
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>ExampleSet</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(ExampleSet.class.getName());

	//
	private List instances;

	//
	private List identifiers;

	//
	private List classes;

	//
	private FreqSet freq;

	//
	public ExampleSet()
	{
		instances = new ArrayList();
		classes = new ArrayList();
		identifiers = new ArrayList();
		freq = new FreqSet();
	} // end constructor

	//
	public abstract ExampleSet subSet(int fromIndex, int toIndex);

	public void clear()
	{
		instances.clear();
		classes.clear();
		identifiers.clear();
	} // end clear

	//
	public Object x(int i)
	{
		return instances.get(i);
	} // end x

	//
	public Object id(int i)
	{
		return identifiers.get(i);
	} // end id

	//
	public Object y(int i)
	{
		return classes.get(i);
	} // end y

	//
	public int size()
	{
		return instances.size();
	} // end size

	//
	public void add(Object x, Object y, Object id)
	{
		instances.add(x);
		classes.add(y);
		identifiers.add(id);
		freq.add(y);
	} // end add

	/**
	 * Appends all of the elements in the specified example set
	 * to the end of this example set.
	 *
	 * @param data example set whose elements are to be added
	 *						 to this example set.
	 *
	 */
	public void addAll(ExampleSet data)
	{
		for (int i=0;i<data.size();i++)
			add(data.x(i), data.y(i), data.id(i));

	} // end addAll

	/*
	 * Returns a view of the portion of this example set between
	 * the specified fromIndex, inclusive, and toIndex, exclusive.
	 * (If fromIndex and toIndex are equal, the returned example
	 * set is empty.) The returned example set is backed by this
	 * example set, so non-structural changes in the returned
	 * example set are reflected in this example set, and vice-versa.
	 * set operations supported by this example set.
	 *
	 * @param fromIndex low endpoint (inclusive) of the subset.
	 * @param toIndex		high endpoint (exclusive) of the subset.
	 * @return a view of the specified range within this example set.
	 * @throws IndexOutOfBoundsException for an illegal endpoint index value (fromIndex < 0 || toIndex > size || fromIndex > toIndex).

	public ExampleSet subset(int fromIndex, int toIndex) throws IndexOutOfBoundsException
	{
		if (fromIndex < 0 || toIndex > size || fromIndex > toIndex)
			throw new IndexOutOfBoundsException();

	} // end subset
	*/

	//
	public Object[] classArray()
	{
		return freq.toArray();
	} // end classArray

	//
	public Iterator classes()
	{
		return freq.iterator();
	} // end classes

	//
	public int classFreq(Object y)
	{
		return freq.get(y);
	} // end classFreq

	//
	public int getClassCount()
	{
		return freq.size();
	} // end getClassCount

	/**
	 * Returns a  copy of this ExampleSet instance
	 *
	 * @return a copy of this example set.
	 */
	public abstract ExampleSet copy();

	//
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		for (int i=0;i<size();i++)
		{
			sb.append("(" + i + ") ");
			sb.append(id(i));
			sb.append("\n");
		} // end for

		return sb.toString();
	} // end toString


} // end abstract class ExampleSet
