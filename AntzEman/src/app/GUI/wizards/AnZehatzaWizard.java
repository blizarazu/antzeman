package app.GUI.wizards;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.xml.rpc.ServiceException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;

import app.GUI.MainGUI;
import app.exceptions.MorfreusIsDownException;
import app.kudeaketa.Kudeatzailea;
import app.objektuak.AnalisiEmaitza;

import com.swtdesigner.SWTResourceManager;
import app.GUI.Messages;

/**
 * Analisi zehatza egiteko morroia.
 * 
 * @author Blizarazu
 * 
 */
public class AnZehatzaWizard extends Wizard {

	private TestuenPropWizard page1;
	private AnalisiPropWizard page2;
	private AnalisiaEginWizard page3;
	private Kudeatzailea kud;
	private MainGUI jabea;

	private String aukHizkuntza;

	private AnalisiEmaitza emaitza;

	private Button hasiButton;

	/**
	 * AnZehatzaWizard hasieratzen du.
	 * 
	 * @param jabea
	 *            aplikazio leiho jabea
	 */
	public AnZehatzaWizard(MainGUI jabea) {
		super();
		this.setHelpAvailable(false);
		this.setNeedsProgressMonitor(true);
		this.jabea = jabea;
		this.kud = this.jabea.getKudeatzailea();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	public void addPages() {
		page1 = new TestuenPropWizard();
		page2 = new AnalisiPropWizard();
		page3 = new AnalisiaEginWizard();
		addPage(page1);
		addPage(page2);
		addPage(page3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	public boolean performFinish() {
		jabea.setEmaitza(emaitza);
		return true;
	}

	/**
	 * Testuen propietateak ezartzeko morroiaren orria
	 * 
	 * @author Blizarazu
	 * 
	 */
	private class TestuenPropWizard extends WizardPage {

		private Combo combo;

		/**
		 * Create the wizard
		 */
		private TestuenPropWizard() {
			super("wizardPage");
			setTitle(Messages.analisiZehatza);
			setDescription(Messages.anZehatzDesk1);
		}

		/**
		 * Create contents of the wizard
		 * 
		 * @param parent
		 */
		public void createControl(Composite parent) {
			Composite container = new Composite(parent, SWT.NULL);
			//
			setControl(container);

			final CLabel analisatzekoLabel = new CLabel(container, SWT.NONE);
			analisatzekoLabel
					.setToolTipText(Messages.anTestuaTooltip);
			analisatzekoLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			analisatzekoLabel.setText(Messages.analizatzekoTestua2);
			analisatzekoLabel.setBounds(17, 10, 120, 19);
			analisatzekoLabel.pack();

			final CLabel label = new CLabel(container, SWT.NONE);
			label.setBounds(143, 10, 120, 19);
			label.setText(kud.getTestua(0).getPath());
			label.pack();

			final CLabel konparatzekoTestuaLabel = new CLabel(container,
					SWT.NONE);
			konparatzekoTestuaLabel
					.setToolTipText(Messages.konpTestuaTooltip);
			konparatzekoTestuaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			konparatzekoTestuaLabel.setText(Messages.konparatzekoTestua2);
			konparatzekoTestuaLabel.setBounds(10, 35, 127, 19);
			konparatzekoTestuaLabel.pack();

			final CLabel label_1 = new CLabel(container, SWT.NONE);
			label_1.setBounds(143, 35, 120, 19);
			label_1.setText(kud.getTestua(1).getPath());
			label_1.pack();

			final CLabel testuenHizkuntzaLabel = new CLabel(container, SWT.NONE);
			testuenHizkuntzaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			testuenHizkuntzaLabel.setText(Messages.testuenHizkuntza);
			testuenHizkuntzaLabel.setBounds(25, 60, 112, 19);
			testuenHizkuntzaLabel.pack();

			final ComboViewer comboViewer = new ComboViewer(container,
					SWT.READ_ONLY);
			comboViewer.addSelectionChangedListener(new HizkuntzaAldatu());
			combo = comboViewer.getCombo();
			combo.setBounds(143, 60, 93, 21);
			combo.setItems(kud.getHizkuntzak());
			combo.select(0);
			aukHizkuntza = combo.getItem(combo.getSelectionIndex());
			combo.pack();
		}

		/**
		 * Hizkuntzen comboBoxaren ekintza
		 * 
		 * @author Blizarazu
		 * 
		 */
		private final class HizkuntzaAldatu implements
				ISelectionChangedListener {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(final SelectionChangedEvent event) {
				aukHizkuntza = combo.getItem(combo.getSelectionIndex());
				page3.setHizkuntza(aukHizkuntza);
			}
		}
	}

	/**
	 * Analisien propietateak ezartzeko morroiaren orria
	 * 
	 * @author Blizarazu
	 * 
	 */
	private class AnalisiPropWizard extends WizardPage {

		private Button esaldikaBut;

		/**
		 * Create the wizard
		 */
		private AnalisiPropWizard() {
			super("Analisi Zehatza");
			setTitle(Messages.analisiZehatza);
			setDescription(Messages.analisiDeskrib2);
		}

		/**
		 * Create contents of the wizard
		 * 
		 * @param parent
		 */
		public void createControl(Composite parent) {
			Composite container = new Composite(parent, SWT.NULL);
			//
			setControl(container);

			final Button esaldiakKonparatuAnalisiButton = new Button(container,
					SWT.RADIO | SWT.LEFT);
			this.esaldikaBut = esaldiakKonparatuAnalisiButton;
			esaldiakKonparatuAnalisiButton
					.setToolTipText(Messages.esaldiToolTip);
			esaldiakKonparatuAnalisiButton.setText(Messages.esaldiakKonparatu);
			esaldiakKonparatuAnalisiButton.setBounds(22, 57, 116, 19);
			esaldiakKonparatuAnalisiButton.pack();

			final CLabel zehaztasunMailaLabel = new CLabel(container, SWT.NONE);
			zehaztasunMailaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			zehaztasunMailaLabel.setText(Messages.zehaztasunMaila);
			zehaztasunMailaLabel.setBounds(10, 10, 116, 19);
			zehaztasunMailaLabel.pack();

			final Button paragrafoakKonparatuButton = new Button(container,
					SWT.RADIO);
			paragrafoakKonparatuButton.setSelection(true);
			paragrafoakKonparatuButton
					.setToolTipText(Messages.paragrafoToolTip);
			paragrafoakKonparatuButton.setText(Messages.paragrafoakKonparatu);
			paragrafoakKonparatuButton.setBounds(22, 35, 136, 16);
			paragrafoakKonparatuButton.pack();
		}

		/**
		 * Konparaketa esaldika egingo den ala ez itzultzen du. Esaldika egingo
		 * ez bada paragrafoka egingo da.
		 * 
		 * @return true konparaketa esaldika egingo bada eta paragrafoka egingo
		 *         bada false
		 */
		private boolean esaldika() {
			return esaldikaBut.getSelection();
		}
	}

	/**
	 * Analisia egiten hasteko morroiaren orria
	 * 
	 * @author Blizarazu
	 * 
	 */
	private class AnalisiaEginWizard extends WizardPage {

		private CLabel hizkLabel;
		private CLabel egoeraLabel;
		private ProgressBar progressBar;

		/**
		 * Create the wizard
		 */
		private AnalisiaEginWizard() {
			super("wizardPage");
			setTitle(Messages.analisiZehatza);
			setDescription(Messages.analisiDeskrib1);
		}

		/**
		 * Create contents of the wizard
		 * 
		 * @param parent
		 */
		public void createControl(Composite parent) {
			Composite container = new Composite(parent, SWT.NULL);
			//
			setControl(container);

			Composite composite;
			composite = new Composite(container, SWT.NONE);

			hasiButton = new Button(composite, SWT.NONE);
			hasiButton.addSelectionListener(new AnalisiaHasi());
			hasiButton.setText(Messages.hasi);
			hasiButton.pack();

			CLabel analizatzekoTestuaLabel;
			analizatzekoTestuaLabel = new CLabel(composite, SWT.NONE);
			analizatzekoTestuaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			analizatzekoTestuaLabel.setText(Messages.analizatzekoTestua2);
			analizatzekoTestuaLabel.pack();

			progressBar = new ProgressBar(composite, SWT.NONE);

			CLabel label;
			label = new CLabel(composite, SWT.NONE);
			label.setText(kud.getTestua(0).getPath());
			label.pack();

			CLabel konparatzekoTestuaLabel;
			konparatzekoTestuaLabel = new CLabel(composite, SWT.NONE);
			konparatzekoTestuaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			konparatzekoTestuaLabel.setText(Messages.konparatzekoTestua2);
			konparatzekoTestuaLabel.pack();

			CLabel label_1;
			label_1 = new CLabel(composite, SWT.NONE);
			label_1.setText(kud.getTestua(1).getPath());
			label_1.pack();

			CLabel hizkuntzaLabel;
			hizkuntzaLabel = new CLabel(composite, SWT.NONE);
			hizkuntzaLabel.setFont(SWTResourceManager.getFont("", 9, SWT.BOLD));
			hizkuntzaLabel.setText(Messages.hizkuntza);
			hizkuntzaLabel.pack();

			CLabel label_2;
			label_2 = new CLabel(composite, SWT.NONE);
			this.hizkLabel = label_2;
			label_2.setText(aukHizkuntza);
			label_2.pack();

			egoeraLabel = new CLabel(composite, SWT.NONE);
			egoeraLabel.setText(Messages.hasiGabe);
			egoeraLabel.pack();
			final GroupLayout groupLayout = new GroupLayout(container);
			groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
					GroupLayout.LEADING).add(composite,
					GroupLayout.PREFERRED_SIZE, 387, Short.MAX_VALUE));
			groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
					GroupLayout.LEADING).add(composite,
					GroupLayout.PREFERRED_SIZE, 229, Short.MAX_VALUE));

			final GroupLayout groupLayout_1 = new GroupLayout(composite);
			groupLayout_1
					.setHorizontalGroup(groupLayout_1
							.createParallelGroup(GroupLayout.LEADING)
							.add(
									groupLayout_1
											.createSequentialGroup()
											.addContainerGap()
											.add(
													groupLayout_1
															.createParallelGroup(
																	GroupLayout.LEADING)
															.add(
																	egoeraLabel,
																	GroupLayout.PREFERRED_SIZE,
																	464,
																	Short.MAX_VALUE)
															.add(
																	progressBar,
																	GroupLayout.PREFERRED_SIZE,
																	464,
																	Short.MAX_VALUE)
															.add(
																	groupLayout_1
																			.createSequentialGroup()
																			.add(
																					groupLayout_1
																							.createParallelGroup(
																									GroupLayout.TRAILING)
																							.add(
																									konparatzekoTestuaLabel,
																									GroupLayout.PREFERRED_SIZE,
																									GroupLayout.DEFAULT_SIZE,
																									GroupLayout.PREFERRED_SIZE)
																							.add(
																									analizatzekoTestuaLabel,
																									GroupLayout.PREFERRED_SIZE,
																									GroupLayout.DEFAULT_SIZE,
																									GroupLayout.PREFERRED_SIZE)
																							.add(
																									hizkuntzaLabel,
																									GroupLayout.PREFERRED_SIZE,
																									GroupLayout.DEFAULT_SIZE,
																									GroupLayout.PREFERRED_SIZE))
																			.addPreferredGap(
																					LayoutStyle.RELATED)
																			.add(
																					groupLayout_1
																							.createParallelGroup(
																									GroupLayout.LEADING)
																							.add(
																									label_2,
																									GroupLayout.PREFERRED_SIZE,
																									73,
																									Short.MAX_VALUE)
																							.add(
																									label_1,
																									GroupLayout.PREFERRED_SIZE,
																									329,
																									Short.MAX_VALUE)
																							.add(
																									label,
																									GroupLayout.PREFERRED_SIZE,
																									329,
																									Short.MAX_VALUE))
																			.addPreferredGap(
																					LayoutStyle.RELATED))
															.add(
																	GroupLayout.TRAILING,
																	hasiButton,
																	GroupLayout.PREFERRED_SIZE,
																	44,
																	GroupLayout.PREFERRED_SIZE))
											.addContainerGap()));
			groupLayout_1
					.setVerticalGroup(groupLayout_1
							.createParallelGroup(GroupLayout.LEADING)
							.add(
									groupLayout_1
											.createSequentialGroup()
											.addContainerGap()
											.add(
													groupLayout_1
															.createParallelGroup(
																	GroupLayout.LEADING,
																	false)
															.add(
																	groupLayout_1
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
																					GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(
																					LayoutStyle.RELATED,
																					GroupLayout.DEFAULT_SIZE,
																					Short.MAX_VALUE)
																			.add(
																					label_2,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE))
															.add(
																	groupLayout_1
																			.createSequentialGroup()
																			.add(
																					analizatzekoTestuaLabel,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(
																					LayoutStyle.RELATED)
																			.add(
																					konparatzekoTestuaLabel,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)
																			.addPreferredGap(
																					LayoutStyle.RELATED)
																			.add(
																					hizkuntzaLabel,
																					GroupLayout.PREFERRED_SIZE,
																					GroupLayout.DEFAULT_SIZE,
																					GroupLayout.PREFERRED_SIZE)))
											.add(19, 19, 19).add(egoeraLabel,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
													LayoutStyle.RELATED).add(
													progressBar,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
													LayoutStyle.RELATED).add(
													hasiButton).add(49, 49, 49)));
			composite.setLayout(groupLayout_1);
			container.setLayout(groupLayout);

			this.setPageComplete(false);
		}

		/**
		 * Hizkuntzaren labelari testua ezartzen dio pasatako hizkuntzaren
		 * izenarekin.
		 * 
		 * @param aukHizkuntza
		 *            aukeratu den hizkuntzaren izena.
		 */
		public void setHizkuntza(String aukHizkuntza) {
			this.hizkLabel.setText(aukHizkuntza);
		}

		/**
		 * Analisia hasteko botoiaren ekintza.
		 * 
		 * @author Blizarazu
		 * 
		 */
		private final class AnalisiaHasi extends SelectionAdapter {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(final SelectionEvent e) {
				try {
					egoeraLabel.setText(Messages.analisiaHasieratzen);
					progressBar.setSelection(0);
					kud.setHizkuntza(0, kud.getLaburdura(aukHizkuntza));
					progressBar.setSelection(15);
					kud.setHizkuntza(1, kud.getLaburdura(aukHizkuntza));
					progressBar.setSelection(30);
					kud.setAnalisiAukerak(page2.esaldika());
					egoeraLabel.setText(Messages.analisiaEgiten);
					progressBar.setSelection(50);
					emaitza = kud.analisiZehatza();
					setPageComplete(true);
					hasiButton.setEnabled(false);
					progressBar.setSelection(100);
					egoeraLabel.setText(Messages.analisiaBukatuta);
				} catch (SocketTimeoutException e1) {
					e1.printStackTrace();

					progressBar.setSelection(100);
					egoeraLabel.setText(Messages.erroreBatSortuDa);

					MessageDialog
							.openError(getShell(), Messages.errorea,
									Messages.ezinMorfeusKonektatu);
				} catch (IOException e1) {
					e1.printStackTrace();

					progressBar.setSelection(100);
					egoeraLabel.setText(Messages.erroreBatSortuDa);

					MessageDialog.openError(getShell(), Messages.errorea,
							Messages.ezinFitxIrakurri);
				} catch (MorfreusIsDownException e1) {
					e1.printStackTrace();

					progressBar.setSelection(100);
					egoeraLabel.setText(Messages.erroreBatSortuDa);

					String mezua = Messages.morfeusErorita;
					String infMail = e1.getInformMail();
					if (infMail.length() > 0)
						mezua += "\n\n" + Messages.mezuaBidali1 + " "
								+ e1.getInformMail()
								+ " " + Messages.mezuaBidali2;
					MessageDialog.openError(getShell(),
							Messages.morfeusZerbitzuaEroritaDago, mezua);
					System.out.println(e1.getStr());
				} catch (ServiceException e1) {
					e1.printStackTrace();

					progressBar.setSelection(100);
					egoeraLabel.setText(Messages.erroreBatSortuDa);

					MessageDialog
							.openError(getShell(), Messages.errorea,
									Messages.ezinMorfeusKonektatu);
				} catch (Exception e1) {
					e1.printStackTrace();

					progressBar.setSelection(100);
					egoeraLabel.setText(Messages.erroreBatSortuDa);

					MessageDialog
							.openError(getShell(), Messages.errorea,
									Messages.ezinMorfeusKonektatu);
				}
			}
		}
	}
}
