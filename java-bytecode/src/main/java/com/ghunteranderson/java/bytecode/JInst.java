package com.ghunteranderson.java.bytecode;

public abstract class JInst {

  public OpCode opcode;

  public JInst(OpCode opcode) {
    this.opcode = opcode;
  }

  public static JInst_LiteralArg _astore(int index){
    return new JInst_LiteralArg(OpCode._astore, index, 1);
  }

  public static JInst_LiteralArg _dstore(int index){
    return new JInst_LiteralArg(OpCode._dstore, index, 1);
  }

  public static JInst_LiteralArg _fstore(int index){
    return new JInst_LiteralArg(OpCode._fstore, index, 1);
  }

  public static JInst_ConstPoolArg _getstatic(JConst_FieldRefInfo field){
    return new JInst_ConstPoolArg(OpCode._getstatic, field);
  }

  public static JInst_ConstPoolArg _getstatic(String clazz, String fieldName, String fieldDescriptor){
    return JInst._getstatic(new JConst_FieldRefInfo(clazz, fieldName, fieldDescriptor));
  }

  public static JInst_ConstPoolArg _invokevirtual(JConst_MethodRefInfo method){
    return new JInst_ConstPoolArg(OpCode._invokevirutal, method);
  }

  public static JInst_ConstPoolArg _invokevirtual(String clazz, String methodName, String methodDescriptor){
    return JInst._invokevirtual(new JConst_MethodRefInfo(clazz, methodName, methodDescriptor));
  }

  public static JInst_LiteralArg _istore(int index){
    return new JInst_LiteralArg(OpCode._istore, index, 1);
  }

  public static JInst_ConstPoolArg _ldc_w(JConst constant){
    return new JInst_ConstPoolArg(OpCode._ldc_w, constant);
  }
  
  public static JInst_ConstPoolArg _ldc_w(String string){
    return JInst._ldc_w(new JConst_StringInfo(string));
  }

  public static JInst_LiteralArg _lstore(int index){
    return new JInst_LiteralArg(OpCode._lstore, index, 1);
  }

  public static JInst_NoArgs _return(){
    return new JInst_NoArgs(OpCode._return);
  }


}
