/**
 * 
 */
package app.objektuak;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Analisi zehatzen emaitzentzat egitura.
 * 
 * @author blizarazu
 * 
 */
public class AnalisiEmaitza extends
		Hashtable<TestuZati, AnalisiEmaitza.Emaitza> {

	private static final long serialVersionUID = 1L;

	/**
	 * Konparaketak paragrafoka egin dira
	 */
	public static final int PARAGRAFOKA = 0;
	/**
	 * Konparaketak esaldika egin dira
	 */
	public static final int ESALDIKA = 1;

	private Vector<Testua> vTestuak;

	// Analisi mota
	// 0 -> Paragrafoka
	// 1 -> Esaldika
	private int metodoa;

	// Batazbesteko antzekotasuna
	private float batazbestekoa;

	/**
	 * AnalisiEmaitza hasieratuko da emandako testuekin eta motarekin.
	 * 
	 * @param t1
	 *            analisiaren analizatzeko testua
	 * @param t2
	 *            analisiaren konparatzeko testua
	 * @param mota
	 *            0 bada konparaketak paragrafoka egin direla esan nahiko du eta
	 *            1 bada esaldika
	 */
	public AnalisiEmaitza(Testua t1, Testua t2, int mota) {
		vTestuak = new Vector<Testua>();
		vTestuak.insertElementAt(t1, 0);
		vTestuak.addElement(t2);
		this.metodoa = mota;
	}

	/**
	 * AnalisiEmaitza emandako fitxategitik kagatuko da. Fitxategiak aurretik
	 * gordetako emaitza bat eduki beharko du.
	 * 
	 * @param fitx
	 *            analisi baten emaitza bat gordetan duen fitxategia
	 * @throws JDOMException
	 * @throws IOException
	 */
	public AnalisiEmaitza(String fitx) throws JDOMException, IOException {
		vTestuak = new Vector<Testua>();
		this.emaitzakKargatu(fitx);
	}

	/**
	 * Analisiak emandako bi testuen arteko antzekotasun batazbestekoa ezartzen
	 * du.
	 * 
	 * @param btzbst
	 *            analisiak emandako bi testuen arteko antzekotasun
	 *            batazbestekoa
	 */
	public void setBatazbestekoa(float btzbst) {
		this.batazbestekoa = btzbst;
	}

	/**
	 * Analisia egiteko erabili den testuetako bat itzultzen du.
	 * 
	 * @param pos
	 *            0 bada analizatzeko testua itzuliko da eta 1 bada konparatzeko
	 *            testua
	 * @return eskatutako testua
	 */
	public Testua getTestua(int pos) {
		return this.vTestuak.get(pos);
	}

	/**
	 * Analizatzeko testua itzuliko du.
	 * 
	 * @return analizatzeko testua
	 */
	public Testua getAnalizatzekoTestua() {
		return this.vTestuak.firstElement();
	}

	/**
	 * Analisiak emandako bi testuen arteko antzekotasun batazbestekoa itzuliko
	 * da.
	 * 
	 * @return Analisiak emandako bi testuen arteko antzekotasun batazbestekoa
	 */
	public float getBatazbestekoa() {
		return this.batazbestekoa;
	}

	/**
	 * Analisia egiteko erabili den metodoa itzuliko da.
	 * 
	 * @return analisia egiteko erabili den metodoa
	 */
	public int getMetodoa() {
		return metodoa;
	}

	/**
	 * Analisia egiteko erabili den metodoa ezarriko da.
	 * 
	 * @param analisia
	 *            egiteko erabili den metodoa
	 */
	public void setMetodoa(int metodoa) {
		this.metodoa = metodoa;
	}

	/**
	 * Analizatzeko testuaren testu zati batek, konparatzeko testuaren testu
	 * zati batekin duen antzekotasun ehunekoa ezartzen du.
	 * 
	 * @param key
	 *            analizatzeko testuaren testu zatia
	 * @param valueP
	 *            konparatzeko testuaren testu zatia
	 * @param value
	 *            bien arteko antzekotasun ehunekoa
	 */
	public void put(TestuZati key, TestuZati valueP, float value) {
		Emaitza e = new Emaitza();
		e.put(valueP, value);
		this.put(key, e);
	}

	/**
	 * Analizatzeko testuaren testu zati bati, konparatzeko testuaren testu zati
	 * batekin duen antzekotasun ehunekoa gehituko dio.
	 * 
	 * @param key
	 *            analizatzeko testuaren testu zatia
	 * @param valueP
	 *            konparatzeko testuaren testu zatia
	 * @param value
	 *            bien arteko antzekotasun ehunekoa
	 */
	public void addEmaitza(TestuZati key, TestuZati valueP, float value) {
		Emaitza e = this.get(key);
		if (e != null)
			e.put(valueP, value);
		else
			this.put(key, valueP, value);
	}

	/**
	 * Analizatzeko testuaren testu zati bati dagokion konparatzeko testuko
	 * testu zatiak itzultzen ditu.
	 * 
	 * @param tz
	 *            analizatzeko testuaren testu zatia
	 * @return konparatzeko testuko testu zatiak
	 */
	public Enumeration<TestuZati> getEmaitzak(TestuZati tz) {
		return this.get(tz).keys();
	}

	/**
	 * Analizatzeko testuaren testu zati batek, konparatzeko testu zati batekin
	 * duen antzekotasun ehunekoa itzultzen du.
	 * 
	 * @param tz1
	 *            analizatzeko testuaren testu zatia
	 * @param tz2
	 *            konparatzeko testuaren testu zatia
	 * @return bien arteko antzekotasun ehunekoa
	 */
	public float getEmaitzaValue(TestuZati tz1, TestuZati tz2) {
		return this.get(tz1).get(tz2);
	}

	/**
	 * Analizatzeko testuko testu zati bati, konparatzeko testuaren testu
	 * zatiekin duen antzekotasun ehuneko maxikoa.
	 * 
	 * @param tz
	 *            analizatzeko testuko testu zatia
	 * @return antzekotasun ehuneko maximoa
	 */
	public float getEhunekoMax(TestuZati tz) {
		float max = 0;
		for (Float f : this.get(tz).values()) {
			if (f.floatValue() > max)
				max = f.floatValue();
		}
		return max;
	}

	/**
	 * Analisien emaitzak xml fitxategi baten gorde.
	 * 
	 * @param fitx
	 *            xml fitxategiaren bidea
	 * @param testuOsoa
	 *            analizatzeko eta konparatzeko testu osoak gordeko diren edo
	 *            bakarrik beraien bideak gordeko diren
	 * @throws IOException
	 */
	public void gorde(String fitx, boolean testuOsoa) throws IOException {
		Element root = new Element("analisia");
		root.setAttribute("mota", "zehatza");
		root.setAttribute("metodoa", String.valueOf(this.metodoa));
		root.setAttribute("batazbest", String.valueOf(this.batazbestekoa));
		Document doc = new Document(root);

		int i = 0;
		for (Testua t : this.vTestuak) {
			Element anTestu = new Element("fitxategia");
			anTestu.setAttribute("bal", String.valueOf(i));
			Element fitxIzen = new Element("izena");
			fitxIzen.setAttribute("mota", String.valueOf(t.getMota()));
			fitxIzen.setText(t.getPath());
			anTestu.addContent(fitxIzen);
			Element fitxHizk = new Element("hizkuntza");
			fitxHizk.setText(t.getHizkLaburdura());
			anTestu.addContent(fitxHizk);
			Element fitxOsoa = new Element("testua");
			fitxOsoa.setAttribute("bal", String.valueOf(testuOsoa));
			if (testuOsoa)
				fitxOsoa.setText(t.getTestua());
			anTestu.addContent(fitxOsoa);
			root.addContent(anTestu);
			i++;
		}

		Element ema = new Element("emaitza");

		List<TestuZati> keys = Collections.list(this.keys());
		Collections.sort(keys);
		for (TestuZati tz1 : keys) {
			Element emaTZ1 = new Element("testuzati");
			emaTZ1.setAttribute("testua", "0");
			emaTZ1.setAttribute("hasi", String.valueOf(tz1.getHasieraPos()));
			emaTZ1.setAttribute("buka", String.valueOf(tz1.getBukaeraPos()));
			List<TestuZati> eKeys = Collections.list(this.getEmaitzak(tz1));
			Collections.sort(eKeys);
			for (TestuZati tz2 : eKeys) {
				String bal = String.valueOf(this.getEmaitzaValue(tz1, tz2));
				Element emaTZ2 = new Element("testuzati");
				emaTZ2.setAttribute("testua", "1");
				emaTZ2
						.setAttribute("hasi", String.valueOf(tz2
								.getHasieraPos()));
				emaTZ2
						.setAttribute("buka", String.valueOf(tz2
								.getBukaeraPos()));
				emaTZ2.setText(bal);
				emaTZ1.addContent(emaTZ2);
			}
			ema.addContent(emaTZ1);
		}

		root.addContent(ema);

		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream fos = new FileOutputStream(fitx);
		outputter.output(doc, fos);
		fos.flush();
		fos.close();
	}

	/**
	 * Analisi baten emaitzak gordeta dauden fitxategi batetik emaitzak kargatu.
	 * 
	 * @param fitx
	 *            analisiaren emaitzak gordeta dituen fitxategia
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void emaitzakKargatu(String fitx) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder(false);
		// file:/ jarri behar da protokoloa adierazteko,
		// bestela ez ditu txuriuneak duten fitxategien bideak hartzen.
		if (fitx.startsWith("/"))
			fitx = fitx.substring(1);
		Document doc = builder.build("file:/" + fitx);
		Element root = doc.getRootElement();
		this.metodoa = Integer
				.parseInt(root.getAttribute("metodoa").getValue());
		this.batazbestekoa = Float.parseFloat(root.getAttribute("batazbest")
				.getValue());

		// Testua motako objektuak sortu eta kargatzen dira
		List<Element> lFitx = root.getChildren("fitxategia");
		for (Element e : lFitx) {
			String izena = e.getChildText("izena");
			String hizk = e.getChildText("hizkuntza");
			Element testu = e.getChild("testua");
			Testua t;
			if (Boolean.parseBoolean(testu.getAttributeValue("bal")))
				t = new Testua(izena, hizk, testu.getText());
			else
				t = new Testua(izena, hizk);
			this.vTestuak.insertElementAt(t, Integer.parseInt(e
					.getAttributeValue("bal")));
		}

		// Emaitzak kargatzen dira
		Element ema = root.getChild("emaitza");
		List<Element> lTZ1 = ema.getChildren("testuzati");
		for (Element e : lTZ1) {
			if (Integer.parseInt(e.getAttributeValue("testua")) == 0) {
				EmaitzaTestuZati etz = new EmaitzaTestuZati(Integer.parseInt(e
						.getAttributeValue("hasi")), Integer.parseInt(e
						.getAttributeValue("buka")));
				List<Element> lTZ2 = e.getChildren("testuzati");
				for (Element e2 : lTZ2) {
					EmaitzaTestuZati etz2 = new EmaitzaTestuZati(Integer
							.parseInt(e2.getAttributeValue("hasi")), Integer
							.parseInt(e2.getAttributeValue("buka")));
					float balio = Float.parseFloat(e2.getText());
					this.addEmaitza(etz, etz2, balio);
				}
			}
		}
	}

	/**
	 * Emaitzentzat klase laguntzailea
	 * 
	 * @author Blizarazu
	 * 
	 */
	private class Emaitza extends Hashtable<TestuZati, Float> {

		private static final long serialVersionUID = 1L;

		/**
		 * Emaitza hasieratzen du
		 */
		public Emaitza() {
			super();
		}

		/**
		 * Konparatzeko testuaren testu zati bat gordetzen du dagokion
		 * atzekotasun ehunekoarekin.
		 * 
		 * @param key
		 *            konparatzeko testuaren testu zatia
		 * @param value
		 *            antzekotasun ehunekoa
		 */
		public void put(TestuZati key, float value) {
			super.put(key, new Float(value));
		}

		/**
		 * Emandako konparatzeko testuaren testu zatiari dagokion antzekotasun
		 * ehunekoa itzultzen du.
		 * 
		 * @param key
		 *            konparatzeko testuaren testu zatia
		 * @return antzekotasun ehunekoa
		 */
		public float get(TestuZati key) {
			return super.get(key).floatValue();
		}
	}
}
