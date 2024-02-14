package com.ghunteranderson.tsugi.backend.jvm;

import java.util.List;

import com.ghunteranderson.java.bytecode.JAccessFlags;
import com.ghunteranderson.java.bytecode.JClassFile;
import com.ghunteranderson.java.bytecode.JConst_ClassInfo;
import com.ghunteranderson.java.bytecode.JConst_Utf8Info;
import com.ghunteranderson.java.bytecode.JMethodInfo;
import com.ghunteranderson.tsugi.syntax.FunctionDeclarationNode;
import com.ghunteranderson.tsugi.syntax.ModuleNode;

public class JvmClassCompiler {

  public JClassFile compile(ModuleNode srcModule){
    var classFile = buildEmptyClassFile(srcModule.getName());

    compileFunctions(srcModule, classFile);

    return classFile;
  }

  private void compileFunctions(ModuleNode srcModule, JClassFile classFile) {
    for(var srcFunction : srcModule.getFunctions()){
      compileFunction(srcModule, srcFunction, classFile);
    }
  }

  private void compileFunction(ModuleNode srcModule, FunctionDeclarationNode srcFunction, JClassFile classFile) {
    var method = new JMethodInfo();
    classFile.methods.add(method);
    method.accessFlags = List.of(JAccessFlags.ACC_PUBLIC, JAccessFlags.ACC_STATIC);
    method.name = new JConst_Utf8Info(srcFunction.getName());
    method.descriptor = TypeUtils.typeForMethod(srcFunction.getReturnType());
    method.attributes.add(JvmCodeCompiler.compileFunction(srcModule, srcFunction, classFile));
  }


  private JClassFile buildEmptyClassFile(String moduleName){
    var classFile = new JClassFile();
    classFile.accessFlags = List.of(JAccessFlags.ACC_PUBLIC, JAccessFlags.ACC_SUPER, JAccessFlags.ACC_FINAL);
    classFile.superClass = new JConst_ClassInfo("java/lang/Object");
    classFile.thisClass = new JConst_ClassInfo(moduleName + "/Module");
    return classFile;
  }
  
}
