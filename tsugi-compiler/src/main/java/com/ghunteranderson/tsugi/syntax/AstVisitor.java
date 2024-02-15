package com.ghunteranderson.tsugi.syntax;

public interface AstVisitor {
  // Global Elements
  void visit(ModuleNode moduleNode);
  void visit(FunctionDeclarationNode func);

  // Statements
  void visit(VariableDeclarationNode node);
  void visit(VariableAssignmentNode node);
  void visit(FunctionStatementNode node);
  void visit(VariableExpressionNode node);

  // Expressions
  void visit(FunctionExpressionNode node);
  void visit(LiteralNumberNode literalNumberNode);
  void visit(LiteralStringNode literalStringNode);
  void visit(OperationExpressionNode operationExpressionNode);
}
