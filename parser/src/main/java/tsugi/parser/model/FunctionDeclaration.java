package tsugi.parser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FunctionDeclaration {
	
	private String name;
	private TypeReference returnType;
	private List<TypeAndName> parameters = new ArrayList<>();
	private List<Statement> body = new ArrayList<>();

}
