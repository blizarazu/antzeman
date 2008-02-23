package app.objektuak;

import java.util.Vector;

/**
 * Paragrafoentzat eta esaldientzat interfazea
 * 
 * @author Blizarazu
 * 
 */
public interface TestuZati extends Comparable<TestuZati> {

	/**
	 * Testu zatiaren hasiera posizioa testuan itzultzen du.
	 * 
	 * @return Testu zatiaren hasiera posizioa testuan
	 */
	public abstract int getHasieraPos();

	/**
	 * Testu zatiaren bukaera posizioa testuan itzultzen du.
	 * 
	 * @return Testu zatiaren hasiera posizioa testuan
	 */
	public abstract int getBukaeraPos();

	/**
	 * Testu zatiak dituen hitzak bektore batetan itzultzen ditu.
	 * 
	 * @return Testu zatiak dituen hitzak bektore batetan
	 */
	public abstract Vector<Hitza> getHitzak();
}