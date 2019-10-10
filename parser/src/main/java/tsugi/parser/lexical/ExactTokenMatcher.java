package tsugi.parser.lexical;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExactTokenMatcher implements TokenMatcher {
	
	private final TokenType type;
	private final String token;
	
	@Override
	public boolean startsWith(String s) {
		return token.startsWith(s);
	}

	@Override
	public boolean matches(String s) {
		return token.equals(s);
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
	
}
