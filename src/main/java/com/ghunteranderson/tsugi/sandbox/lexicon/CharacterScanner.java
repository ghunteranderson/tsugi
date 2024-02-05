package com.ghunteranderson.tsugi.sandbox.lexicon;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.ghunteranderson.tsugi.sandbox.ErrorCode;
import com.ghunteranderson.tsugi.sandbox.TsugiCompilerException;

public class CharacterScanner implements Closeable {

  private final Scanner in;
  private Character next = null;
  private int row = 1;
  private int col = 0; // This is immediately incremented when the first character is read

  public CharacterScanner(InputStream in){
    this.in = new Scanner(in, StandardCharsets.UTF_8);
    this.in.useDelimiter("");
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
      next = in.next().charAt(0);
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

  @Override
  public void close() throws IOException {
    this.in.close();
  }

  private TsugiCompilerException buildEndOfSourceException(){
    throw new TsugiCompilerException(ErrorCode.END_OF_SOURCE, new SourceLocation(row, col));
  }

  
}
