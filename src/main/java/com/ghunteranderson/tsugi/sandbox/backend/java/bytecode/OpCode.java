package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum OpCode {
  _getstatic(0xb2),
  _invokevirutal(0xb6),
  //_ldc(0x12), // Ignoring the complexity of U1 constant pool indexes. For now, everything is a u2 reference
  _ldc_w(0x13),
  _return(0xb1),
  ;
  public final int code;
}
