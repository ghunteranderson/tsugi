package com.ghunteranderson.tsugi.syntax;

import java.util.ArrayList;
import java.util.List;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.FunctionSymbol;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FunctionStatementNode extends StatementNode {
  
  private QualifiedRefNode ref;
  private List<ExpressionNode> args;
  private FunctionSymbol functionSymbol;

  public FunctionStatementNode(SourceLocation location){
    super("function", location);
    args = new ArrayList<>();
  }

  @Override
  public void acceptCodeVisitor(AstCodeVisitor visitor) {
    for(var a : args)
      a.acceptCodeVisitor(visitor);
    visitor.visitStatement(this);
  }

  

}
