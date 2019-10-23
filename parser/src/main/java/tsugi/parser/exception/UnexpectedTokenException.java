package tsugi.parser.exception;

import org.apache.commons.text.StringEscapeUtils;

public class UnexpectedTokenException extends InvalidSourceCodeException {

	public UnexpectedTokenException(int line, int col, String token) {
		super(line, col, StringEscapeUtils.unescapeJava(token));
	}
}
