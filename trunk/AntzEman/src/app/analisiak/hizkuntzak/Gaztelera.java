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
 * Gaztelerazko analisi morfologikoa egiten duen klasea.
 * 
 * @author Blizarazu
 * 
 */
public class Gaztelera implements Hizkuntza {

	/**
	 * Klase honek analisi morfologikoa zein hizkuntzatan egiten duen. Kasu
	 * honetan Gaztelera.
	 */
	public static final String HIZKUNTZA = "Gaztelera";
	/**
	 * Klase honek analisi morfologikoa egiten duen hizkuntzaren laburdura. Kasu
	 * honetan es.
	 */
	public static final String LABURDURA = "es";

	private String freelingDir;

	private tokenizer tk;
	private splitter sp;
	private hmm_tagger tg;
	private maco mf;

	/**
	 * Gaztelera hasieratzen du emandako FreeLingen gaztelerazko datu
	 * fitxategiekin.
	 * 
	 * @param dir
	 *            FreeLingen gaztelerazko datu fitxategiak.
	 */
	public Gaztelera(String dir) {
		this.freelingDir = dir;
		// create options set for maco analyzer. Default values are OK, except
		// for data files.
		maco_options op = new maco_options("es");
		op.set_active_modules(true, true, true, true, true, true, true, true,
				true);
		op.set_data_files(freelingDir + "es/locucions.dat", freelingDir
				+ "es/quantities.dat", freelingDir + "es/sufixos.dat",
				freelingDir + "es/probabilitats.dat", freelingDir
						+ "es/maco.db", freelingDir + "es/np.dat", freelingDir
						+ "common/punct.dat");

		// Analizatzaileak sortu
		this.tk = new tokenizer(freelingDir + "es/tokenizer.dat");
		this.sp = new splitter(freelingDir + "es/splitter.dat");
		this.mf = new maco(op);
		this.tg = new hmm_tagger("es", freelingDir + "es/tagger.dat", true);
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
