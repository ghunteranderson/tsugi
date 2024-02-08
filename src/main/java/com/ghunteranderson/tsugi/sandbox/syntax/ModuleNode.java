package com.ghunteranderson.tsugi.sandbox.syntax;

import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleNode extends GenericNode {
  private String name;
  private List<QualifiedRefNode> imports = new LinkedList<>();
  private List<FunctionDeclarationNode> functions = new LinkedList<>();
  

  public ModuleNode(String name, SourceLocation location){
    super(location);
    this.name = name;
  }
}
