///////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2001 Jason Baldridge and Gann Bierner
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
//////////////////////////////////////////////////////////////////////////////   
package ch.epfl.bbp.shaded.opennlp.maxent;

import java.io.*;

import ch.epfl.bbp.shaded.opennlp.maxent.io.*;

/**
 * Trains or evaluates maxent components which have implemented the Evalable
 * interface.
 *
 * @author      Gann Bierner
 * @version     $Revision: 1.4 $, $Date: 2003/04/05 13:33:38 $
 */
public class TrainEval {
    
//    public static void eval(MaxentModel model, Reader r, Evalable e) {
//	eval(model, r, e, false);
//    }
//
//    public static void eval(MaxentModel model, Reader r,
//			    Evalable e, boolean verbose) {
//
//	float totPos=0, truePos=0, falsePos=0;
//	Event[] events = (e.getEventCollector(r)).getEvents(true);
//	//MaxentModel model = e.getModel(dir, name);
//	String negOutcome = e.getNegativeOutcome();
//	for(int i=0; i<events.length; i++) {
//	    String guess =
//		model.getBestOutcome(model.eval(events[i].getContext()));
//	    String ans = events[i].getOutcome();
//	    if(verbose)
//		System.out.println(ans + " " + guess);
//	    if(!ans.equals(negOutcome)) totPos++;
//	    if(!guess.equals(negOutcome) && !guess.equals(ans))
//		falsePos++;
//	    else if(ans.equals(guess))
//		truePos++;
//	}
//	
//	System.out.println("Precision: " + truePos/(truePos+falsePos));
//	System.out.println("Recall:    " + truePos/totPos);
//	
//    }
//
//    public static MaxentModel train(EventStream events, int cutoff) {
//	return GIS.trainModel(events, 100, cutoff);
//    }
//
//    public static void run(String[] args, Evalable e) throws IOException {
//	String dir = "./";
//	String stem = "maxent";
//	int cutoff = 0; // default to no cutoff
//	boolean train = false;
//	boolean verbose = false;
//	boolean local = false;
//	gnu.getopt.Getopt g =
//	    new gnu.getopt.Getopt("maxent", args, "d:s:c:tvl");
//	int c;
//	while ((c = g.getopt()) != -1) {
//	    switch(c) {
//	    case 'd':
//		dir = g.getOptarg()+"/";
//		break;
//	    case 's':
//		stem = g.getOptarg();
//		break;
//	    case 'c':
//		cutoff = Integer.parseInt(g.getOptarg());
//		break;
//	    case 't':
//		train = true;
//		break;
//	    case 'l':
//		local = true;
//		break;
//	    case 'v':
//		verbose = true;
//		break;
//	    }
//	}
//
//	int lastIndex = g.getOptind();
//	if (lastIndex >= args.length) {
//	    System.out.println("This is a usage message from opennlp.maxent.TrainEval. You have called the training procedure for a maxent application with the incorrect arguments.  These are the options:");
//
//	    System.out.println("\nOptions for defining the model location and name:");
//	    System.out.println(" -d <directoryName>");
//	    System.out.println("\tThe directory in which to store the model.");
//	    System.out.println(" -s <modelName>");
//	    System.out.println("\tThe name of the model, e.g. EnglishPOS.bin.gz or NameFinder.txt.");
//	    
//	    System.out.println("\nOptions for training:");
//	    System.out.println(" -c <cutoff>");
//	    System.out.println("\tAn integer cutoff level to reduce infrequent contextual predicates.");
//	    System.out.println(" -t\tTrain a model. If absent, the given model will be loaded and evaluated.");
//	    System.out.println("\nOptions for evaluation:");
//	    System.out.println(" -l\t the evaluation method of class that uses the model. If absent, TrainEval's eval method is used.");
//	    System.out.println(" -v\t verbose.");
//	    System.out.println("\nThe final argument is the data file to be loaded and used for either training or evaluation.");
//	    System.out.println("\nAs an example for training:\n java opennlp.grok.preprocess.postag.POSTaggerME -t -d ./ -s EnglishPOS.bin.gz -c 7 postag.data");
//	    System.exit(0);
//	}
//
//	FileReader datafr = new FileReader(args[lastIndex]);
//	
//	if (train) {
//	    MaxentModel m =
//		train(new EventCollectorAsStream(e.getEventCollector(datafr)),
//		      cutoff);
//	    new SuffixSensitiveGISModelWriter((GISModel)m,
//					      new File(dir+stem)).persist();
//	}
//	else {
//	    MaxentModel model =
//		new SuffixSensitiveGISModelReader(new File(dir+stem)).getModel();
//	    if (local) {
//		e.localEval(model, datafr, e, verbose);
//	    } else {
//		eval(model, datafr, e, verbose);
//	    }
//	}
//    }

}
