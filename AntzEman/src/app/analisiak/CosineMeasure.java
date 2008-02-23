/**
 * 
 */
package app.analisiak;

import java.math.BigDecimal;
import java.util.Vector;
import java.util.regex.Pattern;

import app.objektuak.Hitza;

/**
 * Kosinuaren metrikaren metodoa erabiliz bi testu konparatuko ditu klase honek.
 * 
 * @author Blizarazu
 * 
 */
public class CosineMeasure {

	private Vector<LemaKontagailua> vKomuna;

	private Vector<Hitza> vHitzak1;

	private Vector<Hitza> vHitzak2;

	/**
	 * CosineMeasure hasieratzen du.
	 * 
	 * @param vHitz1
	 *            lehenengo testuaren hitzen bektorea.
	 * @param vHitz2
	 *            bigarren testuaren hitzen bektorea.
	 */
	public CosineMeasure(Vector<Hitza> vHitz1, Vector<Hitza> vHitz2) {
		this.vKomuna = new Vector<LemaKontagailua>();
		this.vHitzak1 = vHitz1;
		this.vHitzak2 = vHitz2;
	}

	/**
	 * Hasieratzerakoan emandako bi bektoreak konparatuko ditu kosinuare
	 * metrikaren metodoa erabiliz. Bukatzerakoan bi bektoreen hitzen arteko
	 * antzekotasun ehunekoa itzuliko du.
	 * 
	 * @return bi bektoreen arteko antzekotasun ehunekoa float baten
	 */
	public float konparatu() {
		this.vKomunaEraiki(this.hitzakProzesatu(this.vHitzak1), this
				.hitzakProzesatu(this.vHitzak2));

		for (Hitza h1 : vHitzak1)
			this.gehituKop(1, h1);
		for (Hitza h2 : vHitzak2)
			this.gehituKop(2, h2);

		int[] kop1 = this.getKopuruak(1);
		int[] kop2 = this.getKopuruak(2);

		double zatik = 0;
		for (int i = 0; i < vKomuna.size(); i++)
			zatik = zatik + (kop1[i] * kop2[i]);

		double zatit1 = 0;
		for (int i1 : kop1)
			zatit1 = zatit1 + Math.pow(i1, 2);
		zatit1 = Math.sqrt(zatit1);

		double zatit2 = 0;
		for (int i2 : kop2)
			zatit2 = zatit2 + Math.pow(i2, 2);
		zatit2 = Math.sqrt(zatit2);

		double zatit = zatit1 * zatit2;
		double emaitza = zatik / zatit;

		float f;
		try {
			BigDecimal bd = new BigDecimal(emaitza).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			f = bd.floatValue();
		} catch (NumberFormatException ex) {
			f = -1;
		}
		return f;
	}

	/**
	 * Emandako hitzen bektorearen hitz bakoitzaren kopurua itzuliko da
	 * 
	 * @param i
	 *            bektoreko hitzen kopurua lortu nahi den
	 * @return int array bat emandako bektore zenbakiaren hitz bakoitzaren
	 *         kopuruarekin
	 */
	private int[] getKopuruak(int i) {
		int[] kop = new int[vKomuna.size()];
		for (int j = 0; j < vKomuna.size(); j++)
			kop[j] = vKomuna.get(j).getKop(i);
		return kop;
	}

	/**
	 * Pasatako bektore zenbakian, pasatako hitzaren kopuruari bat gehitu zaio
	 * 
	 * @param i
	 *            inkrementatu nahi dugun hitza zein bektorekoa den
	 * @param h
	 *            zein hitz inkrementatuko den
	 */
	private void gehituKop(int i, Hitza h) {
		int pos = vKomuna.indexOf(new LemaKontagailua(h.getLema()));
		// hitzakProzesatu metodoarekin behar ez genituen hitzak
		// kendu ditugu eta horiek ez ditugu kontuan hartu behar.
		// Hitz horientzako pos -1 izango da.
		if (pos >= 0)
			vKomuna.get(pos).ink(i);
	}

	/**
	 * Emandako hitzen bektoretik konparaketa egiteko hitzak bakarrik aterako
	 * ditu eta beste bektore batetan itzuliko ditu
	 * 
	 * @param hitzak
	 *            balio duten hitzak zein bektoretik ateratzea nahi dugun
	 * @return bektore berri bat balio duten hitzak bakarrik duena
	 */
	private Vector<LemaKontagailua> hitzakProzesatu(Vector<Hitza> hitzak) {
		Vector<LemaKontagailua> lemak = new Vector<LemaKontagailua>();
		for (Hitza h : hitzak) {
			if (this.balioDu(h))
				lemak.add(new LemaKontagailua(h.getLema()));
		}
		return lemak;
	}

	/**
	 * Pasatako bi bektoreak bateratuko ditu eta vKomuna bektorean gordeko dira
	 * 
	 * @param vLema1
	 *            bateratzeko lehenengo bektorea
	 * @param vLema2
	 *            bateratzeko bigarren bektorea
	 */
	private void vKomunaEraiki(Vector<LemaKontagailua> vLema1,
			Vector<LemaKontagailua> vLema2) {
		for (LemaKontagailua l1 : vLema1) {
			if (!this.vKomuna.contains(l1))
				vKomuna.addElement(l1);
		}
		for (LemaKontagailua l2 : vLema2) {
			if (!this.vKomuna.contains(l2))
				vKomuna.addElement(l2);
		}
	}

	/**
	 * Hitza analisirako baliagarria den edo ez esango du. Hitza analisirako
	 * baliagarria da baldin eta mota hauetakoak EZ badira: determinatzailea
	 * izenordaina konjuntzioa interjekzioa preposizioa puntuazio ikurra
	 * 
	 * @param h
	 *            analisirako baliagarria den edo ez jakin nahi dugun hitza.
	 * @return hitza analisirako baliagarria bada true, bestela false
	 */
	private boolean balioDu(Hitza h) {
		return !Pattern.matches("D.*|P.*|C.*|I.*|S.*|U.*|F[a-z]+", h.getTag());
	}

	/**
	 * @author Blizarazu
	 * 
	 * Hitzen kontaketan laguntzeko klase lagungarria
	 */
	private class LemaKontagailua implements Comparable<LemaKontagailua> {
		private String lema;

		private int kop1;

		private int kop2;

		/**
		 * LemaKontagailua klasea hasieratuko du
		 * 
		 * @param lema
		 *            kontagailu honi dagokion hitzaren lema
		 */
		public LemaKontagailua(String lema) {
			this.lema = lema;
			this.kop1 = 0;
			this.kop2 = 0;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		public boolean equals(Object obj) {
			if (obj.getClass() == LemaKontagailua.class)
				return this.lema.equals(((LemaKontagailua) obj).getLema());
			else
				return false;
		}

		/**
		 * Kontagailu honi dagokion lema itzultzen du
		 * 
		 * @return kontagailu honi dagokion lema
		 */
		public String getLema() {
			return this.lema;
		}

		/**
		 * Pasatako kontagailuaren kopurua itzuliko da, 1 bada kop1 eta 2 bada
		 * kop2
		 * 
		 * @param i
		 *            zein kontagailu lortu nahi den
		 * @return i=1 bada kop1, i=2 bada kop2, bestela -1
		 */
		public int getKop(int i) {
			if (i == 1)
				return this.kop1;
			else if (i == 2)
				return this.kop2;
			else
				return -1;

		}

		/**
		 * Pasatako kontagailua inkrementatuko du. i=1 bada kop1-i bat gehituko
		 * zaio eta i=2 bada kop2-ri. Bestela ez du ezer egingo.
		 * 
		 * @param i
		 *            zein kontagailu inkrementatu nahi den
		 */
		public void ink(int i) {
			if (i == 1)
				this.kop1++;
			else if (i == 2)
				this.kop2++;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		public int compareTo(LemaKontagailua o) {
			return this.lema.compareTo(o.lema);
		}
	}
}
