/*
 * Copyright (c) 2004 Parser OBjectS group
 * Released under ZLib/LibPNG license. See "license.txt" for details.
 */
package pobs.errorhandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Vector;

import pobs.PErrorHandler;
import pobs.PParser;
import pobs.PScanner;
import pobs.scanner.PPosition;
import pobs.scanner.PTextScanner;

/**
 * Default error handler. Creates pretty error messages.
 * 
 * @author Franz-Josef Elmer
 */
public class PDefaultErrorHandler implements PErrorHandler {

    /**
     * Immutable class for keeping track of position and cause of a syntax 
     * error.
     */
    private static class SyntaxErrorEntry {
        public final long position;

        public final String info;

        /**
         * Sole constructor.
         * 
         * @param position
         *            index position in the input at which the error occurred
         * @param parser
         *            the parser which caused the error
         */
        public SyntaxErrorEntry(long position, PParser parser) {
            this.position = position;
            this.info = parser.getErrorInfo();
        }
    }

    private static class SemanticError {
    	public final long position;
    	public final Exception exception;
    	public SemanticError(long position, Exception exception) {
    		this.position = position;
    		this.exception = exception;
    	}
    }

    private static String renderPosition(long index, PScanner scanner) {
    	if (scanner instanceof PTextScanner) {
    		PPosition p = ((PTextScanner) scanner).getPosition(index);
			return "line " + (p.getLine() + 1) 
					+ " column " + (p.getColumn() + 1);
    	} else {
    		return Long.toString(index);
    	}
    }
    
    private final Vector<SyntaxErrorEntry> list = new Vector<SyntaxErrorEntry>();
    private final Vector<SemanticError> semanticErrors = new Vector<SemanticError>();
    private final boolean showStackTrace;

    private long maxPosition;
    
    
	public PDefaultErrorHandler(boolean showStackTrace) {
		this.showStackTrace = showStackTrace;
	}
	
    public void notifySyntaxError(long position, PParser parser) {
      	list.addElement(new SyntaxErrorEntry(position, parser));
        maxPosition = Math.max(maxPosition, position);
    }
    
	public void notifySemanticError(long position, RuntimeException exception) {
		semanticErrors.addElement(new SemanticError(position, exception));

	}
    public long getErrorPosition() {
        return maxPosition;
    }
    
	public boolean semanticErrorOccured() {
		return semanticErrors.size() > 0;
	}

	public String createErrorMessage(PScanner input) {
        StringBuffer buffer = new StringBuffer();
        if (semanticErrorOccured()) {
        	createSemanticErrorMessages(buffer, input);
        } else {
            createSyntaxErrorMessage(buffer, input);
        }
        return new String(buffer);
    }

	private void createSyntaxErrorMessage(StringBuffer buffer, PScanner input) {
		buffer.append("Error at ").append(renderPosition(maxPosition, input))
			  .append(": Expecting either ");
		removeEntries();
		for (int i = 0, n = list.size(); i < n; i++) {
		    SyntaxErrorEntry errorEntry = list.elementAt(i);
		    String delim = "";
		    if (i > 0) {
		        if (i < n - 1) {
		            delim = ", ";
		        } else {
		            delim = n > 2 ? ", or " : " or ";
		        }
		    }
		    buffer.append(delim).append(errorEntry.info);
		}
	}

	private void createSemanticErrorMessages(StringBuffer buffer, PScanner input) {
		for (int i = 0, n = semanticErrors.size(); i < n; i++) {
			SemanticError e = semanticErrors.elementAt(i);
			buffer.append("Error at ").append(renderPosition(e.position, input))
				  .append(": ").append(e.exception.getMessage()).append("\n");
			if (showStackTrace) {
		    	StringWriter writer = new StringWriter();
		    	e.exception.printStackTrace(new PrintWriter(writer));
				buffer.append(writer).append('\n');
			}
		}
	}

	/**
     * Removes any error entries in the list which occurred before the index
     * position at which the error occurred or which have an info which 
     * already occured.
     */
    private void removeEntries() {
    	Hashtable<String, String> errorInfos = new Hashtable<String, String>();
        for (int i = list.size() - 1; i >= 0; i--) {
            SyntaxErrorEntry entry = list.elementAt(i);
            if (entry.position < maxPosition || entry.info == null) {
                list.removeElementAt(i);
            } else {
            	if (errorInfos.get(entry.info) == null) {
            		errorInfos.put(entry.info, entry.info);
            	} else {
            		list.removeElementAt(i);
            	}
            }
        }
    }
    
}