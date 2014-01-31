/** 
 *		This class is meant add a feature of value one to the first line of a PDF page.
 *		This is done so because the first line is most of the time a header.
 *		This measure effectively isolates the header.
 *
 *		@author Samuel LT'E Kimoto
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

public class FirstLinePipe extends Pipe implements Serializable {
	String feature;
	
	public FirstLinePipe (String featureName)
	{
		this.feature = featureName;
	}
	
	public Instance pipe (Instance carrier)
	{
		TokenSequence ts = (TokenSequence) carrier.getData();
		int count = 0;
		
		for (int i = 0; i < ts.size(); i++) {
			Token t = ts.get(i);
			
			//First Line
			if(i==0){
				
				t.setFeatureValue(feature, 1);
			}
		}
		return carrier;
	}


	// Serialization 
	
	private static final long serialVersionUID = 1;
	private static final int CURRENT_SERIAL_VERSION = 0;
	
	private void writeObject (ObjectOutputStream out) throws IOException {
		out.writeInt(CURRENT_SERIAL_VERSION);
		out.writeObject(feature);
	}
	
	private void readObject (ObjectInputStream in) throws IOException, ClassNotFoundException {
		int version = in.readInt ();
		feature = (String) in.readObject();
	}
}
