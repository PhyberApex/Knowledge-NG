package de.knowhow.model.db;

/*
 * Class especially for working with SQLite database
 */

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import de.knowhow.exception.DatabaseException;

public class DAO_SQLite extends DAO {

	private static Logger logger = Logger.getLogger(DAO_SQLite.class.getName());
	
	public void openDB() throws DatabaseException {
		try {
			Class.forName("org.sqlite.JDBC");
			this.setConnection(DriverManager.getConnection("jdbc:sqlite:"
					+ this.getDatabaseName()));
		} catch (Exception e) {
			throw new DatabaseException("openError");
		}
	}

	protected void createDB() {
		Statement state;
		try {
			state = this.getConnection().createStatement();
			try {
				state.executeUpdate("DROP TABLE article");
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				state.executeUpdate("DROP TABLE topic");
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				state.executeUpdate("DROP TABLE attachment");
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				state.executeUpdate("DROP TABLE css");
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				state
						.executeUpdate("CREATE TABLE article (article_ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, lastEdit TEXT, content TEXT, topic_ID_FK INTEGER, FOREIGN KEY (topic_ID_FK) REFERENCES topic(topic_ID));");
				state
						.executeUpdate("CREATE TABLE topic (topic_ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, topic_ID_FK INTEGER, FOREIGN KEY (topic_ID_FK) REFERENCES topic(topic_ID))");
				state
						.executeUpdate("CREATE TABLE attachment (image TEXT, attachment_ID INTEGER PRIMARY KEY  AUTOINCREMENT, name TEXT, article_ID_FK INTEGER, bin BLOB);");
				state
						.executeUpdate("CREATE TABLE css (css_ID INTEGER PRIMARY KEY, rule TEXT, tag TEXT);");
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}