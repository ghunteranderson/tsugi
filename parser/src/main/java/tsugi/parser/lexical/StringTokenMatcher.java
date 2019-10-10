package tsugi.parser.lexical;

import java.util.Arrays;

import tsugi.parser.exception.UnexpectedTokenException;

public class StringTokenMatcher implements TokenMatcher {
	
	@Override
	public boolean startsWith(String s) {
		return checkString(s, true);
	}

	@Override
	public boolean matches(String s) {
		return checkString(s, false);
	}

	@Override
	public Token create(String s, int line, int col) {
		return Token.builder()
				.type(TokenType.STRING)
				.column(col)
				.line(line)
				.value(parseString(s, line, col))
				.build();
	}
	
	private String parseString(String s, int line, int col) {
		int oi=1, ni=0;
		
		char[] parsed = new char[s.length()-2];
		
		final int stopIndex = s.length()-2;
		while(oi <=stopIndex) {
			char c = s.charAt(oi);
			oi++;
			if(c =='\\') {
				c = s.charAt(oi);
				oi++;
				char unescaped;
				switch(c) {
					case 't': unescaped = '\t'; break;
					case 'n': unescaped = '\n'; break;
					case 'r': unescaped = '\r'; break;
					case 'b': unescaped = '\b'; break;
					case '\\': unescaped = '\\'; break;
					case '"': unescaped = '"'; break;
					default: throw new UnexpectedTokenException(line, col+oi, "\\" + c);
				}
				parsed[ni] = unescaped;
				ni++;
			}
			else {
				parsed[ni] = c;
				ni++;
			}
		}
		
		
		return new String(Arrays.copyOf(parsed, ni));
	}
	
	
	
	private boolean checkString(String s, boolean allowPartial) {
		
		// Empty String is OK
		if(s.isEmpty())
			return allowPartial;
		
		// Check for opening quote
		if(s.charAt(0) != '"')
			return false;
		
		// Skim for closing quote
		boolean complete = false;
		int i;
		for(i=1; !complete && i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == '"') {
				complete = true;
			}
			// Ignore whatever follows an escape character
			else if(c ==  '\\' && i+1 < s.length()) {
				i++;
				c = s.charAt(i);
			}
		}
		
		if(!complete)
			return allowPartial;
		else
			return i == s.length();
	}
	
	

}
