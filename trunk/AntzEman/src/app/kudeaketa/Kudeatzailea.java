package app.kudeaketa;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.htmlparser.util.ParserException;
import org.jdom.JDOMException;

import app.exceptions.MorfreusIsDownException;
import app.exceptions.NotWebPageException;
import app.objektuak.AnalisiEmaitza;
import app.objektuak.BilaketaEmaitza;
import app.objektuak.Testua;

/**
 * Aplikazioaren kudeatzaile nagusia
 * 
 * @author Blizarazu
 * 
 */
public class Kudeatzailea {

	private AnalisiKud aKud;
	private BilaketaKud bKud;

	/**
	 * Kudeatzailea hasieratzen du
	 * 
	 * @throws JDOMException
	 * @throws IOException
	 */
	public Kudeatzailea() throws JDOMException, IOException {
		this.aKud = new AnalisiKud();
		this.bKud = new BilaketaKud();
	}

	/**
	 * Analisia egiteko eskuragarri dauden hizkuntzen izenak itzultzen ditu.
	 * 
	 * @return Analisia egiteko eskuragarri dauden hizkuntzen izenak stringen
	 *         array baten
	 */
	public String[] getHizkuntzak() {
		List<String> hizkuntzak = this.aKud.getHizkuntzak();
		Collections.sort(hizkuntzak);
		String[] hizk = new String[hizkuntzak.size()];
		int i = 0;
		for (String s : hizkuntzak) {
			hizk[i] = s;
			i++;
		}
		return hizk;
	}

	/**
	 * Hizkuntza baten laburdura itzultzen du.
	 * 
	 * @param hizkuntza
	 *            hizkuntzaren izen osoa, adibidez Euskara, Gaztelera, Ingelesa,
	 *            ...
	 * @return hizkuntza horren laburdura, analisiak egiteko eskuragarri badago
	 */
	public String getLaburdura(String hizkuntza) {
		return this.aKud.getLaburdura(hizkuntza);
	}

	/**
	 * Bilaketak egiteko eskuragarri dauden guneen izenak itzultzen ditu.
	 * 
	 * @return Bilaketak egiteko eskuragarri dauden guneen izenak stringen array
	 *         baten
	 */
	public String[] getBilaketaGuneak() {
		List<String> bilaketaGuneak = this.bKud.getBilaketaGuneak();
		Collections.sort(bilaketaGuneak);
		String[] bLek = new String[bilaketaGuneak.size()];
		int i = 0;
		for (String s : bilaketaGuneak) {
			bLek[i] = s;
			i++;
		}
		return bLek;
	}

	/**
	 * Analisia egiteko testua kargatuko du. Kargatu behar den testua
	 * analizatzekoa bada bilaketa kudeatzailean ere kargatuko du.
	 * 
	 * @param testuZenb
	 *            0 bada analisi testu bezala ezarriko da eta 1 bada
	 *            konparatzeko testu bezala
	 * @param fitx
	 *            testuaren fitxategiaren bidea
	 * @throws IOException
	 */
	public void kargatuTestua(int testuZenb, String fitx) throws IOException {
		this.aKud.setTestua(testuZenb, fitx);
		if (testuZenb == 0)
			this.bKud.setTestua(this.getTestua(0));
	}

	/**
	 * Konparaketa egiteko web orria kargatuko du.
	 * 
	 * @param url
	 *            web orriaren helbidea
	 * @throws IOException
	 * @throws ParserException
	 * @throws NotWebPageException
	 */
	public void kargatuWebOrria(String url) throws IOException,
			ParserException, NotWebPageException {
		this.aKud.setTestua(1, url, Testua.WEB_ORRIA);
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
		return aKud.getTestua(testuZenb);
	}

	/**
	 * Ezarrita dauden bi testuen arteko analisi azkarra egingo da Kolmogoroven
	 * teknika jarraituz.
	 * 
	 * @return bi testuen arteko antzekotasun ehunekoa
	 * @throws IOException
	 */
	public float analisiAzkarra() throws IOException {
		return aKud.analisiAzkarra();
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
		return aKud.analisiZehatza();
	}

	/**
	 * Ezarrita dagoen testua interneten bilatuko da antzeko testuak duten web
	 * guneak bilatzeko asmoz.
	 * 
	 * @return bilaketek itzulitako emaitzak
	 * @throws ParserException
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	public BilaketaEmaitza internetenBilatu() throws ParserException,
			IOException, MorfreusIsDownException, ServiceException {
		return this.bKud.internetenBilatu();
	}

	/**
	 * Analisia esaldika egingo den edo ez ezartzen du. Esaldika egingo ez bada
	 * paragrafoka egingo da.
	 * 
	 * @param esaldika
	 *            true bada analisia esaldika egingo da, bestela paragrafoka
	 */
	public void setAnalisiAukerak(boolean esaldika) {
		if (esaldika)
			aKud.setMetodoa(AnalisiKud.ESALDIKA);
		else
			aKud.setMetodoa(AnalisiKud.PARAGRAFOKA);
	}

	/**
	 * Bilaketa esaldika egingo den edo ez ezartzen du eta bilaketa gunea
	 * ezartzen du. Esaldika egingo ez bada paragrafoka egingo da.
	 * 
	 * @param esaldika
	 *            esaldika true bada analisia esaldika egingo da, bestela
	 *            paragrafoka
	 * @param gunea
	 *            bilaketak egingo diren guneen izena
	 */
	public void setBilaketaAukerak(boolean esaldika, String gunea) {
		if (esaldika)
			bKud.setMetodoa(BilaketaKud.ESALDIKA);
		else
			bKud.setMetodoa(BilaketaKud.PARAGRAFOKA);
		bKud.setGunea(gunea);
	}

	/**
	 * Bilaketak egingo diren guneak
	 * 
	 * @param gunea
	 *            bilaketak egingo diren guneen izena
	 */
	public void setBilaketaLekua(String gunea) {
		this.bKud.setGunea(gunea);
	}

	/**
	 * Testu bati hizkuntza ezartzen dio.
	 * 
	 * @param i
	 *            0 bada analizatzeko testuari ezarriko zaio hizkuntza, 1 bada
	 *            konparatzeko testuari
	 * @param hizkuntza
	 *            testuari ezarri nahi diogun hizkuntzaren laburdura, adibidez
	 *            eu, es, en...
	 */
	public void setHizkuntza(int i, String hizkuntza) {
		this.aKud.setHizkuntza(i, hizkuntza);
	}

	/**
	 * Hizkuntza baten laburdura emanda hizkuntza horren izena itzultzen du.
	 * 
	 * @param laburdura hitzkuntzaren laburdura
	 * @return laburdura horri dagokion hizkuntzaren izena
	 */
	public String getHizkuntza(String laburdura) {
		return this.aKud.getHizkuntza(laburdura);
	}
}
