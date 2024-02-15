package com.ghunteranderson.java.bytecode;

public class JInst_LiteralArg extends JInst {

  public int arg;
  public int size;

  public JInst_LiteralArg(OpCode opcode, int arg, int argLength) {
    super(opcode);
    this.arg = arg;
  }
}