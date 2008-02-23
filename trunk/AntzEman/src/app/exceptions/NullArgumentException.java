/**
 * 
 */
package app.exceptions;

/*
 * (non-Javadoc)
 * 
 * @see java.lang.IllegalArgumentException
 */
public class NullArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = 4967144103236120962L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.IllegalArgumentException#IllegalArgumentException()
	 */
	public NullArgumentException() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.IllegalArgumentException#IllegalArgumentException(java.lang.String)
	 */
	public NullArgumentException(String s) {
		super(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.IllegalArgumentException#forInputStream(java.lang.String)
	 */
	static NullArgumentException forInputStream(String s) {
		return new NullArgumentException("For input string: \"" + s + "\"");
	}
}
