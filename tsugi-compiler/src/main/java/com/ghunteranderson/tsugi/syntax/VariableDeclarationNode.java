package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

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

  @Override
  public void acceptVisitor(AstVisitor visitor) {
    visitor.visit(this);
  }

}
