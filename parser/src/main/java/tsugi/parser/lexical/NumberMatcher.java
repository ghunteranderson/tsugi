package tsugi.parser.lexical;

public class NumberMatcher implements TokenMatcher {

	private int count;
	
	@Override
	public void reset() {
		count=0;
	}

	@Override
	public Token create(String s, int line, int col) {
		return Token.builder()
				.column(col)
				.line(line)
				.type(TokenType.INTEGER)
				.value(s)
				.build();
	}

	@Override
	public boolean willAccept(char c) {
		return c <= '9' && c >= '0';
	}

	@Override
	public void add(char c) {
		if(willAccept(c))
			count++;
		else
			throw new IllegalArgumentException("Matcher cannot accept character " + c);
	}

	@Override
	public boolean isComplete() {
		return count > 0;
	}

}
