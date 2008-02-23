package app.analisiak.hizkuntzak;

import java.io.IOException;

import javax.xml.rpc.ServiceException;

import app.exceptions.MorfreusIsDownException;
import app.objektuak.Paragrafoa;

/**
 * Hizkuntzentzat interfazea.
 * 
 * @author Blizarazu
 * 
 */
public interface Hizkuntza {

	/**
	 * Emandako testuaren analisi morfologikoa egingo da paragrafoak eta
	 * esaldiak banatuz eta hitzen lemak eta etiketak itzuliz.
	 * 
	 * @param text
	 *            analisi morfologikoa egin behar zaion stringa.
	 * @param hasPos
	 *            analizatzeko stringa testu osoan hasten den posizioa. Hitzen
	 *            eta esaldien posizioa hemendik aurrera kontatzen hasiko dira.
	 * @return stringaren hitzak, lemak eta etiketak esaldi eta paragrafotan
	 *         banatuta diruen paragrafoa motako objektu bat
	 * @throws MorfreusIsDownException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public abstract Paragrafoa analizatu(String text, int hasPos)
			throws MorfreusIsDownException, IOException, ServiceException;

	/**
	 * Klase honek analisi morfologikoa zein hizkuntzatan egiten duen itzultzen
	 * du. Hizkuntzaren izen osoa itzuliko du, adibidez "Euskara", "Gaztelera",
	 * "Ingelesa"...
	 * 
	 * @return klase honek analisi morfologikoa egiten duen hizkuntzaren izen
	 *         osoa, adibidez "Euskara", "Gaztelera", "Ingelesa"...
	 */
	public abstract String getHizkuntza();

	/**
	 * Klase honek analisi morfologikoa zein hizkuntzatan egiten duen itzultzen
	 * du. Hizkuntzaren laburdura itzuliko du, adibidez "eu", "es", "eu"...
	 * 
	 * @return klase honek analisi morfologikoa egiten duen hizkuntzaren
	 *         laburdura, adibidez "eu", "es", "eu"...
	 */
	public abstract String getLaburdura();
}
