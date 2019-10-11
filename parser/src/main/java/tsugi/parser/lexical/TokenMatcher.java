package tsugi.parser.lexical;

public interface TokenMatcher {
	boolean offer(char c);
	void reset();
	Token create(String s, int line, int col);
}
