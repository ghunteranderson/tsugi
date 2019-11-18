package tsugi.parser;

import tsugi.parser.lexical.LexicalScanner;
import tsugi.parser.model.FunctionDeclaration;
import static tsugi.parser.TokenAssertion.*;
import static tsugi.parser.lexical.TokenType.*;

public class FunctionParser {
	
	private TypeReferenceParser typeRefParser = new TypeReferenceParser();
	private BlockParser blockParser = new BlockParser();
	
	public FunctionDeclaration parse(LexicalScanner scanner) {
		FunctionDeclaration function = new FunctionDeclaration();

		assertType(scanner.next(), FUNC);
		
		var token = assertType(scanner.next(), IDENTIFIER);
		function.setName(token.getValue());
		
		assertType(scanner.next(), LEFT_PAREN);
		parseParamDeclare(scanner, function);
		assertType(scanner.next(), RIGHT_PAREN);
		
		token = scanner.peek();
		if(token.getType() == COLON) {
			scanner.next();
			function.setReturnType(typeRefParser.parse(scanner));
		}
		
		function.setBody(blockParser.parse(scanner));
		
		return function;
	}
	
	private void parseParamDeclare(LexicalScanner scanner, FunctionDeclaration func) {
		
	}

}
