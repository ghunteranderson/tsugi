package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

public class VariableExpressionNode extends ExpressionNode {

  public QualifiedRefNode name;

  public VariableExpressionNode(SourceLocation location, QualifiedRefNode name) {
    super("variable", location);
    this.name = name;
  }

  @Override
  public void acceptCodeVisitor(AstCodeVisitor visitor) {
    visitor.visitExpression(this);
  }
}
