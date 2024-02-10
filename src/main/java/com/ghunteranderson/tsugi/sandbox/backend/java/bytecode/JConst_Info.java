package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public sealed class JConst_Info permits
    JConst_ClassInfo, JConst_Utf8Info, JConst_InterfaceMethodRefInfo, 
    JConst_StringInfo, JConst_NumberInfo, JConst_NameAndTypeInfo,
    JConst_MethodHandleInfo, JConst_MethodTypeInfo, JConst_InvokeDynamicInfo,
    JConst_MethodRefInfo, JConst_FieldRefInfo {

  public final JConstTag tag;

  @Getter
  @RequiredArgsConstructor
  public enum JConstTag {
    CONSTANT_Class((byte) 7),
    CONSTANT_Fieldref((byte) 9),
    CONSTANT_Methodref((byte) 10),
    CONSTANT_InterfaceMethodref((byte) 11),
    CONSTANT_String((byte) 8),
    CONSTANT_Integer((byte) 3),
    CONSTANT_Float((byte) 4),
    CONSTANT_Long((byte) 5),
    CONSTANT_Double((byte) 6),
    CONSTANT_NameAndType((byte) 12),
    CONSTANT_Utf8((byte) 1),
    CONSTANT_MethodHandle((byte) 15),
    CONSTANT_MethodType((byte) 16),
    CONSTANT_InvokeDynamic((byte) 18);

    private final byte code;
  }

}
