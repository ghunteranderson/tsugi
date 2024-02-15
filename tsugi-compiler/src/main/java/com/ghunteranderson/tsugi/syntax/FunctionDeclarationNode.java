package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.FunctionSymbol;
import com.ghunteranderson.tsugi.syntax.AstCodeVisitor.AstCodeVisitable;
import com.ghunteranderson.tsugi.syntax.AstElementVisitor.AstElementVisitable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class FunctionDeclarationNode implements AstElementVisitable, AstCodeVisitable {

  private ModuleNode parent;
  private SourceLocation location;
  private String name;
  private QualifiedRefNode returnType;
  private StatementBlockNode body;
  private FunctionSymbol symbol;
  
  public FunctionDeclarationNode(ModuleNode parent, String name, SourceLocation location){
    this.parent = parent;
    this.name = name;
  }

  @Override
  public void acceptElementVisitor(AstElementVisitor v) {
    v.visitFunction(this);
  }

  @Override
  public void acceptCodeVisitor(AstCodeVisitor visitor) {
    visitor.visitFunction(this);
    body.acceptCodeVisitor(visitor);
    visitor.leaveFunction(this);
  }

}
