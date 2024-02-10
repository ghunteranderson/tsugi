package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_InvokeDynamicInfo extends JConst_Info {

  public Object bootstrapMethod; // TODO: Change this type to JBootstrapMethod (actual name pending)
  public JConst_NameAndTypeInfo nameAndType;

  public JConst_InvokeDynamicInfo(JConstTag tag) {
    super(JConstTag.CONSTANT_InvokeDynamic);
  }

}
