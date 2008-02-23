/**
 * 
 */
package app.objektuak;

import java.util.Vector;

/**
 * Klase hau testuaren paragrafoak gordetzeko egitura izango da.
 * 
 * @author blizarazu
 * 
 */
public class Paragrafoa extends Vector<Esaldia> implements TestuZati,
		Comparable<TestuZati> {

	private static final long serialVersionUID = 1L;

	/**
	 * Paragrafoa testuaren zein posiziotan hasten den adierazten du.
	 */
	private int hasi;
	/**
	 * Paragrafoa testuaren zein posiziotan bukatzen den adierazten du.
	 */
	private int buka;

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.TestuZati#addElement(detekti.objektuak.Esaldia)
	 */
	public void addElement(Esaldia esald) {
		super.addElement(esald);
		this.hasi = this.firstElement().getHasieraPos();
		this.buka = this.lastElement().getBukaeraPos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.TestuZati#getHasieraPos()
	 */
	public int getHasieraPos() {
		return this.hasi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.TestuZati#getBukaeraPos()
	 */
	public int getBukaeraPos() {
		return this.buka;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see detekti.objektuak.TestuZati#getHitzak()
	 */
	public Vector<Hitza> getHitzak() {
		Vector<Hitza> vHitzak = new Vector<Hitza>();
		for (Esaldia e : this) {
			for (Hitza h : e)
				vHitzak.addElement(h);
		}
		return vHitzak;
	}

	// public String getStringLema() {
	// String str = "";
	// for (Esaldia e : this)
	// str += e.getStringLema() + " ";
	// return str.trim();
	// }

	// public String getString() {
	// String str = "";
	// for (Esaldia e : this)
	// str += e.getString() + " ";
	// return str.trim();
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(TestuZati p) {
		return this.hasi - p.getHasieraPos();
	}
}
