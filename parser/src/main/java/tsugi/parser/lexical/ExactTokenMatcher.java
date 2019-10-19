package tsugi.parser.lexical;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExactTokenMatcher implements TokenMatcher {
	
	private final TokenType type;
	private final char[] token;
	private int nextIndex = 0;
	
	public ExactTokenMatcher(TokenType type, String token) {
		this.type = type;
		this.token = token.toCharArray();
		this.nextIndex = 0;
	}

	@Override
	public Token create(String s, int line, int col) {
		return Token.builder()
				.type(type)
				.value(s)
				.line(line)
				.column(col)
				.build();
	}

	@Override
	public void reset() {
		nextIndex = 0;
	}
	
	@Override
	public boolean willAccept(char c) {
		return nextIndex < token.length && c == token[nextIndex];
	}

	@Override
	public void add(char c) {
		if(!willAccept(c))
			throw new IllegalArgumentException("Matcher cannot accept character " + c);
		nextIndex++;
	}

	@Override
	public boolean isComplete() {
		return nextIndex == token.length;
	}
	
}
