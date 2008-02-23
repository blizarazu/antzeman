package app.analisiak.bilaketaGuneak;

import java.util.Vector;

import org.htmlparser.util.ParserException;

import app.objektuak.Link;

/**
 * Bilaketzeko guneentzat interfazea.
 * 
 * @author Blizarazu
 * 
 */
public interface BilaketaGunea {

	/**
	 * Bilatuko den testuaren hizkuntza ezartzen du
	 * 
	 * @param hizkuntza
	 *            bilatuko den testuaren hizkuntza formatu laburrean, adibidez
	 *            "eu" edo "es" edo "en"
	 */
	public abstract void setHizkuntza(String hizkuntza);

	/**
	 * Pasatako stringa bilatuko da interneten
	 * 
	 * @param s
	 *            bilatuko den stringa
	 * @return bilaketaren emaitzak dituen bektorea
	 * @throws ParserException
	 */
	public abstract Vector<Link> bilatu(String s) throws ParserException;

}
