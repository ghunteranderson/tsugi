package com.ghunteranderson.tsugi.utils;

public class StackSizeCounter {

  private int maxSize;
  private int currentSize;

  public void push(){
    currentSize++;
    checkMax();
  }

  public void push(int netDelta){
    currentSize += netDelta;
    checkMax();
  }

  public void pop(){
    currentSize--;
  }

  private void checkMax(){
    if(currentSize > maxSize)
      maxSize = currentSize;
  }

  public int getMaxSize(){
    return maxSize;
  }
  
  
}
