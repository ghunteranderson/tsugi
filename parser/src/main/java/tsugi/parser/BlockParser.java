package tsugi.parser;

import java.util.ArrayList;
import java.util.List;

import tsugi.parser.exception.UnexpectedTokenException;
import tsugi.parser.lexical.LexicalScanner;
import tsugi.parser.model.Statement;
import tsugi.parser.model.VarDeclaration;

import static tsugi.parser.TokenAssertion.*;
import static tsugi.parser.lexical.TokenType.*;

public class BlockParser {
	
	private final TypeReferenceParser
	
	public List<Statement> parse(LexicalScanner scanner){
		var statements = new ArrayList<Statement>();
		assertType(scanner.next(), LEFT_BRACE);
		while(scanner.peek().getType() != RIGHT_BRACE) {
			statements.add(parseStatement(scanner));
		}
		scanner.next();
		return statements;
	}

	private Statement parseStatement(LexicalScanner scanner) {
		var token = scanner.peek();
		if(token.getType() == VAR)
			return parseVarDeclare(scanner);
		
		throw new UnexpectedTokenException(token);
	}

	private Statement parseVarDeclare(LexicalScanner scanner) {
		VarDeclaration declaration = new VarDeclaration();
		assertType(scanner.next(), VAR);
		
		var token = assertType(scanner.next(), IDENTIFIER);
		declaration.setName(token.getValue());
		
		boolean typeApplied = false;
		
		token = scanner.peek();
		if(token.getType() == COLON) {
			scanner.next();
			declaration.setType(type);
		}
		
	}

}
