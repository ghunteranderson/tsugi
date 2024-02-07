package com.ghunteranderson.tsugi.sandbox.lexicon;

import java.io.IOException;

public interface CharacterInputStream {
  char next() throws IOException;
  boolean hasNext();
}
