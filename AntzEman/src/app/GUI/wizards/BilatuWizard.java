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
import org.htmlparser.util.ParserException;

import app.GUI.MainGUI;
import app.exceptions.MorfreusIsDownException;
import app.kudeaketa.Kudeatzailea;
import app.objektuak.BilaketaEmaitza;

import com.swtdesigner.SWTResourceManager;
import app.GUI.Messages;

/**
 * Interneten bilaketa egiteko morroia.
 * 
 * @author Blizarazu
 * 
 */
public class BilatuWizard extends Wizard {

	private TestuenPropWizard page1;
	private BilaketaPropWizard page2;
	private BilaketaEginWizard page3;
	private Kudeatzailea kud;
	private MainGUI jabea;

	private String aukHizkuntza;
	private String aukLekua;

	private BilaketaEmaitza emaitzak;

	private Button hasiButton;

	/**
	 * BilatuWizard hasieratzen du.
	 * 
	 * @param jabea
	 *            aplikazio leiho jabea
	 */
	public BilatuWizard(MainGUI jabea) {
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
		page2 = new BilaketaPropWizard();
		page3 = new BilaketaEginWizard();
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
		jabea.setBilaketaEmaitza(emaitzak);
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
			setTitle(Messages.internetenBilatu);
			setDescription(Messages.anTestuHizkAukeratu);
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
			analisatzekoLabel.setBounds(21, 10, 120, 19);
			analisatzekoLabel.pack();

			final CLabel label = new CLabel(container, SWT.NONE);
			label.setBounds(143, 10, 120, 19);
			label.setText(kud.getTestua(0).getPath());
			label.pack();

			final CLabel hizkuntzaLabel = new CLabel(container, SWT.NONE);
			hizkuntzaLabel.setFont(SWTResourceManager.getFont("", 9, SWT.BOLD));
			hizkuntzaLabel.setText(Messages.testuarenHizkuntza);
			hizkuntzaLabel.setBounds(17, 35, 124, 19);
			hizkuntzaLabel.pack();

			final ComboViewer comboViewer = new ComboViewer(container,
					SWT.READ_ONLY);
			comboViewer.addSelectionChangedListener(new HizkuntzaAldatu());
			combo = comboViewer.getCombo();
			combo.setBounds(143, 35, 93, 21);
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
	 * Bilaketaren propietateak ezartzeko morroiaren orria
	 * 
	 * @author Blizarazu
	 * 
	 */
	private class BilaketaPropWizard extends WizardPage {

		private Combo combo;
		private Button esaldikaBut;

		/**
		 * Create the wizard
		 */
		private BilaketaPropWizard() {
			super("Analisi Zehatza");
			setTitle(Messages.internetenBilatu);
			setDescription(Messages.bilatuDeskr2);
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
			esaldiakKonparatuAnalisiButton.setBounds(24, 82, 116, 19);
			esaldiakKonparatuAnalisiButton.pack();

			final CLabel zehaztasunMailaLabel = new CLabel(container, SWT.NONE);
			zehaztasunMailaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			zehaztasunMailaLabel.setText(Messages.zehaztasunMaila);
			zehaztasunMailaLabel.setBounds(12, 35, 116, 19);
			zehaztasunMailaLabel.pack();

			final Button paragrafoakKonparatuButton = new Button(container,
					SWT.RADIO);
			paragrafoakKonparatuButton.setSelection(true);
			paragrafoakKonparatuButton
					.setToolTipText(Messages.paragrafoToolTip);
			paragrafoakKonparatuButton.setText(Messages.paragrafoakKonparatu);
			paragrafoakKonparatuButton.setBounds(24, 60, 136, 16);
			paragrafoakKonparatuButton.pack();

			final CLabel bilaketaLekuaLabel = new CLabel(container, SWT.NONE);
			bilaketaLekuaLabel.setBounds(12, 10, 89, 19);
			bilaketaLekuaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			bilaketaLekuaLabel.setText(Messages.bilaketaGunea);
			bilaketaLekuaLabel.pack();

			final ComboViewer comboViewer = new ComboViewer(container,
					SWT.READ_ONLY);
			comboViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {
						public void selectionChanged(
								final SelectionChangedEvent event) {
							aukLekua = combo.getItem(combo.getSelectionIndex());
							page3.setLekua(aukLekua);
						}
					});
			combo = comboViewer.getCombo();
			combo.setBounds(107, 10, 89, 19);
			combo.setItems(kud.getBilaketaGuneak());
			combo.select(0);
			aukLekua = combo.getItem(combo.getSelectionIndex());
			combo.pack();
		}

		/**
		 * Bilaketa esaldika egingo den ala ez itzultzen du. Esaldika egingo ez
		 * bada paragrafoka egingo da.
		 * 
		 * @return true bilaketa esaldika egingo bada eta paragrafoka egingo
		 *         bada false
		 */
		private boolean esaldika() {
			return esaldikaBut.getSelection();
		}
	}

	/**
	 * Bilaketa hasteko morroiaren orria
	 * 
	 * @author Blizarazu
	 * 
	 */
	private class BilaketaEginWizard extends WizardPage {

		private CLabel hizkLabel;
		private CLabel lekuaLabel;
		private CLabel egoeraLabel;
		private ProgressBar progressBar;

		/**
		 * Create the wizard
		 */
		private BilaketaEginWizard() {
			super("wizardPage");
			setTitle(Messages.internetenBilatu);
			setDescription(Messages.bilaketaDeskrib1);
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
			hasiButton.addSelectionListener(new BilaketaHasi());
			hasiButton.setText(Messages.bilatzenHasi);
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

			CLabel bilatzekoLekuaLabel;
			bilatzekoLekuaLabel = new CLabel(composite, SWT.NONE);
			bilatzekoLekuaLabel.setFont(SWTResourceManager.getFont("", 9,
					SWT.BOLD));
			bilatzekoLekuaLabel.setText(Messages.bilaketaGunea);
			bilatzekoLekuaLabel.pack();

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
					GroupLayout.PREFERRED_SIZE, 482, Short.MAX_VALUE));
			groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
					GroupLayout.LEADING).add(
					groupLayout.createSequentialGroup().add(composite,
							GroupLayout.PREFERRED_SIZE, 213, Short.MAX_VALUE)
							.addContainerGap()));

			CLabel label_3;
			label_3 = new CLabel(composite, SWT.NONE);
			this.lekuaLabel = label_3;
			label_3.setText(aukLekua);
			label_3.pack();

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
																	groupLayout_1
																			.createSequentialGroup()
																			.add(
																					groupLayout_1
																							.createParallelGroup(
																									GroupLayout.TRAILING)
																							.add(
																									hizkuntzaLabel,
																									GroupLayout.PREFERRED_SIZE,
																									GroupLayout.DEFAULT_SIZE,
																									GroupLayout.PREFERRED_SIZE)
																							.add(
																									analizatzekoTestuaLabel,
																									GroupLayout.PREFERRED_SIZE,
																									GroupLayout.DEFAULT_SIZE,
																									GroupLayout.PREFERRED_SIZE)
																							.add(
																									bilatzekoLekuaLabel,
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
																									336,
																									Short.MAX_VALUE)
																							.add(
																									label,
																									GroupLayout.PREFERRED_SIZE,
																									336,
																									Short.MAX_VALUE)
																							.add(
																									label_3,
																									GroupLayout.PREFERRED_SIZE,
																									336,
																									Short.MAX_VALUE)))
															.add(
																	progressBar,
																	GroupLayout.PREFERRED_SIZE,
																	464,
																	Short.MAX_VALUE)
															.add(
																	GroupLayout.TRAILING,
																	hasiButton,
																	GroupLayout.PREFERRED_SIZE,
																	80,
																	GroupLayout.PREFERRED_SIZE)
															.add(
																	egoeraLabel,
																	GroupLayout.PREFERRED_SIZE,
																	464,
																	Short.MAX_VALUE))
											.addContainerGap()));
			groupLayout_1.setVerticalGroup(groupLayout_1.createParallelGroup(
					GroupLayout.LEADING).add(
					groupLayout_1.createSequentialGroup().addContainerGap()
							.add(
									groupLayout_1.createParallelGroup(
											GroupLayout.LEADING).add(
											analizatzekoTestuaLabel,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE).add(
											label, GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED).add(
									groupLayout_1.createParallelGroup(
											GroupLayout.LEADING).add(
											hizkuntzaLabel,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE).add(
											label_2,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED).add(
									groupLayout_1.createParallelGroup(
											GroupLayout.LEADING).add(
											bilatzekoLekuaLabel,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE).add(
											label_3,
											GroupLayout.PREFERRED_SIZE,
											GroupLayout.DEFAULT_SIZE,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.RELATED).add(
									egoeraLabel, GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED).add(
									progressBar, GroupLayout.PREFERRED_SIZE,
									GroupLayout.DEFAULT_SIZE,
									GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.RELATED).add(
									hasiButton).addContainerGap(45,
									Short.MAX_VALUE)));
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
		 * Lekuaren labelari testua ezartzen dio pasatako bilaketa gunearen
		 * izenarekin.
		 * 
		 * @param aukLekua
		 *            aukeratu den bilaketa gunearen izena
		 */
		public void setLekua(String aukLekua) {
			this.lekuaLabel.setText(aukLekua);
		}

		/**
		 * Bilaketa hasteko botoiaren ekintza
		 * 
		 * @author Blizarazu
		 * 
		 */
		private final class BilaketaHasi extends SelectionAdapter {
			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(final SelectionEvent e) {
				try {
					egoeraLabel.setText(Messages.bilaketaHasieratzen);
					progressBar.setSelection(0);
					kud.setHizkuntza(0, kud.getLaburdura(aukHizkuntza));
					progressBar.setSelection(15);
					kud.setBilaketaAukerak(page2.esaldika(), aukLekua);
					egoeraLabel.setText(Messages.bilaketaEgiten);
					progressBar.setSelection(50);
					emaitzak = kud.internetenBilatu();
					setPageComplete(true);
					hasiButton.setEnabled(false);
					egoeraLabel
							.setText(Messages.bilaketaBukatuta);
					progressBar.setSelection(100);
				} catch (ParserException e1) {
					e1.printStackTrace();

					progressBar.setSelection(100);
					egoeraLabel.setText(Messages.erroreBatSortuDa);

					MessageDialog
							.openError(
									getShell(),
									Messages.errorea,
									Messages.ezinBilaketaEgin);
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
				}
			}
		}
	}
}
