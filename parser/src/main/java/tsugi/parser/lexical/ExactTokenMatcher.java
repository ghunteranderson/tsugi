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
	public boolean offer(char c) {
		if(nextIndex < token.length && c == token[nextIndex]) {
			nextIndex++;
			return true;
		}
		else {
			nextIndex = token.length+1;
			return false;
		}
	}

	@Override
	public void reset() {
		nextIndex = 0;
	}
	
	public boolean matched() {
		return nextIndex == token.length;
	}
	
}
