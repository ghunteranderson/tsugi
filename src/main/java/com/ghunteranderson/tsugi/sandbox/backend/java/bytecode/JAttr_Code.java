package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import java.util.LinkedList;
import java.util.List;

public final class JAttr_Code extends JAttr_Info {

  public short maxStack;
  public short maxLocals;
  public List<JInst> code = new LinkedList<>();
  public List<JException> exceptionTable = new LinkedList<>();
  public List<JAttr_Info> attributes = new LinkedList<>();

  public JAttr_Code() {
    super("Code");
  }

  public static class JException {
    public short startPc;
    public short endPc;
    public short handlerPc;
    public JConst_ClassInfo catchType;
  }
}
