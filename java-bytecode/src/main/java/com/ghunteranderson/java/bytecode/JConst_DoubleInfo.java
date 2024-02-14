package com.ghunteranderson.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_DoubleInfo extends JConst_NumberInfo {

  public double value;

  public JConst_DoubleInfo() {
    super(JConstTag.CONSTANT_Integer);
  }
}
