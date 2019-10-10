package tsugi.parser.lexical;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
	private final TokenType type;
	private final String value;
	private final int line;
	private final int column;
}
