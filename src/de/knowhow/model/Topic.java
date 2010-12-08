package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class Topic {

	private int topic_ID;
	private String name;
	private int topic_ID_FK;
	private DAO db;
	private static Logger logger = Logger.getRootLogger();

	public Topic(DAO db, int topic_ID, String name, int topic_ID_FK) {
		this.db = db;
		this.topic_ID = topic_ID;
		this.name = name;
		this.topic_ID_FK = topic_ID_FK;
	}

	public Topic(DAO db) throws DatabaseException {
		this.db = db;
		try {
			this.db
					.prepareStatement("INSERT INTO topic (name, topic_ID_FK) values(?, ?);");
			this.db.getStatement().setString(1, "New Topic");
			this.db.getStatement().setInt(2, 0);
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
		this.topic_ID = getNextID();
		this.name = "new Topic";
		this.topic_ID_FK = 0;
	}

	public int getTopic_ID() {
		return topic_ID;
	}

	public void setTopic_ID(int topic_ID) {
		this.topic_ID = topic_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws DatabaseException {
		try {
			this.name = name;
			this.db
					.prepareStatement("UPDATE topic SET name = ? WHERE topic_ID = ?");
			this.db.getStatement().setString(1, this.getName());
			this.db.getStatement().setInt(2, this.getTopic_ID());
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
	}

	public int getTopic_ID_FK() {
		return topic_ID_FK;
	}

	public void setTopic_ID_FK(int topic_ID_FK) throws DatabaseException {
		this.topic_ID_FK = topic_ID_FK;
		try {
			this.db
					.prepareStatement("UPDATE topic SET topic_ID_FK = ? WHERE topic_ID = ?");
			this.db.getStatement().setInt(1, this.getTopic_ID_FK());
			this.db.getStatement().setInt(2, this.getTopic_ID());
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
	}

	public String toString() {
		return this.getName();
	}

	public void delete() throws DatabaseException {
		try {
			this.db.prepareStatement("DELETE FROM topic WHERE topic_ID = ?");
			this.db.getStatement().setInt(1, this.getTopic_ID());
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
	}

	private int getNextID() throws DatabaseException {
		int nextID = 0;
		try {
			this.db
					.prepareStatement("SELECT topic_ID AS next FROM topic ORDER BY topic_ID DESC LIMIT 1");
			ResultSet rs = this.db.execute();

			while (rs.next()) {
				nextID = Integer.parseInt(rs.getString("next"));
			}
			rs.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
		return nextID;
	}
}
