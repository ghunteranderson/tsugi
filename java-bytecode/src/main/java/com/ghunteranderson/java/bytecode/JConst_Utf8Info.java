package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_Utf8Info extends JConst {
  
  public String value;
  
  public JConst_Utf8Info() {
    super(JConstTag.CONSTANT_Utf8);
  }

  public JConst_Utf8Info(String value){
    this();
    this.value = value;
  }

}
