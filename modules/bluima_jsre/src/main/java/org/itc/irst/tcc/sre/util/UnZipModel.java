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

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.zip.*;
import java.util.*;

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class UnZipModel
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>UnZipModel</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(UnZipModel.class.getName());

	//
	static final int BUFFER = 2048;

	//
	private ZipInputStream in;

	//
	private Map map;

	//
	public UnZipModel(File f) throws IOException
	{
		FileInputStream src = new FileInputStream(f);
		in = new ZipInputStream(new BufferedInputStream(src));
		map = new HashMap();
		extract();
	} // end constructor

	//
	private void extract() throws IOException
	{
		logger.debug("unzip model files");

		ZipEntry entry;
		while((entry = in.getNextEntry()) != null)
		{
			logger.debug("extracting: " + entry);
			int count;
			byte data[] = new byte[BUFFER];


			String name = entry.getName();
			File tmp = File.createTempFile(name, null);
			tmp.deleteOnExit();

			map.put(name, tmp);

			// write the tmp file to the disk
			FileOutputStream fos = new FileOutputStream(tmp);
			BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);

			while ((count = in.read(data, 0, BUFFER)) != -1)
				dest.write(data, 0, count);

			dest.flush();
			dest.close();
			in.closeEntry();

		} // end while

		in.close();
	} // end extract

	//
	public File get(String desc)
	{
		return (File) map.get(desc);
	} // end get

	//
	public Set list()
	{
		return map.keySet();
	} // end list

} // end class UnZipModel
