package com.ghunteranderson.tsugi;

import java.io.FileOutputStream;

import org.junit.jupiter.api.Test;

public class TsugiTest {

  @Test
  public void test() throws Exception {
    var source = TestUtils.streamString("""
      module greeting;

      function void greet(){
        println("Hello world");
      }
    """);

    var out = new FileOutputStream("Module.class");
    
    Tsugi.compile(source, out);
  }
  
}
