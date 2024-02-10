package com.ghunteranderson.tsugi.sandbox.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

public class BinaryWriter {

  // This number is kind of arbitrary
  // Need to stay away from Integer.MAX_VALUE;
  private static final int ARRAY_MAX_SIZE = Short.MAX_VALUE;

  // Coefficient for growth when fragment is filled
  private static final float ARRAY_GROWTH_COEFFICIENT = 1.5f;


  private List<ArrayFragment> fragments = new LinkedList<>();
  private byte[] buffer = new byte[32];
  private int next = 0;

  public void writeU1(byte b){
    if(next == buffer.length)
      pushFragment();
    buffer[next] = b;
    next++;
  }

  public void writeAll(byte[] arg){
    var argNext = 0;
    while(argNext < arg.length){
      while(argNext < arg.length && next < buffer.length){
        buffer[next] = arg[argNext];
        next++;
        argNext++;
      }
      if(next == buffer.length)
        pushFragment();
    }
  }

  public void writeU1(int i){
    writeU1((byte)i);
  }

  public void writeU2(int i){
    writeU1((byte)(i>>8));
    writeU1((byte)i);
  }

  public void writeU4(int i){
    writeU1((byte)(i >> 24));
    writeU1((byte)(i >> 16));
    writeU1((byte)(i >> 8));
    writeU1((byte)(i >> 0));
  }

  
  /**
   * Inserts a GraphedByteOutputStream into the data.
   * Writing additional data to the inserted output stream
   * will inject that data into containing output stream 
   * @param value
   */
  public BinaryWriter insertLiveWriter(){
    if(next > 0)
      pushFragment();

    var writer = new BinaryWriter();
    fragments.add(new MultipleArrayFragments(writer));
    return writer;
  }

  public byte[] toBytes(){
    var fragments = new MultipleArrayFragments(this).getFragments().collect(Collectors.toList());
    var totalSize = fragments.stream().map(f -> f.length).reduce(0, Integer::sum);

    // Copy data into new array
    var output = new byte[totalSize]; 
    var outputNext = 0;
    for(var fragment : fragments){
      for(int fragmentNext = 0; fragmentNext < fragment.length;)
        output[outputNext++] = fragment[fragmentNext++];
    }

    return output;
  }

  public void toOutputStream(OutputStream os) throws IOException {
    var fragments = new MultipleArrayFragments(this).getFragments().collect(Collectors.toList());
    for(var fragment : fragments)
      os.write(fragment);
  }

  public int size(){
    return new MultipleArrayFragments(this).size();
  }

  private void pushFragment(){
    if(next == buffer.length){
      int newSize = Math.min((int)(buffer.length * ARRAY_GROWTH_COEFFICIENT), ARRAY_MAX_SIZE);
      fragments.add(new SingleArrayFragment(buffer));
      buffer = new byte[newSize];
      next = 0;
    }
    else {
      fragments.add(new SingleArrayFragment(Arrays.copyOf(buffer, next)));
      // The buffer can be reused since it was copied into the fragment
      // The buffer wasn't full so no need to increase its size yet
      next = 0;
    }
  }


  private interface ArrayFragment {
    /**
     * @return a Stream of array fragments in this node
     */
    public Stream<byte[]> getFragments();

    public int size();
  }

  @RequiredArgsConstructor
  private static class SingleArrayFragment implements ArrayFragment {

    private final byte[] value;

    @Override
    public Stream<byte[]> getFragments() {
      return Stream.of(value);
    }

    @Override
    public int size(){
      return value.length;
    }

  }

  @RequiredArgsConstructor
  private static class MultipleArrayFragments implements ArrayFragment {
    private final BinaryWriter ref;

    public Stream<byte[]> getFragments(){
      if(ref.next > 0)
        ref.pushFragment();
      return ref.fragments.stream().flatMap(ArrayFragment::getFragments);
    }

    public int size(){
      return ref.fragments.stream().map(ArrayFragment::size).reduce(0, Integer::sum) + ref.next;
    }

  }
  
}
