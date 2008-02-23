package app.analisiak.hizkuntzak;

import app.exceptions.NullArgumentException;
import app.objektuak.Esaldia;
import app.objektuak.Hitza;
import app.objektuak.Paragrafoa;
import freeling.ListSentence;
import freeling.ListWord;
import freeling.hmm_tagger;
import freeling.maco;
import freeling.maco_options;
import freeling.sentence;
import freeling.splitter;
import freeling.tokenizer;
import freeling.word;

/**
 * Ingelerazko analisi morfologikoa egiten duen klasea.
 * 
 * @author Blizarazu
 * 
 */
public class Ingelesa implements Hizkuntza {

	/**
	 * Klase honek analisi morfologikoa zein hizkuntzatan egiten duen. Kasu
	 * honetan Ingelesa.
	 */
	public static final String HIZKUNTZA = "Ingelesa";
	/**
	 * Klase honek analisi morfologikoa egiten duen hizkuntzaren laburdura. Kasu
	 * honetan en.
	 */
	public static final String LABURDURA = "en";

	private String freelingDir;

	private tokenizer tk;
	private splitter sp;
	private hmm_tagger tg;
	private maco mf;

	/**
	 * Ingelesa hasieratzen du emandako FreeLingen ingelesezko datu
	 * fitxategiekin.
	 * 
	 * @param dir
	 *            FreeLingen ingelesezko datu fitxategiak.
	 */
	public Ingelesa(String dir) {
		this.freelingDir = dir;

		// create options set for maco analyzer. Default values are OK, except
		// for data files.
		maco_options op = new maco_options("en");
		op.set_active_modules(true, true, true, true, true, true, true, true,
				true);
		op.set_data_files(freelingDir + "en/locucions.dat", freelingDir
				+ "en/quantities.dat", freelingDir + "en/sufixos.dat",
				freelingDir + "en/probabilitats.dat", freelingDir
						+ "en/maco.db", freelingDir + "en/np.dat", freelingDir
						+ "common/punct.dat");

		// Analizatzaileak sortu
		this.tk = new tokenizer(freelingDir + "en/tokenizer.dat");
		this.sp = new splitter(freelingDir + "en/splitter.dat");
		this.mf = new maco(op);
		this.tg = new hmm_tagger("en", freelingDir + "en/tagger.dat", true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.analisiak.hizkuntzak.Hizkuntza#analizatu(java.lang.String, int)
	 */
	@Override
	public Paragrafoa analizatu(String text, int hasPos) {
		if (text.isEmpty())
			throw new NullArgumentException();

		ListWord l = tk.tokenize(text); // tokenize
		ListSentence ls = sp.split(l, false); // esaldiak banatu
		mf.analyze(ls); // analisi morfologikoa
		tg.analyze(ls); // PoS tagging

		Paragrafoa p = new Paragrafoa();

		// analizatutako hitzak esaldien listatik atera.
		for (int i = 0; i < ls.size(); i++) {
			sentence s = ls.get(i);
			Esaldia e = new Esaldia();
			for (int j = 0; j < s.size(); j++) {
				word w = s.get(j);
				Hitza h = new Hitza(w.get_form(), w.get_lemma(),
						w.get_parole(), hasPos + w.get_span_start(), hasPos
								+ w.get_span_finish());
				e.addElement(h);
			}
			p.addElement(e);
		}
		return p;
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
