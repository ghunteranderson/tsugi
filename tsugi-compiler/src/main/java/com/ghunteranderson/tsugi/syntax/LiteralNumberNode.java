package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LiteralNumberNode extends ExpressionNode {

  private String value;

  public LiteralNumberNode(String value, SourceLocation location) {
    super("number", location);
    this.value = value;
  }

  @Override
  public void acceptVisitor(AstVisitor visitor) {
    visitor.visit(this);
  }
}
