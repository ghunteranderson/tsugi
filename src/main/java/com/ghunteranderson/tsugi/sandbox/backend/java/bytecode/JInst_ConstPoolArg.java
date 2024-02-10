package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

public class JInst_ConstPoolArg extends JInst {
  public JConst_Info constant;

  public JInst_ConstPoolArg(OpCode opcode, JConst_Info constant) {
    super(opcode);
    this.constant = constant;
  }
}