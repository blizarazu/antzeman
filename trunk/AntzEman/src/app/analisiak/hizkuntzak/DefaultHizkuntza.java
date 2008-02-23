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
 * Hizkuntza motako klase lehenetsi bat da. FreeLing erabiltzen duen edozein
 * hizkuntza analizatu daiteke klase honekin.
 * 
 * @author Blizarazu
 * 
 */
public class DefaultHizkuntza implements Hizkuntza {

	private String freelingDir;

	/**
	 * Klase honek analisi morfologikoa zein hizkuntzatan egiten duen, adibidez
	 * "Euskara", "Gaztelera", "Ingelesa"...
	 */
	public String hizkuntza;
	/**
	 * Klase honek analisi morfologikoa zein hizkuntzatan egiten duen, adibidez
	 * "eu", "es", "eu"...
	 */
	public String laburdura;

	private tokenizer tk;
	private splitter sp;
	private hmm_tagger tg;
	private maco mf;

	/**
	 * DefaultHizkuntza hasieratzen du emandako hizkuntzarako. Hizkuntzaren izen
	 * osoa, laburdura eta FreeLingentzako datuen fitxategi sisteman kokalekua
	 * eman behar zaizkio.
	 * 
	 * @param hizk
	 *            klase honek analisi morfologikoa egingo duen hizkuntzaren izen
	 *            osoa, adibidez "Euskara", "Gaztelera", "Ingelesa"...
	 * @param labur
	 *            klase honek analisi morfologikoa egingo duen hizkuntzaren
	 *            laburdura, adibidez "eu", "es", "eu"...
	 * @param dataPath
	 *            hizkuntza honetarako FreeLingen datu fitxategien bidea
	 */
	public DefaultHizkuntza(String hizk, String labur, String dataPath) {
		this.hizkuntza = hizk;
		this.laburdura = labur;
		this.freelingDir = dataPath;

		// create options set for maco analyzer. Default values are Ok, except
		// for data files.
		maco_options op = new maco_options(laburdura);
		op.set_active_modules(true, true, true, true, true, true, true, true,
				true);
		op.set_data_files(freelingDir + laburdura + "/locucions.dat",
				freelingDir + laburdura + "/quantities.dat", freelingDir
						+ laburdura + "/sufixos.dat", freelingDir + laburdura
						+ "/probabilitats.dat", freelingDir + laburdura
						+ "/maco.db", freelingDir + laburdura + "/np.dat",
				freelingDir + "common/punct.dat");

		// Analizatzaileak sortu
		this.tk = new tokenizer(freelingDir + laburdura + "/tokenizer.dat");
		this.sp = new splitter(freelingDir + laburdura + "/splitter.dat");
		this.mf = new maco(op);
		this.tg = new hmm_tagger(laburdura, freelingDir + laburdura
				+ "/tagger.dat", true);
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
		ListSentence ls = sp.split(l, false); // split sentences
		mf.analyze(ls); // morphological analysis
		tg.analyze(ls); // PoS tagging

		Paragrafoa p = new Paragrafoa();

		// get the analyzed words out of ls.
		for (int i = 0; i < ls.size(); i++) {
			sentence s = ls.get(i);
			Esaldia e = new Esaldia();
			for (int j = 0; j < s.size(); j++) {
				word w = s.get(j);
				System.out.println(w.get_form());
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
		return this.hizkuntza;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.analisiak.hizkuntzak.Hizkuntza#getLaburdura()
	 */
	@Override
	public String getLaburdura() {
		return this.laburdura;
	}

}
