package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VariableAssignmentNode extends StatementNode {

  private QualifiedRefNode ref;
  private ExpressionNode value;

  public VariableAssignmentNode(SourceLocation location){
    super("variable_declaration", location);
  }
  
}
