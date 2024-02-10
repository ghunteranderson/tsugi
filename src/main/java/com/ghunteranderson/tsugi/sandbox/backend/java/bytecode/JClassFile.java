package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import java.util.LinkedList;
import java.util.List;

public class JClassFile {
  public List<JAccessFlags> accessFlags = new LinkedList<>();
  public JConst_Info thisClass;
  public JConst_Info superClass;
  public List<JConst_Info> interfaces = new LinkedList<>();
  public List<JFieldInfo> fields = new LinkedList<>();
  public List<JMethodInfo> methods = new LinkedList<>();
  public List<JAttr_Info> attributes = new LinkedList<>();
}
