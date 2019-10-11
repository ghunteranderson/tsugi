package tsugi.parser.lexical;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import tsugi.parser.exception.UnexpectedEndOfFileException;

public class TranparentCharacterBuffer {

	private InputStreamReader reader;
	private List<Character> buffer;
	private int targetBufferSize;
	
	public TranparentCharacterBuffer(InputStream is) {
		this(is, 100);
	}
	
	public TranparentCharacterBuffer(InputStream is, int size) {
		buffer = new ArrayList<>(size);
		reader = new InputStreamReader(is, StandardCharsets.UTF_8);
		targetBufferSize = size;
	}
	
	public boolean hasNext() {
		return hasCount(1);
	}
	
	public boolean hasCount(int count) {
		boolean available = buffer.size() >= count;
		if(available)
			return true;
		else {
			load(count);
			return buffer.size() >= count;
		}
	}
	
	public char peek() {
		return peekAhead(0);
	}
	
	public char peekAhead(int skip) {
		if(load(skip+1))
			return buffer.get(skip);
		else
			throw new UnexpectedEndOfFileException();
	}
	
	public char next() {
		if(hasCount(1))
			return buffer.remove(0);
		else
			throw new UnexpectedEndOfFileException();
	}
	
	private boolean load(int requestedBufferSize) {	
		try {
			int remaining = Math.max(requestedBufferSize - buffer.size(), targetBufferSize);
			if(remaining > 0) {
				char[] tmpBuffer = new char[remaining];
				int read = reader.read(tmpBuffer);
				for(int i=0; i<read; i++)
					buffer.add(tmpBuffer[i]);
			}
			return buffer.size() >= requestedBufferSize;							
		} catch (IOException ex) {
			throw new UnexpectedEndOfFileException();
		}
	}
}
