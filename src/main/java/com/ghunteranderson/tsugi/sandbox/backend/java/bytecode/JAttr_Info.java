package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

public sealed class JAttr_Info permits JAttr_ConstantValue, JAttr_Code {

    public final String attributeName;
    
    public JAttr_Info(String attributeName){
      this.attributeName = attributeName;
    }
}
