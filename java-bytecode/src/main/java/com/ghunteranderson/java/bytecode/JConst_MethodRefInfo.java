package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_MethodRefInfo extends JConst {

  public JConst_ClassInfo clazz;
  public JConst_NameAndTypeInfo nameAndType;

  public JConst_MethodRefInfo() {
    super(JConstTag.CONSTANT_Methodref);
  }

  public JConst_MethodRefInfo(JConst_ClassInfo clazz, JConst_NameAndTypeInfo nameAndType){
    this();
    this.clazz = clazz;
    this.nameAndType = nameAndType;
  }

  public JConst_MethodRefInfo(String clazz, String methodName, String methodDescriptor){
    this(
      new JConst_ClassInfo(clazz),
      new JConst_NameAndTypeInfo(methodName, methodDescriptor)
    );
  }

  

}
