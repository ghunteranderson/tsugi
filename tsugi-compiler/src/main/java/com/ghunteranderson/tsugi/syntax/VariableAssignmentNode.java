package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VariableAssignmentNode extends StatementNode {

  private String name;
  private ExpressionNode value;

  public VariableAssignmentNode(SourceLocation location){
    super("variable_assignment", location);
  }

  @Override
  public void acceptCodeVisitor(AstCodeVisitor visitor) {
    value.acceptCodeVisitor(visitor);
    visitor.visitStatement(this);
  }


}
