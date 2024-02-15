package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.syntax.AstCodeVisitor.AstCodeVisitable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class StatementNode implements AstCodeVisitable {

  private final String statementType;
  private SourceLocation location;

  public StatementNode(String statementType, SourceLocation location){
    this.location = location;
    this.statementType = statementType;
  }
  
}
