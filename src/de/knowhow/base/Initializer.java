package de.knowhow.base;

import java.util.Locale;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.xml.DOMConfigurator;

/*
 * Class to initialize the workspace
 */

public class Initializer {

	private static Initializer instance;

	private Initializer() {

	}

	public static synchronized Initializer getInstance() {
		if (instance == null) {
			instance = new Initializer();
		}
		return instance;
	}

	public boolean initializeApp() {

		try {
			DOMConfigurator.configure("logger.xml");

			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
			Config config = Config.getInstance();
			Constants.setLanguage(new Locale(config.getProperty("lang")));
			ViewConstants.reload(config);
		} catch (Exception e) {
			return false;
		}
		return true;

	}

}
