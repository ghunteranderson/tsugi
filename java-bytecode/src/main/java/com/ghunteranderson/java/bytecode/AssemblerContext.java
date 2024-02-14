package com.ghunteranderson.java.bytecode;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
class AssemblerContext {
  JClassFile classFile;
  ConstantPool constantPool;
}