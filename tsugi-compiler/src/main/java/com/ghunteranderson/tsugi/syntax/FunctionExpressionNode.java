package com.ghunteranderson.tsugi.syntax;

import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.FunctionSymbol;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionExpressionNode extends ExpressionNode {
  
  public static final String EXPRESSION_TYPE = "invocation";
  
  private QualifiedRefNode ref;
  private List<ExpressionNode> args;
  private FunctionSymbol functionSymbol;
  
  public FunctionExpressionNode(SourceLocation location) {
    super(EXPRESSION_TYPE, location);
    args = new LinkedList<>();
  }

  @Override
  public void acceptCodeVisitor(AstCodeVisitor visitor) {
    visitor.visitExpression(this);
  }


}
