package tsugi.parser;

import tsugi.parser.exception.UnexpectedTokenException;
import tsugi.parser.lexical.LexicalScanner;
import tsugi.parser.lexical.TokenType;
import tsugi.parser.model.TypeReference;

public class TypeReferenceParser {
	
	public TypeReference parse(LexicalScanner scanner) {
		TypeReference type = new TypeReference();
		
		var token = scanner.next();
		if(token.getType() != TokenType.IDENTIFIER)
			throw new UnexpectedTokenException(token);
		
		type.add(token.getValue());
		
		while(true) {
			token = scanner.peek();
			if(token.getType() == TokenType.DOT) {
				scanner.next();
				token = scanner.next();
				if(token.getType() != TokenType.IDENTIFIER)
					throw new UnexpectedTokenException(token);
				type.add(token.getValue());
			}
			else {
				break;
			}
		}
		return type;
	}

}
