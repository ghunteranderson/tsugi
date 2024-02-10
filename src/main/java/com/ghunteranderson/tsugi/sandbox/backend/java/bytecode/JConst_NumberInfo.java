package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = true)
public sealed class JConst_NumberInfo extends JConst_Info permits
    JConst_DoubleInfo,
    JConst_FloatInfo,
    JConst_LongInfo,
    JConst_IntegerInfo {

    public JConst_NumberInfo(JConstTag tag) {
        super(tag);
    }
}
