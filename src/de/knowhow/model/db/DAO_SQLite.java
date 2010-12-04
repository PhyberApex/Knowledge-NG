package de.knowhow.model.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

	private void createDB() {
		Statement state;
		try {
			state = this.getConnection().createStatement();
			try {
				state.executeUpdate("DROP TABLE article");
			} catch (SQLException e) {
			}
			try {
				state
						.executeUpdate("CREATE TABLE article (article_ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, lastEdit TEXT, content TEXT, topic_ID_FK INTEGER, FOREIGN KEY (topic_ID_FK) REFERENCES topic(topic_ID));");
			} catch (SQLException e) {
			}
			try {
				state.executeUpdate("DROP TABLE topic");
			} catch (SQLException e) {
			}
			try {
				state
						.executeUpdate("CREATE TABLE topic (topic_ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, topic_ID_FK INTEGER, FOREIGN KEY (topic_ID_FK) REFERENCES topic(topic_ID))");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			try {
				state.executeUpdate("DROP TABLE attachment");
			} catch (SQLException e) {
			}
			try {
				state
						.executeUpdate("CREATE TABLE attachment (image TEXT, attachment_ID INTEGER PRIMARY KEY  AUTOINCREMENT, name TEXT, article_ID_FK INTEGER, bin BLOB);");
			} catch (SQLException e) {
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void checkDB() {
		if (!isValidDB()) {
			createDB();
		}
	}
}
