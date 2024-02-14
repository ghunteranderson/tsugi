package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_NameAndTypeInfo extends JConst {

  public JConst_Utf8Info name;
  public JConst_Utf8Info descriptor;

  public JConst_NameAndTypeInfo() {
    super(JConstTag.CONSTANT_NameAndType);
  }

  public JConst_NameAndTypeInfo(JConst_Utf8Info name, JConst_Utf8Info descriptor){
    this();
    this.name = name;
    this.descriptor = descriptor;
  }

  public JConst_NameAndTypeInfo(String name, String descriptor){
    this();
    this.name = new JConst_Utf8Info(name);
    this.descriptor = new JConst_Utf8Info(descriptor);
  }

}
