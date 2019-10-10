package tsugi.parser.lexical;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import tsugi.parser.exception.UnexpectedEndOfFileException;
import tsugi.parser.exception.UnexpectedTokenException;

import static tsugi.parser.lexical.TokenType.*;

public class LexicalScanner {
	
	private LineAwareCharacterScanner scanner;
	private List<TokenMatcher> matchersGlobalList;
	private int lastCol;
	private int lastLine;
	
	public LexicalScanner(InputStream is) {
		scanner = new LineAwareCharacterScanner(is);
		matchersGlobalList = Arrays.asList(
				new ExactTokenMatcher(LEFT_PAREN, "("),
				new ExactTokenMatcher(RIGHT_PAREN, ")"),
				new ExactTokenMatcher(NEW_LINE, "\n"),
				new ExactTokenMatcher(NEW_LINE, "\r\n"),
				new ExactTokenMatcher(ASSIGNMENT, "="),
				new StringTokenMatcher(),
				new IdentifierMatcher()
				);
	}
	
	public Token next() {
		removeWhiteSpace();
		markPosition();
		
		Token token = null;
		String partialToken = "" + scanner.next();
		String tmp = partialToken;
		List<TokenMatcher> matchers = matchersGlobalList.stream()
				.filter(m -> m.startsWith(tmp))
				.collect(Collectors.toList());
			
		if(matchers.isEmpty()){
			// pass
		}
		// This could be an ID. We must be careful about looking ahead
		else if(matchers.stream().anyMatch(m -> m instanceof IdentifierMatcher)) {
			while(true) {
				char c = scanner.peek();
				String newPartialToken = partialToken+c;
				List<TokenMatcher> newMatchers = matchers.stream()
						.filter(m -> m.startsWith(newPartialToken))
						.collect(Collectors.toList());
				
				// It's OK to keep the token
				
				if(!newMatchers.isEmpty()) {
					scanner.next();
					partialToken = newPartialToken;
					matchers = newMatchers;
				}
				else {
					// All matchers are removed by the next character
					// Is the a non-dendifier matcher?
					
					List<TokenMatcher> nonIdMatcher = matchers.stream()
							.filter(m -> !(m instanceof IdentifierMatcher))
							.collect(Collectors.toList());
					if(nonIdMatcher.size() > 1)
						throw new IllegalStateException("Conflicting matchers:" + nonIdMatcher);
					else if(nonIdMatcher.size()==1) {
						while(!nonIdMatcher.get(0).matches(partialToken))
							partialToken += scanner.next();
						token = nonIdMatcher.get(0).create(partialToken, lastLine, lastCol);
						break; // Tie between ID and non-ID. Non-ID took priority
					}
					
					// Our only hope is if it's an identifier
					TokenMatcher idMatcher = matchers.stream()
							.filter(m -> m instanceof IdentifierMatcher)
							.findAny().orElse(null);
					if(idMatcher != null) {
						token = idMatcher.create(partialToken, lastLine, lastCol);
						break; // Found identifier
					}
					else
						break; // Could not parse
				}
				
				// We've found a single non-identifier match
				if(matchers.size()==1 && matchers.stream().noneMatch(m -> m instanceof IdentifierMatcher)){
					while(!matchers.get(0).matches(partialToken))
						partialToken += scanner.next();
					token = matchers.get(0).create(partialToken, lastLine, lastCol);
					break; // Found non-identifier
				}
			}
		}
		// This token is not an id. No look ahead neccessary
		else {
			while(matchers.size() > 1) {
				partialToken += scanner.next();
				String s = partialToken;
				matchers = matchers.stream()
						.filter(m -> m.startsWith(s))
						.collect(Collectors.toList());
			}
			if(!matchers.isEmpty()) {
				while(!matchers.get(0).matches(partialToken))
					partialToken += scanner.next();
				token = matchers.get(0).create(partialToken, lastLine, lastCol);
			}
		}
			
		
		
		if(token != null)
			return token;
		else if(!scanner.hasNext())
			throw new UnexpectedEndOfFileException(scanner.getLine(), scanner.getColumn(), partialToken);
		else
			throw new UnexpectedTokenException(lastLine, lastCol, partialToken);
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
	
	
}
