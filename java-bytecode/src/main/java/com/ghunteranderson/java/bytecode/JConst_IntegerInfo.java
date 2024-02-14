package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_IntegerInfo extends JConst_NumberInfo {

  public int value;

  public JConst_IntegerInfo() {
    super(JConstTag.CONSTANT_Integer);
  }
}
