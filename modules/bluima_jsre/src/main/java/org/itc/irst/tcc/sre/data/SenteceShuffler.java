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


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class SenteceShuffler implements Shuffler
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>SenteceShuffler</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(SenteceShuffler.class.getName());

	//
	private Random rnd;

	//
	private Map map;

	//
	public SenteceShuffler()
	{
		rnd = new Random(13);
		map = new HashMap();
	} // end constructor

	//
	public ExampleSet shuffle(ExampleSet data)
	{
		logger.info("Data set shuffler " + data.size());

		crateMap(data);
		Object[] array = keyArray();
		shuffleArray(array);

		return createExampleSet(array, data);
	} // end shuffle

	//
	public ExampleSet createExampleSet(Object[] shuffled, ExampleSet data)
	{
		ExampleSet copy = new SentenceSetCopy();

		for (int i=0;i<shuffled.length;i++)
		{
			List list = (List) shuffled[i];

			for(int j=0;j<list.size();j++)
			{
				int index = ((Integer) list.get(j)).intValue();
				copy.add(data.x(index), data.y(index), data.id(index));
			} // end for j

		} // end for i
		return data;
	} // end createExampleSet

	//
	private void crateMap(ExampleSet data)
	{
		//
		for (int i=0;i<data.size();i++)
		{
			String id =  (String) data.id(i);
			String prefix = id.substring(0, id.indexOf('-'));

			put(prefix, new Integer(i));
		} // end for

	} // end crateMap

	//
	private Object[] keyArray()
	{
		logger.debug("toArray");
		int i = 0;
		Object[] array = new Object[map.size()];
		Iterator it = map.keySet().iterator();

		while (it.hasNext())
			array[i++] = map.get(it.next());

		return array;
	} // end keyArray

	//
	private void put(String d, Integer index)
	{
		List list = (List) map.get(d);
		if (list == null)
		{
			list = new ArrayList();
		}

		list.add(index);
		map.put(d, list);
	} // end put

	//
	private List get(String d)
	{
		return (List) map.get(d);
	} // end get

	//
	private void shuffleArray(Object[] array)
	{

		logger.info("shuffling...");
		int l = array.length;
		for(int i=0;i<l;i++)
		{
			int j = i + (int) (rnd.nextDouble() * (l - i));

			Object t = array[i];
			array[i] = array[j];
			array[j] = t;
		}
	} // end shuffleArray

    // //
    // public static void main(String args[]) throws Exception
    // {
    // String logConfig = System.getProperty("log-config");
    // if (logConfig == null)
    // logConfig = "log-config.txt";
    //
    // PropertyConfigurator.configure(logConfig);
    //
    // if (args.length != 1)
    // {
    // System.err.println("java -mx512M org.itc.irst.tcc.sre.data.SenteceShuffler in");
    // System.exit(-1);
    // }
    //
    // ExampleSet in = new SentenceSetCopy();
    // in.read(new BufferedReader(new FileReader(args[0])));
    //
    // System.out.println("size: " + in.size());
    // System.out.println(in);
    //
    //
    // Shuffler shuffler = new SenteceShuffler();
    // ExampleSet out = shuffler.shuffle(in);
    //
    // System.out.println("size: " + out.size());
    // System.out.println(out);
    //
    // } // end main

} // end class SenteceShuffler
