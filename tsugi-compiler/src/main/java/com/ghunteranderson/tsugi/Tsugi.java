package com.ghunteranderson.tsugi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.ghunteranderson.java.bytecode.ByteCodeAssembler;
import com.ghunteranderson.tsugi.backend.jvm.JvmCompiler;
import com.ghunteranderson.tsugi.semantics.AstCodeSymbolAnalyzer;
import com.ghunteranderson.tsugi.semantics.AstGlobalSymbolAnalyzer;
import com.ghunteranderson.tsugi.syntax.SyntaxParser;

public class Tsugi {

  public static final void compile(InputStream source, OutputStream target) throws IOException {
    var ast = List.of(new SyntaxParser(source).parseModule());
    var symbolTable = new AstGlobalSymbolAnalyzer().buildSymbolTable(ast);
    new AstCodeSymbolAnalyzer().analyzeCodeSymbols(symbolTable, ast);

    var classFile = new JvmCompiler().compile(ast.get(0), symbolTable);
    var binary = new ByteCodeAssembler().assemble(classFile);
    binary.toOutputStream(target);
  }
  
}
