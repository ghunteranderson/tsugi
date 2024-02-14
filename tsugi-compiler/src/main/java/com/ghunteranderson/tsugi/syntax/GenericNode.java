package com.ghunteranderson.tsugi.syntax;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Data;

@Data
public class GenericNode {
  private SourceLocation location;

  public GenericNode(SourceLocation location){
    this.location = location;
  }

}
