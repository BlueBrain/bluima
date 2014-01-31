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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class Orthographic
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>Orthographic</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(Orthographic.class.getName());

	//
	public static final String WORD_FORM = "_word";
	public static final String TYPE = "_type";
	public static final String LEMMA = "_lemma";
	public static final String PART_OF_SPEECH = "_POS";
	public static final String STEM = "_stem";
	public static final String UPPER_CASE = "_UPPER";
	public static final String LOWER_CASE = "_low";
	public static final String CAPITALIZED = "_Cap";
	public static final String NUMERIC = "_num";
	public static final String ALPHANUMERIC = "_alpha";
	public static final String PUNCTUATION = "_punct";
	public static final String PARENTHESIS = "_par";
	public static final String QUOTE = "_quote";

	//
	public static final String ALL_DIGITS = "_digs";
	public static final String ENDS_WITH_DOTS = "_ewd";
	public static final String SINGLE_LETTER = "_sch";
	public static final String SINGLE_DIGIT = "_sdig";
	public static final String ROMAN_NUMBER = "_romans";
	public static final String GREEK_LETTER = "_greeks";
	public static final String ALL_CONSONANTS = "_cons";
	public static final String ALL_VOWELS = "_vows";
	public static final String CONTAINS_DASH = "_dash";
	public static final String CONTAINS_SYMBOLS = "_symbs";
	public static final String PERCENTAGE = "_perc";

	//
	public static final String PART = "_part";

	//
	private static final String[] romans = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"};

	//
	private static final String[] greekLetters = {"alpha", "beta", "gamma", "delta", "epsilon", "zeta", "eta", "theta", "iota", "kappa", "lambda", "xi", "omicron", "rho", "sigma", "tau", "upsilon", "phi", "chi", "psi", "omega"};

	//
	private static final char[] consonants = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Z', 'Y'};

	//
	private static final char[] vowels = {'A', 'E', 'I', 'O', 'U'};

	//
	public static Object[] characterNGram(String s, int b, int e)
	{
		List list = new ArrayList();

		//logger.info(s + ", " + s.length() + ", " + b + ", " + e);

		if (s.length() < b)
			return list.toArray();

		int begin = 0, end = s.length() - b;

		if (end > s.length())
			end = s.length();
		//logger.info("begin = " + begin + ", end = " + end);

		for (int i=begin;i<=end;i++)
		{
			//logger.info("i = " + i + ", " + s.charAt(i) + ", " + (i+b));

			int jend = i + e;
			if (jend > s.length())
				jend = s.length();

			//logger.info("jend = " + jend);
			for (int j=i+b;j<=jend;j++)
			{
				//logger.info("\nj = " + j + ", " + s.charAt(j));
				//logger.info(i + ", " + j + ", " + s.substring(i, j).toLowerCase());
				list.add("NGRAM(" + s.substring(i, j).toLowerCase() + ")");
			}
		}

		return list.toArray();
	} // end prefixes

	//
	public static Object[] prefixes(String s, int b, int e)
	{
		List list = new ArrayList();

		//logger.info(s + ", " + s.length() + ", " + b + ", " + e);

		if (s.length() < b)
			return list.toArray();

		int end = e + 1, begin = b;

		if (end > s.length())
			end = s.length();

		//logger.info("begin = " + begin + ", end = " + end);

		for (int i=begin;i<end;i++)
		{
			list.add("P(" + s.substring(0, i).toLowerCase() + ")");
		}

		return list.toArray();
	} // end prefixes

	//
	public static Object[] suffixes(String s, int b, int e)
	{
		List list = new ArrayList();

		//logger.info(s + ", " + s.length() + ", " + b + ", " + e);

		if (s.length() < b)
			return list.toArray();

		int end = s.length() - b + 1, begin = s.length() - e;

		//logger.info("begin = " + begin + ", end = " + end);

		if (begin < 0)
			begin = 0;

		for (int i=begin;i<end;i++)
		{
			//logger.info("i = " + i + ", " + s.charAt(i));
			list.add("S(" + s.substring(i, s.length()).toLowerCase() + ")");
		}

		return list.toArray();
	} // end prefixes

	//
	public static final boolean isNumeric(String s)
	{
		//logger.debug("Orthographic.isNumeric: " + s);
		if (s.length() == 0)
			return false;
		/*
		if (s.charAt(0) == '+' || s.charAt(0) == '-' || s.charAt(0) == '.' || Character.isDigit(s.charAt(0)))
		{
			logger.debug("Orthographic.inside: " + s);
			for(int i=1;i<s.length();i++)
			{
				if (s.charAt(i) != '.' || s.charAt(i) != ',')
					if (!Character.isDigit(s.charAt(i)))
						return false;
			} // end for i
		}
		else
			return false;
		*/

		try
		{
			Double.parseDouble(s);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		return true;
	} // end isNumeric

	//
	public static final boolean allDigits(String s)
	{
		//logger.debug("Orthographic.allDigits: " + s);
		if (s.length() == 0)
			return false;

		for(int i=0;i<s.length();i++)
		{
			if (!Character.isDigit(s.charAt(i)))
				return false;
		} // end for i

		return true;
	} // end allDigits

	//
	public static final boolean endsWithDots(String s)
	{
		if (s.length() < 2)
			return false;

		return s.charAt(s.length() - 1) == '.';
	} // end endsWithDots

	//
	public static final boolean isUpperCase(String s)
	{
		if (s.length() == 0)
			return false;

		if (!Character.isUpperCase(s.charAt(0)))
				return false;

		//logger.debug("Orthographic.isUpperCase: " + s);
		for(int i=1;i<s.length();i++)
		{
			if (s.charAt(i) != '-')
				if (!Character.isUpperCase(s.charAt(i)))
					return false;
		} // end for i

		return true;
	} // end isUpperCase

	//
	public static final boolean isLowerCase(String s)
	{
		//logger.debug("Orthographic.isLowerCase: " + s);

		if (s.length() == 0)
			return false;

		if (!Character.isLowerCase(s.charAt(0)))
				return false;

		for(int i=0;i<s.length();i++)
		{
			if (s.charAt(i) != '-')
				if (!Character.isLowerCase(s.charAt(i)))
					return false;
		} // end for i

		return true;
	} // end isLowerCase

	//
	public static final boolean isAlphanumeric(String s)
	{
		//logger.debug("Orthographic.isAlphanumeric: " + s);

		if (s.length() == 0)
			return false;

		if (!Character.isLetterOrDigit(s.charAt(0)))
				return false;

		for(int i=0;i<s.length();i++)
		{
			if (s.charAt(i) != '-')
				if (!Character.isLetterOrDigit(s.charAt(i)))
					return false;
		} // end for i

		return true;
	} // end isAlphanumeric

	//
	public static final boolean isCapitalized(String s)
	{
		//logger.debug("Orthographic.isCapitalized: " + s);
		if (s.length() == 0)
			return false;

		if (!Character.isUpperCase(s.charAt(0)))
			return false;

		return isLowerCase(s.substring(1, s.length()));
	} // end isCapitalized

	//
	public static final boolean isPunctuation(String s)
	{
		//System.out.println("\"" + s + "\"");
		if (s.length() != 1)
			return false;

		switch (s.charAt(0))
		{
			case '!': return true;
			case ',': return true;
			case '.': return true;
			case ':': return true;
			case ';': return true;
			case '?': return true;
			case '\u00a1': return true;
			case '\u00bf': return true;
			case '\u01c3': return true;
		}

		//if (s.charAt(0) == Character.CONNECTOR_PUNCTUATION)
		//	return true;

		return false;
	} // end isPunctuation

	//
	public static final boolean isParenthesis(String s)
	{
		if (s.length() != 1)
			return false;

		switch (s.charAt(0))
		{
			case '(': return true;
			case ')': return true;
			case '\"': return true;
			case '\'': return true;
			case '[': return true;
			case ']': return true;
			case '{': return true;
			case '}': return true;
		}

		//if (s.charAt(0) == Character.OTHER_PUNCTUATION)
		//	return true;


		return false;
	} // end isParenthesis

	//
	public static final boolean isQuote(String s)
	{
		if (s.length() != 1)
			return false;

		switch (s.charAt(0))
		{
			case '\"': return true;
			case '\'': return true;
		}

		return false;
	} // end isQuote

	//
	public static final boolean isSingleLetter(String s)
	{
		//logger.debug("Orthographic.isSingleLetter: " + s);

		if (s.length() == 1)
				return Character.isLetter(s.charAt(0));

		return false;
	} // end isSingleLetter

	//
	public static final boolean isSingleDigit(String s)
	{
		//logger.debug("Orthographic.isSingleDigit: " + s);

		if (s.length() == 1)
				return Character.isDigit(s.charAt(0));

		return false;
	} // end isSingleDigit

	//
	public static final String wordShape(String s)
	{
		//logger.debug("Orthographic.wordShape: " + s);
		StringBuffer sb = new StringBuffer();
		char c;
		for(int i=0;i<s.length();i++)
		{
			c = s.charAt(i);
			if (Character.isLetter(c))
				if (Character.isLowerCase(c))
					sb.append("x");
				else
					sb.append("X");
			else if (Character.isDigit(c))
				sb.append("d");
			else
				sb.append(c);
		} // end for i

		return sb.toString();
	} // end wordShape

	//
	public static final String wordShape2(String s)
	{
		//logger.debug("Orthographic.wordShape2: " + s);
		StringBuffer sb = new StringBuffer();
		char c;
		char o = ' ';
		boolean star = false;
		for (int i=0;i<s.length();i++)
		{
			c = s.charAt(i);
			//logger.debug("WordShape.getWordShape.c: " + c);
			if (Character.isUpperCase(c))
			{
				if (Character.isUpperCase(o))
				{
					if (!star)
					{
						sb.append("+");
						star = true;
					}
				}
				else
				{
					sb.append("X");
					star = false;
				}
			}
			else if (Character.isLowerCase(c))
			{
				if (Character.isLowerCase(o))
				{
					if (!star)
					{
						sb.append("+");
						star = true;
					}
				}
				else
				{
					sb.append("x");
					star = false;
				}
			}
			else if (Character.isDigit(c))
			{
				if (Character.isDigit(o))
				{
					if (!star)
					{
						sb.append("+");
						star = true;
					}
				}
				else
				{
					sb.append("d");
					star = false;
				}
			}
			else
				sb.append(c);

			o = c;
			//logger.debug("WordShape.getWordShape.sb: " + sb.toString());
		}

		return sb.toString();
	} // end wordShape2

	//
	public static final boolean isRomanNumber(String s)
	{
		//logger.debug("Orthographic.isRomanNumber: " + s);
		String t = s.toUpperCase();

		for (int i=0;i<romans.length;i++)
		{
			if (t.equals(romans[i]))
				return true;
		}

		return false;
	} // end isRomanNumber

	//
	public static final boolean allConsonants(String s)
	{
		//logger.debug("Orthographic.allConsonants: " + s);
		String t = s.toUpperCase();

		for (int i=0;i<t.length();i++)
		{
			if (!isConsonant(t.charAt(i)))
				return false;
		}

		return true;
	} // end allConsonants

	//
	public static final boolean isConsonant(char c)
	{
		//logger.debug("Orthographic.isConsonant: " + s);

		for (int i=0;i<consonants.length;i++)
		{
			if (c == consonants[i])
				return true;
		}

		return false;
	} // end isConsonant

	//
	public static final boolean allVowels(String s)
	{
		//logger.debug("Orthographic.allVowels: " + s);
		String t = s.toUpperCase();

		for (int i=0;i<t.length();i++)
		{
			if (!isVowel(t.charAt(i)))
				return false;
		}

		return true;
	} // end allVowels

	//
	public static final boolean isVowel(char c)
	{
		//logger.debug("Orthographic.isVowel: " + s);

		for (int i=0;i<vowels.length;i++)
		{
			if (c == vowels[i])
				return true;
		}

		return false;
	} // end isVowel

	//
	public static final boolean isGreekLetter(String s)
	{
		//logger.debug("Orthographic.isGreekLetter: " + s);
		String t = s.toLowerCase();
		for (int i=0;i<greekLetters.length;i++)
		{
			if (t.equals(greekLetters[i]))
				return true;
		}

		return false;
	} // end isGreekLetter

	//
	public static final boolean containsGreekLetter(String s)
	{
		//logger.debug("Orthographic.containsGreekLetter: " + s);
		String t = s.toLowerCase();
		for (int i=0;i<greekLetters.length;i++)
		{
			if (t.matches(".*" + greekLetters[i] + ".*"))
				return true;
		}

		return false;
	} // end containsGreekLetter

	//
	public static final boolean isAcronym(String s)
	{
		//logger.debug("Orthographic.containsGreekLetter: " + s);

		if (s.matches("([A-Za-z]\\.)+"))
				return true;

		return false;
	} // end isAcronym

	//
	public static final boolean isCapAndCap(String s)
	{
		//logger.debug("Orthographic.isCapAndCap: " + s);

		if (s.matches("[A-Z][a-z0-9]+[A-Z][a-z0-9]+"))
				return true;

		return false;
	} // end isCapAndCap

	//(\+|-)?[1-9][0-9]*(\.[0-9]*)?
	//
	public static final boolean isRealNumber(String s)
	{
		//logger.debug("Orthographic.isRealNumber: " + s);

		if (s.matches("(\\+|-)?[1-9][0-9]*([,\\.][0-9]*)?"))
				return true;

		return false;
	} // end isRealNumber

	//(\+|-)?[1-9][0-9]*(\.[0-9]*)?
	//
	public static final boolean isNaturalNumber(String s)
	{
		//logger.debug("Orthographic.isNaturalNumber: " + s);

		if (s.matches("[0-9]+"))
				return true;

		return false;
	} // end isNaturalNumber

	//
	public static final boolean containsDots(String s)
	{
		//logger.debug("Orthographic.containsDots: " + s);

		for(int i=0;i<s.length();i++)
		{
			if (s.charAt(i) == '.')
				return true;
		}

		return false;
	} // end containsDots

	//
	public static final boolean containsDigits(String s)
	{
		//logger.debug("Orthographic.containsDigits: " + s);

		for(int i=0;i<s.length();i++)
		{
			if (Character.isDigit(s.charAt(i)))
				return true;
		}

		return false;
	} // end containsDigits

	//
	public static final boolean containsDash(String s)
	{
		//logger.debug("Orthographic.containsDash: " + s);

		for(int i=0;i<s.length();i++)
		{
			if (s.charAt(i) == '-')
				return true;
		}

		return false;
	} // end containsDash

	//
	public static final boolean isSymbol(String s)
	{
		//logger.debug("Orthographic.isSymbol: " + s);
		if (s.length() != 1)
			return false;

		return isSymbol(s.charAt(0));
	} // end isSymbol

	//
	public static final boolean isPercentage(String s)
	{
		//logger.debug("Orthographic.isPercentage: " + s);
		if (s.length() < 2)
			return false;

		return s.charAt(s.length() - 1) == '%';
	} // end isPercentage

	//
	public static final boolean isSymbol(char c)
	{
		//logger.debug("Orthographic.isSymbol: " + s);

		switch (c)
		{
			case '+': return true;
			case '-': return true;
			case '*': return true;
			case '/': return true;
			case '=': return true;
			case '&': return true;
			case '%': return true;
			case '$': return true;
			case '#': return true;
			case '@': return true;
			case '�': return true;
			case '~': return true;
			case '>': return true;
			case '<': return true;
			case '_': return true;
			case '\\': return true;
			//case '�': return true;
		}

		return false;
	} // end isSymbol

	//
	public static final boolean containsSymbols(String s)
	{
		//logger.debug("Orthographic.containsSymbols: " + s);

		for(int i=0;i<s.length();i++)
		{
			if (!isSymbol(s.charAt(i)))
				return false;
		}

		return true;
	} // end containsSymbols

	//
	public static void test(String t) throws Exception
	{
		logger.info(t);
		logger.info("wordShape(\"" + t + "\") = \"" + Orthographic.wordShape(t) + "\"");
		logger.info("wordShape2(\"" + t + "\") = \"" + Orthographic.wordShape2(t) + "\"");
		logger.info("isPunctuation(\"" + t + "\") = \"" + Orthographic.isPunctuation(t) + "\"");
		logger.info("endsWithDots(\"" + t + "\") = \"" + Orthographic.endsWithDots(t) + "\"");
		logger.info("isUpperCase(\"" + t + "\") = \"" + Orthographic.isUpperCase(t) + "\"");
		logger.info("isLowerCase(\"" + t + "\") = \"" + Orthographic.isLowerCase(t) + "\"");
		logger.info("isAlphanumeric(\"" + t + "\") = \"" + Orthographic.isAlphanumeric(t) + "\"");
		logger.info("isCapitalized(\"" + t + "\") = \"" + Orthographic.isCapitalized(t) + "\"");
		logger.info("isParenthesis(\"" + t + "\") = \"" + Orthographic.isParenthesis(t) + "\"");
		logger.info("isQuote(\"" + t + "\") = \"" + Orthographic.isQuote(t) + "\"");
		logger.info("isRomanNumber(\"" + t + "\") = \"" + Orthographic.isRomanNumber(t) + "\"");
		logger.info("isGreekLetter(\"" + t + "\") = \"" + Orthographic.isGreekLetter(t) + "\"");
		logger.info("isSymbol(\"" + t + "\") = \"" + Orthographic.isSymbol(t) + "\"");
		logger.info("isNumeric(\"" + t + "\") = \"" + Orthographic.isNumeric(t) + "\"");
		logger.info("isSymbol(\"" + t + "\") = \"" + Orthographic.isSymbol(t) + "\"");
		logger.info("isNumeric(\"" + t + "\") = \"" + Orthographic.isNumeric(t) + "\"");

		logger.info("allDigits(\"" + t + "\") = \"" + Orthographic.allDigits(t) + "\"");
		logger.info("allConsonants(\"" + t + "\") = \"" + Orthographic.allConsonants(t) + "\"");
		logger.info("allVowels(\"" + t + "\") = \"" + Orthographic.allVowels(t) + "\"");

		logger.info("containsSymbols(\"" + t + "\") = \"" + Orthographic.containsSymbols(t) + "\"");
		logger.info("containsGreekLetter(\"" + t + "\") = \"" + Orthographic.containsGreekLetter(t) + "\"");
		logger.info("containsDash(\"" + t + "\") = \"" + Orthographic.containsDash(t) + "\"");

		logger.info("containsDigits(\"" + t + "\") = \"" + Orthographic.containsDigits(t) + "\"");
		logger.info("isAcronym(\"" + t + "\") = \"" + Orthographic.isAcronym(t) + "\"");
		logger.info("isPercentage(\"" + t + "\") = \"" + Orthographic.isPercentage(t) + "\"");
		logger.info("isRealNumber(\"" + t + "\") = \"" + Orthographic.isRealNumber(t) + "\"");
		logger.info("isCapAndCap(\"" + t + "\") = \"" + Orthographic.isCapAndCap(t) + "\"");

		Object[] pre = Orthographic.prefixes(t, 2, 4);
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<pre.length;i++)
			sb.append(pre[i] + " ");
		logger.info("prefixes(\"" + t + "\") = " + sb.toString());

		Object[] suf = Orthographic.suffixes(t, 2, 4);
		StringBuffer ss = new StringBuffer();
		for (int i=0;i<suf.length;i++)
			ss.append(suf[i] + " ");
		logger.info("suffixes(\"" + t + "\") = " + ss.toString());

		Object[] ngram = Orthographic.characterNGram(t, 2, 4);
		StringBuffer sn = new StringBuffer();
		for (int i=0;i<ngram.length;i++)
			sn.append(ngram[i] + " ");
		logger.info("ngram(\"" + t + "\") = " + sn.toString());

	} // end test

	//
	public static final Object[] infix(String s)
	{
		//logger.debug("Orthographic.infix: \"" + s + "\"");
		List list = new ArrayList();

		for (int i=0;i<s.length();i++)
		{
			for (int j=2;j<=5;j++)
			{
				int e = i + j;

				if (e > s.length())
					e = s.length();

				list.add(s.substring(i, e));
			} // end for j
		} // end for i

		return list.toArray();
	} // end infix


	//
	public static final Object[] split(String s)
	{
		//logger.debug("Orthographic.split: \"" + s + "\"");
		List list = new ArrayList();

		if (s.length() < 2)
			return null;

		StringBuffer sb = new StringBuffer();

		char x = ' ', y = ' ';
		int a = 0, b = 0;

		for (int i=0;i<s.length()-1;i++)
		{
			x = s.charAt(i);
			y = s.charAt(i+1);
			a = Character.getType(x);
			b = Character.getType(y);

			//logger.debug("x) ch(" + i + ") = '" + x + "', type('" + x + "') = \"" + a + "\"");
			//logger.debug("y) ch(" + (i + 1) + ") = '" + y + "', type('" + y + "') = \"" + b + "\"");

			if (a != Character.DASH_PUNCTUATION)
			/*&&
					 a != Character.OTHER_PUNCTUATION &&
					 a != Character.CONNECTOR_PUNCTUATION &&
					 a != Character.START_PUNCTUATION &&
					 a != Character.END_PUNCTUATION)*/
				sb.append(x);

			if (i != 0 || a != Character.UPPERCASE_LETTER || b != Character.LOWERCASE_LETTER)
			{
				if (a != b)
				{
					if (sb.length() > 0)
						list.add(sb.toString());
					//logger.debug("add " + sb);
					sb = new StringBuffer();
				}
			}
		}

		if (b != Character.DASH_PUNCTUATION)
			sb.append(y);

		if (sb.length() > 0)
			list.add(sb.toString());

		//logger.debug("add " + sb);
		/*
		Iterator it = list.iterator();
		while (it.hasNext())
			logger.debug(it.next());
		*/

		if (list.size() > 1)
			return list.toArray();

		return null;
	} // end split

	//
	public static void main(String args[]) throws Exception
	{
		

		if (args.length != 1)
		{
			System.err.println("Wrong number of parameters " + args.length);
			System.err.println("Usage: java org.itc.irst.tcc.ker.data.Orthographic token");
			System.exit(-1);
		}

		String t = args[0];

		Orthographic.test(t);
	/*
		Object[] o = Orthographic.split(t);

		if (o != null)
		{
			for (int i=0;i<o.length;i++)
				Orthographic.test((String) o[i]);
		}
	*/
	//for (int i=0;i<t.length();i++)
	//	logger.debug("ch(" + i + ") = '" + t.charAt(i) + "', type('" + t.charAt(i) + "') = \"" + Character.getType(t.charAt(i)) + "\"");


	} // end main


} // end class Orthographic
