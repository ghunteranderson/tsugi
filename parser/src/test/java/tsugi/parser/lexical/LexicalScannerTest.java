package tsugi.parser.lexical;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tsugi.parser.exception.UnexpectedEndOfFileException;
import tsugi.test.fblt.FileBasedTsugiTestFactory;
import tsugi.test.fblt.TestFiles;
import tsugi.test.fblt.TokenDescription;

public class LexicalScannerTest {
	
	@TestFactory
	public Stream<DynamicTest> dynamic(){
		return Arrays.asList(/*"samples/snippets",*/ "samples/programs")
			.stream()
			.flatMap(folder -> new FileBasedTsugiTestFactory().fromFolder(folder, "tokens.json", LexicalScannerTest::runTest).stream());
		
	}
	
	
	private static void runTest(TestFiles testFiles) {
		try {
			// Create Scanner
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream sourceFileStream = loader.getResourceAsStream(testFiles.getSourceFile());
			assertNotNull(sourceFileStream, "Could not open file " + testFiles.getSourceFile());
			var scanner = new LexicalScanner(sourceFileStream);
			
			// Load verify data
			InputStream verifyStream = loader.getResourceAsStream(testFiles.getVerifyFile());
			assertNotNull(sourceFileStream, "Could not open file " + testFiles.getVerifyFile());
			List<TokenDescription> tokens = new ObjectMapper().readerFor(new TypeReference<List<TokenDescription>>() {}).readValue(verifyStream);
			
			for(TokenDescription expectedToken : tokens) {
				Token t = scanner.next();
				assertEquals(expectedToken.getType(), t.getType().toString(), String.format("Expected %s at line %s col %s", expectedToken.getType(), t.getLine(), t.getColumn()));
				if(expectedToken.getValue() != null) {
					assertEquals(expectedToken.getValue(), t.getValue());
				}
			}
			assertThrows(UnexpectedEndOfFileException.class, () -> scanner.next());
		} catch(IOException ex) {
			fail(ex);
		}
		
	}
}
