package tsugi.parser.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class InvalidSourceCodeException extends RuntimeException {
	private final int line;
	private final int column;
	private final String code;
	
	public String toString() {
		return String.format("%s on line %s column %s: %s", this.getClass().getSimpleName(), line, column, code);
	}
}
