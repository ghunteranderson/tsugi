package com.ghunteranderson.tsugi.sandbox.lexicon;

public enum Token {
  // Keywords
  MODULE,
  IMPORT,
  FUNCTION,

  // Meta
  IDENTIFIER,
  EOF,
  STRING,
  NUMBER,
  
  // Symbols
  SEMICOLON,
  PAREN_L,
  PAREN_R,
  BRACE_L,
  BRACE_R,
  BRACKET_L,
  BRACKET_R,
  DOT,
  EQUALS,
  MINUS,
  COMMA,

  ;
}
