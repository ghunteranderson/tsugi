package tsugi.parser.exception;

public class UnexpectedTokenException extends InvalidSourceCodeException {

	public UnexpectedTokenException(int line, int col, String token) {
		super(line, col, token);
	}
}
