package tsugi.parser.lexical;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import tsugi.parser.exception.UnexpectedEndOfFileException;
import static tsugi.parser.lexical.TokenType.*;

import static org.junit.jupiter.api.Assertions.*;

public class LexicalScannerTest {

	@Test
	public void test_parenthesisOnly() {
		var in = new ByteArrayInputStream(" (  ()( )   )()   ".getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScanner(in);
		
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		
		assertThrows(UnexpectedEndOfFileException.class, scanner::next);

	}
	
	@Test
	public void test_parensAndNewLines() {
		var in = new ByteArrayInputStream(" (  (\n)( ) \n  )\r\n()   \n".getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScanner(in);
		
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.NEW_LINE, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		assertEquals(TokenType.NEW_LINE, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		assertEquals(TokenType.NEW_LINE, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
		assertEquals(TokenType.NEW_LINE, scanner.next().getType());
		assertThrows(UnexpectedEndOfFileException.class, scanner::next);

	}
	
	@Test
	public void test_stringParsing() {
		var in = new ByteArrayInputStream("( \"hello world! \\n \\\" \\t \" )".getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScanner(in);
		
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		var stringToken = scanner.next();
		assertEquals(TokenType.STRING, stringToken.getType());
		assertEquals("hello world! \n \" \t ", stringToken.getValue());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
	}
	
	@Test
	public void test_printHelloWorld() {
		var in = new ByteArrayInputStream("printLn(\"Hello World!\")".getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScanner(in);
		
		assertEquals(TokenType.IDENTIFIER, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.STRING, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
	}
	
	@Test
	public void test_assignmentWithVar() {
		var in = new ByteArrayInputStream("Integer abc = lookup(\"Hello World!\")".getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScanner(in);
		
		assertEquals(TokenType.IDENTIFIER, scanner.next().getType());
		assertEquals(TokenType.IDENTIFIER, scanner.next().getType());
		assertEquals(TokenType.ASSIGNMENT, scanner.next().getType());
		assertEquals(TokenType.IDENTIFIER, scanner.next().getType());
		assertEquals(TokenType.LEFT_PAREN, scanner.next().getType());
		assertEquals(TokenType.STRING, scanner.next().getType());
		assertEquals(TokenType.RIGHT_PAREN, scanner.next().getType());
	}
	
	@Test
	public void test_ifStatementWithThen() {
		var in = new ByteArrayInputStream("if a == b || b<c then return variable".getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScanner(in);
		
		checkTypes(scanner, new TokenType[] {
				IF, IDENTIFIER, CMP_EQ, IDENTIFIER, OR, IDENTIFIER, CMP_LT, IDENTIFIER, THEN, RETURN, IDENTIFIER
		});
	
	}
	
	private void checkTypes(LexicalScanner scanner, TokenType[] types) {
		for(TokenType type : types)
			assertEquals(type, scanner.next().getType());
		assertThrows(UnexpectedEndOfFileException.class, () -> scanner.next());
	}
}
