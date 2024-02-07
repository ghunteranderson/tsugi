package com.ghunteranderson.tsugi.sandbox.lexicon;

import java.io.InputStream;

import com.ghunteranderson.tsugi.sandbox.ErrorCode;
import com.ghunteranderson.tsugi.sandbox.TsugiCompilerException;

public class LexicalAnalyzer {

  private final CharacterScanner in;

  private Lexeme nextToken = null;

  public LexicalAnalyzer(InputStream is) {
    this.in = new CharacterScanner(is);
    nextToken = parseNext();
  }

  public Lexeme next() {
    if (nextToken == null)
      nextToken = parseNext();

    var next = nextToken;
    nextToken = null;
    return next;
  }

  public Lexeme peek() {
    if(nextToken == null)
      nextToken = parseNext();
    return nextToken;
  }

  private Lexeme parseNext() {
    Lexeme lexeme = null;

    if (!in.hasNext()) {
      return new Lexeme(Token.EOF, "", in.getRow(), in.getCol());
    }

    // Determine the category of token we're parsing
    var c = in.peek();
    if (c == '"')
      lexeme = parseString();
    else if(c == '-' || LiteralUtils.isDigit(c))
      lexeme = parseNumber();
    else if (LiteralUtils.isSymbol(c))
      lexeme = parseSymbol();
    else if (LiteralUtils.isIdentLeadingChar(c))
      lexeme = parseWord();
    else
      return panic();

    in.skipWhiteSpace();
    return lexeme;
  }

  private Lexeme parseWord() {
    var builder = new StringBuilder();
    var row = in.getRow();
    var col = in.getCol();

    while (in.hasNext()) {
      var next = in.peek();
      if (LiteralUtils.isIdentChar(next)) {
        builder.append(next);
        in.next();
      } else
        break;
    }

    var lexeme = builder.toString();
    var token = lookupWordType(lexeme);
    return new Lexeme(token, lexeme, row, col);
  }

  private Token lookupWordType(String word) {
    switch (word) {
      case ("module"):
        return Token.MODULE;
      case ("import"):
        return Token.IMPORT;
      case ("function"):
        return Token.FUNCTION;
      default:
        return Token.IDENTIFIER;
    }
  }

  private Lexeme parseSymbol() {
    int row = in.getRow();
    int col = in.getCol();
    var next = in.next();

    switch (next) {
      case (';'):
        return new Lexeme(Token.SEMICOLON, ";", row, col);
      case ('('):
        return new Lexeme(Token.PAREN_L, "(", row, col);
      case (')'):
        return new Lexeme(Token.PAREN_R, ")", row, col);
      case ('{'):
        return new Lexeme(Token.BRACE_L, "{", row, col);
      case ('}'):
        return new Lexeme(Token.BRACE_R, "}", row, col);
      case ('['):
        return new Lexeme(Token.BRACKET_L, "[", row, col);
      case (']'):
        return new Lexeme(Token.BRACKET_R, "]", row, col);
      case ('.'):
        return new Lexeme(Token.DOT, ".", row, col);
      case ('='):
        return new Lexeme(Token.EQUALS, "=", row, col);
      case (','):
        return new Lexeme(Token.COMMA, ",", row, col);
      default:
        return panic();
    }

  }

  private Lexeme parseString() {
    var builder = new StringBuilder();
    var row = in.getRow();
    var col = in.getCol();

    in.next(); // consume leading double-quote

    while (true) {
      char c = in.next();
      if (c == '"')
        break;
      else if (c == '\\') {
        // Entering escape mode
        c = in.next();
        if (c == 't')
          builder.append('\t');
        else if (c == '\'')
          builder.append('\'');
        else if (c == '"')
          builder.append('"');
        else if (c == 'r')
          builder.append('\r');
        else if (c == '\\')
          builder.append('\\');
        else if (c == 'n')
          builder.append('\n');
        else if (c == 'f')
          builder.append('\f');
        else if (c == 'b')
          builder.append('\b');
        else
          return panic();
      } else
        builder.append(c);
    }

    return new Lexeme(Token.STRING, builder.toString(), row, col);
  }

  private Lexeme parseNumber(){
    var builder = new StringBuilder();
    var row = in.getRow();
    var col = in.getCol();

    char c = in.peek();
    if(c == '-'){
      builder.append(in.next());
      c = in.peek();
      if(!LiteralUtils.isDigit(c))
        return new Lexeme(Token.MINUS, "-", row, col);
    }

    boolean decimalSeen = false; 
    while(true){
      c = in.peek();
      if(LiteralUtils.isDigit(c)){
        builder.append(in.next());
      }
      else if (c == '.'){
        if(decimalSeen)
          throw new TsugiCompilerException(ErrorCode.INVALID_NUMBER, new SourceLocation(row, col));
        decimalSeen = true;
        builder.append(in.next());
      }
      else {
        break;
      }
    }
    return new Lexeme(Token.NUMBER, builder.toString(), row, col);
  }

  private <T> T panic(){
    throw new TsugiCompilerException(
          ErrorCode.LEXICAL_PANIC,
          new SourceLocation(in.getRow(), in.getCol()));
  }


}