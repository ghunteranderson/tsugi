package com.ghunteranderson.tsugi.backend.jvm;

import com.ghunteranderson.java.bytecode.JConst_NameAndTypeInfo;
import com.ghunteranderson.java.bytecode.JConst_Utf8Info;
import com.ghunteranderson.tsugi.syntax.QualifiedRefNode;

public class TypeUtils {
  
  public static JConst_NameAndTypeInfo typeForVar(String name, QualifiedRefNode type){
    return new JConst_NameAndTypeInfo(name, typeToDescriptor(type));

  }

  public static JConst_Utf8Info typeForMethod(QualifiedRefNode returnType){
    return new JConst_Utf8Info("()" + typeToDescriptor(returnType));
  }

  public static JConst_Utf8Info typeFor(QualifiedRefNode type){
    return new JConst_Utf8Info(typeToDescriptor(type));
  }

  private static String typeToDescriptor(QualifiedRefNode returnType){
    var idents = returnType.getIdentifiers();

    // Check if type is a built-in (e.g. primitive)
    if(idents.size() == 1){
      switch(idents.get(0)){
        case "string":
          return "Ljava/lang/String;";
        case "int":
          return "I";
        case "long":
          return "J";
        case "float":
          return "F";
        case "double":
          return "D";
        case "boolean":
          return "Z";
        case "void":
          return "V";
      }
    }
    
    var descriptor = new StringBuilder()
      .append("L")
      .append(idents.get(0));

    for(int i=1; i<idents.size(); i++){
      descriptor.append("/").append(idents.get(i));
    }
    descriptor.append(";");
    return descriptor.toString();
   
  }
}
