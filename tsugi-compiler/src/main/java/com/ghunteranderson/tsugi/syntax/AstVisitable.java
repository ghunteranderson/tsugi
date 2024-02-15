package com.ghunteranderson.tsugi.syntax;

public interface AstVisitable {
  void acceptVisitor(AstVisitor visitor);
}
