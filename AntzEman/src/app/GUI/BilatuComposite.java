package app.GUI;

import java.util.Vector;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import app.actions.Bilatu;
import app.actions.FitxAukera;
import app.kudeaketa.Kudeatzailea;
import app.objektuak.AnalisiEmaitza;
import app.objektuak.BilaketaEmaitza;
import app.objektuak.Esaldia;
import app.objektuak.GoogleLink;
import app.objektuak.Link;
import app.objektuak.Paragrafoa;
import app.objektuak.Testua;

import com.swtdesigner.SWTResourceManager;

/**
 * Testu bat interneten bilatzeko barne panela
 * 
 * @author Blizarazu
 * 
 */
public class BilatuComposite extends Composite implements BarnePanela {

	private StyledText styledText_2;
	private StyledText styledText;
	private List list_1;
	private List list;
	private Text text;
	private Group group_4;

	private CLabel besteakLabel;
	private CLabel label;
	private CLabel label_1;
	private CLabel label_2;
	private Button konparatuButton;
	private Button bilatuButton;

	private Kudeatzailea kud;
	private MainGUI jabea;

	private FitxAukera fitxAukera;
	private Bilatu bilatu;

	private BilaketaEmaitza emaitzak;

	private Vector<Link> unekoEmaitza;

	private String unekoURL;

	private StyleRange[] defaultStyleRange;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 * @param owner
	 */
	public BilatuComposite(Composite parent, int style, MainGUI owner) {
		super(parent, style);

		this.jabea = owner;
		this.kud = this.jabea.getKudeatzailea();
		this.fitxAukera = this.jabea.getFitxAukera();
		this.bilatu = this.jabea.getBilatu();

		Group group_3;

		CLabel fitxategiaLabel;

		Button button;

		SashForm sashForm_2;

		SashForm sashForm_1;

		CLabel orriaLabel;

		CLabel estekaLabel;

		CLabel bilaketaGuneaLabel;

		Group group;

		Group antzekoTestuakInternetenGroup;
		antzekoTestuakInternetenGroup = new Group(this, SWT.NONE);
		antzekoTestuakInternetenGroup
				.setText(Messages.antzekoTestuakInternetenBilatu);
		sashForm_1 = new SashForm(antzekoTestuakInternetenGroup, SWT.NONE);

		group_4 = new Group(sashForm_1, SWT.NONE);

		final ListViewer listViewer = new ListViewer(group_4, SWT.BORDER);
		list = listViewer.getList();
		listViewer.addSelectionChangedListener(new ErakutsiEmaitzak());

		final GroupLayout groupLayout_5 = new GroupLayout(group_4);
		groupLayout_5.setHorizontalGroup(groupLayout_5.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_5.createSequentialGroup().addContainerGap().add(
						list, GroupLayout.PREFERRED_SIZE, 78, Short.MAX_VALUE)
						.addContainerGap()));
		groupLayout_5.setVerticalGroup(groupLayout_5.createParallelGroup(
				GroupLayout.TRAILING).add(
				GroupLayout.LEADING,
				groupLayout_5.createSequentialGroup().addContainerGap().add(
						list, GroupLayout.PREFERRED_SIZE, 303, Short.MAX_VALUE)
						.addContainerGap()));
		group_4.setLayout(groupLayout_5);

		final Group analizatzekoTestuaGroup = new Group(sashForm_1, SWT.NONE);
		analizatzekoTestuaGroup.setText(Messages.analizatzekoTestua);
		group_3 = new Group(analizatzekoTestuaGroup, SWT.NONE);
		fitxategiaLabel = new CLabel(group_3, SWT.NONE);
		fitxategiaLabel.setText(Messages.fitxategia);
		fitxategiaLabel.pack();

		text = new Text(group_3, SWT.BORDER);
		text.setEnabled(false);
		text.setEditable(false);
		button = new Button(group_3, SWT.NONE);
		button.setData("testuZenb", "0");
		button.addSelectionListener(this.fitxAukera);
		button.setText("...");
		final GroupLayout groupLayout_2 = new GroupLayout(group_3);
		groupLayout_2.setHorizontalGroup(groupLayout_2.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_2.createSequentialGroup().addContainerGap().add(
						fitxategiaLabel, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(text, GroupLayout.PREFERRED_SIZE, 41,
								Short.MAX_VALUE).addPreferredGap(
								LayoutStyle.RELATED).add(button)
						.addContainerGap()));
		groupLayout_2.setVerticalGroup(groupLayout_2.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_2.createSequentialGroup().add(
						groupLayout_2.createParallelGroup(GroupLayout.LEADING)
								.add(
										groupLayout_2.createParallelGroup(
												GroupLayout.BASELINE).add(
												button).add(text,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.add(fitxategiaLabel,
										GroupLayout.PREFERRED_SIZE, 23,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		group_3.setLayout(groupLayout_2);

		styledText = new StyledText(analizatzekoTestuaGroup, SWT.WRAP
				| SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
		styledText.setEditable(false);
		defaultStyleRange = styledText.getStyleRanges();

		final GroupLayout groupLayout_1 = new GroupLayout(
				analizatzekoTestuaGroup);
		groupLayout_1.setHorizontalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_1.createSequentialGroup().addContainerGap().add(
						groupLayout_1.createParallelGroup(GroupLayout.LEADING)
								.add(GroupLayout.TRAILING, group_3,
										GroupLayout.PREFERRED_SIZE, 177,
										Short.MAX_VALUE).add(
										GroupLayout.TRAILING, styledText,
										GroupLayout.PREFERRED_SIZE, 177,
										Short.MAX_VALUE)).add(9, 9, 9)));
		groupLayout_1.setVerticalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.TRAILING).add(
				groupLayout_1.createSequentialGroup().add(group_3,
						GroupLayout.PREFERRED_SIZE, 44,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(styledText,
						GroupLayout.PREFERRED_SIZE, 263, Short.MAX_VALUE)
						.addContainerGap()));
		analizatzekoTestuaGroup.setLayout(groupLayout_1);

		final Group aurkitutakoWebOrriakGroup = new Group(sashForm_1, SWT.NONE);
		aurkitutakoWebOrriakGroup.setText(Messages.aurkitutakoWebOrriak);
		sashForm_2 = new SashForm(aurkitutakoWebOrriakGroup, SWT.VERTICAL);

		final Composite composite = new Composite(sashForm_2, SWT.NONE);

		final ListViewer listViewer_1 = new ListViewer(composite, SWT.V_SCROLL
				| SWT.BORDER);
		listViewer_1.addSelectionChangedListener(new ErakutsiLinkInfo());
		list_1 = listViewer_1.getList();
		final GroupLayout groupLayout_4 = new GroupLayout(composite);
		groupLayout_4.setHorizontalGroup(groupLayout_4.createParallelGroup(
				GroupLayout.LEADING).add(list_1, GroupLayout.PREFERRED_SIZE,
				63, Short.MAX_VALUE));
		groupLayout_4.setVerticalGroup(groupLayout_4.createParallelGroup(
				GroupLayout.LEADING).add(list_1, GroupLayout.PREFERRED_SIZE,
				74, Short.MAX_VALUE));
		composite.setLayout(groupLayout_4);

		final Group informazioaGroup = new Group(sashForm_2, SWT.NONE);
		informazioaGroup.setText(Messages.informazioa);
		konparatuButton = new Button(informazioaGroup, SWT.NONE);
		konparatuButton.addSelectionListener(new EmaitzarekinKonparatu());
		konparatuButton.setEnabled(false);
		konparatuButton.setText(Messages.orriHonekinKonparatu);
		orriaLabel = new CLabel(informazioaGroup, SWT.NONE);
		orriaLabel.setText(Messages.orria);
		orriaLabel.pack();
		estekaLabel = new CLabel(informazioaGroup, SWT.NONE);
		estekaLabel.setText(Messages.esteka);
		estekaLabel.pack();

		besteakLabel = new CLabel(informazioaGroup, SWT.NONE);

		label = new CLabel(informazioaGroup, SWT.NONE);

		label_1 = new CLabel(informazioaGroup, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(0, 0, 255));

		styledText_2 = new StyledText(informazioaGroup, SWT.V_SCROLL
				| SWT.READ_ONLY | SWT.H_SCROLL | SWT.BORDER);
		styledText_2.setVisible(false);
		styledText_2.setEditable(false);

		final GroupLayout groupLayout_7 = new GroupLayout(informazioaGroup);
		groupLayout_7
				.setHorizontalGroup(groupLayout_7
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								GroupLayout.TRAILING,
								groupLayout_7
										.createSequentialGroup()
										.add(
												groupLayout_7
														.createParallelGroup(
																GroupLayout.TRAILING)
														.add(
																GroupLayout.LEADING,
																groupLayout_7
																		.createSequentialGroup()
																		.addContainerGap()
																		.add(
																				groupLayout_7
																						.createParallelGroup(
																								GroupLayout.LEADING)
																						.add(
																								GroupLayout.TRAILING,
																								estekaLabel,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)
																						.add(
																								GroupLayout.TRAILING,
																								orriaLabel,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				groupLayout_7
																						.createParallelGroup(
																								GroupLayout.LEADING)
																						.add(
																								label,
																								GroupLayout.PREFERRED_SIZE,
																								155,
																								Short.MAX_VALUE)
																						.add(
																								label_1,
																								GroupLayout.PREFERRED_SIZE,
																								155,
																								Short.MAX_VALUE)))
														.add(
																groupLayout_7
																		.createSequentialGroup()
																		.addContainerGap()
																		.add(
																				besteakLabel,
																				GroupLayout.PREFERRED_SIZE,
																				202,
																				Short.MAX_VALUE)))
										.add(9, 9, 9))
						.add(
								GroupLayout.TRAILING,
								groupLayout_7.createSequentialGroup()
										.addContainerGap(89, Short.MAX_VALUE)
										.add(konparatuButton).addContainerGap())
						.add(
								groupLayout_7.createSequentialGroup()
										.addContainerGap().add(styledText_2,
												GroupLayout.PREFERRED_SIZE,
												202, Short.MAX_VALUE)
										.addContainerGap()));
		groupLayout_7
				.setVerticalGroup(groupLayout_7
						.createParallelGroup(GroupLayout.TRAILING)
						.add(
								GroupLayout.LEADING,
								groupLayout_7
										.createSequentialGroup()
										.addContainerGap()
										.add(
												groupLayout_7
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(
																groupLayout_7
																		.createSequentialGroup()
																		.add(
																				label,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				label_1,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.add(
																groupLayout_7
																		.createSequentialGroup()
																		.add(
																				orriaLabel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				estekaLabel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(LayoutStyle.RELATED)
										.add(besteakLabel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.RELATED)
										.add(styledText_2,
												GroupLayout.PREFERRED_SIZE, 40,
												Short.MAX_VALUE)
										.addPreferredGap(LayoutStyle.RELATED)
										.add(konparatuButton).addContainerGap()));
		informazioaGroup.setLayout(groupLayout_7);
		group = new Group(aurkitutakoWebOrriakGroup, SWT.NONE);
		bilaketaGuneaLabel = new CLabel(group, SWT.NONE);
		bilaketaGuneaLabel.setText(Messages.bilaketaGunea);
		bilaketaGuneaLabel.pack();
		final GroupLayout groupLayout = new GroupLayout(
				aurkitutakoWebOrriakGroup);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap().add(
						groupLayout.createParallelGroup(GroupLayout.TRAILING)
								.add(GroupLayout.LEADING, group,
										GroupLayout.PREFERRED_SIZE, 225,
										Short.MAX_VALUE).add(
										GroupLayout.LEADING, sashForm_2,
										GroupLayout.PREFERRED_SIZE, 225,
										Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout.createSequentialGroup().add(group,
						GroupLayout.PREFERRED_SIZE, 44,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(sashForm_2,
						GroupLayout.PREFERRED_SIZE, 263, Short.MAX_VALUE)
						.addContainerGap()));
		sashForm_2.setWeights(new int[] { 72, 180 });

		label_2 = new CLabel(group, SWT.NONE);
		final GroupLayout groupLayout_6 = new GroupLayout(group);
		groupLayout_6.setHorizontalGroup(groupLayout_6.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_6.createSequentialGroup().addContainerGap().add(
						bilaketaGuneaLabel, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED).add(label_2,
								GroupLayout.PREFERRED_SIZE, 119,
								Short.MAX_VALUE).addContainerGap()));
		groupLayout_6.setVerticalGroup(groupLayout_6.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_6.createSequentialGroup().add(
						groupLayout_6.createParallelGroup(GroupLayout.LEADING)
								.add(bilaketaGuneaLabel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										label_2, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		group.setLayout(groupLayout_6);
		aurkitutakoWebOrriakGroup.setLayout(groupLayout);

		bilatuButton = new Button(antzekoTestuakInternetenGroup, SWT.NONE);
		bilatuButton.setEnabled(false);
		bilatuButton.addSelectionListener(bilatu);
		bilatuButton.setText(Messages.bilatu);
		final GroupLayout groupLayout_8 = new GroupLayout(
				antzekoTestuakInternetenGroup);
		groupLayout_8.setHorizontalGroup(groupLayout_8.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				groupLayout_8.createSequentialGroup().addContainerGap().add(
						groupLayout_8.createParallelGroup(GroupLayout.TRAILING)
								.add(GroupLayout.LEADING, sashForm_1,
										GroupLayout.PREFERRED_SIZE, 439,
										Short.MAX_VALUE).add(bilatuButton))
						.addContainerGap()));
		groupLayout_8.setVerticalGroup(groupLayout_8.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				groupLayout_8.createSequentialGroup().addContainerGap().add(
						sashForm_1, GroupLayout.PREFERRED_SIZE, 392,
						Short.MAX_VALUE).addPreferredGap(LayoutStyle.RELATED)
						.add(bilatuButton).addContainerGap()));
		antzekoTestuakInternetenGroup.setLayout(groupLayout_8);
		sashForm_1.setWeights(new int[] { 99, 201, 249 });
		final GroupLayout groupLayout_3 = new GroupLayout(this);
		groupLayout_3.setHorizontalGroup(groupLayout_3.createParallelGroup(
				GroupLayout.TRAILING).add(
				GroupLayout.LEADING,
				groupLayout_3.createSequentialGroup().addContainerGap().add(
						antzekoTestuakInternetenGroup,
						GroupLayout.PREFERRED_SIZE, 607, Short.MAX_VALUE)
						.addContainerGap()));
		groupLayout_3.setVerticalGroup(groupLayout_3.createParallelGroup(
				GroupLayout.TRAILING).add(
				GroupLayout.LEADING,
				groupLayout_3.createSequentialGroup().addContainerGap().add(
						antzekoTestuakInternetenGroup,
						GroupLayout.PREFERRED_SIZE, 422, Short.MAX_VALUE)
						.addContainerGap()));
		setLayout(groupLayout_3);
		//
	}

	/**
	 * Web horriarekin konparatzeko botoiaren ekintza
	 * 
	 * @author Blizarazu
	 * 
	 */
	private final class EmaitzarekinKonparatu extends SelectionAdapter {
		public void widgetSelected(final SelectionEvent e) {
			jabea.webKonparatu(unekoURL);
		}
	}

	/**
	 * Testu zatien listaren ekintza
	 * 
	 * @author Blizarazu
	 * 
	 */
	private final class ErakutsiEmaitzak implements ISelectionChangedListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(final SelectionChangedEvent event) {
			garbituInfo();
			int index = list.getSelectionIndex();
			if (index == 0) {
				styledText.setStyleRanges(defaultStyleRange);
				unekoEmaitza = emaitzak.getEmaitzaTotala();
			} else {
				unekoEmaitza = emaitzak.getEmaitzaAt(index - 1);
				Testua t = emaitzak.getTestua();
				switch (emaitzak.getMetodoa()) {
				case BilaketaEmaitza.PARAGRAFOKA:
					Paragrafoa p = t.elementAt(index - 1);
					int has = p.getHasieraPos();
					int buka = p.getBukaeraPos();
					testuaMarkatu(has, buka);
					break;

				case BilaketaEmaitza.ESALDIKA:
					Esaldia e = emaitzak.getTestua().getEsaldiak().elementAt(
							index - 1);
					has = e.getHasieraPos();
					buka = e.getBukaeraPos();
					testuaMarkatu(has, buka);
					break;

				default:
					break;
				}
			}
			for (Link l : unekoEmaitza)
				list_1.add(l.getText());
		}

		/**
		 * Tesua pasatako hasiera posiziotik bukaera posizioraino kkolorez
		 * markatuko da
		 * 
		 * @param hasi
		 *            hasiera posizioa
		 * @param buka
		 *            bukaera posizioa
		 */
		public void testuaMarkatu(int hasi, int buka) {
			try {
				StyleRange styleRange1 = new StyleRange();
				styleRange1.start = hasi;
				styleRange1.length = buka - hasi;

				// Testuaren atzeko partea gorria jarri.
				styleRange1.background = SWTResourceManager.getColor(255, 0, 0);

				styledText.setStyleRanges(defaultStyleRange);
				styledText.setStyleRange(styleRange1);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Emaitzen listaren ekintza
	 * 
	 * @author Blizarazu
	 * 
	 */
	private final class ErakutsiLinkInfo implements ISelectionChangedListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(final SelectionChangedEvent event) {
			int index = list_1.getSelectionIndex();
			if (index >= 0) {
				Link l = unekoEmaitza.elementAt(index);
				label.setText(l.getText());
				label_1.setText(l.getURL());
				if (l.getURL().length() > 0) {
					konparatuButton.setEnabled(true);
					unekoURL = l.getURL();
				} else
					konparatuButton.setEnabled(false);
				if (l.getClass().equals(GoogleLink.class)) {
					GoogleLink gl = (GoogleLink) l;
					besteakLabel.setText(Messages.kointziditutakoHitzak);
					String str = "";
					for (String s : gl.getKointzidentziak())
						str += s + "\n";
					styledText_2.setText(str.trim());
					styledText_2.setVisible(true);
				}
			} else {
				konparatuButton.setEnabled(false);
				label.setText("");
				label_1.setText("");
				besteakLabel.setText("");
				styledText_2.setText("");
				unekoURL = "";
			}
		}
	}

	/**
	 * Lista eta testu gune guztien edukia ezabatuko da
	 */
	public void garbitu() {
		list.deselectAll();
		list.removeAll();
		styledText.setStyleRanges(defaultStyleRange);
		this.garbituInfo();
		unekoEmaitza = null;
	}

	/**
	 * Emaitzen infomazioa duten label eta testu gunea garbituko dira
	 */
	public void garbituInfo() {
		list_1.deselectAll();
		list_1.removeAll();
		konparatuButton.setEnabled(false);
		label.setText("");
		label_1.setText("");
		besteakLabel.setText("");
		styledText_2.setVisible(false);
		styledText_2.setText("");
		unekoURL = "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.widgets.Composite#checkSubclass()
	 */
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * Emaitzak motako objektua emanda, testu zatien lista kargatuko da emaitza
	 * horiekin.
	 * 
	 * @param emaitzak
	 *            bilaketa baten emaitzak.
	 */
	public void emaitzakKargatu(BilaketaEmaitza emaitzak) {
		this.emaitzak = emaitzak;
		String s = "";
		this.garbitu();
		list.add(Messages.testuOsoa);
		switch (this.emaitzak.getMetodoa()) {
		case AnalisiEmaitza.PARAGRAFOKA:
			s = Messages.paragrafoak;
			for (int i = 0; i < this.emaitzak.emaitzaKop(); i++) {
				int has = this.emaitzak.getTestua().elementAt(i)
						.getHasieraPos();
				int buka = this.emaitzak.getTestua().elementAt(i)
						.getBukaeraPos();
				String str = i + 1 + ": " + has + "-" + buka;
				list.add(str);
			}
			break;
		case AnalisiEmaitza.ESALDIKA:
			s = Messages.esaldiak;
			for (int i = 0; i < this.emaitzak.emaitzaKop(); i++) {
				int has = this.emaitzak.getTestua().getEsaldiak().elementAt(i)
						.getHasieraPos();
				int buka = this.emaitzak.getTestua().getEsaldiak().elementAt(i)
						.getBukaeraPos();
				String str = i + 1 + ": " + has + "-" + buka;
				list.add(str);
			}
			break;

		default:
			break;
		}
		group_4.setText(s);
		label_2.setText(this.emaitzak.getBilaketaGunea());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.GUI.BarnePanela#setTestua(int, java.lang.String)
	 */
	@Override
	public void setTestua(int testuZenb, String fitx) {
		if (fitx != null) {
			switch (testuZenb) {
			case 0:
				this.garbitu();
				this.text.setText(fitx);
				bilatuButton.setEnabled(true);
				this.styledText.setText(this.kud.getTestua(testuZenb)
						.getTestua());
				break;
			default:
				break;
			}
		}
	}
}
