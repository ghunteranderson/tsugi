package com.ghunteranderson.tsugi.semantics;

import java.util.List;

import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TsugiCompilerException;
import com.ghunteranderson.tsugi.semantics.SymbolTable.TypeSymbol;
import com.ghunteranderson.tsugi.semantics.SymbolTable.ModuleSymbol;
import com.ghunteranderson.tsugi.semantics.SymbolTable.FunctionSymbol;
import com.ghunteranderson.tsugi.syntax.AstVisitor;
import com.ghunteranderson.tsugi.syntax.FunctionDeclarationNode;
import com.ghunteranderson.tsugi.syntax.FunctionExpressionNode;
import com.ghunteranderson.tsugi.syntax.FunctionStatementNode;
import com.ghunteranderson.tsugi.syntax.LiteralNumberNode;
import com.ghunteranderson.tsugi.syntax.LiteralStringNode;
import com.ghunteranderson.tsugi.syntax.ModuleNode;
import com.ghunteranderson.tsugi.syntax.OperationExpressionNode;
import com.ghunteranderson.tsugi.syntax.QualifiedRefNode;
import com.ghunteranderson.tsugi.syntax.VariableAssignmentNode;
import com.ghunteranderson.tsugi.syntax.VariableDeclarationNode;
import com.ghunteranderson.tsugi.syntax.VariableExpressionNode;

import lombok.RequiredArgsConstructor;

public class AstGlobalSymbolAnalyzer {

  /**
   * Compile all of the declared global symbols into a symbol table for reference.
   * This includes all modules, functions, etc. 
   * 
   * This does not include local variables or parameters of a function.
   * @param modules
   * @return
   */
  public SymbolTable buildSymbolTable(List<ModuleNode> modules){
    var table = new SymbolTable();
    
    // Symbol analysis is broken into two passes removing the need to forward declare
    // - The first pass gathers data on declared moduels, types, and functions
    // - The second pass walks though code with knowledge of the symbols declared outside the function

    // build global 
    var visitor1 = new AstGlobalAnalysisVisitor(table);
    modules.forEach(m -> visitor1.visit(m));

    return table;
  }

  @RequiredArgsConstructor
  private static class AstGlobalAnalysisVisitor implements AstVisitor {
    private final SymbolTable table;

    @Override
    public void visit(ModuleNode moduleNode) {
      var module = new ModuleSymbol(moduleNode.getName());
      table.registerModule(module);
      moduleNode.setSymbol(module);
    }

    @Override
    public void visit(FunctionDeclarationNode func) {
      var returnType = lookupType(table, func.getReturnType());
      var qualifiedFuncName = buildQualifiedName(func);
      var funcSymbol = FunctionSymbol.builder()
        .parent(func.getParent().getSymbol())
        .returnType(returnType)
        .qualifiedName(qualifiedFuncName)
        .parameterTypes(List.of())
        .build();
      table.registerFunction(funcSymbol);
      func.setSymbol(funcSymbol);
    }

    @Override
    public void visit(VariableAssignmentNode variableAssignmentNode) {
    }

    @Override
    public void visit(VariableDeclarationNode variableDeclarationNode) {
    }

    @Override
    public void visit(FunctionStatementNode functionStatementNode) {
    }

    @Override
    public void visit(VariableExpressionNode node) {
    }

    @Override
    public void visit(FunctionExpressionNode node) {
    }

    @Override
    public void visit(LiteralNumberNode literalNumberNode) {
    }

    @Override
    public void visit(LiteralStringNode literalStringNode) {
    }

    @Override
    public void visit(OperationExpressionNode operationExpressionNode) {
    }
  }

  private static TypeSymbol lookupType(SymbolTable table, QualifiedRefNode typeRef){
    var qualifiedName = TypeUtils.qualifiedRefToString(typeRef);
    var type = table.getType(qualifiedName);
    if(type.isEmpty())
      throw new TsugiCompilerException(ErrorCode.INVALID_SEMANTICS, typeRef.getLocation(), "Undefined type: "  + qualifiedName);
    else
      return type.get();
  }

  private static String buildQualifiedName(FunctionDeclarationNode function){
    return function.getParent().getSymbol().qualifiedName + "." + function.getName();
  }
  
}
