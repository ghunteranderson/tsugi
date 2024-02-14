package com.ghunteranderson.tsugi.lexicon;

public record Lexeme(
    Token token,
    String lexeme,
    SourceLocation location) {

  public Lexeme(Token token, String lexeme, int row, int col) {
    this(token, lexeme, new SourceLocation(row, col));
  }

  public boolean is(Token type) {
    return token == type;
  }

  public boolean isNot(Token type) {
    return token != type;
  }
}
