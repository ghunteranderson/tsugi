package com.ghunteranderson.tsugi.syntax;

public interface AstCodeVisitor {
  // Function
  void visitFunction(FunctionDeclarationNode func);
  void leaveFunction(FunctionDeclarationNode func);

  // Statements
  void visitStatement(VariableDeclarationNode statement);
  void visitStatement(VariableAssignmentNode statement);
  void visitStatement(FunctionStatementNode statement);
  
  // Expressions
  void visitExpression(FunctionExpressionNode node);
  void visitExpression(LiteralNumberNode literalNumberNode);
  void visitExpression(LiteralStringNode literalStringNode);
  void visitExpression(OperationExpressionNode operationExpressionNode);
  void visitExpression(VariableExpressionNode statement);

  // Blocks
  void visitBlock(StatementBlockNode block);
  void leaveBlock(StatementBlockNode block);

  public interface AstCodeVisitable {
    void acceptCodeVisitor(AstCodeVisitor visitor);
  }

}
