package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_FloatInfo extends JConst_NumberInfo {

  public float value;

  public JConst_FloatInfo() {
    super(JConstTag.CONSTANT_Float);
  }
}
