/**
 * 
 */
package app.kudeaketa;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import org.htmlparser.util.ParserException;
import org.jdom.JDOMException;

import app.analisiak.CosineMeasure;
import app.analisiak.Kolmogorov;
import app.analisiak.hizkuntzak.DefaultHizkuntza;
import app.analisiak.hizkuntzak.Euskara;
import app.analisiak.hizkuntzak.Gaztelera;
import app.analisiak.hizkuntzak.Hizkuntza;
import app.analisiak.hizkuntzak.Ingelesa;
import app.config.AnalisiKonfigurazioa;
import app.exceptions.MorfreusIsDownException;
import app.exceptions.NotWebPageException;
import app.objektuak.AnalisiEmaitza;
import app.objektuak.Hitza;
import app.objektuak.TestuZati;
import app.objektuak.Testua;

/**
 * Analisiak kudeatuko dituen klasea
 * 
 * @author blizarazu
 * 
 */
public class AnalisiKud {

	/**
	 * Konparaketak paragrafoka egingo dira
	 */
	public static final int PARAGRAFOKA = 0;
	/**
	 * Konparaketak esaldika egingo dira
	 */
	public static final int ESALDIKA = 1;

	private Hashtable<String, Hizkuntza> hizkuntzak;

	private AnalisiKonfigurazioa konfig;

	private Vector<Testua> testuak;

	private int metodoa;

	private AnalisiEmaitza emaitzak;

	/**
	 * AnalisiKud hasieratzen du.
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	public AnalisiKud() throws JDOMException, IOException {
		this.testuak = new Vector<Testua>();
		this.testuak.setSize(2);
		this.hizkuntzak = new Hashtable<String, Hizkuntza>();
		this.konfig = new AnalisiKonfigurazioa();
		this.hizkuntzakKargatu();
	}

	/**
	 * Analisia egiteko hizkuntzak kargatzen ditu.
	 */
	private void hizkuntzakKargatu() {
		String freelingDir = this.konfig.getFreelingDir();
		String morfeusURL = this.konfig.getMorfeusURL();
		this.hizkuntzak.put(Euskara.LABURDURA, new Euskara(morfeusURL));
		this.hizkuntzak.put(Gaztelera.LABURDURA, new Gaztelera(freelingDir));
		this.hizkuntzak.put(Ingelesa.LABURDURA, new Ingelesa(freelingDir));
		this.xmlHizkuntzakKargatu();
	}

	/**
	 * Analisien konfigurazio fitxategian dauden hizkuntzak kargatzen ditu.
	 */
	private void xmlHizkuntzakKargatu() {
		String freelingDir = this.konfig.getFreelingDir();
		Vector<String> hizkuntzak = this.konfig.getHizkIzenak();
		Enumeration<String> e = this.hizkuntzak.keys();
		boolean badago;
		if (hizkuntzak != null) {
			for (String hizk : hizkuntzak) {
				badago = false;
				while (e.hasMoreElements()) {
					String s = e.nextElement();
					if (s.equals(this.konfig.getHizkLabur(hizk))) {
						badago = true;
						break;
					}
				}
				if (!badago) {
					String labur = this.konfig.getHizkLabur(hizk);
					try {
						this.hizkuntzak.put(labur, new DefaultHizkuntza(hizk,
								labur, freelingDir));
					} catch (Exception ex) {
						System.out.println("Hizkuntza ez da existitzen.");
					}
				}
			}
		}
	}

	/**
	 * Analisia egiteko metodoa ezartzen du.
	 * 
	 * @param metodoa
	 *            Analisia egiteko metodoa. 0 bada analisia paragrafoka egingo
	 *            da eta 1 bada esaldika.
	 */
	public void setMetodoa(int metodoa) {
		this.metodoa = metodoa;
	}

	/**
	 * Analisia egiteko eskuragarri dauden hizkuntzen izenak itzultzen ditu.
	 * 
	 * @return Analisia egiteko eskuragarri dauden hizkuntzen izenak stringen
	 *         lista baten
	 */
	public List<String> getHizkuntzak() {
		Enumeration<String> e = this.hizkuntzak.keys();
		Vector<String> l = new Vector<String>();
		while (e.hasMoreElements())
			l.addElement(this.hizkuntzak.get(e.nextElement()).getHizkuntza());
		return l;
	}

	/**
	 * Hizkuntza baten laburdura itzultzen du.
	 * 
	 * @param hizkIzena
	 *            hizkuntzaren izen osoa, adibidez Euskara, Gaztelera, Ingelesa,
	 *            ...
	 * @return hizkuntza horren laburdura, analisiak egiteko eskuragarri badago
	 */
	public String getLaburdura(String hizkIzena) {
		Enumeration<String> e = this.hizkuntzak.keys();
		while (e.hasMoreElements()) {
			Hizkuntza h = this.hizkuntzak.get(e.nextElement());
			if (h.getHizkuntza().equals(hizkIzena))
				return h.getLaburdura();
		}
		return null;
	}

	/**
	 * Analisia egiteko testua ezartzen du
	 * 
	 * @param testuZenb
	 *            0 bada analisi testu bezala ezarriko da eta 1 bada
	 *            konparatzeko testu bezala
	 * @param fitx
	 *            testuaren fitxategiaren bidea
	 * @param hizk
	 *            testuaren hizkuntza
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	public void setTestua(int testuZenb, String fitx, String hizk)
			throws IOException, MorfreusIsDownException, ServiceException {
		Testua t = new Testua(this.hizkuntzak.get(hizk), fitx, true);
		if (testuZenb < this.testuak.size())
			this.testuak.removeElementAt(testuZenb);
		this.testuak.insertElementAt(t, testuZenb);
	}

	/**
	 * Analisia egiteko testua ezartzen du
	 * 
	 * @param testuZenb
	 *            0 bada analisi testu bezala ezarriko da eta 1 bada
	 *            konparatzeko testu bezala
	 * @param fitx
	 *            testuaren fitxategiaren bidea
	 * @throws IOException
	 */
	public void setTestua(int testuZenb, String fitx) throws IOException {
		Testua t = new Testua(fitx);
		if (testuZenb < this.testuak.size())
			this.testuak.removeElementAt(testuZenb);
		this.testuak.insertElementAt(t, testuZenb);
	}

	/**
	 * Analisia egiteko testua ezartzen du
	 * 
	 * @param testuZenb
	 *            0 bada analisi testu bezala ezarriko da eta 1 bada
	 *            konparatzeko testu bezala
	 * @param fitx
	 *            testuaren fitxategiaren bidea
	 * @param mota
	 *            0 bada testua testu hutsa dela adieraziko du eta 1 bada web
	 *            orri bateko testua dela
	 * @throws IOException
	 * @throws ParserException
	 * @throws NotWebPageException
	 */
	public void setTestua(int testuZenb, String fitx, int mota)
			throws IOException, ParserException, NotWebPageException {
		Testua t = new Testua(fitx, mota);
		if (testuZenb < this.testuak.size())
			this.testuak.removeElementAt(testuZenb);
		this.testuak.insertElementAt(t, testuZenb);
	}

	/**
	 * Analisiak egiteko eskatutako testua itzultzen du
	 * 
	 * @param testuZenb
	 *            0 bada analizatzeko testua itzuliko du eta 1 bada konparatzeko
	 *            testua
	 * @return eskatutako testua
	 */
	public Testua getTestua(int testuZenb) {
		return this.testuak.elementAt(testuZenb);
	}

	/**
	 * Analisiaren emaitzak itzuliko ditu
	 * 
	 * @return analisiaren emaitzak
	 */
	public AnalisiEmaitza getEmaitzak() {
		return emaitzak;
	}

	/**
	 * Analisi zehatza egingo du ezarritako bi testuen artean eta ezarritako
	 * metodoa jarraituz.
	 * 
	 * @return analisi zehatz horrek sortutako emaitzak
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	public AnalisiEmaitza analisiZehatza() throws IOException,
			MorfreusIsDownException, ServiceException {
		return this.analisiZehatza(this.metodoa);
	}

	/**
	 * Analisi zehatza egingo du ezarritako bi testuen artean pasatako metodoa
	 * jarraituz.
	 * 
	 * @param metodoa
	 *            0 konparaketak paragrafoka egiteko eta 1 esaldika egiteko
	 * @return analisi zehatz horrek sortutako emaitzak
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	public AnalisiEmaitza analisiZehatza(int metodoa) throws IOException,
			MorfreusIsDownException, ServiceException {
		this.metodoa = metodoa;
		Testua t1 = this.getTestua(0);
		Testua t2 = this.getTestua(1);
		t1.prozesatu();
		t2.prozesatu();
		switch (this.metodoa) {
		case AnalisiKud.PARAGRAFOKA:
			this.emaitzak = new AnalisiEmaitza(t1, t2,
					AnalisiEmaitza.PARAGRAFOKA);
			this.cosineMeasure(t1, t2);
			break;
		case AnalisiKud.ESALDIKA:
			this.emaitzak = new AnalisiEmaitza(t1, t2, AnalisiEmaitza.ESALDIKA);
			this.cosineMeasure(t1.getEsaldiak(), t2.getEsaldiak());
			break;
		default:
			return null;
		}
		return this.emaitzak;
	}

	/**
	 * Bi testu kosinuaren metrikaren bitartez konparatuko dira
	 * 
	 * @param v1
	 *            analizatzeko testuaren testu zatiak, metodoaren arabera
	 *            paragrafoak edo esaldiak izango dira
	 * @param v2
	 *            konparatzeko testuaren testu zatiak, metodoaren arabera
	 *            paragrafoak edo esaldiak izango dira
	 */
	private void cosineMeasure(Vector<?> v1, Vector<?> v2) {
		CosineMeasure cm;
		Vector<Hitza> p1Hitzak = new Vector<Hitza>();
		Vector<Hitza> p2Hitzak = new Vector<Hitza>();

		int kont = 0;
		float lag = 0;
		float batug = 0;

		for (int i = 0; i < v1.size(); i++) {
			TestuZati tz1 = (TestuZati) v1.elementAt(i);
			p1Hitzak = tz1.getHitzak();
			for (int j = 0; j < v2.size(); j++) {
				TestuZati tz2 = (TestuZati) v2.elementAt(j);
				p2Hitzak = tz2.getHitzak();
				cm = new CosineMeasure(p1Hitzak, p2Hitzak);
				float zenb = cm.konparatu();
				if (zenb > -1) {
					this.emaitzak.addEmaitza(tz1, tz2, zenb);
					if (zenb > lag)
						lag = zenb;
				}
			}
			kont++;
			batug += lag;
			lag = 0;
		}
		if (kont > 0)
			this.emaitzak.setBatazbestekoa(batug / kont);
		else
			this.emaitzak.setBatazbestekoa(0);
	}

	/**
	 * Ezarrita dauden bi testuen arteko analisi azkarra egingo da Kolmogoroven
	 * teknika jarraituz.
	 * 
	 * @return bi testuen arteko antzekotasun ehunekoa
	 * @throws IOException
	 */
	public float analisiAzkarra() throws IOException {
		String t1 = this.getTestua(0).getTestua();
		String t2 = this.getTestua(1).getTestua();
		Kolmogorov k = new Kolmogorov(t1, t2);
		return k.konparatu();
	}

	/**
	 * Testu bati hizkuntza ezartzen dio.
	 * 
	 * @param i
	 *            0 bada analizatzeko testuari ezarriko zaio hizkuntza, 1 bada
	 *            konparatzeko testuari
	 * @param hizkLabur
	 *            testuari ezarri nahi diogun hizkuntzaren laburdura, adibidez
	 *            eu, es, en...
	 */
	public void setHizkuntza(int i, String hizkLabur) {
		this.getTestua(i).setHizkuntza(this.hizkuntzak.get(hizkLabur));
	}

	/**
	 * Hizkuntza baten laburdura emanda hizkuntza horren izena itzultzen du.
	 * 
	 * @param laburdura hitzkuntzaren laburdura
	 * @return laburdura horri dagokion hizkuntzaren izena
	 */
	public String getHizkuntza(String laburdura) {
		return this.hizkuntzak.get(laburdura).getHizkuntza();
	}
}
