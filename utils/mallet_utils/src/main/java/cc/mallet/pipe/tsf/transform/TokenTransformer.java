package cc.mallet.pipe.tsf.transform;

public interface TokenTransformer {

	/**
	 * Transform given token to arbitrary String representation.
	 * 
	 * Seen as a binary feature generator, the TokenTransformer
	 * _rejects_ a token by returning null, and _accepts_ it
	 * by returning a non-null String object.
	 * 
	 * The final String representation is arbitrary and defined
	 * by the implementing TokenTransformer.
	 * See {@link CommonNerTokenTransformers} for examples.
	 * 
	 * @param token token as String, its text, to transform
	 * @return String representation of token after transformation
	 * 	or null if token is rejected. 
	 */
	public String transform(String token);

}
