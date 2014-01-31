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
public class SecondTargetContext extends LocalContext
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>SecondTargetContext</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(SecondTargetContext.class.getName());

	/**
	 * A prototype for a global context such that only
	 * one instance class can ever exist.
	 */
	private static SecondTargetContext context;

	//
	private SecondTargetContext()
	{
		context = this;
	} // end constructor

	/**
	 * Returns <code>SecondTargetContext</code> object; only
	 * one <code>SecondTargetContext</code> instance can
	 * exist.
	 *
	 * @return	<code>SecondTargetContext</code> object
	 */
	public static synchronized SecondTargetContext getInstance()
	{

		if (context == null)
		{
			context = new SecondTargetContext();
		}

		return context;
	} // end getInstance


	//
	// attenzione che quando non trova il target
	// il local context potrebbe essere il full
	// context. verificare
	public Sentence filter(Sentence sent)
	{
		//logger.debug("\"" + Array.toString(sent) + "\"");
		int start = 0, end = sent.length() - 1;
		boolean b = false, p = false;
		for (int i=0;i<sent.length();i++)
		{
			if (sent.wordAt(i).getRole().equals(Word.TARGET_LABEL))
			{
				if (b)
				{
					start = i - getSize();
					p = true;
					break;
				}
				else
					b = true;
			}
		} // end for i

		if (!p)
			return null;

		end = start + 2 * getSize() + 1;

		if (start < 0)
			start = 0;

		if (end > sent.length())
			end = sent.length();

		return sent.fragment(start, end);

	} // end filter

	//
	public int index(Sentence sent)
	{
		//logger.debug("\"" + Array.toString(sent) + "\"");

		boolean b = false;
		for (int i=0;i<sent.length();i++)
		{
			Word w = sent.wordAt(i);
			if (w.getRole().equals(Word.TARGET_LABEL))
			{
				if (b)
				{
					return i;
				}
				else
					b = true;
			}
		} // end for i

		return -1;

	} // end index

	//
	public Word word(Sentence sent)
	{
		//logger.debug("\"" + Array.toString(sent) + "\"");

		boolean b = false;
		for (int i=0;i<sent.length();i++)
		{
			Word w = sent.wordAt(i);
			if (w.getRole().equals(Word.TARGET_LABEL))
			{
				if (b)
				{
					return w;
				}
				else
					b = true;
			}
		} // end for i

		return null;

	} // end word

} // end class SecondTargetContext
