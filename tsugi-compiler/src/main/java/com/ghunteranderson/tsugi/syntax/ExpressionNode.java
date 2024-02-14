package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExpressionNode extends GenericNode {

  private final String expressionType;

  public ExpressionNode(String expressionType, SourceLocation location){
    super(location);
    this.expressionType = expressionType;
  }

}
