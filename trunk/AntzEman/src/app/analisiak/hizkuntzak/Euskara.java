package app.analisiak.hizkuntzak;

import java.io.IOException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import app.exceptions.MorfreusIsDownException;
import app.objektuak.Esaldia;
import app.objektuak.Hitza;
import app.objektuak.Paragrafoa;
import app.utils.MorfeusBezeroa;
import app.utils.MorfeusBezeroa.MorfEsaldi;
import app.utils.MorfeusBezeroa.Token;

/**
 * Euskarazko analisi morfologikoa egiten duen klasea.
 * 
 * @author Blizarazu
 * 
 */
public class Euskara implements Hizkuntza {

	/**
	 * Klase honek analisi morfologikoa zein hizkuntzatan egiten duen. Kasu
	 * honetan Euskara.
	 */
	public static final String HIZKUNTZA = "Euskara";
	/**
	 * Klase honek analisi morfologikoa egiten duen hizkuntzaren laburdura. Kasu
	 * honetan eu.
	 */
	public static final String LABURDURA = "eu";

	private MorfeusBezeroa morf;

	/**
	 * Euskara hasieratzen du emandako Morfeus zerbitzariaren URLarekin.
	 * 
	 * @param url
	 *            Morfeus zerbitzariaren URLa.
	 */
	public Euskara(String url) {
		morf = new MorfeusBezeroa(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.analisiak.hizkuntzak.Hizkuntza#analizatu(java.lang.String, int)
	 */
	@Override
	public Paragrafoa analizatu(String text, int hasPos)
			throws MorfreusIsDownException, IOException, ServiceException {
		Paragrafoa p = new Paragrafoa();
		Vector<MorfEsaldi> vMorf;
		vMorf = morf.analizatu(text);
		int azkenpos = hasPos;
		if (vMorf != null) {
			for (MorfEsaldi me : vMorf) {
				Esaldia e = new Esaldia();
				for (Token t : me) {
					String hitza = t.getHitza();
					String lema = t.getLema();
					String tag = bihurtuTag(t.getTags());
					int hasiera = text.indexOf(hitza, azkenpos);
					if (hasiera == -1)
						hasiera = azkenpos;
					int bukaera = hasiera + hitza.length();					
					azkenpos = bukaera;
					Hitza h = new Hitza(hitza, lema, tag, hasiera, bukaera);
					e.addElement(h);
				}
				p.addElement(e);
			}
		}
		return p;
	}

	/**
	 * Morfeus zerbitzariak itzulitako etiketak PAROLE modura pasako dira, baina
	 * Bakarrik PAROLE tag-aren lehenengo letra behar dugu hitza deskartatua
	 * izango den edo ez jakiteko, beraz lehenengo hizkia bakarrik gordeko da.
	 * 
	 * @param tags
	 *            Morfeus zerbitzariak hitz baterako itzulitako etiketak
	 * @return etiketa horiei dagokien PAROLE etiketetako lehenengo hizkia
	 */
	private String bihurtuTag(String[] tags) {
		// Bakarrik PAROLE tag-aren lehenengo letra behar dugu hitza deskartatua
		// izango den edo ez jakiteko, beraz hurbilpen bat egingo da.
		if (tags[0].equals("IZE"))
			return "N";
		else if (tags[0].equals("ADJ"))
			return "A";
		else if (tags[0].equals("ADI "))
			return "V";
		else if (tags[0].equals("ADB"))
			return "R";
		else if (tags[0].equals("DET"))
			return "D";
		else if (tags[0].equals("IOR"))
			return "P";
		else if (tags[0].equals("LOT"))
			return "C";
		else if (tags[0].equals("PRT"))
			return "C";
		else if (tags[0].equals("ITJ"))
			return "I";
		else if (tags[0].equals("BST"))
			return "C";
		else if (tags[0].equals("ADL"))
			return "V";
		else if (tags[0].equals("ADT"))
			return "V";
		else if (tags[0].equals("SIG"))
			return "N";
		else if (tags[0].equals("SNB"))
			return "Z";
		else if (tags[0].equals("LAB"))
			return "C";
		else if (tags[0].startsWith("PUNT_"))
			return "F";
		else if (tags[0].equals("BEREIZ"))
			return "F";
		else
			return "F";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.analisiak.hizkuntzak.Hizkuntza#getHizkuntza()
	 */
	@Override
	public String getHizkuntza() {
		return HIZKUNTZA;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.analisiak.hizkuntzak.Hizkuntza#getLaburdura()
	 */
	@Override
	public String getLaburdura() {
		return LABURDURA;
	}

}
