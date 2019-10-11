package tsugi.parser.lexical;

import java.io.InputStream;

import lombok.Getter;

public class LineAwareCharacterScanner {
	
	private TranparentCharacterBuffer buffer;
	@Getter
	private int column = 1;
	@Getter
	private int line = 1;
	
	
	public LineAwareCharacterScanner(InputStream is) {
		buffer = new TranparentCharacterBuffer(is);
	}
	
	public char next() {
		char c = buffer.next();
		
		if(c == '\n') {
			line++;
			column=0;
		}
		else
			column++;
		return c;
	}
	
	public char peek() {
		return buffer.peek();
	}
	
	public boolean hasNext() {
		return buffer.hasNext();
	}	
}
