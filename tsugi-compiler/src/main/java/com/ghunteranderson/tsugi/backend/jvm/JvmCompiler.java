package com.ghunteranderson.tsugi.backend.jvm;

import java.util.List;

import com.ghunteranderson.java.bytecode.JAccessFlags;
import com.ghunteranderson.java.bytecode.JAttr;
import com.ghunteranderson.java.bytecode.JAttr_Code;
import com.ghunteranderson.java.bytecode.JClassFile;
import com.ghunteranderson.java.bytecode.JConst_ClassInfo;
import com.ghunteranderson.java.bytecode.JConst_StringInfo;
import com.ghunteranderson.java.bytecode.JConst_Utf8Info;
import com.ghunteranderson.java.bytecode.JInst;
import com.ghunteranderson.java.bytecode.JMethodInfo;
import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TsugiCompilerException;
import com.ghunteranderson.tsugi.semantics.SymbolTable;
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

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

public class JvmCompiler {

  public JClassFile compile(ModuleNode module, SymbolTable symbolTable) {
    var classFile = buildEmptyClassFile(module, symbolTable);
    var compiler = new JvmFunctionCompilerVisitor(symbolTable, classFile);


    module.acceptVisitor(compiler);
    return classFile;
  }

  public JClassFile buildEmptyClassFile(ModuleNode moduleNode, SymbolTable symbolTable){
    var classFile = new JClassFile();
    classFile.accessFlags = List.of(JAccessFlags.ACC_PUBLIC, JAccessFlags.ACC_SUPER, JAccessFlags.ACC_FINAL);
    classFile.superClass = new JConst_ClassInfo("java/lang/Object");
    var moduleSymbol = moduleNode.getSymbol();
    classFile.thisClass = new JConst_ClassInfo(moduleSymbol.qualifiedName + "/Module");
    return classFile;
  }

  @RequiredArgsConstructor
  private static class JvmFunctionCompilerVisitor implements AstVisitor {

    private final SymbolTable symbols;
    private final JClassFile classFile;
    
    private LocalsTable<VariableDeclarationNode> locals;
    private JMethodInfo method;
    private JAttr_Code code;

    @Override
    public void visit(FunctionDeclarationNode func) {
      method = new JMethodInfo();
      classFile.methods.add(method);
      method.accessFlags = List.of(JAccessFlags.ACC_PUBLIC, JAccessFlags.ACC_STATIC);
      method.name = new JConst_Utf8Info(func.getName());
      method.descriptor = TypeUtils.typeForMethod(func.getReturnType());

      code = new JAttr_Code();
      method.attributes.add(code);

      locals = new LocalsTable<>();
      locals.pushScope();
    }

    @Override
    public void visit(VariableDeclarationNode node) {
      locals.add(node);
      code.maxLocals = Math.max(locals.size(), code.maxLocals);
    }

    @Override
    public void visit(VariableAssignmentNode node) {
      int index = findVariableIndex(node.getName());
      if (index < 0)
        throw new TsugiCompilerException(ErrorCode.INVALID_SEMANTICS, node.getLocation(),
            "Variable not declared: " + node.getName());

      var type = node.getValue().getTypeSymbol();
      JInst storeInst = switch (type.qualifiedName) {
        case "double" -> JInst._dstore(index);
        case "float" -> JInst._fstore(index);
        case "long" -> JInst._lstore(index);
        case "int" -> JInst._istore(index);
        default -> JInst._astore(index);
      };
      code.code.add(storeInst);
    }

    @Override
    public void visit(FunctionStatementNode node) {
      // TODO Auto-generated method stub
      var methodRef = TypeUtils.typeForMethod(node.)
    }

    @Override
    public void visit(VariableExpressionNode node) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(FunctionExpressionNode node) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(LiteralNumberNode literalNumberNode) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(LiteralStringNode literalStringNode) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    @Override
    public void visit(OperationExpressionNode operationExpressionNode) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

    private int findVariableIndex(String name) {
      int i = 0;
      for (var local : locals) {
        if (local.getName().equals(name))
          return i;
        i = i + 1;
      }
      return -1;
    }

    @Override
    public void visit(ModuleNode moduleNode) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }

  }

}
