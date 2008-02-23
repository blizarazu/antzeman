package app.actions;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import app.GUI.MainGUI;
import app.GUI.wizards.AnZehatzaWizard;

/**
 * Analisi zehatza egiteko botoiaren ekintza
 * 
 * @author Blizarazu
 * 
 */
public class AnalisiZehatza extends SelectionAdapter {

	private MainGUI jabea;

	/**
	 * AnalisiZehatza ekintza hasieratzen du.
	 * 
	 * @param owner
	 *            aplikazio leiho jabea.
	 */
	public AnalisiZehatza(MainGUI owner) {
		this.jabea = owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(final SelectionEvent e) {
		AnZehatzaWizard wizard = new AnZehatzaWizard(this.jabea);
		WizardDialog dialog = new WizardDialog(this.jabea.getShell(), wizard);
		dialog.open();
	}
}