package app.kudeaketa;

import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import org.htmlparser.util.ParserException;

import app.analisiak.bilaketaGuneak.BilaketaGunea;
import app.analisiak.bilaketaGuneak.GoogleBilaketa;
import app.exceptions.MorfreusIsDownException;
import app.objektuak.BilaketaEmaitza;
import app.objektuak.Link;
import app.objektuak.Testua;

/**
 * Bilaketak kudeatuko dituen klasea
 * 
 * @author Blizarazu
 * 
 */
public class BilaketaKud {

	/**
	 * Bilaketak paragrafoka egingo dira
	 */
	public static final int PARAGRAFOKA = 0;
	/**
	 * Bilaketak esaldika egingo dira
	 */
	public static final int ESALDIKA = 1;

	private Testua testua;

	private int metodoa;
	private String gunea;

	private BilaketaGunea bilatzailea;

	private Hashtable<String, BilaketaGunea> guneak;

	/**
	 * BilaketaKud hasieratzen du.
	 */
	public BilaketaKud() {
		this.guneak = new Hashtable<String, BilaketaGunea>();
		this.guneakKargatu();
	}

	/**
	 * Bilaketak egiteko guneak kargatzen ditu.
	 */
	private void guneakKargatu() {
		this.guneak.put(GoogleBilaketa.BILAKETA_GUNEA, new GoogleBilaketa());
		this.guneak.put("Wikipedia", new GoogleBilaketa("wikipedia.org"));
	}

	/**
	 * Bilaketak egiteko eskuragarri dauden guneen izenak itzultzen ditu.
	 * 
	 * @return Bilaketak egiteko eskuragarri dauden guneen izenak stringen lista
	 *         baten
	 */
	public List<String> getBilaketaGuneak() {
		return Collections.list(this.guneak.keys());
	}

	/**
	 * Bilaketa egiteko testua ezartzen du
	 * 
	 * @param t
	 *            interneten bilatuko den testua
	 */
	public void setTestua(Testua t) {
		this.testua = t;
	}

	/**
	 * Bilaketa egiteko metodoa ezartzen du.
	 * 
	 * @param metodoa
	 *            Bilaketa egiteko metodoa. 0 bada analisia paragrafoka egingo
	 *            da eta 1 bada esaldika.
	 */
	public void setMetodoa(int metodoa) {
		this.metodoa = metodoa;
	}

	/**
	 * Bilaketa egiteko gunea ezartzen du.
	 * 
	 * @param gunea
	 *            bilaketa egingo den gunearen izena
	 */
	public void setGunea(String gunea) {
		this.gunea = gunea;
		this.bilatzailea = this.guneak.get(this.gunea);
	}

	/**
	 * Ezarrita dagoen testua interneten bilatuko da antzeko testuak duten web
	 * guneak bilatzeko asmoz.
	 * 
	 * @return bilaketek itzulitako emaitzak
	 * @throws IOException
	 * @throws ParserException
	 * @throws MorfreusIsDownException
	 * @throws ServiceException
	 */
	public BilaketaEmaitza internetenBilatu() throws IOException,
			ParserException, MorfreusIsDownException, ServiceException {
		this.bilatzailea.setHizkuntza(this.testua.getHizkLaburdura());
		this.testua.prozesatu();
		Vector<String> v = new Vector<String>();
		switch (this.metodoa) {
		case AnalisiKud.PARAGRAFOKA:
			v = this.testua.getParagrafoakString();
			return bilatu(v);
		case AnalisiKud.ESALDIKA:
			v = this.testua.getEsaldiakString();
			return bilatu(v);
		default:
			return null;
		}
	}

	/**
	 * Emandako bektorean dauden stringak bilatuko dira interneten antzeko
	 * testuak duten web orriak bilatzeko asmoz.
	 * 
	 * @param v
	 *            bilatu behar diren stringak dituen bektorea
	 * @return bilaketek itzulitako emaitzak
	 * @throws ParserException
	 */
	private BilaketaEmaitza bilatu(Vector<String> v) throws ParserException {
		Vector<Link> vBilaketa;
		BilaketaEmaitza emaitza = new BilaketaEmaitza(this.testua, this.gunea,
				this.metodoa);
		Vector<Kontagailua> totala = new Vector<Kontagailua>();
		for (String s : v) {
			if (s.trim().length() > 0) {
				vBilaketa = this.bilatzailea.bilatu(s.trim());
				Vector<Kontagailua> le = new Vector<Kontagailua>();
				for (Link l : vBilaketa)
					// Kokalekuarengatik puntuak kalkulatzeko
					// bektorearen luzeera - link-aren posizioa egiten da.
					// Horrela
					// 10 emaitza eman dituen bilaketa bateko lehenengoak 10
					// puntu
					// edukiko ditu.
					le.addElement(new Kontagailua(l,
							(vBilaketa.size() - vBilaketa.indexOf(l))));
				Collections.sort(le);
				Vector<Link> vlag = new Vector<Link>();
				Kontagailua kont = null;
				for (Kontagailua k : le) {
					vlag.addElement(k.getLink());
					if (totala.size() > 0) {
						for (Kontagailua k2 : totala) {
							if (k2.getLink().getURL().equals(
									k.getLink().getURL())) {
								k2.gehituPuntuak(k.getPuntuak());
								k2.getLink().elkartu(k.getLink());
								kont = null;
								break;
							} else
								kont = k.kopiatu();
						}
						if (kont != null)
							totala.addElement(kont);
					} else
						totala.addElement(k.kopiatu());
				}
				emaitza.addEmaitza(vlag);
			}
		}
		Collections.sort(totala);
		Vector<Link> vlag = new Vector<Link>();
		for (Kontagailua k : totala) {
			vlag.addElement(k.getLink());
		}
		emaitza.setEmaitzaTotala(vlag);

		return emaitza;
	}

	/**
	 * Bilaketen emaitzek itzulitako gueen garrantzia erabakitzeko klase
	 * lagungarria
	 * 
	 * @author Blizarazu
	 * 
	 */
	private class Kontagailua implements Comparable<Kontagailua> {

		private Link link;
		private int puntuak;

		/**
		 * Kontagailua hasieratzen du emandako puntuekin.
		 * 
		 * @param link
		 *            kontagailu honi dagokion web orriaren esteka
		 * @param i
		 *            hasierako puntuak
		 */
		public Kontagailua(Link link, int i) {
			this.link = link;
			this.puntuak = this.link.puntuakKalkulatu() + i;
		}

		/**
		 * Kontagailua hasieratzen du.
		 * 
		 * @param link
		 *            kontagailu honi dagokion web orriaren esteka
		 */
		private Kontagailua(Link link) {
			this.link = link;
		}

		/**
		 * Kontagailuari puntuak ezartzen dizkio aurretik zeuden puntuak
		 * ezabatuz.
		 * 
		 * @param puntuak
		 *            kontagailu honentzat puntuazio berria
		 */
		private void setPuntuak(int puntuak) {
			this.puntuak = puntuak;
		}

		/**
		 * Kontagailuaren kopia bat itzultzen du.
		 * 
		 * @return kontagailuaren kopia bat
		 */
		public Kontagailua kopiatu() {
			Kontagailua k = new Kontagailua(this.link.kopiatu());
			k.setPuntuak(this.puntuak);
			return k;
		}

		/**
		 * Kontagailu honi dagokion web horriaren esteka itzultzen du.
		 * 
		 * @return Kontagailu honi dagokion web horriaren esteka
		 */
		public Link getLink() {
			return this.link;
		}

		/**
		 * Kontagailu honek momentu honetan dituen puntuak itzultzen ditu.
		 * 
		 * @return Kontagailu honek momentu honetan dituen puntuak
		 */
		public int getPuntuak() {
			return this.puntuak;
		}

		/**
		 * Kontagailu honen puntuei emandako puntuak gehituko zaizkio.
		 * 
		 * @param puntuak
		 *            gehitzea nahi dugun puntu kopurua
		 */
		public void gehituPuntuak(int puntuak) {
			this.puntuak += puntuak;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(Kontagailua o) {
			return o.getPuntuak() - this.puntuak;
		}
	}
}
