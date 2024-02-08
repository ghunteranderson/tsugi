package com.ghunteranderson.tsugi.sandbox.syntax;

import java.util.ArrayList;
import java.util.List;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionStatementNode extends StatementNode {
  
  private QualifiedRefNode ref;
  private List<ExpressionNode> args;

  public FunctionStatementNode(SourceLocation location){
    super("function", location);
    args = new ArrayList<>();
  } 

}
