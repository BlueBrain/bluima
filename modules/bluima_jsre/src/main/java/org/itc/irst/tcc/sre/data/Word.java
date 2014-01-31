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

import org.itc.irst.tcc.sre.util.PorterStemmer;
//import org.itc.irst.tcc.sre.util.Stemmer;
//import org.itc.irst.tcc.sre.util.StemmerFactory;
//import org.itc.irst.tcc.sre.util.StemmerNotFoundException;
//import org.itc.irst.tcc.sre.Parameter;
import org.slf4j.Logger;import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class Word
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>Word</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(Word.class.getName());


	//
	public static final String TARGET_LABEL = "T";

	//
	public static final String AGENT_LABEL = "A";

	//
	public static final String OTHER_LABEL = "O";

	//
	private static final int OFFSET = 0;
	private static final int FORM = 1;
	private static final int LEMMA = 2;
	private static final int POS = 3;

	// entity type (e.g. PER, ORG, LOC)
	private static final int ENTITY_TYPE = 4;

	// candidate enity role (e.g. T, A, O)
	private static final int LABEL = 5;

	private static final int STEM = 6;

	//
	private String[] feats;

	//
	private int offset;

	//
	private String wlemma;

	//
	private double v;

	//
	//static final StemmerFactory stemmerFactory = StemmerFactory.getStemmerFactory();

	//
	//static final Parameter parameter = Parameter.getInstance();

	// /ren
	public Word(String[] feats, int offset) {
        this.feats = feats;
        this.offset = offset;
    }

    public Word(String t)
	{
		// offset&&form&&lemma&&POS&&type&&label

		try
		{
			feats = new String[7];
			String[] f = t.split("&&");
			offset = Integer.parseInt(f[OFFSET]);

			feats[FORM] = f[FORM];
			feats[LEMMA] = f[LEMMA];
			feats[POS] = f[POS];
			if (f.length == 5)
			{
				feats[ENTITY_TYPE] = OTHER_LABEL;
				feats[LABEL] = f[ENTITY_TYPE];
			}
			else
			{
				feats[ENTITY_TYPE] = f[ENTITY_TYPE];
				feats[LABEL] = f[LABEL];
			}
			/*
			try
			{
				Stemmer stemmer = stemmerFactory.getInstance(parameter.stemmerName());
				feats[STEM] = stemmer.stem(feats[FORM]);
			}
			catch (StemmerNotFoundException e)
			{
				logger.error(e);
			}
			*/

			// ACE2005, Roth and Semeval-re
			feats[STEM] = PorterStemmer.getStemmer().stem(feats[FORM]);
			// AImed
			//feats[STEM] = f[FORM];

		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			logger.error("Parsing error for word: \"" + t + "\"");
			throw new ArrayIndexOutOfBoundsException(e.toString());
		}
	} // end constructor

	//
	public int getOffset() {return offset;}

	//
	public String getForm(boolean b)
	{
		return feats[FORM];
	} // end getForm

	//
	public String getStem()
	{
		return feats[STEM].toLowerCase();
	} // end getStem

	//
	public String getForm()
	{
		//	look at the candidate role
		if (feats[LABEL].equals(TARGET_LABEL))
		{
			// used in AImed, LLL challenge, Roth, Semeval-re
			return "TARGET";
			//return feats[ENTITY_TYPE];
			//return "TARGET" + Math.random();
			//return feats[FORM];
			//return feats[FORM].toLowerCase();
		}
		else if (feats[LABEL].equals(AGENT_LABEL))
		{
			// used in AImed, LLL challenge, Roth, Semeval-re
			return "AGENT";
			//return feats[ENTITY_TYPE];
			//return "AGENT" + Math.random();
			//return feats[FORM];
			//return feats[FORM].toLowerCase();
		}
		else
		{
			// look at the entity type
			if (feats[ENTITY_TYPE].equals(OTHER_LABEL))
			{
				//return feats[FORM];
				// common tokens
				return feats[STEM].toLowerCase();
			}

			// other entities

			// if the ArgumentSet is not inizialized
			// returns the word form
			// used in AImed, LLL challenge, Roth, Semeval-re
			////////////////// not used in ACE 2005 all
			if (ArgumentSet.getInstance().contains(feats[ENTITY_TYPE]))
				return feats[ENTITY_TYPE];// + Math.random();

			// other type of entities
			// entity: this could be not stemmed
			//return feats[STEM];
			return feats[FORM].toLowerCase();
		}

	} // end getForm

	//
	public String getLemma()
	{
		return feats[LEMMA].toLowerCase();
	} // end getLemma

	//
	public String getPos() {return feats[POS];}

	//
	public String getWPos() {return feats[POS].substring(0, 1);}

	//
	public String getRole()
	{
		return feats[LABEL];
	}

	//
	public String getType()
	{
		return feats[ENTITY_TYPE];
	}

	//
	public int hashCode()
	{
		//logger.info("Word.hashCode?");
		//return super.hashCode();
		return feats[FORM].hashCode();
	}

	//
	public boolean equals(Object obj)
	{
		//logger.info("Word.equals?");


		if (!(obj instanceof Word))
			return false;

		if (this == obj)
			return true;

		Word anotherFeature = (Word) obj;
		/*
		return ((offset == anotherFeature.getOffset()) &&
						(feats[LEMMA].equals(anotherFeature.getLemma())) &&
						(feats[FORM].equals(anotherFeature.getForm())) &&
						(feats[POS].equals(anotherFeature.getPos())) &&
						(feats[ENTITY_TYPE].equals(anotherFeature.getType())) &&
						(feats[LABEL].equals(anotherFeature.getRole())));

			*/

		return feats[FORM].equals(anotherFeature.getForm(false));
	} // end equals

	//
	public String toString()
	{
		StringBuffer sb = new StringBuffer();

		sb.append(offset);
		for (int i=1;i<feats.length-1;i++)
		{
			sb.append("&&");
			sb.append(feats[i]);
		}

		return sb.toString();
	} // end toString

} // end class Word
