package com.ghunteranderson.tsugi.semantics;

import com.ghunteranderson.tsugi.syntax.QualifiedRefNode;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class TypeUtils {
  
  public static String qualifiedRefToString(QualifiedRefNode typeRef){
    var idents = typeRef.getIdentifiers();
    var sb = new StringBuilder()
      .append(idents.get(0));

    for(var i = 1; i<idents.size(); i++){
      sb.append('.').append(idents.get(i));
    }
    return sb.toString();
  }

}
