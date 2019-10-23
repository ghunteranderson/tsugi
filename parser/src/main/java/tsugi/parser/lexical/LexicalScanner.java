package tsugi.parser.lexical;

public interface LexicalScanner {

	Token next();
	Token peek();

}