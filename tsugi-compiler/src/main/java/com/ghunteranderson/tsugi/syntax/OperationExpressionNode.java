package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OperationExpressionNode extends ExpressionNode {

  public OperationType op;
  public ExpressionNode left;
  public ExpressionNode right;

  public OperationExpressionNode(SourceLocation location) {
    super("operation", location);
  }

  public enum OperationType {
    ADD,
    SUB,
    MUL,
    DIV
  }
  
}
