package com.ghunteranderson.java.bytecode;

public class JInst_ConstPoolArg extends JInst {
  public JConst constant;

  public JInst_ConstPoolArg(OpCode opcode, JConst constant) {
    super(opcode);
    this.constant = constant;
  }
}