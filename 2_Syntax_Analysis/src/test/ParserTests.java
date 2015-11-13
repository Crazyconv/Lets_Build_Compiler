package test;

import static org.junit.Assert.fail;

import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import parser.Parser;

public class ParserTests {
	private void runtest(String src) {
		runtest(src, true);
	}

	private void runtest(String src, boolean succeed) {
		Parser parser = new Parser();
		try {
			parser.parse(new Lexer(new StringReader(src)));
			if(!succeed) {
				fail("Test was supposed to fail, but succeeded");
			}
		} catch (beaver.Parser.Exception e) {
			if(succeed) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		} catch (Throwable e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testEmptyModule() {
		runtest("module Test { }");
		
		runtest("module Test { " 
				+ " import CZ3007;"
				+ " abc [][][] arrTest;"
				+ " int Deposit;"
				+ " public type id = \"abc\";"
				+ " public int calculateDeposit(int hours, boolean a){"
				+ " 	return arrTest = \"aaa\";"
				+ "	}"
				+ " public boolean isStudent(abc a){"
				+ " 	Student a;"
				+ " 	{"
				+ "			break;"	
				+ "			return;"
				+ "		}"
				+ " 	1;"
				+ "		break;"
				+ "		while(1){"
				+ "			break;"
				+ " 	}"
				+ "		if(1 < a){"
				+ "			break;"
				+ " 	}else{"
				+ "			return;"
				+ "		}"	
				+ " 	return -arrTest(arr[3] = \"bbb\",24) * 3 + 24 <= 25;"
				+ "	}"
				+ "}" );
	}
}
