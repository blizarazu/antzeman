/**
 * 
 */
package app.objektuak;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import org.htmlparser.util.ParserException;

import app.analisiak.hizkuntzak.Hizkuntza;
import app.exceptions.MorfreusIsDownException;
import app.exceptions.NotWebPageException;
import app.utils.TestuIrakurlea;

/**
 * Testuak eta beraien propietateak gordetzeko erabiliko da.
 * 
 * @author blizarazu
 * 
 */
public class Testua extends Vector<Paragrafoa> {

	private static final long serialVersionUID = 1L;

	/**
	 * Testu hau testu soila dela adierazten du
	 */
	public static final int TESTU_SOILA = 0;
	/**
	 * Testu hau web orri bat dela adierazten du
	 */
	public static final int WEB_ORRIA = 1;

	private String path;

	private String hizkLaburdura;

	private String testua;

	private boolean prozesatua;

	private int mota;

	private Hizkuntza hizkAnalyzer;
	private TestuIrakurlea tIrak;

	/**
	 * Testua hasieratzen du emandako parametroekin.
	 * 
	 * @param analyzer
	 *            analisia egiteko analizatzaile morfologikorako Hizkuntza
	 *            motako klasea
	 * @param path
	 *            fitxategiaren bidea
	 * @param prozesatu
	 *            true bada testua une honetan prozesatuko da, bestela orain ez
	 *            da prozesatuko baina beranduago prozesatu daiteke
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	public Testua(Hizkuntza analyzer, String path, boolean prozesatu)
			throws IOException, MorfreusIsDownException, ServiceException {
		super();
		this.path = path;
		this.setHizkuntza(analyzer);
		this.mota = Testua.TESTU_SOILA;
		tIrak = new TestuIrakurlea();
		this.testua = tIrak.irakurri(this.path);
		if (prozesatu) {
			this.testuaKargatu();
			this.prozesatua = true;
		} else {
			this.prozesatua = false;
		}
	}

	/**
	 * Testua hasieratzen du emandako parametroekin.
	 * 
	 * @param path
	 *            fitxtegiaren bidea
	 * @throws IOException
	 */
	public Testua(String path) throws IOException {
		super();
		this.path = path;
		this.mota = Testua.TESTU_SOILA;
		tIrak = new TestuIrakurlea();
		this.testua = tIrak.irakurri(this.path);
		this.prozesatua = false;
	}

	/**
	 * Testua hasieratzen du emandako parametroekin.
	 * 
	 * @param analyzer
	 *            analisia egiteko analizatzaile morfologikorako Hizkuntza
	 *            motako klasea
	 * @param path
	 *            fitxtegiaren bidea
	 * @throws IOException
	 */
	public Testua(Hizkuntza analyzer, String path) throws IOException {
		super();
		this.path = path;
		this.setHizkuntza(analyzer);
		this.mota = Testua.TESTU_SOILA;
		tIrak = new TestuIrakurlea();
		this.testua = tIrak.irakurri(this.path);
		this.prozesatua = false;
	}

	/**
	 * Testua hasieratzen du emandako parametroekin.
	 * 
	 * @param path
	 *            fitxtegiaren bidea
	 * @param hizkuntza
	 *            testuaren hizkuntza
	 * @param text
	 *            fitxategiaren edukia
	 */
	protected Testua(String path, String hizkuntza, String text) {
		this.path = path;
		this.hizkLaburdura = hizkuntza;
		this.testua = text;
		this.mota = Testua.TESTU_SOILA;
		this.prozesatua = false;
	}

	/**
	 * Testua hasieratzen du emandako parametroekin.
	 * 
	 * @param path
	 *            fitxtegiaren bidea
	 * @param hizkuntza
	 *            testuaren hizkuntza
	 * @throws IOException
	 */
	protected Testua(String path, String hizkuntza) throws IOException {
		this.path = path;
		this.hizkLaburdura = hizkuntza;
		tIrak = new TestuIrakurlea();
		this.testua = tIrak.irakurri(this.path);
		this.mota = Testua.TESTU_SOILA;
		this.prozesatua = false;
	}

	/**
	 * Testua hasieratzen du emandako parametroekin.
	 * 
	 * @param analyzer
	 *            analisia egiteko analizatzaile morfologikorako Hizkuntza
	 *            motako klasea
	 * @param path
	 *            fitxategiaren bidea
	 * @param mota
	 *            0 testua testu hutsa bada, 1 web horri bat bada
	 * @throws IOException
	 * @throws ParserException
	 * @throws NotWebPageException
	 */
	public Testua(Hizkuntza analyzer, String path, int mota)
			throws IOException, ParserException, NotWebPageException {
		super();
		this.path = path;
		this.setHizkuntza(analyzer);
		this.mota = mota;
		tIrak = new TestuIrakurlea();
		switch (this.mota) {
		case Testua.TESTU_SOILA:
			this.testua = tIrak.irakurri(this.path);
			break;
		case Testua.WEB_ORRIA:
			this.testua = tIrak.webIrakurri(this.path);
			break;
		default:
			this.testua = tIrak.irakurri(this.path);
			break;
		}

		this.prozesatua = false;
	}

	/**
	 * Testua hasieratzen du emandako parametroekin.
	 * 
	 * @param path
	 *            fitxategiaren bidea
	 * @param mota
	 *            0 testua testu hutsa bada, 1 web horri bat bada
	 * @throws IOException
	 * @throws ParserException
	 * @throws NotWebPageException
	 */
	public Testua(String path, int mota) throws IOException, ParserException,
			NotWebPageException {
		super();
		this.path = path;
		this.mota = mota;
		tIrak = new TestuIrakurlea();
		switch (this.mota) {
		case Testua.TESTU_SOILA:
			this.testua = tIrak.irakurri(this.path);
			break;
		case Testua.WEB_ORRIA:
			this.testua = tIrak.webIrakurri(this.path);
			break;
		default:
			this.testua = tIrak.irakurri(this.path);
			break;
		}

		this.prozesatua = false;
	}

	/**
	 * Testuaren analisi morfologikoa egingo du oraindik egin ez bada. Analisi
	 * morfologikoa lehendik egin bada ez du ezer egingo.
	 * 
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	public void prozesatu() throws IOException, MorfreusIsDownException,
			ServiceException {
		if (!this.prozesatua) {
			this.testuaKargatu();
			this.prozesatua = true;
		}
	}

	/**
	 * Testu hutsa itzuliko da. Fitxategia edo web orri baten edukia (HTML
	 * etiketarik gabe) itzuliko dira.
	 * 
	 * @return testua duen string bat
	 */
	public String getTestua() {
		return this.testua;
	}

	/**
	 * Testuaren analisi morfologikoa eginda dagoen edo ez itzuliko du.
	 * 
	 * @return true testuaren analisi morfologikoa eginga badago, bestela false
	 */
	public boolean isProzesatua() {
		return this.prozesatua;
	}

	/**
	 * Testu fitxategiaren bidea edo web orria bada honen helbidea itzuliko du.
	 * 
	 * @return fitxategiaren bidea edo web orriaren URLa
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Testu honen hizkuntzaren laburdura.
	 * 
	 * @return hizkuntzaren laburdura, adibidez eu, es, en...
	 */
	public String getHizkLaburdura() {
		if (this.hizkAnalyzer != null)
			return this.hizkAnalyzer.getLaburdura();
		else
			return hizkLaburdura;
	}

	/**
	 * Testu honen hizkuntzaren izena.
	 * 
	 * @return hizkuntzaren izena, adibidez Euskara, Gaztelera, Ingelesa...
	 */
	public String getHizkIzena() {
		if (this.hizkAnalyzer != null)
			return this.hizkAnalyzer.getHizkuntza();
		else
			return null;
	}

	/**
	 * Zein testu mota den itzuliko du.
	 * 
	 * @return 0 testu soila bada, 1 web orria bada.
	 */
	public int getMota() {
		return mota;
	}

	/**
	 * Testu honetarako hizkuntza bat ezartzen du analisi morfologikoa egin ahal
	 * izateko.
	 * 
	 * @param hizk
	 *            Hitzkuntza motako objektua testu honi dagokion
	 *            hizkuntzarentako analisi morfologikoarentzat
	 */
	public void setHizkuntza(Hizkuntza hizk) {
		if (this.hizkAnalyzer != hizk) {
			this.hizkAnalyzer = hizk;
			this.hizkLaburdura = this.hizkAnalyzer.getLaburdura();
			this.prozesatua = false;
		}
	}

	/**
	 * Testu honen Hitza motako objektu guztiak bektore baten
	 * 
	 * @return Hitzen bektore bat testu honen hitzekin
	 */
	public Vector<Hitza> getHitzak() {
		Vector<Hitza> vHitzak = new Vector<Hitza>();
		for (Paragrafoa p : this) {
			for (Esaldia e : p) {
				for (Hitza h : e)
					vHitzak.addElement(h);
			}
		}
		return vHitzak;
	}

	/**
	 * Testu honen Esaldia motako objektu guztiak bektore baten
	 * 
	 * @return Esaldien bektore bat testu honen esaldiekin
	 */
	public Vector<Esaldia> getEsaldiak() {
		Vector<Esaldia> vEsaldiak = new Vector<Esaldia>();
		for (Paragrafoa p : this) {
			for (Esaldia e : p)
				vEsaldiak.addElement(e);
		}
		return vEsaldiak;
	}

	// public Vector<String> getParagrafoakLema() {
	// Vector<String> v = new Vector<String>();
	// for (Paragrafoa p : this) {
	// v.addElement(p.getStringLema());
	// }
	// return v;
	// }

	/**
	 * Testu honen paragrafoen stringa bektore batetan banatuta
	 * 
	 * @return stringen bektore bat testuko paragrafoekin
	 */
	public Vector<String> getParagrafoakString() {
		Vector<String> v = new Vector<String>();
		for (Paragrafoa p : this) {
			v.addElement(this.testua.substring(p.getHasieraPos(), p
					.getBukaeraPos()));
		}
		return v;
	}

	// public Vector<String> getEsaldiakLema() {
	// Vector<String> v = new Vector<String>();
	// for (Paragrafoa p : this) {
	// for (Esaldia e : p)
	// v.addElement(e.getStringLema());
	// }
	// return v;
	// }

	/**
	 * Testu honen esaldien stringa bektore batetan banatuta
	 * 
	 * @return stringen bektore bat testuko esaldiekin
	 */
	public Vector<String> getEsaldiakString() {
		Vector<String> v = new Vector<String>();
		for (Paragrafoa p : this) {
			for (Esaldia e : p)
				v.addElement(this.testua.substring(e.getHasieraPos(), e
						.getBukaeraPos()));
		}
		return v;
	}

	/**
	 * Testuaren analisi morfologikoa egingo du.
	 * 
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	private void testuaKargatu() throws IOException, MorfreusIsDownException,
			ServiceException {
		StringReader sr = new StringReader(this.testua);
		BufferedReader br = new BufferedReader(sr);
		String strLine;

		int pos = 0;
		String str;
		while ((strLine = br.readLine()) != null) {
			str = strLine.trim();
			if (!str.isEmpty()) {
				// FreeLing-ek analizatzen bukatzeko '.' behar du.
				if (str.charAt(str.length() - 1) != '.')
					str += ".";
				// Arazoak eman ditzaketen karaktere batzuk kendu.
				// Txuriune batekin ordezkatzen dira amaieran testuaren posizioa
				// berdina izateko.
				// Testuetako " kendu behar dira, bestela itxita ez badaude ez
				// du analisia ondo egiten, esaldi hori saltatzen baitu.
				str = str.replace('"', ' ');
				this.addElement(this.hizkAnalyzer.analizatu(str, pos));
			}
			// strLine-en luzeera baino 1 gehiago izan behar da lerro bukaeraren
			// karakterea gehitu behar zaiolako
			pos += strLine.length() + 1;
		}
		br.close();
		sr.close();
	}

	/**
	 * Testuaren mota ezartzen du.
	 * 
	 * @param mota
	 *            0 testu soila bada, 1 web orria bada.
	 */
	public void setMota(int mota) {
		this.mota = mota;
	}

	/**
	 * Testuaren mota web orri bat dela ezarriko da.
	 */
	public void setWebOrria() {
		this.mota = WEB_ORRIA;
	}
}
