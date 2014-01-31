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

/**
 * TO DO
 *
 * @author 	Claudio Giuliano
 * @version %I%, %G%
 * @since		1.0
 */
public class ZipModel
{
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>ZipModel</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(ZipModel.class.getName());

	//
	static final int BUFFER = 2048;

	//
	private ZipOutputStream out;

	//
	public ZipModel(File f) throws IOException
	{
		FileOutputStream dest = new FileOutputStream(f);
		out = new ZipOutputStream(new BufferedOutputStream(dest));
	} // end constructor

	//
	public void close() throws IOException
	{
		out.close();
	} // end close

	//
	public void add(File f) throws IOException
	{
		logger.debug(f.getName() + " added to the zip model (" + f + ")");

		byte data[] = new byte[BUFFER];

		BufferedInputStream origin = new BufferedInputStream(new FileInputStream(f), BUFFER);
		ZipEntry entry = new ZipEntry(f.getName());
		out.putNextEntry(entry);
		int count;

		while((count = origin.read(data, 0, BUFFER)) != -1)
 			out.write(data, 0, count);

		out.closeEntry();
		origin.close();
	} // end add

	//
	public void add(File f, String desc) throws IOException
	{
		logger.debug(desc + " added to the zip model (" + f + ")");

		byte data[] = new byte[BUFFER];

		BufferedInputStream origin = new BufferedInputStream(new FileInputStream(f), BUFFER);
		ZipEntry entry = new ZipEntry(desc);
		out.putNextEntry(entry);
		int count;

		while((count = origin.read(data, 0, BUFFER)) != -1)
 			out.write(data, 0, count);

		out.closeEntry();
		origin.close();
	} // end add

	//
	public static void write(File zip, File[] files) throws IOException
	{
		FileOutputStream dest = new FileOutputStream(zip);
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
		//out.setMethod(ZipOutputStream.DEFLATED);
		byte data[] = new byte[BUFFER];

		for (int i=0; i<files.length; i++)
		{
			logger.debug("Adding: "+files[i]);
			BufferedInputStream origin = new BufferedInputStream(new FileInputStream(files[i]), BUFFER);
      ZipEntry entry = new ZipEntry(files[i].getName());
			out.putNextEntry(entry);
			int count;

			while((count = origin.read(data, 0, BUFFER)) != -1)
 				out.write(data, 0, count);

			origin.close();
     }

		out.close();
   } // end write

} // end class
