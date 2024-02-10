package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ghunteranderson.tsugi.sandbox.utils.BinaryWriter;

import static com.ghunteranderson.tsugi.sandbox.backend.java.bytecode.JAccessFlags.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ByteCodeAssemblerTest {

  @Test
  public void test() {
    var classFile = new JClassFile();
    classFile.accessFlags = List.of(ACC_PUBLIC, ACC_SUPER);
    classFile.thisClass = new JConst_ClassInfo("HelloBytecode");
    classFile.superClass = new JConst_ClassInfo("java/lang/Object");
    classFile.interfaces = List.of();
    classFile.fields = List.of();


    var main = new JMethodInfo();
    classFile.methods.add(main);
    main.accessFlags = List.of(ACC_PUBLIC, ACC_STATIC);
    main.name = new JConst_Utf8Info("main");
    main.descriptor = new JConst_Utf8Info("([Ljava/lang/String;)V");

    var code = new JAttr_Code();
    main.attributes.add(code);
    code.maxLocals = 1;
    code.maxStack = 2;
    code.code = List.of(
      JInst._getstatic("java/lang/System", "out", "Ljava/io/PrintStream;"),
      JInst._ldc_w("Hello Bytecode"),
      JInst._invokevirtual("java/io/PrintStream", "println", "(Ljava/lang/String;)V"),
      JInst._return()
    );


    var assembler = new ByteCodeAssembler();
    var binary = assembler.assemble(classFile);
    
    writeToFile("HelloBytecode.class", binary);
  }

  private void writeToFile(String path, BinaryWriter writer){
    try {
      writer.toOutputStream(new FileOutputStream(path));
    } catch(IOException ex){
      fail("Could not write to file:" + path, ex);
    }
  }

}
