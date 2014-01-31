/**
 * This class is meant to count the number of characters that follow a certain pattern
 * and find the ratio of that number with the total number of characters in the text line.
 * This ratio is then compared to a certain threshold to result in a +1 or 0 feature value.
 * 
 * You use this ratio to differentiate between text lines types.
 * e.g: a table text line will have more blank space than a normal text line.
 * 
 * @author Samuel LT'E Kimoto
 */
package ch.epfl.bbp.nlp.tables.pipes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class PatternRatioPipe extends Pipe implements Serializable{
	Pattern regex;
	String feature;
	double threshold;
	
	public PatternRatioPipe (String featureName, Pattern regex, double threshold)
	{
		this.feature = featureName;
		this.regex = regex;
		this.threshold = threshold;
	}

	// Too dangerous with both arguments having the same type
	//public RegexMatches (String regex, String feature) {
	//this (Pattern.compile (regex), feature);
  //}
	

	public Instance pipe (Instance carrier)
	{
		TokenSequence ts = (TokenSequence) carrier.getData();
		int count = 0;
		
		for (int i = 0; i < ts.size(); i++) {
			Token t = ts.get(i);
			String s = t.getText();
			String conS=s;
			count = 0;
			Matcher matcher = regex.matcher(s);
			while(matcher.find()){
				count = count + (matcher.end() - matcher.start()); 
			}
			if ((double)count/s.length() >= threshold){ 
				t.setFeatureValue (feature, 1.0);
			}
			
			if(conS.compareTo(s) != 0) {
				count = 0;
				matcher = regex.matcher(conS);
				while(matcher.find()){
					count = count + (matcher.end() - matcher.start()); 
				}
				if ((double)count/conS.length() >= threshold){ 
					t.setFeatureValue (feature, 1.0);
				}
			}
		}
		return carrier;
	}


	// Serialization 
	
	private static final long serialVersionUID = 1;
	private static final int CURRENT_SERIAL_VERSION = 0;
	
	private void writeObject (ObjectOutputStream out) throws IOException {
		out.writeInt(CURRENT_SERIAL_VERSION);
		out.writeObject(regex);
		out.writeObject(feature);
		out.writeDouble(threshold);
	}
	
	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
		int version = in.readInt ();
		regex = (Pattern) in.readObject();
		feature = (String) in.readObject();
		threshold = in.readDouble();
	}

}
