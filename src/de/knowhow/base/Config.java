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

import org.apache.log4j.Logger;

import de.knowhow.extra.security.Coder;
import de.knowhow.model.db.DAO;
import de.knowhow.model.db.DAO_MYSQL;
import de.knowhow.model.db.DAO_SQLite;

public class Config {

	private static Config instance;
	private Properties prop;
	private static int SQLITE = 1;
	private static int MYSQL = 2;
	private static DAO dbHandle;
	private static Logger logger = Logger.getLogger(Config.class.getName());

	private Config() {
		FileReader reader = null;
		this.prop = new Properties();
		// Read file if exist, if not create it
		try {
			reader = new FileReader("knowledge.cfg");
		} catch (FileNotFoundException e) {
			File prop = new File("knowledge.cfg");
			try {
				Coder coder = Coder.getInstance();
				// Create file and set default values
				prop.createNewFile();
				reader = new FileReader("knowledge.cfg");
				this.prop.setProperty("databasetyp", coder.decode("1"));
				this.prop.setProperty("lang", coder.decode("EN"));
				this.prop
						.setProperty("defaultdb", coder.decode("knowledge.db"));
				saveChanges();
			} catch (IOException e1) {
				logger.error(e1.getMessage());
			}
		}
		try {
			this.prop.load(reader);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public void setProperty(String key, String value) {
		value = Coder.getInstance().decode(value);
		prop.setProperty(key, value);
	}

	public String getProperty(String key) {
		String value = this.prop.getProperty(key);
		return Coder.getInstance().encode(value);
	}

	// This method writes all changes to the file
	public void saveChanges() {
		FileWriter writer = null;
		try {
			writer = new FileWriter("knowledge.cfg");
			this.prop.store(writer, null);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public static synchronized Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	public synchronized DAO getDBHandle() {
		if (dbHandle == null) {
			String dbtype = prop.getProperty("databasetyp");
			if (dbtype.equals(String.valueOf(SQLITE))) {
				dbHandle = new DAO_SQLite();
			} else if (dbtype.equals(String.valueOf(MYSQL))) {
				dbHandle = new DAO_MYSQL();

			} else {
				// unknown databasetype switching to SQlite
				logger.info("unknown databasetype switching to SQLite");
				dbHandle = new DAO_SQLite();
			}
		}
		return dbHandle;
	}
}