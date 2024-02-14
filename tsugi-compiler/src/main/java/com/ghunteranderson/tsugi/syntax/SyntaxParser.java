package com.ghunteranderson.tsugi.syntax;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ghunteranderson.tsugi.ErrorCode;
import com.ghunteranderson.tsugi.TsugiCompilerException;
import com.ghunteranderson.tsugi.lexicon.Token;
import com.ghunteranderson.tsugi.syntax.OperationExpressionNode.OperationType;

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
        module.getFunctions().add(parseFunctionDeclaration());
      else
        throw new TsugiCompilerException(
          ErrorCode.INVALID_SYNTAX,
          token.location(),
          "Unexpected token while parsing module: " + token.lexeme());
      
    }

    return module;
  }

  private List<QualifiedRefNode> parseImports(){
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

  private FunctionDeclarationNode parseFunctionDeclaration(){
    // Parse Declaration
    var funcToken = in.next(Token.FUNCTION);
    var returnType = parseQualifiedRef();
    var funcName = in.next(Token.IDENTIFIER);

    // Parse Parameters (not supported yet)
    in.next(Token.PAREN_L);
    in.next(Token.PAREN_R);

    var body = parseStatementBlock();

    var function = new FunctionDeclarationNode(funcName.lexeme(), funcToken.location());
    function.setReturnType(returnType);
    function.setStatements(body);
    return function;
  }

  private List<StatementNode> parseStatementBlock(){
    in.next(Token.BRACE_L);

    var statements = new LinkedList<StatementNode>();

    while(true){
      var token = in.peek();
      if(token.is(Token.BRACE_R))
        break;
      else
        statements.addAll(parseStatement());
    }

    in.next(Token.BRACE_R);
    return statements;
  }

  private List<StatementNode> parseStatement(){
    var statements = new ArrayList<StatementNode>(2);
    var ref = parseQualifiedRef();
    
    var next = in.peek();
    if(next.is(Token.PAREN_L)){
      // Statement is a function call
      var args = parseFunctionArguments();
      var funcStat = new FunctionStatementNode(ref.getLocation());
      funcStat.setRef(ref);
      funcStat.setArgs(args);
      statements.add(funcStat);
    }
    else if(next.is(Token.IDENTIFIER)){
      // Statement is a variable declaration
      var varName = in.next();
      var varDec = new VariableDeclarationNode(varName.location());
      varDec.setType(ref);
      varDec.setName(varName.lexeme());
      // Check for an assignment
      next = in.peek();
      if(next.is(Token.EQUALS)){
        var exp = parseExpression();
        var varRef = new QualifiedRefNode(varName.location());
        varRef.setIdentifiers(List.of(varName.lexeme()));
        var varAssign =  new VariableAssignmentNode(next.location());
        varAssign.setValue(exp);
        varAssign.setRef(varRef);
        statements.add(varAssign);
      }
    }
    else if(next.is(Token.EQUALS)){
      // Statement is an assignment
      var exp = parseExpression();
      var varAssign = new VariableAssignmentNode(ref.getLocation());
      varAssign.setLocation(ref.getLocation());
      varAssign.setValue(exp);
      statements.add(varAssign);
    }
    else {
      throw new TsugiCompilerException(ErrorCode.INVALID_SYNTAX, ref.getLocation());
    }
    in.next(Token.SEMICOLON);


    return statements;
  }

  // Parses the actual arguments in a function invocation (starting with PAREN_L)
  private List<ExpressionNode> parseFunctionArguments(){
    var args = new ArrayList<ExpressionNode>();
    // Parse Arguments
    in.next(Token.PAREN_L);
    var token = in.peek();
    if(token.isNot(Token.PAREN_R)){
      // Function has arguments that need to be parsed.
      while(true){
        args.add(parseExpression()); 
        if(in.peek().is(Token.COMMA)){
          in.next(); // Consume comma and expect another argument
          continue;
        }
        else
          break;
      
      }
    }
    in.next(Token.PAREN_R);
    return args;
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
    var left = praseTerm();
    var opToken = in.peek();
    OperationType op = switch(opToken.token()){
      case PLUS -> OperationType.ADD;
      case SLASH_F -> OperationType.DIV;
      default -> null;
    };
    if(op == null)
      return left;
    else
      in.next(); // Consume operator
    
    var right = praseTerm();

    var opNode = new OperationExpressionNode(opToken.location());
    opNode.setLeft(left);
    opNode.setOp(op);
    opNode.setRight(right);
    return opNode;
  }

  private ExpressionNode praseTerm(){
    var left = parseFactor();
    var opToken = in.peek();
    OperationType op = switch(opToken.token()){
      case ASTERISK -> OperationType.MUL;
      case SLASH_F -> OperationType.DIV;
      default -> null;
    };
    if(op == null)
      return left;
    else
      in.next(); // Consume operator
    
    var right = parseFactor();

    var opNode = new OperationExpressionNode(opToken.location());
    opNode.setLeft(left);
    opNode.setOp(op);
    opNode.setRight(right);
    return opNode;
  }

  private ExpressionNode parseFactor(){
    var token = in.peek();

    // Factor is a string
    if(token.is(Token.STRING)){
      token = in.next();
      return new LiteralStringNode(token.lexeme(), token.location());
    }
    // Factor is a number
    else if(token.is(Token.NUMBER)){
      token = in.next();
      return new LiteralNumberNode(token.lexeme(), token.location());
    }
    else if(token.is(Token.IDENTIFIER)){
      var ref = parseQualifiedRef();
      token = in.peek();
      // Factor is a variable
      if(token.isNot(Token.PAREN_L)){
        return new VariableExpressionNode(ref.getLocation(), ref);
      }
      // Factor is a function call
      else {
        var args = parseFunctionArguments();
        var func = new FunctionExpressionNode(ref.getLocation()); 
        func.setRef(ref);
        func.setArgs(args);
        return func;
      }
    }
    else {
      throw new TsugiCompilerException(ErrorCode.INVALID_SYNTAX, token.location(), "Invalid token while parsing expression: " + token);
    }
  }



  
  
}
