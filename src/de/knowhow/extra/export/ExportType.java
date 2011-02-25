package de.knowhow.extra.export;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import de.knowhow.base.Constants;
import de.knowhow.controller.MainController;
import de.knowhow.extra.Splash;

public abstract class ExportType implements Runnable {

	protected MainController mc;
	protected static Splash splash;

	public ExportType(MainController mc) {
		this.mc = mc;
	}

	private void doExport() {
		JFileChooser fcExport = new JFileChooser();
		fcExport.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fcExport.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fcExport.getSelectedFile();
			URL url = ClassLoader
					.getSystemResource("de/knowhow/resource/img/loading.png");
			Image image = null;
			if (url != null)
				try {
					image = ImageIO.read(url);
				} catch (IOException ex) {
				}
			splash = new Splash(image, Constants.getText("splash.init"));
			splash.setVisible(true);
			try {
				innerExport(file.getAbsolutePath());
			} catch (IOException e) {
				mc.error(e);
				e.printStackTrace();
			}
			splash.close();
		}
	}

	protected abstract void innerExport(String path) throws IOException;

	@Override
	public void run() {
		doExport();
	}
}
