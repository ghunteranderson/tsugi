package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JAccessFlags {
  ACC_PUBLIC((short) 0x0001),
  ACC_PRIVATE((short) 0x0002),
  ACC_PROTECTED((short) 0x0004),
  ACC_STATIC((short) 0x0008),

  ACC_FINAL((short) 0x0010),
  ACC_SUPER((short) 0x0020),
  ACC_VOLATILE((short)0x0040),
  ACC_TRANSIENT((short)0x0080),
  
  ACC_NATIVE((short) 0x0100),
  ACC_INTERFACE((short) 0x0200),
  ACC_ABSTRACT((short) 0x0400),
  ACC_STRICT((short) 0x0800),
  
  ACC_SYNTHETIC((short) 0x1000),
  ACC_ANNOTATION((short) 0x2000),
  ACC_ENUM((short) 0x4000),
  //ACC_TBD((short) 0x8000),
  ;

  private final short flag;
}
