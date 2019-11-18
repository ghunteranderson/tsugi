package tsugi.parser.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TsugiFile {

	private final String fileName;
	private TypeReference packageName;
	private List<TypeReference> imports = new ArrayList<>();
	private List<FunctionDeclaration> functions = new ArrayList<>();
	
}
