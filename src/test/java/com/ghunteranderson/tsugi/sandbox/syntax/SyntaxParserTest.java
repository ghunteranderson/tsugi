package com.ghunteranderson.tsugi.sandbox.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import com.ghunteranderson.tsugi.sandbox.TestUtils;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import lombok.Data;

public class SyntaxParserTest {

  private static final Jsonb json = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

  @Test
  void manualTest(){
    var sourceFile = "src/test/resources/syntax/samples/helloworld.tsugi";
    var checkFile = "src/test/resources/syntax/samples/helloworld.json";
    genericSyntaxTest(new File(sourceFile), new File(checkFile));
  }

  @TestFactory
  public Stream<DynamicTest> test() {
    return listFileRecursive(new File("src/test/resources/syntax"))
        .filter(file -> file.getName().endsWith(".tsugi"))
        .sorted()
        .map(sourceCode -> {
          var baseName = sourceCode.getName();
          baseName = baseName.substring(0, baseName.length() - ".tsugi".length());
          var checkFile = new File(sourceCode.getParent(), baseName + ".json");
          return new TestDescription(baseName, sourceCode, checkFile);
        })
        .map(test -> DynamicTest.dynamicTest(test.name(), () -> genericSyntaxTest(test.sourceFile(), test.checkFile())));
  }

  private void genericSyntaxTest(File source, File checkFile) {
    var parser = new SyntaxParser(TestUtils.streamFile(source));
    var module = parser.parseModule();
    var result = ParseResult.success(module);

    var actual = formatJson(json.toJson(result));
    var expected = formatJson(TestUtils.readFile(checkFile));
    assertEquals(expected, actual);
  }

  private String formatJson(String jsonString){
    return json.toJson(json.fromJson(jsonString, Map.class));
  }

  private static Stream<File> listFileRecursive(File root) {
    return Stream.of(root.listFiles())
        .flatMap(f -> {
          if (f.isFile())
            return Stream.of(f);
          else
            return listFileRecursive(f);
        });
  }

  @Data
  public static class ParseResult {
    public static ParseResult success(ModuleNode module) {
      var result = new ParseResult();
      result.module = module;
      return result;
    }

    private ModuleNode module;
  }

  private static record TestDescription(
      String name,
      File sourceFile,
      File checkFile) {
  }

}
