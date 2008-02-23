/**
 * 
 */
package app.objektuak;

import java.util.Vector;

/**
 * Klase hau testuaren esaldiak gordetzeko egitura izango da.
 * 
 * @author blizarazu
 * 
 */
public class Esaldia extends Vector<Hitza> implements TestuZati,
		Comparable<TestuZati> {

	private static final long serialVersionUID = 1L;

	/**
	 * Esaldia testuaren zein posiziotan hasten den adierazten du.
	 */
	private int hasi;
	/**
	 * Esaldia testuaren zein posiziotan bukatzen den adierazten du.
	 */
	private int buka;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Vector#addElement(java.lang.Object)
	 */
	public void addElement(Hitza hitz) {
		super.addElement(hitz);
		this.hasi = this.firstElement().getHasieraPos();
		this.buka = this.lastElement().getBukaeraPos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.objektuak.TestuZati#getHasieraPos()
	 */
	public int getHasieraPos() {
		return this.hasi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.objektuak.TestuZati#getBukaeraPos()
	 */
	public int getBukaeraPos() {
		return this.buka;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.objektuak.TestuZati#getHitzak()
	 */
	public Vector<Hitza> getHitzak() {
		Vector<Hitza> vHitzak = new Vector<Hitza>();
		for (Hitza h : this)
			vHitzak.addElement(h);
		return vHitzak;
	}

	// public String getStringLema() {
	// String str = "";
	// for (Hitza h : this) {
	// if (!h.isPuntIkurra())
	// str += " ";
	// str += h.getLema() + " ";
	// }
	// return str.trim();
	// }

	// public String getString() {
	// String str = "";
	// for (Hitza h : this) {
	// if (!h.isPuntIkurra())
	// str += " ";
	// str += h.getHitza() + " ";
	// }
	// return str.trim();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TestuZati e) {
		return this.hasi - e.getHasieraPos();
	}
}
