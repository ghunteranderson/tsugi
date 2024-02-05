package com.ghunteranderson.tsugi.sandbox.syntax;

import java.util.ArrayList;
import java.util.List;

import com.ghunteranderson.tsugi.sandbox.lexicon.SourceLocation;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QualifiedRefNode extends GenericNode {

  private List<String> identifiers;

  public QualifiedRefNode(SourceLocation location){
    super(location);
    identifiers = new ArrayList<>();
  }
  
}
