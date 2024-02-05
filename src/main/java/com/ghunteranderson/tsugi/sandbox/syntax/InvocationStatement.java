package com.ghunteranderson.tsugi.sandbox.syntax;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvocationStatement extends StatementNode {

  public static final String STATEMENT_TYPE = "invocation";

  private FunctionInvocationNode invocation;

  public InvocationStatement(FunctionInvocationNode invocation) {
    super(STATEMENT_TYPE, invocation.getLocation());
    this.invocation = invocation;
  }
  
}
