package de.knowhow.model.db;

import java.sql.DriverManager;

import org.apache.log4j.Logger;

import de.knowhow.exception.DatabaseException;

public class DAO_SQLite extends DAO {

	private static Logger logger = Logger.getRootLogger();

	public void openDB() throws DatabaseException {
		try {
			Class.forName("org.sqlite.JDBC");
			this.setConnection(DriverManager.getConnection("jdbc:sqlite:"
					+ this.getDatabaseName()));
		} catch (Exception e) {
			logger.error("Error on opening Database: " + e.getMessage());
			throw new DatabaseException("openError");
		}
	}

}
