package com.ghunteranderson.tsugi.syntax;

import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatementBlockNode extends StatementNode {

  public StatementBlockNode(SourceLocation location) {
    super("block_statement", location);
  }

  private List<StatementNode> statements = new LinkedList<>();

  @Override
  public void acceptCodeVisitor(AstCodeVisitor visitor) {
    visitor.visitBlock(this);
    statements.forEach(s -> s.acceptCodeVisitor(visitor));
    visitor.leaveBlock(this);
  }

}
