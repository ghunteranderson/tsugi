package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

public class LiteralNumberNode extends ExpressionNode {

  public String value;

  public LiteralNumberNode(String value, SourceLocation location) {
    super("number", location);
    this.value = value;
  }
}
