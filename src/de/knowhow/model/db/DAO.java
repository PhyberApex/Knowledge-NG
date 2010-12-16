package de.knowhow.model.db;

import java.sql.*;
import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;

public abstract class DAO {

	private PreparedStatement statement;
	private Connection connection;
	private String databaseName = Constants.getDBName();

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
			throw new DatabaseException(e.getMessage());// "sqlError");
		}
	}

	public void executeBatch() throws DatabaseException {
		try {
			this.getStatement().addBatch();
			this.getStatement().executeBatch();
		} catch (SQLException e) {
			throw new DatabaseException("sqlExecuteError");
		}
	}

	public ResultSet execute() throws DatabaseException {
		ResultSet rs = null;
		try {
			rs = this.getStatement().executeQuery();
		} catch (SQLException e) {
			throw new DatabaseException("sqlExecuteError");
		}
		return rs;
	}

	public void executeUpdate(String pSQL) throws DatabaseException {
		try {
			Statement stat = this.getConnection().createStatement();
			stat.executeUpdate(pSQL);
		} catch (SQLException e) {
			throw new DatabaseException("sqlExecuteError");
		}
	}

	public void closeDB() throws DatabaseException {
		try {
			this.getConnection().close();
		} catch (SQLException e) {
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
						.executeQuery("SELECT attachment_ID, name, article_ID_FK, bin, image FROM attachment;");
				state
						.executeQuery("SELECT css_ID, tag, rule FROM css;");
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