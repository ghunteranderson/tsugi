package com.ghunteranderson.tsugi.sandbox.syntax;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;

@Data
public class GenericNode {
  private SourceLocation location;

  public GenericNode(SourceLocation location){
    this.location = location;
  }

}
