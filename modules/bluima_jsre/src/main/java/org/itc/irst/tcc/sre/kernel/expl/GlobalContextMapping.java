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
 package org.itc.irst.tcc.sre.kernel.expl;

import java.util.Properties;

import org.itc.irst.tcc.sre.data.Sentence;
import org.itc.irst.tcc.sre.data.context.BetweenAfterContext;
import org.itc.irst.tcc.sre.data.context.BetweenContext;
import org.itc.irst.tcc.sre.data.context.ForeBetweenContext;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.SparseVector;
import org.itc.irst.tcc.sre.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class GlobalContextMapping extends AbstractMapping implements ContextMapping
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>GlobalContextMapping</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(GlobalContextMapping.class.getName());

	//
	private int ngram;

	//
	private static final int NUMBER_OF_SUBSPACES = 3;

	//
	public static final int DEFAULT_NGRAM = 3;

	//
	private static final int FORE_BETWEEN_SPACE = 0;

	//
	private static final int BETWEEN_SPACE = 1;

	//
	private static final int BETWEEN_AFTER_SPACE = 2;

	//
	private ForeBetweenContext foreBetweenContext;

	//
	private BetweenContext betweenContext;

	//
	private BetweenAfterContext betweenAfterContext;

	//
	public GlobalContextMapping()
	{
		logger.debug("GlobalContextMapping");

		this.ngram = DEFAULT_NGRAM;

		foreBetweenContext = ForeBetweenContext.getInstance();
		betweenContext = BetweenContext.getInstance();
		betweenAfterContext = BetweenAfterContext.getInstance();

		//logger.debug("GlobalContextMapping");
		//logger.debug("ngram: " + ngram);

	} // end constructor

	//
	public void setParameters(Properties parameters)
	{
		logger.debug("GlobalContextMapping.setParameters");
		String n = parameters.getProperty("n-gram");
		if (n != null)
				ngram = Integer.parseInt(n);

		logger.debug("n-gram: " + ngram);
	} // end setParameters

	//
	public int subspaceCount()
	{
		return NUMBER_OF_SUBSPACES;
	} // end subspaceCount

	//
	public Vector[] map(Object x, Object id, FeatureIndex[] index) throws IllegalArgumentException
	{
		//logger.debug("GlobalContextMapping.map");

		boolean b = (x instanceof Sentence);
		if (!b)
		{
			throw new IllegalArgumentException();
		}

		Sentence sent = (Sentence) x;
		Vector[] subspaces = new Vector[NUMBER_OF_SUBSPACES];

		// fore-between
		Sentence fore = foreBetweenContext.filter(sent);
		subspaces[FORE_BETWEEN_SPACE] = createSubspace(fore, index[FORE_BETWEEN_SPACE]);
		//subspaces[FORE_BETWEEN_SPACE] = new SparseVector();

		// between
		Sentence between = betweenContext.filter(sent);
		subspaces[BETWEEN_SPACE] = createSubspace(between, index[BETWEEN_SPACE]);

		// between-after
		Sentence after = betweenAfterContext.filter(sent);
		subspaces[BETWEEN_AFTER_SPACE] = createSubspace(after, index[BETWEEN_AFTER_SPACE]);
		//subspaces[BETWEEN_AFTER_SPACE] = new SparseVector();

		// normalize subspaces
		for (int j=0;j<NUMBER_OF_SUBSPACES;j++)
			subspaces[j].normalize();

		//
		return subspaces;
	} // end map

	//
	protected Vector createSubspace(Sentence sent, FeatureIndex index)
	{
		//logger.debug("createSubspace");
		Vector vec = new SparseVector();

		// unigram
		for (int i=0;i<sent.length();i++)
		{
			//String t = "WF:" + sent.wordAt(i).getForm() + "-" + sent.wordAt(i).getWPos();
			String t = sent.wordAt(i).getForm();
			updateVector(vec, index, t);

			//String p = sent.wordAt(i).getWPos();
			//updateVector(vec, index, p);
		} // end for i

		if (ngram > 1)
		{
			// bigram
			for (int i=0;i<sent.length()-1;i++)
			{
				//String t1 = sent.wordAt(i).getForm() + "-" + sent.wordAt(i).getWPos();
				//String t2 = sent.wordAt(i+1).getForm() + "-" + sent.wordAt(i).getWPos();
				String t1 = sent.wordAt(i).getForm();
				String t2 = sent.wordAt(i+1).getForm();
				String t = t1 + "_" + t2;
				updateVector(vec, index, t);

				//String p1 = sent.wordAt(i).getWPos();
				//String p2 = sent.wordAt(i+1).getWPos();
				//String p = p1 + "_" + p2;
				//updateVector(vec, index, p);

			} // end for i
		}

		if (ngram > 2)
		{
			// trigram
			for (int i=0;i<sent.length()-2;i++)
			{
				//String t1 = sent.wordAt(i).getForm() + "-" + sent.wordAt(i).getWPos();
				//String t2 = sent.wordAt(i+1).getForm() + "-" + sent.wordAt(i).getWPos();
				//String t3 = sent.wordAt(i+2).getForm() + "-" + sent.wordAt(i).getWPos();
				String t1 = sent.wordAt(i).getForm();
				String t2 = sent.wordAt(i+1).getForm();
				String t3 = sent.wordAt(i+2).getForm();
				String t = t1 + "_" + t2 + "_" + t3;

				updateVector(vec, index, t);

				//String p1 = sent.wordAt(i).getWPos();
				//String p2 = sent.wordAt(i+1).getWPos();
				//String p3 = sent.wordAt(i+2).getWPos();
				//String p = p1 + "_" + p2 + "_" + p3;
				//updateVector(vec, index, p);

				// sparse bigrams
				// add more precision
				//t = t1 + "_" + t3;
				//updateVector(vec, index, t);

			} // end for i
		}

		return  vec;
	} // end createSubspace

	//
	private void updateVector(Vector vec, FeatureIndex index, String t)
	{
		//logger.debug("updateVector");
		int j = index.put(t);
		//logger.debug(t + " " + j);

		// Roth and Semeval-re
		if (j != -1)
			vec.add(j, 1);

		// AImed
/*
		if (j != -1)
		{
			if (vec.existsIndex(j))
				vec.set(j, vec.get(j) + 1); // tf
			else
				vec.add(j, 1);
		}
*/
	} // end updateVector

	//
 	public String toString()
 	{
 		return "GlobalContextMapping";
 	} // end toString

} // end class GlobalContextMapping
