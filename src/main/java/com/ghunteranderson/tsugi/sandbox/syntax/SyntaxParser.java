package com.ghunteranderson.tsugi.sandbox.syntax;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.sandbox.ErrorCode;
import com.ghunteranderson.tsugi.sandbox.TsugiCompilerException;
import com.ghunteranderson.tsugi.sandbox.lexicon.Token;

public class SyntaxParser {

  private final CheckedLexicalAnalyzer in;

  public SyntaxParser(InputStream source){
    in = new CheckedLexicalAnalyzer(source);
  }

  public ModuleNode parseModule(){
    var moduleKeyword = in.next(Token.MODULE);
    var moduleIdent = in.next(Token.IDENTIFIER);
    in.next(Token.SEMICOLON);

    var module = new ModuleNode(moduleIdent.lexeme(), moduleKeyword.location());

    // Consume rest of file for now.
    while(true){
      var token = in.peek();

      if(token.is(Token.EOF))
        break;
      else if(token.is(Token.IMPORT))
        module.setImports(parseImports());
      else if(token.is(Token.FUNCTION))
        module.getFunctions().add(parseFunction());
      else
        throw new TsugiCompilerException(
          ErrorCode.INVALID_SYNTAX,
          token.location(),
          "Unexpected token while parsing module: " + token.lexeme());
      
    }

    return module;
  }

  public List<QualifiedRefNode> parseImports(){
    var imports = new LinkedList<QualifiedRefNode>();

    in.next(Token.IMPORT);
    in.next(Token.PAREN_L);

    var token = in.peek();

    if (token.is(Token.IDENTIFIER)){
      imports.add(parseQualifiedRef());

      // We found one import. There may be more.
      while(true){
        token = in.peek();
        if(token.isNot(Token.COMMA))
          break;

        // Comma found, there may be an identifier
        in.next(); // Consume comma
        token = in.peek();
        if(token.is(Token.IDENTIFIER))
          imports.add(parseQualifiedRef());
        else
          break;
      }
    }

    in.next(Token.PAREN_R);
    return imports;
  }

  private FunctionNode parseFunction(){
    // Parse Declaration
    var funcToken = in.next(Token.FUNCTION);
    var returnType = in.next(Token.IDENTIFIER);
    var funcName = in.next(Token.IDENTIFIER);

    // Parse Parameters (not supported yet)
    in.next(Token.PAREN_L);
    in.next(Token.PAREN_R);

    var body = parseStatementBlock();

    var function = new FunctionNode(funcName.lexeme(), funcToken.location());
    function.setReturnType(returnType.lexeme());
    function.setBody(body);

    return function;
  }

  private StatementBlockNode parseStatementBlock(){
    var lbraceToken = in.next(Token.BRACE_L);

    var block = new StatementBlockNode(lbraceToken.location());
    var statements = block.getStatements();

    while(true){
      var token = in.peek();
      if(token.is(Token.BRACE_R))
        break;
      else
        statements.add(parseStatement());
    }

    in.next(Token.BRACE_R);
    return block;
  }

  private StatementNode parseStatement(){
    // For now, assuming everything is a function call
    
    var ref = parseQualifiedRef();
    var invocationNode = new FunctionInvocationNode(ref.getLocation());
    invocationNode.setRef(ref);

    
    // Parse Arguments
    in.next(Token.PAREN_L);
    while(true){
      var token = in.peek();
      if(token.is(Token.PAREN_R))
        break;
      else {
        var expression = parseExpression();
        invocationNode.getArgs().add(expression); 
      }
    }
    in.next(Token.PAREN_R);
    
    in.next(Token.SEMICOLON);

    return new InvocationStatement(invocationNode);
  }

  private QualifiedRefNode parseQualifiedRef(){
    var firstToken = in.next(Token.IDENTIFIER);
    var refNode = new QualifiedRefNode(firstToken.location());
    var idents = refNode.getIdentifiers();

    idents.add(firstToken.lexeme());

    while(true){
      var token = in.peek();
      if(token.is(Token.DOT)){
        in.next(Token.DOT);
        token = in.next(Token.IDENTIFIER);
        idents.add(token.lexeme());
      }
      else
        break;
    }
    return refNode;
  }

  private ExpressionNode parseExpression(){
    // Assumes only string expressions
    var string = in.next(Token.STRING);
    var stringNode = new StringNode(string.lexeme(), string.location());
    return stringNode;
  }

  
  
}
