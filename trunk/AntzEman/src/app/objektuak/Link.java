package app.objektuak;

/**
 * Bilaketen emaitzentzat interfazea
 * 
 * @author Blizarazu
 * 
 */
public interface Link {

	/**
	 * Emaitzak eman duen web orriaren esteka itzultzen du.
	 * 
	 * @return emaitzak eman duen web orriaren esteka
	 */
	public abstract String getURL();

	/**
	 * Emaitzak eman duen web orriaren izenburua itzultzen du.
	 * 
	 * @return emaitzak eman duen web orriaren izenburua
	 */
	public abstract String getText();

	/**
	 * Emaitzaren estekaren puntuak kalkulatzen ditu.
	 * 
	 * @return emaitzaren estekaren puntuak
	 */
	public abstract int puntuakKalkulatu();

	/**
	 * Eandako esteka esteka honekin bateratuko da.
	 * 
	 * @param link
	 *            bateratzeko Link motako objektua
	 */
	public abstract void elkartu(Link link);

	/**
	 * Link objektu honen kopia bat egingo da.
	 * 
	 * @return Link objektu honen kopia
	 */
	public abstract Link kopiatu();

}