package tsugi.parser.exception;

public class UnexpectedEndOfFileException extends InvalidSourceCodeException {

	public UnexpectedEndOfFileException(int line, int col, String code) {
		super(line, col, code);
	}
	
	public UnexpectedEndOfFileException() {
		super(-1, -1, "Line number unknown.");
	}
}
