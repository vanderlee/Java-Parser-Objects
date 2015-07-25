/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.java.test;

import example.java.*;
import test.TestCaseParser;
import pobs.PDirective;
import pobs.PObject;

/**
 * @author	Martijn W. van der Lee 
 */
public class JavaTest extends TestCaseParser {
	public JavaTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(JavaTest.class);
	}

	public void testPackageDeclaration() {
		Java java = new Java();
		PObject p = java.ptr_package_declaration;

		assertMismatch("Zero-length input", parseJava(p, ""), 0);
		assertMismatch("None", parseJava(p, "package;"), 7);	
		assertMatch("One", parseJava(p, "package one;"), 12);	
		assertMatch("Two", parseJava(p, "package one.two;."), 16);	
		assertMismatch("Two no WS", parseJava(p, "packageone.two;."), 7);	
		assertMismatch("Two fail", parseJava(p, "package one.two.;"), 15);	
	}

	public void testImportDeclarations() {
		Java java = new Java();
		PObject p = java.ptr_import_declarations;

		assertMismatch("Zero-length input", parseJava(p, ""), 0);
		assertMismatch("No package", parseJava(p, "import;x"), 0);	
		assertMatch("Single type", parseJava(p, "import a.b.C;x"), 13);	
		assertMatch("On demand", parseJava(p, "import a.b.*;x"), 13);	
		assertMatch("Single type, WS", parseJava(p, "import a . b . C;x"), 17);	
		assertMatch("On demand, WS", parseJava(p, "import a . b . *;x"), 17);	
		assertMatch("Multiple", parseJava(p, "import a.B;\nimport c.*;x"), 23);	
		assertMismatch("Multiple no WS", parseJava(p, "importa.B;\nimport c.*;x"), 0);	
	}
	
	public void testArrayInitializer() {
		Java java = new Java();
		PObject p = java.ptr_array_initializer;

		assertMismatch("Zero-length input", parseJava(p, ""), 0);
		assertMatch("Simple", parseJava(p, "{1,2}"), 5);
		//assertMatch("Simple, WS", parseJava(p, "{ 1 , 2 }"), 9);
		//assertMatch("Recursive", parseJava(p, "{{1,2},{2,3}}"), 12);
	}
	
	public void testType() {
		Java java = new Java();
		PObject p = java.ptr_type;

		assertMismatch("Zero-length input", parseJava(p, ""), 0);
		assertMatch("Boolean", parseJava(p, "boolean"), 7);
		assertMatch("Class/Interface", parseJava(p, "example.java.Java"), 17);
		assertMatch("Array", parseJava(p, "int[ ]"), 6);
		assertMatch("Float", parseJava(p, "float"), 5);
		assertMatch("Int", parseJava(p, "byte"), 4);
	}
	
	public void testTypeDeclarations() {
		Java java = new Java();
		PObject p = java.ptr_type_declarations;

		assertMatch("No type", parseJava(p, ";"), 1);
	}
	
	public void testJava() {
		Java java = new Java();
		assertMatch("Zero-length input", parseJava(java, ""), 0);
	}
	
	final public pobs.PMatch parseJava(pobs.PObject parser, java.lang.String input) {
		PObject s = new example.java.JavaSkipper();	
		
		return super.parse(parser, input, new PDirective(s));
	}	
}