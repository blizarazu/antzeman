package app.actions;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import app.GUI.MainGUI;
import app.GUI.wizards.BilatuWizard;

/**
 * Bilatzeko botoiaren ekintza
 * 
 * @author Blizarazu
 * 
 */
public class Bilatu extends SelectionAdapter {

	private MainGUI jabea;

	/**
	 * Bilatu ekintza hasieratzen du.
	 * 
	 * @param owner
	 *            aplikazio leiho jabea.
	 */
	public Bilatu(MainGUI owner) {
		this.jabea = owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(final SelectionEvent e) {
		BilatuWizard wizard = new BilatuWizard(jabea);
		WizardDialog dialog = new WizardDialog(jabea.getShell(), wizard);
		dialog.open();
	}
}
