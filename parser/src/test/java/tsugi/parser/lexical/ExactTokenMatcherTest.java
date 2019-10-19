package tsugi.parser.lexical;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExactTokenMatcherTest {

	
	@Test
	public void test() {
		var matcher = new ExactTokenMatcher(null, "abc");
		
		assertTrue(matcher.willAccept('a'));
		matcher.add('a');
		assertFalse(matcher.isComplete());
		
		assertTrue(matcher.willAccept('b'));
		matcher.add('b');
		assertFalse(matcher.isComplete());
		
		assertTrue(matcher.willAccept('c'));
		matcher.add('c');
		assertTrue(matcher.isComplete());
		
		assertFalse(matcher.willAccept('d'));
		assertThrows(IllegalArgumentException.class, () -> matcher.add('d'));
	}
}
