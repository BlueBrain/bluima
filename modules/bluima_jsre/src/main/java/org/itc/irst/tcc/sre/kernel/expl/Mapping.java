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
import org.itc.irst.tcc.sre.data.ExampleSet;
import org.itc.irst.tcc.sre.data.VectorSet;
import org.itc.irst.tcc.sre.util.FeatureIndex;

/**
 * An object that maps an example set into a vectorial
 * feature space.
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public interface Mapping
{
	/**
	 * Set the parameters to the mapping.
	 *
	 * @param parameters	the specified parameters
	 */
	public abstract void setParameters(Properties parameters);

	/**
	 * Maps the specified example set into a feature space.
	 *
	 * @param data	input example set.
	 * @param index	array of indexes of the subspaces.
	 * @return 	a vectorial feature space.
	 * @throws IllegalArgumentException if the the specified example set is illegal or inappropriate.
	 */
	public abstract VectorSet map(ExampleSet data, FeatureIndex[] index) throws IllegalArgumentException;

	/**
	 * Returns the number of active subspaces in 
	 * this mapping.
	 *
	 * @return the number of active subspaces.
	 */
	public abstract int subspaceCount();

} // end interface Mapping