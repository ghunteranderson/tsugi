package com.ghunteranderson.tsugi.backend.jvm;

import java.util.List;

import com.ghunteranderson.java.bytecode.JAttr_Code;
import com.ghunteranderson.java.bytecode.JClassFile;
import com.ghunteranderson.java.bytecode.JConst_NameAndTypeInfo;
import com.ghunteranderson.java.bytecode.JInst;
import com.ghunteranderson.tsugi.syntax.ExpressionNode;
import com.ghunteranderson.tsugi.syntax.FunctionDeclarationNode;
import com.ghunteranderson.tsugi.syntax.LiteralNumberNode;
import com.ghunteranderson.tsugi.syntax.ModuleNode;
import com.ghunteranderson.tsugi.syntax.StatementNode;
import com.ghunteranderson.tsugi.syntax.VariableAssignmentNode;
import com.ghunteranderson.tsugi.syntax.VariableDeclarationNode;
import com.ghunteranderson.tsugi.utils.LocalsTable;
import com.ghunteranderson.tsugi.utils.StackSizeCounter;

public class JvmCodeCompiler {

  public static JAttr_Code compileFunction(ModuleNode srcModule, FunctionDeclarationNode srcFunc, JClassFile targetClass){
    var ctx = new CompilerContext();
    ctx.srcModule = srcModule;
    ctx.srcFunction = srcFunc;
    ctx.locals = new LocalsTable<>();
    ctx.stack = new StackSizeCounter();
    

    var attr = new JAttr_Code();
    srcFunc.getStatements().forEach(s -> compileStatement(ctx, attr.code, s));
    attr.maxLocals = ctx.locals.size();
    attr.maxStack = 0;

    return attr;
  }



  private static void compileStatement(CompilerContext ctx, List<JInst> instructions, StatementNode statement) {
    if(statement instanceof VariableDeclarationNode stat){
      ctx.locals.addLocal(TypeUtils.typeForVar(stat.getName(), stat.getType()));
    }
    else if(statement instanceof VariableAssignmentNode stat){
      
    }
    
  }

  private static void compileExpression(CompilerContext ctx, List<JInst> instructions, ExpressionNode expression){
    if(expression instanceof LiteralNumberNode exp){
      
        
    }
  }



  private static class CompilerContext {
    public ModuleNode srcModule;
    public FunctionDeclarationNode srcFunction;
    public LocalsTable<JConst_NameAndTypeInfo> locals;
    public StackSizeCounter stack;
  }
}
