package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
class AssemblerContext {
  JClassFile classFile;
  ConstantPool constantPool;
}