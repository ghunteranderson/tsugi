package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import java.util.LinkedList;
import java.util.List;

public class JMethodInfo {

  public List<JAccessFlags> accessFlags = new LinkedList<>();
  public JConst_Utf8Info name;
  public JConst_Utf8Info descriptor;
  public List<JAttr_Info> attributes = new LinkedList<>();
}
