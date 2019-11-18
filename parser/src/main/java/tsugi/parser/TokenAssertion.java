package tsugi.parser;

import tsugi.parser.exception.UnexpectedTokenException;
import tsugi.parser.lexical.Token;
import tsugi.parser.lexical.TokenType;

public class TokenAssertion {

	public static Token assertType(Token token, TokenType type) {
		if(token.getType() != type)
			throw new UnexpectedTokenException(token);
		return token;
	}
}
