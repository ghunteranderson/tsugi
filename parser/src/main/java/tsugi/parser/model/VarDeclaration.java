package tsugi.parser.model;

import lombok.Data;

@Data
public class VarDeclaration {
	private String name;
	private TypeReference type;
	private Expression value;
}
