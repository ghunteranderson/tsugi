package com.ghunteranderson.tsugi.sandbox.syntax;

import java.util.List;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionDeclarationNode extends GenericNode {

  private String name;
  private QualifiedRefNode returnType;
  private List<StatementNode> statements;
  

  public FunctionDeclarationNode(String name, SourceLocation location){
    super(location);
    this.name = name;
  }
  
}
