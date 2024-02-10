package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public final class JConst_LongInfo extends JConst_NumberInfo {

  public long value;

  public JConst_LongInfo() {
    super(JConstTag.CONSTANT_Long);
  }

}
