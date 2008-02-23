/**
 * 
 */
package app.analisiak;

import java.io.IOException;
import java.math.BigDecimal;

import app.utils.Trinkoketa;

/**
 * Kolmogoroven metodoa erabiliz bi testu konparatuko ditu klase honek.
 * 
 * @author blizarazu
 * 
 */
public class Kolmogorov {

	private String testu1;
	private String testu2;

	/**
	 * Kolmogorov klasea hasieratuko du konparatu nahi diren bi testuekin.
	 * 
	 * @param str1
	 *            kontaraketa egiteko lehenengo testua
	 * @param str2
	 *            kontaraketa egiteko bigarren testua
	 */
	public Kolmogorov(String str1, String str2) {
		this.testu1 = str1;
		this.testu2 = str2;
	}

	/**
	 * Kargatutako bi testuak konparatuko ditu Kolmogoroven metodoa erabiliz
	 * 
	 * @return bi testuen arteko antzekotasun ehunekoa
	 * @throws IOException
	 */
	public float konparatu() throws IOException {
		int tam1 = Trinkoketa.getTamainaTrinkotuta(this.testu1);
		int tam2 = Trinkoketa.getTamainaTrinkotuta(this.testu2);
		String str12 = this.testu1 + " " + this.testu2;
		String str21 = this.testu2 + " " + this.testu1;
		int tam12 = Trinkoketa.getTamainaTrinkotuta(str12);
		int tam21 = Trinkoketa.getTamainaTrinkotuta(str21);
		int dif1 = tam12 - tam2;
		int dif2 = tam21 - tam1;
		double zenb = Math.max(dif1, dif2);
		double izend = Math.max(tam1, tam2);
		double emaitza = zenb / izend;
		emaitza = 1 - emaitza;

		// Bi double zatitzean sortzen den dezimalen errorea konpontzeko.
		BigDecimal bd = new BigDecimal(emaitza).setScale(2,
				BigDecimal.ROUND_HALF_UP);

		return bd.floatValue();
	}
}
