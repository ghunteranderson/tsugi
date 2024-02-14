package com.ghunteranderson.tsugi.lexicon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.ghunteranderson.tsugi.TestUtils;

public class Utf8CharacterInputStreamTest {
  

  @ParameterizedTest
  @ValueSource(strings={
    "ABC\nabc\t123 !@#",
    "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ"
  })
  public void testAsciiStream(String string) throws IOException {
    checkString(string);
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "© The Company 2024",
    "The © Company 2024",
    "The Company 2024©"
  })
  public void twoByteCharacters(String string) throws IOException {
    checkString(string);
  }

  @ParameterizedTest
  @ValueSource(strings = {
    "x + 3 ≤ 4y",
    "x + 3 ≥ 4y",
    "x ⫓ {1, 3, 4}",
  })
  public void threeByteCharacters(String string) throws IOException {
    checkString(string);
  }

  private void checkString(String string) throws IOException {
    var is = new Utf8CharacterInputStream(TestUtils.streamString(string));
    for(int i=0; i<string.length(); i++){
      assertTrue(is.hasNext(), "Failed at i=" + i);
      assertEquals(string.charAt(i), is.next(), "Failed at i=" + i);
    }
    assertFalse(is.hasNext());
  }
}
