package tsugi.parser.lexical;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringTokenMatcherTest {

	
	@Test
	public void test_offer_emptyStringIsAString() {
		assertEquals(1, getLastSuccessIndex("\"\""));
	}
	
	@Test
	public void test_offer_WhiteSpaceAfterIsIgnored() {
		assertEquals(1, getLastSuccessIndex("\"\"   "));
	}
	
	@Test
	public void test_offer_stringsCanHaveLettersAndNumbers() {
		assertEquals(9, getLastSuccessIndex("\"just4fun\"+"));
	}
	
	@Test
	public void test_offer_stringsCanHaveSpaces() {
		assertEquals(11, getLastSuccessIndex("\"just 4 fun\"+"));
	}
	
	@Test
	public void test_offer_stringsHaveEscapedQuotes() {
		assertEquals(15, getLastSuccessIndex("\"just \\\"4\\\" fun\"+"));
	}
	
	@Test
	public void test_offer_notAString() {
		assertEquals(-1, getLastSuccessIndex("+\"just \\\"4\\\" fun\"+"));
	}
	
	private int getLastSuccessIndex(String s) {
		var checker = new StringTokenMatcher();
		int out = -1;
		
		for(int i=0; i<s.length(); i++) {
			if(checker.offer(s.charAt(i))) {
				out = i;
			}
		}
		
		return out;
	}
}
