package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_InterfaceMethodRefInfo extends JConst_Info {

  public JConst_ClassInfo clazz;
  public JConst_NameAndTypeInfo nameAndType;

  public JConst_InterfaceMethodRefInfo() {
    super(JConstTag.CONSTANT_InterfaceMethodref);
  }

}
