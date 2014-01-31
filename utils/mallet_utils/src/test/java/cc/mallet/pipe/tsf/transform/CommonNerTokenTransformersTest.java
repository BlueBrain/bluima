package cc.mallet.pipe.tsf.transform;

import org.junit.Test;
import static org.junit.Assert.*;

import cc.mallet.pipe.tsf.transform.CommonNerTokenTransformers;
import cc.mallet.pipe.tsf.transform.TokenTransformer;

public class CommonNerTokenTransformersTest {

	@Test
	public void testLowerCaseTransformer() {
		TokenTransformer transformer = CommonNerTokenTransformers.getLowerCaseTransformer();
		
		assertEquals("", transformer.transform(""));
		assertEquals("miau", transformer.transform("miau"));
		assertEquals("miau", transformer.transform("MIAU"));
		
		assertEquals("a", transformer.transform("A"));
		assertEquals("aaaaa", transformer.transform("AaAaa"));
		assertEquals("aa", transformer.transform("aA"));
	}
	
	@Test
	public void testMorphologyUpperLowerNumberTransformer() {
		TokenTransformer transformer = CommonNerTokenTransformers.getMorphologyUpperLowerNumberTransformer();
		
		assertEquals("", transformer.transform(""));
		assertEquals("aaaa", transformer.transform("miau"));
		assertEquals("AaAA", transformer.transform("GnRH"));
		assertEquals("a1", transformer.transform("s2"));
		assertEquals("aa11aaaa", transformer.transform("rp47phox"));
		assertEquals("A111", transformer.transform("U937"));
		assertEquals("A11a1", transformer.transform("U23k9"));
		assertEquals(".", transformer.transform("."));
	}
	
	@Test
	public void testMorphologyUpperLowerNumberNonContiguousTransformer() {
		TokenTransformer transformer = 
				CommonNerTokenTransformers.getMorphologyUpperLowerNumberNonContiguousTransformer();
		
		assertEquals("", transformer.transform(""));
		assertEquals("a", transformer.transform("miau"));
		assertEquals("AaA", transformer.transform("GnRH"));
		assertEquals("a1", transformer.transform("s2"));
		assertEquals("a1a", transformer.transform("rp47phox"));
		assertEquals("A1", transformer.transform("U937"));
		assertEquals("A1a1", transformer.transform("U23k9"));
		assertEquals(".", transformer.transform("."));
	}
	
	@Test
	public void testMorphologyContiguousNumbersGroupedOrRejectedTransformer() {
		TokenTransformer transformer = 
				CommonNerTokenTransformers.getMorphologyContiguousNumbersGroupedOrRejectedTransformer();
		
		assertEquals(null, transformer.transform(""));
		assertEquals(null, transformer.transform("miau"));
		assertEquals(null, transformer.transform("GnRH"));
		assertEquals("s*", transformer.transform("s2"));
		assertEquals("rp*phox", transformer.transform("rp47phox"));
		assertEquals("u*", transformer.transform("U937"));
		assertEquals("u*k*", transformer.transform("U23k9"));
		assertEquals(null, transformer.transform("."));
	}
	
	@Test
	public void testEnglishVowelsTransformer() {
		TokenTransformer transformer = 
				CommonNerTokenTransformers.getEnglishVowelsTransformer();
		
		assertEquals("", transformer.transform(""));
		assertEquals("*iau", transformer.transform("miau"));
		assertEquals("****", transformer.transform("GnRH"));
		assertEquals("**", transformer.transform("s2"));
		assertEquals("******o*", transformer.transform("rp47phox"));
		assertEquals("U***", transformer.transform("U937"));
		assertEquals("U****", transformer.transform("U23k9"));
		assertEquals("*", transformer.transform("."));
	}
	
	

}
