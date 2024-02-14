package com.ghunteranderson.tsugi.lexicon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TestUtils;
import com.ghunteranderson.tsugi.TsugiCompilerException;

public class LexicalAnalyzerTest {

  private LexicalAnalyzer lexicalAnalyzer = null;

  @Test
  void test_moduleDeclaration() {
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("module stdio;"));
    assertNext(Token.MODULE, "module", 1, 1);
    assertNext(Token.IDENTIFIER, "stdio", 1, 8);
    assertNext(Token.SEMICOLON, ";", 1, 13);
    assertNext(Token.EOF, "", 1, 14);
  }

  @Test
  void test_import_empty() {
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
        import (
        )
        """));
    assertNext(Token.IMPORT, "import", 1, 1);
    assertNext(Token.PAREN_L, "(", 1, 8);
    assertNext(Token.PAREN_R, ")", 2, 1);
    assertNext(Token.EOF, "", 3, 1); // Row 3 since source is actually 3 lines long
  }

  @Test
  void test_import_multipleValues() {
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
        import (
          abC,
          dEf,
          Ghi
        )
        """));
    assertNext(Token.IMPORT, "import", 1, 1);
    assertNext(Token.PAREN_L, "(", 1, 8);
    assertNext(Token.IDENTIFIER, "abC", 2, 3);
    assertNext(Token.COMMA, ",", 2, 6);
    assertNext(Token.IDENTIFIER, "dEf", 3, 3);
    assertNext(Token.COMMA, ",", 3, 6);
    assertNext(Token.IDENTIFIER, "Ghi", 4, 3);
    assertNext(Token.PAREN_R, ")", 5, 1);
    assertNext(Token.EOF, "", 6, 1); // Row 3 since source is actually 3 lines long
  }

  @Test
  void test_functionDeclaration_noArgs_emptyBody() {
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
        function void main(){

        }"""));
    assertNext(Token.FUNCTION, "function", 1, 1);
    assertNext(Token.IDENTIFIER, "void", 1, 10);
    assertNext(Token.IDENTIFIER, "main", 1, 15);
    assertNext(Token.PAREN_L, "(", 1, 19);
    assertNext(Token.PAREN_R, ")", 1, 20);
    assertNext(Token.BRACE_L, "{", 1, 21);
    assertNext(Token.BRACE_R, "}", 3, 1);
    assertNext(Token.EOF, "", 3, 2);
  }

  @Test
  void test_sample_helloWorld() {
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
        module greeting;

        import (
          stdio
        )

        function void main(string[] args){
          stdio.out.println("Hello World!");
        }
        """));

    // Module
    assertNext(Token.MODULE, "module");
    assertNext(Token.IDENTIFIER, "greeting");
    assertNext(Token.SEMICOLON, ";");
    // Import
    assertNext(Token.IMPORT, "import");
    assertNext(Token.PAREN_L, "(");
    assertNext(Token.IDENTIFIER, "stdio");
    assertNext(Token.PAREN_R, ")");
    // Function
    assertNext(Token.FUNCTION, "function");
    assertNext(Token.IDENTIFIER, "void");
    assertNext(Token.IDENTIFIER, "main");
    assertNext(Token.PAREN_L, "(");
    assertNext(Token.IDENTIFIER, "string");
    assertNext(Token.BRACKET_L, "[");
    assertNext(Token.BRACKET_R, "]");
    assertNext(Token.IDENTIFIER, "args");
    assertNext(Token.PAREN_R, ")");
    assertNext(Token.BRACE_L, "{");
    //Println
    assertNext(Token.IDENTIFIER, "stdio");
    assertNext(Token.DOT, ".");
    assertNext(Token.IDENTIFIER, "out");
    assertNext(Token.DOT, ".");
    assertNext(Token.IDENTIFIER, "println");
    assertNext(Token.PAREN_L, "(");
    assertNext(Token.STRING, "Hello World!");
    assertNext(Token.PAREN_R, ")");
    assertNext(Token.SEMICOLON, ";");

    assertNext(Token.BRACE_R, "}");
    assertNext(Token.EOF, "");

  }

  @Test
  void test_structDefinition(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
    struct Vector2f {
      float x,
      float y
    }
    """));
    assertNext(Token.STRUCT, "struct");
    assertNext(Token.IDENTIFIER, "Vector2f");
    assertNext(Token.BRACE_L, "{");
    assertNext(Token.IDENTIFIER, "float");
    assertNext(Token.IDENTIFIER, "x");
    assertNext(Token.COMMA, ",");
    assertNext(Token.IDENTIFIER, "float");
    assertNext(Token.IDENTIFIER, "y");
    assertNext(Token.BRACE_R, "}");
  }

  @Test
  void test_structConstructor(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
    Vector2f velocity = new Vector2f {
      x: 12.2,
      y: -38.2
    }
    """));
    assertNext(Token.IDENTIFIER, "Vector2f");
    assertNext(Token.IDENTIFIER, "velocity");
    assertNext(Token.EQUALS, "=");
    assertNext(Token.NEW, "new");
    assertNext(Token.IDENTIFIER, "Vector2f");
    assertNext(Token.BRACE_L, "{");
    assertNext(Token.IDENTIFIER, "x");
    assertNext(Token.COLON, ":");
    assertNext(Token.NUMBER, "12.2");
    assertNext(Token.COMMA, ",");
    assertNext(Token.IDENTIFIER, "y");
    assertNext(Token.COLON, ":");
    assertNext(Token.NUMBER, "-38.2");
    assertNext(Token.BRACE_R, "}");
  }

  @Test
  void test_stringAssignment_empty(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
      string s = "";
      """));
    assertNext(Token.IDENTIFIER, "string", 1, 1);
    assertNext(Token.IDENTIFIER, "s", 1, 8);
    assertNext(Token.EQUALS, "=", 1, 10);
    assertNext(Token.STRING, "", 1, 12);
  }

  @Test
  void test_stringAssignment_simple(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
      string s = "Hello World";
      """));
    assertNext(Token.IDENTIFIER, "string");
    assertNext(Token.IDENTIFIER, "s");
    assertNext(Token.EQUALS, "=");
    assertNext(Token.STRING, "Hello World");
  }

  @Test
  void test_stringAssignment_escapedCharacters(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
      string s = "\\t \\' \\" \\r \\\\ \\n \\f \\b";
      """));
    assertNext(Token.IDENTIFIER, "string", 1, 1);
    assertNext(Token.IDENTIFIER, "s", 1, 8);
    assertNext(Token.EQUALS, "=", 1, 10);
    assertNext(Token.STRING, "\t \' \" \r \\ \n \f \b", 1, 12);
  }

  @Test
  void test_numberAssignment_positiveInteger(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
      int i = 2024;
      """));
    assertNext(Token.IDENTIFIER, "int", 1, 1);
    assertNext(Token.IDENTIFIER, "i", 1, 5);
    assertNext(Token.EQUALS, "=", 1, 7);
    assertNext(Token.NUMBER, "2024", 1, 9);
  }

  @Test
  void test_numberAssignment_negativeInteger(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
      int i = -3028;
      """));
    assertNext(Token.IDENTIFIER, "int", 1, 1);
    assertNext(Token.IDENTIFIER, "i", 1, 5);
    assertNext(Token.EQUALS, "=", 1, 7);
    assertNext(Token.NUMBER, "-3028", 1, 9);
  }

  @Test
  void test_numberAssignment_decimal(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
      int i = -30.839;
      """));
    assertNext(Token.IDENTIFIER, "int");
    assertNext(Token.IDENTIFIER, "i");
    assertNext(Token.EQUALS, "=");
    assertNext(Token.NUMBER, "-30.839");
  }

  @Test
  void test_numberAssignment_invalid_multipleDecimals(){
    lexicalAnalyzer = new LexicalAnalyzer(TestUtils.streamString("""
      int i = -30.83.9;
      """));
    assertNext(Token.IDENTIFIER, "int");
    assertNext(Token.IDENTIFIER, "i");
    assertNext(Token.EQUALS, "=");
    assertError(ErrorCode.INVALID_NUMBER);
  }

  private void assertNext(Token token, String lexeme, int row, int col) {
    assertEquals(new Lexeme(token, lexeme, row, col), lexicalAnalyzer.next());
  }

  private void assertNext(Token token, String lexeme) {
    var next = lexicalAnalyzer.next();
    assertEquals(token, next.token());
    assertEquals(lexeme, lexeme);
  }

  private void assertError(ErrorCode code) {
    var ex = assertThrows(TsugiCompilerException.class, lexicalAnalyzer::next);
    assertEquals(code, ex.getCode());
  }
}
