/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import junit.framework.TestCase;

/**
 * @author Franz-Josef Elmer
 */
public class NanoCTest extends TestCase {

	public NanoCTest(String arg0) {
		super(arg0);
	}
	
	private void check(String expectedOutput, String code){
		NanoC nanoC = new NanoC();
//		System.out.println("---------------\n" + code + "\n");
		assertEquals(expectedOutput, nanoC.exec(code));
	}
	
	public void test1() {
		check("42", "main(){b=42;print(b);}");
		check("414243", "main(){b=42;print(41,b,43);}");
		check("414243", "main(){print();b=42;print(41,b,43);}");
	}

	public void testStringCharConstants() {
		check("hello world", "main(){print(\"hello world\");}");
		check("hello world", "main(){print(\"hello \",\"world\");}");
		check("hello world", "main(){print(\"hello\",' ',\"world\");}");
		check("\"hi\"\n\t\\w'o'rld", 
			  "main(){print(\"\\\"hi\\\"\\n\\t\\\\w\'o\'rld\");}");
		check("The answer is 42", "main() {\n"
								+ "  answer = 42;\n" 
								+ "  print(\"The answer is \", answer);\n"
								+ "}");
	}
	
	public void testFunctionCalls() {
		check("answer=42", "f(){return 42;}\n"
						 + "main(){a=f();print(\"answer=\",a);}\n");
		check("answer=42", "f(){return 42;}\n"
						 + "main(){a=f();print(\"answer=\",f());}\n");
		check("hello", "main(){print(\"hello\");return;print(\" world\");}");
		check("hello", "f(a){return a;}\n"
					 + "main(){print(f(\"hello\"));}");
	}
	
	public void testPostIncDec() {
		check("012321", "main(){a=0;print(a++,a++,a++,a--,a--,a--);}\n");
	}
	public void testPreIncDec() {
		check("012100", "main(){a=0;print(a,++a,++a,--a,--a,a);}\n");
	}
	
	public void testArithmeticOps() {
		check("42", "main(){a=6*7;print(a);}\n");
		check("42", "main(){a=10+5*6+2;print(a);}\n");
		check("42", "main(){a=15+8*8/2-5;print(a);}\n");
		check("42", "main(){a = 15 + 8 * 8 / 2 - 15 % 10;print(a);}\n");
		check("42", "f(a,b){return a + b;}\n" 
				  + "main(){a = 15 + f(2,6) * 8 / 2 - 15 % f(1,9);print(a);}\n");
	}
	
	public void testMinus() {
		check("42", "main(){a=-8+50;print(a);}");
		check("-42", "main(){a=7+(-50)*2+51;print(a);}");
	}
	
	public void testBracketExpression() {
		check("42", "main(){print((2+4)*7-8*(9-9));}");
		check("42", "main(){print((2+4)*7-(8)*(9-9));}");
		check("42", "f(a){return (a-1)*(a+1);}\n"
				  + "main(){print(2+(2-1)*(f(7)-8));}");
	}
	
	public void testNumericalCompareOps() {
		check("0", "main(){print(42<40);}");
		check("1", "main(){print(40<42);}");
		check("0", "main(){print(42<=40);}");
		check("1", "main(){print(42<=42);}");
		check("0", "main(){print(40>42);}");
		check("1", "main(){print(42>40);}");
		check("1", "main(){print(42>=40);}");
		check("1", "main(){print(42>=42);}");
		check("0", "main(){print(42>=43);}");
		check("0", "main(){print(42==40);}");
		check("0", "main(){print(42==43);}");
		check("1", "main(){print(42==42);}");
		check("1", "main(){print(42!=40);}");
		check("1", "main(){print(42!=43);}");
		check("0", "main(){print(42!=42);}");
		check("1", "main(){a=42;print(a==42);}");
		check("1", "main(){a=6;b=7;print(a*b+6-a==42);}");
	}

	public void testAlphabeticalCompareOps() {
		check("0", "main(){print(\"abc\"<\"ab\");}");
		check("0", "main(){print(\"abc\"<\"abc\");}");
		check("1", "main(){print(\"abc\"<\"abcd\");}");
		check("1", "main(){print(\"abc\">\"ab\");}");
		check("0", "main(){print(\"abc\">\"abc\");}");
		check("0", "main(){print(\"abc\">\"abcd\");}");
		check("1", "main(){print(\"abc\">=\"ab\");}");
		check("1", "main(){print(\"abc\">=\"abc\");}");
		check("0", "main(){print(\"abc\">\"abcd\");}");
	}
	
	public void testLogicAnd() {
		check(".0", "f(a){print('.');return a;}\n"
				  + "main(){print(f(0)&&f(0));}");
		check(".0", "f(a){print('.');return a;}\n"
				  + "main(){print(f(0)&&f(1));}");
		check("..0", "f(a){print('.');return a;}\n"
				   + "main(){print(f(1)&&f(0));}");
		check("..1", "f(a){print('.');return a;}\n"
				   + "main(){print(f(1)&&f(1));}");
	}
	
	public void testLogicOr() {
		check("..0", "f(a){print('.');return a;}\n"
				   + "main(){print(f(0)||f(0));}");
		check("..1", "f(a){print('.');return a;}\n"
				   + "main(){print(f(0)||f(1));}");
		check(".1", "f(a){print('.');return a;}\n"
				  + "main(){print(f(1)||f(0));}");
		check(".1", "f(a){print('.');return a;}\n"
				  + "main(){print(f(1)||f(1));}");
	}
	
	public void testNegate() {
		check("..1", "f(a){print('.');return a;}\n"
				   + "main(){print(!f(1)||f(1));}");
		check("..0", "f(a){print('.');return a;}\n"
				   + "main(){print(!f(1)||!f(1));}");
		check(".1", "f(a){print('.');return a;}\n"
				  + "main(){print(!f(0)||!f(1));}");
		check("..0", "f(a){print('.');return a;}\n"
				  + "main(){print(!(f(0)||f(1)));}");
	}
	
	public void testIfStatement() {
		check("42", "main(){if (1) print(42);}");
		check("", "main(){if (0) print(42);}");
		check("6*7", "main(){if (0) print(42); else print(\"6*7\");}");
		check("42", "main(){if (1) {print(4);print(2);}}");
		check("40", "main() {\n" 
				  + "  if (0) {\n"
				  + "    print(4);\n" 
				  +	"    print(2);\n" 
				  + "  } else {\n"
				  + "    print(40);\n" 
				  + "  }\n"
				  + "}");
	}
	
	public void testForStatement() {
		check("0123", "main(){for(i=0;i<4;i++) print(i);}");
		check("0123", "main(){i=0;for(;i<4;i++) print(i);}");
		check("0123", "main(){i=0;for(;i<4;) print(i++);}");
		check("0123", "main(){i=0;for(;i<4;) {print(i);i++;}}");
	}
	
	public void testWhileStatement() {
		check("0123", "main(){i=0;while(i<4) print(i++);}");
		check("0123", "main(){i=0;while(i<4) {print(i);i++;}}");
	}
	
	public void testNestedControlsAndLocalVariables() {
		check(" end 0 3 6 9 12 15 18 38",
			  "f(a) {\n"
			+ "  b = a / 2;\n"
			+ "  if (b % 3 == 0) {\n"
			+ "    a = b;\n"
			+ "    print(a, ' ');\n"
			+ "  }\n"
			+ "}\n"
			+ 
			"main() {\n"
			+ "  z = \" end \";\n"
			+ "  print(z);\n"
			+ "  for (i = 0; i < 20; i++) {\n"
			+ "    z = 2 * i;\n"
			+ "    f(z);\n"
			+ "  }\n"
			+ "  print(z);\n"
			+ "}"
			);
	}
	
	public void testBreakStatement() {
		check("012345",
			  "main() {"
			+ "  for(i = 0; i < 10; i++) {"
			+ "    if (i % 7 == 6) break;"
			+ "    print(i);"
			+ "  }"
			+ "}");
		check("012345",
			  "main() {"
			+ "  for(i = 0; i < 10; i++) {"
			+ "    if (i % 7 == 6) {"
			+ "      a = i * 2;" 
			+ "      break;"
			+ "    }"
			+ "    print(i);"
			+ "  }"
			+ "}");
		check("012345",
			  "main() {"
			+ "  for(i = 0; i < 10; i++) {"
			+ "    if (i % 7 == 6) {"
			+ "      a = i * 2;"
			+ "      if (a == 13) {"
			+ "        b = a + 3;"
			+ "      } else "
			+ "      break;"
			+ "    }"
			+ "    print(i);"
			+ "  }"
			+ "}");
		check("0 0 1 3 6 10 15 15 15 15 ",
			  "main() {"
			+ "  for(i = 0; i < 10; i++) {"
			+ "    s = 0;"
			+ "    for (j = 0; j < i; j++) {"
			+ "      s = s + j;"
			+ "      if (j == 5) {"
			+ "        break;"
			+ "      }"
			+ "    }"
			+ "    print(s,' ');"
			+ "  }"
			+ "}");
		
	}
	
	public void testComments() {
		check("012345",
			  "/* A comment in the first line. */\n"
			+ "main() {\n"
			+ "  // for loop\n"
			+ "  // with break\n"
			+ "  for(i = 0; i < 10; i++) {\n"
			+ "    /*** BLOCK COMMENTfor ***/\n"
			+ "    if (i % 7 == 6) break;\n"
			+ "    print(i); //abc\n"
			+ "  }//\n"
			+ "}");
	}
}
