/**
 * 
 */
package app.analisiak.bilaketaGuneak;

import java.util.Vector;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import app.objektuak.GoogleLink;
import app.objektuak.Link;

/**
 * Googlen bilaketak egingo dituen klasea.
 * 
 * @author blizarazu
 * 
 */
public class GoogleBilaketa implements BilaketaGunea {

	/**
	 * Bilaketa gunearen izena
	 */
	public static final String BILAKETA_GUNEA = "Google";

	private final String url = "http://www.google.com/search?aq=t&oq=";

	private final String urlHizk = "&hl=";

	private final String urlQuery = "&q=";

	/**
	 * Googleko bilaketa arrunta egiteko
	 */
	private final String motaSearch = "&btnG=Search";

	/**
	 * Googleko "Baietz lehenengoan!" bilaketa mota egiteko
	 */
	private final String motaLehenengoan = "&btnI=I'm Feeling Lucky";

	/**
	 * Bilaketa zein hizkuntzatan egingo den
	 */
	private String hizkuntza;

	/**
	 * Biltuko den karaktere katea
	 */
	private String strBilaketa;

	/**
	 * URLa eraikita
	 */
	private String azkenURL;

	/**
	 * Googleri web gune konkretu batetan bilatzeko eskatuko zaio
	 */
	private String site;

	/**
	 * GoogleBilaketa hasieratuko da pasatako bilatzeko stringa eta testu horren
	 * hizkuntzarekin
	 * 
	 * @param str
	 *            googlen bilatuko den stringa
	 * @param hizk
	 *            bilatuko den testuaren hizkuntza formatu laburrean, adibidez
	 *            "eu" edo "es" edo "en"
	 */
	public GoogleBilaketa(String str, String hizk) {
		this.strBilaketa = str;
		this.hizkuntza = hizk;
	}

	/**
	 * GoogleBilaketa hasieratuko da
	 */
	public GoogleBilaketa() {
	}

	/**
	 * GoogleBilaketa hasieratuko da Googleri emandako gunean dauden orriak
	 * bakarrik bilatzeko eskatuz. Bilaketa beraz, bilatzeko stringean "site:"
	 * eta emandako gunea gehituz egingo da
	 * 
	 * @param site
	 *            googleri gune konkretu honetan bilatzeko eskatuko zaio,
	 *            adibidez "wikipedia.org"
	 */
	public GoogleBilaketa(String site) {
		this.site = site;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.analisiak.bilaketaGuneak.BilaketaGunea#bilatu(java.lang.String)
	 */
	public Vector<Link> bilatu(String str) throws ParserException {
		this.strBilaketa = str;
		return bilatu(false);
	}

	/**
	 * Pasatako stringa bilatuko da interneten. Bilaketa zehatza egiteko eskatu
	 * ezkero string hori kakotxen (") artean jarriko da, string konkretu hori
	 * aldaketarik egin gabe bilatu dezan.
	 * 
	 * @param zehatz
	 *            true bada bilatzeko stringa kokotxen (") artean jarriko da
	 *            string horrek bilatzerakoan aldaketarik jasan ez dezan
	 * @param str
	 *            Googlen bilatuko den stringa
	 * @return bilaketaren emaitzak dituen bektorea
	 * @throws ParserException
	 */
	public Vector<Link> bilatu(boolean zehatz, String str)
			throws ParserException {
		this.strBilaketa = str;
		return bilatu(zehatz);
	}

	/**
	 * Ezarrita zegoen stringa bilatuko da interneten. Bilaketa zehatza egiteko
	 * eskatu ezkero string hori kakotxen (") artean jarriko da, string konkretu
	 * hori aldaketarik egin gabe bilatu dezan.
	 * 
	 * @param zehatz
	 *            true bada bilatzeko stringa kokotxen (") artean jarriko da
	 *            string horrek bilatzerakoan aldaketarik jasan ez dezan
	 * @return bilaketaren emaitzak dituen bektorea
	 * @throws ParserException
	 */
	public Vector<Link> bilatu(boolean zehatz) throws ParserException {
		this.eraikiURL(zehatz, false);
		Parser parser = new Parser(azkenURL);
		NodeFilter filterDIV = new NodeClassFilter(Div.class);
		NodeFilter filterLink = new NodeClassFilter(LinkTag.class);
		NodeFilter filterTable = new NodeClassFilter(TableTag.class);
		NodeList nl = parser.extractAllNodesThatMatch(filterDIV);
		Vector<Link> vEmaitza = new Vector<Link>();
		for (int i = 0; i < nl.size(); i++) {
			Tag t = (Tag) nl.elementAt(i);
			String classValue = t.getAttribute("class");
			if ("g".equals(classValue)) {
				NodeList list = t.getChildren().extractAllNodesThatMatch(
						filterLink);
				NodeList listLabur = t.getChildren().extractAllNodesThatMatch(
						filterTable);
				for (int j = 0; j < list.size(); j++) {
					LinkTag lt = (LinkTag) list.elementAt(j);
					TableTag tt = (TableTag) listLabur.elementAt(j);
					String link = lt.getLink();
					if (link.indexOf("http://translate.google.com") == -1
							&& link.indexOf("http://news.google.com") == -1) {
						Vector<String> vkointz = this.getKointzidentziak(tt);
						vEmaitza.addElement(new GoogleLink(lt.getLinkText(),
								link, vkointz));
					}
				}
			}
		}
		return vEmaitza;
	}

	/**
	 * Bilatu den stringa eta emaitzako gunearen artean aurkitu diren
	 * kointziditutako hitzak. Hau emaitzaren gunearen deskribapenean beltzez
	 * dauden hitzak jasoz egingo da.
	 * 
	 * @param tt
	 *            emaitzaren deskribapena duen html zatia TableTag egitura
	 *            batetan
	 * @return aurkitu diren kointziditutako esaldiak dituen bektore bat
	 */
	private Vector<String> getKointzidentziak(TableTag tt) {
		SimpleNodeIterator sni = tt.elements();
		Vector<String> labur = new Vector<String>();
		while (sni.hasMoreNodes()) {
			Node n = sni.nextNode();
			NodeList cl = n.getFirstChild().getChildren();
			for (int p = 0; p < cl.size(); p++) {
				Node node = cl.elementAt(p);
				if (node.toHtml().equals("<b>")) {
					p++;
					if (!cl.elementAt(p).toPlainTextString().contains("..."))
						labur.addElement(cl.elementAt(p).toPlainTextString());
				} else if (node.toHtml().equals("</b>")) {
					p++;
					while (p < cl.size()
							&& cl.elementAt(p).toHtml().equals(" "))
						p++;
					if (p < cl.size()) {
						if (cl.elementAt(p).toHtml().equals("<b>")) {
							p++;
							if (!cl.elementAt(p).toPlainTextString().contains(
									"...")) {
								String last = labur.lastElement();
								labur.remove(labur.size() - 1);
								labur.addElement(last + " "
										+ cl.elementAt(p).toPlainTextString());
							}
						}
					}
				}
			}
		}
		return labur;
	}

	/**
	 * Bilatzeko stringa ezartzen du
	 * 
	 * @param str
	 *            interneten bilatzeko stringa
	 */
	public void setBilaketa(String str) {
		this.strBilaketa = str;
	}

	/**
	 * Bilatzeko stringa eta bere hizkuntza ezartzen ditu
	 * 
	 * @param str
	 *            interneten bilatzeko stringa
	 * @param hizk
	 *            interneten bilatzeko restuaren hizkuntza
	 */
	public void setBilaketa(String str, String hizk) {
		this.strBilaketa = str;
		this.hizkuntza = hizk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.analisiak.bilaketaGuneak.BilaketaGunea#setHizkuntza(java.lang.String)
	 */
	public void setHizkuntza(String hizk) {
		this.hizkuntza = hizk;
	}

	/**
	 * Internetera bidaliko den URLa eraikiko du bilaketa egiteko. Bilaketa
	 * zehatza egiteko eskatu ezkero string hori kakotxen (") artean jarriko da,
	 * string konkretu hori aldaketarik egin gabe bilatu dezan. Googlen "Baietz
	 * Lehenengoan!" aukera erabiltzeko bigarren parametroa true izan behar da.
	 * 
	 * @param zehatz
	 *            true bada bilatzeko stringa kokotxen (") artean jarriko da
	 *            string horrek bilatzerakoan aldaketarik jasan ez dezan
	 * @param baietzLehenengoan
	 *            Googlen "Baietz Lehenengoan!" aukera erabiliz egingo du
	 *            bilaketa, beraz bilakta normal baten lehenengo emaitza
	 *            itzultzea bezala izango da
	 */
	public void eraikiURL(boolean zehatz, boolean baietzLehenengoan) {
		this.azkenURL = this.url;
		this.azkenURL += this.urlHizk + this.hizkuntza;
		this.azkenURL += this.urlQuery + this.strTratatu(zehatz);
		if (baietzLehenengoan)
			this.azkenURL += this.motaLehenengoan;
		else
			this.azkenURL += this.motaSearch;
	}

	/**
	 * Bilatzeko stringa tratatuko da behar ez diren karaktereak kenduz eta
	 * bilaketa zehatza bada kakotxak (") jarriz.
	 * 
	 * @param zehatz
	 *            true bada bilatzeko stringa kokotxen (") artean jarriko da
	 *            string horrek bilatzerakoan aldaketarik jasan ez dezan
	 * @return bilatzeko stringa beharrezko aldaketak eginda
	 */
	public String strTratatu(boolean zehatz) {
		String str = strBilaketa.trim().replaceAll("\n", "");
		String site = this.site;
		if (zehatz)
			str = "\"" + str + "\"";
		if (this.site != null) {
			if (site.indexOf("wikipedia") != -1)
				site = this.hizkuntza + "." + site;
			str = "site:" + site + " " + str;
		}
		return str;
	}
}
