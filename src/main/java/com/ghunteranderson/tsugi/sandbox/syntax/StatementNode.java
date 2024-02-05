package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatementNode extends GenericNode {

  private final String statementType;

  public StatementNode(String statementType, SourceLocation location){
    super(location);
    this.statementType = statementType;
  }
  
}
