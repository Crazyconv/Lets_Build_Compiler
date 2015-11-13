/* You do not need to change anything up here. */
package lexer;

import frontend.Token;
import static frontend.Token.Type.*;
import java.util.Map;
import java.util.HashMap;
import java.lang.String;

%%

%public
%final
%class Lexer
%function nextToken
%type Token
%unicode
%line
%column

%{
	Map<String, Token.Type> keywordMap = new HashMap<String, Token.Type>() {{
		put("boolean", BOOLEAN);
		put("break", BREAK);
		put("else", ELSE);
		put("false", FALSE);
		put("if", IF);
		put("import", IMPORT);
		put("int", INT);
		put("module", MODULE);
		put("public", PUBLIC);
		put("return", RETURN);
		put("true", TRUE);
		put("type", TYPE);
		put("void", VOID);
		put("while", WHILE);
	}};
	
	Map<String, Token.Type> punctuationMap = new HashMap<String, Token.Type>() {{
		put(",", COMMA);
		put("[", LBRACKET);
		put("{", LCURLY);
		put("(", LPAREN);
		put("]", RBRACKET);
		put("}", RCURLY);
		put(")", RPAREN);
		put(";", SEMICOLON);
	}};
	
	Map<String, Token.Type> operatorMap = new HashMap<String, Token.Type>() {{
		put("/", DIV);
		put("==", EQEQ);
		put("=", EQL);
		put(">=", GEQ);
		put(">", GT);
		put("<=", LEQ);
		put("<", LT);
		put("-", MINUS);
		put("!=", NEQ);
		put("+", PLUS);
		put("*", TIMES);			
	}};
	
	private Token token(Token.Type type) {
		String text = yytext();
		if(type == STRING_LITERAL)
			text = text.substring(1, text.length() - 1);
		return new Token(type, yyline, yycolumn, text);
	}

%}

/* This definition may come in handy. If you wish, you can add more definitions here. */
WhiteSpace = [ ] | \t | \f | \n | \r
Punctuation = "," | "[" | "{" | "(" | "]" | "}" | ")" | ";"
Operator = [=><](=)? | "!=" | "/" | "+" | "*" | "-"

%%
/* put in your rules here.    */
/* keywords & identifiers */
[_A-Za-z][_A-Za-z0-9]*      { Token.Type type = keywordMap.get(yytext());
                              	if(type == null)
                             		return token(ID);
                             	else
                             		return token(type); }
/* punctuation */
{Punctuation}               { return token(punctuationMap.get(yytext())); }
/* operators */
{Operator}                  { return token(operatorMap.get(yytext())); }
/* literals */
[0-9]+                      { return token(INT_LITERAL); }
\"[^\"]*\"                  { return token(STRING_LITERAL); }
/* while space */
{WhiteSpace}                {}
/* You don't need to change anything below this line. */
.							{ throw new Error("unexpected character '" + yytext() + "'"); }
<<EOF>>						{ return token(EOF); }
