package tsugi.parser.exception;

import org.apache.commons.text.StringEscapeUtils;

import tsugi.parser.lexical.Token;

public class UnexpectedTokenException extends InvalidSourceCodeException {

	public UnexpectedTokenException(int line, int col, String token) {
		super(line, col, StringEscapeUtils.unescapeJava(token));
	}
	
	public UnexpectedTokenException(Token token) {
		super(token.getLine(), token.getColumn(), StringEscapeUtils.unescapeJava(token.getValue()));
	}
}
