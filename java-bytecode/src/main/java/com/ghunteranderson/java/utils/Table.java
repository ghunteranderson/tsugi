package com.ghunteranderson.java.utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Table<K, V> {

  private List<K> keys;
  private List<V> values;
  
  public Table(List<K> keys, Function<K, V> valueSupplier){
    keys = new ArrayList<>(keys);
    values = new ArrayList<>(keys.size());
    keys.forEach(k -> values.add(valueSupplier.apply(k)));
  }

  public int put(K key, V value){
    var index = keys.size();
    keys.add(key);
    values.add(value);
    return index;
  }

  public K getKey(int i){
    return keys.get(i);
  }

  public V getValue(int i){
    return values.get(i);
  }

  public Map.Entry<K, V> get(int i){
    return new AbstractMap.SimpleImmutableEntry<>(
      keys.get(i),
      values.get(i)
    );
  }

  public V get(K key){
    return values.get(keys.indexOf(key));
  }

  public int indexOf(K key){
    return keys.indexOf(key);
  }

  public List<K> keys(){
    return new ArrayList<>(keys);
  }

  public List<V> values(){
    return new ArrayList<>(values);
  }

  public List<Map.Entry<K, V>> entries(){
    var size = keys.size();
    var out = new ArrayList<Map.Entry<K, V>>(size);

    for(int i = 0; i<size; i++){
      out.add(new AbstractMap.SimpleImmutableEntry<K,V>(keys.get(i), values.get(i)));
    }
    return out;
  }
}
