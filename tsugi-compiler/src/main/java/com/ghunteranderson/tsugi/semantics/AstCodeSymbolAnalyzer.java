package com.ghunteranderson.tsugi.semantics;

import java.util.List;
import java.util.stream.Collectors;

import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TsugiCompilerException;
import com.ghunteranderson.tsugi.lexicon.SourceLocation;
import com.ghunteranderson.tsugi.semantics.SymbolTable.TypeSymbol;
import com.ghunteranderson.tsugi.syntax.AstVisitor;
import com.ghunteranderson.tsugi.syntax.FunctionDeclarationNode;
import com.ghunteranderson.tsugi.syntax.FunctionExpressionNode;
import com.ghunteranderson.tsugi.syntax.FunctionStatementNode;
import com.ghunteranderson.tsugi.syntax.LiteralNumberNode;
import com.ghunteranderson.tsugi.syntax.LiteralStringNode;
import com.ghunteranderson.tsugi.syntax.ModuleNode;
import com.ghunteranderson.tsugi.syntax.OperationExpressionNode;
import com.ghunteranderson.tsugi.syntax.VariableAssignmentNode;
import com.ghunteranderson.tsugi.syntax.VariableDeclarationNode;
import com.ghunteranderson.tsugi.syntax.VariableExpressionNode;
import com.ghunteranderson.tsugi.utils.LocalsTable;

import lombok.RequiredArgsConstructor;

public class AstCodeSymbolAnalyzer {

  private static LocalVarMetadata getLocal(LocalsTable<LocalVarMetadata> locals, String name, SourceLocation loc){
    for(var local  : locals){
      if(local.name.equals(name))
        return local;
    }
    throw new TsugiCompilerException(ErrorCode.INVALID_SEMANTICS, loc, "Undefined variable: " + name);
  }
  
  public void analyzeCodeSymbols(SymbolTable symbols, List<ModuleNode> modules){
    
  }

  private static class LocalVarMetadata {
    private String name;
    private TypeSymbol type;
  }

  @RequiredArgsConstructor
  private static class AstCodeSymbolVisitor implements AstVisitor {
    private final SymbolTable symbols;
    private LocalsTable<LocalVarMetadata> locals;

    @Override
    public void visit(ModuleNode moduleNode) {
    }

    @Override
    public void visit(FunctionDeclarationNode func) {
      locals = new LocalsTable<>();
      // TODO: Add parameters to locals table 
    }

    @Override
    public void visit(VariableDeclarationNode stat) {
      var localName = stat.getName();
      // TODO: As is, the declaration must use a fully qualified type
      var typeName = TypeUtils.qualifiedRefToString(stat.getType());
      var type = symbols
        .getType(typeName)
        .orElseThrow(() -> new TsugiCompilerException(ErrorCode.INVALID_SEMANTICS, stat.getLocation(), "Undeclared type: " + typeName));
      
      var local = new LocalVarMetadata();
      local.name = localName;
      local.type = type;
      locals.add(local);
    }

    @Override
    public void visit(VariableAssignmentNode stat) {
    }

    @Override
    public void visit(FunctionStatementNode node) {
      var name = node.getRef().getIdentifiers().get(0);
      var qualifiedName = node.getParent().getSymbol().qualifiedName + "." + name;
      symbols.getFunction(null, null)
    }

    @Override
    public void visit(VariableExpressionNode node) {
      var varName = TypeUtils.qualifiedRefToString(node.name);
      var local = getLocal(locals, varName, node.getLocation());
      node.setTypeSymbol(local.type);
    }

    @Override
    public void visit(FunctionExpressionNode node) {
      // TODO: Check the parameter types
      var argTypes = node.getArgs().stream().map(arg -> arg.getTypeSymbol()).collect(Collectors.toList());
      var funcName = TypeUtils.qualifiedRefToString(node.getRef());
      var funcSymbol = symbols.getFunction(funcName, argTypes);
      if(funcSymbol.isEmpty()){
        throw new TsugiCompilerException(
            ErrorCode.INVALID_SEMANTICS,
            node.getLocation(),
            "Function not define for argument types.");
      }
      node.setTypeSymbol(funcSymbol.get().returnType);
      node.setFunctionSymbol(funcSymbol.get());
    }

    @Override
    public void visit(LiteralNumberNode literalNumberNode) {
      var stringValue = literalNumberNode.getValue();
      TypeSymbol typeSymbol;
      if(stringValue.indexOf('.') >= 0){
        typeSymbol = symbols.getType("double").get();
      }
      else
        typeSymbol = symbols.getType("int").get();
      literalNumberNode.setTypeSymbol(typeSymbol);
    }

    @Override
    public void visit(LiteralStringNode literalStringNode) {
      var type = symbols.getType("string").get();
      literalStringNode.setTypeSymbol(type);
    }

    @Override
    public void visit(OperationExpressionNode operationExpressionNode) {
      var leftType = operationExpressionNode.getLeft().getTypeSymbol();
      var rightType = operationExpressionNode.getRight().getTypeSymbol();

      TypeSymbol targetType = null;
      var typePriority = new String[]{"string", "double", "float", "long", "int"};
      for(var i = 0; i<typePriority.length; i++){
        targetType = getType(typePriority[i], leftType, rightType);
        if(targetType != null)
          break;
      }
      if(targetType == null)
        throw new TsugiCompilerException(
            ErrorCode.INVALID_SEMANTICS,
            operationExpressionNode.getLocation(),
            "Operation is not valid for operands.");
      else
        operationExpressionNode.setTypeSymbol(targetType);
    }

    private TypeSymbol getType(String target, TypeSymbol... types){
      for(int i=0; i<types.length; i++){
        var type = types[i];
        if(types[i].qualifiedName.equals(target))
          return type;
      }
      return null;
    }
  }
}
