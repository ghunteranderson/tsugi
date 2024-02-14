package com.ghunteranderson.tsugi.lexicon;

import java.io.IOException;
import java.io.InputStream;

import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TsugiCompilerException;

public class CharacterScanner {

  private final CharacterInputStream in;
  private Character next = null;
  private int row = 1;
  private int col = 0; // This is immediately incremented when the first character is read

  public CharacterScanner(InputStream in){
    this.in = new Utf8CharacterInputStream(in);
    populateNext();
    skipWhiteSpace(); // Move cursor to first non-whitespace character in file
  }

  public char peek(){
    if(next == null)
      throw buildEndOfSourceException();
    return next;
  }

  public char next(){
    if(next == null)
      throw buildEndOfSourceException();
    var c = next;
    populateNext();
    return c;
  }

  public boolean hasNext(){
    return next != null;
  }

  public int getRow(){
    return row;
  }

  public int getCol(){
    return col;
  }

  private void populateNext(){
    if(!in.hasNext()){
      next = null;
      col++;
    }
    else{
      try{
        next = in.next();
      } catch(IOException ex){
        throw new TsugiCompilerException(ErrorCode.IO_ERROR, new SourceLocation(row, col), ex);
      }
      if(next == '\n'){
        row++;
        col = 0;
      }
      else
        col++;
    }
  }

  public void skipWhiteSpace(){
    while(next != null && LiteralUtils.isWhiteSpace(next)){
      populateNext();
    }
  }

  private TsugiCompilerException buildEndOfSourceException(){
    throw new TsugiCompilerException(ErrorCode.END_OF_SOURCE, new SourceLocation(row, col));
  }

  
}
