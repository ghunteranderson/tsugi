package com.ghunteranderson.tsugi.syntax;

import java.util.ArrayList;
import java.util.List;

import com.ghunteranderson.tsugi.lexicon.SourceLocation;

import lombok.Data;

@Data
public class QualifiedRefNode {

  private SourceLocation location;
  private List<String> identifiers;

  public QualifiedRefNode(SourceLocation location){
    this.location = location;
    identifiers = new ArrayList<>();
  }

}
