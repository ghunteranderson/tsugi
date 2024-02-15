package com.ghunteranderson.tsugi.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LocalsTable<V> implements Iterable<V> {

  private List<List<V>> scopes;
  private List<V> currentScope;
  private int maxSize;
  private int currentSize;

  public LocalsTable() {
    scopes = new LinkedList<>();
    pushScope();
  }

  public void add(V local) {
    currentScope.add(local);
    currentSize++;
    if (currentSize > maxSize)
      maxSize = currentSize;
  }

  public int size() {
    return currentSize;
  }

  public boolean contains(Predicate<V> condition){
    for(var local : this){
      if(condition.test(local))
        return true;
    }
    return false;
  }

  public int largestSizeObserved() {
    return maxSize;
  }

  public void pushScope() {
    var newScope = new LinkedList<V>();
    scopes.add(newScope);
    currentScope = newScope;
  }

  public void popScope() {
    var removedScope = scopes.remove(scopes.size() - 1);
    if (scopes.isEmpty())
      currentScope = null;
    else
      currentScope = scopes.get(scopes.size() - 1);

    currentSize -= removedScope.size();
  }

  public Stream<V> stream(){
    return scopes.stream().flatMap(List::stream);
  }

  @Override
  public Iterator<V> iterator() {
    return stream().iterator();
  }
}
