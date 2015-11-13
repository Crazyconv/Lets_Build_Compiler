package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. You
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual, expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					return;
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	/* keyword and whitespace */
	@Test
	public void testKWWS(){
		runtest("while void\ttype\rtrue\nreturn\fpublic module int import if false else break boolean",
				new Token(WHILE, 0, 0, "while"),
				new Token(VOID, 0, 6, "void"),
				new Token(TYPE, 0, 11, "type"),
				new Token(TRUE, 1, 0, "true"),
				new Token(RETURN, 2, 0, "return"),
				new Token(PUBLIC, 3, 0, "public"),
				new Token(MODULE, 3, 7, "module"),
				new Token(INT, 3, 14, "int"),
				new Token(IMPORT, 3, 18, "import"),
				new Token(IF, 3, 25, "if"),
				new Token(FALSE, 3, 28, "false"),
				new Token(ELSE, 3, 34, "else"),
				new Token(BREAK, 3, 39, "break"),
				new Token(BOOLEAN, 3, 45, "boolean"),
				new Token(EOF, 3, 52, "")
				);
	}
	
	/* punctuation */
	@Test
	public void testPunc(){
		runtest(",;[]{}()",
				new Token(COMMA, 0, 0, ","),
		        new Token(SEMICOLON, 0, 1, ";"),
		        new Token(LBRACKET, 0, 2, "["),
		        new Token(RBRACKET, 0, 3, "]"),
		        new Token(LCURLY, 0, 4, "{"),
		        new Token(RCURLY, 0, 5, "}"),
		        new Token(LPAREN, 0, 6, "("),
		        new Token(RPAREN, 0, 7, ")"),
		        new Token(EOF, 0, 8, "")
				);
	}
	
	/* operator */
	@Test
	public void testOp(){
		runtest("/===>=><=<-!=+*",
				new Token(DIV, 0, 0, "/"),
				new Token(EQEQ, 0, 1, "=="),
				new Token(EQL, 0, 3, "="),
				new Token(GEQ, 0, 4, ">="),
				new Token(GT, 0, 6, ">"),
				new Token(LEQ, 0, 7, "<="),
				new Token(LT, 0, 9, "<"),
				new Token(MINUS, 0, 10, "-"),
				new Token(NEQ, 0, 11, "!="),
				new Token(PLUS, 0, 13, "+"),
				new Token(TIMES, 0, 14, "*"),
				new Token(EOF, 0, 15, "")
				);
	}
	
	/* identifier */
	@Test
	public void testID(){
		runtest("_a1 A2_ _ a",
				new Token(ID, 0, 0, "_a1"),
				new Token(ID, 0, 4, "A2_"),
				new Token(ID, 0, 8, "_"),
				new Token(ID, 0, 10, "a"),
				new Token(EOF, 0, 11, "")
				);
		runtest("1a_", 
				new Token(INT_LITERAL, 0, 0, "1"),
				new Token(ID, 0, 1, "a_"),
				new Token(EOF, 0, 3, "")
				);
	}
	
	
	/* literal */
	@Test
	public void testLt(){
		runtest("0123 \"@#$\"",
				new Token(INT_LITERAL, 0, 0, "0123"),
				new Token(STRING_LITERAL, 0, 5, "@#$"),
				new Token(EOF, 0, 10, "")
				);
		runtest("\"\"", new Token(STRING_LITERAL, 0, 0, ""),
				new Token(EOF, 0, 2, ""));
	}
	
	/* identifier VS keyword */
	@Test
	public void testIDKW(){
		runtest("if if2",
				new Token(IF, 0, 0, "if"),
				new Token(ID, 0, 3, "if2"),
				new Token(EOF, 0, 6, "")
				);
	}
	
	/* string literal VS others */
	@Test
	public void testStrLt(){
		runtest("\"if <= + hello\"",
				new Token(STRING_LITERAL, 0, 0, "if <= + hello"),
				new Token(EOF, 0, 15, "")
				);
	}
	
		
	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				(Token)null);
	}
	
	@Test
	public void testStringLiteralEscapeCharacter() {
		runtest("\"\\n\"",
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}
	
}
