package app.GUI;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.grouplayout.GroupLayout;
import org.eclipse.swt.layout.grouplayout.LayoutStyle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import app.objektuak.AnalisiEmaitza;
import app.objektuak.TestuZati;
import app.objektuak.Testua;

import com.swtdesigner.SWTResourceManager;

/**
 * @author Blizarazu
 * 
 */
public class EmaitzakComposite extends Composite implements BarnePanela {

	private Tree tree;
	private StyledText styledText_1;
	private StyledText styledText;

	private Group treeGroup;
	private Composite composite;

	private Group analisiarenPropietateakGroup;
	private Group analizatzekoTestuaGroup;
	private Group konparatzekoTestuaGroup;
	private Group emaitzakGordeGroup;

	private CLabel label_5;
	private CLabel label;
	private CLabel label_1;
	private CLabel label_2;
	private CLabel label_3;
	private CLabel label_4;

	private TreeColumn column1;
	private TreeColumn column2;

	private Button radioButton1;
	private Button radioButton2;

	private Spinner spinner;

	private Button filtratuButton;
	private Button emaitzakGordeButton;

	private AnalisiEmaitza emaitza;
	private Button testuakGordeButton;
	private Button testuakGordeButton_1;
	private MainGUI jabea;

	/**
	 * Create the composite
	 * 
	 * @param parent
	 * @param style
	 * @param owner
	 */
	public EmaitzakComposite(Composite parent, int style, MainGUI owner) {
		super(parent, style);

		this.jabea = owner;
		
		SashForm sashForm;

		ToolBar toolBar_1;

		Group group;

		CLabel fitxategiaLabel;

		CLabel hizkuntzaLabel;

		CLabel fitxategiaLabel_1;

		CLabel hizkuntzaLabel_1;

		SashForm sashForm_2;

		CLabel testuenArtekoBatazbestekoLabel;

		Group emaitzakAztertuGroup;
		emaitzakAztertuGroup = new Group(this, SWT.NONE);
		emaitzakAztertuGroup.setText(Messages.emaitzakAztertu);
		sashForm_2 = new SashForm(emaitzakAztertuGroup, SWT.VERTICAL);
		sashForm = new SashForm(sashForm_2, SWT.HORIZONTAL);

		treeGroup = new Group(sashForm, SWT.NONE);

		final TreeViewer treeViewer = new TreeViewer(treeGroup, SWT.BORDER);
		tree = treeViewer.getTree();
		tree.setHeaderVisible(true);

		this.column1 = new TreeColumn(tree, SWT.LEFT);
		column1.setText(Messages.testuZatiak);
		column2 = new TreeColumn(tree, SWT.LEFT);
		this.column2.setText(Messages.antzekotasuna);

		final SashForm sashForm_1 = new SashForm(sashForm, SWT.HORIZONTAL);

		final Group analizatzekoTestuaGroup_1 = new Group(sashForm_1, SWT.NONE);
		analizatzekoTestuaGroup_1.setText(Messages.analizatzekoTestua);

		styledText = new StyledText(analizatzekoTestuaGroup_1, SWT.WRAP
				| SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
		final GroupLayout groupLayout_1 = new GroupLayout(
				analizatzekoTestuaGroup_1);
		groupLayout_1.setHorizontalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_1.createSequentialGroup().addContainerGap().add(
						styledText, GroupLayout.PREFERRED_SIZE, 166,
						Short.MAX_VALUE).addContainerGap()));
		groupLayout_1.setVerticalGroup(groupLayout_1.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_1.createSequentialGroup().addContainerGap().add(
						styledText, GroupLayout.PREFERRED_SIZE, 188,
						Short.MAX_VALUE).addContainerGap()));
		analizatzekoTestuaGroup_1.setLayout(groupLayout_1);

		final Group konparatzekoTestuaGroup_1 = new Group(sashForm_1, SWT.NONE);
		konparatzekoTestuaGroup_1.setText(Messages.konparatzekoTestua);

		styledText_1 = new StyledText(konparatzekoTestuaGroup_1, SWT.WRAP
				| SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
		final GroupLayout groupLayout_2 = new GroupLayout(
				konparatzekoTestuaGroup_1);
		groupLayout_2.setHorizontalGroup(groupLayout_2.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_2.createSequentialGroup().addContainerGap().add(
						styledText_1, GroupLayout.PREFERRED_SIZE, 166,
						Short.MAX_VALUE).addContainerGap()));
		groupLayout_2.setVerticalGroup(groupLayout_2.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_2.createSequentialGroup().addContainerGap().add(
						styledText_1, GroupLayout.PREFERRED_SIZE, 188,
						Short.MAX_VALUE).addContainerGap()));
		konparatzekoTestuaGroup_1.setLayout(groupLayout_2);
		sashForm.setWeights(new int[] { 93, 339 });
		sashForm_1.setWeights(new int[] { 92, 92 });

		// Bi StyledText-ak sortu eta gero jarri behar da.
		treeViewer.addSelectionChangedListener(new TestuZatiAukeratzaile());

		final GroupLayout groupLayout_3 = new GroupLayout(treeGroup);
		groupLayout_3.setHorizontalGroup(groupLayout_3.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_3.createSequentialGroup().addContainerGap().add(
						tree, GroupLayout.PREFERRED_SIZE, 81, Short.MAX_VALUE)
						.addContainerGap()));
		groupLayout_3.setVerticalGroup(groupLayout_3.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_3.createSequentialGroup().addContainerGap().add(
						tree, GroupLayout.PREFERRED_SIZE, 188, Short.MAX_VALUE)
						.addContainerGap()));
		treeGroup.setLayout(groupLayout_3);
		group = new Group(sashForm_2, SWT.NONE);

		composite = new Composite(group, SWT.NONE);
		final StackLayout stackLayout = new StackLayout();
		composite.setLayout(stackLayout);

		toolBar_1 = new ToolBar(group, SWT.NONE);

		final ToolItem newItemToolItem = new ToolItem(toolBar_1, SWT.RADIO);
		newItemToolItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				((StackLayout) composite.getLayout()).topControl = analisiarenPropietateakGroup;
				composite.layout();
			}
		});
		newItemToolItem.setSelection(true);
		newItemToolItem.setText(Messages.analisiarenPropietateak);

		final ToolItem newItemToolItem_1 = new ToolItem(toolBar_1, SWT.RADIO);
		newItemToolItem_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				((StackLayout) composite.getLayout()).topControl = analizatzekoTestuaGroup;
				composite.layout();
			}
		});
		newItemToolItem_1.setText(Messages.analizatzekoTestua);

		final ToolItem newItemToolItem_2 = new ToolItem(toolBar_1, SWT.RADIO);
		newItemToolItem_2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				((StackLayout) composite.getLayout()).topControl = konparatzekoTestuaGroup;
				composite.layout();
			}
		});
		newItemToolItem_2.setText(Messages.konparatzekoTestua);

		analisiarenPropietateakGroup = new Group(composite, SWT.NONE);
		analisiarenPropietateakGroup.setText(Messages.analisiarenPropietateak);

		label_5 = new CLabel(analisiarenPropietateakGroup, SWT.NONE);

		radioButton1 = new Button(analisiarenPropietateakGroup, SWT.RADIO);
		radioButton1.addSelectionListener(new RadioButtonAukera());
		radioButton1.setSelection(true);
		radioButton1.setText(Messages.guztiaErakutsi);

		radioButton2 = new Button(analisiarenPropietateakGroup, SWT.RADIO);
		radioButton2.addSelectionListener(new RadioButtonAukera());
		radioButton2.setText(Messages.filtratuaErakutsi);

		spinner = new Spinner(analisiarenPropietateakGroup, SWT.BORDER);
		spinner.setEnabled(false);

		filtratuButton = new Button(analisiarenPropietateakGroup, SWT.NONE);
		filtratuButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				zuhaitzaKargatu(emaitza, spinner.getSelection());
			}
		});
		filtratuButton.setEnabled(false);
		filtratuButton.setText(Messages.filtratu);
		testuenArtekoBatazbestekoLabel = new CLabel(
				analisiarenPropietateakGroup, SWT.NONE);
		testuenArtekoBatazbestekoLabel
				.setText(Messages.batazbestekoAntzekotasuna);

		label_4 = new CLabel(analisiarenPropietateakGroup, SWT.NONE);

		final GroupLayout groupLayout_5 = new GroupLayout(
				analisiarenPropietateakGroup);
		groupLayout_5
				.setHorizontalGroup(groupLayout_5
						.createParallelGroup(GroupLayout.LEADING)
						.add(
								groupLayout_5
										.createSequentialGroup()
										.addContainerGap()
										.add(
												groupLayout_5
														.createParallelGroup(
																GroupLayout.LEADING)
														.add(
																groupLayout_5
																		.createSequentialGroup()
																		.add(
																				10,
																				10,
																				10)
																		.add(
																				spinner,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				filtratuButton))
														.add(radioButton1)
														.add(radioButton2)
														.add(
																groupLayout_5
																		.createSequentialGroup()
																		.add(
																				testuenArtekoBatazbestekoLabel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				LayoutStyle.RELATED)
																		.add(
																				label_4,
																				GroupLayout.PREFERRED_SIZE,
																				213,
																				Short.MAX_VALUE))
														.add(
																label_5,
																GroupLayout.PREFERRED_SIZE,
																0,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout_5.setVerticalGroup(groupLayout_5.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_5.createSequentialGroup().addContainerGap().add(
						groupLayout_5.createParallelGroup(GroupLayout.LEADING)
								.add(testuenArtekoBatazbestekoLabel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										label_4, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.RELATED).add(label_5,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addPreferredGap(
								LayoutStyle.RELATED).add(radioButton1)
						.addPreferredGap(LayoutStyle.RELATED).add(radioButton2)
						.addPreferredGap(LayoutStyle.RELATED).add(
								groupLayout_5.createParallelGroup(
										GroupLayout.BASELINE).add(spinner,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										filtratuButton)).addContainerGap(26,
								Short.MAX_VALUE)));
		analisiarenPropietateakGroup.setLayout(groupLayout_5);
		analizatzekoTestuaGroup = new Group(composite, SWT.NONE);
		analizatzekoTestuaGroup.setText(Messages.analizatzekoTestua);
		fitxategiaLabel = new CLabel(analizatzekoTestuaGroup, SWT.NONE);
		fitxategiaLabel.setFont(SWTResourceManager.getFont("", 8, SWT.BOLD));
		fitxategiaLabel.setText(Messages.fitxategia);
		fitxategiaLabel.pack();
		hizkuntzaLabel = new CLabel(analizatzekoTestuaGroup, SWT.NONE);
		hizkuntzaLabel.setFont(SWTResourceManager.getFont("", 8, SWT.BOLD));
		hizkuntzaLabel.setText(Messages.hizkuntza);
		hizkuntzaLabel.pack();
		label = new CLabel(analizatzekoTestuaGroup, SWT.NONE);

		label_1 = new CLabel(analizatzekoTestuaGroup, SWT.NONE);

		final GroupLayout groupLayout_6 = new GroupLayout(
				analizatzekoTestuaGroup);
		groupLayout_6.setHorizontalGroup(groupLayout_6.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_6.createSequentialGroup().addContainerGap().add(
						groupLayout_6.createParallelGroup(GroupLayout.LEADING)
								.add(GroupLayout.TRAILING, fitxategiaLabel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										GroupLayout.TRAILING, hizkuntzaLabel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.RELATED).add(
								groupLayout_6.createParallelGroup(
										GroupLayout.LEADING).add(label_1,
										GroupLayout.PREFERRED_SIZE, 320,
										Short.MAX_VALUE).add(label,
										GroupLayout.PREFERRED_SIZE, 320,
										Short.MAX_VALUE)).addContainerGap()));
		groupLayout_6.setVerticalGroup(groupLayout_6.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_6.createSequentialGroup().addContainerGap().add(
						groupLayout_6.createParallelGroup(GroupLayout.TRAILING)
								.add(fitxategiaLabel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(label,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.RELATED).add(
								groupLayout_6.createParallelGroup(
										GroupLayout.TRAILING).add(
										hizkuntzaLabel,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										label_1, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(20081, Short.MAX_VALUE)));
		analizatzekoTestuaGroup.setLayout(groupLayout_6);
		konparatzekoTestuaGroup = new Group(composite, SWT.NONE);
		konparatzekoTestuaGroup.setText(Messages.konparatzekoTestua);
		fitxategiaLabel_1 = new CLabel(konparatzekoTestuaGroup, SWT.NONE);
		fitxategiaLabel_1.setFont(SWTResourceManager.getFont("", 8, SWT.BOLD));
		fitxategiaLabel_1.setText(Messages.fitxategia);
		fitxategiaLabel_1.pack();
		hizkuntzaLabel_1 = new CLabel(konparatzekoTestuaGroup, SWT.NONE);
		hizkuntzaLabel_1.setFont(SWTResourceManager.getFont("", 8, SWT.BOLD));
		hizkuntzaLabel_1.setText(Messages.hizkuntza);
		hizkuntzaLabel_1.pack();
		label_2 = new CLabel(konparatzekoTestuaGroup, SWT.NONE);

		label_3 = new CLabel(konparatzekoTestuaGroup, SWT.NONE);

		final GroupLayout groupLayout_7 = new GroupLayout(
				konparatzekoTestuaGroup);
		groupLayout_7.setHorizontalGroup(groupLayout_7.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_7.createSequentialGroup().addContainerGap().add(
						groupLayout_7.createParallelGroup(GroupLayout.LEADING)
								.add(GroupLayout.TRAILING, hizkuntzaLabel_1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										GroupLayout.TRAILING,
										fitxategiaLabel_1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.RELATED).add(
								groupLayout_7.createParallelGroup(
										GroupLayout.LEADING).add(label_3,
										GroupLayout.PREFERRED_SIZE, 320,
										Short.MAX_VALUE).add(label_2,
										GroupLayout.PREFERRED_SIZE, 320,
										Short.MAX_VALUE)).addContainerGap()));
		groupLayout_7.setVerticalGroup(groupLayout_7.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_7.createSequentialGroup().addContainerGap().add(
						groupLayout_7.createParallelGroup(GroupLayout.TRAILING)
								.add(fitxategiaLabel_1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										label_2, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.RELATED).add(
								groupLayout_7.createParallelGroup(
										GroupLayout.TRAILING).add(
										hizkuntzaLabel_1,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE).add(
										label_3, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(20081, Short.MAX_VALUE)));
		konparatzekoTestuaGroup.setLayout(groupLayout_7);

		emaitzakGordeGroup = new Group(composite, SWT.NONE);
		emaitzakGordeGroup.setText(Messages.emaitzakGorde);

		testuakGordeButton = new Button(emaitzakGordeGroup, SWT.RADIO);
		testuakGordeButton.setSelection(true);
		testuakGordeButton.setText(Messages.testuakGorde);

		testuakGordeButton_1 = new Button(emaitzakGordeGroup, SWT.RADIO);
		testuakGordeButton_1.setText(Messages.testuakEzGorde);

		emaitzakGordeButton = new Button(emaitzakGordeGroup, SWT.NONE);
		emaitzakGordeButton.setEnabled(false);
		emaitzakGordeButton.addSelectionListener(new EmaitzakGorde());
		emaitzakGordeButton.setText(Messages.emaitzakGorde);
		final GroupLayout groupLayout_8 = new GroupLayout(emaitzakGordeGroup);
		groupLayout_8.setHorizontalGroup(groupLayout_8.createParallelGroup(
				GroupLayout.TRAILING).add(
				groupLayout_8.createSequentialGroup().addContainerGap().add(
						groupLayout_8.createParallelGroup(GroupLayout.LEADING)
								.add(testuakGordeButton).add(
										testuakGordeButton_1).add(
										emaitzakGordeButton)).addContainerGap(
						150, Short.MAX_VALUE)));
		groupLayout_8.setVerticalGroup(groupLayout_8.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_8.createSequentialGroup().addContainerGap().add(
						testuakGordeButton)
						.addPreferredGap(LayoutStyle.RELATED).add(
								testuakGordeButton_1).addPreferredGap(
								LayoutStyle.RELATED).add(emaitzakGordeButton)
						.add(77, 77, 77)));
		emaitzakGordeGroup.setLayout(groupLayout_8);

		final ToolItem newItemToolItem_3 = new ToolItem(toolBar_1, SWT.RADIO);
		newItemToolItem_3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				((StackLayout) composite.getLayout()).topControl = emaitzakGordeGroup;
				composite.layout();
			}
		});
		newItemToolItem_3.setText(Messages.emaitzakGorde);

		final GroupLayout groupLayout_4 = new GroupLayout(group);
		groupLayout_4.setHorizontalGroup(groupLayout_4.createParallelGroup(
				GroupLayout.TRAILING).add(
				groupLayout_4.createSequentialGroup().addContainerGap().add(
						groupLayout_4.createParallelGroup(GroupLayout.TRAILING)
								.add(GroupLayout.LEADING, composite,
										GroupLayout.PREFERRED_SIZE, 457,
										Short.MAX_VALUE).add(
										GroupLayout.LEADING, toolBar_1,
										GroupLayout.PREFERRED_SIZE, 457,
										Short.MAX_VALUE)).addContainerGap()));
		groupLayout_4.setVerticalGroup(groupLayout_4.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_4.createSequentialGroup().add(toolBar_1,
						GroupLayout.PREFERRED_SIZE, 25,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.RELATED).add(composite,
						GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
						.addContainerGap()));
		stackLayout.topControl = analisiarenPropietateakGroup;

		group.setLayout(groupLayout_4);
		final GroupLayout groupLayout_9 = new GroupLayout(emaitzakAztertuGroup);
		groupLayout_9.setHorizontalGroup(groupLayout_9.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_9.createSequentialGroup().addContainerGap().add(
						sashForm_2, GroupLayout.PREFERRED_SIZE, 481,
						Short.MAX_VALUE).addContainerGap()));
		groupLayout_9.setVerticalGroup(groupLayout_9.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout_9.createSequentialGroup().addContainerGap().add(
						sashForm_2, GroupLayout.PREFERRED_SIZE, 481,
						Short.MAX_VALUE).addContainerGap()));
		emaitzakAztertuGroup.setLayout(groupLayout_9);
		sashForm_2.setWeights(new int[] { 223, 189 });
		final GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout.createSequentialGroup().addContainerGap().add(
						emaitzakAztertuGroup, GroupLayout.PREFERRED_SIZE, 505,
						Short.MAX_VALUE).addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.LEADING).add(
				groupLayout.createSequentialGroup().addContainerGap().add(
						emaitzakAztertuGroup, GroupLayout.PREFERRED_SIZE, 519,
						Short.MAX_VALUE).addContainerGap()));
		setLayout(groupLayout);
		//
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
	 * Zuhaitzak eta labelak beteko dira emandako analisien emaitzekin
	 * 
	 * @param ema
	 *            analisi zehatz baten emaitzak
	 */
	public void emaitzaKargatu(AnalisiEmaitza ema) {
		this.emaitza = ema;
		if (this.emaitza != null) {
			Testua anTestu = ema.getAnalizatzekoTestua();
			Testua konpTestu = ema.getTestua(1);
			this.emaitzakGordeButton.setEnabled(true);
			this.label.setText(anTestu.getPath());
			this.label.pack();
			this.label_1.setText(this.jabea.getKudeatzailea().getHizkuntza(anTestu.getHizkLaburdura()));
			this.label_1.pack();
			this.label_2.setText(konpTestu.getPath());
			this.label_2.pack();
			this.label_3.setText(this.jabea.getKudeatzailea().getHizkuntza(konpTestu.getHizkLaburdura()));
			this.label_3.pack();
			this.styledText.setText(anTestu.getTestua());
			this.styledText_1.setText(konpTestu.getTestua());
			String s = "";
			switch (ema.getMetodoa()) {
			case AnalisiEmaitza.PARAGRAFOKA:
				s = Messages.paragrafoak;
				this.label_5.setText(Messages.paragrafoakKonparatuDira);
				break;
			case AnalisiEmaitza.ESALDIKA:
				s = Messages.esaldiak;
				this.label_5
						.setText(Messages.esaldiakKonparatuDira);
				break;

			default:
				break;
			}
			this.label_5.pack();
			int i = new Float(this.emaitza.getBatazbestekoa() * 100).intValue();
			this.label_4.setText("%" + i);
			this.label_4.pack();
			this.treeGroup.setText(s);
			this.column1.setText(s);
			this.tree.getColumn(0).setText(s);
			this.tree.getColumn(0).pack();
			this.tree.getColumn(1).setText(Messages.antzekotasuna);
			this.tree.getColumn(1).pack();
			this.zuhaitzaKargatu(ema, -1);
		}
	}

	/**
	 * Testu zatien zuhaitza kargatuko da emandako emaitzekin, baina bakarrik
	 * iragazkia baino antzekotasun ehunekoa duten testu zatiak erakutsiko dira.
	 * 
	 * @param ema
	 *            kargatuko diren analisi zehatzaren emaitzak
	 * @param iragazkia
	 *            zein antzekotasun ehunekotik gorako emaitzak erakutsi behar
	 *            diren
	 */
	private void zuhaitzaKargatu(AnalisiEmaitza ema, int iragazkia) {
		List<TestuZati> keys = Collections.list(ema.keys());
		Collections.sort(keys);
		tree.removeAll();
		int i = 1;
		for (TestuZati tz1 : keys) {
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setData(tz1);
			int ehunMax = new Float(ema.getEhunekoMax(tz1) * 100).intValue();
			item.setText(new String[] {
					i + ": " + tz1.getHasieraPos() + "-" + tz1.getBukaeraPos(),
					"%" + String.valueOf(ehunMax) });
			List<TestuZati> eKeys = Collections.list(ema.getEmaitzak(tz1));
			Collections.sort(eKeys);
			int j = 1;
			for (TestuZati tz2 : eKeys) {
				int portzentaia = new Float(ema.getEmaitzaValue(tz1, tz2) * 100)
						.intValue();
				// Emandako portzentaietik gora edo berdin daudenak erakutsi edo
				// filtroa -1 bada
				// filtroa desaktibatu.
				if (portzentaia >= iragazkia || iragazkia == -1) {
					TreeItem subItem = new TreeItem(item, SWT.NONE);
					subItem.setData(tz2);
					subItem.setText(new String[] {
							j + ": " + tz2.getHasieraPos() + "-"
									+ tz2.getBukaeraPos(),
							"%" + String.valueOf(portzentaia) });
				}
				j++;
			}
			i++;
		}
	}

	/**
	 * Emaitzak gordetzeko botoiaren ekintza.
	 * 
	 * @author Blizarazu
	 * 
	 */
	private final class EmaitzakGorde extends SelectionAdapter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(final SelectionEvent e) {
			try {
				boolean testuakGorde = testuakGordeButton.getSelection();
				int jarraitu = SWT.YES;
				if (!testuakGorde) {
					MessageBox messageBox = new MessageBox(getShell(),
							SWT.ICON_WARNING | SWT.YES | SWT.NO);
					messageBox
							.setMessage(Messages.adiFitxEzAldatu);
					messageBox.setText(Messages.testuakEzAldatu);
					jarraitu = messageBox.open();
				}
				if (jarraitu == SWT.YES) {
					FileDialog dialog = new FileDialog(getShell(), SWT.SAVE);
					dialog.setFilterNames(new String[] { Messages.xmlFitx });
					dialog.setFilterExtensions(new String[] { "*.xml" });
					String fitx = dialog.open();
					if (fitx.length() > 0)
						emaitza.gorde(fitx, testuakGorde);
				}
			} catch (IOException e1) {
				e1.printStackTrace();

				MessageDialog.openError(getShell(), Messages.errorea,
						Messages.ezinFitxIrakurri);
			}
		}
	}

	/**
	 * Testu osoa gorde edo ez gordetzeko aukera botoien ekintza
	 * 
	 * @author Blizarazu
	 * 
	 */
	private final class RadioButtonAukera extends SelectionAdapter {
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(final SelectionEvent e) {
			if (radioButton2.getSelection()) {
				spinner.setEnabled(true);
				filtratuButton.setEnabled(true);
				zuhaitzaKargatu(emaitza, spinner.getSelection());
			} else {
				spinner.setEnabled(false);
				filtratuButton.setEnabled(false);
				zuhaitzaKargatu(emaitza, -1);
			}
		}
	}

	/**
	 * Testu zatien zuhaitzaren ekintza
	 * 
	 * @author Blizarazu
	 * 
	 */
	private final class TestuZatiAukeratzaile implements
			ISelectionChangedListener {

		private int parentHasi;
		private int parentBuka;
		private int umeHasi;
		private int umeBuka;

		private StyleRange[] defaultStyleRange1;
		private StyleRange[] defaultStyleRange2;

		public TestuZatiAukeratzaile() {
			parentHasi = 0;
			parentBuka = 0;
			umeHasi = 0;
			umeBuka = 0;
			defaultStyleRange1 = styledText.getStyleRanges();
			defaultStyleRange2 = styledText_1.getStyleRanges();
		}

		public void selectionChanged(final SelectionChangedEvent event) {
			TreeItem[] selectedItems = ((TreeViewer) event.getSource())
					.getTree().getSelection();
			if (selectedItems.length > 0) {
				TreeItem item = selectedItems[0];
				TreeItem parentItem;
				TestuZati tzParent;
				TestuZati tzUme;
				if ((parentItem = item.getParentItem()) != null) {
					tzUme = (TestuZati) item.getData();
					umeHasi = tzUme.getHasieraPos();
					umeBuka = tzUme.getBukaeraPos();
					tzParent = (TestuZati) parentItem.getData();
					markatuTestu2(umeHasi, umeBuka);
				} else {
					tzParent = (TestuZati) item.getData();
					TreeItem[] children = item.getItems();
					styledText_1.setStyleRanges(defaultStyleRange2);
					for (int i = 0; i < children.length; i++) {
						TestuZati tzChild = (TestuZati) children[i].getData();
						int ehuneko = new Float(emaitza.getEmaitzaValue(
								tzParent, tzChild) * 100).intValue();
						if (ehuneko > 0) {
							StyleRange styleRange = new StyleRange();
							// Gorriaren indarra zehazteko. 0 gorria da eta
							// hortik gora gorri argiagoa.
							// %10-etik 10-era joango da kolorea aldatzen.
							// 1-etik 10-era argiena izango da eta %100-a
							// ilunena.
							int kolore;
							if (ehuneko == 100) {
								kolore = 0;
							} else {
								kolore = (ehuneko - 1) / 10;
								kolore = (kolore + 1) * 25;
								kolore = 255 - kolore;
							}
							styleRange.start = tzChild.getHasieraPos();
							styleRange.length = tzChild.getBukaeraPos()
									- tzChild.getHasieraPos();
							styleRange.background = SWTResourceManager
									.getColor(255, kolore, kolore);
							try {
								styledText_1.setStyleRange(styleRange);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							}
						}
					}
				}
				parentHasi = tzParent.getHasieraPos();
				parentBuka = tzParent.getBukaeraPos();
				this.markatuTestu1(parentHasi, parentBuka);
			}
		}

		public void testuaMarkatu(int hasi1, int buka1, int hasi2, int buka2) {
			markatuTestu1(hasi1, buka1);
			markatuTestu2(hasi2, buka2);
		}

		public void markatuTestu1(int hasi1, int buka1) {
			try {
				StyleRange styleRange1 = new StyleRange();
				styleRange1.start = hasi1;
				styleRange1.length = buka1 - hasi1;

				// Testuaren atzeko partea gorria jarri.
				styleRange1.background = SWTResourceManager.getColor(255, 0, 0);

				styledText.setStyleRanges(defaultStyleRange1);
				styledText.setStyleRange(styleRange1);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}

		public void markatuTestu2(int hasi2, int buka2) {
			try {
				StyleRange styleRange2 = new StyleRange();
				styleRange2.start = hasi2;
				styleRange2.length = buka2 - hasi2;

				styleRange2.background = SWTResourceManager.getColor(255, 0, 0);

				styledText_1.setStyleRanges(defaultStyleRange2);
				styledText_1.setStyleRange(styleRange2);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
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
	}
}
