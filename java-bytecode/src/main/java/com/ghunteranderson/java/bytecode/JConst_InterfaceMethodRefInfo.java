package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_InterfaceMethodRefInfo extends JConst {

  public JConst_ClassInfo clazz;
  public JConst_NameAndTypeInfo nameAndType;

  public JConst_InterfaceMethodRefInfo() {
    super(JConstTag.CONSTANT_InterfaceMethodref);
  }

}
