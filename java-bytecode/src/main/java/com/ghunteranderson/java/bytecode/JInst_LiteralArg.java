package com.ghunteranderson.java.bytecode;

public class JInst_LiteralArg extends JInst {

  public int arg;

  public JInst_LiteralArg(OpCode opcode, int arg) {
    super(opcode);
    this.arg = arg;
  }
}