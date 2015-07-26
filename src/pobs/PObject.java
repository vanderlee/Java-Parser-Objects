/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs;

/**
 * Defines the method signature required to link Parser OBjectS together.
 * <p>
 * To make such a web of Parser OBjectS thread safe this interface should be
 * implemented in such a way that it is guranteed that after invocation of
 * {@link #process(PScanner, long, PContext)}no instance field is modified
 * directly or indirectly. Everthing what is needed to provide thread-safe
 * processing should be delivered by the three arguments of the process method.
 * 
 * @author Martijn W. van der Lee
 * @author Franz-Josef Elmer
 */
public interface PObject {
	/**
	 * Processes the input from the specified begin offset using the specified
	 * context.
	 * 
	 * @param input
	 *            a PScanner which contains the entire input sequence
	 * @param begin
	 *            the index location into the input from where to start
	 *            processing
	 * @param context
	 *            the current processing context
	 * @return the processing result
	 * @see PMatch
	 */
	public PMatch process(PScanner input, long begin, PContext context);
}