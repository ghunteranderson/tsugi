package com.ghunteranderson.tsugi.lexicon;

import java.io.IOException;
import java.io.InputStream;

public class Utf8CharacterInputStream implements CharacterInputStream {

  private static final int BUFFER_SIZE = 64;

  private final InputStream is;
  private boolean endReached;

  private byte[] byteBuffer;
  private int bbh; // byte buffer head (location of next byte)
  private int bbt; // byte buffer tail

  public Utf8CharacterInputStream(InputStream is){
    this.is = is;
    this.byteBuffer = new byte[BUFFER_SIZE];
    this.bbh = 0;
    this.bbt = 0;
  }

  @Override
  public char next() throws IOException {
    if((bbh - bbt + BUFFER_SIZE) % BUFFER_SIZE < 4 && !endReached)
      fillBuffer();
    
    int codePoint = ((int)byteBuffer[bbt]) & 0xff;
    bbt  = (bbt+1) % BUFFER_SIZE;
    
    if(codePoint > 127){
      if ((codePoint & 0b11110000) == 0b11110000){
        // A Java char is 16bits allowing it to store code points up to 3 UTF-8 bytes.
        // Supporting 4 UTF-8 bytes requires a surrogate pair of chars.
        // For now, this level of UTF-8 support is out of scope.
        throw new IOException("Utf8CharacterInputStream doesn't support 4 byte characters");
      }
      else if ((codePoint & 0b11100000) == 0b11100000){
        codePoint = codePoint & 0b00001111; // Chomp off the upper 4 bytes
        codePoint = (codePoint << 6) | (((int)byteBuffer[bbt]) & 0b00111111);
        bbt  = (bbt+1) % BUFFER_SIZE;
        codePoint = (codePoint << 6) | (((int)byteBuffer[bbt]) & 0b00111111);
        bbt  = (bbt+1) % BUFFER_SIZE;
      }
      else if ((codePoint & 0b11000000) == 0b11000000){
        codePoint = codePoint & 0b00011111; // Chomp off the upper 3 bytes
        codePoint = (codePoint << 6) | (((int)byteBuffer[bbt]) & 0b00111111);
        bbt  = (bbt+1) % BUFFER_SIZE;
      }
      
    }
    return (char)codePoint;
  }

  @Override
  public boolean hasNext(){
    return (bbh - bbt + BUFFER_SIZE) % BUFFER_SIZE > 0 || !endReached; 
  }

  private void fillBuffer() throws IOException {
    // Check buffer capacity
    int capacity = (bbt - bbh - 1 + BUFFER_SIZE) % BUFFER_SIZE;
    if(capacity == 0)
      return;

    // Read bytes from input stream
    byte[] data = new byte[capacity];
    int bytesRead = is.read(data);

    // Check for end of stream (no data read)
    if(bytesRead == -1){
      endReached = true;
      return;
    }

    // Copy new data into circular buffer
    for(int i=0; i<bytesRead; i++){
      byteBuffer[bbh] = data[i];
      bbh = (bbh+1) % BUFFER_SIZE;
    }
  }

}
