package com.ghunteranderson.java.bytecode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
enum OpCode {
  _astore(0x3a),
  _dstore(0x39),
  _fstore(0x38),
  _getstatic(0xb2),
  _invokevirutal(0xb6),
  _istore(0x36),
  //_ldc(0x12), // Ignoring the complexity of U1 constant pool indexes. For now, everything is a u2 reference
  _ldc_w(0x13),
  _lstore(0x37),
  _return(0xb1),
  ;
  public final int code;
}
