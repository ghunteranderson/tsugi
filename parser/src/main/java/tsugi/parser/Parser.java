package tsugi.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import tsugi.parser.exception.UnexpectedTokenException;
import tsugi.parser.lexical.LexicalScanner;
import tsugi.parser.lexical.LexicalScannerImpl;
import tsugi.parser.lexical.TokenType;
import tsugi.parser.model.TsugiFile;
import tsugi.parser.model.TypeReference;

public class Parser {
	
	private final String path;
	private final LexicalScanner lexicalScanner;
	private final TypeReferenceParser typeReferenceParser;
	private final FunctionParser functionParser = new FunctionParser();
	
	public Parser(String path) throws IOException {
		this(path, new LexicalScannerImpl(new FileInputStream(path)));
	}
	
	public Parser(String path, LexicalScanner scanner) {
		this.path = path;
		this.lexicalScanner = scanner;
		this.typeReferenceParser = new TypeReferenceParser();
	}
	
	public TsugiFile parse() {
		TsugiFile file = new TsugiFile(path);
		
		parsePackageDeclare(file);
		parseImports(file);
		
		while(true) {
			var token = lexicalScanner.peek();
			if(token.getType() == TokenType.EOF)
				break;
			else if(token.getType() == TokenType.FUNC)
				file.getFunctions().add(functionParser.parse(lexicalScanner));
		}
		
		
		return file;
	}
	
	// PACKAGE_DECLARE
	private void parsePackageDeclare(TsugiFile file) {
		var token = lexicalScanner.next();
		if(token.getType() != TokenType.PACKAGE)
			throw new UnexpectedTokenException(token);
		
		var typeParser = new TypeReferenceParser();
		file.setPackageName(typeParser.parse(lexicalScanner));
	}
	
	// IMPORT_DECLARE
	private void parseImports(TsugiFile file) {
		var token = lexicalScanner.peek();
		if(token.getType() != TokenType.IMPORT)
			return;
		lexicalScanner.next();
		
		var imports = new ArrayList<TypeReference>();
		token = lexicalScanner.peek();
		if(token.getType() == TokenType.IDENTIFIER) {
			// Single import
			imports.add(typeReferenceParser.parse(lexicalScanner));
		} else if(token.getType() == TokenType.LEFT_PAREN) {
			lexicalScanner.next();
			// Multiple imports
			while(true) {
				
				// Read import
				imports.add(typeReferenceParser.parse(lexicalScanner));
				
				// Check for end of imports
				token = lexicalScanner.next();
				if(token.getType() == TokenType.COMMA)
					continue;
				else if(token.getType() == TokenType.RIGHT_PAREN)
					break;
				else
					throw new UnexpectedTokenException(token);
			}
		} else {
			throw new UnexpectedTokenException(token);
		}
		file.setImports(imports);
	}
}
