package com.ghunteranderson.tsugi.semantics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TsugiCompilerException;

import lombok.Builder;
import lombok.EqualsAndHashCode;

public class SymbolTable {

  private static final String[] BUILT_IN_TYPES = new String[] {
      "string", "int", "long", "float", "double", "boolean"
  };

  private Map<String, ModuleSymbol> modules = new HashMap<>();
  private Set<FunctionSymbol> functions = new HashSet<>();
  private Set<TypeSymbol> types = new HashSet<>();

  public SymbolTable() {
    addBuiltInSymbols();
  }

  private void addBuiltInSymbols(){
    var builtInModule = new ModuleSymbol("<built-in>"); // A fake module to hold built in 
    modules.put(builtInModule.qualifiedName, builtInModule);
    Stream.of(BUILT_IN_TYPES).forEach(type -> types.add(new TypeSymbol(builtInModule, type, true)));
  }

  public Optional<ModuleSymbol> getModule(String qualifiedName) {
    return Optional.ofNullable(modules.get(qualifiedName));
  }

  public ModuleSymbol registerModule(ModuleSymbol module) {
    var name = module.qualifiedName;
    if (modules.containsKey(name))
      throw new TsugiCompilerException(ErrorCode.UNKNOWN_ERROR, null, "Module declared twice: " + name);
    modules.put(name, module);
    return module;
  }

  public Optional<FunctionSymbol> getFunction(String qualifiedName, List<TypeSymbol> parameterTypes){
    for(var func : functions){
      if(func.qualifiedName.equals(qualifiedName) && func.parameterTypes.equals(parameterTypes))
        return Optional.of(func);
    }
    return Optional.empty();
  }

  public FunctionSymbol registerFunction(FunctionSymbol function) {
    var alreadyExists = getFunction(function.qualifiedName, function.parameterTypes).isPresent();
    if (alreadyExists){
      var error = "Function " + function.qualifiedName + " in module " + function.parent.qualifiedName + " is declared twice.";
      throw new TsugiCompilerException(ErrorCode.UNKNOWN_ERROR, null, error);
    }
    functions.add(function);
    return function;
  }

  public Optional<TypeSymbol> getType(String qualifiedName) {
    for(var type : types){
      if(type.qualifiedName.equals(qualifiedName))
        return Optional.of(type);
    }
    return Optional.empty();
  }

  public TypeSymbol createType(ModuleSymbol parent, String qualifiedName) {
    var type = new TypeSymbol(null, qualifiedName, false);
    if (types.contains(type)) {
      throw new TsugiCompilerException(ErrorCode.UNKNOWN_ERROR, null,
          "Type declared twice: " + parent.qualifiedName + "." + qualifiedName);
    }
    return type;
  }

  @EqualsAndHashCode
  @Builder
  public static class ModuleSymbol {
    public final String qualifiedName;
  }

  @EqualsAndHashCode
  @Builder
  public static class FunctionSymbol {
    public final ModuleSymbol parent;
    public final String qualifiedName;
    public final TypeSymbol returnType;
    public final List<TypeSymbol> parameterTypes;

  }

  @EqualsAndHashCode
  @Builder
  public static class TypeSymbol {
    public final ModuleSymbol parent;
    public final String qualifiedName;
    public final boolean builtin;
  }

}
