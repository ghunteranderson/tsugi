package com.ghunteranderson.tsugi.sandbox;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

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

}
