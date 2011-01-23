package de.knowhow.model.db;

/*
 * Class especially for working with MYSQL database
 */

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import de.knowhow.base.Config;
import de.knowhow.exception.DatabaseException;

public class DAO_MYSQL extends DAO {

	private String host;
	private String user;
	private String password;
	private static Logger logger = Logger.getLogger(DAO_MYSQL.class.getName());

	public void openDB() throws DatabaseException {
		Config config = Config.getInstance();
		this.host = config.getProperty("host");
		this.user = config.getProperty("user");
		this.password = config.getProperty("password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.setConnection(DriverManager.getConnection("jdbc:mysql://"
					+ host + "/" + getDatabaseName() + "?user=" + user
					+ "&password=" + password));
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
						.executeUpdate("CREATE TABLE article (article_ID INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), lastEdit VARCHAR(10), content LONGTEXT, topic_ID_FK INTEGER, FOREIGN KEY (topic_ID_FK) REFERENCES topic(topic_ID));");
				state
						.executeUpdate("CREATE TABLE topic (topic_ID INTEGER PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), topic_ID_FK INTEGER, FOREIGN KEY (topic_ID_FK) REFERENCES topic(topic_ID))");
				state
						.executeUpdate("CREATE TABLE attachment (image VARCHAR(5), attachment_ID INTEGER AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), bin BLOB, article_ID_FK INTEGER);");
				state
						.executeUpdate("CREATE TABLE css (css_ID ID INTEGER AUTO_INCREMENT PRIMARY KEY, rule LONGTEXT, tag VARCHAR(255));");
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}