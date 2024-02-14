package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_ClassInfo extends JConst {
  
  public JConst_Utf8Info name;
  
  public JConst_ClassInfo(){
    super(JConstTag.CONSTANT_Class);
  }

  public JConst_ClassInfo(String className){
    this();
    this.name = new JConst_Utf8Info(className);
  }

}
