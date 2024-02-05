package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionNode extends GenericNode {

  private String name;
  private String returnType;
  private StatementBlockNode body;
  

  public FunctionNode(String name, SourceLocation location){
    super(location);
    this.name = name;
  }
  
}
