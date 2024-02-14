package com.ghunteranderson.java.bytecode;

public sealed class JAttr permits JAttr_ConstantValue, JAttr_Code {

    public final String attributeName;
    
    public JAttr(String attributeName){
      this.attributeName = attributeName;
    }
}
