/**
 * 
 */
package app.exceptions;

/*
 * (non-Javadoc)
 * 
 * @see java.lang.IllegalArgumentException
 */
public class EmptyVectorException extends IllegalArgumentException {

	private static final long serialVersionUID = -1973465260883957756L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.IllegalArgumentException#IllegalArgumentException()
	 */
	public EmptyVectorException() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.IllegalArgumentException#IllegalArgumentException(java.lang.String)
	 */
	public EmptyVectorException(String s) {
		super(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.IllegalArgumentException#forInputStream(java.lang.String)
	 */
	static EmptyVectorException forInputStream(String s) {
		return new EmptyVectorException("For input string: \"" + s + "\"");
	}
}
