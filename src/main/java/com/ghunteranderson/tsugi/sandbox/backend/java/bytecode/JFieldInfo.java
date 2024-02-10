package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import java.util.List;

public class JFieldInfo {
  public List<JAccessFlags> accessFlags;
  public JConst_Utf8Info name;
  public JConst_Utf8Info descriptor;
  public List<JAttr_Info> attributes;
}