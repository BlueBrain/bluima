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
import java.util.Properties;

/**
 *
 * @author		Claudio Giuliano
 * @version 	1.0
 * @since			1.0
 */
public class MappingParameters
{
		/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>MappingParameters</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(MappingParameters.class.getName());

	//
	private String name;

	//
	private String className;

	//
	private Properties parameters;

	//
	public MappingParameters()
	{
		parameters = new Properties();
	} // end constructor

	//
	public void setName(String name)
	{
		//logger.debug("setName");
		this.name = name;
	} // end setName

	//
	public void setClassName(String className)
	{
		//logger.debug("setClassName");
		this.className = className;
	} // end setClassName

	//
	public void setParameters(String k, String v)
	{
		//logger.debug("setParameters: " + k + ", " + v);
		parameters.setProperty(k, v);
	} // end setParameters

	//
	public Properties getParameters()
 	{
 		return parameters;
 	} // end getParameters

 	//
	public String getName()
 	{
 		return name;
 	} // end getName

  //
	public String getClassName()
 	{
 		return className;
 	} // end getClassName

 	//
 	public String toString()
 	{
 		return name + ", " + className + ", " + parameters;
 	} // end toString
} // end class MappingParameters
