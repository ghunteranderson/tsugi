package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.TypeSymbol;
import com.ghunteranderson.tsugi.syntax.AstCodeVisitor.AstCodeVisitable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class ExpressionNode implements AstCodeVisitable {

  private final String expressionType;
  private SourceLocation location;
  private TypeSymbol typeSymbol;

  public ExpressionNode(String expressionType, SourceLocation location){
    this.expressionType = expressionType;
    this.location = location;
  }

}
