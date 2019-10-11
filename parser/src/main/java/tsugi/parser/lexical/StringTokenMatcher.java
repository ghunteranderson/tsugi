package tsugi.parser.lexical;

import java.util.Arrays;

import tsugi.parser.exception.UnexpectedTokenException;

public class StringTokenMatcher implements TokenMatcher {
	
	private boolean ok;
	private boolean escaping;
	private boolean closed;
	private boolean opened;
	
	public StringTokenMatcher() {
		reset();
	}
	
	@Override
	public boolean offer(char c) {
		// Once syntax is broken, don't continue
		if(!ok)
			return false;
		
		// Cannot consume after closed
		if(closed) {
			ok = false;
			return false;
		}
		
		// First character must be a quote
		if(ok && !opened) {
			if(c == '"') {
				opened = true;
				return true;
			} else {
				ok = false;
				return false;
			}
		}
		
		// Enable escaping or skip next character if escaped
		if(c == '\\') {
			escaping = !escaping;
			return true;
		}
		// Escape character
		else if(escaping) {
			escaping  = !escaping;
			return true;
		}
		
		// Close character
		if(c == '"') {
			closed = true;
			return true;
		}
		
		// consume character
		return true;
	}
	
	@Override
	public void reset() {
		ok = true;
		escaping = false;
		closed = false;
		opened = false;
	}
	

	@Override
	public Token create(String s, int line, int col) {
		return Token.builder()
				.type(TokenType.STRING)
				.column(col)
				.line(line)
				.value(unescapeString(s, line, col))
				.build();
	}
	
	private String unescapeString(String s, int line, int col) {
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

}
