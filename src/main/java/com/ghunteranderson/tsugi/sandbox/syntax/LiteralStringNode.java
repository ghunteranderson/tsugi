package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

public class LiteralStringNode extends ExpressionNode {
  public static final String EXPRESSION_TYPE = "string";

  public String value;

  public LiteralStringNode(String value, SourceLocation location) {
    super(EXPRESSION_TYPE, location);
    this.value = value;
  }
}
