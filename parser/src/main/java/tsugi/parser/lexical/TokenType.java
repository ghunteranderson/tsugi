package tsugi.parser.lexical;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum TokenType {
	IDENTIFIER,
	STRING,
	INTEGER,
	LEFT_PAREN,
	RIGHT_PAREN,
	COLON,
	IF,
	ELSE,
	CMP_G,
	CMP_L,
	CMP_GE,
	CMP_LE,
	CMP_NE,
	CMP_EQ,
	NEW_LINE,
	ASSIGNMENT
}
