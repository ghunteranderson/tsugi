package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

public class VariableExpressionNode extends ExpressionNode {

  public QualifiedRefNode ref;

  public VariableExpressionNode(SourceLocation location, QualifiedRefNode ref) {
    super("variable", location);
    this.ref = ref;
  }
}
