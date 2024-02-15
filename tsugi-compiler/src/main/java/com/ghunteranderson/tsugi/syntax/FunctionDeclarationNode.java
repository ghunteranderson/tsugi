package com.ghunteranderson.tsugi.syntax;

import java.util.List;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.FunctionSymbol;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class FunctionDeclarationNode implements AstVisitable {

  private ModuleNode parent;
  private SourceLocation location;
  private String name;
  private QualifiedRefNode returnType;
  private List<StatementNode> statements;
  private FunctionSymbol symbol;
  
  public FunctionDeclarationNode(ModuleNode parent, String name, SourceLocation location){
    this.parent = parent;
    this.name = name;
  }

  @Override
  public void acceptVisitor(AstVisitor v) {
    v.visit(this);
    for (var s : statements)
      s.acceptVisitor(v);

  }

}
