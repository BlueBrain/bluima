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
import java.text.*;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class FreqSet
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>FreqSet</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(FreqSet.class.getName());

	//
	private Map map;

	//
	public FreqSet()
	{
		map = new HashMap();
	} // end constructor

	//
	public boolean add(Object o)
	{
		Counter c = (Counter) map.get(o);
		if (c == null)
		{
			map.put(o, new Counter(1));
			return true;
		}

		c.inc();
		return false;
	} // end add

	//
	public boolean contains(Object o)
	{
		Counter c = (Counter) map.get(o);
		if (c == null)
			return false;

		return true;
	} // end contains

	//
	public Collection values()
	{
		return map.values();
	} // end values

	public Object[] toArray()
	{
		return map.keySet().toArray();
	} // end toArray

	//
	public Iterator iterator()
	{
		return map.keySet().iterator();
	} // end iterator

	//
	public int get(Object o)
	{
		Counter c = (Counter) map.get(o);
		if (c == null)
			return -1;

		return c.get();

	} // end get

	//
	public int size()
	{
		return map.size();
	} // end size

	//
	class Counter
	{
		//
		int count;

		//
		public Counter(int count)
		{
			this.count = count;
		} // end constructor

		//
		public void inc()
		{
			count++;
		} // end inc

		//
		public int get()
		{
			return count;
		} // end get

	} // end class Counter

} // end class FreqSet
