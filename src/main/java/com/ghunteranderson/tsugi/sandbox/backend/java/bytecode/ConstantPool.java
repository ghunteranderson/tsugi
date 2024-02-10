package com.ghunteranderson.tsugi.sandbox.backend.java.bytecode;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ConstantPool {

  private final List<JConst_Info> pool;

  public ConstantPool(){
    this(new LinkedList<>());
  }

  public ConstantPool(List<JConst_Info> pool){
    this.pool = pool;
    if(pool.isEmpty() || pool.get(0) != null)
      pool.add(0, null);
  }

  public List<JConst_Info> values(){
    return pool;
  }

  public int findConstant(JConst_Info constInfo){
    var size = pool.size();
    int i;
    for(i=0; i<size; i++){
      if(Objects.equals(constInfo, pool.get(i)))
        return i;
    }
    
    return addConstants(constInfo);
  }

  private int addConstants(JConst_Info cnst) {
    var index = pool.size();
    pool.add(cnst);

    if(cnst instanceof JConst_ClassInfo info){
      addConstants(info.name);
    }
    else if (cnst instanceof JConst_FieldRefInfo info){
      addConstants(info.clazz);
      addConstants(info.nameAndType);
    }
    else if(cnst instanceof JConst_InterfaceMethodRefInfo info){
      addConstants(info.clazz);
      addConstants(info.nameAndType);
    }
    else if(cnst instanceof JConst_MethodRefInfo info){
      addConstants(info.clazz);
      addConstants(info.nameAndType);
    }
    else if(cnst instanceof JConst_InvokeDynamicInfo info){
      addConstants(info.nameAndType);
    }
    else if(cnst instanceof JConst_MethodHandleInfo info){
      addConstants(info.reference);
    }
    else if(cnst instanceof JConst_MethodTypeInfo info){
      addConstants(info.descriptor);
    }
    else if(cnst instanceof JConst_NameAndTypeInfo info){
      addConstants(info.name);
      addConstants(info.descriptor);
    }
    else if(cnst instanceof JConst_StringInfo info){
      addConstants(info.string);
    }

    return index;
  }

  
}
