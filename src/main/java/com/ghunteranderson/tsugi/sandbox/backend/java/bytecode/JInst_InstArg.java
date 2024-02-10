package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

public class JInst_InstArg extends JInst {
  
  public JInst instruction;

  public JInst_InstArg(OpCode opcode, JInst instruction) {
    super(opcode);
    this.instruction = instruction;
  }
}