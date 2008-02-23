package app.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;

import app.GUI.MainGUI;
import app.GUI.Messages;

/**
 * Fitxategiak aukeratzeko ekintza.
 * 
 * @author Blizarazu
 * 
 */
public class FitxAukera extends SelectionAdapter {

	private MainGUI jabea;

	/**
	 * Fitxategiak aukeratzeko ekintza hasieratzen du.
	 * 
	 * @param owner
	 *            aplikazio leiho jabea.
	 */
	public FitxAukera(MainGUI owner) {
		super();
		this.jabea = owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(final SelectionEvent e) {
		int testuZenb = Integer.parseInt(e.widget.getData("testuZenb")
				.toString());
		FileDialog dialog = new FileDialog(this.jabea.getShell(), SWT.OPEN);
		dialog.setFilterNames(new String[] {
				Messages.jasanFitxGuzt, Messages.docFitxategiak,
				Messages.txtFitxategiak, Messages.datFitxategiak, Messages.pdfFitxategiak,
				Messages.fitxategiGuztiak });
		dialog.setFilterExtensions(new String[] {
				" *.doc; *.txt; *.dat; *.pdf", "*.doc", "*.txt", ".dat",
				".pdf", "*.*" });
		String fitx = dialog.open();
		if (fitx != null)
			this.jabea.setTestua(testuZenb, fitx);
	}
}
