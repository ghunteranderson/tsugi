package com.ghunteranderson.tsugi.sandbox.syntax;

import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Getter;

@Getter
public class StatementBlockNode extends GenericNode {
  
  private List<StatementNode> statements;

  public StatementBlockNode(SourceLocation location){
    super(location);
    statements = new LinkedList<>();
  }

}