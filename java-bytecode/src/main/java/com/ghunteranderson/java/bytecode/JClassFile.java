package com.ghunteranderson.java.bytecode;

import java.util.LinkedList;
import java.util.List;

public class JClassFile {
  public List<JAccessFlags> accessFlags = new LinkedList<>();
  public JConst thisClass;
  public JConst superClass;
  public List<JConst> interfaces = new LinkedList<>();
  public List<JFieldInfo> fields = new LinkedList<>();
  public List<JMethodInfo> methods = new LinkedList<>();
  public List<JAttr> attributes = new LinkedList<>();
}
