package tsugi.parser.lexical;

import static tsugi.parser.lexical.TokenType.AND;
import static tsugi.parser.lexical.TokenType.ASSIGNMENT;
import static tsugi.parser.lexical.TokenType.CMP_EQ;
import static tsugi.parser.lexical.TokenType.IF;
import static tsugi.parser.lexical.TokenType.LEFT_PAREN;
import static tsugi.parser.lexical.TokenType.NEW_LINE;
import static tsugi.parser.lexical.TokenType.OR;
import static tsugi.parser.lexical.TokenType.RETURN;
import static tsugi.parser.lexical.TokenType.RIGHT_PAREN;
import static tsugi.parser.lexical.TokenType.THEN;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import tsugi.parser.exception.UnexpectedEndOfFileException;
import tsugi.parser.exception.UnexpectedTokenException;

public class LexicalScanner {
	
	private LineAwareCharacterScanner scanner;
	private TokenMatcher[] matchers;
	private int[] lastSuccessIndex;
	private boolean[] continueFlag;
	private int lastCol;
	private int lastLine;
	
	public LexicalScanner(InputStream is) {
		scanner = new LineAwareCharacterScanner(is);
		matchers = new TokenMatcher[] {
				new ExactTokenMatcher(LEFT_PAREN, "("),
				new ExactTokenMatcher(RIGHT_PAREN, ")"),
				new ExactTokenMatcher(NEW_LINE, "\n"),
				new ExactTokenMatcher(NEW_LINE, "\r\n"),
				new ExactTokenMatcher(OR, "||"),
				new ExactTokenMatcher(AND, "&&"),
				new ExactTokenMatcher(RETURN, "return"),
				new ExactTokenMatcher(THEN, "then"),
				new ExactTokenMatcher(IF,  "if"),
				new ExactTokenMatcher(ASSIGNMENT, "="),
				new ExactTokenMatcher(CMP_EQ,  "=="),
				new ExactTokenMatcher(TokenType.CMP_LT, "<"),
				new ExactTokenMatcher(TokenType.CMP_LE, "<="),
				new ExactTokenMatcher(TokenType.CMP_GT, ">"),
				new ExactTokenMatcher(TokenType.CMP_GE, ">="),
				new ExactTokenMatcher(TokenType.NOT, "!"),
				new ExactTokenMatcher(TokenType.CMP_NE, "!="),
				new StringTokenMatcher(),
				new IdentifierMatcher()
		};
		lastSuccessIndex = new int[matchers.length];
		continueFlag = new boolean[matchers.length];
		resetMatchers();
	}

	public Token next() {
		removeWhiteSpace();
		markPosition();
		resetMatchers();
		
		boolean hitEndOfFile = false;
		String partialToken = "";
		try {
			boolean continueChecking = true;
			while(continueChecking) {
				updateMatchers(scanner.peek());
				continueChecking = continueCheckingMatchers();
				if(continueChecking)
					partialToken += scanner.next();
			}			
		} catch(UnexpectedEndOfFileException ex) {
			// End of file was reached
			// Need to check if there is a match winner
			hitEndOfFile = true;
		}
		
		TokenMatcher matcher = getWinners();
		if(matcher != null) {
			return matcher.create(partialToken, lastLine, lastCol);
		}
		else if(hitEndOfFile)
			throw new UnexpectedEndOfFileException(lastLine, lastCol, partialToken);
		else {
			throw new UnexpectedTokenException(lastLine, lastCol, partialToken);
		}
			
		
	}
	
	private TokenMatcher getWinners(){
		int bestScore = -1;
		for(int i=0; i<matchers.length; i++) {
			if(lastSuccessIndex[i] > bestScore) {
				bestScore = lastSuccessIndex[i];
			}
		}
		
		if(bestScore > -1) {
			for(int i=0; i<matchers.length; i++) {
				if(lastSuccessIndex[i] == bestScore)
					return matchers[i];
			}
		}
		return null;
	}
	
	
	private void removeWhiteSpace() {
		while(scanner.hasNext()){
			char c = scanner.peek();
			if(c == ' ' || c == '\t')
				scanner.next();
			else
				break;
		}
	}
	
	private void markPosition() {
		this.lastCol = scanner.getColumn();
		this.lastLine = scanner.getLine();
	}
	
	private void resetMatchers() {
		for(int i=0; i<matchers.length; i++) {
			matchers[i].reset();
			lastSuccessIndex[i] = -1;
			continueFlag[i] = true;
		}
	}
	
	private void updateMatchers(char c) {
		for(int i=0; i<matchers.length; i++) {
			if(continueFlag[i]) {
				boolean accept = matchers[i].offer(c);
				if(accept)
					lastSuccessIndex[i]++;
				else
					continueFlag[i] = false;
			}
		}
	}
	
	private boolean continueCheckingMatchers() {
		for(int i=0; i<continueFlag.length; i++)
			if(continueFlag[i])
				return true;
		return false;
	}
	
	
}
