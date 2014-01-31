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
package org.itc.irst.tcc.sre.data.context;

import org.itc.irst.tcc.sre.data.Sentence;
import org.itc.irst.tcc.sre.data.Word;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
*
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class MiddleContext extends GlobalContext
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>MiddleContext</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(MiddleContext.class.getName());

	/**
	 * A prototype for a global context such that only
	 * one instance class can ever exist.
	 */
	private static MiddleContext context;

	//
	private MiddleContext()
	{
		context = this;
	} // end constructor

	//
	public Sentence filter(Sentence sent)
	{
		boolean b = true;
		int start = 0, end = sent.length() - 1;
		for (int i=0;i<sent.length();i++)
		{
			if (!sent.wordAt(i).getRole().equals(Word.OTHER_LABEL))
			{
				if (b)
					//start = i + 1;
					start = i - 1;
				else
				{
					//end = i;
					end = i + 2;
					break;
				}
				b = false;
			}
		}

		if (end > sent.length())
			end = sent.length();
		if (start < 0)
			start = 0;

		return sent.fragment(start, end);
	} // end filter

	/**
		* Returns <code>MiddleContext</code> object; only
	 * one <code>MiddleContext</code> instance can
	 * exist.
	 *
	 * @return	<code>MiddleContext</code> object
	 */
	public static synchronized MiddleContext getInstance()
	{
		//logger.debug("Lexicon.getLexicon");
		if (context == null)
		{
			context = new MiddleContext();
		}

		return context;
	} // end getInstance

} // end class MiddleContext
