package ch.epfl.bbp.nlp.tables.pipes;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;
import java.io.IOException;

/**
 * Counts the number of columns in a text line according to a given pattern and
 * add a feature of value one if the number of column is larger than a given
 * threshold. This feature was created, because a table text line would likely
 * have at least two columns.
 * 
 * @author Samuel LT'E Kimoto
 */
public class CellsPresencePipe extends Pipe implements Serializable {
    Pattern regex;
    String feature;
    int threshold;

    public CellsPresencePipe(String featureName, Pattern regex, int threshold) {
        this.threshold = threshold;
        this.feature = featureName;
        this.regex = regex;
    }

    public Instance pipe(Instance carrier) {
        TokenSequence ts = (TokenSequence) carrier.getData();
        int count = 0;

        for (int i = 0; i < ts.size(); i++) {
            Token t = ts.get(i);
            String s = t.getText();
            String conS = s;
            count = 0;
            Matcher matcher = regex.matcher(s);
            while (matcher.find()) {
                count++;
            }
            if (count >= threshold) {
                t.setFeatureValue(feature, 1.0);
            }

            if (conS.compareTo(s) != 0) {
                count = 0;
                matcher = regex.matcher(conS);
                while (matcher.find()) {
                    count++;
                }
                if (count >= threshold) {
                    t.setFeatureValue(feature, 1.0);
                }
            }
        }
        return carrier;
    }

    // Serialization

    private static final long serialVersionUID = 1;
    private static final int CURRENT_SERIAL_VERSION = 0;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(CURRENT_SERIAL_VERSION);
        out.writeObject(regex);
        out.writeObject(feature);
        out.writeInt(threshold);
    }

    private void readObject(ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        int version = in.readInt();
        regex = (Pattern) in.readObject();
        feature = (String) in.readObject();
        threshold = in.readInt();
    }
}
