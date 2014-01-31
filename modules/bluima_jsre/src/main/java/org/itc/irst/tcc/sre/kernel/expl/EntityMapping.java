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

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import java.util.*;
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.Sentence;
import org.itc.irst.tcc.sre.data.SentenceSetCopy;
import org.itc.irst.tcc.sre.data.VectorSet;
import org.itc.irst.tcc.sre.data.Word;
import org.itc.irst.tcc.sre.data.context.TargetContext;
import org.itc.irst.tcc.sre.data.context.SecondTargetContext;
import org.itc.irst.tcc.sre.data.context.AgentContext;
import org.itc.irst.tcc.sre.util.Vector;
import org.itc.irst.tcc.sre.util.SparseVector;
import org.itc.irst.tcc.sre.util.FeatureIndex;
import org.itc.irst.tcc.sre.util.Orthographic;


/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class EntityMapping extends AbstractMapping implements ContextMapping
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>EntityMapping</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(EntityMapping.class.getName());

	//
	//private int relationType;

	//
	private int windowSize;

	//
	private static final int NUMBER_OF_SUBSPACES = 1;

	//
	private static final int TARGET_SPACE = 0;

	//
	private static final int AGENT_SPACE = 1;

	//
	private TargetContext targetContext;

	//
	private SecondTargetContext secondTargetContext;

	//
	private AgentContext agentContext;

	//

	public EntityMapping()
	{
		logger.debug("EntityMapping");

		//super(NUMBER_OF_SUBSPACES);
		//this.relationType = relationType;


		targetContext = TargetContext.getInstance();
		secondTargetContext = SecondTargetContext.getInstance();
		agentContext = AgentContext.getInstance();

	} // end constructor

	//
	public void setParameters(Properties parameters)
	{
		logger.debug("EntityMapping.setParameters");

	} // end setParameters

	//
	public int subspaceCount()
	{
		return NUMBER_OF_SUBSPACES;
	} // end subspaceCount

	//
	public Vector[] map(Object x, Object id, FeatureIndex[] index) throws IllegalArgumentException
	{
		//logger.debug("EntityMapping.map");

		boolean b = (x instanceof Sentence);
		if (!b)
		{
			throw new IllegalArgumentException();
		}

		Sentence sent = (Sentence) x;
		Vector[] subspaces = new SparseVector[NUMBER_OF_SUBSPACES];

		// local context

		Word target = null, agent = null;

		target = targetContext.word(sent);
		Word  secondTarget = secondTargetContext.word(sent);

		if (secondTarget == null)
			agent = agentContext.word(sent);
		else
			agent = secondTarget;

		//subspaces[TARGET_SPACE] = createSubspace(target, index[TARGET_SPACE]);
		//subspaces[AGENT_SPACE] = createSubspace(agent, index[AGENT_SPACE]);

		subspaces[TARGET_SPACE] = new SparseVector();
		createSubspace(subspaces[TARGET_SPACE], target, index[TARGET_SPACE], "target");
		createSubspace(subspaces[TARGET_SPACE], agent, index[TARGET_SPACE], "agent");

		createSubspace(subspaces[TARGET_SPACE], target, agent, index[TARGET_SPACE]);

		// normalize
		for (int j=0;j<NUMBER_OF_SUBSPACES;j++)
			subspaces[j].normalize();

		//
		return subspaces;
	} // end map

	//
	protected Vector createSubspace(Word w, FeatureIndex index)
	{
		Vector vec = new SparseVector();
		//logger.debug("w: " + w);

		String attr = w.getLemma();
		String[] s = attr.split("_");

		// head
		addFeature(s[0] + "_HEAD", vec, index);

		//
		addFeature(s[1] + "_MENTION_TYPE", vec, index);

		//
		addFeature(s[2] + "_ENTITY_TYPE", vec, index);

		//
		addFeature(s[3] + "_ENTITY_SUBTYPE", vec, index);

		//
		if (!s[4].equals("null"))
			addFeature(s[4] + "_ROLE", vec, index);
		return vec;
	} // end createSubspace

	//
	protected void createSubspace(Vector vec , Word w, FeatureIndex index, String prefix)
	{
		//Vector vec = new SparseVector();
		//logger.debug("w: " + w);

		String attr = w.getLemma();
		String[] s = attr.split("_");

		// head
		addFeature(prefix + "_" + s[0] + "_HEAD", vec, index);

		//
		addFeature(prefix + "_" + s[1] + "_MENTION_TYPE", vec, index);

		//
		addFeature(prefix + "_" + s[2] + "_ENTITY_TYPE", vec, index);

		//
		addFeature(prefix + "_" + s[3] + "_ENTITY_SUBTYPE", vec, index);

		//
		if (!s[4].equals("null"))
			addFeature(prefix + "_" + s[4] + "_ROLE", vec, index);
		//return vec;
	} // end createSubspace

	//
	protected void createSubspace(Vector vec , Word wt, Word wa, FeatureIndex index)
	{
		//Vector vec = new SparseVector();
		//logger.debug("w: " + w);

		String[] l = {"H", "MT", "ET", "EST", "R"};
		String[] s = wt.getLemma().split("_");
		String[] t = wa.getLemma().split("_");

		for (int i=0;i<s.length;i++)
		{
			for (int j=0;j<t.length;j++)
			{
				// head
				if (!s[i].equals("null") && !t[j].equals("null"))
				{
					addFeature(s[i] + "_" + t[j] , vec, index);
					//logger.info(s[i] + "_" + t[j]);

				}
			}
		}
	} // end createSubspace

	//
	protected Vector createSubspace(Word wt, Word wa, FeatureIndex index)
	{
		Vector vec = new SparseVector();

		String[] s = wt.getLemma().split("_");
		String[] t = wa.getLemma().split("_");

		for (int i=0;i<s.length;i++)
		{
			for (int j=0;j<t.length;j++)
			{
				// head
				addFeature(s[i] + "_" + t[j] , vec, index);

			}
		}
		return vec;
	} // end createSubspace


	//
	protected void addFeature(String feat, Vector vec, FeatureIndex index)
	{
		//logger.info("feat: " + feat);

		int j = index.put(feat);

		if (j != -1)
		{
			//logger.info(feat + " " + j);
			vec.add(j, 1);
		}


		//logger.debug("added feat: " + f);
	} // end addFeature

	//
	protected int findTargetOffset(Sentence sent)
	{
		for (int i=0;i<sent.length();i++)
		{
			if (sent.wordAt(i).getRole().equals(Word.TARGET_LABEL) ||
				sent.wordAt(i).getRole().equals(Word.AGENT_LABEL))
					return i;
		} // end for i

		return -1;
	} // end findTargetOffset

	//
 	public String toString()
 	{
 		return "EntityMapping";
 	} // end toString

} // end class EntityMapping
