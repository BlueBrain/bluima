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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class Sentence
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>Sentence</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(Sentence.class.getName());

	//
	public static final int GLOBAL_CONTEXT_SIZE = 5;

	//
	public static final int DEFAULT_LOCAL_CONTEXT_SIZE = 2;

	//
	//Word[] sent;
	List sent;

	//
	public Sentence()
	{
		sent = new ArrayList();
	} // end constructor

	//
	public Sentence(String t)
	{
		sent = new ArrayList();
		parse(t);
		/*
		StringTokenizer st = new StringTokenizer(t, " ");

		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			Word w = new Word(token);
			sent.add(w);
		} // end while
		*/
	}	// end constructor

	//
	public void parse(String t)
	{
		sent = new ArrayList();
		StringTokenizer st = new StringTokenizer(t, " ");

		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			try
			{
				Word w = new Word(token);
				sent.add(w);
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				logger.error("Parsing error detected in sentence: \"" + t + "\"",e);
				System.exit(-1);
			}

		} // end while

	}	// end parse

	//
	public Sentence(List sent)
	{
		this.sent = sent;
	} // end constructor

	//
	public Word wordAt(int i)
	{
		//return wordAt(i);
		return (Word) sent.get(i);
	} // end wordAt

	//
	public int length()
	{
		return sent.size();
	} // end length

	//
	public Sentence fragment(int beginIndex)
	{
		return fragment(beginIndex, length());
	} // end fragment

	//
	public Sentence fragment(int fromIndex, int toIndex)
	{
		//if (fromIndex < 0 || toIndex > sent.length || fromIndex > toIndex)
		//	throw new IndexOutOfBoundsException();

		return new Sentence(sent.subList(fromIndex, toIndex));
	} // end fragment

	//
	public String toOriginalString()
	{
		StringBuffer sb = new StringBuffer();

		for(int i=0;i<length();i++)
		{
			if (i > 0)
				sb.append(" ");
			sb.append(wordAt(i));
		}

		return sb.toString();
	} // end toOriginalString

	//
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		for(int i=0;i<length();i++)
		{
			if (i > 0)
				sb.append(" ");
			/*
			sb.append(wordAt(i).getOffset() + ":");
			sb.append(wordAt(i).getForm(false));
			if (!wordAt(i).getType().equals(Word.OTHER_LABEL))
				sb.append(":" + wordAt(i).getType());

			if (!wordAt(i).getRole().equals(Word.OTHER_LABEL))
				sb.append(":" + wordAt(i).getRole());
			*/

			if (wordAt(i).getRole().equals(Word.TARGET_LABEL))
			{
				sb.append(wordAt(i).getOffset() + ":");
				sb.append(wordAt(i) + ":" + Word.TARGET_LABEL);
			}
			else if (wordAt(i).getRole().equals(Word.AGENT_LABEL))
			{
				sb.append(wordAt(i).getOffset() + ":");
				sb.append(wordAt(i) + ":" + Word.AGENT_LABEL);
			}
			else
			{
				 sb.append(wordAt(i).getForm(true));
			}


			//sb.append(wordAt(i).getForm(true));
		}

		return sb.toString();
	} // end toString


} // end class Sentence
