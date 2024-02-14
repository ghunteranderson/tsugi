package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_MethodTypeInfo extends JConst {

  public JConst_Utf8Info descriptor;

  public JConst_MethodTypeInfo(JConstTag tag) {
    super(JConstTag.CONSTANT_MethodType);
  }
}