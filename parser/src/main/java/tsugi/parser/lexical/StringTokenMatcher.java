package tsugi.parser.lexical;

import java.util.Arrays;

import tsugi.parser.exception.UnexpectedTokenException;

public class StringTokenMatcher implements TokenMatcher {
	
	private boolean escaping;
	private boolean closed;
	private boolean opened;
	
	public StringTokenMatcher() {
		reset();
	}
	
	@Override
	public void reset() {
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

	@Override
	public boolean willAccept(char c) {
		if(!opened)
			return c == '"';
		else if(closed)
			return false;
		else if(escaping)
			return c == '"' 
				||  c == '\\' 
				|| c == 'b' 
				|| c == 'r' 
				|| c == 'n' 
				|| c == 't';
		else
			return c >= ' ' && c <= '~';			
	}

	@Override
	public void add(char c) {
		if(!willAccept(c))
			throw new IllegalArgumentException("Matcher cannot accept character " + c);
		
		if(!opened)
			opened=true;
		else if(escaping)
			escaping = false;
		else if(!escaping && c == '\\')
			escaping = true;
		else if(c == '"')
			closed = true;
	}

	@Override
	public boolean isComplete() {
		return closed;
	}

}
