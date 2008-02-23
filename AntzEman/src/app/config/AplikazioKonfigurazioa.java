package app.config;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * Aplikazioaren konfigurazioa kudeatuko duen klasea.
 * 
 * @author Blizarazu
 * 
 */
public class AplikazioKonfigurazioa {

	/**
	 * Aplikazioaren konfigurazioaren fitxategia.
	 */
	public static final String APLIKAZIO_CONFIG_XML = "appconfig.xml";

	private Document doc;
	private Element root;

	/**
	 * AplikazioKonfigurazioa hasieratuko du.
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	public AplikazioKonfigurazioa() throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder(false);
		this.doc = builder.build(APLIKAZIO_CONFIG_XML);
		this.root = doc.getRootElement();
	}

	/**
	 * Aplikazioaren uneko hizkuntza itzultzen du.
	 * 
	 * @return aplikazioaren uneko hizkuntzaren laburdura, adibidez eu, es,
	 *         en...
	 */
	public String getLanguage() {
		return this.root.getChild("lang").getText();
	}

	/**
	 * Aplikazioaren uneko hizkuntza aldatzen du.
	 * 
	 * @param langBerria
	 *            aplikazioarentzat hizkuntza berria
	 */
	public void changeLanguage(String langBerria) {
		this.root.getChild("lang").setText(langBerria);
	}

	/**
	 * Konfigurazioan egindako aldaketak fitxategian gordeko ditu.
	 * 
	 * @throws IOException
	 */
	public void gordeKonfig() throws IOException {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		FileOutputStream fos = new FileOutputStream(APLIKAZIO_CONFIG_XML);
		outputter.output(doc, fos);
		fos.flush();
		fos.close();
	}
}
