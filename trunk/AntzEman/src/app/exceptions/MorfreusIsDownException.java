package app.exceptions;

/**
 * Morfeus erorita dagoenean emango den salbuespena.
 * 
 * @author Blizarazu
 * 
 */
public class MorfreusIsDownException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Morfeus zerbitzaria erorita dagoela informatzeko mezua zein emailera
	 * bidali behar den
	 */
	private String informMail;

	/**
	 * Salbuespena eman duen unean analizatzen ari zen Stringa
	 */
	private String str;

	/**
	 * MorfeusIsDownException salbuspena hasieratzen du.
	 */
	public MorfreusIsDownException() {
		super();
		informMail = "";
	}

	/**
	 * MorfeusIsDownException salbuspena hasieratzen du Morfeus zerbitzaria
	 * erorita dagoela informatzeko emailarekin.
	 * 
	 * @param str
	 *            Salbuespena eman duen unean analizatzen ari zen Stringa
	 * @param informMail
	 *            Morfeus zerbitzaria erorita dagoela informatzeko mezua zein
	 *            emailera bidali behar den
	 */
	public MorfreusIsDownException(String str, String informMail) {
		this.informMail = informMail;
		this.str = str;
	}

	/**
	 * Morfeus zerbitzaria erorita dagoela informatzeko mezua zein emailera
	 * bidali behar den itzultzen du.
	 * 
	 * @return Morfeus zerbitzaria erorita dagoela informatzeko mezua zein
	 *         emailera bidali behar den.
	 */
	public String getInformMail() {
		return informMail;
	}

	public String getStr() {
		return this.str;
	}
}
