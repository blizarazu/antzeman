package app.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Analisien konfigurazioa kudeatuko duen klasea.
 * 
 * @author Blizarazu
 * 
 */
public class AnalisiKonfigurazioa {

	/**
	 * Analisien konfigurazioaren fitxategia.
	 */
	public static final String ANALISI_CONFIG_XML = "anconfig.xml";

	private Document doc;
	private Element root;

	/**
	 * AnalisiKonfigurazioa hasieratuko du.
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	public AnalisiKonfigurazioa() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder(false);
		this.doc = builder.build(ANALISI_CONFIG_XML);
		this.root = doc.getRootElement();
	}

	/**
	 * Analisi konfigurazioan FreeLingentzat beste hizkuntza berri bat gordeko
	 * du. Hizkuntza honen FreeLingen datu fitxategien izena pasatako
	 * laburduraren berdina izan beharko da.
	 * 
	 * @param izena
	 *            hizkuntzaren izen osoa, adibidez Euskara, Gaztelera,
	 *            Ingelesa...
	 * @param labur
	 *            hizkuntzaren laburdura, adibidez eu, es, en. Hizkuntza honen
	 *            FreeLingen datu fitxategien izena honen berdina izan beharko
	 *            da.
	 */
	public void insertHizkuntza(String izena, String labur) {
		if (!this.isHizkuntza(izena)) {
			Element e = new Element("hizkuntza");
			e.setAttribute("id", labur);
			e.setText(izena);
			this.root.getChild("hizkuntzak").addContent(e);
		}
	}

	/**
	 * Pasatako hizkuntza konfigurazio fitxategian gordeta dagoen edo ez.
	 * 
	 * @param izena
	 *            konfigurazio fitxategian gordeta dagoen jakin nahi dugun
	 *            hizkuntzaren izen osoa, adibidez Euskara, Gaztelera,
	 *            Ingelesa...
	 * @return true hizkuntza konfigurazio fitxategian gordeta badago, bestela
	 *         false
	 */
	public boolean isHizkuntza(String izena) {
		Element hizkChild = this.root.getChild("hizkuntzak");
		if (hizkChild != null) {
			Iterator<?> iter = hizkChild.getChildren("hizkuntza").iterator();
			while (iter.hasNext()) {
				Element e = (Element) iter.next();
				if (e.getText().equals(izena))
					return true;
			}
		}
		return false;
	}

	/**
	 * Konfigurazioan egindako aldaketak fitxategian gordeko ditu.
	 * 
	 * @throws IOException
	 */
	public void gordeKonfig() throws IOException {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream fos = new FileOutputStream(ANALISI_CONFIG_XML);
		outputter.output(doc, fos);
		fos.flush();
		fos.close();
	}

	/**
	 * FreeLing instalatuta dagoen bidea itzuliko du.
	 * 
	 * @return FreeLing fitxategi sisteman non instalatuta dagoen.
	 */
	public String getFreelingDir() {
		return this.root.getChild("freeling").getAttributeValue("dir");
	}

	/**
	 * Morfeus zerbitzariaren URLa.
	 * 
	 * @return Morfeus zerbitzariaren URLa
	 */
	public String getMorfeusURL() {
		return this.root.getChild("morfeus").getAttributeValue("url");
	}

	/**
	 * Konfigurazio fitxategian dauden hizkuntza guztien izenak.
	 * 
	 * @return konfigurazio fitxategian dauden hizkuntza guztien izen osoak,
	 *         adibidez Euskara, Gaztelera, Ingelesa...
	 */
	public Vector<String> getHizkIzenak() {
		Element hizkChild = this.root.getChild("hizkuntzak");
		if (hizkChild != null) {
			List<Element> hizkuntzak = hizkChild.getChildren("hizkuntza");
			Vector<String> hizkIzenak = new Vector<String>();
			for (Element e : hizkuntzak) {
				hizkIzenak.add(e.getText());
			}
			return hizkIzenak;
		} else
			return null;
	}

	/**
	 * Hizkuntza baten izena emanda, konfigurazio fitxategiko hizkuntza horri
	 * dagokion elementu osoa itzultzen du.
	 * 
	 * @param izena
	 *            lortu nahi dugun hizkuntzaren izen osoa.
	 * @return hizkuntza horri dagokion konfigurazio fitxategiko elementu osoa.
	 */
	public Element getHizkuntza(String izena) {
		Element hizkChild = this.root.getChild("hizkuntzak");
		if (hizkChild != null) {
			Iterator<?> iter = hizkChild.getChildren("hizkuntza").iterator();
			while (iter.hasNext()) {
				Element e = (Element) iter.next();
				if (e.getText().equals(izena))
					return e;
			}
		}
		return null;
	}

	/**
	 * Hizkuntza bat emanda, hizkuntza horri dagokion laburdura. Hau da, Euskara
	 * pasatzen badiogu eu itzuliko du.
	 * 
	 * @param hizkIzena
	 *            hizkuntzaren izen osoa
	 * @return hizkuntza horri dagokion laburdura
	 */
	public String getHizkLabur(String hizkIzena) {
		Element e = this.getHizkuntza(hizkIzena);
		if (e != null)
			return e.getAttributeValue("id");
		else
			return null;
	}
}
