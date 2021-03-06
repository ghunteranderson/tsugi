package tsugi.parser.lexical;

public class IdentifierMatcher implements TokenMatcher {
	
	private int nextIndex;
	
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
	public void reset() {
		nextIndex = 0;
	}
	
	@Override
	public boolean willAccept(char c) {
		return 
				(c >= 'a' && c <='z')
				|| (c >= 'A' && c <='Z')
				|| (c == '_')
				|| (nextIndex > 0 && c>='0' && c <='9');
	}

	@Override
	public void add(char c) {
		if(willAccept(c))
			nextIndex++;
		else
			throw new IllegalArgumentException("Matcher cannot accept character " + c);
	}

	@Override
	public boolean isComplete() {
		return nextIndex > 0;
	}

}
