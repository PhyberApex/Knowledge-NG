package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import de.knowhow.base.Constants;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class Article {

	private int article_ID;
	private String name;
	private String lastEdit;
	private String content;
	private int topic_ID_FK;
	private DAO db;

	public Article(DAO db, int article_ID, String name, String lastEdit,
			String content, int topic_ID_FK) {
		this.db = db;
		this.article_ID = article_ID;
		this.name = name;
		this.lastEdit = lastEdit;
		this.content = content;
		this.topic_ID_FK = topic_ID_FK;
	}

	public Article(DAO db) throws DatabaseException {
		this.db = db;
		try {
			this.db.prepareStatement("INSERT INTO article (name, content, lastEdit, topic_ID_FK) values(?, ?, ?, ?);");
			this.db.getStatement().setString(1, "new Article");
			this.db.getStatement().setString(
					2,
					"<html>\n<head>\n<title></title>\n</head>\n<body>\n<p>"
							+ Constants.getText("menu.edit") + "->"
							+ Constants.getText("menu.edit.renameArticle")
							+ "</p>\n</body>\n</html>");
			this.db.getStatement().setString(3, Constants.getDate());
			this.db.getStatement().setInt(4, 0);
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
		}
		this.setArticle_ID(getNextID());
		this.name = "new Article";
		this.content = "<html>\n<head>\n<title></title>\n</head>\n<body>\n<p>"
				+ Constants.getText("menu.edit") + "->"
				+ Constants.getText("menu.edit.renameArticle")
				+ "</p>\n</body>\n</html>";
		this.lastEdit = Constants.getDate();
	}

	public int getArticle_ID() {
		return article_ID;
	}

	public void setArticle_ID(int article_ID) {
		this.article_ID = article_ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws DatabaseException {
		this.name = name;
		try {
			this.db.prepareStatement("UPDATE article SET name = ? WHERE article_ID = ?");
			this.db.getStatement().setString(1, this.getName());
			this.db.getStatement().setInt(2, this.getArticle_ID());
			this.db.executeBatch();
			setLastEdit(Constants.getDate());
		} catch (SQLException e) {
		}
	}

	public String getLastEdit() {
		return lastEdit;
	}

	public void setLastEdit(String lastEdit) throws DatabaseException {
		this.lastEdit = lastEdit;
		try {

			this.db.prepareStatement("UPDATE Article SET lastEdit = ? WHERE article_ID = ?");
			this.db.getStatement().setString(1, this.getLastEdit());
			this.db.getStatement().setInt(2, this.getArticle_ID());
			this.db.executeBatch();
		} catch (SQLException e) {
		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) throws DatabaseException {
		this.content = content;
		try {

			this.db.prepareStatement("UPDATE article SET content = ? WHERE article_ID = ?");
			this.db.getStatement().setString(1, this.getContent());
			this.db.getStatement().setInt(2, this.getArticle_ID());
			this.db.executeBatch();
			setLastEdit(Constants.getDate());
		} catch (SQLException e) {
		}
	}

	public int getTopic_ID_FK() {
		return topic_ID_FK;
	}

	public void setTopic_ID_FK(int topic_ID_FK) throws DatabaseException {
		this.topic_ID_FK = topic_ID_FK;
		try {
			this.db.prepareStatement("UPDATE article SET topic_ID_FK = ? WHERE article_ID = ?");
			this.db.getStatement().setInt(1, this.getTopic_ID_FK());
			this.db.getStatement().setInt(2, this.getArticle_ID());
			this.db.executeBatch();
			setLastEdit(Constants.getDate());
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
		}
	}

	public String toString() {
		return this.getName();
	}

	public void delete() throws DatabaseException {
		try {
			this.db.prepareStatement("DELETE FROM article WHERE article_ID = ?");
			this.db.getStatement().setInt(1, this.getArticle_ID());
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
		}
	}

	private int getNextID() throws DatabaseException {
		int nextID = 0;
		try {
			this.db.prepareStatement("SELECT article_ID AS next FROM article ORDER BY article_ID DESC LIMIT 1");
			ResultSet rs = this.db.execute();
			while (rs.next()) {
				nextID = Integer.parseInt(rs.getString("next"));
			}
			rs.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
		}
		return nextID;
	}
}