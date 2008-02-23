package app;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import app.GUI.MainGUI;

import com.swtdesigner.SWTResourceManager;

/**
 * AntzEman aplikazioaren hasieratzailea.
 * 
 * @author Blizarazu
 * 
 */
public class AntzemanLauncher {

	/**
	 * AntzEman aplikazioa hasieratzen du.
	 */
	public static void main(String args[]) {
		final Display display = new Display();
		final Image image = new Image(display, SWTResourceManager.getImage(
				AntzemanLauncher.class, "/app/resources/images/splash.png")
				.getImageData());
		final Shell splash = new Shell(SWT.ON_TOP);
		final ProgressBar bar = new ProgressBar(splash, SWT.NONE);
		final int[] count = new int[] { 1 };
		bar.setMaximum(100);
		Label label = new Label(splash, SWT.NONE);
		label.setImage(image);
		FormLayout layout = new FormLayout();
		splash.setLayout(layout);
		FormData labelData = new FormData();
		labelData.right = new FormAttachment(100, 0);
		labelData.bottom = new FormAttachment(100, 0);
		label.setLayoutData(labelData);
		FormData progressData = new FormData();
		progressData.left = new FormAttachment(0, 5);
		progressData.right = new FormAttachment(100, -5);
		progressData.bottom = new FormAttachment(100, -5);
		bar.setLayoutData(progressData);
		splash.pack();
		Rectangle splashRect = splash.getBounds();
		Rectangle displayRect = display.getBounds();
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		splash.setLocation(x, y);
		splash.open();
		display.asyncExec(new Runnable() {
			public void run() {
				try {
					// FreeLing-en liburutegia kargatu
					System.loadLibrary("morfo_java");
					bar.setSelection(65);
					// Aplikazioa kargatu
					MainGUI window = new MainGUI();
					bar.setSelection(90);

					window.setBlockOnOpen(true);
					bar.setSelection(100);
					try {
						Thread.sleep(1000);
					} catch (Throwable e) {
					}
					splash.close();
					image.dispose();
					window.open();
					window.saveConfig();
					Display.getCurrent().dispose();
				} catch (Exception e) {
					e.printStackTrace();
					splash.close();
					image.dispose();
					MessageDialog.openError(new Shell(), "Errorea",
							"Errorea aplikazioa abiaraztean.");

				}
				--count[0];
			}
		});
		while (count[0] > 0) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
