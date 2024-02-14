package com.ghunteranderson.tsugi.lexicon;

import java.io.IOException;

public interface CharacterInputStream {
  char next() throws IOException;
  boolean hasNext();
}
