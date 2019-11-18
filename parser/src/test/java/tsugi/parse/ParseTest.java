package tsugi.parse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import tsugi.parser.Parser;
import tsugi.parser.lexical.LexicalScannerImpl;
import tsugi.parser.model.TsugiFile;

public class ParseTest {
	
	@Test
	public void packageAndMultipleImports() {
		var source = 
				  "package sample.parsing\n"
				+ "import ( java.lang.Boolean,\n"
				+ "    java.lang.Integer,\n"
				+ "    java.util.List )\n";
		
		var is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScannerImpl(is);
		TsugiFile file = new Parser("synthetic", scanner).parse();
		
		assertNotNull(file);
		assertEquals("sample.parsing", file.getPackageName().toString());
		assertEquals(3, file.getImports().size());
		assertEquals("java.lang.Boolean", file.getImports().get(0).toString());
		assertEquals("java.lang.Integer", file.getImports().get(1).toString());
		assertEquals("java.util.List", file.getImports().get(2).toString());
		
	}
	
	@Test
	public void packageAndSingleImport() {
		var source = 
				  "package sample.parsing\n"
				+ "import java.lang.Boolean \n";
		
		var is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScannerImpl(is);
		TsugiFile file = new Parser("synthetic", scanner).parse();
		
		assertNotNull(file);
		assertEquals("sample.parsing", file.getPackageName().toString());
		assertEquals(1, file.getImports().size());
		assertEquals("java.lang.Boolean", file.getImports().get(0).toString());
		
	}
	
	@Test
	public void mainFunction() {
		var source = 
				"package sample.main\n"
				+ "import (\n\tjava.util.List, \n\tjava.util.ArrayList\n)"
				+ "\n\n\n\n"
				+ "func main() {"
				+ "\tvar a = 1"
				//+ "\tprintLn(a)"
				+ "}";
		
		var is = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
		var scanner = new LexicalScannerImpl(is);
		TsugiFile file = new Parser("synthetic", scanner).parse();
		
		assertNotNull(file);
		assertEquals("sample.main", file.getPackageName().toString());
		assertEquals(2, file.getImports().size());
		assertEquals("java.util.List", file.getImports().get(0).toString());
		assertEquals("java.util.ArrayList", file.getImports().get(1).toString());
		
		assertEquals(1, file.getFunctions().size());
		var main = file.getFunctions().get(0);
		assertEquals("main", main.getName());
		
	}
}
