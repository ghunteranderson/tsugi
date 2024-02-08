package com.ghunteranderson.tsugi.sandbox.syntax;

import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionExpressionNode extends ExpressionNode {
  
  public static final String EXPRESSION_TYPE = "invocation";
  
  private QualifiedRefNode ref;
  private List<ExpressionNode> args;
  
  public FunctionExpressionNode(SourceLocation location) {
    super(EXPRESSION_TYPE, location);
    args = new LinkedList<>();
  }

}
