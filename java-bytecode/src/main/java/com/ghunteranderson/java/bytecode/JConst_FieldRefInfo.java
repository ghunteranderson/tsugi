package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_FieldRefInfo extends JConst {

  public JConst_ClassInfo clazz;
  public JConst_NameAndTypeInfo nameAndType;

  public JConst_FieldRefInfo() {
    super(JConstTag.CONSTANT_Fieldref);
  }

  public JConst_FieldRefInfo(JConst_ClassInfo clazz, JConst_NameAndTypeInfo nameAndType) {
    this();
    this.clazz = clazz;
    this.nameAndType = nameAndType;
  }

  public JConst_FieldRefInfo(String clazz, String fieldName, String fieldDescriptor){
    this(new JConst_ClassInfo(clazz), new JConst_NameAndTypeInfo(fieldName, fieldDescriptor));
  }
}
