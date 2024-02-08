package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

public class LiteralNumberNode extends ExpressionNode {

  public String value;

  public LiteralNumberNode(String value, SourceLocation location) {
    super("number", location);
    this.value = value;
  }
}
