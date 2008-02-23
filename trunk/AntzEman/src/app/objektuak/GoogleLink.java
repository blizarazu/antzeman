package app.objektuak;

import java.util.Vector;

/**
 * Googlen egindako bilaketen emaitzak gordetzeko egitura da.
 * 
 * @author Blizarazu
 * 
 */
public class GoogleLink implements Link {

	private String text;

	private String url;

	private Vector<String> kointzidentziak;

	/**
	 * GoogleLink hasieratzen du emandako parametroekin.
	 * 
	 * @param text
	 *            bilatu den gunearen izenburua
	 * @param url
	 *            bilatu den gunearen esteka
	 * @param kointz
	 *            bilaketan kointziditu duten hitzak
	 */
	public GoogleLink(String text, String url, Vector<String> kointz) {
		this.text = text;
		this.url = url;
		this.kointzidentziak = kointz;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.Link#getURL()
	 */
	public String getURL() {
		return this.url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.Link#getText()
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * @return
	 */
	public Vector<String> getKointzidentziak() {
		return this.kointzidentziak;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.objektuak.Link#puntuakKalkulatu()
	 */
	@Override
	public int puntuakKalkulatu() {
		String[] s;
		int hitzkop = 0;
		for (String str : this.kointzidentziak) {
			s = str.split(" ");
			hitzkop = hitzkop + s.length;
		}
		try {
			return (hitzkop / this.kointzidentziak.size());
		} catch (java.lang.ArithmeticException e) {
			return 0;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.objektuak.Link#elkartu(app.objektuak.Link)
	 */
	@Override
	public void elkartu(Link link) {
		if (link.getClass().equals(GoogleLink.class)) {
			GoogleLink gl = (GoogleLink) link;
			for (String str : gl.getKointzidentziak())
				this.kointzidentziak.addElement(str);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.objektuak.Link#kopiatu()
	 */
	@Override
	public Link kopiatu() {
		Vector<String> v = new Vector<String>();
		for (String s : this.kointzidentziak)
			v.addElement(s);
		return new GoogleLink(new String(this.text), new String(this.url), v);
	}
}
