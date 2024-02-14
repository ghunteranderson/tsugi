package com.ghunteranderson.tsugi;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Getter;

@Getter
public class TsugiCompilerException extends RuntimeException {

  private final ErrorCode code;
  private final SourceLocation location;

  public TsugiCompilerException(ErrorCode code, SourceLocation location){
    this(code, location, code.getDefaultMessage());
  }

  public TsugiCompilerException(ErrorCode code, SourceLocation location, String message){
    super(message);
    this.code = code;
    this.location = location;
  }

  public TsugiCompilerException(ErrorCode code, SourceLocation location, Throwable ex){
    this(code, location, code.getDefaultMessage(), ex);
  }

  public TsugiCompilerException(ErrorCode code, SourceLocation location, String message, Throwable ex){
    super(message, ex);
    this.code = code;
    this.location = location;
  }

}
