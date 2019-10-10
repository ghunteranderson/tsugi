package tsugi.parser.lexical;

import java.util.regex.Pattern;

public class IdentifierMatcher implements TokenMatcher {
	
	private Pattern pattern = Pattern.compile("^[a-zA-Z_]{1}[a-zA-Z0-9_]*$");
	
	@Override
	public boolean startsWith(String s) {
		return pattern.matcher(s).find();
	}

	@Override
	public boolean matches(String s) {
		return pattern.matcher(s).find();
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

}
