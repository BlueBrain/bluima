package cc.mallet.pipe.tsf.transform;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

/**
 * Produce feature after given TokenTransformer.
 * 
 * Binary feature.
 * 
 * {@link TokenTransformer} may reject the given token by returning null.
 * In this case no feature is produced.
 * 
 * Otherwise the produced feature is exactly: (featureName + transform)
 * where transform is the transformation produced by TokenTransformer
 * 
 * @author jmcejuela
 *
 */
public class TokenTransform extends Pipe {

	final String featureName;
	final TokenTransformer tokenTransformer;
	
	private static final long serialVersionUID = 3777436246217136679L;	
	
	
	public TokenTransform(String featureName, TokenTransformer tokenTransformer) {
		this.featureName = featureName;
		this.tokenTransformer = tokenTransformer;
	}
	
	public Instance pipe (Instance carrier) {
		TokenSequence ts = (TokenSequence) carrier.getData();
		for (int i = 0; i < ts.size(); i++) {
			Token token = ts.get(i);
			String s = token.getText();			
			String transform = tokenTransformer.transform(s);
			if (null != transform)
				token.setFeatureValue((featureName + transform), 1.0);
		}
		return carrier;
	}

}
