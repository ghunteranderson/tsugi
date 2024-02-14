package com.ghunteranderson.java.bytecode;

import java.util.LinkedList;
import java.util.List;

public final class JAttr_Code extends JAttr {

  public int maxStack;
  public int maxLocals;
  public List<JInst> code = new LinkedList<>();
  public List<JException> exceptionTable = new LinkedList<>();
  public List<JAttr> attributes = new LinkedList<>();

  public JAttr_Code() {
    super("Code");
  }

  public static class JException {
    public int startPc;
    public int endPc;
    public int handlerPc;
    public JConst_ClassInfo catchType;
  }
}
