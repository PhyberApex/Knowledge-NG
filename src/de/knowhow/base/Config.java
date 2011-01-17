package de.knowhow.base;

/**
 * Class for handling with the configuration file
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class Config {

	private Properties prop;

	public Config() {
		FileReader reader = null;
		this.prop = new Properties();
		// Read file if exist, if not create it
		try {
			reader = new FileReader("knowledge.cfg");
		} catch (FileNotFoundException e) {
			File prop = new File("knowledge.cfg");
			try {
				// Create file and set default values
				prop.createNewFile();
				reader = new FileReader("knowledge.cfg");
				this.prop.setProperty("databasetyp", "1");
				this.prop.setProperty("resolution", "1");
				this.prop.setProperty("lang", "EN");
				this.prop.setProperty("defaultdb", "knowledge.db");
				saveChanges();
			} catch (IOException e1) {
			}
		}
		try {
			this.prop.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setProperty(String key, String value) {
		prop.setProperty(key, value);
	}

	public String getProperty(String key) {
		return this.prop.getProperty(key);
	}

	//This method writes all changes to the file
	public void saveChanges() {
		FileWriter writer = null;
		try {
			writer = new FileWriter("knowledge.cfg");
			this.prop.store(writer, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}