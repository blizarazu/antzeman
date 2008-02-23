package app.exceptions;

/**
 * Web orri batena ez den URL bat kargatzen saiatzenrakoan emango den
 * salbuespena.
 * 
 * @author Blizarazu
 * 
 */
public class NotWebPageException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Fitxategia bada, horren luzapena, adibidez doc edo pdf.
	 */
	private String fileExtension;

	/**
	 * NotWebPageException hasieratzen du.
	 */
	public NotWebPageException() {
		super();
	}

	/**
	 * NotWebPageException hasieratzen du pasatako fitxategi luzapenarekin.
	 * 
	 * @param fileExtension
	 *            fitxategiaren luzapena
	 */
	public NotWebPageException(String fileExtension) {
		super();
		this.fileExtension = fileExtension;
	}

	/**
	 * Fitxategiaren luzapena itzultzen du.
	 * 
	 * @return fitxategiaren luzapena.
	 */
	public String getFileExtension() {
		return fileExtension;
	}
}
