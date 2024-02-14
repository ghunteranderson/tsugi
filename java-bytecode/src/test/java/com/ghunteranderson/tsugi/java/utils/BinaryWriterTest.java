package com.ghunteranderson.tsugi.java.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.ghunteranderson.java.utils.BinaryWriter;

public class BinaryWriterTest {

  @Test
  void test_writeU(){
    var writer = new BinaryWriter();

    writer.writeU1(0x11);
    writer.writeU2(0x2233);
    writer.writeU4(0x44556677);

    assertEquals(7, writer.size());

    byte[] out = writer.toBytes();
    int i = 0;
    assertEquals(0x11, out[i++]);
    assertEquals(0x22, out[i++]);
    assertEquals(0x33, out[i++]);
    assertEquals(0x44, out[i++]);
    assertEquals(0x55, out[i++]);
    assertEquals(0x66, out[i++]);
    assertEquals(0x77, out[i++]);
  }

  @Test
  void test_writeAll(){
    var writer = new BinaryWriter();

    writer.writeAll(new byte[]{0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77});

    assertEquals(7, writer.size());

    byte[] out = writer.toBytes();
    int i = 0;
    assertEquals(0x11, out[i++]);
    assertEquals(0x22, out[i++]);
    assertEquals(0x33, out[i++]);
    assertEquals(0x44, out[i++]);
    assertEquals(0x55, out[i++]);
    assertEquals(0x66, out[i++]);
    assertEquals(0x77, out[i++]);
  }

  @Test
  void test_insertLiveWriter(){
    var primary = new BinaryWriter();
    primary.writeU4(0x01020304);
    var secondary = primary.insertLiveWriter();
    primary.writeU4(0x05060708);
    secondary.writeU2(0x1234);

    assertEquals(10, primary.size());
    assertEquals(2, secondary.size());

    var out = primary.toBytes();
    int i = 0;
    assertEquals(0x01, out[i++]);
    assertEquals(0x02, out[i++]);
    assertEquals(0x03, out[i++]);
    assertEquals(0x04, out[i++]);
    // Secondary writer starts
    assertEquals(0x12, out[i++]);
    assertEquals(0x34, out[i++]);
    // Primary writer resumes
    assertEquals(0x05, out[i++]);
    assertEquals(0x06, out[i++]);
    assertEquals(0x07, out[i++]);
    assertEquals(0x08, out[i++]);

  }
  
}
