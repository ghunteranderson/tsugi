package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_Utf8Info extends JConst_Info {
  
  public String value;
  
  public JConst_Utf8Info() {
    super(JConstTag.CONSTANT_Utf8);
  }

  public JConst_Utf8Info(String value){
    this();
    this.value = value;
  }

}
