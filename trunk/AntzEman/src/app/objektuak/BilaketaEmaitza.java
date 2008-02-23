package app.objektuak;

import java.util.Vector;

/**
 * Interneteko bilaketentzat emaitzentzat egitura.
 * 
 * @author Blizarazu
 * 
 */
public class BilaketaEmaitza {

	/**
	 * Bilaketa paragrafoka egin da
	 */
	public static final int PARAGRAFOKA = 0;
	/**
	 * Bilaketa esaldika egin da
	 */
	public static final int ESALDIKA = 1;

	private int metodoa;

	private String bilaketaGunea;

	private Testua testua;

	private Vector<Vector<Link>> emaitzak;

	private Vector<Link> emaitzaTotala;

	/**
	 * BilaketaEmaitza emaitza hasieratzen du emandako testua, bilaketa gunea
	 * eta bilatzeko metodoarekin.
	 * 
	 * @param testua
	 *            interneten bilatu den testua
	 * @param bilaketaGunea
	 *            bilaketa egin den gunearen izena
	 * @param metodoa
	 *            0 bada bilaketa paragrafoka egin dela esan nahiko du, 1 bada
	 *            esaldika.
	 */
	public BilaketaEmaitza(Testua testua, String bilaketaGunea, int metodoa) {
		this.emaitzak = new Vector<Vector<Link>>();
		this.testua = testua;
		this.bilaketaGunea = bilaketaGunea;
		this.metodoa = metodoa;
	}

	/**
	 * Bilaketak testu zati batentzako aurkitutako guneak gordetzen ditu.
	 * 
	 * @param emaitza
	 *            Link motako objektuez osatutako bektore bat aurkitutako
	 *            guneekin
	 */
	public void addEmaitza(Vector<Link> emaitza) {
		this.emaitzak.addElement(emaitza);
	}

	/**
	 * Testu osoarentzat aurkitutako guneak gordetzen ditu.
	 * 
	 * @param emaitza
	 *            Link motako objektuez osatutako bektore bat aurkitutako
	 *            guneekin
	 */
	public void setEmaitzaTotala(Vector<Link> emaitza) {
		this.emaitzaTotala = emaitza;
	}

	/**
	 * Testu osoarentzat aurkitutako guneak itzultzen ditu.
	 * 
	 * @return Link motako objektuez osatutako bektore bat testu osoarentzak
	 *         aurkitu diren guneekin
	 */
	public Vector<Link> getEmaitzaTotala() {
		return this.emaitzaTotala;
	}

	/**
	 * Testu zati batentzat aurkitutako guneak itzultzen ditu.
	 * 
	 * @param index
	 *            zein testu zatiko emaitzak lortuko diren
	 * @return Link motako objektuez osatutako bektore bat aurkitutako guneekin
	 */
	public Vector<Link> getEmaitzaAt(int index) {
		return this.emaitzak.get(index);
	}

	/**
	 * Bilaketa egiteko erabili den metodoa itzultzen du.
	 * 
	 * @return 0 bilaketa paragrafoka egin bada eta 1 esaldika egin bada
	 */
	public int getMetodoa() {
		return this.metodoa;
	}

	/**
	 * Bilaketak egin diren gunearen izena.
	 * 
	 * @return bilaketak egin diren gunearen izena, adibidez Google
	 */
	public String getBilaketaGunea() {
		return this.bilaketaGunea;
	}

	/**
	 * Bilaketak zein gunetan egin diren ezartzen du.
	 * 
	 * @param gunea
	 *            bilaketak egin diren gunearen izena
	 */
	public void setBilaketaGunea(String gunea) {
		this.bilaketaGunea = gunea;
	}

	/**
	 * Bilaketak egiteko erabili den metodoa ezartzen du.
	 * 
	 * @param metodoa
	 *            0 bilaketak paragrafoka egin badira eta 1 esaldika egin badira
	 */
	public void setMetodoa(int metodoa) {
		this.metodoa = metodoa;
	}

	/**
	 * Interneten bilatu den testua itzultzen du.
	 * 
	 * @return Testua motako objektu bat interneten bilatu den testuaren
	 *         informazioarekin
	 */
	public Testua getTestua() {
		return this.testua;
	}

	/**
	 * Zenbat testu zatirentzat lortu diren emaitzak.
	 * 
	 * @return zenbat testu zatirentzat lortu diren emaitzak
	 */
	public int emaitzaKop() {
		return this.emaitzak.size();
	}
}
