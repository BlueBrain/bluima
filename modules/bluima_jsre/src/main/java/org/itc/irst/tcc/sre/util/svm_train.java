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
import libsvm.*;
import java.io.*;
import java.util.*;

/**
 * TO DO
 *
 * @author 	unknown
 * @version %I%, %G%
 * @since		1.0
 */
public class svm_train {
	/**
	 * Define a static logger variable so that it references the
	 * Logger instance named <code>svm_train</code>.
	 */
	static Logger logger = LoggerFactory.getLogger(svm_train.class.getName());

	private svm_parameter param;		// set by parse_command_line
	private svm_problem prob;		// set by read_problem
	private svm_model model;
	private String input_file_name;		// set by parse_command_line
	private String model_file_name;		// set by parse_command_line
	private String error_msg;
	private int cross_validation;
	private int nr_fold;

	// my code
	public void run(File input_file, File model_file, double c, int mem, double[] weight) throws IOException
	{
		input_file_name = input_file.getAbsolutePath();
		model_file_name = model_file.getAbsolutePath();

		//System.out.println("input_file_name: " + input_file_name);
		//System.out.println("model_file_name: " + model_file_name);
		//System.out.println("mem: " + mem);

		set_param(c, mem, weight);
		read_problem();
		error_msg = svm.svm_check_parameter(prob,param);

		if(error_msg != null)
		{
			System.err.print("Error: "+error_msg+"\n");
			System.exit(1);
		}

		if(cross_validation != 0)
		{
			//do_cross_validation();
		}
		else
		{
			model = svm.svm_train(prob,param);
			svm.svm_save_model(model_file_name, model);
		}
	}

	private static double atof(String s)
	{
		return Double.valueOf(s).doubleValue();
	}

	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}

	// read in a problem (in svmlight format)
	private void read_problem() throws IOException
	{
		BufferedReader fp = new BufferedReader(new FileReader(input_file_name));
		java.util.Vector vy = new java.util.Vector();
		java.util.Vector vx = new java.util.Vector();
		int max_index = 0;

		while(true)
		{
			String line = fp.readLine();
			if(line == null) break;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			vy.addElement(st.nextToken());
			int m = st.countTokens()/2;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}
			if(m>0) max_index = Math.max(max_index, x[m-1].index);
			vx.addElement(x);
		}

		prob = new svm_problem();
		prob.l = vy.size();
		prob.x = new svm_node[prob.l][];
		for(int i=0;i<prob.l;i++)
			prob.x[i] = (svm_node[])vx.elementAt(i);
		prob.y = new double[prob.l];
		for(int i=0;i<prob.l;i++)
			prob.y[i] = atof((String)vy.elementAt(i));

		if(param.gamma == 0)
			param.gamma = 1.0/max_index;

		fp.close();
	}

	// my code
	private void set_param(double c, int mem, double[] weight)
	{
		//logger.debug("set_param: " + weight.length);

		param = new svm_parameter();
		// default values
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.LINEAR;
		param.degree = 3;
		param.gamma = 0;	// 1/k
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = mem;

		//
		//param.C = 1;
		param.C = c;
		logger.info("C = " + param.C);

		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 1;
		param.nr_weight = 0;

		for (int i=0;i<weight.length;i++)
		{
			if (weight[i] != 0)
					param.nr_weight++;
		} // for i

		//param.nr_weight = weight.length - 1;
		param.weight = new double[param.nr_weight];
		param.weight_label = new int[param.nr_weight];
		int j = 0;
		for (int i=0;i<weight.length;i++)
		{
			if (weight[i] != 0)
			{
				param.weight[j] = weight[i];
				param.weight_label[j] = i;
				logger.debug("weight[" + param.weight_label[j] + "] = " + param.weight[j]);
				j++;
			}
		} // end for i

		/*
		for (int i=0;i<param.nr_weight;i++)
		{
			param.weight[i] = weight[i + 1];
			param.weight_label[i] = i + 1;
			logger.info("weight["+ param.weight_label[i] + "] = " + param.weight[i]);
		} // end for i
		*/

		// change here to weight the classes
		//param.nr_weight = 0;

		/*
		param.nr_weight = 2;
		int[] wl = {1, 2};
		double[] w = {4.0, 18.0};
		param.weight_label = wl;
		param.weight = w;

		for (int i=0;i<param.nr_weight;i++)
			System.out.println(i + ", " + param.weight_label[i] + " = " + param.weight[i]);
		*/

		//param.weight_label = weight_label;
		//param.weight = weight;


		cross_validation = 0;

	} // end param
}
