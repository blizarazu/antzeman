package app.GUI;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import app.actions.AnalisiZehatza;
import app.actions.FitxAukera;
import app.kudeaketa.Kudeatzailea;
import app.objektuak.Testua;

/**
 * Bi testuren konparaketa egiteko barne panela
 * 
 * @author Blizarazu
 * 
 */
public class BiTestuComposite extends Composite implements BarnePanela {

	private Text text_1;
	private StyledText styledText_1;
	private StyledText styledText;
	private Text text;
	private CLabel emaitzaLabel;
	private Button analisiZehatzaButton;
	private Button analisiAzkarraButton;

	private Kudeatzailea kud;
	private MainGUI jabea;

	private FitxAukera fitxAukera;
	private AnalisiZehatza analisiZehatza;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 * @param owner
	 */
	public BiTestuComposite(Composite parent, int style, MainGUI owner) {
		super(parent, style);

		this.jabea = owner;
		this.kud = this.jabea.getKudeatzailea();
		this.fitxAukera = this.jabea.getFitxAukera();
		this.analisiZehatza = this.jabea.getAnalisiZehatza();

		SashForm sashForm;

		Group group;

		CLabel fitxategiaLabel;

		Button button;

		Group group_1;

		CLabel fitxategiaLabel_1;

		Button button_1;

		Group group_2;

		Group biTestuKonparatuGroup;
		biTestuKonparatuGroup = new Group(this, SWT.NONE);
		biTestuKonparatuGroup.setText(Messages.biTestuKonparatu);
		group_2 = new Group(biTestuKonparatuGroup, SWT.NONE);

		emaitzaLabel = new CLabel(group_2, SWT.NONE);
		emaitzaLabel.setText(Messages.analisiAzkarrikEz);
		emaitzaLabel.pack();

		final GroupLayout groupLayout_5 = new GroupLayout(group_2);
		groupLayout_5.setHorizontalGroup(groupLayout_5.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_5.createSequentialGroup().addContainerGap().add(
						emaitzaLabel, GroupLayout.PREFERRED_SIZE, 431,
						Short.MAX_VALUE).addContainerGap()));
		groupLayout_5.setVerticalGroup(groupLayout_5.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_5.createSequentialGroup().add(emaitzaLabel,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE).addContainerGap(
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		group_2.setLayout(groupLayout_5);
		sashForm = new SashForm(biTestuKonparatuGroup, SWT.NONE);

		final Group analizatzekoTestuaGroup = new Group(sashForm, SWT.NONE);
		analizatzekoTestuaGroup.setText(Messages.analizatzekoTestua);
		group = new Group(analizatzekoTestuaGroup, SWT.NONE);
		fitxategiaLabel = new CLabel(group, SWT.NONE);
		fitxategiaLabel.setText(Messages.fitxategia);
		fitxategiaLabel.pack();

		text = new Text(group, SWT.BORDER);
		text.setEditable(false);
		text.setEnabled(false);

		styledText = new StyledText(analizatzekoTestuaGroup, SWT.WRAP
				| SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
		styledText.setEditable(false);
		button = new Button(group, SWT.NONE);
		button.setData("testuZenb", "0");
		button.addSelectionListener(this.fitxAukera);
		button.setText("...");

		final GroupLayout groupLayout_2 = new GroupLayout(group);
		groupLayout_2.setHorizontalGroup(groupLayout_2.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_2.createSequentialGroup().addContainerGap().add(
						fitxategiaLabel, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(text, GroupLayout.PREFERRED_SIZE, 78,
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
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		group.setLayout(groupLayout_2);
		final GroupLayout groupLayout = new GroupLayout(analizatzekoTestuaGroup);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				GroupLayout.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap().add(
						groupLayout.createParallelGroup(GroupLayout.TRAILING)
								.add(GroupLayout.LEADING, styledText,
										GroupLayout.PREFERRED_SIZE, 198,
										Short.MAX_VALUE).add(
										GroupLayout.LEADING, group,
										GroupLayout.PREFERRED_SIZE, 198,
										Short.MAX_VALUE)).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout.createSequentialGroup().add(group,
						GroupLayout.PREFERRED_SIZE, 46,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(styledText,
						GroupLayout.PREFERRED_SIZE, 144, Short.MAX_VALUE)
						.addContainerGap()));
		analizatzekoTestuaGroup.setLayout(groupLayout);

		final Group konparatzekoTestuaGroup = new Group(sashForm, SWT.NONE);
		konparatzekoTestuaGroup.setText(Messages.konparatzekoTestua);

		styledText_1 = new StyledText(konparatzekoTestuaGroup, SWT.WRAP
				| SWT.V_SCROLL | SWT.BORDER);
		styledText_1.setEditable(false);
		group_1 = new Group(konparatzekoTestuaGroup, SWT.NONE);
		fitxategiaLabel_1 = new CLabel(group_1, SWT.NONE);
		fitxategiaLabel_1.setText(Messages.fitxategia);
		fitxategiaLabel_1.pack();

		text_1 = new Text(group_1, SWT.BORDER);
		text_1.setEditable(false);
		text_1.setEnabled(false);
		button_1 = new Button(group_1, SWT.NONE);
		button_1.setData("testuZenb", "1");
		button_1.addSelectionListener(this.fitxAukera);
		button_1.setText("...");
		final GroupLayout groupLayout_3 = new GroupLayout(group_1);
		groupLayout_3.setHorizontalGroup(groupLayout_3.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_3.createSequentialGroup().addContainerGap().add(
						fitxategiaLabel_1, GroupLayout.PREFERRED_SIZE,
						GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.RELATED)
						.add(text_1, GroupLayout.PREFERRED_SIZE, 77,
								Short.MAX_VALUE).addPreferredGap(
								LayoutStyle.RELATED).add(button_1)
						.addContainerGap()));
		groupLayout_3.setVerticalGroup(groupLayout_3.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_3.createSequentialGroup().add(
						groupLayout_3.createParallelGroup(GroupLayout.LEADING)
								.add(
										groupLayout_3.createParallelGroup(
												GroupLayout.BASELINE).add(
												button_1).add(text_1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.add(fitxategiaLabel_1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		group_1.setLayout(groupLayout_3);
		final GroupLayout groupLayout_1 = new GroupLayout(
				konparatzekoTestuaGroup);
		groupLayout_1.setHorizontalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.TRAILING).add(
				groupLayout_1.createSequentialGroup().addContainerGap().add(
						groupLayout_1.createParallelGroup(GroupLayout.TRAILING)
								.add(GroupLayout.LEADING, styledText_1,
										GroupLayout.PREFERRED_SIZE, 199,
										Short.MAX_VALUE).add(
										GroupLayout.LEADING, group_1,
										GroupLayout.PREFERRED_SIZE, 199,
										Short.MAX_VALUE)).addContainerGap()));
		groupLayout_1.setVerticalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_1.createSequentialGroup().add(group_1,
						GroupLayout.PREFERRED_SIZE, 46,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(styledText_1,
						GroupLayout.PREFERRED_SIZE, 144, Short.MAX_VALUE)
						.addContainerGap()));
		konparatzekoTestuaGroup.setLayout(groupLayout_1);

		analisiZehatzaButton = new Button(biTestuKonparatuGroup, SWT.NONE);
		analisiZehatzaButton.setEnabled(false);
		analisiZehatzaButton.addSelectionListener(analisiZehatza);
		analisiZehatzaButton.setText(Messages.analisiZehatza);
		analisiZehatzaButton.pack();

		analisiAzkarraButton = new Button(biTestuKonparatuGroup, SWT.NONE);
		analisiAzkarraButton.setEnabled(false);
		analisiAzkarraButton.addSelectionListener(new AnalisiAzkarra());
		analisiAzkarraButton.setText(Messages.analisiAzkarra);
		analisiAzkarraButton.pack();
		final GroupLayout groupLayout_6 = new GroupLayout(biTestuKonparatuGroup);
		groupLayout_6
				.setHorizontalGroup(groupLayout_6
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								groupLayout_6
										.createSequentialGroup()
										.add(
												groupLayout_6
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(
																GroupLayout.TRAILING,
																groupLayout_6
																		.createSequentialGroup()
																		.addContainerGap()
																		.add(
																				group_2,
																				GroupLayout.PREFERRED_SIZE,
																				532,
																				Short.MAX_VALUE))
														.add(
																GroupLayout.TRAILING,
																groupLayout_6
																		.createSequentialGroup()
																		.addContainerGap(
																				370,
																				Short.MAX_VALUE)
																		.add(
																				analisiZehatzaButton,
																				GroupLayout.PREFERRED_SIZE,
																				84,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				analisiAzkarraButton,
																				GroupLayout.PREFERRED_SIZE,
																				82,
																				GroupLayout.PREFERRED_SIZE))
														.add(
																groupLayout_6
																		.createSequentialGroup()
																		.addContainerGap()
																		.add(
																				sashForm,
																				GroupLayout.PREFERRED_SIZE,
																				532,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		groupLayout_6.setVerticalGroup(groupLayout_6.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_6.createSequentialGroup().addContainerGap().add(
						group_2, GroupLayout.PREFERRED_SIZE, 41,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(sashForm,
						GroupLayout.PREFERRED_SIZE, 302, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.RELATED).add(
								groupLayout_6.createParallelGroup(
										GroupLayout.BASELINE).add(
										analisiZehatzaButton,
										GroupLayout.PREFERRED_SIZE, 23,
										GroupLayout.PREFERRED_SIZE).add(
										analisiAzkarraButton,
										GroupLayout.PREFERRED_SIZE, 23,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		biTestuKonparatuGroup.setLayout(groupLayout_6);
		final GroupLayout groupLayout_4 = new GroupLayout(this);
		groupLayout_4.setHorizontalGroup(groupLayout_4.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_4.createSequentialGroup().addContainerGap().add(
						biTestuKonparatuGroup, GroupLayout.PREFERRED_SIZE, 482,
						Short.MAX_VALUE).addContainerGap()));
		groupLayout_4.setVerticalGroup(groupLayout_4.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_4.createSequentialGroup().addContainerGap().add(
						biTestuKonparatuGroup, GroupLayout.PREFERRED_SIZE, 353,
						Short.MAX_VALUE).addContainerGap()));
		setLayout(groupLayout_4);
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
	 * Analisi azkarra egiteko botoiaren ekintza
	 * 
	 * @author Blizarazu
	 * 
	 */
	private final class AnalisiAzkarra extends SelectionAdapter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(final SelectionEvent e) {
			try {
				int emaitza = new Float(kud.analisiAzkarra() * 100).intValue();
				emaitzaLabel.setText(Messages.biTestuAntzeko1 + emaitza + Messages.biTestuAntzeko2);

				MessageDialog.openInformation(getShell(),
						Messages.analisiAzkEginda,
						Messages.biTestuAntzeko1 + emaitza
								+ Messages.biTestuAntzeko2);
			} catch (IOException e1) {
				e1.printStackTrace();

				MessageDialog.openError(getShell(), Messages.errorea,
						Messages.ezinFitxIrakurri);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see app.GUI.BarnePanela#setTestua(int, java.lang.String)
	 */
	@Override
	public void setTestua(int testuZenb, String fitx) {
		try {
			if (fitx != null) {
				this.kud.kargatuTestua(testuZenb, fitx);
				this.emaitzaLabel
						.setText(Messages.analisiAzkarrikEz);
				switch (testuZenb) {
				case 0:
					this.text.setText(fitx);
					this.styledText.setText(kud.getTestua(testuZenb)
							.getTestua());
					break;
				case 1:
					if (kud.getTestua(testuZenb).getMota() == Testua.TESTU_SOILA) {
						this.text_1.setText(fitx);
						this.styledText_1.setText(kud.getTestua(testuZenb)
								.getTestua());
					} else {
						this.text_1.setText("");
						this.styledText_1.setText("");
					}
					break;
				default:
					break;
				}
				if (styledText.getText().length() > 0
						&& styledText_1.getText().length() > 0) {
					analisiAzkarraButton.setEnabled(true);
					analisiZehatzaButton.setEnabled(true);
				} else {
					analisiAzkarraButton.setEnabled(false);
					analisiZehatzaButton.setEnabled(false);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

			MessageDialog.openError(getShell(), Messages.errorea,
					Messages.ezinFitxIrakurri);
		}
	}

	/**
	 * Testu guneen edukia ezabatuko dira. i=0 bada analizatzeko testuaren
	 * guneak garbituko dira eta 1 bada konparatzeko testuarenak.
	 * 
	 * @param i
	 *            zein testuko testu guneak garbitu nahi diren. 0 bada
	 *            analizatzeko testuaren guneak garbituko dira eta 1 bada
	 *            konparatzeko testuarenak.
	 */
	public void clearTestua(int i) {
		switch (i) {
		case 0:
			this.text.setText("");
			this.styledText.setText("");
			break;
		case 1:
			this.text_1.setText("");
			this.styledText_1.setText("");
			break;
		default:
			break;
		}

		if (styledText.getText().length() > 0
				&& styledText_1.getText().length() > 0) {
		} else {
			analisiAzkarraButton.setEnabled(false);
			analisiZehatzaButton.setEnabled(false);
		}
	}
}
