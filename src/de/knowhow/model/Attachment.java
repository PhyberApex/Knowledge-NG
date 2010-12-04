package de.knowhow.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import de.knowhow.exception.DatabaseException;
import de.knowhow.model.db.DAO;

public class Attachment {

	private int attachment_ID;
	private boolean image;
	private String name;
	private byte[] binary;
	private DAO db;
	private int article_ID_FK;
	private Logger logger = Logger.getRootLogger();

	public Attachment(DAO db, int attachment_ID, String name,
			int article_ID_FK, boolean image) {
		this.db = db;
		this.attachment_ID = attachment_ID;
		this.name = name;
		this.article_ID_FK = article_ID_FK;
		this.image = image;
	}

	public Attachment(DAO db, String name, int artID, byte[] bin, boolean image)
			throws DatabaseException {
		this.db = db;
		this.setAttachment_ID(getNextID() + 1);
		this.setName(name);
		this.setArticle_ID_FK(artID);
		this.setBinary(bin);
		this.image = image;
		try {
			this.db
					.prepareStatement("INSERT INTO attachment (name, image, article_ID_FK, binary) values(?, ?, ?, ?);");
			this.db.getStatement().setString(1, this.name);
			this.db.getStatement().setBoolean(2, this.image);
			this.db.getStatement().setInt(3, this.article_ID_FK);
			this.db.getStatement().setBytes(4, this.binary);
			this.db.executeBatch();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAttachment_ID() {
		return attachment_ID;
	}

	public void setAttachment_ID(int attachmentID) {
		attachment_ID = attachmentID;
	}

	public byte[] getBinary() {
		return binary;
	}

	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

	public int getArticle_ID_FK() {
		return article_ID_FK;
	}

	public void setArticle_ID_FK(int articleIDFK) {
		article_ID_FK = articleIDFK;
	}

	public boolean isImage() {
		return image;
	}

	public void setImage(boolean image) {
		this.image = image;
	}

	private int getNextID() throws DatabaseException {
		int nextID = 0;
		try {
			this.db
					.prepareStatement("SELECT attachment_ID AS next FROM attachment ORDER BY attachment_ID DESC LIMIT 1");
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

	public void loadBin() throws DatabaseException {
		try {
			this.db
					.prepareStatement("SELECT binary FROM attachment WHERE attachment_ID = "
							+ this.attachment_ID + ";");
			ResultSet rs = this.db.execute();
			rs.next();
			this.binary = rs.getBytes(1);
			rs.close();
		} catch (DatabaseException e) {
			throw e;
		} catch (SQLException e) {
			logger.error("Fehler im SQL-Syntax: " + e.getMessage());
		}
	}
}