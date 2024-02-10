package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

public class JInst_LocalArg extends JInst {
  
  public int local;

  public JInst_LocalArg(OpCode opcode, int local) {
    super(opcode);
    this.local = local;
  }
}