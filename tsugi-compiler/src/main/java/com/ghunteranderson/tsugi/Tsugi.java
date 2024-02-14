package com.ghunteranderson.tsugi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.ghunteranderson.java.bytecode.ByteCodeAssembler;
import com.ghunteranderson.tsugi.backend.jvm.JvmClassCompiler;
import com.ghunteranderson.tsugi.syntax.SyntaxParser;

public class Tsugi {

  public static final void compile(InputStream source, OutputStream target) throws IOException {
    var ast = new SyntaxParser(source).parseModule();
    var classFile = new JvmClassCompiler().compile(ast);
    var binary = new ByteCodeAssembler().assemble(classFile);
    binary.toOutputStream(target);
  }
  
}
