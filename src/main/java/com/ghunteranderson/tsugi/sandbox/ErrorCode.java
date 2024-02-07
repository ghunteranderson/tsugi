package com.ghunteranderson.tsugi.sandbox;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  END_OF_SOURCE(0001, "Unexpected end of source code."),
  LEXICAL_PANIC(0002, "Unexpected error reading next token."),
  INVALID_STRING(0003, "Invalid string format."),
  INVALID_NUMBER(0004, "Invalid string format."),
  INVALID_SYNTAX(0005, "Invalid syntax."),
  IO_ERROR(0006, "IO Exception."),
  ;
  private final int number;
  private final String defaultMessage;
}