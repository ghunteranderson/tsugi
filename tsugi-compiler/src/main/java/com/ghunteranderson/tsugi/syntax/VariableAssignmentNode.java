package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

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
