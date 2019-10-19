package tsugi.parser.lexical;

public interface TokenMatcher {
	boolean willAccept(char c);
	void add(char c);
	boolean isComplete();
	void reset();
	Token create(String s, int line, int col);
}
