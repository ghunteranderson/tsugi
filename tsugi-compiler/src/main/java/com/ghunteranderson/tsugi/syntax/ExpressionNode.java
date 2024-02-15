package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.TypeSymbol;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class ExpressionNode implements AstVisitable {

  private final String expressionType;
  private SourceLocation location;
  private TypeSymbol typeSymbol;

  public ExpressionNode(String expressionType, SourceLocation location){
    this.expressionType = expressionType;
    this.location = location;
  }

}
