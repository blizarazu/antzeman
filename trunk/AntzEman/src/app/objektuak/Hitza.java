/**
 * 
 */
package app.objektuak;

/**
 * Testuaren hitzak gordetzeko egitura da. Hitzaren lema, etiketak eta hitza
 * bera gordetzen dira eta baita testuan duen hasiera eta bukaerako posizioak.
 * 
 * @author blizarazu
 * 
 */
public class Hitza {

	private String hitza;
	private String lema;
	private String tag;
	private int hasi;
	private int buka;

	/**
	 * Hitza hasieratzen du.
	 * 
	 * @param hitza
	 *            hitza bera
	 * @param lema
	 *            hitzaren lema
	 * @param tag
	 *            hitzaren PAROLE etiketa
	 * @param hasPos
	 *            hitzak testu osoan duen hasierako posizioa
	 * @param bukPos
	 *            hitzak testu osoan duen buaerako posizioa
	 */
	public Hitza(String hitza, String lema, String tag, int hasPos, int bukPos) {
		this.hitza = hitza;
		this.lema = lema;
		this.tag = tag;
		this.hasi = hasPos;
		this.buka = bukPos;
	}

	/**
	 * Hitza itzultzen du string batetan.
	 * 
	 * @return hitzaren stringa
	 */
	public String getHitza() {
		return this.hitza;
	}

	/**
	 * Lema itzultzen du string batetan.
	 * 
	 * @return hitzaren lema
	 */
	public String getLema() {
		return this.lema;
	}

	/**
	 * PAROLE etiketa itzultzen du string batetan.
	 * 
	 * @return hitzeren PAROLE etiketa
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * Hitzak testuan duen hasierako posizioa itzultzen du.
	 * 
	 * @return hitzak testuan duen hasierako posizioa
	 */
	public int getHasieraPos() {
		return this.hasi;
	}

	/**
	 * Hitzak testuan duen bukaerako posizioa itzultzen du.
	 * 
	 * @return hitzak testuan duen bukaerako posizioa
	 */
	public int getBukaeraPos() {
		return this.buka;
	}

	/**
	 * Hitz hau puntuazio ikurra bada true itzuliko du. PAROLE tag-a Fz hasten
	 * bada puntuazio ikurra esan nahiko du.
	 * 
	 * @return hitz hau puntuazio ikurra bada true itzuliko du bestela false
	 */
	public boolean isPuntIkurra() {
		return this.tag.startsWith("F");
	}
}
