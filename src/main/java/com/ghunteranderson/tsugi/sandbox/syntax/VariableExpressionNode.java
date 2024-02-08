package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

public class VariableExpressionNode extends ExpressionNode {

  public QualifiedRefNode ref;

  public VariableExpressionNode(SourceLocation location, QualifiedRefNode ref) {
    super("variable", location);
    this.ref = ref;
  }
}
