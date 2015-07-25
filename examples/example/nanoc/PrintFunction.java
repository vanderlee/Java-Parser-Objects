/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package example.nanoc;

import java.io.PrintWriter;

/**
 * @author Franz-Josef Elmer
 */
public class PrintFunction extends Function {
	private final PrintWriter[] writers;
	public PrintFunction() {
		super("print", new Variable[0]);
		writers = new PrintWriter[] {new PrintWriter(System.out)};
	}
	
	public PrintFunction(PrintWriter[] writers) {
		super("print", new Variable[0]);
		this.writers = writers;
	}
	
	public Object call(Object[] argumentValues) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < argumentValues.length; i++) {
			buffer.append(argumentValues[i]);
		}
		String output = new String(buffer);
		for (int i = 0; i < writers.length; i++) {
			writers[i].print(output);
			writers[i].flush();
		}
		return "";
	}
}
