package app.GUI;

import java.io.IOException;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.htmlparser.util.ParserException;
import org.jdom.JDOMException;

import app.actions.AnalisiZehatza;
import app.actions.Bilatu;
import app.actions.FitxAukera;
import app.config.AplikazioKonfigurazioa;
import app.exceptions.NotWebPageException;
import app.kudeaketa.Kudeatzailea;
import app.objektuak.AnalisiEmaitza;
import app.objektuak.BilaketaEmaitza;

import com.swtdesigner.ResourceManager;
import com.swtdesigner.SWTResourceManager;

/**
 * AntzEmanen leiho nagusia
 * 
 * @author Blizarazu
 * 
 */
public class MainGUI extends ApplicationWindow {

	private Action ingelesaAction;
	private Action gazteleraAction;
	private Action euskaraAction;
	private Action internetenBilatuAction;
	private Action testuWebAction;
	private Action emaitzaIrekiAction;
	private Action biTestuAction;
	private Action emaitzakAztertuAction;

	private Kudeatzailea kud;

	private Composite comp;

	private BiTestuComposite biTestuComposite;
	private EmaitzakComposite emaitzakComposite;
	private TestuWebComposite testuWebComposite;
	private BilatuComposite bilatuComposite;

	private FitxAukera fitxAukera;
	private AnalisiZehatza analisiZehatza;
	private Bilatu bilatu;

	private AplikazioKonfigurazioa appConf;

	private String lang;

	/**
	 * Create the application window
	 */
	public MainGUI() {
		super(null);
		try {
			kud = new Kudeatzailea();
			appConf = new AplikazioKonfigurazioa();
			lang = appConf.getLanguage();
			hizkuntzaKargatu();
			fitxAukera = new FitxAukera(this);
			analisiZehatza = new AnalisiZehatza(this);
			bilatu = new Bilatu(this);
			createActions();
			addCoolBar(SWT.FLAT);
			addMenuBar();
			addStatusLine();
		} catch (JDOMException e) {
			e.printStackTrace();
			MessageDialog
					.openError(
							getShell(),
							Messages.errorea,
							Messages.ezinKonfFitxIrak);
			this.close();
		} catch (IOException e) {
			e.printStackTrace();
			MessageDialog.openError(getShell(), Messages.errorea,
					Messages.ezinKonfFitxIreki);
			this.close();
		}
	}

	/**
	 * Aplikazioaren hizkuntza kargatuko du.
	 */
	private void hizkuntzaKargatu() {
		if (this.lang.length() > 0)
			Messages.initializeMessages("app.resources.languages.lang_"
					+ this.lang, Messages.class);
	}

	/**
	 * Aplikazioaren kudeatzailea itzuliko du.
	 * 
	 * @return aplikazioaren kudeatzailea
	 */
	public Kudeatzailea getKudeatzailea() {
		return this.kud;
	}

	/**
	 * FitxAukera ekintza itzuliko du.
	 * 
	 * @return FitxAukera ekintza
	 */
	protected FitxAukera getFitxAukera() {
		return this.fitxAukera;
	}

	/**
	 * AnalisiZehatza ekintza itzuliko du.
	 * 
	 * @return AnalisiZehatza ekintza
	 */
	protected AnalisiZehatza getAnalisiZehatza() {
		return this.analisiZehatza;
	}

	/**
	 * Bilatu ekintza itzuliko du.
	 * 
	 * @return Bilatu ekintza
	 */
	protected Bilatu getBilatu() {
		return this.bilatu;
	}

	/**
	 * Create contents of the application window
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);
		comp = container;
		final StackLayout stackLayout = new StackLayout();
		container.setLayout(stackLayout);

		// BiTestuComposite biTestuComposite;
		biTestuComposite = new BiTestuComposite(comp, SWT.NONE, this);

		// EmaitzakComposite emaitzakComposite;
		emaitzakComposite = new EmaitzakComposite(comp, SWT.NONE, this);

		// TestuWebComposite testuWebComposite;
		testuWebComposite = new TestuWebComposite(comp, SWT.NONE, this);

		bilatuComposite = new BilatuComposite(comp, SWT.NONE, this);
		stackLayout.topControl = biTestuComposite;
		//
		return container;
	}

	/**
	 * Create the actions
	 */
	private void createActions() {
		emaitzakAztertuAction = new Action(Messages.emaitzakAztertu) {
			public void run() {
				((StackLayout) comp.getLayout()).topControl = emaitzakComposite;
				comp.layout();
			}
		};
		emaitzakAztertuAction.setToolTipText(Messages.azkenEmaitzaIkusi);
		emaitzakAztertuAction.setImageDescriptor(ResourceManager
				.getImageDescriptor(MainGUI.class,
						"/app/resources/images/emaitzak_46x46.png"));

		biTestuAction = new Action(Messages.biTestuKonparatu) {
			public void run() {
				((StackLayout) comp.getLayout()).topControl = biTestuComposite;
				comp.layout();
			}
		};
		biTestuAction.setToolTipText(Messages.biTestuKonparatu_toolTipText);
		biTestuAction.setImageDescriptor(ResourceManager.getImageDescriptor(
				MainGUI.class, "/app/resources/images/biTestu_46x46.png"));

		testuWebAction = new Action(Messages.testuWebKonparatu) {
			public void run() {
				((StackLayout) comp.getLayout()).topControl = testuWebComposite;
				comp.layout();
			}
		};
		testuWebAction.setToolTipText(Messages.testuWebKonparatu_toolTipText);
		testuWebAction.setImageDescriptor(ResourceManager.getImageDescriptor(
				MainGUI.class, "/app/resources/images/testuWeb_46x46.png"));

		internetenBilatuAction = new Action(Messages.internetenBilatu) {
			public void run() {
				((StackLayout) comp.getLayout()).topControl = bilatuComposite;
				comp.layout();
			}
		};
		internetenBilatuAction
				.setToolTipText(Messages.internetenBilatu_toolTipText);
		internetenBilatuAction.setImageDescriptor(ResourceManager
				.getImageDescriptor(MainGUI.class,
						"/app/resources/images/internetenBilatu_46x46.png"));

		emaitzaIrekiAction = new Action(Messages.emaitzaIreki) {
			public void run() {
				try {
					FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
					dialog.setFilterNames(new String[] { Messages.xmlFitx });
					dialog.setFilterExtensions(new String[] { "*.xml" });
					String fitx = dialog.open();
					if (fitx != null) {
						emaitzakComposite.emaitzaKargatu(new AnalisiEmaitza(
								fitx));
						((StackLayout) comp.getLayout()).topControl = emaitzakComposite;
						comp.layout();
					}
				} catch (JDOMException e) {
					e.printStackTrace();
					MessageDialog
							.openError(getShell(), Messages.errorea,
									Messages.ezinEmaFitxIrak);
				} catch (IOException e) {
					e.printStackTrace();
					MessageDialog
							.openError(getShell(), Messages.errorea,
									Messages.ezinEmaFitxIreki);
				}

			}
		};
		emaitzaIrekiAction.setToolTipText(Messages.emaitzaIreki_toolTipText);

		euskaraAction = new Action(Messages.euskara, IAction.AS_RADIO_BUTTON) {
			public void run() {
				if (this.isChecked()) {
					if (!appConf.getLanguage().equals("eu")) {
						appConf.changeLanguage("eu");
						MessageDialog
								.openInformation(getShell(),
										"Hizkuntza aldatuta",
										"Aldaketak eragina izateko aplikazioa berrabiarazi beharko duzu.");
					}
				}
			}
		};

		gazteleraAction = new Action(Messages.gaztelera,
				IAction.AS_RADIO_BUTTON) {
			public void run() {
				if (this.isChecked()) {
					if (!appConf.getLanguage().equals("es")) {
						appConf.changeLanguage("es");
						MessageDialog
								.openInformation(getShell(), "Idioma cambiado",
										"Para que los cambios tengan efecto tendrá que reiniciar la aplicación.");
					}
				}
			}
		};

		ingelesaAction = new Action(Messages.ingelesa, IAction.AS_RADIO_BUTTON) {
			public void run() {
				if (this.isChecked()) {
					if (!appConf.getLanguage().equals("en")) {
						appConf.changeLanguage("en");
						MessageDialog
								.openInformation(getShell(),
										"Language changed",
										"The applications needs to restart for the changes to take effect.");
					}
				}
			}
		};
		// Create the actions

		if (this.lang.equals("eu"))
			euskaraAction.setChecked(true);
		else if (this.lang.equals("es"))
			gazteleraAction.setChecked(true);
		else if (this.lang.equals("en"))
			ingelesaAction.setChecked(true);
	}

	/**
	 * Create the menu manager
	 * 
	 * @return the menu manager
	 */
	@Override
	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager("menu");

		final MenuManager menuManager_1 = new MenuManager(
				Messages.fitxategia_menu);
		menuManager.add(menuManager_1);

		menuManager_1.add(emaitzaIrekiAction);

		final MenuManager menuManager_2 = new MenuManager(Messages.ikusi_menu);
		menuManager.add(menuManager_2);

		menuManager_2.add(biTestuAction);

		menuManager_2.add(testuWebAction);

		menuManager_2.add(internetenBilatuAction);

		menuManager_2.add(emaitzakAztertuAction);

		final MenuManager menuManager_3 = new MenuManager(Messages.aukerak_menu);
		menuManager.add(menuManager_3);

		final MenuManager menuManager_4 = new MenuManager(
				Messages.hizkuntza_menu);
		menuManager_3.add(menuManager_4);

		menuManager_4.add(euskaraAction);

		menuManager_4.add(gazteleraAction);

		menuManager_4.add(ingelesaAction);
		return menuManager;
	}

	/**
	 * Create the coolbar manager
	 * 
	 * @return the coolbar manager
	 */
	@Override
	protected CoolBarManager createCoolBarManager(int style) {
		CoolBarManager coolBarManager = new CoolBarManager(style);

		final ToolBarManager toolBarManager = new ToolBarManager(SWT.FLAT);
		coolBarManager.add(toolBarManager);

		toolBarManager.add(biTestuAction);

		toolBarManager.add(testuWebAction);

		toolBarManager.add(internetenBilatuAction);

		final ToolBarManager toolBarManager_1 = new ToolBarManager(SWT.FLAT);
		coolBarManager.add(toolBarManager_1);

		toolBarManager_1.add(emaitzakAztertuAction);
		return coolBarManager;
	}

	/**
	 * Create the status line manager
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setMessage(null, "");
		fillStatusLine(statusLineManager);
		return statusLineManager;
	}

	/**
	 * @param statusLine
	 */
	protected void fillStatusLine(IStatusLineManager statusLine) {
	}

	/**
	 * Configure the shell
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("AntzEman");
		newShell.setImage(SWTResourceManager.getImage(MainGUI.class,
				"/app/resources/images/antzEman_32x32.png"));
		newShell.setMaximized(true);
	}

	/**
	 * Analisi zehatz baten emaitzak ezarriko ditu.
	 * 
	 * @param ema
	 *            analisi zehatz baten emaitzak
	 */
	public void setEmaitza(AnalisiEmaitza ema) {
		emaitzakComposite.emaitzaKargatu(ema);
		((StackLayout) comp.getLayout()).topControl = emaitzakComposite;
		comp.layout();
	}

	/**
	 * Bilaketa baten emaitzak ezarriko ditu.
	 * 
	 * @param emaitzak
	 *            bilaketa baten emaitzak
	 */
	public void setBilaketaEmaitza(BilaketaEmaitza emaitzak) {
		bilatuComposite.emaitzakKargatu(emaitzak);
		((StackLayout) comp.getLayout()).topControl = bilatuComposite;
		comp.layout();
	}

	/**
	 * Barne panelean dauden testu guneei edukia esleituko zaie fitxategi
	 * bidearekin.
	 * 
	 * @param testuZenb
	 *            kagatu beharreko testua analizatzeko edo konparatzeko testua
	 *            den jakiteko. 0 analizatzeko testua, 1 konparatzeko testua.
	 * @param fitx
	 *            fitxategiaren bidea.
	 */
	public void setTestua(int testuZenb, String fitx) {
		Control[] c = this.comp.getChildren();
		for (int i = 0; i < c.length; i++) {
			BarnePanela bp = (BarnePanela) c[i];
			bp.setTestua(testuZenb, fitx);
		}
	}

	/**
	 * Web orri bat kargatzen du nabigatzailean
	 * 
	 * @param url
	 *            kargatu nahi den web orriaren helbidea
	 */
	public void webKonparatu(String url) {
		this.testuWebComposite.kargatuURL(url);
		((StackLayout) comp.getLayout()).topControl = testuWebComposite;
		comp.layout();
	}

	/**
	 * Web orria kudeatzailean ezartzen du analisia egiteko prest utziz
	 * 
	 * @param url
	 *            kargatu nahi den web orriaren helbidea
	 */
	public void kargatuWebOrria(String url) {
		try {
			this.biTestuComposite.clearTestua(1);
			kud.kargatuWebOrria(url);
		} catch (ParserException e) {
			e.printStackTrace();

			MessageDialog
					.openError(getShell(), Messages.errorea,
							Messages.ezinOrriaKargatu);
		} catch (IOException e) {
			e.printStackTrace();

			MessageDialog.openError(getShell(), Messages.errorea,
					Messages.ezinFitxIrakurri);
		} catch (NotWebPageException e) {
			e.printStackTrace();
			MessageDialog
					.openError(
							getShell(),
							Messages.errorea,
							Messages.ezWebOrri1
									+ " " + e.getFileExtension() + " "
									+ Messages.ezWebOrri2);
		}
	}

	/**
	 * Aplikazioaren konfigurazioa gordeko da.
	 */
	public void saveConfig() {
		try {
			this.appConf.gordeKonfig();
		} catch (IOException e) {
			e.printStackTrace();
			MessageDialog.openError(getShell(), Messages.errorea,
					Messages.ezinKonfGorde);
		}
	}
}
