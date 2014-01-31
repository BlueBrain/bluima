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

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class ArgumentSet
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>ArgumentSet</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(ArgumentSet.class.getName());

	/**
	 * A prototype for a global context such that only
	 * one instance class can ever exist.
	 */
	private static ArgumentSet argumentSet;

	//
	private Set set;

	//
	private ArgumentSet()
	{
		argumentSet = this;
		set = new HashSet();
	} // end constructor

	//
	public void init(ExampleSet exampleSet)
	{
		logger.info("find argument types");
		set = new HashSet();

		if (exampleSet.size() > 1)
		{
			Sentence sent = (Sentence) exampleSet.x(0);
			addCandidateType(sent);
		}
	} // end init

	//
	public boolean contains(Object o)
	{
		return set.contains(o);
	} // end contains

	//
	private void addCandidateType(Sentence sent)
	{
		boolean b = true;


		for (int i=0;i<sent.length();i++)
		{
			if (!sent.wordAt(i).getRole().equals(Word.OTHER_LABEL))
			{
				if (b)
				{
					logger.info("arg1 type: " + sent.wordAt(i).getType());
					set.add(sent.wordAt(i).getType());
				}
				else
				{
					logger.info("arg2 type: " + sent.wordAt(i).getType());
					set.add(sent.wordAt(i).getType());
					break;
				}
				b = false;
			}
		} // end for
	} // end addCandidateType

	/**
	 * Returns <code>ArgumentSet</code> object; only
	 * one <code>ArgumentSet</code> instance can
	 * exist.
	 *
	 * @return	<code>ArgumentSet</code> object
	 */
	public static synchronized ArgumentSet getInstance()
	{
		if (argumentSet == null)
		{
			argumentSet = new ArgumentSet();
		}

		return argumentSet;
	} // end getInstance

} // end class ArgumentSet
