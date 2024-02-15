package com.ghunteranderson.tsugi.syntax;

public interface AstElementVisitor {
  // Global Elements
  void visitModule(ModuleNode module);
  void visitFunction(FunctionDeclarationNode func);

  public interface AstElementVisitable {
    void acceptElementVisitor(AstElementVisitor visitor);
  }
}
