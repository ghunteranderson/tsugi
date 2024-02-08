package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VariableDeclarationNode extends StatementNode {

  private QualifiedRefNode type;
  private String name;

  public VariableDeclarationNode(SourceLocation location){
    super("variable_declaration", location);
  }
  
}
