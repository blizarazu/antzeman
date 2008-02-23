package app.GUI;

/**
 * MainGUIren StackLayout duen compositearen barnean egongo diren compositentzat
 * interfazea.
 * 
 * @author Blizarazu
 * 
 */
public interface BarnePanela {

	/**
	 * Barne panelean dauden testu guneei edukia esleituko zaie fitxategi
	 * bidearekin.
	 * 
	 * @param testuZenb
	 *            kagatu beharreko testua analizatzeko edo konparatzeko testua
	 *            den jakiteko. 0 analizatzeko testua, 1 konparatzeko testua.
	 * @param fitx
	 *            fitxategiaren bidea.
	 */
	void setTestua(int testuZenb, String fitx);

}
