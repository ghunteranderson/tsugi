package com.ghunteranderson.tsugi.sandbox;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtils {

  public static InputStream streamString(String s){
    return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
  }

  public static InputStream streamFile(String path){
    var is = TestUtils.class.getClassLoader().getResourceAsStream(path);
    if(is == null)
      fail("Could open input stream for: " + path);
    return is;
  }

  public static InputStream streamFile(File file){
    try {
      return new FileInputStream(file);
    } catch (FileNotFoundException e) {
      fail("Could not create stream for file: " + file);
      return null;
    }
  }

  public static String readFile(String path){
    var fullPath = Path.of(TestUtils.class.getClassLoader().getResource(path).getPath());
    try {
      return Files.readString(fullPath);
    } catch(IOException ex){
      fail("Failed to read path:" + path, ex);
      return null;
    }
  }

  public static String readFile(File file){
    try {
      return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    } catch(IOException ex){
      fail("Failed to read path:" + file, ex);
      return null;
    }
  }
  
}
