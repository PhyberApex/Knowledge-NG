package de.knowhow.model.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

import org.apache.log4j.Logger;

import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;

public abstract class DAO {

	private PreparedStatement statement;
	private Connection connection;
	private String databaseName = Constants.getDBName();
	private static Logger logger = Logger.getRootLogger();
	private boolean valid = false;

	public PreparedStatement getStatement() {
		return statement;
	}

	public void setStatement(PreparedStatement statement) {
		this.statement = statement;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	

	public void prepareStatement(String statement) throws DatabaseException {
		try {
			this.setStatement(this.getConnection().prepareStatement(statement));
		} catch (SQLException e) {
			logger.error("There was an error in the SQL-Statement: "
					+ e.getMessage());
			throw new DatabaseException(e.getMessage());// "sqlError");
		}
	}

	public void executeBatch() throws DatabaseException {
		try {
			this.getStatement().addBatch();
			this.getStatement().executeBatch();
		} catch (SQLException e) {
			logger
					.error("There was an error in the Execution of the SQL-Statement: "
							+ e.getMessage());
			throw new DatabaseException("sqlExecuteError");
		}
	}

	public ResultSet execute() throws DatabaseException {
		ResultSet rs = null;
		try {
			rs = this.getStatement().executeQuery();
		} catch (SQLException e) {
			logger
					.error("There was an error in the Execution of the SQL-Statement: "
							+ e.getMessage());
			throw new DatabaseException("sqlExecuteError");
		}
		return rs;
	}

	public void executeUpdate(String pSQL) throws DatabaseException {
		try {
			Statement stat = this.getConnection().createStatement();
			stat.executeUpdate(pSQL);
		} catch (SQLException e) {
			logger
					.error("There was an error in the Execution of the SQL-Statement: "
							+ e.getMessage());
			throw new DatabaseException("sqlExecuteError");
		}
	}

	public void closeDB() throws DatabaseException {
		try {
			this.getConnection().close();
		} catch (SQLException e) {
			logger.error("There was an error in closing the database: "
					+ e.getMessage());
			throw new DatabaseException("closeError");
		}
	}

	public boolean isValidDB() {
		try {
			Statement state = this.getConnection().createStatement();
			try {
				state
						.executeQuery("SELECT article_ID, name, content, lastEdit, topic_ID_FK FROM article;");
				state
						.executeQuery("SELECT name, topic_ID, topic_id_fk FROM topic;");
				state
						.executeQuery("SELECT attachment_ID, name, article_ID_FK, binary, image FROM attachment;");
			} catch (SQLException e) {
				return false;
			}
		} catch (SQLException e1) {
			return false;
		}
		return true;
	}

	public void checkDB() {
	}

	public void openDB() throws DatabaseException {
	}
}