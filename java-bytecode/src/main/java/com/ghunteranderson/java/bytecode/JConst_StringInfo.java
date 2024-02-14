package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_StringInfo extends JConst {

  public JConst_Utf8Info string;
  
  public JConst_StringInfo() {
    super(JConstTag.CONSTANT_String);
  }

  public JConst_StringInfo(String string){
    this();
    this.string = new JConst_Utf8Info(string);
  }
}
