/**
 * 
 */
package app.objektuak;

import java.util.Vector;

/**
 * Emaitzak fitxategi batetik kargatzerakoan objektu hau erabiliko da bertan
 * gordeta dauden paragrafo edo esaldiak memorira pasatzeko.
 * 
 * @author Blizarazu
 * 
 */
public class EmaitzaTestuZati implements TestuZati {

	private int hasieraPos;
	private int bukaeraPos;

	/**
	 * EmaitzaTestuZati hasieratzen du emandako hasiera eta bukaerako
	 * posizioekin.
	 * 
	 * @param hasPos
	 *            testu zati honen hasierako posizioa
	 * @param bukPos
	 *            testu zati honen bukaerako posizioa
	 */
	public EmaitzaTestuZati(int hasPos, int bukPos) {
		this.hasieraPos = hasPos;
		this.bukaeraPos = bukPos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.TestuZati#getBukaeraPos()
	 */
	@Override
	public int getBukaeraPos() {
		return this.bukaeraPos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.TestuZati#getHasieraPos()
	 */
	@Override
	public int getHasieraPos() {
		return this.hasieraPos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.TestuZati#getHitzak()
	 */
	@Override
	public Vector<Hitza> getHitzak() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TestuZati tz) {
		return this.hasieraPos - tz.getHasieraPos();
	}

}
