package com.ghunteranderson.tsugi.syntax;

import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.ModuleSymbol;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ModuleNode implements AstVisitable {
  private String name;
  private SourceLocation location;
  private List<QualifiedRefNode> imports = new LinkedList<>();
  private List<FunctionDeclarationNode> functions = new LinkedList<>();
  private ModuleSymbol symbol;
  

  public ModuleNode(String name, SourceLocation location){
    this.name = name;
    this.location = location;
  }

  @Override
  public void acceptVisitor(AstVisitor v) {
    v.visit(this);
    for(var func : functions){
      func.acceptVisitor(v);
    }
  }
}
