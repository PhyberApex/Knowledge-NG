package de.knowhow.model.db;

import java.sql.DriverManager;

import org.apache.log4j.Logger;

import de.knowhow.exception.DatabaseException;

public class DAO_MYSQL extends DAO {

	private String host;
	private String user;
	private String password;
	private static Logger logger = Logger.getRootLogger();

	public void openDB() throws DatabaseException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.setConnection(DriverManager.getConnection("jdbc:mysql://"+host+"/"+getDatabaseName()+"?user="+user+"&password="+password));
		} catch (Exception e) {
			logger.error("Error on opening Database: " + e.getMessage());
			throw new DatabaseException("openError");
		}
	}

}
