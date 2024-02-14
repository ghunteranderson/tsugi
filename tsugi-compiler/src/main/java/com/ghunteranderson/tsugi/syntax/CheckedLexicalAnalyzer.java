package com.ghunteranderson.tsugi.syntax;

import java.io.InputStream;

import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TsugiCompilerException;
import com.ghunteranderson.tsugi.lexicon.Lexeme;
import com.ghunteranderson.tsugi.lexicon.LexicalAnalyzer;
import com.ghunteranderson.tsugi.lexicon.Token;

public class CheckedLexicalAnalyzer {

  private final LexicalAnalyzer in;

  public CheckedLexicalAnalyzer(InputStream source){
    in = new LexicalAnalyzer(source);
  }

  public Lexeme next(){
    return in.next();
  }

  public Lexeme peek() {
    return in.peek();
  }

  public Lexeme next(Token expectedToken){
    var next = in.next();
    if(next.token() != expectedToken){
      throw new TsugiCompilerException(
        ErrorCode.INVALID_SYNTAX,
        next.location(),
        "Expected a " + expectedToken + " but found token: " + next.lexeme());
    }
    return next;
  }
  
}
