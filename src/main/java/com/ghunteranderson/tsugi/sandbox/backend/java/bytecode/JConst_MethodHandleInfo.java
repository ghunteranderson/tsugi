package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_MethodHandleInfo extends JConst_Info {

  public JMethodHandleKind kind;
  public JConst_InterfaceMethodRefInfo reference;


  public JConst_MethodHandleInfo() {
    super(JConstTag.CONSTANT_MethodHandle);
  }

  @Getter
  @RequiredArgsConstructor
  public enum JMethodHandleKind {
    REF_getField(1),
    REF_getStatic(2),
    REF_putField(3),
    REF_putStatic(4),
    REF_invokeVirtual(5),
    REF_invokeStatic(6),
    REF_invokeSpecial(7),
    REF_newInvokeSpecial(8),
    REF_invokeInterface(9);

    private final int value;
  }

}
