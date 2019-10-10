package tsugi.parser.lexical;

public interface TokenMatcher {
	boolean startsWith(String s);
	boolean matches(String s);
	Token create(String s, int line, int col);
}
