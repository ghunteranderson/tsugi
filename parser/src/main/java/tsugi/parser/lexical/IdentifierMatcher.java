package tsugi.parser.lexical;

public class IdentifierMatcher implements TokenMatcher {
	
	private int nextIndex;
	private boolean ok;
	
	public IdentifierMatcher() {
		reset();
	}
	
	@Override
	public Token create(String s, int line, int col) {
		return Token.builder()
				.type(TokenType.IDENTIFIER)
				.line(line)
				.column(col)
				.value(s)
				.build();
	}

	@Override
	public boolean offer(char c) {
		if(ok && isValidChar(c, nextIndex)) {
			nextIndex++;
			return true;
		}
		else {
			ok = false;
			return false;
		}
	}

	@Override
	public void reset() {
		nextIndex = 0;
		ok = true;
	}
	
	private boolean isValidChar(char c, int i) {
		return 
			(c >= 'a' && c <='z')
			|| (c >= 'A' && c <='Z')
			|| (c == '_')
			|| (i > 0 && c>='0' && c <='9');
	}

}
