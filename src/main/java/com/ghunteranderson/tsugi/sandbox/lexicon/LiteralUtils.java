package com.ghunteranderson.tsugi.sandbox.lexicon;

public class LiteralUtils {

  public static boolean isIdentChar(char c){
    return (c >= 'a' && c <= 'z')
      || (c >= 'A' && c <= 'Z')
      || (c >= '0' && c <= '9')
      || c == '_';
  }

  public static boolean isIdentLeadingChar(char c){
    return (c >= 'a' && c <= 'z')
      || (c >= 'A' && c <= 'Z')
      || c == '_';
  }

  public static boolean isDigit(char c){
    return c >= '0' && c <= '9';
  }

  public static boolean isSymbol(char c){
    return (c >= '!' && c <= '/')
      || (c >= ':' && c <= '@')
      || (c >= '[' && c <= '`')
      || (c >= '{' && c <= '~'); 
  }

  public static boolean isWhiteSpace(char c){
    return c <= ' ' || c  == '\u007f'; // 0x7f = DEL
  }
  
}
