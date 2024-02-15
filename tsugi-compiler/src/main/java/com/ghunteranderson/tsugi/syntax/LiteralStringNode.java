package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

public class LiteralStringNode extends ExpressionNode {
  public static final String EXPRESSION_TYPE = "string";

  public String value;

  public LiteralStringNode(String value, SourceLocation location) {
    super(EXPRESSION_TYPE, location);
    this.value = value;
  }

  @Override
  public void acceptVisitor(AstVisitor visitor) {
    visitor.visit(this);
  }
}
