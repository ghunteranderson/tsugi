package com.ghunteranderson.tsugi.utils;

import java.util.LinkedList;
import java.util.List;

public class LocalsTable<V> {

  private List<V> locals = new LinkedList<>();

  public void addLocal(V local){
    locals.add(local);
  }

  public int indexOf(V local){
    return locals.indexOf(local);
  }

  public int size(){
    return locals.size(); // This should be the max size but since there's not way to remove locals for now, we'll ignore that for now
  }
  
}
