package com.ghunteranderson.tsugi.sandbox.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.yaml.snakeyaml.Yaml;

import com.ghunteranderson.tsugi.sandbox.TestUtils;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.Data;

@SuppressWarnings("unchecked")
public class SyntaxParserTest {

  private static final Jsonb json = JsonbBuilder.create();
  private static final Yaml yaml = new Yaml();
  private static final Set<String> IGNORED_FIELDS = Set.of("location");

  @Test
  void manualTest(){
    var sample = "import/import_empty";
    var sourceFile = "src/test/resources/syntax/" + sample + ".tsugi";
    var checkFile = "src/test/resources/syntax/" + sample + ".yaml";
    genericSyntaxTest(new File(sourceFile), new File(checkFile));
  }

  @TestFactory
  public Stream<DynamicTest> test() {
    return listFileRecursive(new File("src/test/resources/syntax/import"))
        .filter(file -> file.getName().endsWith(".tsugi"))
        .sorted()
        .map(sourceCode -> {
          var baseName = sourceCode.getName();
          baseName = baseName.substring(0, baseName.length() - ".tsugi".length());
          var checkFile = new File(sourceCode.getParent(), baseName + ".yaml");
          return new TestDescription(baseName, sourceCode, checkFile);
        })
        .map(test -> DynamicTest.dynamicTest(test.name(), () -> genericSyntaxTest(test.sourceFile(), test.checkFile())));
  }

  private void genericSyntaxTest(File source, File checkFile) {
    var parser = new SyntaxParser(TestUtils.streamFile(source));
    var module = parser.parseModule();
    var result = ParseResult.success(module);

    var actual = toYaml(result);
    var expected = toYaml(checkFile);
    assertEquals(expected, actual);
  }

  private String toYaml(ParseResult r){
    // SnakeYaml is having problems serializing Java records so we're sticking to Yasson for this step
    var map = (Map<String, Object>)json.fromJson(json.toJson(r), Map.class);
    map = normalizeMap(map);
    return yaml.dump(map); 
  }

  private String toYaml(File file){
    var string = TestUtils.readFile(file);
    var map = (Map<String, Object>) yaml.loadAs(string, Map.class);
    map = normalizeMap(map);
    return yaml.dump(map);
  }

  // This method removes
  private Map<String, Object> normalizeMap(Map<String, Object> map){
    var out = new LinkedHashMap<String, Object>(map.size());

    map.entrySet().stream()
      .filter(e -> !IGNORED_FIELDS.contains(e.getKey()))
      .sorted(Map.Entry.comparingByKey())
      .forEach(e -> {
        var key = e.getKey();
        var value = e.getValue();

        if(value instanceof Map m){
          value = normalizeMap(m);
        }
        if(value instanceof Collection collection){
          var newCollection = new ArrayList<>(collection.size());
          value = newCollection;
          for(var element : collection){
            if(element instanceof Map mapElement){
              newCollection.add(normalizeMap(mapElement));
            }
            else
              newCollection.add(element);
          }
        }
        out.put(key, value);
      });
    return out;
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
